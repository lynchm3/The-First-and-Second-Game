package com.marklynch.objects.units;

import java.util.Vector;

import com.marklynch.level.squares.Square;

public class Path {
	public Vector<Square> squares;
	public int travelCost;

	public Path() {
		squares = new Vector<Square>();
		travelCost = 0;

	}

	public Path(Vector<Square> squares, int travelCost) {
		super();
		this.squares = squares;
		this.travelCost = travelCost;
	}

}
