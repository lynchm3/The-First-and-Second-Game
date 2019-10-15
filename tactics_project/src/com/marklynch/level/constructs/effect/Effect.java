package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.Stats;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.utils.DamageDealer;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Color;
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

	public Stats highLevelStats = new Stats();

	public Effect() {

		highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(HIGH_LEVEL_STATS.STRENGTH, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(HIGH_LEVEL_STATS.DEXTERITY, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(HIGH_LEVEL_STATS.ENDURANCE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(HIGH_LEVEL_STATS.INTELLIGENCE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.FRIENDLY_FIRE, new Stat(HIGH_LEVEL_STATS.FRIENDLY_FIRE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(HIGH_LEVEL_STATS.PIERCE_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(HIGH_LEVEL_STATS.FIRE_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(HIGH_LEVEL_STATS.WATER_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE, new Stat(HIGH_LEVEL_STATS.POISON_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLEED_DAMAGE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING, new Stat(HIGH_LEVEL_STATS.HEALING, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(HIGH_LEVEL_STATS.SLASH_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(HIGH_LEVEL_STATS.PIERCE_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(HIGH_LEVEL_STATS.BLUNT_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(HIGH_LEVEL_STATS.WATER_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(HIGH_LEVEL_STATS.ELECTRICAL_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(HIGH_LEVEL_STATS.POISON_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(HIGH_LEVEL_STATS.BLEED_RES, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(HIGH_LEVEL_STATS.HEALING_RES, 0));

	}

	public void activate() {
	}

	public Effect makeCopy(GameObject source, GameObject target) {
		return new EffectWet(source, target, totalTurns);
	}

	private static final int height = 32;

	public void draw2(int offsetY) {
		// Draw object

		if (!target.squareGameObjectIsOn.visibleToPlayer)
			return;

		if (target.squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGridPixels + target.drawOffsetX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGridPixels + target.drawOffsetY);
			if (target != null && target.getPrimaryAnimation() != null) {
				actorPositionXInPixels += target.getPrimaryAnimation().offsetX;
				actorPositionYInPixels += target.getPrimaryAnimation().offsetY;
			}

			float alpha = 1.0f;
			alpha = 0.75f;
			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
					actorPositionYInPixels + offsetY * height, actorPositionXInPixels + height,
					actorPositionYInPixels + offsetY * height + height, target.backwards);
		}
	}

	public void onAdd() {

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
	public CopyOnWriteArrayList<Object> getEffectiveHighLevelStatTooltip(HIGH_LEVEL_STATS statType) {
		CopyOnWriteArrayList<Object> result = new CopyOnWriteArrayList<Object>(Object.class);
		result.add(effectName + " " + highLevelStats.get(statType).value);
		return result;
	}

	public abstract Color getColor();
}
