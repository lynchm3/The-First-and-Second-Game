package com.marklynch.ai.utils;

import java.util.LinkedList;
import java.util.List;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

/**
 * The AStarSearch class, along with the AStarNode class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public class AStarSearchSquare {

	/**
	 * A simple priority list, also called a priority queue. Objects in the list
	 * are ordered by their priority, determined by the object's Comparable
	 * interface. The highest priority item is first in the list.
	 */
	public static class PriorityList extends LinkedList {

		public void add(Comparable object) {
			for (int i = 0; i < size(); i++) {
				if (object.compareTo(get(i)) <= 0) {
					add(i, object);
					return;
				}
			}
			addLast(object);
		}
	}

	/**
	 * Construct the path, not including the start node.
	 */
	protected LinkedList<Square> constructPath(Square node) {
		Game.constructPath++;
		LinkedList<Square> path = new LinkedList<Square>();
		while (node.pathParent != null) {
			path.addFirst(node);
			node = node.pathParent;
		}
		return path;
	}

	/**
	 * Find the path from the start node to the end node. A list of AStarNodes
	 * is returned, or null if the path is not found.
	 */

	public LinkedList<Square> findPath(Actor actor, Square startNode, Square goalNode, int maxCount) {
		Game.findPath++;

		Square bestNodeFound = null;
		float bestPathFoundDistanceToTarget = Integer.MAX_VALUE;

		PriorityList openList = new PriorityList();
		LinkedList<Square> closedList = new LinkedList();

		startNode.costFromStart = 0;
		startNode.estimatedCostToGoal = startNode.getEstimatedCost(goalNode);
		startNode.pathParent = null;
		openList.add(startNode);

		int count = 0;
		while (!openList.isEmpty() && count < maxCount) {
			Square node = (Square) openList.removeFirst();
			if (node == goalNode) {
				// we've reached the goal node!
				return constructPath(goalNode);
			}

			List neighbors = node.getAllNeighbourSquaresThatCanBeMovedTo(actor, goalNode);
			for (int i = 0; i < neighbors.size(); i++) {
				Square neighborNode = (Square) neighbors.get(i);
				boolean isOpen = openList.contains(neighborNode);
				boolean isClosed = closedList.contains(neighborNode);
				float costFromStart = node.costFromStart;
				if (actor == Game.level.player)
					costFromStart += node.pathCostForPlayer;
				else
					costFromStart += node.pathCostForAI;
				// check if the neighbor node has not been
				// traversed or if a shorter path to this
				// neighbor node is found.
				if ((!isOpen && !isClosed) || costFromStart < neighborNode.costFromStart) {
					neighborNode.pathParent = node;
					neighborNode.costFromStart = costFromStart;
					neighborNode.estimatedCostToGoal = neighborNode.getEstimatedCost(goalNode);
					if (isClosed) {
						closedList.remove(neighborNode);
					}
					if (!isOpen) {
						openList.add(neighborNode);
					}
				}
			}
			closedList.add(node);
			count++;

			if (node.estimatedCostToGoal < bestPathFoundDistanceToTarget) {
				bestNodeFound = node;
				bestPathFoundDistanceToTarget = node.estimatedCostToGoal;
			}

			if (count == maxCount) {
				return constructPath(bestNodeFound);
				// return constructPath(node);
				// return null;
			}
		}

		// no path found
		return null;
	}

}