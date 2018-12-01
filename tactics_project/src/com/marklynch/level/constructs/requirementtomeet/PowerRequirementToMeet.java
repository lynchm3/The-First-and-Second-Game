package com.marklynch.level.constructs.requirementtomeet;

import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.actors.Actor;

public class PowerRequirementToMeet extends RequirementToMeet {

	Power power;

	@Override
	public boolean isRequirementMet(Actor actor) {
		// if(actor.skills.contains(skill))
		// return true
		// else
		return false;
	}

	@Override
	public String getText() {
		return "";
	}

}
