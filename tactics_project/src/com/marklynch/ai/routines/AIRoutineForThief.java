package com.marklynch.ai.routines;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Junk;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.Storage;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionTakeItems;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Armor;
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

	int theftCooldown = 0;

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
		GameObject container = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(18f, false, false, true, true, false,
				true, 0, false, Corpse.class, Storage.class);
		if (container != null) {
			if (container.owner != null && container.owner != actor)
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_THIEVING;
			else
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			this.actor.thoughtBubbleImageTextureObject = container.imageTexture;
			boolean lootedCarcass = AIRoutineUtils.lootTarget(container);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
			} else {

			}
			return;
		}

		// 1. pick up loot on ground, even if owned, all specific stuff, no
		// stupid generic game object
		if (theftCooldown <= 0) {

			GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(10f, true, false, true, false, false,
					true, 0, false, Weapon.class, Armor.class, Food.class, Junk.class, MeatChunk.class, Gold.class);
			if (loot != null) {
				if (loot.owner != null && loot.owner != actor)
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_THIEVING;
				else
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
				this.actor.thoughtBubbleImageTextureObject = loot.imageTexture;

				int weaponDistance = Game.level.activeActor.straightLineDistanceTo(loot.squareGameObjectIsOn);

				if (weaponDistance > 1) {
					AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
					return;
				} else {
					ActionTakeItems actionTake = new ActionTakeItems(Game.level.activeActor, loot.squareGameObjectIsOn,
							loot);
					if (actionTake.legal) {
						actionTake.perform();
						return;
					} else {
						if (canSeeActor()) {
							theftCooldown = 100;
						} else {
							actionTake.perform();
							return;
						}
					}
				}
			}
		} else {
			theftCooldown--;
		}

		// Defer to quest
		if (deferToQuest())
			return;

		// Sell items
		if (sellItems(10))
			return;

		// Go about ur business... (move around randomly...)
		if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(7, 10, true);
		}

		if (targetSquare != null) {
			Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(this.actor.getPathTo(targetSquare));
			if (squareToMoveTo == null) {
				targetSquare = null;

				this.actor.thoughtBubbleImageTextureObject = getGlobalImage("music.png", false);
				return;
			} else {
				new ActionMove(this.actor, squareToMoveTo, true).perform();
				this.actor.activityDescription = ACTIVITY_WANDERING;
				this.actor.thoughtBubbleImageTextureObject = getGlobalImage("music.png", false);
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
