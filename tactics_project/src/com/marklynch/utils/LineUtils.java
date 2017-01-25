package com.marklynch.utils;

import mdesl.graphics.Color;

import org.lwjgl.opengl.GL11;

import com.marklynch.Game;

public class LineUtils {

	public static void drawLine(Color color, float x1, float y1, float x2, float y2, float lineWidth) {
		GL11.glLineWidth(lineWidth * Game.zoom);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}

}
