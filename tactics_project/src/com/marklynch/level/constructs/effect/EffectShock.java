package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.WaterSource;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.ui.ActivityLog;

public class EffectShock extends Effect {

	public EffectShock() {
	}

	public EffectShock(GameObject source, GameObject target, int totalTurns) {
		this.logString = " shocked by ";
		this.effectName = "Shock";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("spark.png", false);
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, 5));
	}

	@Override
	public void activate() {

		// float damage = 5 - (10 * (target.getEffectiveFireResistance() /
		// 100f));
		if (target instanceof FlammableLightSource) {
			((FlammableLightSource) target).setLighting(true);
			turnsRemaining = 0;
			return;
		} else {
			float damage = target.changeHealth(this, null, this);
			if (Game.level.shouldLog(target))
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		}
		// target.attackedBy(this, null);

		// Spread fire if not turn 1
		if (totalTurns == turnsRemaining) {

			// If in world (not in inventory)
			Square squareTargetIsOn = target.squareGameObjectIsOn;
			if (squareTargetIsOn != null) {
				ArrayList<Square> adjacentSquares = target.getAllSquaresAtDistance(1);
				for (Square adjacentSquare : adjacentSquares) {
					for (GameObject gameObject : adjacentSquare.inventory.getGameObjects()) {
						if (gameObject instanceof WaterSource) {
							gameObject.addEffect(this.makeCopy(source, gameObject));
//							if (Game.level.shouldLog(gameObject))
//								Game.level
//										.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
						}
					}
				}
			}
		}

		turnsRemaining--;

	}

	@Override
	public EffectShock makeCopy(GameObject source, GameObject target) {
		return new EffectShock(source, target, totalTurns);
	}

}
