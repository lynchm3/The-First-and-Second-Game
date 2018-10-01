package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;

public class TemplatesProjectiles {

	public TemplatesProjectiles() {

		Templates.ARROW = new Arrow();
		Templates.ARROW.name = "Arrow";
		Templates.ARROW.setImageAndExtrapolateSize("arrow.png");
		Templates.ARROW.textureLoaded = getGlobalImage("arrow_loaded.png", true);
		Templates.ARROW.textureEmbedded = getGlobalImage("arrow_embedded.png", true);
		Templates.ARROW.textureEmbeddedPoint = getGlobalImage("arrow_embedded_point.png", true);
		Templates.ARROW.totalHealth = Templates.ARROW.remainingHealth = 10;
		Templates.ARROW.weight = 2f;
		Templates.ARROW.value = 9;
		Templates.ARROW.anchorX = 8;
		Templates.ARROW.anchorY = 16;
		Templates.ARROW.templateId = GameObject.generateNewTemplateId();
		Templates.ARROW.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(5));
		Templates.ARROW.flipYAxisInMirror = false;

		Templates.FIRE_BALL = new Arrow();
		Templates.FIRE_BALL.name = "Fireball";
		Templates.FIRE_BALL.setImageAndExtrapolateSize("effect_burn.png");
		Templates.FIRE_BALL.totalHealth = Templates.FIRE_BALL.remainingHealth = 10;
		Templates.FIRE_BALL.weight = 2f;
		Templates.FIRE_BALL.value = 12;
		Templates.FIRE_BALL.anchorX = 0;
		Templates.FIRE_BALL.anchorY = 0;
		Templates.FIRE_BALL.templateId = GameObject.generateNewTemplateId();
		Templates.FIRE_BALL.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(5));
		Templates.FIRE_BALL.flipYAxisInMirror = false;

		Templates.WATER_BALL = new Arrow();
		Templates.WATER_BALL.name = "Waterball";
		Templates.WATER_BALL.setImageAndExtrapolateSize("effect_wet.png");
		Templates.WATER_BALL.totalHealth = Templates.WATER_BALL.remainingHealth = 10;
		Templates.WATER_BALL.weight = 2f;
		Templates.WATER_BALL.value = 12;
		Templates.WATER_BALL.anchorX = 0;
		Templates.WATER_BALL.anchorY = 0;
		Templates.WATER_BALL.templateId = GameObject.generateNewTemplateId();
		Templates.WATER_BALL.highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(5));
		Templates.WATER_BALL.flipYAxisInMirror = false;
	}

}
