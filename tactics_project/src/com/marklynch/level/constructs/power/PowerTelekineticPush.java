package com.marklynch.level.constructs.power;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.primary.AnimationPushed;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
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
	public void cast(Actor source, GameObject targetGameObject, Square targetSquare, Action action) {

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
		Square endSquare = lastSquare;
		HashMap<GameObject, Square> pushedObjectToStartSquare = new HashMap<GameObject, Square>();
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

			GameObject obstacle = null;
			if (!currentSquare.inventory.canShareSquare) {
				obstacle = currentSquare.inventory.gameObjectThatCantShareSquare;
				int damageToObstacle = 0;
				for (GameObject gameObject : (ArrayList<GameObject>) lastSquare.inventory.gameObjects.clone()) {

					// public float changeHealth(Object attacker, Action action, Stat damage) {
					gameObject.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, gameObject.weight));
					damageToObstacle += gameObject.weight;
				}
				obstacle.changeHealth(source, action, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, damageToObstacle));
				break;
			}

			ArrayList<GameObject> temp = new ArrayList<GameObject>();
			temp.addAll(lastSquare.inventory.gameObjects);

			for (GameObject gameObject : temp) {
				// gameObject.secondaryAnimations.add(new AnimationMovementFade(lastSquare,
				// gameObject));

				if (!pushedObjectToStartSquare.keySet().contains(gameObject)) {
					pushedObjectToStartSquare.put(gameObject, gameObject.squareGameObjectIsOn);
				}

				currentSquare.inventory.add(gameObject);
			}

			endSquare = lastSquare = currentSquare;

			pushCount++;

		}

		for (GameObject pushedGameObject : pushedObjectToStartSquare.keySet()) {
			pushedGameObject.primaryAnimation = new AnimationPushed(pushedGameObject,
					pushedObjectToStartSquare.get(pushedGameObject), endSquare);
		}
	}

}
