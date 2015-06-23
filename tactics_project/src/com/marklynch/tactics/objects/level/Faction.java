package com.marklynch.tactics.objects.level;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.Path;

public class Faction {

	enum AI_MODE {
		TARGET_SPECIFIC_OBJECT,
		TARGET_NEAREST_ENEMY,
		TARGET_NEAREST_ENEMY_IN_A_FACTION,
		TARGET_WEAKEST_ENEMY,
		TARGET_WEAKEST_ENEMY_IN_A_FACTION,
		RANDOM_ATTACK_ENEMIES_ONLY,
		RANDOM_ATTACK_ENEMIES_AND_ALLIES,
		RANDOM_ATTACK_ENEMIES_AND_ALLIES_AND_OBJECT,
		TARGET_NEAREST_OBJECT
	};

	public String name;

	/**
	 * Map relationships of this faction towards others +-100
	 */
	public Map<Faction, Integer> relationships = new HashMap<Faction, Integer>();
	public Vector<Actor> actors = new Vector<Actor>();
	public Level level;

	public Actor currentActor;
	public int currentActorIndex = 0;

	public Color color;

	public enum STAGE {
		SELECT, MOVE, ATTACK
	};

	public STAGE currentStage = STAGE.SELECT;
	public int timeAtCurrentStage = 0;
	public final int STAGE_DURATION = 1000;

	public Faction(String name, Level level, Color color) {
		this.name = name;
		this.level = level;
		this.color = color;
	}

	public void update(int delta) {

		if (currentStage == STAGE.SELECT) {
			// Selecting a hero
			if (timeAtCurrentStage == 0) {
				// start of stage, perform action
				currentActor = actors.get(currentActorIndex);
				level.activeActor = currentActor;
				level.activeActor.calculatePathToAllSquares(level.squares);
				level.activeActor.calculateReachableSquares(level.squares);
				level.activeActor.calculateAttackableSquares(level.squares);
				timeAtCurrentStage += delta;
			} else if (timeAtCurrentStage >= STAGE_DURATION) {
				currentStage = STAGE.MOVE;
				timeAtCurrentStage = 0;
			} else {
				timeAtCurrentStage += delta;
			}
		} else if (currentStage == STAGE.MOVE) {
			// Moving with selected hero
			if (timeAtCurrentStage == 0) {
				// start of stage, perform action

				boolean performedMove = moveTowardsTarget(level.factions.get(0).actors
						.get(0));
				if (performedMove == false) {
					currentStage = STAGE.ATTACK;
					timeAtCurrentStage = 0;
				} else {
					timeAtCurrentStage += delta;

				}

				// moveTowardsTarget(level.inanimateObjects.elementAt(0));
				// moveToRandomSquare();
				// TODO lure enemy in (like in WOW)
				// TODO moveTowardsNearestEnemy
				// TODO moveTowardsNearestObjecy

				// /////////////////////////////////////////////////////////
			} else if (timeAtCurrentStage >= STAGE_DURATION) {
				// end of move phase
				currentStage = STAGE.ATTACK;
				timeAtCurrentStage = 0;
			} else {
				timeAtCurrentStage += delta;
			}
		} else if (currentStage == STAGE.ATTACK) {
			// Attacking with selected hero

			if (timeAtCurrentStage == 0) {
				// start of attack phase, act
				// attackRandomEnemy();
				// attackRandomEnemyOrAlly();

				boolean performedAttack = attackTarget(level.factions.get(0).actors
						.get(0));

				if (performedAttack == false) {
					currentStage = STAGE.SELECT;
					timeAtCurrentStage = 0;

					if (actors.size() <= currentActorIndex) {
						// last dude in the array died, next faction's turn
						currentActorIndex = 0;
						level.endTurn();
					} else {
						if (currentActor != actors.get(currentActorIndex)) {
							// actor died, so the array has shifted...
							// next actor is at his old position in the array,
							// so don't increment
							// index
						} else {
							// actor didnt die, proceed as expected...
							currentActorIndex++;
						}
						if (currentActorIndex >= actors.size()) {
							currentActorIndex = 0;
							level.endTurn();
						}
					}
				} else {
					timeAtCurrentStage += delta;
				}

				// /////////////////////////////////////////////////////////
			} else if (timeAtCurrentStage >= STAGE_DURATION) {
				// end of attack phase
				currentStage = STAGE.SELECT;
				timeAtCurrentStage = 0;

				currentActorIndex++;
				if (currentActorIndex >= actors.size()) {
					currentActorIndex = 0;
					level.endTurn();
				}

			} else {
				timeAtCurrentStage += delta;
			}

		}
	}

	class PathComparator implements Comparator<Path> {
		@Override
		public int compare(Path a, Path b) {
			return a.travelCost < b.travelCost ? -1
					: a.travelCost == b.travelCost ? 0 : 1;
		}
	}

	public boolean moveTowardsTarget(GameObject target) {

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

		this.currentActor.calculatePathToAllSquares(level.squares);

		Vector<Integer> idealWeaponDistances = new Vector<Integer>();
		idealWeaponDistances.add(3);
		// TODO this needs to be calculated based on
		// weapons available and the taret and their weapons
		// TODO what if we're stuck being closed than ideal distance, need to
		// run through this, and there could be a list of ideal distances......
		// :/
		// TODO ideal weapon distance could be on the other side of an object...
		// need to factor this in when choosing a good square :/, when talking
		// about targets squares I need to make list of attack squares, this
		// shit is heavy

		Vector<Square> targetSquares = new Vector<Square>();
		int bestTravelCostFound = Integer.MAX_VALUE;
		Path pathToSquare = null;
		for (int i = 0; i < idealWeaponDistances.size(); i++) {

			// Check if we're already at this ditance
			if (level.activeActor.weaponDistanceTo(target.squareGameObjectIsOn) == idealWeaponDistances
					.get(i))
				return false;

			targetSquares = target.getAllSquaresAtDistance(idealWeaponDistances
					.get(i));

			// TODO picking which of these squares is the best is an interesting
			// issue.
			// Reachable is best.
			// if There's multiple reachable then safest out of them is best :P
			// OR somewhere u can attack someone from is the best... i dunno :D
			for (Square targetSquare : targetSquares) {
				Path currentActorPathToThisSquare = currentActor.paths
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
			return false;
		}

		// TODO move this to an actor method called moveAlongPath
		Square squareToMoveTo = null;
		// squareToMoveTo = pathToSquare.squares.lastElement(); this line works,
		// but allows CPU to cheat
		if (pathToSquare.travelCost <= currentActor.travelDistance) {
			squareToMoveTo = pathToSquare.squares.lastElement();
		} else {
			for (int i = pathToSquare.squares.size() - 1; i >= 0; i--) {
				if (currentActor.paths.get(pathToSquare.squares.get(i)).travelCost <= currentActor.travelDistance) {
					squareToMoveTo = pathToSquare.squares.get(i);
					break;
				}
			}
		}

		if (squareToMoveTo != null) {
			level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public boolean moveToRandomSquare() {
		// MOVE TO RANDOM SQUARE - maybe for a broken robot or confused
		// enemy
		Vector<Square> reachableSquares = new Vector<Square>();
		for (int j = 0; j < level.squares.length; j++) {
			for (int k = 0; k < level.squares[0].length; k++) {
				if (level.squares[j][k].reachableBySelectedCharater) {
					reachableSquares.add(level.squares[j][k]);
				}
			}
		}
		if (reachableSquares.size() > 0) {
			int random = (int) (Math.random() * (reachableSquares.size() - 1));
			Square squareToMoveTo = reachableSquares.get(random);
			level.activeActor.moveTo(squareToMoveTo);
			return true;
		} else {
			return false;
		}
	}

	public boolean attackRandomEnemy() {

		// make a list of attackable enemies
		Vector<Actor> attackableActors = new Vector<Actor>();
		for (Faction faction : level.factions) {
			for (Actor actor : faction.actors) {
				int weaponDistance = level.activeActor
						.weaponDistanceTo(actor.squareGameObjectIsOn);

				if (faction != this
						&& level.activeActor.hasRange(weaponDistance)) {
					attackableActors.add(actor);
				}
			}
		}

		if (attackableActors.size() > 0) {
			int random = (int) (Math.random() * (attackableActors.size() - 1));
			Actor actorToAttack = attackableActors.get(random);
			level.activeActor.attack(actorToAttack, false);
			level.activeActor.highlightSelectedCharactersSquares(level);
			return true;
		} else {
			return false;
		}
	}

	public boolean attackRandomEnemyOrAlly() {
		// TODO needs to be tested
		// make a list of attackable enemies
		Vector<Actor> attackableActors = new Vector<Actor>();
		for (Faction faction : level.factions) {
			for (Actor actor : faction.actors) {
				int weaponDistance = level.activeActor
						.weaponDistanceTo(actor.squareGameObjectIsOn);
				if (actor != level.activeActor
						&& level.activeActor.hasRange(weaponDistance)) {
					attackableActors.add(actor);
				}
			}
		}

		if (attackableActors.size() > 0) {
			int random = (int) (Math.random() * (attackableActors.size() - 1));
			Actor actorToAttack = attackableActors.get(random);
			level.activeActor.attack(actorToAttack, false);
			level.activeActor.highlightSelectedCharactersSquares(level);
			return true;
		} else {
			return false;
		}
	}

	private boolean attackTarget(GameObject gameObject) {
		int weaponDistance = level.activeActor
				.weaponDistanceTo(gameObject.squareGameObjectIsOn);
		if (level.activeActor.hasRange(weaponDistance)) {
			level.activeActor.attack(gameObject, false);
			Actor.highlightSelectedCharactersSquares(level);
			return true;
		} else {
			return attackRandomEnemy();
		}

	}
}
