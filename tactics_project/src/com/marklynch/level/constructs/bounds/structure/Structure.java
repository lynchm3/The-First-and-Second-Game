package com.marklynch.level.constructs.bounds.structure;

import com.marklynch.Game;
import com.marklynch.actions.ActionSpot;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.area.Place;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Structure implements Idable, Place {

	public String name;
	public CopyOnWriteArrayList<StructureRoom> rooms = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
	public boolean seenByPlayer = false;
	public CopyOnWriteArrayList<StructureSection> structureSections = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
	public CopyOnWriteArrayList<Square> entranceSquares = new CopyOnWriteArrayList<Square>(Square.class);
	public Texture mapIconForStructure;
	public Texture icon;
	int gridX1, gridX2, gridY1, gridY2, gridCenterX, gridCenterY;
	public Square centreSquare;
	CopyOnWriteArrayList<Square> floorSquares = new CopyOnWriteArrayList<Square>(Square.class);
	CopyOnWriteArrayList<Square> wallSquares = new CopyOnWriteArrayList<Square>(Square.class);
	CopyOnWriteArrayList<Square> featureSquares = new CopyOnWriteArrayList<Square>(Square.class);
	boolean blocksLineOfSight;
	public Actor owner;
	CopyOnWriteArrayList<Square> squaresToRemove = new CopyOnWriteArrayList<Square>(Square.class);
	boolean showOnMap = false;
	public Long id;

	public Structure(String name, CopyOnWriteArrayList<StructureSection> caveSections, CopyOnWriteArrayList<StructureRoom> rooms,
			CopyOnWriteArrayList<StructurePath> paths, CopyOnWriteArrayList<StructureFeature> features, CopyOnWriteArrayList<Square> entrances,
			Texture iconTexture, int overlayX1, int overlayY1, int overlayX2, int overlayY2, boolean blocksLineOfSight,
			Actor owner, CopyOnWriteArrayList<Square> squaresToRemove, CopyOnWriteArrayList<Wall> extraWalls, Wall wallTemplate,
			Texture floorImageTexture, int level) {
		super();
		this.id = Level.generateNewId(this);

		this.name = name;
		this.structureSections = caveSections;
		this.rooms = rooms;
		this.icon = iconTexture;
		this.gridX1 = overlayX1;
		this.gridY1 = overlayY1;
		this.gridX2 = overlayX2;
		this.gridY2 = overlayY2;
		this.gridCenterX = (gridX1 + gridX2) / 2;
		this.gridCenterY = (gridY1 + gridY2) / 2;
		this.centreSquare = Level.squares[gridCenterX][gridCenterY];
		this.entranceSquares = entrances;
		this.blocksLineOfSight = blocksLineOfSight;
		this.level = level;
		floorSquares = new CopyOnWriteArrayList<Square>(Square.class);
		wallSquares = new CopyOnWriteArrayList<Square>(Square.class);
		featureSquares = new CopyOnWriteArrayList<Square>(Square.class);

		// SquaresToRemove
		floorSquares.addAll(squaresToRemove);

		// Entrance squares
		for (Square entranceSquare : entranceSquares) {
			entranceSquare.setFloorImageTexture(floorImageTexture);
			entranceSquare.calculatePathCost();
			entranceSquare.calculatePathCostForPlayer();
		}

		// Feature squares
		for (StructureFeature feature : features) {
			floorSquares.add(feature.gameObject.squareGameObjectIsOn);
			featureSquares.add(feature.gameObject.squareGameObjectIsOn);
		}

		for (StructureRoom room : rooms) {
			for (StructureFeature feature : room.features) {
				floorSquares.add(feature.gameObject.squareGameObjectIsOn);
				featureSquares.add(feature.gameObject.squareGameObjectIsOn);
			}
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
		CopyOnWriteArrayList<Wall> wallsInCave = new CopyOnWriteArrayList<Wall>(Wall.class);
		wallsInCave.addAll(extraWalls);
		for (StructureRoom room : rooms) {
			wallsInCave.addAll(room.extraWalls);
		}

		for (Wall wallInCave : wallsInCave) {
			wallSquares.add(wallInCave.squareGameObjectIsOn);
		}

		for (StructureSection caveSection : caveSections) {
			for (int i = caveSection.gridX1; i <= caveSection.gridX2; i++) {
				for (int j = caveSection.gridY1; j <= caveSection.gridY2; j++) {
					if (!floorSquares.contains(Game.level.squares[i][j])
							&& !Game.level.squares[i][j].inventory.containsGameObjectOfType(Wall.class)) {

						wallsInCave.add(wallTemplate.makeCopy(Game.level.squares[i][j], this.owner));
						wallSquares.add(Game.level.squares[i][j]);
					}
					if (!squaresToRemove.contains(Game.level.squares[i][j])) {
						Level.squares[i][j].structureSquareIsIn = this;
						Level.squares[i][j].structureSectionSquareIsIn = caveSection;
						if (Level.squares[i][j].getFloorImageTexture() == Square.GRASS_TEXTURE)
							Level.squares[i][j].setFloorImageTexture(floorImageTexture);
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
							- 1].inventory.containsGameObjectOfType(Wall.class)) {
				wall.connectedTopRight = true;
			}
			// Bottom Right
			if (wall.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1
					&& wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid + 1][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.containsGameObjectOfType(Wall.class)) {
				wall.connectedBottomRight = true;
			}
			// BOttom left
			if (wall.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1
					&& wall.squareGameObjectIsOn.xInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
							+ 1].inventory.containsGameObjectOfType(Wall.class)) {
				wall.connectedBottomLeft = true;
			}
			// Top left
			if (wall.squareGameObjectIsOn.xInGrid > 0 && wall.squareGameObjectIsOn.yInGrid > 0
					&& Game.level.squares[wall.squareGameObjectIsOn.xInGrid - 1][wall.squareGameObjectIsOn.yInGrid
							- 1].inventory.containsGameObjectOfType(Wall.class)) {
				wall.connectedTopLeft = true;
			}

			if (wall.connectedLeft && wall.connectedTop) {
				wall.connectedTopLeft = true;
			}

			if (wall.connectedLeft && wall.connectedBottom) {
				wall.connectedBottomLeft = true;
			}

			if (wall.connectedRight && wall.connectedTop) {
				wall.connectedTopRight = true;
			}

			if (wall.connectedRight && wall.connectedBottom) {
				wall.connectedBottomRight = true;
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
						Color.WHITE, 1f, new Object[] { room.name });
		}

	}

	public void drawUI() {

		if (!showOnMap && !Game.fullVisiblity)
			return;

		// 40sqrs is ideal

		if (this.mapIconForStructure == null)
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
		TextureUtils.drawTexture(mapIconForStructure, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);

	}

	public void hasBeenSeenByPlayer(Square squareSeen) {
		this.seenByPlayer = true;
		new ActionSpot(Game.level.player, this, squareSeen).perform();
		this.showOnMap = true;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Texture getIcon() {
		return icon;
	}

	@Override
	public Square getCentreSquare() {
		return centreSquare;
	}
}