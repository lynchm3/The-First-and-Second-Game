package com.marklynch.tactics.objects.unit;

import com.marklynch.tactics.objects.level.Square;

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
