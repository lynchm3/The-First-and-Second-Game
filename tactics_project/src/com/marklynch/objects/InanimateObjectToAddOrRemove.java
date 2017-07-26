package com.marklynch.objects;

import com.marklynch.level.squares.Square;

public class InanimateObjectToAddOrRemove {
	public GameObject gameObject;

	public InanimateObjectToAddOrRemove(GameObject gameObject, Square square) {
		super();
		this.gameObject = gameObject;
		this.square = square;
	}

	public Square square;

}
