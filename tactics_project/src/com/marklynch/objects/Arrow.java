package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;

public class Arrow extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public Texture textureLoaded;
	public Texture textureEmbedded;

	public Arrow() {
		super();

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Arrow makeCopy(Square square, Actor owner) {
		Arrow arrow = new Arrow();
		setInstances(arrow);
		super.setAttributesForCopy(arrow, square, owner);
		arrow.textureEmbedded = this.textureEmbedded;
		arrow.textureLoaded = this.textureLoaded;
		return arrow;
	}
}
