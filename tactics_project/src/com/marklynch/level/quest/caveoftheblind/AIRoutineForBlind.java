package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.objects.units.Actor;

public class AIRoutineForBlind extends AIRoutine {

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForBlind(Actor actor) {
		super(actor);
	}

	@Override
	public void update() {

		this.actor.activityDescription = null;
		this.actor.expressionImageTexture = null;
		createSearchLocationsBasedOnSounds();
		createSearchLocationsBasedOnVisibleAttackers();
		if (runFightRoutine())
			return;
		if (runSearchRoutine())
			return;

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader()) {
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// if group leader wait for group
		if (this.actor.group != null && this.actor == this.actor.group.getLeader()) {
			if (this.actor.group.leaderNeedsToWait()) {
				this.actor.activityDescription = "Waiting for " + this.actor.group.name;
				return;
			}
		}

		// Defer to quest
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return;
			}
		}
	}
}
