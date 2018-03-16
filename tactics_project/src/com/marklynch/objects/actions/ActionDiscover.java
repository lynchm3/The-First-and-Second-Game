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
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject performer;
	Discoverable target;

	public ActionDiscover(GameObject performer, Discoverable target) {
		super(ACTION_NAME, "action_die.png");
		this.performer = performer;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
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

		target.discovered();

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " discovered ", target }));

		performer.actionsPerformedThisTurn.add(this);

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

		if (performer instanceof RockGolem) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer.destroyedBy, " broke ", performer, this.image }));

		} else if (performer instanceof Actor && performer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " burned to death ", this.image }));
		} else if (performer instanceof Vein) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " was depleted ", this.image }));
		} else if (performer.destroyedByAction instanceof ActionSmash) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " smashed ", this.image }));
		} else if (performer instanceof Tree && performer.destroyedByAction instanceof ActionChopStart) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " was chopped down ", this.image }));
		} else if (performer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " burned down ", this.image }));
		} else if (performer instanceof Actor) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer.destroyedBy, " killed ", performer, this.image }));

		} else {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer.destroyedBy, " destroyed a ", performer, this.image }));

		}
	}
}
