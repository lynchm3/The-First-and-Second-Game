package com.marklynch.tactics.objects.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.marklynch.tactics.objects.unit.Actor;

public class Faction {

	public String name;
	public Map<Faction, Integer> relationships = new HashMap<Faction, Integer>();
	public Vector<Actor> actors = new Vector<Actor>();
	public Level level;

	public Actor currentActor;
	public int currentActorIndex = 0;

	public enum STAGE {
		SELECT, MOVE, ATTACK
	};

	public STAGE currentStage = STAGE.SELECT;
	public int timeAtCurrentStage = 0;
	public final int STAGE_DURATION = 1000;

	public Faction(String name, Level level) {
		this.name = name;
		this.level = level;
	}

	public void update(int delta) {

		// Selecting a hero
		if (currentStage == STAGE.SELECT) {
			if (timeAtCurrentStage == 0) {
				currentActor = actors.get(currentActorIndex);
				level.selectedActor = currentActor;
				level.selectedActor.calculatePathToAllSquares(level.squares);
				level.selectedActor.calculateReachableSquares(level.squares);
				level.selectedActor.calculateAttackableSquares(level.squares);
				timeAtCurrentStage += delta;
			} else if (timeAtCurrentStage >= STAGE_DURATION) {
				currentStage = STAGE.MOVE;
				timeAtCurrentStage = 0;
			} else {
				timeAtCurrentStage += delta;
			}
			// Moving with selected hero
		} else if (currentStage == STAGE.MOVE) {
			if (timeAtCurrentStage == 0) {

				// MOVE TOWARDS NEAREST ENEMY
				// a. it's gotta be a reachable enemy
				// b. gotta be by distace to a reachable square
				// level.selectedActor
				// .pathTo(level.factions.get(0).actors.get(0).squareGameObjectIsOn,
				// level.squares);
				// findNearestEnemy();

				// MOVE TO RANDOM SQUARE - maybe for a broken robot or confused
				// enemy
				// moveToRandomSquare()
				// ArrayList<Square> reachableSquares = new ArrayList<Square>();
				// for (int j = 0; j < level.squares.length; j++) {
				// for (int k = 0; k < level.squares[0].length; k++) {
				// if (level.squares[j][k].reachableBySelectedCharater) {
				// reachableSquares.add(level.squares[j][k]);
				// }
				// }
				// }
				// if (reachableSquares.size() > 0) {
				// int random = (int) (Math.random() * (reachableSquares
				// .size() - 1));
				// Square squareToMoveTo = reachableSquares.get(random);
				// level.selectedActor.squareGameObjectIsOn.gameObject = null;
				// level.selectedActor.squareGameObjectIsOn = null;
				// level.selectedActor.distanceMovedThisTurn +=
				// squareToMoveTo.distanceToSquare;
				// level.selectedActor.squareGameObjectIsOn = squareToMoveTo;
				// squareToMoveTo.gameObject = level.selectedActor;
				// level.selectedActor
				// .calculateReachableSquares(level.squares);
				// level.selectedActor
				// .calculateAttackableSquares(level.squares);
				// }

				timeAtCurrentStage += delta;
			} else if (timeAtCurrentStage >= STAGE_DURATION) {
				currentStage = STAGE.ATTACK;
				timeAtCurrentStage = 0;
			} else {
				timeAtCurrentStage += delta;
			}
			// Attacking with selected hero
		} else if (currentStage == STAGE.ATTACK) {
			currentActorIndex++;
			if (currentActorIndex >= actors.size()) {
				currentActorIndex = 0;
				currentStage = STAGE.SELECT;
				timeAtCurrentStage = 0;
				level.endTurn();
			}
		}
	}

	// a. it's gotta be a reachable enemy
	// b. gotta be by distace to a reachable square
	// Square[] findNearestEnemy() {
	//
	// // TODO do i need to exclude enemies that will never be reachable?
	// // I think i need a method PATH TO yep, and get the shortest one of
	// // those FUUUUUUUUUUUUUUCK
	//
	// // Create list of enemies -
	//
	// for (int j = 0; j < level.squares.length; j++) {
	// for (int k = 0; k < level.squares[0].length; k++) {
	//
	// }
	// }
	// }
}
