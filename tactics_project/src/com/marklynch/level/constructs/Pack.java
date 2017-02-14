package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.units.Actor;

public class Pack {
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";

	final private static int FIGHT_LOWER_LIMIT = 2;

	public String name;
	private transient ArrayList<Actor> members;
	private transient Actor leader;
	private transient Square targetSquare;
	private transient ArrayList<Actor> attackers;
	public transient Quest quest;

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

	public void update(Actor actor) {

		// Manage attackers list
		ArrayList<Actor> attackersToRemoveFromList = new ArrayList<Actor>();
		for (Actor attacker : attackers) {
			if (attacker.remainingHealth <= 0) {
				attackersToRemoveFromList.add(attacker);
			}
		}

		for (Actor attackerToRemoveFromList : attackersToRemoveFromList) {
			attackers.remove(attackerToRemoveFromList);
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
			boolean attackedTarget = AIRoutineUtils.attackTarget(target);
			actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
			if (!attackedTarget)
				AIRoutineUtils.moveTowardsTargetToAttack(target);
			return;

		}

		// Defer to quest
		if (quest != null) {
			quest.update(actor);
			return;
		}

		// AI move to random square
		if (actor == leader) {

			if (targetSquare == null || actor.paths.get(targetSquare) == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(10, true);
			}

			if (targetSquare != null) {
				Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(actor.paths.get(targetSquare));
				new ActionMove(actor, squareToMoveTo).perform();
				if (actor.squareGameObjectIsOn == targetSquare)
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

	public int size() {
		return members.size();
	}

	public boolean contains(Actor actor) {
		return members.contains(actor);
	}

	public boolean hasAttackers() {
		return attackers.size() > 0;
	}

	public ArrayList<Actor> getAttackers() {
		return attackers;
	}

	public Actor getLeader() {
		return leader;
	}

	public ArrayList<Actor> getMembers() {
		return members;
	}
}
