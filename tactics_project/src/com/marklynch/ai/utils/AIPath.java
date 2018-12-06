package com.marklynch.ai.utils;

import com.marklynch.level.squares.Square;
import com.marklynch.utils.ArrayList;

public class AIPath {
	public ArrayList<Square> squares = new ArrayList<Square>(Square.class);
	public int travelCost;
	public boolean complete = false;

	public AIPath() {
		squares = new ArrayList<Square>(Square.class);
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
