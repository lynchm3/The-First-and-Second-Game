package com.marklynch.level.constructs;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.CircleUtils;
import com.marklynch.utils.Color;

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
		int circlesToDraw = (int) loudness * 3;

		for (int i = 0; i < circlesToDraw; i++) {
			int radius = (int) (i * Game.HALF_SQUARE_WIDTH);

			// optimisation 1: pass a list of bounds instead of 1
			// optimisation 2: only try to draw square on circle when u know it'll be a
			// possiblity they overlap
			CircleUtils.drawCircleWithinBounds(Color.BLACK, radius, circleCenterX, circleCenterY, destinationSquares);
		}

		for (Square destinationSquare : destinationSquares) {
			// TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2,
			// destinationSquare.xInGridPixels,
			// destinationSquare.yInGridPixels, destinationSquare.xInGridPixels +
			// Game.SQUARE_WIDTH,
			// destinationSquare.yInGridPixels + Game.SQUARE_HEIGHT);
			if (destinationSquare.inventory.actor != null) {
				Actor actor = destinationSquare.inventory.actor;
				if (actor.hiding || actor == Game.level.player)
					continue;

				if (!Game.fullVisiblity) {
					if (destinationSquare.visibleToPlayer == false)
						continue;
				}

				destinationSquare.inventory.actor.drawActor(actor.actorPositionXInPixels, actor.actorPositionYInPixels,
						0.5f, true, actor.scaleX, actor.scaleY, 0f, actor.boundsX1, actor.boundsY1, actor.boundsX2,
						actor.boundsY2, Color.RED, true, true, actor.backwards, false, true);
			}
		}
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
