package com.marklynch.level.constructs.journal;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;

import com.marklynch.utils.Texture;

public class Objective {// implements DestructionListener {

	public String text;
	public GameObject gameObject;
	public Square square;
	public boolean showMarker = true;
	public float width;
	public CopyOnWriteArrayList<Link> links;
	public Texture texture;
	// public boolean objectiveDestroyed = false;
	public boolean objectiveDestroyedAndWitnessed = false;

	public Objective(String text, GameObject gameObject, Square square, Texture texture) {
		super();
		this.gameObject = gameObject;
		this.text = text;
		this.square = square;
		this.texture = texture;
		width = TextUtils.getDimensions(Integer.MAX_VALUE, this)[0];
		links = TextUtils.getLinks(true, this);
	}

	// @Override
	// public void onDestroy() {
	// objectiveDestroyed = true;
	// }

}
