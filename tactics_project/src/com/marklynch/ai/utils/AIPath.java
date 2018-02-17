package com.marklynch.ai.utils;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;

public class AIPath {
	public ArrayList<Square> squares;
	public int travelCost;
	public boolean complete = false;
	public boolean maxedOut = false;

	public AIPath() {
		squares = new ArrayList<Square>();
		travelCost = 0;
		complete = true;

	}

	public AIPath(ArrayList<Square> squares, int travelCost, boolean complete) {
		super();
		this.squares = squares;
		this.travelCost = travelCost;
		this.complete = complete;
	}

}
