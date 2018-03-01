package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.TinyNeutralWildAnimal;

public class AIRoutineForHunter extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	// final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public AIRoutineForHunter(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
		requiredEquipmentTemplateIds.add(Templates.HUNTING_BOW.templateId);
		requiredEquipmentTemplateIds.add(Templates.HUNTING_KNIFE.templateId);
	}

	@Override
	public void update() {

		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.HUNTING;
		}

		if (runSleepRoutine())
			return;

		boolean hostileInAttackers = false;
		for (GameObject attacker : actor.attackers) {
			if (!(attacker instanceof HerbivoreWildAnimal)) {
				hostileInAttackers = true;
				break;
			}
		}

		if (!hostileInAttackers) {

			// Skin carcass
			if (skinCarcass()) {
				this.actor.followersShouldFollow = true;
				return;
			}

			// Loot from carcass
			if (lootCarcass()) {
				this.actor.followersShouldFollow = true;
				return;
			}

			// Loot from ground
			if (lootFromGround()) {
				this.actor.followersShouldFollow = true;
				return;
			}
		}

		// Fight
		if (runFightRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Crime reaction
		if (runCrimeReactionRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

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

		// Defer to group leader
		if (deferToGroupLeader()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// if group leader wait for group
		// if (this.actor.group != null && this.actor ==
		// this.actor.group.getLeader()) {
		// if (this.actor.group.leaderNeedsToWait()) {
		// this.actor.activityDescription = "Waiting for " +
		// this.actor.group.name;
		// return;
		// }
		// }

		// Defer to quest
		if (deferToQuest()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Update wanted poster
		if (updateWantedPosterRoutine())
			return;

		// Update wanted poster
		if (dropOffToLostAndFoundRoutine())
			return;

		// Pick up from lost and found
		if (pickUpFromLostAndFoundRoutine())
			return;

		// Door maintenance routine
		if (runDoorRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Sell items
		if (sellItems(10)) {
			this.actor.followersShouldFollow = true;
			return;
		}

		if (replenishEquipment()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Go about your business
		if (state == STATE.HUNTING)

		{

			actor.thoughtBubbleImageTextureObject = Templates.HUNTING_BOW.imageTexture;
			this.actor.followersShouldFollow = true;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(100, false, true, false, false, false, true, 0,
					false, AggressiveWildAnimal.class, CarnivoreNeutralWildAnimal.class, HerbivoreWildAnimal.class,
					TinyNeutralWildAnimal.class, MeatChunk.class, Gold.class);
			if (target == null) {
				AIRoutineUtils.moveTowards(actor.area.centreSuqare);
				return;
			} else {
				if (target == null || target.squareGameObjectIsOn == null) {
					target = null;
				} else {
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
					if (target.remainingHealth <= 0) {
					} else {
						boolean attackedAnimal = AIRoutineUtils.attackTarget(target);
						if (!attackedAnimal) {
							AIRoutineUtils.moveTowards(target);
							return;
						}
					}
				}
			}

		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {

			actor.thoughtBubbleImageTextureObject = Templates.BED.imageTexture;

			AIRoutineUtils.goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForHunter(actor);
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
