package com.marklynch.level.constructs;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class GroupOfActors implements Idable {
	public Long id;

	public String name;
	protected transient CopyOnWriteArrayList<Actor> members = new CopyOnWriteArrayList<Actor>(Actor.class);
	public transient Actor leader;
	protected transient CopyOnWriteArrayList<GameObject> attackers = new CopyOnWriteArrayList<GameObject>(GameObject.class);
	public transient Quest quest;
	public ConcurrentHashMap<Actor, Square> targetSquaresMap = new ConcurrentHashMap<Actor, Square>();
	public CopyOnWriteArrayList<Square> targetSquares = new CopyOnWriteArrayList<Square>(Square.class);

	public GroupOfActors(String name, Actor... members) {
		super();
		id = Level.generateNewId(this);
		this.name = name;
		this.members = new CopyOnWriteArrayList<Actor>(Actor.class, Arrays.asList(members));
		this.leader = this.members.get(0);
		for (Actor member : members) {
			member.groupOfActors = this;
		}
		attackers = new CopyOnWriteArrayList<GameObject>(GameObject.class);
	}

	public void addMember(Actor actor) {
		if (actor.groupOfActors != null) {
			actor.groupOfActors.removeMember(actor);
		}
		members.add(actor);
		actor.groupOfActors = this;
	}

	public void removeMember(Actor actor) {
		this.members.remove(actor);
		if (actor == this.leader) {
			if (members.size() > 0) {
				setLeader(members.get(0));
			}
		}
	}

	public void setLeader(Actor actor) {
		this.leader = actor;
		actor.groupOfActors = this;
		if (!members.contains(actor)) {
			members.add(actor);
		}
	}

	public boolean update(Actor actor) {

		// Manage attackers list
		CopyOnWriteArrayList<GameObject> attackersToRemoveFromList = new CopyOnWriteArrayList<GameObject>(GameObject.class);
		for (GameObject attacker : attackers) {
			if (attacker.remainingHealth <= 0) {
				attackersToRemoveFromList.add(attacker);
			}
		}

		attackers.removeAll(attackersToRemoveFromList);

		// AI move towards leader
		if (actor == leader) {
			return false;
		} else if (leader.squareGameObjectIsOn != null) {

			float maxDistanceFromLeader = 3;
			if (members.size() > 6)
				maxDistanceFromLeader = 4;

			Square currentTarget = targetSquaresMap.get(actor);

			if (currentTarget == leader.squareGameObjectIsOn
					|| actor.straightLineDistanceTo(leader.squareGameObjectIsOn) > maxDistanceFromLeader) {

				CopyOnWriteArrayList<Square> possibleSquares = new CopyOnWriteArrayList<Square>(Square.class);
				for (int i = 1; i <= maxDistanceFromLeader; i++) {
					CopyOnWriteArrayList<Square> squaresISquareAway = leader.getAllSquaresAtDistance(i);
					for (Square square : squaresISquareAway) {
						if (square.inventory.canShareSquare
								&& square.structureSquareIsIn == leader.squareGameObjectIsOn.structureSquareIsIn
								&& !targetSquares.contains(square)) {
							possibleSquares.add(square);
						}
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

				// AIRoutineUtils.moveTowardsSquareToBeAdjacent(leader);
			} else {

				Square targetSquare = targetSquaresMap.get(actor);
				if (targetSquare != null)
					AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				if (actor.squareGameObjectIsOn == targetSquare)
					targetSquaresMap.put(actor, null);

			}
			// AIRoutineUtils.moveTowardsSquareToBeAdjacent(leader);
			actor.activityDescription = leader.activityDescription;
			return true;
		}
		return false;
	}

	public void addAttacker(GameObject actor) {
		if (actor != null && actor.remainingHealth > 0 && !this.attackers.contains(actor)) {
			this.attackers.add(actor);
		}
	}

	public Actor getMember(int index) {
		return this.members.get(index);
	}

	public int size() {
		return members.size();
	}

	public boolean contains(GameObject gameObject) {
		return members.contains(gameObject);
	}

	public boolean hasAttackers() {
		return attackers.size() > 0;
	}

	public CopyOnWriteArrayList<GameObject> getAttackers() {
		return attackers;
	}

	public Actor getLeader() {
		return leader;
	}

	public CopyOnWriteArrayList<Actor> getMembers() {
		return members;
	}

	public boolean leaderNeedsToWait() {
		int maxDistance = members.size();
		if (maxDistance < 6)
			maxDistance = 6;
		for (Actor actor : members) {

			if (leader.straightLineDistanceTo(actor.squareGameObjectIsOn) > maxDistance)
				return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getId() {
		return id;
	}
}
