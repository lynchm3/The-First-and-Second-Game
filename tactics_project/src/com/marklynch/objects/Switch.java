package com.marklynch.objects;

import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;

public abstract class Switch extends GameObject {

	public String actionName;
	public String actionVerb;
	public RequirementToMeet[] requirementsToMeet;

	public Switch() {
		super();
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	public abstract void use();

}
