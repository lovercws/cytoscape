package com.cytoscape.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cytoscape.service.NetWorkGraphService;
import com.cytoscape.service.impl.NetWorkGraphServiceImpl;
import com.google.gson.Gson;

@WebServlet("/NetworkGraphServlet")
public class NetworkGraphServlet extends HttpServlet {

	private static final long serialVersionUID = -2628582593741489882L;

	@SuppressWarnings("rawtypes")
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
		//获取网络图
		case "getNetworkGraph":
			json=graphService.getNetworkGraph();
			break;
		//更新网络图节点的位置
		case "updatePosition":
			String value=request.getParameter("value");
			Gson gson=new Gson();
			Map map=gson.fromJson(value, Map.class);
			json=graphService.updatePosition(map);
			break;
		default:
			throw new IllegalArgumentException("请求类型非法");
		}
		response.getWriter().print(json);
	}
}
