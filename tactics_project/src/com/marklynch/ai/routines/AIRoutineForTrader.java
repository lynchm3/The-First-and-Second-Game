package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionWrite;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForTrader extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum SHOPKEEP_STATE {
		SHOPKEEPING, UPDATING_SIGN, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SHOPKEEPING = "Shopkeeping";
	final String ACTIVITY_DESCRIPTION_UPDATING_SIGN = "Updating sign";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_RUNNING_AWAY = "Running away";

	public SHOPKEEP_STATE shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	Trader trader;
	Square targetSquare = null;

	public AIRoutineForTrader(Trader actor) {
		super(actor);
		this.trader = actor;
	}

	@Override
	public void update() {
		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.thoughtBubbleImageTexture = null;
		createSearchLocationsBasedOnSounds(Weapon.class);
		createSearchLocationsBasedOnVisibleAttackers();
		if (runFightRoutine()) {
			// createSearchLocationsBasedOnSounds();
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (runSearchRoutine()) {
			// createSearchLocationsBasedOnSounds();
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (searchCooldown > 0) {
			runSearchCooldown();
			searchCooldown--;
			return;
		}

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader()) {
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// 1. pick up loot on ground
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(GameObject.class, 5f, true, false, true,
				false);
		if (loot != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			boolean pickedUpLoot = AIRoutineUtils.pickupTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(loot);
			} else {

			}
			return;
		}

		// Defer to quest
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return;
			}
		}

		// Go about your business
		if (shopkeepState == SHOPKEEP_STATE.SHOPKEEPING)

		{
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOPKEEPING;
			if (!trader.isPlayerInTheShop() && trader.getTextForSign() != null)
				shopkeepState = SHOPKEEP_STATE.UPDATING_SIGN;
			else {
				// Equip ur broom!!!
				if (trader.equipped != trader.broom)
					trader.equipped = trader.broom;
				// if not in building move in to the building
				if (targetSquare != null) {
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
					if (trader.squareGameObjectIsOn == targetSquare || trader.getPathTo(targetSquare) == null)
						targetSquare = null;
				} else if (trader.squareGameObjectIsOn.structureSquareIsIn != trader.shop) {
					targetSquare = AIRoutineUtils.getRandomSquareInRoom(trader.room);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				} else {
					if (Math.random() < 0.05) {
						targetSquare = AIRoutineUtils.getRandomSquareInRoom(trader.room);
						AIRoutineUtils.moveTowardsTargetSquare(targetSquare);

					}
				}
				// AIRoutineUtils.moveTowardsTargetSquare(Game.level.squares[7][1]);
			}

			// if u need to update the sign, do it - make sign info into an
			// array of objects, then u can check ur best shit is on the list :)
			// else, pick a random spot in the shop and move to it
		}

		if (shopkeepState == SHOPKEEP_STATE.UPDATING_SIGN) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_UPDATING_SIGN;
			// Go to the sign
			// New action to set text of the sign
			// Donezo

			AIRoutineUtils.moveTowardsTargetToBeAdjacent(trader.shopSign);
			if (trader.straightLineDistanceTo(trader.shopSign.squareGameObjectIsOn) < 2) {
				new ActionWrite(trader, trader.shopSign, trader.getTextForSign()).perform();
				shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;
			}
		}
	}

}
