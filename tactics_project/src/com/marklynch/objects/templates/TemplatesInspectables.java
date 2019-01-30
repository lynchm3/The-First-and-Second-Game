package com.marklynch.objects.templates;

import com.marklynch.Game;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Inspectable;

public class TemplatesInspectables {

	public TemplatesInspectables() {
		Templates.PIG_SIGN = new Inspectable();
		Templates.PIG_SIGN.name = "Piggy Farm";
		Templates.PIG_SIGN.setImageAndExtrapolateSize("pig_sign.png");
		Templates.PIG_SIGN.totalHealth = Templates.PIG_SIGN.remainingHealth = 26;
		Templates.PIG_SIGN.drawOffsetRatioX = -0.25f;
		Templates.PIG_SIGN.drawOffsetX = Templates.PIG_SIGN.drawOffsetRatioX * Game.SQUARE_WIDTH;
		Templates.PIG_SIGN.weight = 38f;
		Templates.PIG_SIGN.value = 36;
		Templates.PIG_SIGN.anchorX = 0;
		Templates.PIG_SIGN.anchorY = 0;
		Templates.PIG_SIGN.templateId = GameObject.generateNewTemplateId();
		Templates.PIG_SIGN.moveable = false;

		Templates.BLOODY_PULP = new Inspectable();
		Templates.BLOODY_PULP.name = "Bloody Pulp";
		Templates.BLOODY_PULP.setImageAndExtrapolateSize("blood.png");
		Templates.BLOODY_PULP.weight = 1f;
		Templates.BLOODY_PULP.value = 3;
		Templates.BLOODY_PULP.anchorX = 0;
		Templates.BLOODY_PULP.anchorY = 0;
		Templates.BLOODY_PULP.templateId = GameObject.generateNewTemplateId();

		Templates.BLOOD = new Inspectable();
		Templates.BLOOD.name = "Blood";
		Templates.BLOOD.setImageAndExtrapolateSize("blood.png");
		Templates.BLOOD.totalHealth = Templates.BLOOD.remainingHealth = 1;
		Templates.BLOOD.weight = 1f;
		Templates.BLOOD.value = 5;
		Templates.BLOOD.anchorX = 0;
		Templates.BLOOD.anchorY = 0;
		Templates.BLOOD.templateId = GameObject.generateNewTemplateId();

		Templates.DRIED_BLOOD = new Inspectable();
		Templates.DRIED_BLOOD.name = "Dried Blood";
		Templates.DRIED_BLOOD.setImageAndExtrapolateSize("blood.png");
		Templates.DRIED_BLOOD.totalHealth = Templates.DRIED_BLOOD.remainingHealth = 1;
		Templates.DRIED_BLOOD.weight = 1f;
		Templates.DRIED_BLOOD.value = 1;
		Templates.DRIED_BLOOD.anchorX = 0;
		Templates.DRIED_BLOOD.anchorY = 0;
		Templates.DRIED_BLOOD.templateId = GameObject.generateNewTemplateId();

		Templates.GIANT_FOOTPRINT = new Inspectable();
		Templates.GIANT_FOOTPRINT.name = "Giant Footprint";
		Templates.GIANT_FOOTPRINT.setImageAndExtrapolateSize("footprint.png");
		Templates.GIANT_FOOTPRINT.totalHealth = Templates.GIANT_FOOTPRINT.remainingHealth = 1;
		Templates.GIANT_FOOTPRINT.widthRatio = 2f;
		Templates.GIANT_FOOTPRINT.heightRatio = 1.5f;
		Templates.GIANT_FOOTPRINT.canBePickedUp = false;
		Templates.GIANT_FOOTPRINT.fitsInInventory = false;
		Templates.GIANT_FOOTPRINT.persistsWhenCantBeSeen = true;
		Templates.GIANT_FOOTPRINT.attackable = false;
		Templates.GIANT_FOOTPRINT.isFloorObject = true;
		Templates.GIANT_FOOTPRINT.moveable = false;
		Templates.GIANT_FOOTPRINT.orderingOnGound = 20;
		Templates.GIANT_FOOTPRINT.templateId = GameObject.generateNewTemplateId();

		Templates.GIANT_FOOTPRINT_LEFT = new Inspectable();
		Templates.GIANT_FOOTPRINT_LEFT.name = "Giant Footprint";
		Templates.GIANT_FOOTPRINT_LEFT.setImageAndExtrapolateSize("footprint_left.png");
		Templates.GIANT_FOOTPRINT_LEFT.totalHealth = Templates.GIANT_FOOTPRINT_LEFT.remainingHealth = 1;
		Templates.GIANT_FOOTPRINT_LEFT.widthRatio = 2f;
		Templates.GIANT_FOOTPRINT_LEFT.heightRatio = 1.5f;
		Templates.GIANT_FOOTPRINT_LEFT.drawOffsetRatioX = -0.5f;
		Templates.GIANT_FOOTPRINT_LEFT.drawOffsetX = Templates.GIANT_FOOTPRINT_LEFT.drawOffsetRatioX
				* Game.SQUARE_WIDTH;
		Templates.GIANT_FOOTPRINT_LEFT.drawOffsetRatioY = -0.25f;
		Templates.GIANT_FOOTPRINT_LEFT.drawOffsetY = Templates.GIANT_FOOTPRINT_LEFT.drawOffsetRatioY
				* Game.SQUARE_HEIGHT;
		Templates.GIANT_FOOTPRINT_LEFT.canBePickedUp = false;
		Templates.GIANT_FOOTPRINT_LEFT.fitsInInventory = false;
		Templates.GIANT_FOOTPRINT_LEFT.persistsWhenCantBeSeen = true;
		Templates.GIANT_FOOTPRINT_LEFT.attackable = false;
		Templates.GIANT_FOOTPRINT_LEFT.isFloorObject = true;
		Templates.GIANT_FOOTPRINT_LEFT.moveable = false;
		Templates.GIANT_FOOTPRINT_LEFT.orderingOnGound = 20;
		Templates.GIANT_FOOTPRINT_LEFT.templateId = GameObject.generateNewTemplateId();
	}

}
