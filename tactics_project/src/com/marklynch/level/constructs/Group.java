package com.marklynch.level.constructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class Group {
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";

	final private static int FIGHT_LOWER_LIMIT = 2;

	public String name;
	protected transient ArrayList<Actor> members;
	protected transient Actor leader;
	protected transient Square targetSquare;
	protected transient ArrayList<Actor> attackers;
	public transient Quest quest;
	public HashMap<Actor, Square> targetSquaresMap = new HashMap<Actor, Square>();
	public ArrayList<Square> targetSquares = new ArrayList<Square>();

	public Group(String name, ArrayList<Actor> members, Actor leader) {
		super();
		this.name = name;
		this.members = members;
		this.leader = leader;
		for (Actor member : members) {
			member.group = this;
		}
		attackers = new ArrayList<Actor>();
	}

	public void addMember(Actor actor) {
		if (actor.group != null) {
			actor.group.removeMember(actor);
		}
		members.add(actor);
		actor.group = this;
	}

	public void removeMember(Actor actor) {
		this.members.remove(actor);
		if (actor == this.leader)
			this.leader = null;
	}

	public void setLeader(Actor actor) {
		this.leader = actor;
		actor.group = this;
		if (!members.contains(actor)) {
			members.add(actor);
		}
	}

	public boolean update(Actor actor) {

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

		// AI move towards leader
		if (actor == leader) {
			return false;
		} else {
			if (actor.straightLineDistanceTo(leader.squareGameObjectIsOn) > members.size() / 2) {
				// Pick a random square within 2 squares
				Vector<Square> possibleSquares = new Vector<Square>();
				Vector<Square> squaresOneSquareAway = leader.getAllSquaresAtDistance(1);
				for (Square square : squaresOneSquareAway) {
					if (square.inventory.canShareSquare() && square.building == leader.squareGameObjectIsOn.building
							&& !targetSquares.contains(square)) {
						possibleSquares.add(square);
					}
				}
				Vector<Square> squaresTwoSquaresAway = leader.getAllSquaresAtDistance(2);
				for (Square square : squaresTwoSquaresAway) {
					if (square.inventory.canShareSquare() && square.building == leader.squareGameObjectIsOn.building
							&& !targetSquares.contains(square)) {
						possibleSquares.add(square);
					}
				}

				Square targetSquare = null;
				if (possibleSquares.size() > 0) {
					int index = (int) (Math.random() * possibleSquares.size());
					if (index == possibleSquares.size())
						index--;
					targetSquare = possibleSquares.get(index);
				}

				if (targetSquare != null) {
					targetSquares.remove(targetSquaresMap.get(actor));
					targetSquaresMap.put(actor, targetSquare);
					targetSquares.add(targetSquare);
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				}

				// AIRoutineUtils.moveTowardsTargetToBeAdjacent(leader);
			} else {

				Square targetSquare = targetSquaresMap.get(actor);
				if (targetSquare != null)
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);

			}
			// AIRoutineUtils.moveTowardsTargetToBeAdjacent(leader);
			actor.activityDescription = leader.activityDescription;
			return true;
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
