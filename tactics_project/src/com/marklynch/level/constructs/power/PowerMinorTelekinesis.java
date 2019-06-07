package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.actions.Action;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerMinorTelekinesis extends Power {

	private static String NAME = "Minor Telekinesis";

	public PowerMinorTelekinesis() {
		this(null);
	}

	public PowerMinorTelekinesis(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("power_minor_telekinesis.png", false), source, new Effect[] {}, 0,
				null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		passive = true;
		description = "Pull items from adjacent squares towards you";
		activateAtStartOfTurn = false;
		endsTurn = false;
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {

	}

	@Override
	public void log(GameObject performer, Square target2) {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ",
		// name }));
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerMinorTelekinesis(source);
	}
}
