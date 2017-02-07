package com.marklynch.tactics.objects.level;

public class Building {

	String name;
	public int gridX1, gridY1;
	public int gridX2;
	public int gridY2;

	public Building(String name, int gridX1, int gridY1, int gridX2, int gridY2) {
		super();
		this.name = name;
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;
	}
}
