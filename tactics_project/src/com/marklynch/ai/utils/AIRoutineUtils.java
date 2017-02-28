package com.marklynch.ai.utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Structure;
import com.marklynch.level.constructs.cave.Room;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionLootAll;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fight;
import com.marklynch.objects.units.Path;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineUtils {

	public String name = "AI";

	public AIRoutineUtils() {
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

	public static boolean lootNearby() {
		// TODO Auto-generated method stub
		return false;
	}

	public static Square getRandomSquare(int maxDistance, boolean outdoors) {

		int attempts = 0;
		int maxAttempts = 5;
		Square randomSquare = null;
		while (attempts < maxAttempts) {
			int x = (int) (Math.random() * Game.level.width);
			int y = (int) (Math.random() * Game.level.height);
			randomSquare = Game.level.squares[x][y];
			Path currentActorPathToThisSquare = Game.level.activeActor.paths.get(randomSquare);
			if ((!outdoors || outdoors && randomSquare.structureSquareIsIn == null)
					&& currentActorPathToThisSquare != null && currentActorPathToThisSquare.travelCost < maxDistance
					&& randomSquare.inventory.canShareSquare()) {
				return randomSquare;
			}
			attempts++;
		}

		return null;
	}

	public static GameObject getNearestForPurposeOfBeingAdjacent(Class clazz, float maxDistance,
			boolean fitsInInventory, boolean checkActors, boolean checkInanimateObjects, boolean mustContainObjects) {

		GameObject result = null;
		int costToBest = Integer.MAX_VALUE;

		if (checkActors) {
			// 1. check actors
			for (Faction faction : Game.level.factions) {
				for (Actor actor : faction.actors) {
					if (passesChecks(actor, clazz, maxDistance, fitsInInventory, mustContainObjects)) {
						Square square = calculateSquareToMoveToToBeWithinXSquaresToTarget(actor, 1f);
						if (square != null && square.walkingDistanceToSquare < costToBest) {
							result = actor;
							costToBest = square.walkingDistanceToSquare;
						}
					}

				}
			}
		}

		if (checkInanimateObjects) {
			// 2. check gameObjects
			for (GameObject gameObject : Game.level.inanimateObjectsOnGround) {
				if (passesChecks(gameObject, clazz, maxDistance, fitsInInventory, mustContainObjects)) {
					Square square = calculateSquareToMoveToToBeWithinXSquaresToTarget(gameObject, 1f);
					if (square != null && square.walkingDistanceToSquare < costToBest) {
						result = gameObject;
						costToBest = square.walkingDistanceToSquare;
					}
				}
			}
		}

		return result;

	}

	public static GameObject getNearestForPurposeOfAttack(Class clazz, float maxDistance, boolean fitsInInventory,
			boolean checkActors, boolean checkInanimateObjects, boolean mustContainObjects) {

		GameObject result = null;
		int costToBest = Integer.MAX_VALUE;

		if (checkActors) {
			// 1. check actors
			for (Faction faction : Game.level.factions) {
				for (Actor actor : faction.actors) {
					if (passesChecks(actor, clazz, maxDistance, fitsInInventory, mustContainObjects)) {
						Square square = calculateSquareToMoveToToAttackTarget(actor);
						if (square != null && square.walkingDistanceToSquare < costToBest) {
							result = actor;
							costToBest = square.walkingDistanceToSquare;
						}
					}

				}
			}
		}

		if (checkInanimateObjects) {
			// 2. check gameObjects
			for (GameObject gameObject : Game.level.inanimateObjectsOnGround) {
				if (passesChecks(gameObject, clazz, maxDistance, fitsInInventory, mustContainObjects)) {
					Square square = calculateSquareToMoveToToAttackTarget(gameObject);
					if (square != null && square.walkingDistanceToSquare < costToBest) {
						result = gameObject;
						costToBest = square.walkingDistanceToSquare;
					}
				}
			}
		}

		return result;

	}

	public static boolean passesChecks(GameObject gameObject, Class clazz, float maxDistance, boolean fitsInInventory,
			boolean mustContainsObjects) {

		if (gameObject.quest != null)
			return false;

		if (gameObject.remainingHealth <= 0)
			return false;

		if (mustContainsObjects && gameObject.inventory.size() <= 0)
			return false;

		if (gameObject.fitsInInventory != fitsInInventory)
			return false;

		// check class
		if (clazz != null && !clazz.isInstance(gameObject))
			return false;

		// check distance
		if (maxDistance > 0
				&& Game.level.activeActor.straightLineDistanceTo(gameObject.squareGameObjectIsOn) > maxDistance)
			return false;

		return true;

	}

	public static GameObject getNearestAttacker(ArrayList<Actor> attackers) {
		GameObject result = null;
		int costToBest = Integer.MAX_VALUE;

		for (Actor actor : attackers) {
			Square square = calculateSquareToMoveToToAttackTarget(actor);
			if (square != null && square.walkingDistanceToSquare < costToBest) {
				result = actor;
				costToBest = square.walkingDistanceToSquare;
			}
		}

		return result;

	}

	public GameObject getNearestEnemy() {
		GameObject result = null;

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

		// 1. create list of enemies
		for (Faction faction : Game.level.factions) {
			if (faction != Game.level.activeActor.faction
					&& Game.level.activeActor.faction.relationships.get(faction).relationship < 0) {
				for (Actor actor : faction.actors) {
					Square square = calculateSquareToMoveToToAttackTarget(actor);
					if (square != null && square.walkingDistanceToSquare < costToBest) {
						result = actor;
						costToBest = square.walkingDistanceToSquare;
					}
				}
			}
		}

		return result;

	}

	public static boolean moveTowardsTargetToAttack(GameObject target) {

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

		Square squareToMoveTo = calculateSquareToMoveToToAttackTarget(target);

		if (squareToMoveTo != null) {
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean moveTowardsTargetToBeOn(GameObject target) {

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

		Square squareToMoveTo = calculateSquareToMoveToToBeWithinXSquaresToTarget(target, 0f);

		if (squareToMoveTo != null) {
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean moveTowardsTargetToBeAdjacent(GameObject target) {

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

		if (Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn) <= 1) {
			return true;
		}

		Square squareToMoveTo = calculateSquareToMoveToToBeWithinXSquaresToTarget(target, 1f);

		if (squareToMoveTo != null) {
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
			return true;
		} else {
			return false;
		}
	}

	// public boolean moveTowardsNearestEnemyToAttack() {
	//
	// // Vector<Integer> idealWeaponDistances = new Vector<Integer>();
	// // idealWeaponDistances.add(2);
	//
	// // TODO this needs to be calculated based on
	// // weapons available and the taret and their weapons
	// // TODO what if we're stuck being closed than ideal distance, need to
	// // run through this, and there could be a list of ideal distances......
	// // :/
	// // TODO ideal weapon distance could be on the other side of an object...
	// // need to factor this in when choosing a good square :/, when talking
	// // about targets squares I need to make list of attack squares, this
	// // shit is heavy
	//
	// if (squareToMoveTo != null) {
	// Game.level.activeActor.moveTo(squareToMoveTo);
	// return true;
	// } else {
	// return false;
	// }
	// }

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

		// 1. create list of enemies reachable within lowest possible turns
		for (Faction faction : Game.level.factions) {
			if (faction != Game.level.activeActor.faction
					&& Game.level.activeActor.faction.relationships.get(faction).relationship < 0) {
				for (Actor actor : faction.actors) {
					Square square = calculateSquareToMoveToToAttackTarget(actor);
					if (square != null) {
						int turns = square.walkingDistanceToSquare / Game.level.activeActor.travelDistance;
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
			for (GameObject gameObject : Game.level.activeActor.inventory.getGameObjects()) {
				if (gameObject instanceof Weapon) {
					Weapon weapon = (Weapon) gameObject;
					for (float range = weapon.getEffectiveMinRange(); range <= weapon.getEffectiveMaxRange(); range++) {
						Fight fight = new Fight(Game.level.activeActor, weapon, actor,
								actor.bestCounterWeapon(Game.level.activeActor, weapon, range), range);
						fights.add(fight);
					}
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
			squareToMoveTo = calculateSquareToMoveToToAttackTarget(fights.get(0).defender);

		if (squareToMoveTo != null) {
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
			return true;
		} else {
			return false;
		}
	}

	public static Square calculateSquareToMoveToToAttackTarget(GameObject target) {

		Vector<Float> idealWeaponDistances = Game.level.activeActor.calculateIdealDistanceFrom(target);

		Vector<Square> squaresAtSpecifiedDistanceToTarget = new Vector<Square>();
		int bestTravelCostFound = Integer.MAX_VALUE;
		Path pathToSquare = null;
		for (int i = 0; i < idealWeaponDistances.size(); i++) {

			// WAS TRYING TO STOP THEM ATTACKING PEOPLE IN DIFFERENT BUILDINGS,
			// DIDNT WORK, AND WAS STUPID COZ DOORWAYS
			// MAYBE ONLY WHEN DISTANCE > 1

			// Check if we're already at this distance
			if (Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn) == idealWeaponDistances
					.get(i) && inSameBuilding(Game.level.activeActor, target)) {
				return Game.level.activeActor.squareGameObjectIsOn;
			}

			squaresAtSpecifiedDistanceToTarget = target.getAllSquaresAtDistance(idealWeaponDistances.get(i));

			// TODO picking which of these squares is the best is an interesting
			// issue.
			// Reachable is best.
			// if There's multiple reachable then safest out of them is best :P
			// OR somewhere u can attack someone from is the best... i dunno :D
			for (Square squareAtSpecifiedDistanceToTarget : squaresAtSpecifiedDistanceToTarget) {
				Path currentActorPathToThisSquare = Game.level.activeActor.paths.get(squareAtSpecifiedDistanceToTarget);
				if (inSameBuilding(target, squareAtSpecifiedDistanceToTarget) && currentActorPathToThisSquare != null
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

		return getSquareToMoveAlongPath(pathToSquare);

	}

	public static Square calculateSquareToMoveToToBeWithinXSquaresToTarget(GameObject target, float maxDistance) {

		Vector<Float> idealDistances = new Vector<Float>();
		for (int i = 0; i <= maxDistance; i++) {
			idealDistances.addElement((float) i);
		}

		Vector<Square> targetSquares = new Vector<Square>();
		int bestTravelCostFound = Integer.MAX_VALUE;
		Path pathToSquare = null;
		for (int i = 0; i < idealDistances.size(); i++) {

			// Check if we're already at this distance
			if (Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn) == idealDistances.get(i))
				return Game.level.activeActor.squareGameObjectIsOn;

			targetSquares = target.getAllSquaresAtDistance(idealDistances.get(i));

			// TODO picking which of these squares is the best is an interesting
			// issue.
			// Reachable is best.
			// if There's multiple reachable then safest out of them is best :P
			// OR somewhere u can attack someone from is the best... i dunno :D
			for (Square targetSquare : targetSquares) {
				Path currentActorPathToThisSquare = Game.level.activeActor.paths.get(targetSquare);
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

		return getSquareToMoveAlongPath(pathToSquare);

	}

	public static Square getSquareToMoveAlongPath(Path path) {

		// TODO move this to an actor method called moveAlongPath
		Square squareToMoveTo = null;
		// squareToMoveTo = pathToSquare.squares.lastElement(); this line works,
		// but allows CPU to cheat
		if (path.travelCost <= Game.level.activeActor.travelDistance) {
			squareToMoveTo = path.squares.lastElement();
		} else {
			for (int i = path.squares.size() - 1; i >= 0; i--) {
				if (Game.level.activeActor.paths
						.get(path.squares.get(i)).travelCost <= Game.level.activeActor.travelDistance) {
					squareToMoveTo = path.squares.get(i);
					break;
				}
			}
		}

		return squareToMoveTo;

	}

	public static boolean moveTowardsTargetSquare(Square square) {

		if (Game.level.activeActor.squareGameObjectIsOn == square)
			return true;

		Square squareToMoveTo = calculateSquareToMoveToForTargetSquare(square);

		if (squareToMoveTo != null) {
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
			return true;
		} else {
			return false;
		}
	}

	public static Square calculateSquareToMoveToForTargetSquare(Square square) {

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
				if (Game.level.activeActor.paths
						.get(pathToSquare.squares.get(i)).travelCost <= Game.level.activeActor.travelDistance) {
					squareToMoveTo = pathToSquare.squares.get(i);
					break;
				}
			}
		}

		return squareToMoveTo;

	}

	public static boolean moveToRandomSquare() {
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
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
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
				int weaponDistance = Game.level.activeActor.straightLineDistanceTo(actor.squareGameObjectIsOn);

				if (faction != actor.faction && Game.level.activeActor.hasRange(weaponDistance)) {
					attackableActors.add(actor);
				}
			}
		}

		if (attackableActors.size() > 0) {
			int random = (int) (Math.random() * (attackableActors.size() - 1));
			Actor actorToAttack = attackableActors.get(random);
			Game.level.activeActor.equipBestWeapon(actorToAttack);
			new ActionAttack(Game.level.activeActor, actorToAttack).perform();
			// Game.level.activeActor.highlightSelectedCharactersSquares();
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
				int weaponDistance = Game.level.activeActor.straightLineDistanceTo(actor.squareGameObjectIsOn);
				if (actor != Game.level.activeActor && Game.level.activeActor.hasRange(weaponDistance)) {
					attackableActors.add(actor);
				}
			}
		}

		if (attackableActors.size() > 0) {
			int random = (int) (Math.random() * (attackableActors.size() - 1));
			Actor actorToAttack = attackableActors.get(random);
			Game.level.activeActor.equipBestWeapon(actorToAttack);
			new ActionAttack(Game.level.activeActor, actorToAttack).perform();
			// Game.level.activeActor.highlightSelectedCharactersSquares();
			return true;
		} else {
			return false;
		}
	}

	public static boolean attackTarget(GameObject gameObject) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(gameObject.squareGameObjectIsOn);
		if (inSameBuilding(gameObject, Game.level.activeActor) && Game.level.activeActor.hasRange(weaponDistance)) {
			Game.level.activeActor.equipBestWeapon(gameObject);
			new ActionAttack(Game.level.activeActor, gameObject).perform();
			// Actor.highlightSelectedCharactersSquares();
			return true;
		} else {
			return false;
		}

	}

	public static boolean lootTarget(GameObject gameObject) {
		int straightLineDistance = Game.level.activeActor.straightLineDistanceTo(gameObject.squareGameObjectIsOn);
		if (straightLineDistance <= 1) {
			new ActionLootAll(Game.level.activeActor, gameObject).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean pickupTarget(GameObject target) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn);
		if (weaponDistance <= 1) {
			new ActionPickUp(Game.level.activeActor, target).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean sellAllToTarget(Class clazz, GameObject gameObject) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(gameObject.squareGameObjectIsOn);
		if (weaponDistance <= 1 && gameObject.remainingHealth > 0) {
			Game.level.activeActor.sellAllToTarget(clazz, gameObject);
			return true;
		} else {
			return false;
		}
	}

	public AIRoutineUtils makeCopy() {
		return null;
	}

	public static boolean inSameBuilding(GameObject gameObject1, GameObject gameObject2) {
		return inSameBuilding(gameObject1.squareGameObjectIsOn, gameObject2.squareGameObjectIsOn);
	}

	public static boolean inSameBuilding(GameObject gameObject1, Square square2) {
		return inSameBuilding(gameObject1.squareGameObjectIsOn, square2);

	}

	public static boolean inSameBuilding(Square square1, Square square2) {
		return square1.structureSquareIsIn == square2.structureSquareIsIn;
	}

	public static Square getRandomSquareInBuilding(Structure building) {

		ArrayList<Square> randomSquares = new ArrayList<Square>();
		for (Room room : building.rooms) {
			randomSquares.add(getRandomSquareInRoom(room));
		}

		Random random = new Random();
		return randomSquares.get(random.nextInt(randomSquares.size()));
	}

	public static Square getRandomSquareInRoom(Room room) {
		int randomX = (room.gridX1 + 1) + (int) Math.round((Math.random() * ((room.gridX2 - 1) - (room.gridX1 + 1))));
		int randomY = (room.gridY1 + 1) + (int) Math.round((Math.random() * ((room.gridY2 - 1) - (room.gridY1 + 1))));

		if (randomX > 0) {
			if (Game.level.squares[randomX - 1][randomY].inventory.contains(Door.class)) {
				return null;
			}
		}

		if (randomX < Game.level.squares.length - 1) {
			if (Game.level.squares[randomX + 1][randomY].inventory.contains(Door.class)) {
				return null;
			}

		}

		if (randomY > 0) {
			if (Game.level.squares[randomX][randomY - 1].inventory.contains(Door.class)) {
				return null;
			}
		}

		if (randomY < Game.level.squares[0].length - 1) {
			if (Game.level.squares[randomX][randomY + 1].inventory.contains(Door.class)) {
				return null;
			}

		}

		return Game.level.squares[randomX][randomY];
	}

}
