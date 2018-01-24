package com.marklynch.level.constructs.bounds.structure;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Structure {

	public String name;
	public ArrayList<StructureRoom> rooms;
	public boolean seenByPlayer = false;
	public ArrayList<StructureSection> structureSections;
	public ArrayList<Square> entranceSquares;
	public Texture image;
	int gridX1, gridX2, gridY1, gridY2;
	ArrayList<Square> floorSquares;
	ArrayList<Square> wallSquares;
	ArrayList<Square> featureSquares;
	boolean blocksLineOfSight;
	public Actor owner;
	ArrayList<Square> squaresToRemove;
	boolean showOnMap = false;

	public Structure(String name, ArrayList<StructureSection> caveSections, ArrayList<StructureRoom> rooms,
			ArrayList<StructurePath> paths, ArrayList<GameObject> features, ArrayList<Square> entrances,
			String imageTexturePath, int overlayX1, int overlayY1, int overlayX2, int overlayY2,
			boolean blocksLineOfSight, Actor owner, ArrayList<Square> squaresToRemove, ArrayList<Wall> extraWalls,
			Wall wallTemplate, Texture imageTexture, int level) {
		super();

		this.name = name;
		this.structureSections = caveSections;
		this.rooms = rooms;
		if (imageTexturePath != null)
			this.image = ResourceUtils.getGlobalImage(imageTexturePath);
		this.gridX1 = overlayX1;
		this.gridY1 = overlayY1;
		this.gridX2 = overlayX2;
		this.gridY2 = overlayY2;
		this.entranceSquares = entrances;
		this.blocksLineOfSight = blocksLineOfSight;
		this.level = level;
		floorSquares = new ArrayList<Square>();
		wallSquares = new ArrayList<Square>();
		featureSquares = new ArrayList<Square>();

		// SquaresToRemove
		floorSquares.addAll(squaresToRemove);

		// Entrance squares
		for (Square entranceSquare : entranceSquares) {
			entranceSquare.imageTexture = imageTexture;
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
			room.structure = this;
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
		wallsInCave.addAll(extraWalls);

		for (Wall wallInCave : wallsInCave) {
			wallSquares.add(wallInCave.squareGameObjectIsOn);
		}

		for (StructureSection caveSection : caveSections) {
			for (int i = caveSection.gridX1; i <= caveSection.gridX2; i++) {
				for (int j = caveSection.gridY1; j <= caveSection.gridY2; j++) {
					if (!floorSquares.contains(Game.level.squares[i][j])
							&& !Game.level.squares[i][j].inventory.contains(Wall.class)) {

						wallsInCave.add(wallTemplate.makeCopy(Game.level.squares[i][j], this.owner));
						wallSquares.add(Game.level.squares[i][j]);
					}
					if (!squaresToRemove.contains(Game.level.squares[i][j])) {
						Game.level.squares[i][j].structureSquareIsIn = this;
						Game.level.squares[i][j].structureSectionSquareIsIn = caveSection;
						Game.level.squares[i][j].imageTexture = imageTexture;
					}
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
			}
			// Bottom Right
			if (wall.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1
					&& wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottomRight = true;
			}
			// BOttom left
			if (wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& wall.squareGameObjectIsOn.xInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.contains(Wall.class)) {
				wall.connectedBottomLeft = true;
			}
			// Top left
			if (wall.squareGameObjectIsOn.xInGrid > 0 && wall.squareGameObjectIsOn.yInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
							- 1].inventory.contains(Wall.class)) {
				wall.connectedTopLeft = true;
			}
			wall.checkIfFullWall();
		}

	}

	public float roofAlpha = 1f;
	public int level;

	public void draw2() {

		for (StructureRoom room : rooms) {
			if (room.seenByPlayer)
				TextUtils.printTextWithImages(room.x * Game.SQUARE_WIDTH, room.y * Game.SQUARE_HEIGHT, 100, true, null,
						new Object[] { room.name });
		}

	}

	public void drawUI() {

		if (!showOnMap && !Game.fullVisiblity)
			return;

		// 40sqrs is ideal

		if (this.image == null)
			return;

		int squarePositionX1 = (gridX1 + ((gridX2 - gridX1 - 40) / 2)) * (int) Game.SQUARE_WIDTH;
		int squarePositionY1 = (gridY1 + ((gridY2 - gridY1 - 40) / 2)) * (int) Game.SQUARE_HEIGHT;
		int squarePositionX2 = (40 + gridX1 + ((gridX2 - gridX1 - 40) / 2)) * (int) Game.SQUARE_WIDTH;
		int squarePositionY2 = (40 + gridY1 + ((gridY2 - gridY1 - 40) / 2)) * (int) Game.SQUARE_HEIGHT;
		float drawPositionX1 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX1 - Game.windowWidth / 2 + Game.getDragXWithOffset()));
		float drawPositionY1 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY1 - Game.windowHeight / 2 + Game.getDragYWithOffset()));
		float drawPositionX2 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX2 - Game.windowWidth / 2 + Game.getDragXWithOffset()));
		float drawPositionY2 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY2 - Game.windowHeight / 2 + Game.getDragYWithOffset()));
		TextureUtils.drawTexture(image, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);

	}

	public void hasBeenSeenByPlayer(Square squareSeen) {
		this.seenByPlayer = true;
		new ActionSpot(Game.level.player, this, squareSeen).perform();
		this.showOnMap = true;
	}
}