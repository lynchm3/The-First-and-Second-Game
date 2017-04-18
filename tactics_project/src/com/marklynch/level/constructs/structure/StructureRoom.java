package com.marklynch.level.constructs.structure;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class StructureRoom {
	public String name;
	public RoomPart[] roomParts;
	public ArrayList<Square> squares = new ArrayList<Square>();
	public float x;
	public float y;

	public StructureRoom(String name, float x, float y, boolean restricted, ArrayList<Actor> ownersArrayList,
			RoomPart... roomParts) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
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

		if (ownersArrayList.size() > 0) {
			for (Square square : squares) {
				square.owners = ownersArrayList;
			}
		}

	}

	public static class RoomPart {
		public int gridX1, gridY1, gridX2, gridY2;

		public RoomPart(int gridX1, int gridY1, int gridX2, int gridY2) {
			this.gridX1 = gridX1;
			this.gridY1 = gridY1;
			this.gridX2 = gridX2;
			this.gridY2 = gridY2;

		}
	}
}
