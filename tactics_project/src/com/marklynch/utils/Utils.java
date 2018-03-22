package com.marklynch.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static void printStackTrace() {
		for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
			System.out.println("" + s);
		}
	}

	public static List<Point> getCircleLineIntersectionPoint2(Point pointA, Point pointB, Point center, float radius) {

		List<Point> points = new ArrayList<Point>();
		float offX = pointA.x - pointB.x;
		float offY = pointA.y - pointB.y;
		float ls = offX * offX + offY * offY;

		float scale = radius / (float) Math.sqrt(ls);

		float resX = offX * scale + pointB.x;
		float resY = offY * scale + pointB.y;

		points.add(new Point(resX, resY));

		return points;
	}

	public static float radianAngleFromLine(Point pointA, Point pointB) {
		float angle = (float) (Math.atan2(pointA.y - pointB.y, pointA.x - pointB.x));

		if (angle < 6.28319) {
			angle += 6.28319;
		}

		return angle;
	}

	public static class Point {
		public float x, y;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

}
