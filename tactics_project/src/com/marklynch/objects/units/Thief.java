package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class Thief extends Human {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Thief() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForThief(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Thief makeCopy(String name, Square square, Faction faction, GameObject bed, int gold, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area) {
		Thief actor = new Thief();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);

		return actor;
	}

	@Override
	public void addWitnessedCrime(Crime crime) {
		// TODO Auto-generated method stub
		if (crime.visctim == this)
			super.addWitnessedCrime(crime);
	}

}
