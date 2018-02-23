package com.marklynch.ai.utils;

import java.util.ArrayList;
import java.util.List;

import com.marklynch.level.squares.Square;
//import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

/**
 * The AStarNode class, along with the AStarSearch class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public abstract class AStarNodeHighLevel implements Comparable {

	AStarNodeHighLevel pathParent;
	float costFromStart;
	float estimatedCostToGoal;

	// added by me
	public float cost = 1;
	public float costForPlayer = 1;

	public int xInGrid;
	public int yInGrid;

	public float xInGridPixels;
	public float yInGridPixels;

	public ArrayList<Square> neighbors;

	// added by me
	public int straightLineDistanceTo(AStarNodeHighLevel otherNode) {
		return Math.abs(otherNode.xInGrid - this.xInGrid) + Math.abs(otherNode.yInGrid - this.yInGrid);
	}

	public float getCost() {
		return costFromStart + estimatedCostToGoal;
	}

	@Override
	public int compareTo(Object other) {
		float thisValue = this.getCost();
		float otherValue = ((AStarNodeHighLevel) other).getCost();

		float v = thisValue - otherValue;
		return (v > 0) ? 1 : (v < 0) ? -1 : 0; // sign function
	}

	/**
	 * Gets the cost between this node and the specified adjacent (AKA
	 * "neighbor" or "child") node.
	 */
	// public abstract float getCost(AStarNode node);

	/**
	 * Gets the estimated cost between this node and the specified node. The
	 * estimated cost should never exceed the true cost. The better the
	 * estimate, the more effecient the search.
	 */
	public abstract float getEstimatedCost(AStarNodeHighLevel node);

	public List getNeighborsThatCanBeMovedTo(Actor actor) {
		return null;
	}

	/**
	 * Gets the children (AKA "neighbors" or "adjacent nodes") of this node.
	 * 
	 * @param goalNode
	 */
	public abstract List getAllNeighbourSquaresThatCanBeMovedTo(Actor actor, AStarNodeHighLevel goalNode);
}