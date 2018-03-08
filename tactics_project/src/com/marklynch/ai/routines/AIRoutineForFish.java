package com.marklynch.ai.routines;

import java.util.Random;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.actions.ActionFishSwim;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;

public class AIRoutineForFish extends AIRoutine {

	GameObject target;

	final String ACTIVITY_DESCRIPTION_SWIMMING = "Swimming";

	Fish fish;
	Square targetSquare = null;
	float changeX = 0;
	float changeY = 0;

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
			// targetSquare = AIRoutineUtils.getRandomSquare(1, 1, false,
			// false);

			Square newSquare = fish.squareGameObjectIsOn;
			float maxChange = 0.05f;

			if (changeX > maxChange || changeX < -maxChange) {
				changeX = 0;
			}
			if (new Random().nextFloat() < 0.2f) {
				changeX = new Random().nextFloat() * maxChange;
				if (new Random().nextBoolean()) {
					changeX = -changeX;
				}
			}

			if (changeY > maxChange || changeY < -maxChange) {
				changeY = 0;
			}

			if (new Random().nextFloat() < 0.2f) {
				changeY = new Random().nextFloat() * maxChange;
				if (new Random().nextBoolean()) {
					changeY = -changeY;
				}
			}

			float halfWidthRatio = (fish.width / Game.SQUARE_WIDTH) / 2f;
			float halfHeightRatio = (fish.height / Game.SQUARE_HEIGHT) / 2f;

			if (actor.drawOffsetRatioX + changeX < -halfWidthRatio) {

				Square potentialNewSquare = fish.squareGameObjectIsOn.getSquareToLeftOf();
				if (potentialNewSquare == null) {
					changeX = 0;
				} else if (!potentialNewSquare.inventory.contains(WaterBody.class)) {
					changeX = 0;
				} else {
					newSquare = potentialNewSquare;
					changeX += 1;
				}
			} else if (actor.drawOffsetRatioX + changeX >= 1 - halfWidthRatio) {
				Square potentialNewSquare = fish.squareGameObjectIsOn.getSquareToRightOf();
				if (potentialNewSquare == null) {
					changeX = 0;
				} else if (!potentialNewSquare.inventory.contains(WaterBody.class)) {
					changeX = 0;
				} else {
					newSquare = potentialNewSquare;
					changeX -= 1;
				}
			} else if (actor.drawOffsetRatioY + changeY < -halfHeightRatio) {
				Square potentialNewSquare = fish.squareGameObjectIsOn.getSquareAbove();
				if (potentialNewSquare == null) {
					changeY = 0;
				} else if (!potentialNewSquare.inventory.contains(WaterBody.class)) {
					changeY = 0;
				} else {
					newSquare = potentialNewSquare;
					changeY += 1;
				}
			} else if (actor.drawOffsetRatioY + changeY >= 1 - halfHeightRatio) {
				Square potentialNewSquare = fish.squareGameObjectIsOn.getSquareBelow();
				if (potentialNewSquare == null) {
					changeY = 0;
				} else if (!potentialNewSquare.inventory.contains(WaterBody.class)) {
					changeY = 0;
				} else {
					newSquare = potentialNewSquare;
					changeY -= 1;
				}
			}

			new ActionFishSwim(fish, newSquare, actor.drawOffsetRatioX + changeX, actor.drawOffsetRatioY + changeY)
					.perform();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForFish(actor);
	}

}