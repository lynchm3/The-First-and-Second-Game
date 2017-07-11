package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class EffectWet extends Effect {

	public EffectWet(GameObject source, GameObject target, int totalTurns) {
		this.logString = " wet by ";
		this.effectName = "Wet";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.image = getGlobalImage("effect_wet.png");
	}

	public EffectWet(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		turnsRemaining--;
	}

	@Override
	public EffectWet makeCopy(GameObject source, GameObject target) {
		return new EffectWet(source, target, totalTurns);
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

			TextureUtils.drawTexture(image, alpha, actorPositionXInPixels, actorPositionXInPixels + target.width,
					actorPositionYInPixels, actorPositionYInPixels + target.height, target.backwards);
			// TextureUtils.skipNormals = false;
		}
	}

}
