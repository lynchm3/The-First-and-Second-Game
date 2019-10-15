package com.marklynch.level.constructs.power;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.Point;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionTakeItems;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.PressurePlate;
import com.marklynch.objects.inanimateobjects.PressurePlateRequiringSpecificItem;
import com.marklynch.utils.ResourceUtils;

public class PowerQuickFingers extends Power {

	private static String NAME = "Quick Fingers";

	public PowerQuickFingers() {
		this(null);
	}

	public PowerQuickFingers(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("left.png", false), source, new Effect[] {}, 0, null,
				new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		passive = true;
		endsTurn = false;
		activateAtStartOfTurn = true;
		description = "Automatically pick up items within 1 square that don't belong to anyone";
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {

		CopyOnWriteArrayList<GameObject> gameObjectsToTake = new CopyOnWriteArrayList<GameObject>();
		for (Square squareToPickupFrom : source.getAllSquaresWithinDistance(0, 1)) {

			if (squareToPickupFrom.inventory.containsGameObjectOfType(PressurePlate.class)
					|| squareToPickupFrom.inventory
							.containsGameObjectOfType(PressurePlateRequiringSpecificItem.class)) {
				continue;
			}

			gameObjectsToTake.clear();
			for (GameObject gameObject : squareToPickupFrom.inventory.gameObjects) {
				if (gameObject.owner == null && gameObject.fitsInInventory && !(gameObject instanceof Actor)
						&& gameObject.quest == null) {
					gameObjectsToTake.add(gameObject);
				}
			}

			for (GameObject gameObjectToTake : gameObjectsToTake) {
				new ActionTakeItems(source, squareToPickupFrom, gameObjectToTake).perform();
			}
		}

	}

	@Override
	public void log(GameObject performer, Square target2) {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ",
		// name }));
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerQuickFingers(source);
	}
}
