package com.marklynch.tactics.objects.unit;

import java.util.Vector;

import com.marklynch.tactics.objects.level.Square;

public class Path {
	public Vector<Square> squares;
	public int travelCost;

	public Path(Vector<Square> squares, int travelCost) {
		super();
		this.squares = squares;
		this.travelCost = travelCost;
	}

}
