package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionSelectTeleportTarget extends Action {

	public static final String ACTION_NAME = "Teleport";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	GameObject teleportee;

	public ActionSelectTeleportTarget(Actor performer, GameObject teleportee) {
		super(ACTION_NAME, "action_teleport.png");
		super.gameObjectPerformer = this.performer = performer;
		this.teleportee = teleportee;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
		System.out.println("ActionTeleportOther");
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		Game.level.levelMode = LevelMode.LEVEL_SELECT_TELEPORT_SQUARE;
		Level.teleportee = teleportee;

	}

	@Override
	public boolean check() {

		return true;
	}

	@Override
	public boolean checkRange() {

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
