package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square targetSquare;

	final static String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";

	public AIRoutineForWildAnimal(Actor actor) {
		super(actor);
		aiType = AI_TYPE.ANIMAL;
	}

	@Override
	public void update() {

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

		if (runCrimeReactionRoutine()) {
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (runCrimeReactionRoutine()) {
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
			return;
		}

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader()) {
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// Defer to quest
		if (this.actor.quest != null) {
			this.actor.quest.update(this.actor);
			return;
		}

		// Go about ur business...
		if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(10, true);
		}

		if (targetSquare != null) {
			Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(this.actor.getPathTo(targetSquare));
			new ActionMove(this.actor, squareToMoveTo, true).perform();
			// AIRoutineUtils.moveTo(this.actor, squareToMoveTo);
			if (this.actor.squareGameObjectIsOn == targetSquare)
				targetSquare = null;
		}

	}

}
