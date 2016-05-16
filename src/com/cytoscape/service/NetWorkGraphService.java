package com.cytoscape.service;

import java.util.Map;

public interface NetWorkGraphService {

	/**
	 * 获取节点树
	 * @return
	 */
	public String getTreeNodes();

	/**
	 * 保存节点和连接的信息
	 * @param paramMap 参数map
	 * @return
	 */
	public String save(Map<String, Object> paramMap);

	/**
	 * 获取网络图
	 * @return
	 */
	public String getNetworkGraph();

	/**
	 * 获取所有的节点
	 * @param nodeId 
	 * @return
	 */
	public String getCheckboxNodes(String nodeId);

	/**
	 * 获取节点下的节点信息和连接信息
	 * @param nodeId
	 * @return
	 */
	public String getDetailNode(String nodeId);

	/**
	 * 删除节点信息和连接信息
	 * @param nodeId
	 * @return
	 */
	public String delete(String nodeId);

	/**
	 * 更新网络图节点的位置
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String updatePosition(Map map);

}
