package com.cytoscape.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cytoscape.dao.NetWorkGraphDao;
import com.cytoscape.domain.NetworkEdge;
import com.cytoscape.domain.NetworkNode;
import com.cytoscape.utils.ConnectionPool;

public class NetWorkGraphDaoImpl implements NetWorkGraphDao{

	@Override
	public List<NetworkNode> getAllNetworkNodes() throws SQLException {
		Connection connection=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List<NetworkNode> nodes=new ArrayList<NetworkNode>();
		try{
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("SELECT ID,NAME,WIDTH,HEIGHT,SHAPE,SHAPE_POLYGON_POINTS,BACKGROUND_COLOR,BACKGROUND_BLACKEN,BACKGROUND_OPACITY,BORDER_WIDTH,BORDER_STYLE,BORDER_COLOR,BORDER_OPACITY,POSITION_X,POSITION_Y FROM NETWORK_NODE ORDER BY ID");
		    rs=pst.executeQuery();
		    while(rs.next()){
		    	NetworkNode node=new NetworkNode();
		    	node.setId(rs.getString("ID"));
		    	node.setName(rs.getString("NAME"));
		    	
		    	node.setWidth(rs.getFloat("WIDTH"));
		    	node.setHeight(rs.getFloat("HEIGHT"));
		    	
		    	node.setShape(rs.getString("SHAPE"));
		    	node.setShapePolygonPoints(rs.getString("SHAPE_POLYGON_POINTS"));
		    	node.setBackgroundColor(rs.getString("BACKGROUND_COLOR"));
		    	
		    	node.setBackgroundBlacken(rs.getString("BACKGROUND_BLACKEN"));
		    	node.setBackgroundOpacity(rs.getString("BACKGROUND_OPACITY"));
		    	node.setBorderWidth(rs.getString("BORDER_WIDTH"));
		    	
		    	node.setBorderStyle(rs.getString("BORDER_STYLE"));
		    	node.setBorderColor(rs.getString("BORDER_COLOR"));
		    	node.setBorderOpacity(rs.getString("BORDER_OPACITY"));
		    	
		    	node.setPositionX(rs.getFloat("POSITION_X"));
		    	node.setPositionY(rs.getFloat("POSITION_Y"));
		    	nodes.add(node);
		    }
		}finally{
			ConnectionPool.closeResources(rs, connection, pst);
		}
		return nodes;
	}

	@Override
	public List<NetworkEdge> getAllNetworkEdges() throws SQLException {
		Connection connection=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List<NetworkEdge> edges=new ArrayList<NetworkEdge>();
		try{
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("SELECT ID,SOURCE,TARGET,WIDTH,CURVE_STYLE,LINE_COLOR,LINE_STYLE FROM NETWORK_EDGE ORDER BY ID");
		    rs=pst.executeQuery();
		    while(rs.next()){
		    	NetworkEdge edge=new NetworkEdge();
		    	edge.setId(rs.getString("ID"));
		    	edge.setSource(rs.getString("SOURCE"));
		    	edge.setTarget(rs.getString("TARGET"));
		    	
		    	edge.setWidth(rs.getFloat("WIDTH"));
		    	edge.setCurveStyle(rs.getString("CURVE_STYLE"));
		    	edge.setLineColor(rs.getString("LINE_COLOR"));
		    	edge.setLineStyle(rs.getString("LINE_STYLE"));
		    	
		    	edges.add(edge);
		    }
		}finally{
			ConnectionPool.closeResources(rs, connection, pst);
		}
		return edges;
	}

	@Override
	public String saveNetworkNode(NetworkNode node, Connection connection) throws SQLException {
		PreparedStatement pst=null;
		try{
			pst=connection.prepareStatement("INSERT INTO NETWORK_NODE(ID,NAME,SHAPE,BACKGROUND_COLOR) VALUES(?,?,?,?)");
			pst.setString(1, node.getId());
			pst.setString(2, node.getName());
			pst.setString(3, node.getShape());
			pst.setString(4, node.getBackgroundColor());
			pst.execute();
		}finally{
			ConnectionPool.closeResources(null, null, pst);
		}
		return null;
	}

	@Override
	public String saveNetworkEdge(List<NetworkEdge> edges, Connection connection) throws SQLException {
		if(edges==null||edges.size()==0){
			return null;
		}
		PreparedStatement pst=null;
		try{
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("INSERT INTO NETWORK_EDGE(ID,LINE_STYLE,WIDTH,SOURCE,TARGET,CURVE_STYLE,LINE_COLOR) VALUES(?,?,?,?,?,?,?)");
			for (NetworkEdge edge : edges) {
				pst.setString(1, edge.getId());
				pst.setString(2, edge.getLineStyle());
				pst.setFloat(3, edge.getWidth());
				pst.setString(4, edge.getSource());
				pst.setString(5, edge.getTarget());
				pst.setString(6, edge.getCurveStyle());
				pst.setString(7, edge.getLineColor());
				pst.addBatch();
			}
			pst.executeBatch();
		}finally{
			ConnectionPool.closeResources(null, null, pst);
		}
		return null;
	}

	@Override
	public NetworkNode getNetworkNodeById(String nodeId) throws SQLException {
		Connection connection=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		NetworkNode node=new NetworkNode();
		try{
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("SELECT ID,NAME,WIDTH,HEIGHT,SHAPE,SHAPE_POLYGON_POINTS,BACKGROUND_COLOR,BACKGROUND_BLACKEN,BACKGROUND_OPACITY,BORDER_WIDTH,BORDER_STYLE,BORDER_COLOR,BORDER_OPACITY,POSITION_X,POSITION_Y FROM NETWORK_NODE WHERE ID=?");
		    pst.setString(1, nodeId);
			rs=pst.executeQuery();
		    if(rs.next()){
		    	node.setId(rs.getString("ID"));
		    	node.setName(rs.getString("NAME"));
		    	
		    	node.setWidth(rs.getFloat("WIDTH"));
		    	node.setHeight(rs.getFloat("HEIGHT"));
		    	
		    	node.setShape(rs.getString("SHAPE"));
		    	node.setShapePolygonPoints(rs.getString("SHAPE_POLYGON_POINTS"));
		    	node.setBackgroundColor(rs.getString("BACKGROUND_COLOR"));
		    	
		    	node.setBackgroundBlacken(rs.getString("BACKGROUND_BLACKEN"));
		    	node.setBackgroundOpacity(rs.getString("BACKGROUND_OPACITY"));
		    	node.setBorderWidth(rs.getString("BORDER_WIDTH"));
		    	
		    	node.setBorderStyle(rs.getString("BORDER_STYLE"));
		    	node.setBorderColor(rs.getString("BORDER_COLOR"));
		    	node.setBorderOpacity(rs.getString("BORDER_OPACITY"));
		    	
		    	node.setPositionX(rs.getFloat("POSITION_X"));
		    	node.setPositionY(rs.getFloat("POSITION_Y"));
		    }
		}finally{
			ConnectionPool.closeResources(rs, connection, pst);
		}
		return node;
	}

	@Override
	public List<NetworkEdge> getNetworkEdgesByNodeId(String nodeId) throws SQLException {
		Connection connection=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		List<NetworkEdge> edges=new ArrayList<NetworkEdge>();
		try{
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("SELECT ID,SOURCE,TARGET,WIDTH,CURVE_STYLE,LINE_COLOR,LINE_STYLE FROM NETWORK_EDGE WHERE SOURCE=? ORDER BY ID");
		    pst.setString(1, nodeId);
			rs=pst.executeQuery();
		    while(rs.next()){
		    	NetworkEdge edge=new NetworkEdge();
		    	edge.setId(rs.getString("ID"));
		    	edge.setSource(rs.getString("SOURCE"));
		    	edge.setTarget(rs.getString("TARGET"));
		    	
		    	edge.setWidth(rs.getFloat("WIDTH"));
		    	edge.setCurveStyle(rs.getString("CURVE_STYLE"));
		    	edge.setLineColor(rs.getString("LINE_COLOR"));
		    	edge.setLineStyle(rs.getString("LINE_STYLE"));
		    	
		    	edges.add(edge);
		    }
		}finally{
			ConnectionPool.closeResources(rs, connection, pst);
		}
		return edges;
	}

	@Override
	public void updateNetworkNode(NetworkNode node, Connection connection) throws SQLException {
		PreparedStatement pst=null;
		try{
			pst=connection.prepareStatement("UPDATE NETWORK_NODE SET NAME=?,SHAPE=?,BACKGROUND_COLOR=? WHERE ID=?");
		    pst.setString(1, node.getName());
		    pst.setString(2, node.getShape());
		    pst.setString(3, node.getBackgroundColor());
		    pst.setString(4, node.getId());
		    pst.execute();
		}finally{
			ConnectionPool.closeResources(null, null, pst);
		}
	}

	@Override
	public void removeNetworkEdgeByNodeId(String id, Connection connection) throws SQLException {
		PreparedStatement pst=null;
		try{
			pst=connection.prepareStatement("DELETE FROM NETWORK_EDGE WHERE SOURCE=?");
		    pst.setString(1, id);
		    pst.execute();
		}finally{
			ConnectionPool.closeResources(null, null, pst);
		}
	}

	@Override
	public void deleteNetworkNode(String nodeId,Connection connection) throws SQLException {
		PreparedStatement pst=null;
		try{
			pst=connection.prepareStatement("DELETE FROM NETWORK_NODE WHERE ID=?");
		    pst.setString(1, nodeId);
		    pst.execute();
		}finally{
			ConnectionPool.closeResources(null, null, pst);
		}
	}

	@Override
	public void deleteNetworkEdges(String nodeId,Connection connection) throws SQLException {
		PreparedStatement pst=null;
		try{
			pst=connection.prepareStatement("DELETE FROM NETWORK_EDGE WHERE SOURCE=? OR TARGET=?");
		    pst.setString(1, nodeId);
		    pst.setString(2, nodeId);
		    pst.execute();
		}finally{
			ConnectionPool.closeResources(null, null, pst);
		}
	}

	@Override
	public void updatePosition(NetworkNode node) throws SQLException {
		PreparedStatement pst=null;
        Connection connection=null;
		try{
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("UPDATE NETWORK_NODE SET POSITION_X=?,POSITION_Y=? WHERE ID=?");
		    pst.setFloat(1, node.getPositionX());
		    pst.setFloat(2, node.getPositionY());
		    pst.setString(3, node.getId());
		    pst.execute();
		}finally{
			ConnectionPool.closeResources(null, connection, pst);
		}
	}

}
