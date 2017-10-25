package com.marklynch.ai.routines;

import java.util.ArrayList;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.RockGolem;

public class AIRoutineForRockGolem extends AIRoutine {

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	RockGolem rockGolem;
	Square targetSquare = null;
	Sound bellSound = null;
	MeatChunk meatChunk = null;
	Square originalMeatChunkSquare = null;

	boolean hangry = false;
	int timeSinceEating = Integer.MAX_VALUE;

	int failedToGetPathToBellCount = 0;
	int failedToGetPathToFoodCount = 0;

	public AIRoutineForRockGolem(Actor rockGolem) {
		super(rockGolem);
		this.rockGolem = (RockGolem) rockGolem;
		aiType = AI_TYPE.HOSTILE;
	}

	@Override
	public void update() {

		if (!rockGolem.awake)
			return;

		addAllToAttackersList();
		aiRoutineStart();

		// Fight
		if (runFightRoutine())
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
		if (targetSquare != null) {
			AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			if (rockGolem.squareGameObjectIsOn == targetSquare || rockGolem.getPathTo(targetSquare) == null)
				targetSquare = null;
		} else if (rockGolem.squareGameObjectIsOn.structureRoomSquareIsIn != rockGolem.roomLivingIn) {
			if (rockGolem.roomLivingIn == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(5, true);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			} else {
				targetSquare = AIRoutineUtils.getRandomSquareInRoom(rockGolem.roomLivingIn);
				AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			}
		} else {
			rockGolem.sleep();
			// if (Math.random() < 0.1) {
			// if (rockGolem.roomLivingIn == null) {
			// targetSquare = AIRoutineUtils.getRandomSquare(5, true);
			// AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			// } else {
			// targetSquare =
			// AIRoutineUtils.getRandomSquareInRoom(rockGolem.roomLivingIn);
			// AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			// }
			//
			// }
		}
		addAllToAttackersList();
		createSearchLocationsBasedOnVisibleAttackers();
	}

	public void addAllToAttackersList() {
		ArrayList<Square> squares = this.actor.getAllSquaresWithinDistance(this.actor.sight);
		for (Square square : squares) {
			if (this.actor.canSeeSquare(square)) {
				Actor actorOnSquare = (Actor) square.inventory.getGameObjectOfClass(Actor.class);
				if (actorOnSquare != this.actor && actorOnSquare != null) {
					this.actor.addAttackerForThisAndGroupMembers(actorOnSquare);
				}
			}
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForRockGolem(actor);
	}
}
