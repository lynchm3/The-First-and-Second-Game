package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;

public class ActionFollow extends Action {

	public static final String ACTION_NAME = "Follow";

	public Player performer;
	public Actor target;

	// Default for hostiles
	public ActionFollow(Player player, Actor target) {
		super(ACTION_NAME, "action_move.png");
		super.gameObjectPerformer = this.performer = player;
		this.target = target;
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

		Player.playerTargetActor = target;

		if (Game.level.settingFollowPlayer && performer == Game.level.player && Game.level.player.onScreen()) {
			Game.level.cameraFollow = true;
		}
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkRange() {
		// TODO Auto-generated method stub
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
