package com.marklynch.level.constructs.power;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.animation.primary.AnimationPushed;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Portal;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.utils.ResourceUtils;

public class PowerTelekineticPush extends Power {

	private static String NAME = "Telekinetic Push";

	public PowerTelekineticPush(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("right.png", false), source, new Effect[] {}, 2,
				Power.castLocationsLine2, new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTelekineticPush(source);
	}

	@Override
	public void cast(final GameObject source, GameObject targetGameObject, Square targetSquare, final Action action) {

		if (targetSquare.inventory.contains(source))
			return;

		if (source instanceof Actor)
			source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation(), null));

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
					&& !(currentSquare.inventory.gameObjectThatCantShareSquare instanceof Portal)
					&& currentSquare.inventory.gameObjectThatCantShareSquare != null) {
				if (tempObstacle != null)
					tempObstacle = currentSquare.inventory.gameObjectThatCantShareSquare;
			}

			// Pick up new objects
			ArrayList<GameObject> temp = new ArrayList<GameObject>();
			temp.addAll(lastSquare.inventory.gameObjects);
			for (GameObject gameObject : temp) {

				if (!gameObject.isFloorObject && gameObject.moveable) {
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

		for (final GameObject pushedGameObject : pushedObjectAndTheirStartSquare.keySet()) {
			pushedGameObject.setPrimaryAnimation(
					new AnimationPushed(pushedGameObject, pushedObjectAndTheirStartSquare.get(pushedGameObject),
							endSquare, pushedGameObject.getPrimaryAnimation(), new OnCompletionListener() {
								@Override
								public void animationComplete(GameObject gameObject) {
									postAnimation(pushedGameObject, action, obstacle, endSquare);
								}
							}));

		}
	}

	public void postAnimation(GameObject pushedGameObject, Action action, GameObject obstacle, Square endSquare) {

		// pushedGameObject.changeHealth(source, action, new
		// Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, pushedGameObject.weight));
		// if (obstacle != null) {
		// obstacle.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE,
		// pushedGameObject.weight));
		// }
	}

}
