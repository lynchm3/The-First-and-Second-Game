package com.marklynch.level.constructs.adventurelog;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;

public class Objective {

	public GameObject gameObject;
	public String text;
	public Square square;
	public boolean showMarker = true;
	public ArrayList<Link> links;

	public Objective(String text, GameObject gameObject, Square square) {
		super();
		this.gameObject = gameObject;
		this.text = text;
		this.square = square;
		links = TextUtils.getLinks(true, gameObject);
	}

}
