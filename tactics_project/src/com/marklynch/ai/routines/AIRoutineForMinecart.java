package com.marklynch.ai.routines;

import com.marklynch.level.constructs.animation.primary.AnimationHandsUp;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class AIRoutineForMinecart extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

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
		actor.setPrimaryAnimation(new AnimationHandsUp(actor, 100));
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForMinecart(actor);
	}

	// 1. Pick nearest target of type WILD ANIMAL within 100 squares
	// 2. Go to target
	// 3. Kill it
	// 4. Loot it
	// 5. Pick nearest target of type SHOP
	// 6. Go to target
	// 7. Sell loot

	// CAN I MERGE THE PICK WITH THE GO TO?

	// Special cases
	// If u dont have loot, dont got to the shop, got back to step one
	// If there's no wild animal close enough go to sleep until there is
	// DOnt have somewhere to sleep
	// low health
	//

}
