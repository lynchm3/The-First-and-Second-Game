package com.marklynch.ai.routines;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.units.WildAnimal;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.MapUtil;

public class AIRoutineForHunter extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum HUNT_STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, PICK_SHOP_KEEPER, GO_TO_SHOP_KEEPER_AND_SELL_JUNK, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForHunter() {

	}

	@Override
	public void update() {

		// Check for enemies last seen locations to search
		if (Game.level.activeActor.hasAttackers()) {
			for (Actor attacker : Game.level.activeActor.getAttackers()) {
				if (Game.level.activeActor
						.straightLineDistanceTo(attacker.squareGameObjectIsOn) <= Game.level.activeActor.sight
						&& Game.level.activeActor.visibleFrom(attacker.squareGameObjectIsOn)) {
					Game.level.activeActor.locationsToSearch.put(attacker, attacker.squareGameObjectIsOn);
				}
			}
		}

		// Check for sounds to investigate
		ArrayList<Square> squaresThisCanHear = Game.level.activeActor
				.getAllSquaresWithinDistance(Game.level.activeActor.hearing);
		for (Square squareThisCanHear : squaresThisCanHear) {
			for (Sound sound : squareThisCanHear.sounds) {
				if (!Game.level.activeActor.locationsToSearch.containsValue(sound.sourceSquare)
						&& sound.sourceObject instanceof Weapon) {
					Game.level.activeActor.locationsToSearch.put(sound.sourceActor, sound.sourceSquare);
				}
			}
		}

		// 1. Fighting
		if (Game.level.activeActor.hasAttackers()) {

			Game.level.activeActor.getAttackers().sort(AIRoutineUtils.sortAttackers);

			for (Actor attacker : Game.level.activeActor.getAttackers()) {

				if (Game.level.activeActor
						.straightLineDistanceTo(attacker.squareGameObjectIsOn) <= Game.level.activeActor.sight
						&& Game.level.activeActor.visibleFrom(attacker.squareGameObjectIsOn)) {
					target = attacker;
					Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;

					// GET NEAREST ATTACKER FAILING??
					boolean attackedTarget = false;
					if (target != null) {
						attackedTarget = AIRoutineUtils.attackTarget(target);
						if (!attackedTarget)
							AIRoutineUtils.moveTowardsTargetToAttack(target);
					}
					return;
				}
			}
		}

		// Searching
		if (Game.level.activeActor.locationsToSearch.size() > 0) {

			MapUtil.sortByValue(Game.level.activeActor.locationsToSearch);

			// Game.level.activeActor.locationsToSearch.sort(AIRoutineUtils.sortLocationsToSearch);
			ArrayList<Actor> toRemove = new ArrayList<Actor>();
			ArrayList<Actor> toAdd = new ArrayList<Actor>();
			boolean moved = false;

			for (Actor actorToSearchFor : Game.level.activeActor.locationsToSearch.keySet()) {

				if (actorToSearchFor.remainingHealth > 0
						&& Game.level.activeActor.squareGameObjectIsOn.straightLineDistanceTo(
								Game.level.activeActor.locationsToSearch.get(actorToSearchFor)) > 1
						&& Game.level.activeActor
								.getPathTo(Game.level.activeActor.locationsToSearch.get(actorToSearchFor)) != null) {
					Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					AIRoutineUtils
							.moveTowardsTargetSquare(Game.level.activeActor.locationsToSearch.get(actorToSearchFor));
					moved = true;

					for (Actor attacker : Game.level.activeActor.getAttackers()) {
						if (Game.level.activeActor
								.straightLineDistanceTo(attacker.squareGameObjectIsOn) <= Game.level.activeActor.sight
								&& Game.level.activeActor.visibleFrom(attacker.squareGameObjectIsOn)) {
							// Change status to fighting if u spot an enemy
							Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
						}
					}

				} else {
					toRemove.add(actorToSearchFor);
				}
			}

			for (Actor actorsToSearchFor : toRemove) {
				Game.level.activeActor.locationsToSearch.remove(actorsToSearchFor);
			}

			for (Actor actorsToSearchFor : toAdd) {
				Game.level.activeActor.locationsToSearch.put(actorsToSearchFor, actorsToSearchFor.squareGameObjectIsOn);
			}

			if (moved)
				return;

		}

		// If not leader defer to pack
		if (Game.level.activeActor.group != null && Game.level.activeActor != Game.level.activeActor.group.getLeader())

		{
			if (Game.level.activeActor.group.update(Game.level.activeActor)) {
				return;
			}
		}

		// if group leader wait for group
		if (Game.level.activeActor.group != null
				&& Game.level.activeActor == Game.level.activeActor.group.getLeader()) {
			if (Game.level.activeActor.group.leaderNeedsToWait()) {
				Game.level.activeActor.activityDescription = "Waiting for " + Game.level.activeActor.group.name;
				return;
			}
		}

		// 1. loot dead animals
		GameObject carcass = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Carcass.class, 5f, false, false, true,
				true);
		if (carcass != null) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_SKINNING;
			boolean lootedCarcass = AIRoutineUtils.lootTarget(carcass);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(carcass);
			} else {

			}
			return;
		}

		// 1. pick up loot on ground
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(GameObject.class, 5f, true, false, true,
				false);
		if (loot != null) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			boolean pickedUpLoot = AIRoutineUtils.pickupTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(loot);
			} else {

			}
			return;
		}

		// Defer to quest
		if (Game.level.activeActor.quest != null) {
			if (Game.level.activeActor.quest.update(Game.level.activeActor)) {
				return;
			}
		}

		// Go about your business
		if (huntState == HUNT_STATE.PICK_WILD_ANIMAL)

		{
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfAttack(WildAnimal.class, 0, false, true, false, false);
			if (target == null) {
				if (Game.level.activeActor.inventory.contains(Junk.class)) {
					huntState = HUNT_STATE.PICK_SHOP_KEEPER;
				} else {
					huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
				}
			} else {
				huntState = HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK;
			}

		}

		if (huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			if (target.remainingHealth <= 0 && Game.level.activeActor.inventory.size() > 0) {
				huntState = HUNT_STATE.PICK_SHOP_KEEPER;
			} else if (target.remainingHealth <= 0 && target.inventory.size() == 0) {
				huntState = HUNT_STATE.PICK_WILD_ANIMAL;
			} else {
				boolean attackedAnimal = AIRoutineUtils.attackTarget(target);
				if (!attackedAnimal)
					AIRoutineUtils.moveTowardsTargetToAttack(target);
			}
		}

		if (huntState == HUNT_STATE.PICK_SHOP_KEEPER) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_SELLING_LOOT;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Trader.class, 0, false, true, false, false);
			if (target == null) {
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else {
				huntState = HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK;
			}
		}

		if (huntState == HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_SELLING_LOOT;

			boolean soldItems = AIRoutineUtils.sellAllToTarget(Junk.class, target);
			if (!soldItems)
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(target);
			else
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;

		}

		if (huntState == HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_GOING_TO_BED;
			// huntState = HUNT_STATE.PICK_WILD_ANIMAL;

			// CONCEPT OF BED AND SLEEPING NEXT
			if (Game.level.activeActor.bed != null) {

				if (Game.level.activeActor.squareGameObjectIsOn == Game.level.activeActor.bed.squareGameObjectIsOn) {
					huntState = HUNT_STATE.SLEEP;
				} else {
					AIRoutineUtils.moveTowardsTargetToBeOn(Game.level.activeActor.bed);
				}

				// boolean goingToSleep = AIRoutineUtils.sleep();
				// if (!goingToSleep) {
				// AIRoutineUtils.moveTowardsTargetToBeOn(Game.level.activeActor.bed);
				// } else {
				//
				// }

			} else {
				huntState = HUNT_STATE.PICK_WILD_ANIMAL;
			}

			// ALSO -

		}

		if (huntState == HUNT_STATE.SLEEP) {
			// Game.level.activeActor.activityDescription =
			// ACTIVITY_DESCRIPTION_GOING_TO_BED;
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_SLEEPING;
			// sleep();
		}
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
