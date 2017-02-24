package com.marklynch.level.constructs.building;

public class BuildingRoom {
	public String name;
	public int gridX1, gridY1, gridX2, gridY2;

	public BuildingRoom(String name, int gridX1, int gridY1, int gridX2, int gridY2) {
		super();
		this.name = name;
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;
	}
}
