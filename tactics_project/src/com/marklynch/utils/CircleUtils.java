package com.marklynch.utils;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

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

	public static void drawCircleWithinBounds(Color color, double radius, double centerX, double centerY,

			ArrayList<Square> squares) {

		GL11.glLineWidth(10f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i = 0; i < 360; i++) {
			double degInRad = Math.toRadians(i);

			// Check x
			double xToDrawNow = Math.cos(degInRad) * radius + centerX;
			double yToDrawNow = Math.sin(degInRad) * radius + centerY;
			for (Square square : squares) {
				double boundsX1 = square.xInGridPixels;
				double boundsX2 = square.xInGridPixels + Game.SQUARE_WIDTH;
				double boundsY1 = square.yInGridPixels;
				double boundsY2 = square.yInGridPixels + Game.SQUARE_HEIGHT;
				if (xToDrawNow <= boundsX1)
					continue;
				if (xToDrawNow >= boundsX2)
					continue;
				if (yToDrawNow <= boundsY1)
					continue;
				if (yToDrawNow >= boundsY2)
					continue;
				GL11.glVertex2d(xToDrawNow, yToDrawNow);
				break;
			}
		}
		GL11.glEnd();

	}

	public static void drawCircleWithinBounds(Color color, double radius, double centerX, double centerY,

			double boundsX1, double boundsY1, double boundsX2, double boundsY2) {
		GL11.glLineWidth(10f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i = 0; i < 360; i++) {
			double degInRad = Math.toRadians(i);

			double xToDrawNow = Math.cos(degInRad) * radius + centerX;
			if (xToDrawNow < boundsX1)
				continue;
			if (xToDrawNow > boundsX2)
				continue;

			double yToDrawNow = Math.sin(degInRad) * radius + centerY;
			if (yToDrawNow < boundsY1)
				continue;
			if (yToDrawNow > boundsY2)
				continue;

			GL11.glVertex2d(xToDrawNow, yToDrawNow);
		}
		GL11.glEnd();
	}
}
