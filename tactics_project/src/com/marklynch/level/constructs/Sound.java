package com.marklynch.level.constructs;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.CircleUtils;
import com.marklynch.utils.Color;
import com.marklynch.utils.TextureUtils;

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

	public void draw() {
		float circleCenterX = sourceSquare.getCenterX();
		float circleCenterY = sourceSquare.getCenterY();
		CircleUtils.drawCircle(Color.BLACK, 64, circleCenterX, circleCenterY);
		int circlesToDraw = (int) loudness * 2;

		System.out.println("loudness = " + loudness);
		System.out.println("circlesToDraw = " + circlesToDraw);

		float squarePositionX = destinationSquares.get(0).xInGridPixels;
		float squarePositionY = destinationSquares.get(0).yInGridPixels;
		float circleCenterX1 = destinationSquares.get(0).getCenterX();
		float circleCenterY1 = destinationSquares.get(0).getCenterY();
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

		for (int i = 0; i < circlesToDraw; i++) {

			int radius = (int) (i * Game.HALF_SQUARE_WIDTH);
			System.out.println("radius = " + radius);
			System.out.println("circleCenterX = " + circleCenterX);
			System.out.println("circleCenterY = " + circleCenterY);
			CircleUtils.drawCircle(Color.BLACK, radius, circleCenterX, circleCenterY);
		}
		// int radius1 = 128;
		// System.out.println("radius1 = " + radius1);
		// System.out.println("circleCenterX1 = " + circleCenterX1);
		// System.out.println("circleCenterY1 = " + circleCenterY1);
		// // CircleUtils.drawCircle(Color.BLACK, radius1, circleCenterX1,
		// circleCenterY1);
		// CircleUtils.drawCircle(Color.BLACK, radius1, circleCenterX, circleCenterY);

		// for (Square square : destinationSquares) {
		//
		// float squarePositionX2 = square.xInGridPixels;
		// float squarePositionY2 = square.yInGridPixels;
		//
		// TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2,
		// squarePositionX, squarePositionY,
		// squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		// float circleCenterX2 = square.getCenterX();
		// float circleCenterY2 = square.getCenterY();
		// CircleUtils.drawCircle(Color.BLACK, 128, circleCenterX2, circleCenterY2);
		// // System.out.println("circleCenterX2 = " + circleCenterX2);
		// // System.out.println("circleCenterY2 = " + circleCenterY2);
		//
		// }
	}

	@Override
	public String toString() {
		return "Sound [sourcePerformer=" + sourcePerformer + ", sourceObject=" + sourceObject + ", sourceSquare="
				+ sourceSquare + ", destinationSquares=" + destinationSquares + ", loudness=" + loudness + ", legal="
				+ legal + ", actionType=" + actionType + "]";
	}

	public void play() {
		for (Square destinationSquare : destinationSquares) {

			if (destinationSquare.inventory.actor != null) {
				destinationSquare.inventory.actor.createSearchLocationsBasedOnSound(this);
			}

			// destinationSquare.sounds.add(this);
		}

		// ye..
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

		ArrayList<Square> startPath = new ArrayList<Square>();
		startPath.add(currentSquare);
		squareToPath.put(currentSquare, new AIPath((ArrayList<Square>) startPath.clone(), 0, false));

		for (int i = 0; i <= highestPathCostSeen; i++) {
			// get all paths with that cost
			ArrayList<AIPath> pathsWithCurrentCost = new ArrayList<AIPath>();
			ArrayList<AIPath> pathsArrayList = new ArrayList<AIPath>();
			for (AIPath path : squareToPath.values()) {
				pathsArrayList.add(path);
			}
			for (int j = 0; j < pathsArrayList.size(); j++) {
				if (pathsArrayList.get(j).travelCost == i)
					pathsWithCurrentCost.add(pathsArrayList.get(j));
			}

			for (int j = 0; j < pathsWithCurrentCost.size(); j++) {
				ArrayList<Square> squaresInThisPath = pathsWithCurrentCost.get(j).squares;
				createDestinationSounds2(Direction.UP, squaresInThisPath, i);
				createDestinationSounds2(Direction.RIGHT, squaresInThisPath, i);
				createDestinationSounds2(Direction.DOWN, squaresInThisPath, i);
				createDestinationSounds2(Direction.LEFT, squaresInThisPath, i);
			}
		}
	}

	public void createDestinationSounds2(Direction direction, ArrayList<Square> squaresInThisPath, int pathCost) {

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
			ArrayList<Square> newPathSquares = (ArrayList<Square>) squaresInThisPath.clone();
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
