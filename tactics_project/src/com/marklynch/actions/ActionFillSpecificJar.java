package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Jar;
import com.marklynch.ui.ActivityLog;

public class ActionFillSpecificJar extends Action {

	public static final String ACTION_NAME = "Fill Container";

	Jar jar;
	Liquid liquid;

	public ActionFillSpecificJar(Actor performer, Liquid liquid, GameObject source, Jar jar) {
		super(ACTION_NAME, textureFillContainer, performer, source);
		this.jar = jar;
		this.liquid = liquid;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		Liquid water = Templates.WATER.makeCopy(null, performer);

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " filled ", jar, " with ", water }));

		GameObject newJar = liquid.jarForm.makeCopy(null, jar.owner);
		performer.inventory.add(newJar);
		if (performer.equipped == jar)
			performer.equipped = newJar;
		performer.inventory.remove(jar);

		if (liquid.squareGameObjectIsOn != null) {
			liquid.squareGameObjectIsOn.inventory.remove(liquid);
		}
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}