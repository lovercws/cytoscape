package com.cytoscape.domain;

import java.io.Serializable;

/**
 * 网络图节点信息
 * @author ganliang
 */
public class NetworkNode implements Serializable{

	private static final long serialVersionUID = 6788061619334055894L;

	private String id;//节点主键
	private String name;//节点名称
	
	// shape
	private float width;//The width of the node's body
	private float height;//The height of the node's body
	private String shape ;//The shape of the node's body 
	                      //may be rectangle, roundrectangle, ellipse, triangle, pentagon, hexagon, heptagon, octagon, star, diamond, vee, rhomboid, or polygon
	private String shapePolygonPoints;//A space-separated list of numbers ranging on [-1, 1],
	
	// Background
	private String backgroundColor;//The colour of the node's body
	private String backgroundBlacken;//Blackens the node's body for values from 0 to 1
	private String backgroundOpacity;//The opacity level of the node's background colour
	
	// 边框
	private String borderWidth;//The size of the node's border
	private String borderStyle;//The style of the node's border; may be solid, dotted, dashed, or double
	private String borderColor;//The colour of the node's border
	private String borderOpacity;//The opacity of the node's border.
	
	//position
	private float positionX;//节点横坐标位置
	private float positionY;//节点纵坐标位置
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getShapePolygonPoints() {
		return shapePolygonPoints;
	}
	public void setShapePolygonPoints(String shapePolygonPoints) {
		this.shapePolygonPoints = shapePolygonPoints;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBackgroundBlacken() {
		return backgroundBlacken;
	}
	public void setBackgroundBlacken(String backgroundBlacken) {
		this.backgroundBlacken = backgroundBlacken;
	}
	public String getBackgroundOpacity() {
		return backgroundOpacity;
	}
	public void setBackgroundOpacity(String backgroundOpacity) {
		this.backgroundOpacity = backgroundOpacity;
	}
	public String getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(String borderWidth) {
		this.borderWidth = borderWidth;
	}
	public String getBorderStyle() {
		return borderStyle;
	}
	public void setBorderStyle(String borderStyle) {
		this.borderStyle = borderStyle;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public String getBorderOpacity() {
		return borderOpacity;
	}
	public void setBorderOpacity(String borderOpacity) {
		this.borderOpacity = borderOpacity;
	}
	public float getPositionX() {
		return positionX;
	}
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}
	public float getPositionY() {
		return positionY;
	}
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
}
