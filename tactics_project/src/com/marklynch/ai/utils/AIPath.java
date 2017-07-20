package com.marklynch.ai.utils;

import java.util.Vector;

import com.marklynch.level.squares.Square;

public class AIPath {
	public Vector<Square> squares;
	public int travelCost;

	public AIPath() {
		squares = new Vector<Square>();
		travelCost = 0;

	}

	public AIPath(Vector<Square> squares, int travelCost) {
		super();
		this.squares = squares;
		this.travelCost = travelCost;
	}

}
