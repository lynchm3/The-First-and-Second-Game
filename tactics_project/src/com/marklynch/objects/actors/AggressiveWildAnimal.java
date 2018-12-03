package com.marklynch.objects.actors;

import java.util.ArrayList;

import com.marklynch.ai.routines.AIRoutineForWildAnimal;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AggressiveWildAnimal extends WildAnimal {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public AggressiveWildAnimal() {
		super();
		thoughtsOnPlayer = -100;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForWildAnimal(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public AggressiveWildAnimal makeCopy(String name, Square square, Faction faction, GameObject bed,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {

		AggressiveWildAnimal actor = new AggressiveWildAnimal();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, 0, mustHaves, mightHaves, area);

		return actor;
	}

}
