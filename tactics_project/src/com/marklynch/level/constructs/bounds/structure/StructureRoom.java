package com.marklynch.level.constructs.bounds.structure;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.objects.units.Actor;

public class StructureRoom {
	public String name;
	public RoomPart[] roomParts;
	public ArrayList<Square> squares = new ArrayList<Square>();
	public float x;// where to draw name of room
	public float y;
	public boolean seenByPlayer = false;
	public Structure structure;
	public int level = 0;
	public ArrayList<Node> nodes;

	public StructureRoom(String name, float x, float y, boolean restricted, ArrayList<Actor> ownersArrayList,
			Node[] nodes, RoomPart... roomParts) {
		this(name, x, y, restricted, ownersArrayList, 0, true, nodes, roomParts);
	}

	public StructureRoom(String name, float x, float y, boolean restricted, ArrayList<Actor> ownersArrayList, int level,
			Node[] nodes, RoomPart... roomParts) {
		this(name, x, y, restricted, ownersArrayList, level, true, nodes, roomParts);

	}

	public StructureRoom(String name, float x, float y, boolean restricted, ArrayList<Actor> ownersArrayList, int level,
			boolean doesNothing, Node[] nodes, RoomPart[] roomParts) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.level = level;
		this.roomParts = roomParts;// Floor squares
		this.nodes = new ArrayList<>(Arrays.asList(nodes));
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

		for (Square square : squares) {
			square.nodes = this.nodes;
			for (Node node : this.nodes) {
				node.addSquare(square);
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
}
