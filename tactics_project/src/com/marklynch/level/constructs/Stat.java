package com.marklynch.level.constructs;

public class Stat {

	public String name;
	public float value;

	public Stat(String name, float value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Stat makeCopy() {
		return new Stat(name, value);
	}

}
