package com.marklynch.tactics.objects.unit.ai.routines;

import java.util.ArrayList;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.Fight;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.tactics.objects.weapons.Weapon;

public class AIRoutine {

	public String name = "AI";

	public AIRoutine() {
		name = this.getClass().getSimpleName();
	}

	public boolean move() {
		return false;
	}

	public boolean attack() {
		return false;
	}

	public void postLoad() {
	}

	public boolean moveTowardsTargetToAttack(GameObject target) {

		// TODO
		// currently if there's no path it crashes
		// also... stay still fi ur already at the best part :P, issue (need to
		// know ideal distance for this one though)
		// is, its not in target's list of paths
		// ehhhhhhhhhh.... if it's not fully reachable then just go
		// closest as the crow flies?
		// also... have ideal distance (for ranged VS melee for e.g.) (this is
		// weapon distance, not travel distance)

		// MOVE TOWARDS A POINT
		// get as close to the point as possible
		// with as few moves as possible

		Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);

		// Vector<Integer> idealWeaponDistances = new Vector<Integer>();
		// idealWeaponDistances.add(2);

		// TODO this needs to be calculated based on
		// weapons available and the taret and their weapons
		// TODO what if we're stuck being closed than ideal distance, need to
		// run through this, and there could be a list of ideal distances......
		// :/
		// TODO ideal weapon distance could be on the other side of an object...
		// need to factor this in when choosing a good square :/, when talking
		// about targets squares I need to make list of attack squares, this
		// shit is heavy

		Square squareToMoveTo = calculateSquareToMoveToForTarget(target);

		if (squareToMoveTo != null) {
			Game.level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public boolean moveTowardsNearestEnemyToAttack() {

		Square squareToMoveTo = null;
		int costToBest = Integer.MAX_VALUE;

		// TODO
		// currently if there's no path it crashes
		// also... stay still fi ur already at the best part :P, issue (need to
		// know ideal distance for this one though)
		// is, its not in target's list of paths
		// ehhhhhhhhhh.... if it's not fully reachable then just go
		// closest as the crow flies?
		// also... have ideal distance (for ranged VS melee for e.g.) (this is
		// weapon distance, not travel distance)

		// Calculate paths to all squares
		Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);

		// 1. create list of enemies
		for (Faction faction : Game.level.factions) {
			if (faction != Game.level.activeActor.faction
					&& Game.level.activeActor.faction.relationships
							.get(faction).relationship < 0) {
				for (Actor actor : faction.actors) {
					Square square = calculateSquareToMoveToForTarget(actor);
					if (square != null && square.distanceToSquare < costToBest) {
						squareToMoveTo = square;
						costToBest = square.distanceToSquare;
					}
				}
			}
		}

		// Vector<Integer> idealWeaponDistances = new Vector<Integer>();
		// idealWeaponDistances.add(2);

		// TODO this needs to be calculated based on
		// weapons available and the taret and their weapons
		// TODO what if we're stuck being closed than ideal distance, need to
		// run through this, and there could be a list of ideal distances......
		// :/
		// TODO ideal weapon distance could be on the other side of an object...
		// need to factor this in when choosing a good square :/, when talking
		// about targets squares I need to make list of attack squares, this
		// shit is heavy

		if (squareToMoveTo != null) {
			Game.level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public boolean moveTowardsOptimalEnemyToAttack() {

		int turnsToBest = Integer.MAX_VALUE;
		ArrayList<Actor> bestTargetsBasedOnTurnsToReach = new ArrayList<Actor>();

		// TODO
		// currently if there's no path it crashes
		// also... stay still fi ur already at the best part :P, issue (need to
		// know ideal distance for this one though)
		// is, its not in target's list of paths
		// ehhhhhhhhhh.... if it's not fully reachable then just go
		// closest as the crow flies?
		// also... have ideal distance (for ranged VS melee for e.g.) (this is
		// weapon distance, not travel distance)

		// Calculate paths to all squares
		Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);

		// 1. create list of enemies reachable within lowest possible turns
		for (Faction faction : Game.level.factions) {
			if (faction != Game.level.activeActor.faction
					&& Game.level.activeActor.faction.relationships
							.get(faction).relationship < 0) {
				for (Actor actor : faction.actors) {
					Square square = calculateSquareToMoveToForTarget(actor);
					if (square != null) {
						int turns = square.distanceToSquare
								/ Game.level.activeActor.travelDistance;
						if (turns < turnsToBest) {
							bestTargetsBasedOnTurnsToReach.clear();
							bestTargetsBasedOnTurnsToReach.add(actor);
							turnsToBest = turns;
						} else if (square != null && turns == turnsToBest) {
							bestTargetsBasedOnTurnsToReach.add(actor);
						}
					}
				}
			}
		}

		// 2. pick which is the best
		ArrayList<Fight> fights = new ArrayList<Fight>();
		for (Actor actor : bestTargetsBasedOnTurnsToReach) {
			for (Weapon weapon : Game.level.activeActor.weapons.weapons) {
				for (float range = weapon.minRange; range <= weapon.maxRange; range++) {
					Fight fight = new Fight(Game.level.activeActor, weapon,
							actor, actor.bestCounterWeapon(
									Game.level.activeActor, weapon, range),
							range);
					fights.add(fight);
				}
			}
		}

		fights.sort(new Fight.FightComparator());

		// Vector<Integer> idealWeaponDistances = new Vector<Integer>();
		// idealWeaponDistances.add(2);

		// TODO this needs to be calculated based on
		// weapons available and the taret and their weapons
		// TODO what if we're stuck being closed than ideal distance, need to
		// run through this, and there could be a list of ideal distances......
		// :/
		// TODO ideal weapon distance could be on the other side of an object...
		// need to factor this in when choosing a good square :/, when talking
		// about targets squares I need to make list of attack squares, this
		// shit is heavy
		Square squareToMoveTo = null;
		if (fights.size() > 0)
			squareToMoveTo = calculateSquareToMoveToForTarget(fights.get(0).defender);

		if (squareToMoveTo != null) {
			Game.level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public Square calculateSquareToMoveToForTarget(GameObject target) {

		Vector<Float> idealWeaponDistances = Game.level.activeActor
				.calculateIdealDistanceFrom(target);

		Vector<Square> targetSquares = new Vector<Square>();
		int bestTravelCostFound = Integer.MAX_VALUE;
		Path pathToSquare = null;
		for (int i = 0; i < idealWeaponDistances.size(); i++) {

			// Check if we're already at this distance
			if (Game.level.activeActor
					.weaponDistanceTo(target.squareGameObjectIsOn) == idealWeaponDistances
					.get(i))
				return Game.level.activeActor.squareGameObjectIsOn;

			targetSquares = target.getAllSquaresAtDistance(idealWeaponDistances
					.get(i));

			// TODO picking which of these squares is the best is an interesting
			// issue.
			// Reachable is best.
			// if There's multiple reachable then safest out of them is best :P
			// OR somewhere u can attack someone from is the best... i dunno :D
			for (Square targetSquare : targetSquares) {
				Path currentActorPathToThisSquare = Game.level.activeActor.paths
						.get(targetSquare);
				if (currentActorPathToThisSquare != null
						&& currentActorPathToThisSquare.travelCost < bestTravelCostFound) {
					pathToSquare = currentActorPathToThisSquare;
					bestTravelCostFound = pathToSquare.travelCost;
				}
			}

			if (pathToSquare != null)
				break;

		}

		if (pathToSquare == null) {
			return null;
		}

		// TODO move this to an actor method called moveAlongPath
		Square squareToMoveTo = null;
		// squareToMoveTo = pathToSquare.squares.lastElement(); this line works,
		// but allows CPU to cheat
		if (pathToSquare.travelCost <= Game.level.activeActor.travelDistance) {
			squareToMoveTo = pathToSquare.squares.lastElement();
		} else {
			for (int i = pathToSquare.squares.size() - 1; i >= 0; i--) {
				if (Game.level.activeActor.paths.get(pathToSquare.squares
						.get(i)).travelCost <= Game.level.activeActor.travelDistance) {
					squareToMoveTo = pathToSquare.squares.get(i);
					break;
				}
			}
		}

		return squareToMoveTo;

	}

	public boolean moveTowardsTargetSquare(Square square) {

		Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);

		Square squareToMoveTo = calculateSquareToMoveToForTargetSquare(square);

		if (squareToMoveTo != null) {
			Game.level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public Square calculateSquareToMoveToForTargetSquare(Square square) {

		Path pathToSquare = Game.level.activeActor.paths.get(square);

		if (pathToSquare == null) {
			return null;
		}

		// TODO move this to an actor method called moveAlongPath
		Square squareToMoveTo = null;
		// squareToMoveTo = pathToSquare.squares.lastElement(); this line works,
		// but allows CPU to cheat
		if (pathToSquare.travelCost <= Game.level.activeActor.travelDistance) {
			squareToMoveTo = pathToSquare.squares.lastElement();
		} else {
			for (int i = pathToSquare.squares.size() - 1; i >= 0; i--) {
				if (Game.level.activeActor.paths.get(pathToSquare.squares
						.get(i)).travelCost <= Game.level.activeActor.travelDistance) {
					squareToMoveTo = pathToSquare.squares.get(i);
					break;
				}
			}
		}

		return squareToMoveTo;

	}

	public boolean moveToRandomSquare() {
		// MOVE TO RANDOM SQUARE - maybe for a broken robot or confused
		// enemy
		Vector<Square> reachableSquares = new Vector<Square>();
		for (int j = 0; j < Game.level.squares.length; j++) {
			for (int k = 0; k < Game.level.squares[0].length; k++) {
				if (Game.level.squares[j][k].reachableBySelectedCharater) {
					reachableSquares.add(Game.level.squares[j][k]);
				}
			}
		}
		if (reachableSquares.size() > 0) {
			int random = (int) (Math.random() * (reachableSquares.size() - 1));
			Square squareToMoveTo = reachableSquares.get(random);
			Game.level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public boolean attackRandomEnemy() {

		// make a list of attackable enemies
		Vector<Actor> attackableActors = new Vector<Actor>();
		for (Faction faction : Game.level.factions) {
			for (Actor actor : faction.actors) {
				int weaponDistance = Game.level.activeActor
						.weaponDistanceTo(actor.squareGameObjectIsOn);

				if (faction != actor.faction
						&& Game.level.activeActor.hasRange(weaponDistance)) {
					attackableActors.add(actor);
				}
			}
		}

		if (attackableActors.size() > 0) {
			int random = (int) (Math.random() * (attackableActors.size() - 1));
			Actor actorToAttack = attackableActors.get(random);
			Game.level.activeActor.equipBestWeapon(actorToAttack);
			Game.level.activeActor.attack(actorToAttack, false);
			Game.level.activeActor.highlightSelectedCharactersSquares();
			return true;
		} else {
			return false;
		}
	}

	public boolean attackRandomEnemyOrAlly() {
		// TODO needs to be tested
		// make a list of attackable enemies
		Vector<Actor> attackableActors = new Vector<Actor>();
		for (Faction faction : Game.level.factions) {
			for (Actor actor : faction.actors) {
				int weaponDistance = Game.level.activeActor
						.weaponDistanceTo(actor.squareGameObjectIsOn);
				if (actor != Game.level.activeActor
						&& Game.level.activeActor.hasRange(weaponDistance)) {
					attackableActors.add(actor);
				}
			}
		}

		if (attackableActors.size() > 0) {
			int random = (int) (Math.random() * (attackableActors.size() - 1));
			Actor actorToAttack = attackableActors.get(random);
			Game.level.activeActor.equipBestWeapon(actorToAttack);
			Game.level.activeActor.attack(actorToAttack, false);
			Game.level.activeActor.highlightSelectedCharactersSquares();
			return true;
		} else {
			return false;
		}
	}

	public boolean attackTarget(GameObject gameObject) {
		int weaponDistance = Game.level.activeActor
				.weaponDistanceTo(gameObject.squareGameObjectIsOn);
		if (Game.level.activeActor.hasRange(weaponDistance)) {
			Game.level.activeActor.equipBestWeapon(gameObject);
			Game.level.activeActor.attack(gameObject, false);
			Actor.highlightSelectedCharactersSquares();
			return true;
		} else {
			return attackRandomEnemy();
		}

	}

	public AIRoutine makeCopy() {
		return null;
	}

}
