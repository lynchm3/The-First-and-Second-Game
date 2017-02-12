package com.marklynch.tactics.objects.level;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class Pack {

	final private static int FIGHT_LOWER_LIMIT = 2;

	public String name;
	private transient ArrayList<Actor> members;
	private transient Actor leader;
	private transient Square targetSquare;
	private transient ArrayList<Actor> attackers;

	public Pack(String name, ArrayList<Actor> members, Actor leader) {
		super();
		this.name = name;
		this.members = members;
		this.leader = leader;
		for (Actor member : members) {
			member.pack = this;
		}
		attackers = new ArrayList<Actor>();
	}

	public void addMember(Actor actor) {
		if (actor.pack != null) {
			actor.pack.removeMember(actor);
		}
		members.add(actor);
		actor.pack = this;
	}

	public void removeMember(Actor actor) {
		this.members.remove(actor);
		if (actor == this.leader)
			this.leader = null;
	}

	public void setLeader(Actor actor) {
		this.leader = actor;
		actor.pack = this;
		if (!members.contains(actor)) {
			members.add(actor);
		}
	}

	public void update() {

		// Manage attackers list
		ArrayList<Actor> attackersToRemoveFromList = new ArrayList<Actor>();
		for (Actor actor : attackers) {
			if (actor.remainingHealth <= 0) {
				attackersToRemoveFromList.add(actor);
			}
		}

		for (Actor actor : attackersToRemoveFromList) {
			attackers.remove(actor);
		}

		// Manage leader
		if (leader.remainingHealth <= 0) {
			removeMember(leader);
			if (members.size() > 0) {
				setLeader(members.get(0));
			}
		}

		// AI attack attackers
		if (this.attackers.size() != 0) {
			GameObject target = AIRoutineUtils.getNearestAttacker(attackers);
			// Game.level.activeActor.activityDescription =
			// ACTIVITY_DESCRIPTION_HUNTING;
			// if (target.remainingHealth <= 0 &&
			// Game.level.activeActor.inventory.size() > 0) {
			// huntState = HUNT_STATE.PICK_SHOP_KEEPER;
			// } else if (target.remainingHealth <= 0 && target.inventory.size()
			// == 0) {
			// huntState = HUNT_STATE.PICK_WILD_ANIMAL;
			// } else {
			boolean attackedTarget = AIRoutineUtils.attackTarget(target);
			if (!attackedTarget)
				AIRoutineUtils.moveTowardsTargetToAttack(target);
			// }
			return;

		}

		// AI move to random square
		if (Game.level.activeActor == leader) {

			if (targetSquare == null || Game.level.activeActor.paths.get(targetSquare) == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(10, true);
			}

			if (targetSquare != null) {
				Square squareToMoveTo = AIRoutineUtils
						.getSquareToMoveAlongPath(Game.level.activeActor.paths.get(targetSquare));
				AIRoutineUtils.moveTo(Game.level.activeActor, squareToMoveTo);
				if (Game.level.activeActor.squareGameObjectIsOn == targetSquare)
					targetSquare = null;
			}

		} else {
			AIRoutineUtils.moveTowardsTargetToBeAdjacent(leader);

		}

	}

	public void addAttacker(Actor actor) {
		if (!this.attackers.contains(actor)) {
			this.attackers.add(actor);
		}
	}

	public Actor getMember(int i) {
		return this.members.get(i);
	}

	public int getSize() {
		return members.size();
	}
}
