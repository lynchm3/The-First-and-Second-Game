package com.marklynch.level.constructs.requirementtomeet;

import com.marklynch.objects.units.Actor;

public class SkillRequirementToMeet extends RequirementToMeet {

	// Skill skill;
	// int skillLevel; //If i go with skill levels...

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
