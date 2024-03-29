package com.marklynch.ai.routines;

import com.marklynch.actions.ActionFishBeingFished;
import com.marklynch.actions.ActionFishSwim;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Fish;

public class AIRoutineForFish extends AIRoutine {

	public AIRoutineForFish() {
		super();
		aiType = AI_TYPE.RUNNER;
	}

	public AIRoutineForFish(Actor actor) {
		super(actor);
		if (actor.area != null) {
			keepInBounds = true;
			this.areaBounds.add(actor.area);
		}
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		if (actor.squareGameObjectIsOn == null)
			return;

		state = STATE.SWIMMING;

		aiRoutineStart();

		if (runSleepRoutine())
			return;

		if (state == STATE.SWIMMING) {
			if (actor.beingFishedBy != null) {
				new ActionFishBeingFished((Fish) actor).perform();
			} else {
				new ActionFishSwim((Fish) actor).perform();
			}
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForFish(actor);
	}

}
