package com.marklynch.utils;

import org.lwjgl.opengl.GL11;

public class CircleUtils {

	public static void drawCircle(Color color, double radius, double x, double y) {
		GL11.glLineWidth(10f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i = 0; i < 360; i++) {
			double degInRad = Math.toRadians(i);
			GL11.glVertex2d(Math.cos(degInRad) * radius + x, Math.sin(degInRad) * radius + y);
		}
		GL11.glEnd();
	}
}
