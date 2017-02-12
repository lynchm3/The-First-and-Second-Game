package com.marklynch.tactics.objects;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Pack;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class QuestSmallGame extends Quest {

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "Follow the hunters to Superwolf";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";

	// Flags
	boolean questAcceptedFromHunters;
	boolean playerAttackedHunters;
	boolean playerAttackedWolves;
	boolean huntersDead;
	boolean wolvesDead;

	// Actors
	Pack hunterPack;
	Pack wolfPack;
	Actor superWolf;
	Actor cub;

	// Items

	public QuestSmallGame(Pack hunterPack, Actor superWolf, Pack wolfPack, Actor cub) {
		super();
		this.hunterPack = hunterPack;
		this.hunterPack.quest = this;

		this.wolfPack = wolfPack;
		this.wolfPack.quest = this;

		this.superWolf = superWolf;
		this.superWolf.quest = this;

		if (cub != null) {
			this.cub = cub;
			cub.quest = this;
		}

	}

	@Override
	public void update() {
		// Set flags

		// The wolves are dead
		if (wolvesDead == false) {
			wolvesDead = true;
			for (int i = 0; i < wolfPack.size(); i++) {
				if (wolfPack.getMember(i).remainingHealth > 0) {
					wolvesDead = false;
					break;
				}
			}
		}

		// Player has attacked the wolves
		if (playerAttackedWolves == false) {
			for (int i = 0; i < wolfPack.size(); i++) {
				if (wolfPack.hasAttackers()
						&& wolfPack.getAttackers().contains(Game.level.factions.get(0).actors.get(0))) {
					playerAttackedWolves = true;
				}
			}
		}

		// The hunters are dead
		if (huntersDead == false) {
			huntersDead = true;
			for (int i = 0; i < hunterPack.size(); i++) {
				if (hunterPack.getMember(i).remainingHealth > 0) {
					huntersDead = false;
					break;
				}
			}
		}

		// Player has attacked the hunters
		if (playerAttackedHunters == false) {
			for (int i = 0; i < hunterPack.size(); i++) {
				if (hunterPack.hasAttackers()
						&& hunterPack.getAttackers().contains(Game.level.factions.get(0).actors.get(0))) {
					playerAttackedHunters = true;
				}
			}
		}

	}

	@Override
	public void update(Actor actor) {
		if (hunterPack.contains(actor)) {
			updateHunter(actor);
		}
	}

	private void updateHunter(Actor actor) {

		if (!questAcceptedFromHunters) {
			Game.level.activeActor.activityDescription = ACTIVITY_PLANNING_A_HUNT;
			if (actor == hunterPack.getLeader()) {
				AIRoutineUtils.moveTowardsTargetSquare(Game.level.squares[5][8]);
			} else {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(hunterPack.getLeader());

			}

		} else if (questAcceptedFromHunters && !wolvesDead) {

			this.questCurrentObjective = OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF;

			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;

			if (actor == hunterPack.getLeader()) {
				boolean attackedAnimal = AIRoutineUtils.attackTarget(this.superWolf);
				if (!attackedAnimal)
					AIRoutineUtils.moveTowardsTargetToAttack(this.superWolf);
			} else {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(hunterPack.getLeader());

			}
		}

	}

}
