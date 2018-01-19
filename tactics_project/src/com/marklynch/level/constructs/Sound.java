package com.marklynch.level.constructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor.Direction;

public class Sound {
	public GameObject sourcePerformer;
	public GameObject sourceObject;
	public Square sourceSquare;
	public ArrayList<Square> destinationSquares;
	public float loudness;
	public boolean legal;
	public Class actionType;

	public Sound(GameObject sourceActor, GameObject sourceObject, Square sourceSquare, float loudness, boolean legal,
			Class action) {
		super();
		this.sourcePerformer = sourceActor;
		this.sourceObject = sourceObject;
		this.sourceSquare = sourceSquare;
		this.loudness = loudness;
		this.legal = legal;
		this.actionType = action;

		// destinationSquares = new ArrayList<Square>();
		createDestinationSounds();

	}

	public void play() {
		for (Square destinationSquare : destinationSquares) {
			destinationSquare.sounds.add(this);
		}
	}

	HashMap<Square, AIPath> squareToPath = new HashMap<Square, AIPath>();
	int highestPathCostSeen;

	public void createDestinationSounds() {

		highestPathCostSeen = 0;
		squareToPath.clear();
		destinationSquares = new ArrayList<Square>();
		// destinationSquares.clear();

		Square currentSquare = this.sourceSquare;
		this.destinationSquares.add(currentSquare);

		Vector<Square> startPath = new Vector<Square>();
		startPath.add(currentSquare);
		squareToPath.put(currentSquare, new AIPath((Vector<Square>) startPath.clone(), 0, false));

		for (int i = 0; i <= highestPathCostSeen; i++) {
			// get all paths with that cost
			Vector<AIPath> pathsWithCurrentCost = new Vector<AIPath>();
			Vector<AIPath> pathsVector = new Vector<AIPath>();
			for (AIPath path : squareToPath.values()) {
				pathsVector.add(path);
			}
			for (int j = 0; j < pathsVector.size(); j++) {
				if (pathsVector.get(j).travelCost == i)
					pathsWithCurrentCost.add(pathsVector.get(j));
			}

			for (int j = 0; j < pathsWithCurrentCost.size(); j++) {
				Vector<Square> squaresInThisPath = pathsWithCurrentCost.get(j).squares;
				createDestinationSounds2(Direction.UP, squaresInThisPath, i);
				createDestinationSounds2(Direction.RIGHT, squaresInThisPath, i);
				createDestinationSounds2(Direction.DOWN, squaresInThisPath, i);
				createDestinationSounds2(Direction.LEFT, squaresInThisPath, i);
			}
		}
	}

	public void createDestinationSounds2(Direction direction, Vector<Square> squaresInThisPath, int pathCost) {

		if (pathCost > loudness)
			return;

		Square newSquare = null;

		Square parentSquare = squaresInThisPath.get(squaresInThisPath.size() - 1);

		if (parentSquare == null) {

		} else if (direction == Direction.UP) {
			if (parentSquare.yInGrid - 1 >= 0) {
				newSquare = Game.level.squares[parentSquare.xInGrid][parentSquare.yInGrid - 1];
			}
		} else if (direction == Direction.RIGHT) {
			if (parentSquare.xInGrid + 1 < Game.level.squares.length) {
				newSquare = Game.level.squares[parentSquare.xInGrid + 1][parentSquare.yInGrid];
			}
		} else if (direction == Direction.DOWN) {

			if (parentSquare.yInGrid + 1 < Game.level.squares[0].length) {
				newSquare = Game.level.squares[parentSquare.xInGrid][parentSquare.yInGrid + 1];
			}
		} else if (direction == Direction.LEFT) {
			if (parentSquare.xInGrid - 1 >= 0) {
				newSquare = Game.level.squares[parentSquare.xInGrid - 1][parentSquare.yInGrid];
			}
		}

		if (newSquare != null && !squaresInThisPath.contains(newSquare) && !squareToPath.containsKey(newSquare)) {
			Vector<Square> newPathSquares = (Vector<Square>) squaresInThisPath.clone();
			newPathSquares.add(newSquare);
			int newDistance = (int) (pathCost + newSquare.inventory.getSoundDampening());
			if (newDistance > highestPathCostSeen)
				highestPathCostSeen = newDistance;
			AIPath newPath = new AIPath(newPathSquares, newDistance, false);
			squareToPath.put(newSquare, newPath);
			if (!destinationSquares.contains(newSquare))
				this.destinationSquares.add(newSquare);
		}
	}
}
