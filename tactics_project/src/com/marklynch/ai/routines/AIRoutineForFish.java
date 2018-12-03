package com.marklynch.ai.routines;

import com.marklynch.actions.ActionFishBeingFished;
import com.marklynch.actions.ActionFishSwim;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Fish;

public class AIRoutineForFish extends AIRoutine {

	Fish fish;

	public AIRoutineForFish(Actor actor) {
		super(actor);
		if (actor.area != null) {
			keepInBounds = true;
			this.areaBounds.add(actor.area);
		}
		this.fish = (Fish) actor;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		if (fish.squareGameObjectIsOn == null)
			return;

		state = STATE.SWIMMING;

		aiRoutineStart();

		if (runSleepRoutine())
			return;

		if (state == STATE.SWIMMING) {
			if (fish.beingFishedBy != null) {
				new ActionFishBeingFished(fish).perform();
			} else {
				new ActionFishSwim(fish).perform();
			}
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForFish(actor);
	}

}
