package com.cytoscape.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cytoscape.service.NetWorkGraphService;
import com.cytoscape.service.impl.NetWorkGraphServiceImpl;

@WebServlet("/NetworkNodeServlet")
public class NetworkNodeServlet extends HttpServlet {

	private static final long serialVersionUID = 7476016961622096577L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		NetWorkGraphService graphService = new NetWorkGraphServiceImpl();
		String json = "";
		if (type == null) {
			throw new IllegalArgumentException("请求类型不能为空");
		}  
		switch (type) {
		// 获取所有的节点
		case "getTreeNodes":
			json = graphService.getTreeNodes();
			break;
		// 获取所有的节点
		case "getCheckboxNodes":
			String nodeId = request.getParameter("value");
			json = graphService.getCheckboxNodes(nodeId);
			break;
		// 获取节点的详细信息
		case "getDetailNode":
			// 节点id
			nodeId = request.getParameter("value");
			json=graphService.getDetailNode(nodeId);
			break;
		// 保存节点和连接的信息
		case "save":
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String shape=request.getParameter("shape");
			String backgroundColor=request.getParameter("backgroundColor");
			
			String lineStyle=request.getParameter("lineStyle");
			String lineColor=request.getParameter("lineColor");
			String direction=request.getParameter("direction");
			String curveStyle=request.getParameter("curveStyle");
			Map<String, String[]> parameterMap = request.getParameterMap();
			String[] parentNodes = parameterMap.get("parentNode");
			String width=request.getParameter("width");
			
			//将参数封装到map中去
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("id", id);
			paramMap.put("name", name);
			paramMap.put("shape", shape);
			paramMap.put("backgroundColor", backgroundColor);
			paramMap.put("lineStyle", lineStyle);
			paramMap.put("lineColor", lineColor);
			paramMap.put("direction", direction);
			paramMap.put("curveStyle", curveStyle);
			paramMap.put("parentNode", parentNodes);
			paramMap.put("width", width);
			json=graphService.save(paramMap);
			break;
		//删除节点
		case "delete":
			nodeId=request.getParameter("value");//获取节点id
			json=graphService.delete(nodeId);
			break;
		default:
			throw new IllegalArgumentException("请求类型非法");
		}
		response.getWriter().print(json);
	}
}
