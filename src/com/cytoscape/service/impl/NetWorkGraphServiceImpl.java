package com.cytoscape.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cytoscape.dao.NetWorkGraphDao;
import com.cytoscape.dao.impl.NetWorkGraphDaoImpl;
import com.cytoscape.domain.NetworkEdge;
import com.cytoscape.domain.NetworkNode;
import com.cytoscape.service.NetWorkGraphService;
import com.cytoscape.utils.ConnectionPool;

public class NetWorkGraphServiceImpl implements NetWorkGraphService{

	@Override
	public String getTreeNodes() {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
		String json="";
		StringBuilder builder=new StringBuilder("[");
		try {
			List<NetworkNode> nodes = graphDao.getAllNetworkNodes();
			Iterator<NetworkNode> iterator = nodes.iterator();
			while(iterator.hasNext()){
				NetworkNode node = iterator.next();
				builder.append("{id:'"+node.getId()+"',text:'"+node.getName()+"',leaf:true}");
				if(iterator.hasNext()){
					builder.append(",");
				}
			}
			builder.append("]");
			json="[{id:'root',text:'网络图节点',expanded:true,children:"+builder.toString()+"}]";
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String save(Map<String, Object> paramMap) {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
		String json="";
		NetworkNode node=new NetworkNode();
		//节点id
		String id="";
		if(paramMap.get("id")==null||"".equals(paramMap.get("id").toString())){
			id=UUID.randomUUID().toString().replace("-", "");
		}else{
			id=paramMap.get("id").toString();
		}
		node.setId(id);
		node.setName(paramMap.get("name").toString());
		node.setShape(paramMap.get("shape").toString());
		node.setBackgroundColor(paramMap.get("backgroundColor").toString());
		
		String lineStyle = paramMap.get("lineStyle").toString();
		
		float width=0;
		if(paramMap.get("width")!=null){
			width = Float.parseFloat(paramMap.get("width").toString());
		}
		
		String lineColor = paramMap.get("lineColor").toString();
		String direction = paramMap.get("direction").toString();
		String curveStyle = paramMap.get("curveStyle").toString();
		String[] parentNodes = (String[]) paramMap.get("parentNode");
		
		List<NetworkEdge> edges=new ArrayList<NetworkEdge>();
		if(parentNodes!=null&&parentNodes.length>0){
			String source="";
			String target="";
			for (String parentNodeId : parentNodes) {
				NetworkEdge edge=new NetworkEdge();
				//节点-->父节点
				if("ntp".equals(direction)){
					
				}
				//父节点-->节点
				else if("ptn".equals(direction)){
					
				}
				source=id;
				target=parentNodeId;
				edge.setId(UUID.randomUUID().toString().replace("-", ""));
				edge.setLineStyle(lineStyle);
				edge.setLineColor(lineColor);
				edge.setCurveStyle(curveStyle);
				edge.setWidth(width);
				edge.setSource(source);
				edge.setTarget(target);
				edges.add(edge);
			}
		}
		
		Connection connection=null;
		try {
			connection=ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			//保存数据
			if(paramMap.get("id")==null||"".equals(paramMap.get("id").toString())){
				//保存节点信息
				graphDao.saveNetworkNode(node, connection);
				//保存节点连接信息
				graphDao.saveNetworkEdge(edges, connection);
			}
			//更新数据
			else{
				//保存节点信息
				graphDao.updateNetworkNode(node, connection);
				//先删除所有的链接信息
				graphDao.removeNetworkEdgeByNodeId(id,connection);
				//保存节点连接信息
				graphDao.saveNetworkEdge(edges, connection);
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@Override
	public String getNetworkGraph() {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
		String json="";
		StringBuilder elements=new StringBuilder();//节点 连接线
		StringBuilder style=new StringBuilder("[");//样式
		try {
			//获取所有的节点和节点的样式
			List<NetworkNode> nodes = graphDao.getAllNetworkNodes();
			StringBuilder nodesBuilder=new StringBuilder("[");
			//节点的默认样式
			style.append("{selector:'node',style:{'font-size': 14,'background-position-x':'5%','background-position-y':'5%','background-width':20,'background-height':20,'width': 'mapData(score, 0, 1, 20, 50)','height':'mapData(score, 0, 1, 20, 50)','content' : 'data(name)','text-opacity' : 0.5,'text-valign' : 'center','text-outline-width': 2,'min-zoomed-font-size': 8}},");
			Iterator<NetworkNode> iterator = nodes.iterator();
			while(iterator.hasNext()){
				NetworkNode node = iterator.next();
				nodesBuilder.append("{data:{id:'"+node.getId()+"',name:'"+node.getName()+"',shape:'"+node.getShape()+"','background-color':'"+node.getBackgroundColor()+"',positionX:'"+node.getPositionX()+"',positionY:'"+node.getPositionY()+"'},position:{x:"+node.getPositionX()+",y:"+node.getPositionY()+"}}");
				//节点的附加样式
				style.append("{selector:'node[id=\""+node.getId()+"\"]',style:{'background-color': '#"+node.getBackgroundColor()+"','text-outline-color': '#"+node.getBackgroundColor()+"','shape':'"+node.getShape()+"'}},");
				if(iterator.hasNext()){
					nodesBuilder.append(",");
				}
			}
			nodesBuilder.append("]");
			
			//获取所有的连接线 和连接线的样式
			List<NetworkEdge> edges = graphDao.getAllNetworkEdges();
			StringBuilder edgesBuilder=new StringBuilder("[");
			//连接线默认的样式
			style.append("{selector : 'edge',style : {'width' : 4,'target-arrow-shape' : 'triangle','line-color' : '#9dbaea','target-arrow-color' : '#9dbaea','opacity': 0.5,'line-color': '#888'}},");
			Iterator<NetworkEdge> edgesIterator= edges.iterator();
			while(edgesIterator.hasNext()){
				NetworkEdge edge = edgesIterator.next();
				edgesBuilder.append("{data:{id:'"+edge.getId()+"',source:'"+edge.getSource()+"',target:'"+edge.getTarget()+"',width:'"+edge.getWidth()+"'}}");
				//连接线附加样式
				style.append("{selector:'edge[id=\""+edge.getId()+"\"]',style:{'line-color': '#"+edge.getLineColor()+"','width': '"+edge.getWidth()+"','line-style':'"+edge.getLineStyle()+"','curve-style': '"+edge.getCurveStyle()+"','control-point-distances': '40 -40','control-point-weights': '0.25 0.75'}}");
				if(edgesIterator.hasNext()){
					edgesBuilder.append(",");
					style.append(",");
				}
			}
			edgesBuilder.append("]");
			style.append("]");
			
			elements.append("{nodes:"+nodesBuilder.toString()+",edges:"+edgesBuilder.toString()+"}");
			json="{style:"+style.toString()+",elements:"+elements.toString()+"}";
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String getCheckboxNodes(String nodeId) {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
		String json="";
		try {
			List<NetworkNode> nodes = graphDao.getAllNetworkNodes();
			StringBuilder builder=new StringBuilder("[");
			Iterator<NetworkNode> iterator = nodes.iterator();
			while(iterator.hasNext()){
				NetworkNode node = iterator.next();
				//过滤选中的节点
				if(node.getId().equals(nodeId)){
					continue;
				}
				builder.append("{id:'"+node.getId()+"',boxLabel:'"+node.getName()+"',name:'parentNode',inputValue:'"+node.getId()+"',margin:'0 0 0 0'}");
			    if(iterator.hasNext()){
			    	builder.append(",");
			    }
			}
			builder.append("]");
			json=builder.toString();
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String getDetailNode(String nodeId) {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
		String json="";
		try {
			//获取节点信息
			NetworkNode node=graphDao.getNetworkNodeById(nodeId);
			
			//获取节点的链接信息
			List<NetworkEdge> edges=graphDao.getNetworkEdgesByNodeId(nodeId);
			StringBuilder builder=new StringBuilder();
			String lineStyle="";
			String lineColor="";
			String curveStyle="";
			float width=0;
			
			Iterator<NetworkEdge> iterator = edges.iterator();
			while(iterator.hasNext()){
				NetworkEdge edge = iterator.next();
				builder.append(edge.getTarget());
				if(iterator.hasNext()){
					builder.append(",");
				}
				
				lineStyle=edge.getLineStyle();
				width=edge.getWidth();
				lineColor=edge.getLineColor();
				curveStyle=edge.getCurveStyle();
			}
			json="{id:'"+node.getId()+"',name:'"+node.getName()+"',shape:'"+node.getShape()+"',backgroundColor:'"+node.getBackgroundColor()+"',parentNode:'"+builder.toString()+"',lineStyle:'"+lineStyle+"',width:'"+width+"',lineColor:'"+lineColor+"',curveStyle:'"+curveStyle+"'}";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String delete(String nodeId) {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
		Connection connection=null;
		String json="";
		try {
			connection=ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			//删除节点连接信息
			graphDao.deleteNetworkEdges(nodeId,connection);
			//删除节点信息
			graphDao.deleteNetworkNode(nodeId,connection);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String updatePosition(Map map) {
		NetWorkGraphDao graphDao=new NetWorkGraphDaoImpl();
	    NetworkNode node=new NetworkNode();
	    String json="success";
	    node.setPositionX(Float.parseFloat(map.get("x").toString()));
	    node.setPositionY(Float.parseFloat(map.get("y").toString()));
	    node.setId(map.get("id").toString());
	    try {
			graphDao.updatePosition(node);
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}
		return json;
	}

}
