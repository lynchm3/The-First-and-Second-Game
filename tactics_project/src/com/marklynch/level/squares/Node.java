package com.marklynch.level.squares;

import java.util.HashMap;
import java.util.List;

import com.marklynch.Game;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Utils.Point;

public class Node implements Comparable<Node>, Idable {

	public long id;

	public String name;
	public final Square square;

	// path finding
	public Node pathParent;
	public float costFromStart;
	public float estimatedCostToGoal;

	public float cost = 1;
	public float costForPlayer = 1;

	public final int xInGrid;
	public final int yInGrid;

	public ArrayList<Node> neighbors = new ArrayList<Node>(Node.class);

	public HashMap<Node, Integer> costToNeighbors;
	private ArrayList<Square> squares = new ArrayList<Square>(Square.class);
	// end path finding

	public Node(String name, Square square) {
		this(name, square, null, null);
	}

	public Node(String name, Square square, Point p1, Point p2) {

		this.id = Level.generateNewId(this);
		this.name = name;
		this.square = square;
		this.xInGrid = square.xInGrid;
		this.yInGrid = square.yInGrid;
		this.square.node = this;
		if (p1 != null && p2 != null) {
			for (int x = (int) p1.x; x <= p2.x; x++) {
				for (int y = (int) p1.y; y <= p2.y; y++) {
					getSquares().add(Level.squares[x][y]);
				}
			}
		}

		for (Square squareInSquares : getSquares()) {
			for (Node node : squareInSquares.nodes) {
				node.squares.remove(squareInSquares);
			}
			squareInSquares.nodes.clear();
			squareInSquares.nodes.add(this);
		}

	}

//	public Node(String name, Square square, ArrayList<Square> squares) {
//
//		this.id = Level.generateNewId(this);
//		this.name = name;
//		this.square = square;
//		this.xInGrid = square.xInGrid;
//		this.yInGrid = square.yInGrid;
//		this.square.node = this;
//		this.squares = squares;
//		for (Square squareInSquares : squares) {
//			squareInSquares.nodes.clear();
//			squareInSquares.nodes.add(this);
//		}
//	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", square=" + square + "]";
	}

	// PATH FINDING
	public int straightLineDistanceTo(Node otherNode) {
		return Math.abs(otherNode.xInGrid - this.xInGrid) + Math.abs(otherNode.yInGrid - this.yInGrid);
	}

	public float getCost() {
		return costFromStart + estimatedCostToGoal;
	}

	@Override
	public int compareTo(Node other) {
		float thisValue = this.getCost();
		float otherValue = other.getCost();

		float v = thisValue - otherValue;
		return (v > 0) ? 1 : (v < 0) ? -1 : 0; // sign function
	}

	public List getAllNeighbourNodesThatCanBeMovedTo(Actor actor, Square goalSquare) {
		Game.getNeighborsThatCanBeMovedTo++;
		Game.getAllNeighbourSquaresThatCanBeMovedTo++;
		ArrayList<Node> squares = new ArrayList<Node>(Node.class);

		for (Node node : neighbors) {
			if (node.includableInPath(actor, goalSquare)) {
				squares.add(node);
			}
		}
		return squares;
	}

	public float getEstimatedCost(Node node) {
		return this.straightLineDistanceTo(node);
	}

	public List getNeighborsThatCanBeMovedTo(Actor actor) {
		return null;
	}

	public boolean includableInPath(Actor actor, Square goalSquare) {
		return this.square.includableInPath(actor, goalSquare, true);
	}

	public void addSquare(Square square) {
		if (!squares.contains(square)) {
			squares.add(square);
			for (Node node : square.nodes) {
				node.squares.remove(square);
			}
			square.nodes.clear();
			square.nodes.add(this);
		}
	}

	public void setSquares(ArrayList<Square> squares) {
		for (Square square : squares) {
			addSquare(square);
		}
	}

	public void setSquares(Square[] squares) {
		for (Square square : squares) {
			addSquare(square);
		}
	}

	@Override
	public Long getId() {
		return id;
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	// public void calculateDistanceToNeighbours() {
	// for (Node neighbor : neighbors) {
	// costToNeighbors.put(neighbor, this.straightLineDistanceTo(neighbor));
	// }
	// }
	/// END PATH FINDING

}
