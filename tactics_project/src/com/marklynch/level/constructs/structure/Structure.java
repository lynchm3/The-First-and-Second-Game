package com.marklynch.level.constructs.structure;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Structure {

	public String name;
	public ArrayList<StructureRoom> rooms;
	public boolean seenByPlayer = false;
	public ArrayList<StructureSection> caveSections;
	public ArrayList<Square> entranceSquares;
	public Texture imageTexture;
	float overlayX1, overlayX2, overlayY1, overlayY2;
	ArrayList<Square> floorSquares;
	ArrayList<Square> wallSquares;
	ArrayList<Square> featureSquares;
	boolean blocksLineOfSight;

	public Structure(String name, ArrayList<StructureSection> caveSections, ArrayList<StructureRoom> rooms,
			ArrayList<StructureHall> paths, ArrayList<GameObject> features, ArrayList<Square> entrances,
			String imageTexturePath, float overlayX1, float overlayX2, float overlayY1, float overlayY2,
			boolean blocksLineOfSight) {
		super();

		this.name = name;
		this.caveSections = caveSections;
		this.rooms = rooms;
		this.imageTexture = ResourceUtils.getGlobalImage(imageTexturePath);
		this.overlayX1 = overlayX1;
		this.overlayY1 = overlayY1;
		this.overlayX2 = overlayX2;
		this.overlayY2 = overlayY2;
		this.entranceSquares = entrances;
		this.blocksLineOfSight = blocksLineOfSight;
		floorSquares = new ArrayList<Square>();
		wallSquares = new ArrayList<Square>();
		featureSquares = new ArrayList<Square>();

		// Entrance squares
		for (Square entranceSquare : entranceSquares) {
			entranceSquare.imageTexturePath = "stone.png";
			entranceSquare.loadImages();
		}

		// Feature squares
		for (GameObject feature : features) {
			floorSquares.add(feature.squareGameObjectIsOn);
			featureSquares.add(feature.squareGameObjectIsOn);
		}

		// Floor squares
		for (StructureHall path : paths) {
			floorSquares.addAll(path.squares);
		}

		// Entrance Squares

		// Floor squares
		for (StructureRoom caveAtrium : rooms) {
			for (int i = caveAtrium.gridX1; i <= caveAtrium.gridX2; i++) {
				for (int j = caveAtrium.gridY1; j <= caveAtrium.gridY2; j++) {
					floorSquares.add(Game.level.squares[i][j]);
				}
			}
		}

		// Walls
		ArrayList<Wall> wallsInCave = new ArrayList<Wall>();
		for (StructureSection caveSection : caveSections) {
			for (int i = caveSection.gridX1; i <= caveSection.gridX2; i++) {
				for (int j = caveSection.gridY1; j <= caveSection.gridY2; j++) {
					if (!floorSquares.contains(Game.level.squares[i][j])
							&& !Game.level.squares[i][j].inventory.contains(Wall.class)) {
						wallsInCave.add(new Wall("Cave Wall", 1000, "wall.png", Game.level.squares[i][j],
								new Inventory(), false, false, false, false, blocksLineOfSight, true, 1, 1, 0.5f, 0.5f,
								20f, 0f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
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
			// Top 0, -1
			if (wall.squareGameObjectIsOn.yInGrid > 0 && (wallSquares.contains(
					Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid - 1])
					|| featureSquares.contains(
							Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid
									- 1]))) {
				wall.connectedTop = true;
			}
			// Right +1,0
			if (wall.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1 && (wallSquares.contains(
					Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid])
					|| featureSquares.contains(Game.level.squares[wall.squareGameObjectIsOn.xInGrid
							+ 1][wall.squareGameObjectIsOn.yInGrid]))) {
				wall.connectedRight = true;
			}
			// Bottom 0, +1
			if (wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1 && (wallSquares.contains(
					Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid + 1])
					|| featureSquares.contains(
							Game.level.squares[wall.squareGameObjectIsOn.xInGrid][wall.squareGameObjectIsOn.yInGrid
									+ 1]))) {
				wall.connectedBottom = true;
			}
			// LEft -1, 0
			if (wall.squareGameObjectIsOn.xInGrid > 0 && (wallSquares.contains(
					Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid])
					|| featureSquares.contains(Game.level.squares[wall.squareGameObjectIsOn.xInGrid
							- 1][wall.squareGameObjectIsOn.yInGrid]))) {
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

	public float roofAlpha = 1f;

	public void draw2() {
		if (!seenByPlayer)
			return;

		if (imageTexture != null) {

			if (Game.level.player.squareGameObjectIsOn.structureSquareIsIn == this
					|| entranceSquares.contains(Game.level.player.squareGameObjectIsOn)) {
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
			TextureUtils.drawTexture(imageTexture, roofAlpha, this.overlayX1, this.overlayX2, this.overlayY1,
					this.overlayY2);
		}

	}

	public void hasBeenSeenByPlayer() {
		this.seenByPlayer = true;
		new ActionSpot(Game.level.player, this).perform();
		for (Square square : this.entranceSquares) {
			square.seenByPlayer = true;
		}
		// for (Square square : floorSquares) {
		// square.seenByPlayer = true;
		// }
		// for (Square square : wallSquares) {
		// square.seenByPlayer = true;
		// }
		for (Square square : featureSquares) {
			square.seenByPlayer = true;
		}
	}
}