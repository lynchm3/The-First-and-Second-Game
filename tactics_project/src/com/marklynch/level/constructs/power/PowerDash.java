package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.ResourceUtils;

public class PowerDash extends Power {

	private static String NAME = "Dash";

	public PowerDash(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("right.png", false), source, new Effect[] {}, 3,
				Power.castLocationsOnly2, new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerDash(source);
	}

	@Override
	public void cast(final Actor source, GameObject targetGameObject, final Square attemptedTargetSquare,
			final Action action) {

		Direction direction = Direction.LEFT;

		if (attemptedTargetSquare.xInGrid < source.squareGameObjectIsOn.xInGrid) {
			direction = Direction.LEFT;
		} else if (attemptedTargetSquare.xInGrid > source.squareGameObjectIsOn.xInGrid) {
			direction = Direction.RIGHT;
		} else if (attemptedTargetSquare.yInGrid < source.squareGameObjectIsOn.yInGrid) {
			direction = Direction.UP;
		} else if (attemptedTargetSquare.yInGrid > source.squareGameObjectIsOn.yInGrid) {
			direction = Direction.DOWN;
		}

		int distance = 2;

		ArrayList<PushedObject> pushedObjects = new ArrayList<PushedObject>();
		int correctedDistance = push(source, direction, distance, false, pushedObjects);
		System.out.println("correctedDistance = " + correctedDistance);
		Square correctedTargetSquare = null;
		if (direction == direction.LEFT) {
			correctedTargetSquare = Level.squares[source.squareGameObjectIsOn.xInGrid
					- correctedDistance][source.squareGameObjectIsOn.yInGrid];
		} else if (direction == Direction.RIGHT) {
			correctedTargetSquare = Level.squares[source.squareGameObjectIsOn.xInGrid
					+ correctedDistance][source.squareGameObjectIsOn.yInGrid];
		} else if (direction == Direction.UP) {
			correctedTargetSquare = Level.squares[source.squareGameObjectIsOn.xInGrid][source.squareGameObjectIsOn.yInGrid
					- correctedDistance];
		} else /* direction == direction.DOWN */ {
			correctedTargetSquare = Level.squares[source.squareGameObjectIsOn.xInGrid][source.squareGameObjectIsOn.yInGrid
					+ correctedDistance];
		}

		pushedObjects.add(new PushedObject(source, correctedTargetSquare));

		for (final PushedObject pushedObject : pushedObjects) {

			System.out.println("pushedObject = " + pushedObject.gameObject + ", " + pushedObject.destinationSquare);

			pushedObject.gameObject.setPrimaryAnimation(new AnimationStraightLine(pushedObject.gameObject, 2f, true,
					new Square[] { pushedObject.destinationSquare }) {
				@Override
				public void runCompletionAlgorightm(boolean wait) {
					super.runCompletionAlgorightm(wait);
					postRangedAnimation(pushedObject.gameObject, pushedObject.destinationSquare);
				}
			});

		}
	}

	public int push(GameObject source, Direction direction, int attemptedDistance, boolean doAnimation,
			ArrayList<PushedObject> pushedObjects) {

		System.out.println("PUSH " + source + ", " + direction + ", " + attemptedDistance + ", " + doAnimation);

		// int actualPush = attemptedDistance;
		// boolean hitWall = false;
		for (int i = 1; i <= attemptedDistance; i++) {
			Square square = null;
			int squareX;
			int squareY;
			if (direction == Direction.LEFT) {
				squareX = source.squareGameObjectIsOn.xInGrid - i;
				squareY = source.squareGameObjectIsOn.yInGrid;
			} else if (direction == Direction.RIGHT) {
				squareX = source.squareGameObjectIsOn.xInGrid + i;
				squareY = source.squareGameObjectIsOn.yInGrid;
			} else if (direction == Direction.UP) {
				squareX = source.squareGameObjectIsOn.xInGrid;
				squareY = source.squareGameObjectIsOn.yInGrid - i;
			} else /* direction == direction.DOWN */ {
				squareX = source.squareGameObjectIsOn.xInGrid;
				squareY = source.squareGameObjectIsOn.yInGrid + i;
			}

			if (squareX > 0 && squareY > 0 && squareX < Level.squares.length && squareY < Level.squares[0].length) {
				square = Level.squares[squareX][squareY];
				System.out.println("checking square " + square);
				if (square.inventory.contains(Wall.class)) {
					System.out.println("hit wall, returning");
					return i - 1;
				} else {
					final GameObject gameObjectThatCantShareSquare = square.inventory.gameObjectThatCantShareSquare;
					System.out.println("hit gameObject " + gameObjectThatCantShareSquare);
					if (gameObjectThatCantShareSquare != null) {

						int recursiveDistance = push(gameObjectThatCantShareSquare, direction, attemptedDistance, true,
								pushedObjects);
						Square recursiveSquare;
						if (direction == direction.LEFT) {
							recursiveSquare = Level.squares[gameObjectThatCantShareSquare.squareGameObjectIsOn.xInGrid
									- recursiveDistance][gameObjectThatCantShareSquare.squareGameObjectIsOn.yInGrid];
						} else if (direction == Direction.RIGHT) {
							recursiveSquare = Level.squares[gameObjectThatCantShareSquare.squareGameObjectIsOn.xInGrid
									+ recursiveDistance][gameObjectThatCantShareSquare.squareGameObjectIsOn.yInGrid];
						} else if (direction == Direction.UP) {
							recursiveSquare = Level.squares[gameObjectThatCantShareSquare.squareGameObjectIsOn.xInGrid][gameObjectThatCantShareSquare.squareGameObjectIsOn.yInGrid
									- recursiveDistance];
						} else /* direction == direction.DOWN */ {
							recursiveSquare = Level.squares[gameObjectThatCantShareSquare.squareGameObjectIsOn.xInGrid][gameObjectThatCantShareSquare.squareGameObjectIsOn.yInGrid
									+ recursiveDistance];
						}

						// public AnimationStraightLine(GameObject projectileObject, float speed,
						// boolean blockAI, Square... targetSquares) {

						pushedObjects.add(new PushedObject(gameObjectThatCantShareSquare, recursiveSquare));

						// gameObjectThatCantShareSquare.setPrimaryAnimation(
						// new AnimationStraightLine(gameObjectThatCantShareSquare, 2f, true,
						// finaledSquare) {
						// @Override
						// public void runCompletionAlgorightm(boolean wait) {
						// super.runCompletionAlgorightm(wait);
						// postRangedAnimation(gameObjectThatCantShareSquare, finaledSquare);
						// // postRangedAnimation(arrow);
						// }
						// });

						int distanceTargetIsPushed = i - 1 + recursiveDistance;
						if (distanceTargetIsPushed > attemptedDistance) {
							return attemptedDistance;
						} else {
							return distanceTargetIsPushed;
						}
					}
				}
			} else {

				// Outside of grid
				return i - 1;
			}
		}

		return attemptedDistance;
	}

	public void postAnimation(GameObject pushedGameObject, Action action, GameObject obstacle) {

		pushedGameObject.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, pushedGameObject.weight));
		if (obstacle != null) {
			obstacle.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, pushedGameObject.weight));
		}
	}

	public void drawUI() {

		ArrayList<PushedObject> pushedObjects = new ArrayList<PushedObject>();
	}

	public static class PushedObject {
		GameObject gameObject;
		Square destinationSquare;

		public PushedObject(GameObject gameObject, Square destinationSquare) {
			super();
			this.gameObject = gameObject;
			this.destinationSquare = destinationSquare;
		}

	}

}
