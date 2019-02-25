package com.marklynch.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Player;

public class ActionFollow extends Action {

	public static final String ACTION_NAME = "Follow";

	// public Player performer;
	// public Actor target;

	// Default for hostiles
	public ActionFollow(Player player, Actor target) {
		super(ACTION_NAME, textureWalk, player, target);
		// this.target = target;
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

		new ActionWait(performer, performer.squareGameObjectIsOn).perform();

		// if (Game.level.settingFollowPlayer && performer == Game.level.player &&
		// Game.level.player.onScreen()) {
		// Game.level.cameraFollow = true;
		// }

		// performer.actionsPerformedThisTurn.add(this);
		// if (sound != null)
		// sound.play();
		//
		// if (performer == Game.level.player && Game.level.activeActor ==
		// Game.level.player)
		// Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 3) {
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

	@Override
	public boolean shouldContinue() {

		if (Player.inFight()) {
			return false;
		}

		return true;
	}

}
