package com.marklynch.objects.actors;

import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.armor.BodyArmor;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class Thief extends Human {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Thief() {
		super();
		type = "Thief";
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
			GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds, HOBBY[] hobbies) {
		Thief actor = new Thief();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.requiredEquipmentTemplateIds = requiredEquipmentTemplateIds;
		actor.hobbies = hobbies;

		BodyArmor robe = Templates.ROBE.makeCopy(null, actor);
		actor.inventory.add(robe);
		actor.bodyArmor = robe;

		LegArmor pants = Templates.PANTS.makeCopy(null, actor);
		actor.inventory.add(pants);
		actor.legArmor = pants;

		return actor;
	}

	@Override
	public void addWitnessedCrime(Crime crime) {
		// TODO Auto-generated method stub
		if (crime.victim == this)
			super.addWitnessedCrime(crime);
	}

}
