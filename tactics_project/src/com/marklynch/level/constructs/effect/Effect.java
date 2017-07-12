package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public abstract class Effect {

	public String logString;
	public String effectName;
	public GameObject source;
	public GameObject target;
	public int totalTurns;
	public int turnsRemaining;
	public Texture image;

	public abstract void activate();

	public abstract Effect makeCopy(GameObject source, GameObject target);

	public void draw2() {
		// Draw object
		if (target.squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ target.drawOffsetX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ target.drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			// if (!target.squareGameObjectIsOn.visibleToPlayer)
			// alpha = 0.5f;
			// if (target.hiding)
			alpha = 0.75f;

			TextureUtils.drawTexture(image, alpha, actorPositionXInPixels, actorPositionXInPixels + target.width,
					actorPositionYInPixels, actorPositionYInPixels + target.height, target.backwards);
			// TextureUtils.skipNormals = false;
		}
	}

	public static void loadEffectImages() {
		getGlobalImage("effect_burn.png");
		getGlobalImage("effect_poison.png");
		getGlobalImage("effect_wet.png");
	}
}
