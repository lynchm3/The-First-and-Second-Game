package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.DamageDealer;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public abstract class Effect implements DamageDealer {

	// weapons
	public float slashDamage = 0;
	public float pierceDamage = 0;
	public float bluntDamage = 0;
	public float fireDamage = 0; // fire/purify/clean
	public float waterDamage = 0; // water/life
	public float electricalDamage = 0; // lightning/light/electrical/speed
	public float poisonDamage = 0;// poison/ground/contaminate/neutralize/slow/corruption
	public float bleedDamage = 0;
	public float healing = 0;

	public String logString;
	public String effectName;
	public GameObject source;
	public GameObject target;
	public int totalTurns;
	public int turnsRemaining;
	public Texture imageTexture;

	public abstract void activate();

	public abstract Effect makeCopy(GameObject source, GameObject target);

	public void draw2() {
		// Draw object

		if (!target.squareGameObjectIsOn.visibleToPlayer)
			return;

		if (target.squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * target.drawOffsetRatioX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * target.drawOffsetRatioY);
			if (target != null) {
				actorPositionXInPixels += target.primaryAnimation.offsetX;
				actorPositionYInPixels += target.primaryAnimation.offsetY;
			}

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			// if (!target.squareGameObjectIsOn.visibleToPlayer)
			// alpha = 0.5f;
			// if (target.hiding)
			alpha = 0.75f;

			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + target.width, actorPositionYInPixels + target.height, target.backwards);
			// TextureUtils.skipNormals = false;
		}
	}

	public static void loadEffectImages() {
		getGlobalImage("effect_bleed.png", false);
		getGlobalImage("effect_burn.png", false);
		getGlobalImage("effect_poison.png", false);
		getGlobalImage("effect_wet.png", false);
	}

	// @Override
	// public void draw2() {
	// // Draw object
	// if (target.squareGameObjectIsOn != null) {
	//
	// int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGrid *
	// (int) Game.SQUARE_WIDTH
	// + target.drawOffsetX);
	// int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGrid *
	// (int) Game.SQUARE_HEIGHT
	// + target.drawOffsetY);
	//
	// float alpha = 1.0f;
	//
	// // TextureUtils.skipNormals = true;
	//
	// if (!target.squareGameObjectIsOn.visibleToPlayer)
	// alpha = 0.5f;
	// if (target.hiding)
	// alpha = 0.5f;
	//
	// TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
	// actorPositionXInPixels + target.width,
	// actorPositionYInPixels, actorPositionYInPixels + target.height,
	// target.backwards);
	// // TextureUtils.skipNormals = false;
	// }
	// }

	@Override
	public float getEffectiveSlashDamage() {
		return slashDamage;
	}

	@Override
	public String getEffectiveSlashDamageTooltip() {
		return this.effectName + " " + slashDamage;
	}

	@Override
	public float getEffectivePierceDamage() {
		return pierceDamage;
	}

	@Override
	public String getEffectivePierceDamageTooltip() {
		return this.effectName + " " + pierceDamage;
	}

	@Override
	public float getEffectiveBluntDamage() {
		return bluntDamage;
	}

	@Override
	public String getEffectiveBluntDamageTooltip() {
		return this.effectName + " " + bluntDamage;
	}

	@Override
	public float getEffectiveFireDamage() {
		return fireDamage;
	}

	@Override
	public String getEffectiveFireDamageTooltip() {
		return this.effectName + " " + fireDamage;
	}

	@Override
	public float getEffectiveWaterDamage() {
		return waterDamage;
	}

	@Override
	public String getEffectiveWaterDamageTooltip() {
		return this.effectName + " " + waterDamage;
	}

	@Override
	public float getEffectiveElectricalDamage() {
		return electricalDamage;
	}

	@Override
	public String getEffectiveElectricalDamageTooltip() {
		return this.effectName + " " + electricalDamage;
	}

	@Override
	public float getEffectivePoisonDamage() {
		return poisonDamage;
	}

	@Override
	public String getEffectivePoisonDamageTooltip() {
		return this.effectName + " " + poisonDamage;
	}

	@Override
	public float getEffectiveBleedDamage() {
		return poisonDamage;
	}

	@Override
	public String getEffectiveBleedDamageTooltip() {
		return this.effectName + " " + poisonDamage;
	}

	@Override
	public float getEffectiveHealing() {
		return healing;
	}

	@Override
	public String getEffectiveHealingTooltip() {
		return this.effectName + " " + healing;
	}

}
