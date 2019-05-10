package com.marklynch.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

		if (angle < 0) {
			angle += 6.28319f;
		} else if (angle > 6.28319f) {
			angle -= 6.28319f;
		}

		return angle;
	}

	public static class Point {
		public float x, y;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public Point(double x, double y) {
			this.x = (float) x;
			this.y = (float) y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

	public static class Quad {
		public float x1, y1, x2, y2;

		public Quad(float x1, float y1, float x2, float y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		public Quad(double x1, double y1, double x2, double y2) {
			this.x1 = (float) x1;
			this.y1 = (float) y1;
			this.x2 = (float) x2;
			this.y2 = (float) y2;
		}

		@Override
		public String toString() {
			return "Point [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
		}
	}

	public static <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<T>(set);
	}

	public static <T> ArrayList<T> intersection(ArrayList<T> list1, ArrayList<T> list2) {
		ArrayList<T> list = new ArrayList<T>();

		for (T t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

}
