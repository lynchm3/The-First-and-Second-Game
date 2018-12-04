package com.marklynch.level.constructs.power;

import java.util.ArrayList;

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

public class PowerGrabber extends Power {

	private static String NAME = "Grabber";

	public PowerGrabber() {
		this(null);
	}

	public PowerGrabber(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("left.png", false), source, new Effect[] {}, 0, null,
				new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		passive = true;
		activateAtStartOfTurn = true;
		description = "Automatically pick up items within 1 square that don't belong to anyone";
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {

		ArrayList<GameObject> gameObjectsToTake = new ArrayList<GameObject>();
		for (Square squareToPickupFrom : source.getAllSquaresWithinDistance(0, 1)) {

			if (squareToPickupFrom.inventory.contains(PressurePlate.class)
					|| squareToPickupFrom.inventory.contains(PressurePlateRequiringSpecificItem.class)) {
				continue;
			}

			gameObjectsToTake.clear();
			for (GameObject gameObject : squareToPickupFrom.inventory.gameObjects) {
				if (gameObject.owner == null && gameObject.fitsInInventory && !(gameObject instanceof Actor)) {
					gameObjectsToTake.add(gameObject);
				}
			}
			if (gameObjectsToTake.size() > 0) {
				new ActionTakeItems(source, squareToPickupFrom, gameObjectsToTake).perform();
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
		return new PowerGrabber(source);
	}
}
