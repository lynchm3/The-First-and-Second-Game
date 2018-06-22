package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.popups.Notification;
import com.marklynch.utils.ResourceUtils;

public class PowerTeleportOther extends Power {

	private static String NAME = "Teleport";

	public PowerTeleportOther(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("up.png", false), source, new Effect[] {}, 20,
				new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTeleportOther(source);
	}

	@Override
	public void cast(Actor source, GameObject targetGameObject, Square targetSquare, Action action) {

		GameObject objectToTeleport = targetGameObject;
		if (objectToTeleport == null)
			objectToTeleport = targetSquare.inventory.gameObjectThatCantShareSquare;
		if (objectToTeleport == null) {
			Game.level.addNotification(new Notification(new Object[] { "Target unclear" }, null, null));
			return;
		}

		Level.levelMode = LevelMode.LEVEL_SELECT_TELEPORT_SQUARE;
		Level.teleportee = objectToTeleport;
	}

}
