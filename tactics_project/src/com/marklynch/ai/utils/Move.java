package com.marklynch.ai.utils;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Move {

	public Actor actor;
	public Square squareMovedFrom;
	public Square squareMovedTo;
	public int travelCost;

	public Move(Actor actor, Square squareMovedFrom, Square squareMovedTo, int travelCost) {
		super();
		this.actor = actor;
		this.squareMovedFrom = squareMovedFrom;
		this.squareMovedTo = squareMovedTo;
		this.travelCost = travelCost;
	}
}
