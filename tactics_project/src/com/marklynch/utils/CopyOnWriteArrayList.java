package com.marklynch.utils;

import java.util.List;

public class CopyOnWriteArrayList<T> extends java.util.concurrent.CopyOnWriteArrayList<T> {

	public Class clazz;

	public CopyOnWriteArrayList(Class clazz, List<T> list) {
		super(list);
		this.clazz = clazz;

	}

	public CopyOnWriteArrayList(Class clazz) {
		this.clazz = clazz;
	}

}
