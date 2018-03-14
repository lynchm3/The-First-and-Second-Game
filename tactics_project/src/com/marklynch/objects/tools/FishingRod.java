package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;

public class FishingRod extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public float lineAnchorX = 93;
	public float lineAnchorY = 0;
	// public float lineX2;
	// public float lineY2;

	public FishingRod() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public FishingRod makeCopy(Square square, Actor owner) {
		FishingRod weapon = new FishingRod();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}

	public void drawLine(int weaponPositionXInPixels, int weaponPositionYInPixels) {

		float x1 = lineAnchorX + weaponPositionXInPixels;
		float y1 = lineAnchorY + weaponPositionYInPixels;
		float x2 = x1 + 100;
		float y2 = y1 + 100;

		LineUtils.drawLine(Color.RED, x1, y1, x2, y2, 10);
	}

}
