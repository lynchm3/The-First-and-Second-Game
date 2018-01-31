package com.marklynch.utils;

import com.marklynch.utils.Color;

public class StringWithColor {

	public String string;
	public Color color;

	public StringWithColor(String string, Color color) {
		super();
		this.string = string;
		this.color = color;
	}

	public StringWithColor makeCopy() {
		return new StringWithColor(new String(string), new Color(color.r, color.g, color.b, color.a));
	}
}
