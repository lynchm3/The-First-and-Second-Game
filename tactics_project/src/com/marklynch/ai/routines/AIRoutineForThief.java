package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForThief extends AIRoutine {
	Square targetSquare;

	GameObject target;
	// Square squareToMoveTo;

	// enum HUNT_STATE {
	// PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK,
	// GO_TO_WILD_ANIMAL_AND_LOOT, PICK_SHOP_KEEPER,
	// GO_TO_SHOP_KEEPER_AND_SELL_JUNK, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	// };

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_WANDERING = "Wandering";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	// public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForThief(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
	}

	@Override
	public void update() {
		this.actor.aiLine = null;
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
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader())

		{
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// if group leader wait for group
		if (this.actor.group != null && this.actor == this.actor.group.getLeader()) {
			if (this.actor.group.leaderNeedsToWait()) {
				this.actor.activityDescription = "Waiting for " + this.actor.group.name;
				return;
			}
		}

		// 1. loot corpses, even if owned
		GameObject carcass = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Corpse.class, 50f, false, false, true,
				true, false, true);
		if (carcass != null) {
			this.actor.thoughtBubbleImageTexture = carcass.imageTexture;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			boolean lootedCarcass = AIRoutineUtils.lootTarget(carcass);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(carcass);
			} else {

			}
			return;
		}

		// 1. pick up loot on ground, even if owned
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Axe.class, 50f, true, false, true, false,
				false, true);
		if (loot != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			this.actor.thoughtBubbleImageTexture = loot.imageTexture;
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

		// Go about ur business... (move around randomly...)
		if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(10, true);
		}

		if (targetSquare != null) {
			Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(this.actor.getPathTo(targetSquare));
			new ActionMove(this.actor, squareToMoveTo, true).perform();
			this.actor.activityDescription = ACTIVITY_WANDERING;
			// AIRoutineUtils.moveTo(this.actor, squareToMoveTo);
			if (this.actor.squareGameObjectIsOn == targetSquare)
				targetSquare = null;
			return;
		}
	}

}
