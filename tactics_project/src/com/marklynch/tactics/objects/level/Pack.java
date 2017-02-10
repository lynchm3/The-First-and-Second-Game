package com.marklynch.tactics.objects.level;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class Pack {

	public String name;
	private transient ArrayList<Actor> members;
	private transient Actor leader;
	private transient Square targetSquare;

	public Pack(String name, ArrayList<Actor> members, Actor leader) {
		super();
		this.name = name;
		this.members = members;
		this.leader = leader;
		for (Actor member : members) {
			member.pack = this;
		}
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

		if (leader.remainingHealth <= 0) {
			removeMember(leader);
			if (members.size() > 0) {
				setLeader(members.get(0));
			}
		}

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
}
