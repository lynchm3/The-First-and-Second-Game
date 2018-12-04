package com.marklynch.level.constructs.requirementtomeet;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.actors.Actor;

public class RequirementToMeet {
	Power power;
	HIGH_LEVEL_STATS stat;
	float minimumStatLevel;

	public RequirementToMeet() {
	}

	public RequirementToMeet(HIGH_LEVEL_STATS stat, float minimumStatLevel) {
		super();
		this.stat = stat;
		this.minimumStatLevel = minimumStatLevel;
	}

	public RequirementToMeet(Power power) {
		super();
		this.power = power;
	}

	public boolean isRequirementMet(Actor actor) {

		if (stat != null) {

			float actorsRelevantStatLevel = 0;
			actorsRelevantStatLevel = actor.getEffectiveHighLevelStat(stat);

			if (actorsRelevantStatLevel >= minimumStatLevel)
				return true;
			else
				return false;
		} else if (power != null) {
			return true;
		} else {
			return true;
		}
	}

	public String getText() {
		if (stat == HIGH_LEVEL_STATS.STRENGTH) {
			return "[Requires " + minimumStatLevel + " STRENGTH]";
		}
		return "";
	}

}
