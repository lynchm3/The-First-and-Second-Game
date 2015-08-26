package com.marklynch.utils;

import mdesl.graphics.Color;

public class StringWithColor {

	String string;
	Color color;

	public StringWithColor(String string, Color color) {
		super();
		this.string = string;
		this.color = color;
	}

	public StringWithColor makeCopy() {
		return new StringWithColor(new String(string), new Color(color.r,
				color.g, color.b, color.a));
	}
}
