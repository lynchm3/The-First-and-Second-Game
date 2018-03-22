package com.marklynch.ai.routines;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionFishBeingFished;
import com.marklynch.objects.actions.ActionFishSwim;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;

public class AIRoutineForFish extends AIRoutine {

	GameObject target;

	final String ACTIVITY_DESCRIPTION_SWIMMING = "Swimming";

	Fish fish;

	public Square targetSquare = null;
	// hmm

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
