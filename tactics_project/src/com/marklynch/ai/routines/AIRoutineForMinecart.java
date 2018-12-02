package com.marklynch.ai.routines;

import com.marklynch.level.constructs.animation.primary.AnimationHandsUp;
import com.marklynch.objects.actors.Actor;

public class AIRoutineForMinecart extends AIRoutine {

	public AIRoutineForMinecart(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;

	}

	@Override
	public void update() {

		aiRoutineStart();

		// Fight
		if (runFightRoutine(false)) {
			return;
		}

		// Hands up WOO!
		actor.setPrimaryAnimation(new AnimationHandsUp(actor, 100, null));
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForMinecart(actor);
	}

}
