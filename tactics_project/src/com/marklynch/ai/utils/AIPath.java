package com.marklynch.ai.utils;

import com.marklynch.level.squares.Square;
import com.marklynch.utils.CopyOnWriteArrayList;

public class AIPath {
	public CopyOnWriteArrayList<Square> squares = new CopyOnWriteArrayList<Square>(Square.class);
	public int travelCost;
	public boolean complete = false;

	public AIPath() {
		squares = new CopyOnWriteArrayList<Square>(Square.class);
		travelCost = 0;
		complete = true;

	}

	public AIPath(CopyOnWriteArrayList<Square> squares, int travelCost, boolean complete) {
		super();
		this.squares = squares;
		this.travelCost = travelCost;
		this.complete = complete;
	}

}
