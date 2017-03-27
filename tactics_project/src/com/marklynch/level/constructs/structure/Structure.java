package com.marklynch.level.constructs.structure;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.structure.StructureRoom.RoomPart;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Texture;

public class Structure {

	public String name;
	public ArrayList<StructureRoom> rooms;
	public boolean seenByPlayer = false;
	public ArrayList<StructureSection> structureSections;
	public ArrayList<Square> entranceSquares;
	public Texture imageTexture;
	float overlayX1, overlayX2, overlayY1, overlayY2;
	ArrayList<Square> floorSquares;
	ArrayList<Square> wallSquares;
	ArrayList<Square> featureSquares;
	boolean blocksLineOfSight;
	Actor owner;
	ArrayList<Square> squaresToRemove;

	public Structure(String name, ArrayList<StructureSection> caveSections, ArrayList<StructureRoom> rooms,
			ArrayList<StructurePath> paths, ArrayList<GameObject> features, ArrayList<Square> entrances,
			String imageTexturePath, float overlayX1, float overlayX2, float overlayY1, float overlayY2,
			boolean blocksLineOfSight, Actor owner, ArrayList<Square> squaresToRemove) {
		super();

		this.name = name;
		this.structureSections = caveSections;
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

		// SquaresToRemove
		floorSquares.addAll(squaresToRemove);

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
		for (StructurePath path : paths) {
			for (Square square : path.squares)
				floorSquares.add(square);
		}

		// Entrance Squares

		// Floor squares
		for (StructureRoom room : rooms) {
			for (RoomPart roomPart : room.roomParts) {
				for (int i = roomPart.gridX1; i <= roomPart.gridX2; i++) {
					for (int j = roomPart.gridY1; j <= roomPart.gridY2; j++) {
						floorSquares.add(Game.level.squares[i][j]);
						Game.level.squares[i][j].structureRoomSquareIsIn = room;
					}
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

						wallsInCave.add(Templates.WALL.makeCopy(Game.level.squares[i][j], this.owner));
						wallSquares.add(Game.level.squares[i][j]);
					}
					Game.level.squares[i][j].structureSquareIsIn = this;
					Game.level.squares[i][j].structureSectionSquareIsIn = caveSection;
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
		// if (!seenByPlayer)
		// return;

		for (StructureRoom room : rooms) {
			TextUtils.printTextWithImages(new Object[] { room.name }, room.x * Game.SQUARE_WIDTH,
					room.y * Game.SQUARE_HEIGHT, 100, true);

		}

		// if (imageTexture != null) {
		//
		// if (Game.level.player.squareGameObjectIsOn.structureSquareIsIn ==
		// this
		// || entranceSquares.contains(Game.level.player.squareGameObjectIsOn))
		// {
		// if (roofAlpha != 0f) {
		// roofAlpha -= 0.01f;
		// if (roofAlpha < 0f)
		// roofAlpha = 0f;
		// }
		// } else if (roofAlpha != 1f) {
		// roofAlpha += 0.01f;
		// if (roofAlpha > 1f)
		// roofAlpha = 1f;
		// }
		// TextureUtils.drawTexture(imageTexture, roofAlpha, this.overlayX1,
		// this.overlayX2, this.overlayY1,
		// this.overlayY2);
		// }

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