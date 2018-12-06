package com.marklynch.utils;

import java.util.List;

public class ArrayList<T> extends java.util.ArrayList<T> {

	public Class clazz;

	public ArrayList(Class clazz, List<T> list) {
		super(list);
		this.clazz = clazz;

	}

	public ArrayList(Class clazz) {
		this.clazz = clazz;
	}

}
