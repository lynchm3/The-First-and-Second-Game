package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class Human extends Actor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Human() {
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public Human makeCopy(String name, Square square, Faction faction, GameObject bed, int gold, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area) {
		Human actor = new Human();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);

		return actor;
	}

}
