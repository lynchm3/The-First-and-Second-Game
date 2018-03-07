package com.marklynch.ai.routines;

import java.util.Random;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionFishSwim;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;

public class AIRoutineForFish extends AIRoutine {

	GameObject target;

	final String ACTIVITY_DESCRIPTION_SWIMMING = "Swimming";

	Fish fish;
	Square targetSquare = null;
	int targetOffsetX = 0;
	int targetOffsetY = 0;

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
		state = STATE.SWIMMING;

		aiRoutineStart();

		if (runSleepRoutine())
			return;

		if (state == STATE.SWIMMING) {
			// if (targetSquare == null || targetSquare ==
			// fish.squareGameObjectIsOn
			// || !targetSquare.inventory.contains(WaterBody.class)) {
			targetSquare = AIRoutineUtils.getRandomSquare(1, 1, false, false);

			float changeX = new Random().nextFloat() * 0.1f;
			if (new Random().nextBoolean()) {
				changeX = -changeX;
			}

			float changeY = new Random().nextFloat() * 0.1f;
			if (new Random().nextBoolean()) {
				changeY = -changeY;
			}

			// targetOffsetX = new Random().nextInt((int) (Game.SQUARE_WIDTH -
			// fish.width));
			// targetOffsetY = new Random().nextInt((int) (Game.SQUARE_HEIGHT -
			// fish.height));
			// }

			System.out.println("aiRoutine targetSquare = " + targetSquare);
			System.out.println("aiRoutine targetOffsetX = " + targetOffsetX);
			System.out.println("aiRoutine targetOffsetY = " + targetOffsetY);
			new ActionFishSwim(fish, targetSquare, actor.drawOffsetRatioX + changeX, actor.drawOffsetRatioY + changeY)
					.perform();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForFish(actor);
	}

}
