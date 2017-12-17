package com.marklynch.level.constructs.journal;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.GameObject.DestructionListener;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Texture;

public class Objective implements DestructionListener {

	public GameObject gameObject;
	public String text;
	public Square square;
	public boolean showMarker = true;
	public float width;
	public ArrayList<Link> links;
	public Texture texture;

	public Objective(String text, GameObject gameObject, Square square) {
		super();
		this.gameObject = gameObject;
		this.text = text;
		this.square = square;
		if (gameObject != null) {
			this.texture = gameObject.imageTexture;
			gameObject.addDestructionListener(this);
		}
		width = TextUtils.getDimensions(Integer.MAX_VALUE, this)[0];
		links = TextUtils.getLinks(true, this);
	}

	@Override
	public void onDestroy() {
		gameObject = null;
	}

}
