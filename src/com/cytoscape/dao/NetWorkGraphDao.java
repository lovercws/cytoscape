package com.cytoscape.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.cytoscape.domain.NetworkEdge;
import com.cytoscape.domain.NetworkNode;

public interface NetWorkGraphDao {

	/**
	 * 获取所有的节点
	 * @return
	 * @throws SQLException 
	 */
	public List<NetworkNode> getAllNetworkNodes() throws SQLException;
	
	/**
	 * 获取所有的连接线
	 * @return
	 * @throws SQLException 
	 */
	public List<NetworkEdge> getAllNetworkEdges() throws SQLException;
	
	/**
	 * 保存节点信息
	 * @param node 节点
	 * @param connection 数据库连接
	 * @return
	 * @throws SQLException 
	 */
	public String saveNetworkNode(NetworkNode node,Connection connection) throws SQLException;
	
	/**
	 * 保存节点信息
	 * @param edges 连接线
	 * @param connection 数据库连接
	 * @return
	 * @throws SQLException 
	 */
	public String saveNetworkEdge(List<NetworkEdge> edges,Connection connection) throws SQLException;

	/**
	 * 获取节点下的节点信息
	 * @param nodeId 节点id
	 * @return
	 * @throws SQLException 
	 */
	public NetworkNode getNetworkNodeById(String nodeId) throws SQLException;

	/**
	 * 获取节点下的连接信息
	 * @param nodeId 节点id
	 * @return
	 */
	public List<NetworkEdge> getNetworkEdgesByNodeId(String nodeId) throws SQLException;

	/**
	 * 更新节点信息
	 * @param node
	 * @param connection
	 * @throws SQLException 
	 */
	public void updateNetworkNode(NetworkNode node, Connection connection) throws SQLException;

	/**
	 * 删除节点的链接信息
	 * @param id
	 * @param connection
	 * @throws SQLException 
	 */
	public void removeNetworkEdgeByNodeId(String id, Connection connection) throws SQLException;

	/**
	 * 删除节点连接信息
	 * @param nodeId
	 * @throws SQLException 
	 */
	public void deleteNetworkNode(String nodeId,Connection connection) throws SQLException;

	/**
	 * 删除节点信息
	 * @param nodeId
	 * @param connection 
	 */
	public void deleteNetworkEdges(String nodeId, Connection connection) throws SQLException;

	/**
	 * 更新网络图节点坐标位置
	 * @param node
	 * @throws SQLException 
	 */
	public void updatePosition(NetworkNode node) throws SQLException;
	
}
