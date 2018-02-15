package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class HerbivoreWildAnimal extends WildAnimal {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public HerbivoreWildAnimal() {
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
		aiRoutine = new AIRoutineForHerbivoreWildAnimal(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public HerbivoreWildAnimal makeCopy(String name, Square square, Faction faction, GameObject bed,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {

		HerbivoreWildAnimal actor = new HerbivoreWildAnimal();
		setInstances(actor);
		actor.name = name;
		actor.squareGameObjectIsOn = square;
		actor.faction = faction;
		actor.area = area;

		actor.title = title;
		actor.level = level;
		actor.totalHealth = actor.remainingHealth = totalHealth;
		actor.strength = strength;
		actor.dexterity = dexterity;
		actor.intelligence = intelligence;
		actor.endurance = endurance;
		actor.imageTexturePath = imageTexturePath;
		// actor.squareGameObjectIsOn = null;
		actor.travelDistance = travelDistance;
		actor.sight = sight;
		// actor.bed = null;
		// actor.inventory = new Inventory();
		actor.widthRatio = widthRatio;
		actor.heightRatio = heightRatio;
		actor.drawOffsetRatioX = drawOffsetRatioX;
		actor.drawOffsetRatioY = drawOffsetRatioY;
		actor.soundWhenHit = soundWhenHit;
		actor.soundWhenHitting = soundWhenHitting;
		// actor.soundDampening = 1f;
		// actor.stackable = false;
		actor.weight = weight;
		actor.handAnchorX = handAnchorX;
		actor.handAnchorY = handAnchorY;
		actor.headAnchorX = headAnchorX;
		actor.headAnchorY = headAnchorY;
		actor.bodyAnchorX = bodyAnchorX;
		actor.bodyAnchorY = bodyAnchorY;
		actor.legsAnchorX = legsAnchorX;
		actor.legsAnchorY = legsAnchorY;
		actor.canOpenDoors = canOpenDoors;
		actor.canEquipWeapons = canEquipWeapons;
		// gold
		actor.templateId = templateId;
		actor.aiRoutine = aiRoutine.getInstance(actor);

		actor.init(0, mustHaves, mightHaves);
		return actor;
	}

}
