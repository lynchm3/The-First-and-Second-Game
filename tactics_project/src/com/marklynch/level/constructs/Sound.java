package com.marklynch.level.constructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.objects.units.Path;

public class Sound {
	public Actor sourceActor;
	public GameObject sourceObject;
	public Square sourceSquare;
	public ArrayList<Square> destinationSquares;
	public float loudness;
	public boolean legal;
	public Class action;

	public Sound(Actor sourceActor, GameObject sourceObject, Square sourceSquare, float loudness, boolean illegal,
			Class action) {
		super();
		this.sourceActor = sourceActor;
		this.sourceObject = sourceObject;
		this.sourceSquare = sourceSquare;
		this.loudness = loudness;

		createDestinationSounds();
		for (Square destinationSquare : destinationSquares) {
			destinationSquare.sounds.add(this);
		}

	}

	// ArrayList<Square> getAllSquaresWithinDistance(float maxDistance) {
	// ArrayList<Square> squares = new ArrayList<Square>();
	//
	// for (int distance = 0; distance <= maxDistance; distance++) {
	//
	// if (distance == 0)
	//
	// {
	// squares.add(this.sourceSquare);
	// continue;
	// }
	//
	// boolean xGoingUp = true;
	// boolean yGoingUp = true;
	// for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
	// if (ArrayUtils.inBounds(Game.level.squares, this.sourceSquare.xInGrid +
	// x,
	// this.sourceSquare.yInGrid + y)) {
	// squares.add(Game.level.squares[this.sourceSquare.xInGrid + (int)
	// x][this.sourceSquare.yInGrid
	// + (int) y]);
	// }
	//
	// if (xGoingUp) {
	// if (x == distance) {
	// xGoingUp = false;
	// x--;
	// } else {
	// x++;
	// }
	// } else {
	// if (x == -distance) {
	// xGoingUp = true;
	// x++;
	// } else {
	// x--;
	// }
	// }
	//
	// if (yGoingUp) {
	// if (y == distance) {
	// yGoingUp = false;
	// y--;
	// } else {
	// y++;
	// }
	// } else {
	// if (y == -distance) {
	// yGoingUp = true;
	// y++;
	// } else {
	// y--;
	// }
	// }
	//
	// }
	// }
	//
	// return squares;
	// }

	HashMap<Square, Path> squareToPath = new HashMap<Square, Path>();
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
		squareToPath.put(currentSquare, new Path((Vector<Square>) startPath.clone(), 0));

		for (int i = 0; i <= highestPathCostSeen; i++) {
			// get all paths with that cost
			Vector<Path> pathsWithCurrentCost = new Vector<Path>();
			Vector<Path> pathsVector = new Vector<Path>();
			for (Path path : squareToPath.values()) {
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

		if (direction == Direction.UP) {
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
			Path newPath = new Path(newPathSquares, newDistance);
			squareToPath.put(newSquare, newPath);
			if (!destinationSquares.contains(newSquare))
				this.destinationSquares.add(newSquare);
		}
	}
}
