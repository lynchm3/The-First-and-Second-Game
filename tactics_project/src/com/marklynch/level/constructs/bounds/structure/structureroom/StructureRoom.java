package com.marklynch.level.constructs.bounds.structure.structureroom;

import com.marklynch.Game;
import com.marklynch.actions.ActionSpot;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Color;

public class StructureRoom implements Idable {
	public Long id;
	public String name;
	public RoomPart[] roomParts;
	public CopyOnWriteArrayList<Square> squares = new CopyOnWriteArrayList<Square>(Square.class);
	public float x;// where to draw name of room
	public float y;
	public boolean seenByPlayer = false;
	public Structure structure;
	public int level = 0;

	public static Color roomColor = new Color(0.7f, 0.7f, 0.7f);

	public CopyOnWriteArrayList<StructureFeature> features = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
	public CopyOnWriteArrayList<Wall> extraWalls = new CopyOnWriteArrayList<Wall>(Wall.class);

	public StructureRoom(String name, float x, float y, boolean restricted, boolean restrictedAtNight,
			CopyOnWriteArrayList<Actor> ownersCopyOnWriteArrayList, RoomPart... roomParts) {
		this(name, x, y, restricted, restrictedAtNight, ownersCopyOnWriteArrayList, 0, true, roomParts);
	}

	public StructureRoom(String name, float x, float y, boolean restricted, boolean restrictedAtNight,
			CopyOnWriteArrayList<Actor> ownersCopyOnWriteArrayList, int level, RoomPart... roomParts) {
		this(name, x, y, restricted, restrictedAtNight, ownersCopyOnWriteArrayList, level, true, roomParts);

	}

	public StructureRoom(String name, float x, float y, boolean restricted, boolean restrictedAtNight,
			CopyOnWriteArrayList<Actor> ownersCopyOnWriteArrayList, int level, boolean doesNothing, RoomPart[] roomParts) {
		super();

		this.id = Level.generateNewId(this);

		this.name = name;
		this.x = x;
		this.y = y;
		this.level = level;
		this.roomParts = roomParts;// Floor squares
		for (RoomPart roomPart : this.roomParts) {
			for (int i = roomPart.gridX1; i <= roomPart.gridX2; i++) {
				for (int j = roomPart.gridY1; j <= roomPart.gridY2; j++) {
					squares.add(Game.level.squares[i][j]);
				}
			}
		}

		if (restricted) {
			for (Square square : squares) {
				square.restricted = true;
			}
		}

		if (restrictedAtNight) {
			for (Square square : squares) {
				square.restrictedAtNight = true;
			}
		}

		if (ownersCopyOnWriteArrayList.size() > 0) {
			for (Square square : squares) {
				square.owners = ownersCopyOnWriteArrayList;
			}
		}

	}

	public static class RoomPart {
		public int gridX1, gridY1, gridX2, gridY2, gridCenterX, gridCenterY;
		public Square centreSquare;

		public RoomPart(int gridX1, int gridY1, int gridX2, int gridY2) {
			this.gridX1 = gridX1;
			this.gridY1 = gridY1;
			this.gridX2 = gridX2;
			this.gridY2 = gridY2;
			this.gridCenterX = (gridX1 + gridX2) / 2;
			this.gridCenterY = (gridY1 + gridY2) / 2;
			this.centreSquare = Level.squares[gridCenterX][gridCenterY];

		}
	}

	public void hasBeenSeenByPlayer(Square squareSeen) {
		this.seenByPlayer = true;
		new ActionSpot(Game.level.player, this, squareSeen).perform();
		// for (Square square : this.entranceSquares) {
		// square.seenByPlayer = true;
		// }
		// for (Square square : floorSquares) {
		// square.seenByPlayer = true;
		// }
		// for (Square square : wallSquares) {
		// square.seenByPlayer = true;
		// }
		// for (Square square : featureSquares) {
		// square.seenByPlayer = true;
		// }
	}

	@Override
	public Long getId() {
		return id;
	}
}
