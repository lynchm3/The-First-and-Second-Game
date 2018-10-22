package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Vein extends Wall {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public boolean infinite = false;

	public Junk oreTemplate;

	public double dropChance;

	public Vein() {
		super();
		type = "Vein";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	// , boolean infinite, Junk oreTemplate, double dropChance) {
	public Vein makeCopy(Square square, Actor owner, boolean infinite, Junk oreTemplate, double dropChance) {
		Vein vein = new Vein();
		setInstances(vein);
		vein.infinite = infinite;
		vein.oreTemplate = oreTemplate;
		vein.dropChance = dropChance;
		super.setAttributesForCopy(vein, square, owner);
		// if (vein.squareGameObjectIsOn != null) {
		// vein.drawX1 = (int) (vein.squareGameObjectIsOn.xInGridPixels +
		// vein.drawOffsetRatioX);
		// vein.drawX2 = (int) (vein.drawX1 + vein.width);
		// vein.drawY1 = (int) (vein.squareGameObjectIsOn.yInGridPixels +
		// vein.drawOffsetRatioY);
		// vein.drawY2 = (int) (vein.drawY1 + vein.height);
		// }
		vein.initWall(16f);
		return vein;
	}

}
