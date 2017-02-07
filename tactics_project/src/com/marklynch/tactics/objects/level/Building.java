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

		// create walls where theres no doors
		for (int x = gridX1; x <= gridX2; x++) {
			if (!doorLocations.contains(Game.level.squares[x][gridY1]))
				new Wall("Stone Wall", 1000, "wall.png", Game.level.squares[x][gridY1], new Inventory(), false, false,
						false, false);

			if (!doorLocations.contains(Game.level.squares[x][gridY2]))
				new Wall("Stone Wall", 1000, "wall.png", Game.level.squares[x][gridY2], new Inventory(), false, false,
						false, false);
		}
		for (int y = gridY1; y <= gridY2; y++) {
			if (!doorLocations.contains(Game.level.squares[gridX1][y]))
				new Wall("Stone Wall", 1000, "wall.png", Game.level.squares[gridX1][y], new Inventory(), false, false,
						false, false);

			if (!doorLocations.contains(Game.level.squares[gridX2][y]))
				new Wall("Stone Wall", 1000, "wall.png", Game.level.squares[gridX2][y], new Inventory(), false, false,
						false, false);
		}

		for (int i = gridX1 + 1; i <= gridX2 - 1; i++) {
			for (int j = gridY1 + 1; j <= gridY2 - 1; j++) {
				new Roof("Roof", 1000, "roof.png", Game.level.squares[i][j], new Inventory(), false, true, false,
						false);
			}
		}

		for (int i = gridX1; i <= gridX2; i++) {
			for (int j = gridY1; j <= gridY2; j++) {
				Game.level.squares[i][j].building = this;
			}
		}

	}

	// Wallsnew Wall("Stone Wall",1000,"wall.png",Game.level.squares[6][0],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[7][0],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[8][0],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[9][0],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[9][1],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[9][2],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[9][3],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[9][4],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[9][5],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[8][5],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[7][5],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[6][5],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[5][5],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[5][4],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[5][2],new
	// Inventory(),false,false,false,false);new Wall("Stone
	// Wall",1000,"wall.png",Game.level.squares[5][1],new
	// Inventory(),false,false,false,false);
}