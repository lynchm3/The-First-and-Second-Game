package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.ui.ActivityLog;

public class ActionFillSpecificContainer extends Action {

	public static final String ACTION_NAME = "Fill Container";

	ContainerForLiquids containerForLiquids;
	Liquid liquid;

	public ActionFillSpecificContainer(Actor performer, Liquid liquid, GameObject source,
			ContainerForLiquids containerForLiquids) {
		super(ACTION_NAME, textureFillContainer, performer, source);
		this.containerForLiquids = containerForLiquids;
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

		Liquid water = Templates.WATER.makeCopy(null, performer, containerForLiquids.volume);

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " filled ", containerForLiquids, " with ", water }));

		System.out.println("liquid = " + liquid);
		System.out.println("liquid.jarForm = " + liquid.jarForm);
		System.out.println("containerForLiquids = " + containerForLiquids);
		System.out.println("containerForLiquids.owner = " + containerForLiquids.owner);
		GameObject newJar = liquid.jarForm.makeCopy(null, containerForLiquids.owner);
		performer.inventory.add(newJar);
		if (performer.equipped == containerForLiquids)
			performer.equipped = newJar;
		performer.inventory.remove(containerForLiquids);

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