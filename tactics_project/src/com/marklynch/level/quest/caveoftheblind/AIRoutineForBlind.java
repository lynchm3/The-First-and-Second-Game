package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.actions.ActionTakeBite;
import com.marklynch.objects.weapons.Bell;

public class AIRoutineForBlind extends AIRoutine {

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	Blind blind;
	Square targetSquare = null;
	Sound bellSound = null;
	MeatChunk meatChunk = null;

	boolean hangry = false;

	public AIRoutineForBlind(Blind blind) {
		super(blind);
		this.blind = blind;
	}

	@Override
	public void update() {

		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.expressionImageTexture = null;
		MeatChunk tempMeatChunk = (MeatChunk) AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(MeatChunk.class, 3,
				true, false, true, false);
		if (tempMeatChunk != null) {
			this.meatChunk = tempMeatChunk;
			bellSound = null;
			blind.locationsToSearch.clear();
			targetSquare = null;
		}

		if (meatChunk != null) {
			this.blind.activityDescription = "Eating!";
			AIRoutineUtils.moveTowardsSquareToBeAdjacent(meatChunk.squareGameObjectIsOn);
			if (blind.straightLineDistanceTo(meatChunk.squareGameObjectIsOn) <= 1) {
				new ActionTakeBite(blind, meatChunk).perform();
				hangry = false;

			}
			return;
		}

		if (meatChunk == null) {
			Sound tempBellSound = getSoundFromSourceType(Bell.class);
			if (tempBellSound != null) {
				bellSound = tempBellSound;
				blind.locationsToSearch.clear();
				targetSquare = null;
			}
		}

		if (bellSound != null) {
			this.blind.activityDescription = "Dinner time!";
			AIRoutineUtils.moveTowardsSquareToBeAdjacent(bellSound.sourceSquare);
			if (blind.straightLineDistanceTo(bellSound.sourceSquare) <= 1) {
				this.blind.structureSection = this.blind.squareGameObjectIsOn.structureSectionSquareIsIn;
				bellSound = null;
				this.blind.activityDescription = "Hangry";
				hangry = true;

			}
			return;
		}

		createSearchLocationsBasedOnSounds();
		createSearchLocationsBasedOnVisibleAttackers();

		if (runFightRoutine())
			return;
		if (runSearchRoutine())
			return;

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader()) {
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

		// Defer to quest
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return;
			}
		}

		// Move around room
		if (targetSquare != null) {
			AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			if (blind.squareGameObjectIsOn == targetSquare || blind.getPathTo(targetSquare) == null)
				targetSquare = null;
		} else if (blind.squareGameObjectIsOn.structureSectionSquareIsIn != blind.structureSection) {
			if (blind.structureSection == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(5, true);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			} else {
				targetSquare = AIRoutineUtils.getRandomSquareInStructureSection(blind.structureSection);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			}
		} else {
			if (Math.random() < 0.05) {
				if (blind.structureSection == null) {
					targetSquare = AIRoutineUtils.getRandomSquare(5, true);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				} else {
					targetSquare = AIRoutineUtils.getRandomSquareInStructureSection(blind.structureSection);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				}

			}
		}

		if (hangry)
			this.blind.activityDescription = "Hangry";
	}
}
