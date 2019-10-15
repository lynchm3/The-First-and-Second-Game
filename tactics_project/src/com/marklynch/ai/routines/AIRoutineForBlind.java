package com.marklynch.ai.routines;

import com.marklynch.actions.ActionTakeBite;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Blind;
import com.marklynch.objects.actors.RockGolem;
import com.marklynch.objects.inanimateobjects.MeatChunk;
import com.marklynch.objects.utils.ThoughtBubbles;
import com.marklynch.utils.CopyOnWriteArrayList;

public class AIRoutineForBlind extends AIRoutine {

	public Blind blind;
	public Sound bellSound = null;
	public MeatChunk meatChunk = null;
	public Square originalMeatChunkSquare = null;

	public boolean hangry = false;
	public int timeSinceEating = Integer.MAX_VALUE;

	public int failedToGetPathToBellCount = 0;
	public int failedToGetPathToFoodCount = 0;

	public AIRoutineForBlind() {
		super();
		aiType = AI_TYPE.HOSTILE;
	}

	public AIRoutineForBlind(Actor blind) {
		super(blind);
		this.blind = (Blind) blind;
		aiType = AI_TYPE.HOSTILE;
	}

	@Override
	public void update() {

		this.actor.aiLine = null;
		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.thoughtBubbleImageTextureObject = null;

		// hungry?
		if (timeSinceEating > 50) {

			// find a meatchunk within smelling distance
			MeatChunk tempMeatChunk = (MeatChunk) AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(3, true, false,
					false, false, 0, false, true, MeatChunk.class);
			if (tempMeatChunk != null) {
				this.meatChunk = tempMeatChunk;
				this.originalMeatChunkSquare = this.meatChunk.squareGameObjectIsOn;
				bellSound = null;
				blind.investigationsMap.clear();
				targetSquare = null;
				this.actor.thoughtBubbleImageTextureObject = this.meatChunk.imageTexture;
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

				if (AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath)) {
					failedToGetPathToFoodCount = 0;
				} else {
					failedToGetPathToFoodCount++;
					blind.thoughtBubbleImageTextureObject = ThoughtBubbles.QUESTION_MARK;
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
			// Moved below vcode shodily to
			// actor.createSearchLocationsBasedOnSound
			// // If there's no meatchunk maybe there's a dinner bell sound?
			// if (meatChunk == null) {
			//
			// // for()
			// // REACTION TO BELL, NEED TO DO SOMETHING ABOUT THIS, TOOK IT
			// // OUT TEMPORARILY
			// Sound tempBellSound = getSoundFromSourceType(Bell.class);
			// if (tempBellSound != null) {
			// bellSound = tempBellSound;
			// blind.investigationsMap.clear();
			// targetSquare = null;
			// }
			// }

			// There's a bell sound
			if (bellSound != null) {
				this.blind.activityDescription = "Dinner time!";
				this.actor.thoughtBubbleImageTextureObject = ThoughtBubbles.MEAT_CHUNK;
				if (AIRoutineUtils.moveTowards(bellSound.sourceSquare)) {
					failedToGetPathToBellCount = 0;
				} else {
					failedToGetPathToBellCount++;
					blind.thoughtBubbleImageTextureObject = ThoughtBubbles.QUESTION_MARK;
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
		} else

		{
			meatChunk = null;
			hangry = false;
			timeSinceEating++;
			this.actor.thoughtBubbleImageTextureObject = null;
		}

		addNonBlindNonGolemToAttackersList();

		// createSearchLocationsBasedOnSounds(Weapon.class, BrokenGlass.class,
		// Tool.class);
		createSearchLocationsBasedOnVisibleAttackers();

		// Fight
		if (

		runFightRoutine(true))
			return;

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// Defer to quest
		if (deferToQuest())
			return;

		// Move around room
		if (targetSquare != null && targetSquare.inventory.canShareSquare == false)
			targetSquare = null;
		if (targetSquare != null) {
			boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			if (blind.squareGameObjectIsOn == targetSquare || moved == false)
				targetSquare = null;
		} else if (blind.squareGameObjectIsOn.structureRoomSquareIsIn != blind.roomLivingIn) {
			if (blind.roomLivingIn == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(0, 5, true, true, null);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			} else {
				targetSquare = AIRoutineUtils.getRandomSquareInRoom(blind.roomLivingIn);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			}
		} else {
			if (Math.random() < 0.1) {
				if (blind.roomLivingIn == null) {
					targetSquare = AIRoutineUtils.getRandomSquare(0, 5, true, true, null);
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
		CopyOnWriteArrayList<Square> squares = this.actor.getAllSquaresWithinDistance(0, this.actor.sight);
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

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForBlind(actor);
	}
}
