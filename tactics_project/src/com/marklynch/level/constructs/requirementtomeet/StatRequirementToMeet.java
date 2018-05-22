package com.marklynch.level.constructs.requirementtomeet;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.units.Actor;

public class StatRequirementToMeet extends RequirementToMeet {

	// public enum Stat {
	// STAT_HP, STAT_STRENGTH
	// }

	HIGH_LEVEL_STATS stat;
	float minimumStatLevel;

	public StatRequirementToMeet(HIGH_LEVEL_STATS stat, float minimumStatLevel) {
		super();
		this.stat = stat;
		this.minimumStatLevel = minimumStatLevel;
	}

	@Override
	public boolean isRequirementMet(Actor actor) {

		float actorsRelevantStatLevel = 0;
		actorsRelevantStatLevel = actor.getEffectiveHighLevelStat(stat);

		if (actorsRelevantStatLevel >= minimumStatLevel)
			return true;
		else
			return false;
	}

	@Override
	public String getText() {
		if (stat == HIGH_LEVEL_STATS.STRENGTH) {
			return "[Requires " + minimumStatLevel + " STRENGTH]";
		}
		return "";
	}

}
