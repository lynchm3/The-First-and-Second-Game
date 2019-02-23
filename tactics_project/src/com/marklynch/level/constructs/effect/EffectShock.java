package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

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
		float damage = target.changeHealth(this, null, this);
		if (Game.level.shouldLog(target))
			Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));

		turnsRemaining--;

	}

	@Override
	public EffectShock makeCopy(GameObject source, GameObject target) {
		return new EffectShock(source, target, totalTurns);
	}

	public void onAdd() {

		// If in world (not in inventory)
		Square squareTargetIsOn = target.squareGameObjectIsOn;
		if (squareTargetIsOn != null) {
			ArrayList<Square> adjacentSquares = target.getAllSquaresWithinDistance(0, 1);
			for (Square adjacentSquare : adjacentSquares) {
				if (adjacentSquare.inventory.containsGameObjectWithTemplateId(Templates.WATER_SHALLOW.templateId)) {
					for (GameObject gameObject : adjacentSquare.inventory.getGameObjects()) {
						if (!gameObject.hasActiveEffectOfType(EffectShock.class)) {
							gameObject.addEffect(this.makeCopy(this.source, gameObject));
						}
					}
				}
			}
		}
	}

}
