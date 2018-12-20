package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.actions.ActionWrite;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.templates.Templates;

public class AIRoutineForTrader extends AIRoutine {

	public Trader trader;

	public AIRoutineForTrader() {
		super();
		aiType = AI_TYPE.RUNNER;
	}

	public AIRoutineForTrader(Actor actor) {
		super(actor);
		this.trader = (Trader) actor;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.SHOPKEEPING;
		}

		if (runSleepRoutine())
			return;

		// Shout for help
		if (runGetHelpRoutine())
			return;

		// Shout for help cooldown
		if (runEscapeCooldown(true))
			return;

		// Crime reaction
		if (runCrimeReactionRoutine())
			return;

		// Search
		if (runSearchRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Search cooldown
		if (runSearchCooldown()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Loot from ground
		if (lootFromGround())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// Defer to quest
		if (deferToQuest())
			return;

		// Update wanted poster
		if (reportToGuardRoutine())
			return;

		// Pick up from lost and found
		if (pickUpFromLostAndFoundRoutine())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		// Shopkeeper AI 1 - hang in shop
		if (state == STATE.SHOPKEEPING) {
			actor.thoughtBubbleImageTextureObject = Templates.GOLD.imageTexture;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOPKEEPING;
			if (!trader.isPlayerInTheShop() && trader.textForSign != null)
				state = STATE.UPDATING_SIGN;
			else {
				if (trader.equipped != trader.broom && trader.inventory.contains(trader.broom))
					trader.equip(trader.broom);

				if (targetSquare != null && targetSquare.inventory.canShareSquare == false)
					targetSquare = null;
				if (targetSquare != null) {
					boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
					if (trader.squareGameObjectIsOn == targetSquare || !moved)
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
		if (state == STATE.UPDATING_SIGN) {
			if (trader.textForSign == null) {
				state = STATE.SHOPKEEPING;
			} else {
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_UPDATING_SIGN;
				this.actor.thoughtBubbleImageTextureObject = trader.shopSign.imageTexture;
				if (trader.straightLineDistanceTo(trader.shopSign.squareGameObjectIsOn) < 2) {
					new ActionWrite(trader, trader.shopSign, trader.textForSign).perform();
					trader.textForSign = null;
					state = STATE.SHOPKEEPING;
				} else {
					AIRoutineUtils.moveTowards(trader.shopSign.squareGameObjectIsOn);
				}
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			actor.thoughtBubbleImageTextureObject = Templates.BED.imageTexture;

			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForTrader(actor);
	}
}
