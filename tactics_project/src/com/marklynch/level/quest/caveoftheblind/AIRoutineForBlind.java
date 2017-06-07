package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import javax.tools.Tool;

import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.ThoughtBubbles;
import com.marklynch.objects.actions.ActionScream;
import com.marklynch.objects.actions.ActionTakeBite;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.objects.weapons.Weapon;

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
	Square originalMeatChunkSquare = null;

	boolean hangry = false;
	int timeSinceEating = Integer.MAX_VALUE;

	int failedToGetPathToBellCount = 0;
	int failedToGetPathToFoodCount = 0;

	public AIRoutineForBlind(Blind blind) {
		super(blind);
		this.blind = blind;
		aiType = AI_TYPE.HOSTILE;
	}

	@Override
	public void update() {

		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.thoughtBubbleImageTexture = null;

		// hungry?
		if (timeSinceEating > 50) {

			// find a meatchunk within smelling distance
			MeatChunk tempMeatChunk = (MeatChunk) AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(MeatChunk.class, 3,
					true, false, true, false);
			if (tempMeatChunk != null) {
				this.meatChunk = tempMeatChunk;
				this.originalMeatChunkSquare = this.meatChunk.squareGameObjectIsOn;
				bellSound = null;
				blind.investigationsMap.clear();
				targetSquare = null;
				this.actor.thoughtBubbleImageTexture = this.meatChunk.imageTexture;
			} else if (this.meatChunk != null) { // smelt a meatchunk but
													// nooooope
				if (this.meatChunk.squareGameObjectIsOn != this.originalMeatChunkSquare) {
					if (this.blind.squareGameObjectIsOn.structureRoomSquareIsIn != null)
						this.blind.roomLivingIn = this.originalMeatChunkSquare.structureRoomSquareIsIn;
					bellSound = null;
					this.blind.activityDescription = "Hangry";
					hangry = true;
					this.meatChunk = null;
					this.originalMeatChunkSquare = null;
				}
			}

			// There's a meatchunk within smelling distance
			if (meatChunk != null) {
				this.blind.activityDescription = "Eating!";

				if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(meatChunk.squareGameObjectIsOn)) {
					failedToGetPathToFoodCount = 0;
				} else {
					failedToGetPathToFoodCount++;
					blind.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
					if (failedToGetPathToFoodCount == 10) {
						failedToGetPathToFoodCount = 0;
						if (this.blind.squareGameObjectIsOn.structureRoomSquareIsIn != null)
							this.blind.roomLivingIn = this.blind.squareGameObjectIsOn.structureRoomSquareIsIn;
						bellSound = null;
						meatChunk = null;
						this.blind.activityDescription = "Hangry";
						hangry = true;
					}
				}

				if (meatChunk != null && blind.straightLineDistanceTo(meatChunk.squareGameObjectIsOn) <= 1) {
					failedToGetPathToBellCount = 0;
					failedToGetPathToFoodCount = 0;
					new ActionTakeBite(blind, meatChunk).perform();
					timeSinceEating = 0;
					hangry = false;
					bellSound = null;
					meatChunk = null;
				}
				return;
			}

			// If there's no meatchunk maybe there's a dinner bell sound?
			if (meatChunk == null) {
				Sound tempBellSound = getSoundFromSourceType(Bell.class);
				if (tempBellSound != null) {
					bellSound = tempBellSound;
					blind.investigationsMap.clear();
					targetSquare = null;
				}
			}

			// There's a bell sound
			if (bellSound != null) {
				this.blind.activityDescription = "Dinner time!";
				this.actor.thoughtBubbleImageTexture = ThoughtBubbles.MEAT_CHUNK;
				if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(bellSound.sourceSquare)) {
					failedToGetPathToBellCount = 0;
				} else {
					failedToGetPathToBellCount++;
					blind.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
					if (failedToGetPathToBellCount == 10) {
						failedToGetPathToBellCount = 0;
						if (this.blind.squareGameObjectIsOn.structureRoomSquareIsIn != null)
							this.blind.roomLivingIn = this.blind.squareGameObjectIsOn.structureRoomSquareIsIn;
						bellSound = null;
						this.blind.activityDescription = "Hangry";
						hangry = true;
					}
				}
				if (bellSound != null && blind.straightLineDistanceTo(bellSound.sourceSquare) <= 1) {
					this.blind.activityDescription = "Hangry";
					failedToGetPathToBellCount = 0;
					if (this.blind.squareGameObjectIsOn.structureRoomSquareIsIn != null)
						this.blind.roomLivingIn = this.blind.squareGameObjectIsOn.structureRoomSquareIsIn;
					bellSound = null;
					hangry = true;
				}
				return;
			}
		} else {
			meatChunk = null;
			hangry = false;
			timeSinceEating++;
			this.actor.thoughtBubbleImageTexture = null;
		}

		addNonBlindNonGolemToAttackersList();
		createSearchLocationsBasedOnSounds(Weapon.class, BrokenGlass.class, Tool.class);

		createSearchLocationsBasedOnVisibleAttackers();

		if (runFightRoutine()) {
			// createSearchLocationsBasedOnSounds();
			new ActionScream(blind).perform();
			addNonBlindNonGolemToAttackersList();
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		// if (runCrimeReactionRoutine()) {
		// addNonBlindToAttackersList();
		// createSearchLocationsBasedOnVisibleAttackers();
		// return;
		// }

		if (runSearchRoutine()) {
			// createSearchLocationsBasedOnSounds();
			addNonBlindNonGolemToAttackersList();
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
		} else if (blind.squareGameObjectIsOn.structureRoomSquareIsIn != blind.roomLivingIn) {
			if (blind.roomLivingIn == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(5, true);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			} else {
				targetSquare = AIRoutineUtils.getRandomSquareInRoom(blind.roomLivingIn);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			}
		} else {
			if (Math.random() < 0.1) {
				if (blind.roomLivingIn == null) {
					targetSquare = AIRoutineUtils.getRandomSquare(5, true);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				} else {
					targetSquare = AIRoutineUtils.getRandomSquareInRoom(blind.roomLivingIn);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				}

			}
		}
		addNonBlindNonGolemToAttackersList();
		createSearchLocationsBasedOnVisibleAttackers();

		if (hangry)
			this.blind.activityDescription = "Hangry";
	}

	public void addNonBlindNonGolemToAttackersList() {
		ArrayList<Square> squares = this.actor.getAllSquaresWithinDistance(this.actor.sight);
		for (Square square : squares) {
			if (this.actor.canSeeSquare(square)) {
				Actor actorOnSquare = (Actor) square.inventory.getGameObjectOfClass(Actor.class);
				if (actorOnSquare != null && !(actorOnSquare instanceof Blind)
						&& !(actorOnSquare instanceof RockGolem)) {
					this.actor.addAttackerForThisAndGroupMembers(actorOnSquare);
				}
			}
		}
	}
}
