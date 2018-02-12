package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionFillSpecificContainer extends Action {

	public static final String ACTION_NAME = "Fill Container";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	WaterSource waterSource;
	ContainerForLiquids containerForLiquids;

	public ActionFillSpecificContainer(Actor performer, WaterSource waterSource,
			ContainerForLiquids containerForLiquids) {
		super(ACTION_NAME, "action_fill_container.png");
		this.performer = performer;
		this.waterSource = waterSource;
		this.containerForLiquids = containerForLiquids;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (!checkRange())
			return;

		Liquid water = Templates.WATER.makeCopy(null, performer, containerForLiquids.volume);

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " filled ", containerForLiquids, " with ", water }));

		GameObject newJar = Templates.JAR_OF_WATER.makeCopy(null, containerForLiquids.owner);
		performer.inventory.add(newJar);
		if (performer.equipped == containerForLiquids)
			performer.equipped = newJar;
		performer.inventory.remove(containerForLiquids);
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(waterSource.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_DISABLED;
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