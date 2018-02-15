package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Key;
import com.marklynch.objects.Storage;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.tools.Tool;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
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
	final String ACTIVITY_DESCRIPTION_THIEVING = "Thieving!";
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
		aiRoutineStart();

		if (runSleepRoutine())
			return;

		// Fight
		if (runFightRoutine())
			return;

		// No crime reaction routine coz hes a thief.... altho wat about crims
		// against him? :P

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// 1. loot corpses, even if owned
		GameObject container = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(50f, false, false, true, true, false,
				true, 0, Corpse.class, Storage.class);
		if (container != null) {
			if (container.owner != null && container.owner != actor)
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_THIEVING;
			else
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			this.actor.thoughtBubbleImageTextureObject = container.imageTexture;
			boolean lootedCarcass = AIRoutineUtils.lootTarget(container);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(container.squareGameObjectIsOn);
			} else {

			}
			return;
		}

		// 1. pick up loot on ground, even if owned, all specific stuff, no
		// stupid generic game object
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(50f, true, false, true, false, false, true,
				0, Axe.class, Bell.class, Lantern.class, Pickaxe.class, Tool.class, BodyArmor.class, Helmet.class,
				LegArmor.class, Weapon.class, ContainerForLiquids.class, Food.class, Junk.class, Key.class, Junk.class);
		if (loot != null) {
			if (loot.owner != null && loot.owner != actor)
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_THIEVING;
			else
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			this.actor.thoughtBubbleImageTextureObject = loot.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.pickupTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(loot.squareGameObjectIsOn);
			} else {

			}
			return;
		}

		// Defer to quest
		if (deferToQuest())
			return;

		// Sell items
		if (sellItems())
			return;

		// Go about ur business... (move around randomly...)
		if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(7, 10, true);
		}

		if (targetSquare != null) {
			Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(this.actor.getPathTo(targetSquare));
			if (squareToMoveTo == null) {
				targetSquare = null;
				return;
			} else {
				new ActionMove(this.actor, squareToMoveTo, true).perform();
				this.actor.activityDescription = ACTIVITY_WANDERING;
				// AIRoutineUtils.moveTo(this.actor, squareToMoveTo);
				if (this.actor.squareGameObjectIsOn == targetSquare)
					targetSquare = null;
				return;
			}
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForThief(actor);
	}

}
