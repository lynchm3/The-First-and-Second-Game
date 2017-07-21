package com.marklynch.level.constructs.requirementtomeet;

import com.marklynch.objects.units.Actor;

public class StatRequirementToMeet extends RequirementToMeet {

	public enum Stat {
		STAT_HP, STAT_STRENGTH
	}

	Stat stat;
	float minimumStatLevel;

	public StatRequirementToMeet(Stat stat, float minimumStatLevel) {
		super();
		this.stat = stat;
		this.minimumStatLevel = minimumStatLevel;
	}

	@Override
	public boolean isRequirementMet(Actor actor) {

		float actorsRelevantStatLevel = 0;

		if (stat == Stat.STAT_HP) {
			actorsRelevantStatLevel = actor.remainingHealth;
		} else if (stat == Stat.STAT_STRENGTH) {
			actorsRelevantStatLevel = actor.getEffectiveStrength();
		}

		if (actorsRelevantStatLevel >= minimumStatLevel)
			return true;
		else
			return false;
	}

	@Override
	public String getText() {
		if (stat == Stat.STAT_HP) {
			return "[Requires " + minimumStatLevel + " HP]";
		} else if (stat == Stat.STAT_STRENGTH) {
			return "[Requires " + minimumStatLevel + " STRENGTH]";
		}
		return "";
	}

}
