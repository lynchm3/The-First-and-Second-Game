package com.marklynch.level.squares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;

public class Node implements Comparable<Node> {

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

	public ArrayList<Node> neighbors = new ArrayList<Node>();

	public HashMap<Node, Integer> costToNeighbors;
	// end path finding

	public Node(String name, Square square) {
		this.name = name;
		this.square = square;
		this.xInGrid = square.xInGrid;
		this.yInGrid = square.yInGrid;
		square.node = this;
	}

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
		ArrayList<Node> squares = new ArrayList<Node>();

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

	// public void calculateDistanceToNeighbours() {
	// for (Node neighbor : neighbors) {
	// costToNeighbors.put(neighbor, this.straightLineDistanceTo(neighbor));
	// }
	// }
	/// END PATH FINDING

}
