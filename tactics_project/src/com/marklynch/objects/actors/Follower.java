package com.marklynch.objects.actors;

import java.util.Comparator;

import com.marklynch.ai.routines.AIRoutineForFollower;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Follower extends Human implements Comparator<GameObject> {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public Follower() {
		super();
		type = "Follower";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForFollower(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Follower makeCopy(String name, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds,
			HOBBY[] hobbies) {

		Follower actor = new Follower();
		setInstances(actor);

		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.requiredEquipmentTemplateIds = requiredEquipmentTemplateIds;
		actor.hobbies = hobbies;

		return actor;
	}

	@Override
	public int compare(GameObject gameObject1, GameObject gameObject2) {
		return gameObject2.value - gameObject1.value;
	}

}
