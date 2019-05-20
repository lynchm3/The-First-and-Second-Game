package com.marklynch.ai.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.marklynch.Game;
import com.marklynch.actions.ActionAttack;
import com.marklynch.actions.ActionChopping;
import com.marklynch.actions.ActionEatItems;
import com.marklynch.actions.ActionMining;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionSkin;
import com.marklynch.actions.ActionTakeItems;
import com.marklynch.actions.ActiontTakeAll;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Vein;

public class AIRoutineUtils {

	public String name = "AI";

	public AIRoutineUtils() {
		name = this.getClass().getSimpleName();
	}

	public static Square getRandomSquare(int minDistance, int maxDistance, boolean mustBeOutdoors,
			boolean squareMustShare, Area area) {

		int attempts = 0;
		int maxAttempts = 5;
		Square randomSquare = null;
		ArrayList<Square> squaresInRange = Game.level.activeActor.getAllSquaresWithinDistance(minDistance, maxDistance);

		Collections.shuffle(squaresInRange);

		if (squaresInRange.size() == 0)
			return null;

		while (attempts < maxAttempts) {

			randomSquare = squaresInRange.get((int) (Math.random() * (squaresInRange.size() - 1)));

			if (area != null && randomSquare.areaSquareIsIn != area) {
				attempts++;
				continue;
			}

			// AIPath currentActorPathToThisSquare =
			// Game.level.activeActor.getPathTo(randomSquare);

			if ((!mustBeOutdoors || mustBeOutdoors && randomSquare.structureSquareIsIn == null)
					// && currentActorPathToThisSquare != null &&
					// currentActorPathToThisSquare.travelCost < maxDistance
					&& (randomSquare.inventory.canShareSquare || !squareMustShare)
					&& Game.level.activeActor.aiRoutine.squareInBounds(randomSquare)) {

				return randomSquare;
			}
			attempts++;
		}

		return null;
	}

	public static Square getRandomAdjacentSquare(Square startingSquare) {

		int random = new Random().nextInt(4);
		if (random == 0) {
			return startingSquare.getSquareToLeftOf();
		} else if (random == 1) {
			return startingSquare.getSquareToRightOf();
		} else if (random == 2) {
			return startingSquare.getSquareAbove();
		} else {
			return startingSquare.getSquareBelow();
		}
	}

	static Object getFieldValue(Class clazz, String fieldName) {

		try {
			Field myField = clazz.getDeclaredField(fieldName);
			return myField.get(null);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static AIPath tempPath = null;

	public static boolean moveTowards(GameObject gameObject) {
		return moveTowards(gameObject.squareGameObjectIsOn);
	}

	public static boolean moveTowards(Square square) {
		return moveTowards(Game.level.activeActor.getPathTo(square));
	}

	public static boolean moveTowards(AIPath path) {
		if (path == null)
			return false;

		if (path.squares == null)
			return false;

		if (path.squares.size() == 0)
			return false;

		new ActionMove(Game.level.activeActor, path.squares.get(0), true).perform();
		return true;

	}

	public static GameObject getNearestForPurposeOfBeingAdjacent(float maxDistance, boolean fitsInInventory,
			boolean mustContainObjects, boolean mustBeUnowned, boolean ignoreQuestObjects, int minimumGoldValue,
			boolean REMOVETHIS, boolean actorsAlive, Class... types) {

		// Check our current target first, maybe it's a good 'un
		GameObject existingTarget = Game.level.activeActor.aiRoutine.target;
		if (existingTarget != null && passesChecks(existingTarget, fitsInInventory, mustContainObjects, mustBeUnowned,
				ignoreQuestObjects, minimumGoldValue, actorsAlive, types, false)) {
			AIRoutineUtils.tempPath = Game.level.activeActor.getPathTo(existingTarget.squareGameObjectIsOn);
			if (AIRoutineUtils.tempPath == null) {
				Game.level.activeActor.aiRoutine.ignoreList.add(existingTarget);
			} else {
				return existingTarget;
			}
		}

		if (maxDistance > Game.level.width + Game.level.height) {
			maxDistance = Game.level.width + Game.level.height;
		}

		AIPath bestPath = null;
		GameObject bestObject = null;
		int bestPathTravelCost = Integer.MAX_VALUE;
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		for (Class clazz : types) {
			objects.addAll((ArrayList<GameObject>) getFieldValue(clazz, "instances"));
		}

		if (objects.size() > maxDistance * maxDistance) {
			return getNearestForPurposeOfBeingAdjacent(maxDistance, fitsInInventory, mustContainObjects, mustBeUnowned,
					ignoreQuestObjects, minimumGoldValue, actorsAlive, types);
		}

		objects.sort(AIRoutineUtils.sortByStraightLineDistance);

		for (GameObject object : objects) {

			int straightLineDistance = Game.level.activeActor.straightLineDistanceTo(object.squareGameObjectIsOn);
			if (straightLineDistance <= maxDistance) {

				if (passesChecks(object, fitsInInventory, mustContainObjects, mustBeUnowned, ignoreQuestObjects,
						minimumGoldValue, actorsAlive, null, false)) {

					if (Game.level.activeActor.straightLineDistanceTo(object.squareGameObjectIsOn) == 1) {
						return object;
					}

					AIPath path = Game.level.activeActor.getPathTo(object.squareGameObjectIsOn);

					if (path == null) {
						Game.level.activeActor.aiRoutine.ignoreList.add(object);
					} else if (path.complete) {
						tempPath = path;
						return object;
					} else if (straightLineDistance > 20) {
						tempPath = path;
						return object;
					} else if (path.travelCost < bestPathTravelCost) {
						bestObject = object;
						bestPath = path;
						bestPathTravelCost = path.travelCost;
					}
				}
			}
		}

		if (bestObject == null)
			return null;
		if (bestPathTravelCost > maxDistance)
			return null;

		tempPath = bestPath;
		return bestObject;

	}

	private static GameObject getNearestForPurposeOfBeingAdjacent(float maxDistance, boolean fitsInInventory,
			boolean mustContainObjects, boolean mustBeUnowned, boolean ignoreQuestObjects, int minimumValue,
			boolean actorsAlive, Class[] classes) {

		if (maxDistance > Game.level.width + Game.level.height) {
			maxDistance = Game.level.width + Game.level.height;
		}

		for (int i = 1; i < maxDistance; i++) {
			ArrayList<Square> squares = Game.level.activeActor.getAllSquaresAtDistance(i);
			for (Square square : squares) {
				for (GameObject gameObject : square.inventory.gameObjects) {
					if (passesChecks(gameObject, fitsInInventory, mustContainObjects, mustBeUnowned, ignoreQuestObjects,
							minimumValue, actorsAlive, classes, false)) {
						AIPath path = Game.level.activeActor.getPathTo(gameObject.squareGameObjectIsOn);
						if (path != null && (path.complete || i > 20)) {
							tempPath = path;
							return gameObject;
						}
					}

				}
			}
		}

		return null;

	}

	// public static GameObject getNearestForPurposeOfAttacking(float
	// maxDistance, boolean fitsInInventory,
	// boolean checkActors, boolean checkInanimateObjects, boolean
	// mustContainObjects, boolean mustBeUnowned,
	// boolean ignoreQuestObjects, int minimumValue, Class... classes) {
	//
	// if (checkActors) {
	//
	// ArrayList<Integer> ranges = new ArrayList<Integer>();
	// for (int i = 0; i < maxDistance; i += 10) {
	// ranges.add(i);
	// }
	// ranges.add((int) maxDistance);
	//
	// for (int i = 1; i < ranges.size(); i++) {
	// int minRange = ranges.get(i - 1);
	// int maxRange = ranges.get(i);
	//
	// for (Class clazz : classes) {
	// for (Faction faction : Game.level.factions) {
	// for (Actor actor : faction.actors) {
	// if
	// (Game.level.activeActor.straightLineDistanceTo(actor.squareGameObjectIsOn)
	// >= minRange
	// && Game.level.activeActor
	// .straightLineDistanceTo(actor.squareGameObjectIsOn) <= maxRange) {
	// if (passesChecks(actor, fitsInInventory, mustContainObjects,
	// mustBeUnowned,
	// ignoreQuestObjects, minimumValue, clazz)) {
	// Square square = calculateSquareToMoveToToAttackTarget(actor);
	// AIPath path = Game.level.activeActor.getPathTo(square);
	// if (path != null && path.complete) {
	// return actor;
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// // Takes first one that returns a path
	// if (checkInanimateObjects) {
	//
	// ArrayList<Integer> ranges = new ArrayList<Integer>();
	// for (int i = 0; i < maxDistance; i += 10) {
	// ranges.add(i);
	// }
	// ranges.add((int) maxDistance);
	//
	// for (int i = 1; i < ranges.size(); i++) {
	// int minRange = ranges.get(i - 1);
	// int maxRange = ranges.get(i);
	// long start = System.nanoTime();
	//
	// for (Class clazz : classes) {
	// for (GameObject gameObject :
	// Game.level.inanimateObjectsOnGround.get(clazz)) {
	//
	// if
	// (Game.level.activeActor.straightLineDistanceTo(gameObject.squareGameObjectIsOn)
	// >= minRange
	// && Game.level.activeActor
	// .straightLineDistanceTo(gameObject.squareGameObjectIsOn) <= maxRange) {
	// if (!(gameObject instanceof Actor) && passesChecks(gameObject,
	// fitsInInventory,
	// mustContainObjects, mustBeUnowned, ignoreQuestObjects, minimumValue,
	// clazz)) {
	// Square square = calculateSquareToMoveToToAttackTarget(gameObject);
	// AIPath path = Game.level.activeActor.getPathTo(square);
	// if (path != null && path.complete) {
	// return gameObject;
	//
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// return null;
	//
	// }

	public static boolean passesChecks(GameObject gameObject, boolean fitsInInventory, boolean mustContainsObjects,
			boolean mustBeUnowned, boolean ignoreQuestObjects, int minimumValue, boolean actorsAlive,
			Class... classes) {
		return passesChecks(gameObject, fitsInInventory, mustContainsObjects, mustBeUnowned, ignoreQuestObjects,
				minimumValue, actorsAlive, classes, false);
	}

	public static boolean passesChecks(GameObject gameObject, boolean fitsInInventory, boolean mustContainsObjects,
			boolean mustBeUnowned, boolean ignoreQuestObjects, int minimumValue, boolean actorsAlive, Class[] classes,
			boolean DOESNOTHING) {

		if (Game.level.activeActor.aiRoutine.ignoreList.contains(gameObject))
			return false;

		if (gameObject.value < minimumValue)
			return false;

		if (ignoreQuestObjects && gameObject.quest != null)
			return false;

		if (mustBeUnowned && gameObject.owner != null)
			return false;

		if (gameObject.remainingHealth <= 0)
			return false;

		if (mustContainsObjects && gameObject.inventory.size() <= 0)
			return false;

		if (gameObject.fitsInInventory != fitsInInventory)
			return false;

		if (gameObject instanceof Actor) {
			if (gameObject.remainingHealth <= 0 && actorsAlive) {
				return false;
			} else if (gameObject.remainingHealth > 0 && !actorsAlive) {
				return false;
			}
		}

		if (classes == null || classes.length == 0)
			return true;

		for (Class clazz : classes) {
			if (clazz == null || clazz.isInstance(gameObject))
				return true;
		}

		return false;

	}

	public static boolean escapeFromAttacker(GameObject target) {

		boolean moved = false;
		Square squareActorOn = Game.level.activeActor.squareGameObjectIsOn;

		if (squareActorOn.xInGrid > target.squareGameObjectIsOn.xInGrid
				&& squareActorOn.xInGrid < Game.level.squares.length - 1) {
			ActionMove actionMove = new ActionMove(Game.level.activeActor,
					Game.level.squares[squareActorOn.xInGrid + 1][squareActorOn.yInGrid], false);
			if (actionMove.check()) {
				actionMove.perform();
				return true;
			}

		} else if (squareActorOn.xInGrid < target.squareGameObjectIsOn.xInGrid && squareActorOn.xInGrid > 0) {
			ActionMove actionMove = new ActionMove(Game.level.activeActor,
					Game.level.squares[squareActorOn.xInGrid - 1][squareActorOn.yInGrid], false);
			if (actionMove.check()) {
				actionMove.perform();
				return true;
			}
		}

		if (squareActorOn.yInGrid > target.squareGameObjectIsOn.yInGrid
				&& squareActorOn.yInGrid < Game.level.squares[0].length - 1) {
			ActionMove actionMove = new ActionMove(Game.level.activeActor,
					Game.level.squares[squareActorOn.xInGrid][squareActorOn.yInGrid + 1], false);
			if (actionMove.check()) {
				actionMove.perform();
				return true;
			}
		} else if (squareActorOn.yInGrid < target.squareGameObjectIsOn.yInGrid && squareActorOn.yInGrid > 0) {
			ActionMove actionMove = new ActionMove(Game.level.activeActor,
					Game.level.squares[squareActorOn.xInGrid][squareActorOn.yInGrid - 1], false);
			if (actionMove.check()) {
				actionMove.perform();
				return true;
			}
		}

		return false;
	}

	// public static boolean moveTowardsSquareToBeAdjacent(GameObject target) {
	//
	// if
	// (Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn)
	// <= 1) {
	// return true;
	// }
	//
	// Square squareToMoveTo =
	// calculateSquareToMoveToToBeWithinXSquaresToTarget(target.squareGameObjectIsOn,
	// 0f);
	//
	// if (squareToMoveTo != null) {
	// new ActionMove(Game.level.activeActor, squareToMoveTo, true).perform();
	// return true;
	// } else {
	// return false;
	// }
	// }

	// public static boolean moveTowardsSquareToBeAdjacent(Square square) {
	// if (square == null)
	// return false;
	//
	// if (Game.level.activeActor.straightLineDistanceTo(square) <= 1) {
	// return true;
	// }
	//
	// Square squareToMoveTo =
	// calculateSquareToMoveToToBeWithinXSquaresToTarget(square, 0f);
	//
	// if (squareToMoveTo != null) {
	// new ActionMove(Game.level.activeActor, square, true).perform();
	// return true;
	// } else {
	// return false;
	// }
	// }

	// public boolean moveTowardsNearestEnemyToAttack() {
	//
	// // ArrayList<Integer> idealWeaponDistances = new ArrayList<Integer>();
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

	public static Square getSquareToMoveAlongPath(AIPath path) {

		if (path == null || path.complete == false)
			return null;

		if (path.squares.size() == 0)
			return null;

		return path.squares.get(0);

		// // TODO move this to an actor method called moveAlongPath
		// Square squareToMoveTo = null;
		// // squareToMoveTo = pathToSquare.squares.lastElement(); this line
		// works,
		// // but allows CPU to cheat
		// if (path.travelCost <= Game.level.activeActor.travelDistance) {
		// squareToMoveTo = path.squares.lastElement();
		// } else {
		// for (int i = path.squares.size() - 1; i >= 0; i--) {
		//
		// AIPath subPath =
		// Game.level.activeActor.getPathTo(path.squares.get(i));
		// if (subPath != null && subPath.travelCost <=
		// Game.level.activeActor.travelDistance) {
		// squareToMoveTo = path.squares.get(i);
		// break;
		// }
		// }
		// }
		//
		// return squareToMoveTo;

	}

	public static boolean moveTowardsTargetSquare(Square square) {

		if (square == null)
			return true;

		if (Game.level.activeActor.squareGameObjectIsOn == square)
			return true;

		AIPath path = Game.level.activeActor.getPathTo(square);

		if (path == null || path.squares.size() == 0 || path.squares.get(0) == null)
			return true;

		new ActionMove(Game.level.activeActor, path.squares.get(0), true).perform();

		return true;

	}

	public boolean attackRandomEnemy() {

		// make a list of attackable enemies
		ArrayList<Actor> attackableActors = new ArrayList<Actor>();
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
		ArrayList<Actor> attackableActors = new ArrayList<Actor>();
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
		if (Game.level.activeActor.hasRange(weaponDistance) && Game.level.activeActor.canSeeGameObject(gameObject)) {
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
			new ActiontTakeAll(Game.level.activeActor, gameObject).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean skinTarget(GameObject gameObject) {
		int straightLineDistance = Game.level.activeActor.straightLineDistanceTo(gameObject.squareGameObjectIsOn);
		if (straightLineDistance <= 1) {
			new ActionSkin(Game.level.activeActor, gameObject).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean pickupTarget(GameObject target) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn);
		if (weaponDistance <= 1) {
			new ActionTakeItems(Game.level.activeActor, target.squareGameObjectIsOn, target).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean eatTarget(GameObject target) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn);
		if (weaponDistance <= 1) {
			new ActionEatItems(Game.level.activeActor, target).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean mine(GameObject target) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn);
		if (weaponDistance <= 1) {
			new ActionMining(Game.level.activeActor, (Vein) target).perform();
			return true;
		} else {
			return false;
		}
	}

	public static boolean chop(GameObject target) {
		int weaponDistance = Game.level.activeActor.straightLineDistanceTo(target.squareGameObjectIsOn);
		System.out.println("weaponDistance = " + weaponDistance);
		if (weaponDistance <= 1) {
			new ActionChopping(Game.level.activeActor, target).perform();
			return true;
		} else {
			return false;
		}
	}

	public static Square getRandomSquareInBuilding(Structure building) {

		ArrayList<Square> randomSquares = new ArrayList<Square>();
		for (StructureRoom room : building.rooms) {
			randomSquares.add(getRandomSquareInRoom(room));
		}

		Random random = new Random();
		return randomSquares.get(random.nextInt(randomSquares.size()));
	}

	public static Square getRandomSquareInRoom(StructureRoom room) {

		Square potentialSquare = room.squares.get(new Random().nextInt(room.squares.size()));
		if (potentialSquare.inventory.canShareSquare)
			return potentialSquare;
		return null;
	}

	public static Square getRandomSquareInStructureSection(StructureSection structureSection) {
		int randomX = (structureSection.gridX1 + 1)
				+ (int) Math.round((Math.random() * ((structureSection.gridX2 - 1) - (structureSection.gridX1 + 1))));
		int randomY = (structureSection.gridY1 + 1)
				+ (int) Math.round((Math.random() * ((structureSection.gridY2 - 1) - (structureSection.gridY1 + 1))));

		if (randomX > 0) {
			if (Game.level.squares[randomX - 1][randomY].inventory.containsGameObjectOfType(Door.class)) {
				return null;
			}
		}

		if (randomX < Game.level.squares.length - 1) {
			if (Game.level.squares[randomX + 1][randomY].inventory.containsGameObjectOfType(Door.class)) {
				return null;
			}

		}

		if (randomY > 0) {
			if (Game.level.squares[randomX][randomY - 1].inventory.containsGameObjectOfType(Door.class)) {
				return null;
			}
		}

		if (randomY < Game.level.squares[0].length - 1) {
			if (Game.level.squares[randomX][randomY + 1].inventory.containsGameObjectOfType(Door.class)) {
				return null;
			}

		}
		Square potentialSquare = Game.level.squares[randomX][randomY];
		if (potentialSquare.inventory.canShareSquare)
			return potentialSquare;
		return null;
	}

	// public static Comparator<Actor> sortAttackers = new Comparator<Actor>() {
	//
	// @Override
	// public int compare(Actor a, Actor b) {
	//
	// Path pathA = getBestPathToAttackTarget(a);
	// int travelCostA = Integer.MAX_VALUE;
	// if (pathA != null)
	// travelCostA = pathA.travelCost;
	//
	// Path pathB = getBestPathToAttackTarget(b);
	// int travelCostB = Integer.MAX_VALUE;
	// if (pathB != null)
	// travelCostB = pathB.travelCost;
	//
	// return travelCostA - travelCostB;
	// }
	// };

	public static Comparator<GameObject> sortAttackers = new Comparator<GameObject>() {

		@Override
		public int compare(GameObject a, GameObject b) {

			if (a instanceof Actor && !(b instanceof Actor)) {
				return -1;
			}
			if (b instanceof Actor && !(a instanceof Actor)) {
				return 1;
			}

			return Game.level.activeActor.straightLineDistanceTo(a.squareGameObjectIsOn)
					- Game.level.activeActor.straightLineDistanceTo(b.squareGameObjectIsOn);
		}
	};

	public static Comparator<GameObject> sortByStraightLineDistance = new Comparator<GameObject>() {

		@Override
		public int compare(GameObject a, GameObject b) {

			int distanceA = Game.level.activeActor.straightLineDistanceTo(a.squareGameObjectIsOn);
			int distanceB = Game.level.activeActor.straightLineDistanceTo(b.squareGameObjectIsOn);

			return distanceA - distanceB;
		}
	};

	public static Comparator<Square> sortLocationsToSearch = new Comparator<Square>() {

		@Override
		public int compare(Square a, Square b) {

			AIPath pathA = Game.level.activeActor.getPathTo(a);
			int travelCostA = pathA.travelCost;

			AIPath pathB = Game.level.activeActor.getPathTo(b);
			int travelCostB = pathB.travelCost;

			return travelCostB - travelCostA;
		}
	};

}
