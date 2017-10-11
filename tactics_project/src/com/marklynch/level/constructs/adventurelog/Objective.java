package com.marklynch.level.constructs.adventurelog;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class Objective {

	public GameObject gameObject;
	public String text;
	public Square square;
	public boolean showMarker = true;

	public Objective(String text, GameObject gameObject, Square square) {
		super();
		this.gameObject = gameObject;
		this.text = text;
		this.square = square;
	}

}
