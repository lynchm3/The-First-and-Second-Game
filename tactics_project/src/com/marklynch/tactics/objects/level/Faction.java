package com.marklynch.tactics.objects.level;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.Path;

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

				// MOVE TOWARDS A POINT
				// get as close to the point as possible
				// with as few moves as possible

				// for now... just go for this guy...
				Actor target = level.factions.get(0).actors.get(0);
				target.calculatePathToAllSquares(level.squares);
				this.currentActor.calculatePathToAllSquares(level.squares);
				// get matching squares in paths (only reachable squares for
				// current actor)

				// Get squares reachable by currentActor
				Vector<Square> currentActorReachableSquares = new Vector<Square>();
				for (Square square : currentActor.paths.keySet()) {
					if (currentActor.paths.get(square).travelCost <= currentActor.travelDistance)
						currentActorReachableSquares.add(square);
				}

				// Get all squares not blocked off by target
				Vector<Path> targetPaths = new Vector<Path>();
				for (Path path : target.paths.values()) {
					targetPaths.add(path);
				}
				targetPaths.sort(new PathComparator());

				// Get squares that are in both sets, as close as possible to
				// the target
				int bestDistanceFoundTarget = Integer.MAX_VALUE;
				Vector<Square> potentialSquares = new Vector<Square>();
				for (Path path : targetPaths) {
					if (path.travelCost > bestDistanceFoundTarget)
						break;

					if (currentActorReachableSquares.contains(path.squares
							.lastElement())) {
						potentialSquares.add(path.squares.lastElement());
						bestDistanceFoundTarget = path.travelCost;
					}
				}

				// get one closest to current actor
				int bestDistanceFoundCurrentActor = Integer.MAX_VALUE;
				Square squareToMoveTo = null;
				for (Square square : potentialSquares) {
					if (currentActor.paths.get(square).travelCost < bestDistanceFoundCurrentActor) {
						bestDistanceFoundCurrentActor = currentActor.paths
								.get(square).travelCost;
						squareToMoveTo = square;
					}
				}

				level.selectedActor.squareGameObjectIsOn.gameObject = null;
				level.selectedActor.squareGameObjectIsOn = null;
				level.selectedActor.distanceMovedThisTurn += squareToMoveTo.distanceToSquare;
				level.selectedActor.squareGameObjectIsOn = squareToMoveTo;
				squareToMoveTo.gameObject = level.selectedActor;
				level.selectedActor.calculatePathToAllSquares(level.squares);
				level.selectedActor.calculateReachableSquares(level.squares);
				level.selectedActor.calculateAttackableSquares(level.squares);

				// TODO
				// currently if there's no path it crashes
				// also... stay still fi ur already at the best part :P, issue
				// is, its not in target's list of paths
				// ehhhhhhhhhh.... if it's not fully reachable then just go
				// closest as the crow flies?
				// also... have ideal distance (for ranged VS melee for e.g.)

				// /////////////////////////////////////////////////////////
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

	class PathComparator implements Comparator<Path> {
		@Override
		public int compare(Path a, Path b) {
			return a.travelCost < b.travelCost ? -1
					: a.travelCost == b.travelCost ? 0 : 1;
		}
	}

	public void moveToRandomSquare() {
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
			level.selectedActor.squareGameObjectIsOn.gameObject = null;
			level.selectedActor.squareGameObjectIsOn = null;
			level.selectedActor.distanceMovedThisTurn += squareToMoveTo.distanceToSquare;
			level.selectedActor.squareGameObjectIsOn = squareToMoveTo;
			squareToMoveTo.gameObject = level.selectedActor;
			level.selectedActor.calculatePathToAllSquares(level.squares);
			level.selectedActor.calculateReachableSquares(level.squares);
			level.selectedActor.calculateAttackableSquares(level.squares);
		}
	}
}
