package com.marklynch.level.constructs.cave;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Structure;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Wall;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Cave extends Structure {

	ArrayList<CaveSection> caveSections;
	ArrayList<CaveAtrium> caveAtriums;
	Texture imageTexture;

	public Cave(String name, ArrayList<CaveSection> caveSections, ArrayList<CaveAtrium> caveAtriums,
			ArrayList<CavePath> paths, ArrayList<GameObject> features, String imageTexturePath) {
		super();

		this.name = name;
		this.caveSections = caveSections;
		this.caveAtriums = caveAtriums;
		this.imageTexture = ResourceUtils.getGlobalImage(imageTexturePath);
		ArrayList<Square> noWallSquares = new ArrayList<Square>();
		ArrayList<Square> wallSquares = new ArrayList<Square>();
		ArrayList<Square> featureSquares = new ArrayList<Square>();

		for (GameObject feature : features) {
			noWallSquares.add(feature.squareGameObjectIsOn);
			featureSquares.add(feature.squareGameObjectIsOn);
		}

		for (CavePath path : paths) {
			noWallSquares.addAll(path.squares);
		}

		for (CaveAtrium caveAtrium : caveAtriums) {
			for (int i = caveAtrium.gridX1; i <= caveAtrium.gridX2; i++) {
				for (int j = caveAtrium.gridY1; j <= caveAtrium.gridY2; j++) {
					noWallSquares.add(Game.level.squares[i][j]);
				}
			}
		}

		ArrayList<Wall> wallsInCave = new ArrayList<Wall>();
		for (CaveSection caveSection : caveSections) {
			for (int i = caveSection.gridX1; i <= caveSection.gridX2; i++) {
				for (int j = caveSection.gridY1; j <= caveSection.gridY2; j++) {
					if (!noWallSquares.contains(Game.level.squares[i][j])
							&& !Game.level.squares[i][j].inventory.contains(Wall.class)) {
						wallsInCave.add(new Wall("Cave Wall", 1000, "wall.png", Game.level.squares[i][j],
								new Inventory(), false, false, false, false, 1, 1));
						wallSquares.add(Game.level.squares[i][j]);
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
			if (wall.squareGameObjectIsOn.yInGrid > 0 && (wallSquares.contains(
					Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid - 1])
					|| featureSquares.contains(
							Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid
									- 1]))) {
				wall.connectedTop = true;
			}
			// Right
			if (wall.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid
							+ 1][wall.squareGameObjectIsOn.yInGrid].inventory.contains(Wall.class)) {
				wall.connectedRight = true;
			}
			// Bottom
			if (wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottom = true;
			}
			// LEft
			if (wall.squareGameObjectIsOn.xInGrid > 0 && Game.level.squares[wall.squareGameObjectIsOn.xInGrid
					- 1][wall.squareGameObjectIsOn.yInGrid].inventory.contains(Wall.class)) {
				wall.connectedLeft = true;
			}
			// Top Right
			if (wall.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1
					&& wall.squareGameObjectIsOn.yInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid
							- 1].inventory.contains(Wall.class)) {
				wall.connectedTopRight = true;
			} else if (wall.connectedTop && wall.connectedRight) {
				wall.connectedTopRight = true;
			}
			// Bottom Right
			if (wall.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1
					&& wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottomRight = true;
			} else if (wall.connectedBottom && wall.connectedRight) {
				wall.connectedBottomRight = true;
			}
			// BOttom left
			if (wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& wall.squareGameObjectIsOn.xInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottomLeft = true;
			} else if (wall.connectedBottom && wall.connectedLeft) {
				wall.connectedBottomLeft = true;
			}
			// Top left
			if (wall.squareGameObjectIsOn.xInGrid > 0 && wall.squareGameObjectIsOn.yInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
							- 1].inventory.contains(Wall.class)) {
				wall.connectedTopLeft = true;
			} else if (wall.connectedTop && wall.connectedLeft) {
				wall.connectedTopLeft = true;
			}
		}

	}

	float roofAlpha = 1f;

	@Override
	public void draw2() {
		if (imageTexture != null) {

			if (Game.level.player.squareGameObjectIsOn.structureSquareIsIn == this) {
				if (roofAlpha != 0f) {
					roofAlpha -= 0.01f;
					if (roofAlpha < 0f)
						roofAlpha = 0f;
				}
			} else if (roofAlpha != 1f) {
				roofAlpha += 0.01f;
				if (roofAlpha > 1f)
					roofAlpha = 1f;
			}
			TextureUtils.drawTexture(imageTexture, roofAlpha, 640, 640 + 1664, -32, -32 + 800);
		}

	}
}