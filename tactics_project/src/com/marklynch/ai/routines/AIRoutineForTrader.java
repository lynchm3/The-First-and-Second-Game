package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionWrite;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;

public class AIRoutineForTrader extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum SHOPKEEP_STATE {
		SHOPKEEPING, UPDATING_SIGN, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_SHOPKEEPING = "Shopkeeping";
	final String ACTIVITY_DESCRIPTION_UPDATING_SIGN = "Updating Shop Sign";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_RUNNING_AWAY = "Running away";

	public SHOPKEEP_STATE shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	Trader trader;
	Square targetSquare = null;

	public AIRoutineForTrader(Actor actor) {
		super(actor);
		this.trader = (Trader) actor;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		aiRoutineStart();

		if (runSleepRoutine())
			return;

		// Update wanted poster
		if (updateWantedPosterRoutine(trader.wantedPoster))
			return;

		// Crime reaction
		if (runCrimeReactionRoutine())
			return;

		// Shout for help
		if (runGetHelpRoutine())
			return;

		// Shout for help cooldown
		if (runEscapeCooldown(true))
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// Loot from ground
		if (lootFromGround())
			return;

		// Defer to quest
		if (deferToQuest())
			return;

		// Shopkeeper AI 1 - hang in shop
		if (shopkeepState == SHOPKEEP_STATE.SHOPKEEPING)

		{
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOPKEEPING;
			if (!trader.isPlayerInTheShop() && trader.getTextForSign() != null)
				shopkeepState = SHOPKEEP_STATE.UPDATING_SIGN;
			else {
				if (trader.equipped != trader.broom && trader.inventory.contains(trader.broom))
					trader.equip(trader.broom);
				if (targetSquare != null) {
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
					if (trader.squareGameObjectIsOn == targetSquare || trader.getPathTo(targetSquare) == null)
						targetSquare = null;
				} else if (trader.squareGameObjectIsOn.structureRoomSquareIsIn != trader.shopRoom) {
					targetSquare = AIRoutineUtils.getRandomSquareInRoom(trader.shopRoom);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				} else {
					if (Math.random() < 0.05) {
						targetSquare = AIRoutineUtils.getRandomSquareInRoom(trader.shopRoom);
						AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
					}
				}
			}
		}

		// Shopkeeper AI 2 - update sign
		if (shopkeepState == SHOPKEEP_STATE.UPDATING_SIGN) {
			Object[] textForSign = trader.getTextForSign();
			if (textForSign == null) {
				shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;
			} else {
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_UPDATING_SIGN;
				this.actor.thoughtBubbleImageTextureObject = trader.shopSign.imageTexture;
				if (trader.straightLineDistanceTo(trader.shopSign.squareGameObjectIsOn) < 2) {
					new ActionWrite(trader, trader.shopSign, textForSign).perform();
					shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;
				} else {
					AIRoutineUtils.moveTowards(trader.shopSign.squareGameObjectIsOn);
				}
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {

			AIRoutineUtils.goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForTrader(actor);
	}
}
