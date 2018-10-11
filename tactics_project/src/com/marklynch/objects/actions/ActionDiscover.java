package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Vein;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.ui.ActivityLog;

public class ActionDiscover extends Action {

	public static final String ACTION_NAME = "Discover";
	Discoverable target;

	public ActionDiscover(GameObject performer, Discoverable target) {
		super(ACTION_NAME, "action_die.png");
		super.gameObjectPerformer = this.gameObjectPerformer = performer;
		this.target = target;
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

		target.discovered();

		if (Game.level.shouldLog(gameObjectPerformer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " discovered ", target }));

		gameObjectPerformer.actionsPerformedThisTurn.add(this);

		if (sound != null)
			sound.play();
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

	public void logDeath() {

		if (gameObjectPerformer instanceof RockGolem) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { gameObjectPerformer.destroyedBy, " broke ", gameObjectPerformer, this.image }));

		} else if (gameObjectPerformer instanceof Actor && gameObjectPerformer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " burned to death ", this.image }));
		} else if (gameObjectPerformer instanceof Vein) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " was depleted ", this.image }));
		} else if (gameObjectPerformer.destroyedByAction instanceof ActionSmash) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " smashed ", this.image }));
		} else if (gameObjectPerformer instanceof Tree
				&& gameObjectPerformer.destroyedByAction instanceof ActionChoppingStart) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " was chopped down ", this.image }));
		} else if (gameObjectPerformer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " burned down ", this.image }));
		} else if (gameObjectPerformer instanceof Actor) {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { gameObjectPerformer.destroyedBy, " killed ", gameObjectPerformer, this.image }));

		} else {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer.destroyedBy, " destroyed a ",
						gameObjectPerformer, this.image }));

		}
	}
}
