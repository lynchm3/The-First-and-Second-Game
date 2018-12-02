package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.DamageDealer;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public abstract class Effect implements DamageDealer {

	public transient String logString;

	public String effectName;

	public GameObject source;

	public GameObject target;

	public int totalTurns;

	public int turnsRemaining;

	public Texture imageTexture;

	public transient HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();

	public Effect() {

		highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FRIENDLY_FIRE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(0));

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
			if (target != null && target.getPrimaryAnimation() != null) {
				actorPositionXInPixels += target.getPrimaryAnimation().offsetX;
				actorPositionYInPixels += target.getPrimaryAnimation().offsetY;
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
	public float getEffectiveHighLevelStat(HIGH_LEVEL_STATS statType) {
		float result = highLevelStats.get(statType).value;
		return result;
	}

	@Override
	public ArrayList<Object> getEffectiveHighLevelStatTooltip(HIGH_LEVEL_STATS statType) {
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(effectName + " " + highLevelStats.get(statType).value);
		return result;
	}
}
