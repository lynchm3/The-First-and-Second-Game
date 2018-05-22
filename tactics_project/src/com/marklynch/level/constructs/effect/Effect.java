package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
import com.marklynch.objects.DamageDealer;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public abstract class Effect implements DamageDealer {

	public String logString;
	public String effectName;
	public GameObject source;
	public GameObject target;
	public int totalTurns;
	public int turnsRemaining;
	public Texture imageTexture;

	public HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();
	public HashMap<OFFENSIVE_STATS, Stat> offensiveStats = new HashMap<OFFENSIVE_STATS, Stat>();

	public Effect() {
		offensiveStats.put(OFFENSIVE_STATS.SLASH_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.BLUNT_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.PIERCE_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.FIRE_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.WATER_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.POISON_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.BLEED_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.HEALING, new Stat(0));
	}

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

	@Override
	public float getEffectiveOffensiveStat(OFFENSIVE_STATS statType) {
		float result = offensiveStats.get(statType).value;
		return result;
	}

	@Override
	public ArrayList<Object> getEffectiveOffensiveStatTooltip(OFFENSIVE_STATS statType) {
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(effectName + " " + offensiveStats.get(statType).value);
		return result;
	}

}
