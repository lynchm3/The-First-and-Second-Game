package com.marklynch.level.constructs.cave;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Structure;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Wall;

public class Cave extends Structure {

	public Cave(String name, ArrayList<CaveSection> caveSections, ArrayList<CaveAtrium> caveAtriums,
			ArrayList<Square> emptySquares) {
		super();

		this.name = name;

		for (CaveAtrium caveAtrium : caveAtriums) {
			for (int i = caveAtrium.gridX1; i <= caveAtrium.gridX2; i++) {
				for (int j = caveAtrium.gridY1; j <= caveAtrium.gridY2; j++) {
					emptySquares.add(Game.level.squares[i][j]);
				}
			}
		}

		ArrayList<Wall> wallsInCave = new ArrayList<Wall>();
		for (CaveSection caveSection : caveSections) {
			for (int i = caveSection.gridX1; i <= caveSection.gridX2; i++) {
				for (int j = caveSection.gridY1; j <= caveSection.gridY2; j++) {
					if (!emptySquares.contains(Game.level.squares[i][j])
							&& !Game.level.squares[i][j].inventory.contains(Wall.class)) {
						wallsInCave.add(new Wall("Cave Wall", 1000, "wall.png", Game.level.squares[i][j],
								new Inventory(), false, false, false, false, 1, 1));
					}
					Game.level.squares[i][j].structureSquareIsIn = this;
					Game.level.squares[i][j].imageTexturePath = "stone.png";
					Game.level.squares[i][j].loadImages();
				}
			}
		}

		// Do bits, each bit represents a possibility
		for (Wall wall : wallsInCave) {
			// Top
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid - 1].inventory
					.contains(Wall.class)) {
				wall.connectedTop = true;
			}
			// Top Right
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid
					- 1].inventory.contains(Wall.class)) {
				wall.connectedTopRight = true;
			}
			// Right
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid].inventory
					.contains(Wall.class)) {
				wall.connectedRight = true;
			}
			// Bottom Right
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid
					+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottomRight = true;
			}
			// Bottom
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid + 1].inventory
					.contains(Wall.class)) {
				wall.connectedBottom = true;
			}
			// BOttom left
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
					+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottomLeft = true;
			}
			// LEft
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid].inventory
					.contains(Wall.class)) {
				wall.connectedLeft = true;
			}
			// Top left
			if (Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
					- 1].inventory.contains(Wall.class)) {
				wall.connectedTopLeft = true;
			}
		}

	}
}