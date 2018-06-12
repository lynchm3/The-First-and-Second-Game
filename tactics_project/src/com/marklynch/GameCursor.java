package com.marklynch;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.utils.Texture;

public class GameCursor {

	public transient Texture imageTexture = null;
	public transient Texture imageTexture2 = null;
	public transient Texture imageTexture3 = null;
	public transient Texture imageTexture4 = null;
	public static transient Texture pathDot = null;
	public static transient Texture endPathDot = null;
	public transient Texture cursor = null;
	public static transient Texture circle = null;
	public static transient Texture circleBlack = null;
	public static transient Texture circleEdge = null;
	public static transient Texture circleEdgeRed = null;
	public static transient Texture circleEdgeGreen = null;
	public static transient Texture circleMouseFishing = null;

	public transient Texture redArrow = null;
	public transient Texture yellowArrow = null;
	public transient Texture blueArrow = null;
	public transient Texture greenArrow = null;

	public GameCursor() {
		this.imageTexture = getGlobalImage("highlight.png", false);
		this.imageTexture2 = getGlobalImage("highlight2.png", false);
		this.imageTexture3 = getGlobalImage("highlight3.png", false);
		this.imageTexture4 = getGlobalImage("highlight4.png", false);
		this.pathDot = getGlobalImage("path_dot.png", false);
		this.endPathDot = getGlobalImage("end_path_dot.png", false);
		this.cursor = getGlobalImage("cursor.png", false);
		this.circle = getGlobalImage("circle.png", false);
		this.circleBlack = getGlobalImage("circle_black.png", false);
		this.circleEdge = getGlobalImage("circle_edge.png", false);
		this.circleEdgeRed = getGlobalImage("circle_edge_red.png", false);
		this.circleMouseFishing = getGlobalImage("fishing_mouse_circle.png", false);
		circleEdgeGreen = getGlobalImage("circle_edge_green.png", false);
		this.redArrow = getGlobalImage("red_arrow.png", false);
		this.yellowArrow = getGlobalImage("yellow_arrow.png", false);
		this.blueArrow = getGlobalImage("blue_arrow.png", false);
		this.greenArrow = getGlobalImage("green_arrow.png", false);
	}
}
