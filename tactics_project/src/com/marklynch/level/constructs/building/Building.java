package com.marklynch.level.constructs.building;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.level.constructs.Structure;

public class Building extends Structure {

	public Building(String name, int gridX1, int gridY1, int gridX2, int gridY2, ArrayList<Square> doorLocations) {
		super();
		// this.name = name;
		// this.gridX1 = gridX1;
		// this.gridY1 = gridY1;
		// this.gridX2 = gridX2;
		// this.gridY2 = gridY2;
		//
		// String wallName = name + " Wall";
		// String roofName = name + " Roof";
		//
		// // create door
		// for (Square doorLocation : doorLocations) {
		//
		// new Door("Wooden Door", 100, "door.png", doorLocation, new
		// Inventory(), false, true, false, false, 1, 1);
		//
		// }
		//
		// // Top Left corner Wall
		// if (!doorLocations.contains(Game.level.squares[gridX1][gridY1]))
		// new Wall(wallName, 1000, "wall_top_left.png",
		// Game.level.squares[gridX1][gridY1], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// // Top Left corner Roof
		// new Roof(roofName, 1000, "roof_top_left.png",
		// Game.level.squares[gridX1][gridY1], new Inventory(), false, true,
		// false, false, 1, 1);
		//
		// // Bottom left corner wall
		// if (!doorLocations.contains(Game.level.squares[gridX1][gridY2]))
		// new Wall(wallName, 1000, "wall_bottom_left.png",
		// Game.level.squares[gridX1][gridY2], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// // Bottom left corner Roof
		// new Roof(roofName, 1000, "roof_bottom_left.png",
		// Game.level.squares[gridX1][gridY2], new Inventory(), false,
		// true, false, false, 1, 1);
		//
		// // Top right corner wall
		// if (!doorLocations.contains(Game.level.squares[gridX2][gridY1]))
		// new Wall(wallName, 1000, "wall_top_right.png",
		// Game.level.squares[gridX2][gridY1], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// // Top right corner Roof
		// new Roof(roofName, 1000, "roof_top_right.png",
		// Game.level.squares[gridX2][gridY1], new Inventory(), false, true,
		// false, false, 1, 1);
		//
		// // Bottom right corner wall
		// if (!doorLocations.contains(Game.level.squares[gridX2][gridY2]))
		// new Wall(wallName, 1000, "wall_bottom_right.png",
		// Game.level.squares[gridX2][gridY2], new Inventory(),
		// false, false, false, false, 1, 1);
		//
		// // Bottom right corner Roof
		// new Roof(roofName, 1000, "roof_bottom_right.png",
		// Game.level.squares[gridX2][gridY2], new Inventory(), false,
		// true, false, false, 1, 1);
		//
		// // horizontal wall
		// for (int x = gridX1 + 1; x < gridX2; x++) {
		// if (!doorLocations.contains(Game.level.squares[x][gridY1]))
		// new Wall(wallName, 1000, "wall_horizontal.png",
		// Game.level.squares[x][gridY1], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// if (!doorLocations.contains(Game.level.squares[x][gridY2]))
		// new Wall(wallName, 1000, "wall_horizontal.png",
		// Game.level.squares[x][gridY2], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// // top roof
		// new Roof(roofName, 1000, "roof_top.png",
		// Game.level.squares[x][gridY1], new Inventory(), false, true, false,
		// false, 1, 1);
		//
		// // bottom roof
		// new Roof(roofName, 1000, "roof_bottom.png",
		// Game.level.squares[x][gridY2], new Inventory(), false, true,
		// false, false, 1, 1);
		// }
		// // vertical wall
		// for (int y = gridY1 + 1; y < gridY2; y++) {
		// if (!doorLocations.contains(Game.level.squares[gridX1][y]))
		// new Wall(wallName, 1000, "wall_vertical.png",
		// Game.level.squares[gridX1][y], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// if (!doorLocations.contains(Game.level.squares[gridX2][y]))
		// new Wall(wallName, 1000, "wall_vertical.png",
		// Game.level.squares[gridX2][y], new Inventory(), false,
		// false, false, false, 1, 1);
		//
		// // left roof
		// new Roof(roofName, 1000, "roof_left.png",
		// Game.level.squares[gridX1][y], new Inventory(), false, true,
		// false, false, 1, 1);
		//
		// // right roof
		// new Roof(roofName, 1000, "roof_right.png",
		// Game.level.squares[gridX2][y], new Inventory(), false, true,
		// false, false, 1, 1);
		//
		// }
		//
		// // inner roof parts
		// for (int i = gridX1 + 1; i <= gridX2 - 1; i++) {
		// for (int j = gridY1 + 1; j <= gridY2 - 1; j++) {
		// new Roof(roofName, 1000, "roof.png", Game.level.squares[i][j], new
		// Inventory(), false, true, false,
		// false, 1, 1);
		// }
		// }
		//
		// // Tell the squares what building they're in and give them a stone
		// // texture for now
		// for (int i = gridX1; i <= gridX2; i++) {
		// for (int j = gridY1; j <= gridY2; j++) {
		// Game.level.squares[i][j].structureSquareIsIn = this;
		// Game.level.squares[i][j].imageTexturePath = "stone.png";
		// Game.level.squares[i][j].loadImages();
		// }
		// }

	}
}