package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;

public class TemplatesProjectiles {

	public TemplatesProjectiles() {

		Templates.ARROW = new Arrow();
		Templates.ARROW.name = "Arrow";
		Templates.ARROW.imageTexturePath = "arrow.png";
		Templates.ARROW.imageTexture = getGlobalImage(Templates.ARROW.imageTexturePath, true);
		Templates.ARROW.totalHealth = Templates.ARROW.remainingHealth = 10;
		Templates.ARROW.widthRatio = 0.32f;
		Templates.ARROW.heightRatio = 0.16f;
		Templates.ARROW.drawOffsetRatioX = 0f;
		Templates.ARROW.drawOffsetRatioY = 0f;
		Templates.ARROW.soundWhenHit = 1f;
		Templates.ARROW.soundWhenHitting = 1f;
		Templates.ARROW.soundDampening = 1f;
		Templates.ARROW.stackable = false;
		Templates.ARROW.weight = 2f;
		Templates.ARROW.value = 9;
		Templates.ARROW.anchorX = 8;
		Templates.ARROW.anchorY = 16;
		Templates.ARROW.templateId = GameObject.generateNewTemplateId();
		Templates.ARROW.pierceDamage = 5;
		Templates.ARROW.flipYAxisInMirror = false;

		Templates.FIRE_BALL = new Arrow();
		Templates.FIRE_BALL.name = "Fireball";
		Templates.FIRE_BALL.imageTexturePath = "effect_burn.png";
		Templates.FIRE_BALL.imageTexture = getGlobalImage(Templates.FIRE_BALL.imageTexturePath, true);
		Templates.FIRE_BALL.totalHealth = Templates.FIRE_BALL.remainingHealth = 10;
		Templates.FIRE_BALL.widthRatio = 0.32f;
		Templates.FIRE_BALL.heightRatio = 0.32f;
		Templates.FIRE_BALL.drawOffsetRatioX = 0f;
		Templates.FIRE_BALL.drawOffsetRatioY = 0f;
		Templates.FIRE_BALL.soundWhenHit = 1f;
		Templates.FIRE_BALL.soundWhenHitting = 1f;
		Templates.FIRE_BALL.soundDampening = 1f;
		Templates.FIRE_BALL.stackable = false;
		Templates.FIRE_BALL.weight = 2f;
		Templates.FIRE_BALL.value = 12;
		Templates.FIRE_BALL.anchorX = 0;
		Templates.FIRE_BALL.anchorY = 0;
		Templates.FIRE_BALL.templateId = GameObject.generateNewTemplateId();
		Templates.FIRE_BALL.fireDamage = 5;
		Templates.FIRE_BALL.flipYAxisInMirror = false;

		Templates.WATER_BALL = new Arrow();
		Templates.WATER_BALL.name = "Waterball";
		Templates.WATER_BALL.imageTexturePath = "effect_wet.png";
		Templates.WATER_BALL.imageTexture = getGlobalImage(Templates.WATER_BALL.imageTexturePath, true);
		Templates.WATER_BALL.totalHealth = Templates.WATER_BALL.remainingHealth = 10;
		Templates.WATER_BALL.widthRatio = 0.32f;
		Templates.WATER_BALL.heightRatio = 0.32f;
		Templates.WATER_BALL.drawOffsetRatioX = 0f;
		Templates.WATER_BALL.drawOffsetRatioY = 0f;
		Templates.WATER_BALL.soundWhenHit = 1f;
		Templates.WATER_BALL.soundWhenHitting = 1f;
		Templates.WATER_BALL.soundDampening = 1f;
		Templates.WATER_BALL.stackable = false;
		Templates.WATER_BALL.weight = 2f;
		Templates.WATER_BALL.value = 12;
		Templates.WATER_BALL.anchorX = 0;
		Templates.WATER_BALL.anchorY = 0;
		Templates.WATER_BALL.templateId = GameObject.generateNewTemplateId();
		Templates.WATER_BALL.waterDamage = 5;
		Templates.WATER_BALL.flipYAxisInMirror = false;
	}

}
