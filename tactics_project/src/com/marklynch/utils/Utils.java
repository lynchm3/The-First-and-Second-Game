package com.marklynch.utils;

import java.util.concurrent.CopyOnWriteArrayList;
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

		List<Point> points = new CopyOnWriteArrayList<Point>();
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

		return new CopyOnWriteArrayList<T>(set);
	}

	public static <T> CopyOnWriteArrayList<T> intersection(CopyOnWriteArrayList<T> list1, CopyOnWriteArrayList<T> list2) {
		CopyOnWriteArrayList<T> list = new CopyOnWriteArrayList<T>();

		for (T t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public static Point rotatePoint(float cx, float cy, float angle, Point p) {
		double s = Math.sin(angle);
		double c = Math.cos(angle);

		// translate point back to origin:
		p.x -= cx;
		p.y -= cy;

		// rotate point
		double xnew = p.x * c - p.y * s;
		double ynew = p.x * s + p.y * c;

		// translate point back:
		p.x = (float) (xnew + cx);
		p.y = (float) (ynew + cy);
		return p;
	}

	public static Color getPixel(Texture texture, int x, int y) {

		if (texture == null || texture.pixels == null)
			return null;
		// in method
		if (x < 0 || y < 0)
			return null;

		if (x > texture.getWidth() - 1 || y > texture.getHeight() - 1) {
			return null;
		}

		int index = (x + y * texture.getWidth());
		if (index + 3 >= texture.pixels.length)
			return null;

		// int r = texture.pixels[index] & 0xFF;
		// int g = texture.pixels[index + 1] & 0xFF;
		// int b = texture.pixels[index + 2] & 0xFF;
		int a = texture.pixels[index + 3] & 0xFF;

		return new Color(0, 0, 0, a);
	}

}
