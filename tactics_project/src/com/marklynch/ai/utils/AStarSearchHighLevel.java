package com.marklynch.ai.utils;

import java.util.LinkedList;
import java.util.List;

import com.marklynch.Game;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.units.Actor;

/**
 * The AStarSearch class, along with the AStarNode class, implements a generic
 * A* search algorithm. The AStarNode class should be subclassed to provide
 * searching capability.
 */
public class AStarSearchHighLevel {

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
	protected LinkedList<Node> constructPath(Node node) {
		Game.constructPath++;
		LinkedList<Node> path = new LinkedList<Node>();
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

	public LinkedList<Node> findPath(Actor actor, Node startNode, Node goalNode, int maxCount) {
		Game.findPath++;

		Node bestNodeFound = null;
		float bestPathFoundDistanceToTarget = Integer.MAX_VALUE;

		PriorityList openList = new PriorityList();
		LinkedList<Node> closedList = new LinkedList();

		startNode.costFromStart = 0;
		startNode.estimatedCostToGoal = startNode.getEstimatedCost(goalNode);
		startNode.pathParent = null;
		openList.add(startNode);

		int count = 0;
		while (!openList.isEmpty() && count < maxCount) {
			Node node = (Node) openList.removeFirst();
			if (node == goalNode) {
				// we've reached the goal node!
				return constructPath(goalNode);
			}

			List neighbors = node.getAllNeighbourNodesThatCanBeMovedTo(actor, goalNode);
			for (int i = 0; i < neighbors.size(); i++) {
				Node neighborNode = (Node) neighbors.get(i);
				boolean isOpen = openList.contains(neighborNode);
				boolean isClosed = closedList.contains(neighborNode);
				float costFromStart = node.costFromStart;
				if (actor == Game.level.player)
					costFromStart += node.costForPlayer;
				else
					costFromStart += node.cost;
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