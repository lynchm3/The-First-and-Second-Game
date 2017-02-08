package com.marklynch.tactics.objects.level;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.tactics.objects.Door;
import com.marklynch.tactics.objects.Inventory;
import com.marklynch.tactics.objects.Roof;
import com.marklynch.tactics.objects.Wall;

public class Building {

	String name;
	public int gridX1, gridY1, gridX2, gridY2;

	public Building(String name, int gridX1, int gridY1, int gridX2, int gridY2, ArrayList<Square> doorLocations) {
		super();
		this.name = name;
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;

		// create door
		for (Square doorLocation : doorLocations) {

			new Door("Wooden Door", 100, "door.png", doorLocation, new Inventory(), false, true, false, false);

		}

		// Top Left corner Wall
		if (!doorLocations.contains(Game.level.squares[gridX1][gridY1]))
			new Wall("Stone Wall", 1000, "wall_top_left.png", Game.level.squares[gridX1][gridY1], new Inventory(),
					false, false, false, false);

		// Top Left corner Roof
		new Roof("Roof", 1000, "roof_top_left.png", Game.level.squares[gridX1][gridY1], new Inventory(), false, true,
				false, false);

		// Bottom left corner wall
		if (!doorLocations.contains(Game.level.squares[gridX1][gridY2]))
			new Wall("Stone Wall", 1000, "wall_bottom_left.png", Game.level.squares[gridX1][gridY2], new Inventory(),
					false, false, false, false);

		// Bottom left corner Roof
		new Roof("Roof", 1000, "roof_bottom_left.png", Game.level.squares[gridX1][gridY2], new Inventory(), false, true,
				false, false);

		// Top right corner wall
		if (!doorLocations.contains(Game.level.squares[gridX2][gridY1]))
			new Wall("Stone Wall", 1000, "wall_top_right.png", Game.level.squares[gridX2][gridY1], new Inventory(),
					false, false, false, false);

		// Top right corner Roof
		new Roof("Roof", 1000, "roof_top_right.png", Game.level.squares[gridX2][gridY1], new Inventory(), false, true,
				false, false);

		// Bottom right corner wall
		if (!doorLocations.contains(Game.level.squares[gridX2][gridY2]))
			new Wall("Stone Wall", 1000, "wall_bottom_right.png", Game.level.squares[gridX2][gridY2], new Inventory(),
					false, false, false, false);

		// Bottom right corner Roof
		new Roof("Roof", 1000, "roof_bottom_right.png", Game.level.squares[gridX2][gridY2], new Inventory(), false,
				true, false, false);

		// horizontal wall
		for (int x = gridX1 + 1; x < gridX2; x++) {
			if (!doorLocations.contains(Game.level.squares[x][gridY1]))
				new Wall("Stone Wall", 1000, "wall_horizontal.png", Game.level.squares[x][gridY1], new Inventory(),
						false, false, false, false);

			if (!doorLocations.contains(Game.level.squares[x][gridY2]))
				new Wall("Stone Wall", 1000, "wall_horizontal.png", Game.level.squares[x][gridY2], new Inventory(),
						false, false, false, false);

			// top roof
			new Roof("Roof", 1000, "roof_top.png", Game.level.squares[x][gridY1], new Inventory(), false, true, false,
					false);

			// bottom roof
			new Roof("Roof", 1000, "roof_bottom.png", Game.level.squares[x][gridY2], new Inventory(), false, true,
					false, false);
		}
		// vertical wall
		for (int y = gridY1 + 1; y < gridY2; y++) {
			if (!doorLocations.contains(Game.level.squares[gridX1][y]))
				new Wall("Stone Wall", 1000, "wall_vertical.png", Game.level.squares[gridX1][y], new Inventory(), false,
						false, false, false);

			if (!doorLocations.contains(Game.level.squares[gridX2][y]))
				new Wall("Stone Wall", 1000, "wall_vertical.png", Game.level.squares[gridX2][y], new Inventory(), false,
						false, false, false);

			// left roof
			new Roof("Roof", 1000, "roof_left.png", Game.level.squares[gridX1][y], new Inventory(), false, true, false,
					false);

			// right roof
			new Roof("Roof", 1000, "roof_right.png", Game.level.squares[gridX2][y], new Inventory(), false, true, false,
					false);

		}

		// inner roof parts
		for (int i = gridX1 + 1; i <= gridX2 - 1; i++) {
			for (int j = gridY1 + 1; j <= gridY2 - 1; j++) {
				new Roof("Roof", 1000, "roof.png", Game.level.squares[i][j], new Inventory(), false, true, false,
						false);
			}
		}

		// Tell the squares what building they're in and give them a stone
		// texture for now
		for (int i = gridX1; i <= gridX2; i++) {
			for (int j = gridY1; j <= gridY2; j++) {
				Game.level.squares[i][j].building = this;
				Game.level.squares[i][j].imageTexturePath = "stone.png";
				Game.level.squares[i][j].loadImages();
			}
		}

	}
}