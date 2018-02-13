package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class EffectHeal extends Effect {

	public EffectHeal(GameObject source, GameObject target, int totalTurns) {
		this.logString = " healed by ";
		this.effectName = "Heal";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("action_heal.png");
	}

	public EffectHeal(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		if (target instanceof Actor) {
			float healing = 10;
			if (target.remainingHealth + healing > target.totalHealth) {
				healing = target.totalHealth - target.remainingHealth;
			}

			// if (target instanceof Undead) {
			// target.remainingHealth -= healing;
			// if (Game.level.shouldLog(target))
			// Game.level.logOnScreen(
			// new ActivityLog(new Object[] { target, " lost " + healing + " HP
			// to ", this }));
			// target.attackedBy(this, null);
			// } else {
			target.changeHealth(this, null, this, healing);
			if (Game.level.shouldLog(target))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { target, " gained " + healing + " HP from ", this }));
			// }
		}
		turnsRemaining--;
	}

	@Override
	public EffectHeal makeCopy(GameObject source, GameObject target) {
		return new EffectHeal(source, target, totalTurns);
	}

}
