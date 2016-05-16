package com.cytoscape.domain;

import java.io.Serializable;

/**
 * 网络图连接线信息
 * @author ganliang
 */
public class NetworkEdge implements Serializable{

	private static final long serialVersionUID = 6788061619334055894L;

	private String id;//主键id
	
	private String source;//源节点id
	private String target;//目标节点id
	private float width ;//连接线宽度
	
	private String curveStyle;
	private String lineColor;//连接线的颜色
	private String lineStyle;//连接线的样式 solid, dotted, or dashed.
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public String getCurveStyle() {
		return curveStyle;
	}
	public void setCurveStyle(String curveStyle) {
		this.curveStyle = curveStyle;
	}
	public String getLineColor() {
		return lineColor;
	}
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}
	public String getLineStyle() {
		return lineStyle;
	}
	public void setLineStyle(String lineStyle) {
		this.lineStyle = lineStyle;
	}
}
