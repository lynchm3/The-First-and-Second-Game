package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.RockGolem;
import com.marklynch.utils.CopyOnWriteArrayList;

public class AIRoutineForRockGolem extends AIRoutine {

	public RockGolem rockGolem;

	public AIRoutineForRockGolem() {
		super();
		aiType = AI_TYPE.HOSTILE;
	}

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
		if (runFightRoutine(true))
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
			if (rockGolem.squareGameObjectIsOn == targetSquare || !moved)
				targetSquare = null;
		} else if (rockGolem.squareGameObjectIsOn.structureRoomSquareIsIn != rockGolem.roomLivingIn) {
			if (rockGolem.roomLivingIn == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(0, 5, true, true, null);
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
		CopyOnWriteArrayList<Square> squares = this.actor.getAllSquaresWithinDistance(0, this.actor.sight);
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
