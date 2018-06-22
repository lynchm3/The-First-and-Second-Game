package com.marklynch.level.constructs.power;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.animation.primary.AnimationPushed;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.ResourceUtils;

public class PowerTelekineticPush extends Power {

	private static String NAME = "Telekinetic Push";

	public PowerTelekineticPush(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("right.png", false), source, new Effect[] {}, 1,
				new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTelekineticPush(source);
	}

	@Override
	public void cast(final Actor source, GameObject targetGameObject, Square targetSquare, final Action action) {

		if (targetSquare.inventory.contains(source))
			return;

		Direction direction = Direction.LEFT;

		if (targetSquare.xInGrid < source.squareGameObjectIsOn.xInGrid) {
			direction = Direction.LEFT;
		} else if (targetSquare.xInGrid > source.squareGameObjectIsOn.xInGrid) {
			direction = Direction.RIGHT;
		} else if (targetSquare.yInGrid < source.squareGameObjectIsOn.yInGrid) {
			direction = Direction.UP;
		} else if (targetSquare.yInGrid > source.squareGameObjectIsOn.yInGrid) {
			direction = Direction.DOWN;
		}

		int maxPushCount = 5;
		int pushCount = 0;
		Square lastSquare = targetSquare;
		Square tempEndSquare = lastSquare;
		HashMap<GameObject, Square> pushedObjectAndTheirStartSquare = new HashMap<GameObject, Square>();
		GameObject tempObstacle = null;
		// final HashMap<GameObject, Float> pushedObjectToDamageTaken = new
		// HashMap<GameObject, Float>();
		while (pushCount < maxPushCount) {

			Square currentSquare = null;
			if (direction == Direction.LEFT) {
				currentSquare = lastSquare.getSquareToLeftOf();
			} else if (direction == Direction.RIGHT) {
				currentSquare = lastSquare.getSquareToRightOf();

			} else if (direction == Direction.UP) {
				currentSquare = lastSquare.getSquareAbove();

			} else if (direction == Direction.DOWN) {
				currentSquare = lastSquare.getSquareBelow();

			}

			if (currentSquare == null) {
				// Square is outside the map, unlikely to happen...
				break;
			}

			// Hit an obstacle?
			if (!currentSquare.inventory.canShareSquare
					&& !(currentSquare.inventory.gameObjectThatCantShareSquare instanceof VoidHole)
					&& currentSquare.inventory.gameObjectThatCantShareSquare != null) {
				if (tempObstacle != null)
					tempObstacle = currentSquare.inventory.gameObjectThatCantShareSquare;
				for (GameObject gameObject : (ArrayList<GameObject>) lastSquare.inventory.gameObjects.clone()) {

					// public float changeHealth(Object attacker, Action action, Stat damage) {
					// pushedObjectToDamageTaken.put(gameObject, gameObject.weight);
					// damageToObstacle += gameObject.weight;
				}
				break;
			}

			// Pick up new objects
			ArrayList<GameObject> temp = new ArrayList<GameObject>();
			temp.addAll(lastSquare.inventory.gameObjects);
			for (GameObject gameObject : temp) {

				if (!gameObject.isFloorObject) {
					if (!pushedObjectAndTheirStartSquare.keySet().contains(gameObject)) {
						pushedObjectAndTheirStartSquare.put(gameObject, gameObject.squareGameObjectIsOn);
					}
				}
			}

			tempEndSquare = lastSquare = currentSquare;

			pushCount++;

		}

		final GameObject obstacle = tempObstacle;
		final Square endSquare = tempEndSquare;

		if (Game.level.shouldLog(source)) {
			source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation()));
		}

		for (final GameObject pushedGameObject : pushedObjectAndTheirStartSquare.keySet()) {
			endSquare.inventory.add(pushedGameObject);
			pushedGameObject.setPrimaryAnimation(
					new AnimationPushed(pushedGameObject, pushedObjectAndTheirStartSquare.get(pushedGameObject),
							endSquare, pushedGameObject.getPrimaryAnimation()) {
						@Override
						public void runCompletionAlgorightm() {
							super.runCompletionAlgorightm();
							postAnimation(pushedGameObject, action, obstacle);
						}
					}

			);

		}
	}

	public void postAnimation(GameObject pushedGameObject, Action action, GameObject obstacle) {

		pushedGameObject.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, pushedGameObject.weight));
		if (obstacle != null) {
			obstacle.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, pushedGameObject.weight));
		}
	}

}
