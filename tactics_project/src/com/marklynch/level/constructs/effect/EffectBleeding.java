package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class EffectBleeding extends Effect {

	public EffectBleeding(GameObject source, GameObject target, int totalTurns) {
		this.logString = " cut by ";
		this.effectName = "Bleed";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_bleed.png");
	}

	public EffectBleeding(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {

		if (!(target instanceof Actor))
			return;

		float damage = 5 - (10 * (target.getEffectiveSlashResistance() / 100f));
		target.remainingHealth -= damage;
		if (Game.level.shouldLog(target))
			Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		target.attackedBy(this, null);

		// Spread fire if not turn 1
		// if (totalTurns != turnsRemaining) {
		Game.level.inanimateObjectsToAdd.add(
				new InanimateObjectToAddOrRemove(Templates.BLOOD.makeCopy(null, null), target.squareGameObjectIsOn));
		// }

		turnsRemaining--;

	}

	@Override
	public EffectBleeding makeCopy(GameObject source, GameObject target) {
		return new EffectBleeding(source, target, totalTurns);
	}

}
