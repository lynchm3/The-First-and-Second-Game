package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.LegArmor;

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
			GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds, HOBBY[] hobbies) {
		Human actor = new Human();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.requiredEquipmentTemplateIds = requiredEquipmentTemplateIds;
		actor.hobbies = hobbies;
		
		if(bodyArmor != null)
		{			
			actor.bodyArmor = bodyArmor.makeCopy(null, null);
			actor.inventory.add(actor.bodyArmor);	
		}
		
		if(legArmor != null)
		{
			actor.legArmor = legArmor.makeCopy(null, null);
			actor.inventory.add(actor.legArmor);
		}
		

		return actor;
	}

}
