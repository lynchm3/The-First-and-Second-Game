package com.marklynch.utils;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class LineUtils {

	public static void drawLine(Color color, float x1, float y1, float x2,
			float y2) {
		GL11.glLineWidth(10f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}

}
