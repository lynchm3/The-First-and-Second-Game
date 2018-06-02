package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionTeleportOther;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerTeleport extends Power {

	private static String NAME = "Teleport";

	public PowerTeleport(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("up.png", false), source, new Effect[] {}, 10,
				new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTeleport(source);
	}

	@Override
	public void cast(Actor source, Square targetSquare, Action action) {

		GameObject objectToTeleport = targetSquare.inventory.gameObjectThatCantShareSquare;

		System.out.println("objectToTeleport = " + objectToTeleport);

		new ActionTeleportOther(source, objectToTeleport).perform();
	}

}
