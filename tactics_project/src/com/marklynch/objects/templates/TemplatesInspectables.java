package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inspectable;

public class TemplatesInspectables {

	public TemplatesInspectables() {
		Templates.PIG_SIGN = new Inspectable();
		Templates.PIG_SIGN.name = "Piggy Farm";
		Templates.PIG_SIGN.imageTexturePath = "pig_sign.png";
		Templates.PIG_SIGN.totalHealth = Templates.PIG_SIGN.remainingHealth = 26;
		Templates.PIG_SIGN.widthRatio = 1f;
		Templates.PIG_SIGN.heightRatio = 1f;
		Templates.PIG_SIGN.drawOffsetX = -0.25f;
		Templates.PIG_SIGN.drawOffsetY = 0f;
		Templates.PIG_SIGN.soundWhenHit = 1f;
		Templates.PIG_SIGN.soundWhenHitting = 1f;
		Templates.PIG_SIGN.soundDampening = 1f;
		Templates.PIG_SIGN.stackable = false;
		Templates.PIG_SIGN.weight = 38f;
		Templates.PIG_SIGN.value = 36;
		Templates.PIG_SIGN.anchorX = 0;
		Templates.PIG_SIGN.anchorY = 0;
		Templates.PIG_SIGN.templateId = GameObject.generateNewTemplateId();

		Templates.BLOODY_PULP = new Inspectable();
		Templates.BLOODY_PULP.name = "Bloody Pulp";
		Templates.BLOODY_PULP.imageTexturePath = "blood.png";
		Templates.BLOODY_PULP.totalHealth = Templates.BLOODY_PULP.remainingHealth = 1;
		Templates.BLOODY_PULP.widthRatio = 1f;
		Templates.BLOODY_PULP.heightRatio = 1f;
		Templates.BLOODY_PULP.drawOffsetX = 0f;
		Templates.BLOODY_PULP.drawOffsetY = 0f;
		Templates.BLOODY_PULP.soundWhenHit = 1f;
		Templates.BLOODY_PULP.soundWhenHitting = 1f;
		Templates.BLOODY_PULP.soundDampening = 1f;
		Templates.BLOODY_PULP.stackable = false;
		Templates.BLOODY_PULP.weight = 1f;
		Templates.BLOODY_PULP.value = 3;
		Templates.BLOODY_PULP.anchorX = 0;
		Templates.BLOODY_PULP.anchorY = 0;
		Templates.BLOODY_PULP.templateId = GameObject.generateNewTemplateId();

		Templates.BLOOD = new Inspectable();
		Templates.BLOOD.name = "Blood";
		Templates.BLOOD.imageTexturePath = "blood.png";
		Templates.BLOOD.totalHealth = Templates.BLOOD.remainingHealth = 1;
		Templates.BLOOD.widthRatio = 1f;
		Templates.BLOOD.heightRatio = 1f;
		Templates.BLOOD.drawOffsetX = 0f;
		Templates.BLOOD.drawOffsetY = 0f;
		Templates.BLOOD.soundWhenHit = 1f;
		Templates.BLOOD.soundWhenHitting = 1f;
		Templates.BLOOD.soundDampening = 1f;
		Templates.BLOOD.stackable = false;
		Templates.BLOOD.weight = 1f;
		Templates.BLOOD.value = 5;
		Templates.BLOOD.anchorX = 0;
		Templates.BLOOD.anchorY = 0;
		Templates.BLOOD.templateId = GameObject.generateNewTemplateId();

		Templates.DRIED_BLOOD = new Inspectable();
		Templates.DRIED_BLOOD.name = "Dried Blood";
		Templates.DRIED_BLOOD.imageTexturePath = "blood.png";
		Templates.DRIED_BLOOD.totalHealth = Templates.DRIED_BLOOD.remainingHealth = 1;
		Templates.DRIED_BLOOD.widthRatio = 1f;
		Templates.DRIED_BLOOD.heightRatio = 1f;
		Templates.DRIED_BLOOD.drawOffsetX = 0f;
		Templates.DRIED_BLOOD.drawOffsetY = 0f;
		Templates.DRIED_BLOOD.soundWhenHit = 1f;
		Templates.DRIED_BLOOD.soundWhenHitting = 1f;
		Templates.DRIED_BLOOD.soundDampening = 1f;
		Templates.DRIED_BLOOD.stackable = false;
		Templates.DRIED_BLOOD.weight = 1f;
		Templates.DRIED_BLOOD.value = 1;
		Templates.DRIED_BLOOD.anchorX = 0;
		Templates.DRIED_BLOOD.anchorY = 0;
		Templates.DRIED_BLOOD.templateId = GameObject.generateNewTemplateId();

		Templates.GIANT_FOOTPRINT = new Inspectable();
		Templates.GIANT_FOOTPRINT.name = "Giant Footprint";
		Templates.GIANT_FOOTPRINT.imageTexturePath = "footprint.png";
		Templates.GIANT_FOOTPRINT.totalHealth = Templates.GIANT_FOOTPRINT.remainingHealth = 1;
		Templates.GIANT_FOOTPRINT.widthRatio = 2f;
		Templates.GIANT_FOOTPRINT.heightRatio = 1.5f;
		Templates.GIANT_FOOTPRINT.drawOffsetX = -0.5f;
		Templates.GIANT_FOOTPRINT.drawOffsetY = -0.25f;
		Templates.GIANT_FOOTPRINT.soundWhenHit = 1f;
		Templates.GIANT_FOOTPRINT.soundWhenHitting = 1f;
		Templates.GIANT_FOOTPRINT.soundDampening = 1f;
		Templates.GIANT_FOOTPRINT.stackable = false;
		Templates.GIANT_FOOTPRINT.weight = 0f;
		Templates.GIANT_FOOTPRINT.value = 0;
		Templates.GIANT_FOOTPRINT.anchorX = 0;
		Templates.GIANT_FOOTPRINT.anchorY = 0;
		Templates.GIANT_FOOTPRINT.templateId = GameObject.generateNewTemplateId();

		Templates.GIANT_FOOTPRINT_LEFT = new Inspectable();
		Templates.GIANT_FOOTPRINT_LEFT.name = "Giant Footprint";
		Templates.GIANT_FOOTPRINT_LEFT.imageTexturePath = "footprint_left.png";
		Templates.GIANT_FOOTPRINT_LEFT.totalHealth = Templates.GIANT_FOOTPRINT_LEFT.remainingHealth = 1;
		Templates.GIANT_FOOTPRINT_LEFT.widthRatio = 2f;
		Templates.GIANT_FOOTPRINT_LEFT.heightRatio = 1.5f;
		Templates.GIANT_FOOTPRINT_LEFT.drawOffsetX = -0.5f;
		Templates.GIANT_FOOTPRINT_LEFT.drawOffsetY = -0.25f;
		Templates.GIANT_FOOTPRINT_LEFT.soundWhenHit = 1f;
		Templates.GIANT_FOOTPRINT_LEFT.soundWhenHitting = 1f;
		Templates.GIANT_FOOTPRINT_LEFT.soundDampening = 1f;
		Templates.GIANT_FOOTPRINT_LEFT.stackable = false;
		Templates.GIANT_FOOTPRINT_LEFT.weight = 0f;
		Templates.GIANT_FOOTPRINT_LEFT.value = 0;
		Templates.GIANT_FOOTPRINT_LEFT.anchorX = 0;
		Templates.GIANT_FOOTPRINT_LEFT.anchorY = 0;
		Templates.GIANT_FOOTPRINT_LEFT.templateId = GameObject.generateNewTemplateId();
	}

}
