package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.TextureUtils;

public class EffectPoison extends Effect {

	public EffectPoison(GameObject source, GameObject target, int totalTurns) {
		this.logString = " poisoned by ";
		this.effectName = "Posion";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_posion.png");
	}

	public EffectPoison(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		float damage = 10 - (10 * (target.getEffectivePosionResistance() / 100f));
		target.remainingHealth -= damage;
		Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		target.attackedBy(this);
		turnsRemaining--;
	}

	@Override
	public EffectPoison makeCopy(GameObject source, GameObject target) {
		return new EffectPoison(source, target, totalTurns);
	}

	@Override
	public void draw2() {
		// Draw object
		if (target.squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ target.drawOffsetX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ target.drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!target.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			if (target.hiding)
				alpha = 0.5f;

			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionXInPixels + target.width,
					actorPositionYInPixels, actorPositionYInPixels + target.height, target.backwards);
			// TextureUtils.skipNormals = false;
		}
	}

}
