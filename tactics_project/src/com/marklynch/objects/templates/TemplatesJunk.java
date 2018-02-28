package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;

public class TemplatesJunk {

	public TemplatesJunk() {

		Templates.FUR = new Junk();
		Templates.FUR.name = "Fur";
		Templates.FUR.imageTexturePath = "fur.png";
		Templates.FUR.imageTexture = getGlobalImage(Templates.FUR.imageTexturePath, true);
		Templates.FUR.totalHealth = Templates.FUR.remainingHealth = 21;
		Templates.FUR.widthRatio = 1f;
		Templates.FUR.heightRatio = 1f;
		Templates.FUR.drawOffsetRatioX = 0f;
		Templates.FUR.drawOffsetRatioY = 0f;
		Templates.FUR.soundWhenHit = 1f;
		Templates.FUR.soundWhenHitting = 1f;
		Templates.FUR.soundDampening = 1f;
		Templates.FUR.stackable = false;
		Templates.FUR.weight = 15f;
		Templates.FUR.value = 55;
		Templates.FUR.anchorX = 0;
		Templates.FUR.anchorY = 0;
		Templates.FUR.templateId = GameObject.generateNewTemplateId();

		Templates.DIRTY_SHEET_3 = new Junk();
		Templates.DIRTY_SHEET_3.name = "Dirty Sheet";
		Templates.DIRTY_SHEET_3.imageTexturePath = "dirty_sheet_3.png";
		Templates.DIRTY_SHEET_3.imageTexture = getGlobalImage(Templates.DIRTY_SHEET_3.imageTexturePath, true);
		Templates.DIRTY_SHEET_3.totalHealth = Templates.DIRTY_SHEET_3.remainingHealth = 1;
		Templates.DIRTY_SHEET_3.widthRatio = 0.88f;
		Templates.DIRTY_SHEET_3.heightRatio = 1f;
		Templates.DIRTY_SHEET_3.drawOffsetRatioX = 0f;
		Templates.DIRTY_SHEET_3.drawOffsetRatioY = 0f;
		Templates.DIRTY_SHEET_3.soundWhenHit = 1f;
		Templates.DIRTY_SHEET_3.soundWhenHitting = 1f;
		Templates.DIRTY_SHEET_3.soundDampening = 1f;
		Templates.DIRTY_SHEET_3.stackable = false;
		Templates.DIRTY_SHEET_3.weight = 4f;
		Templates.DIRTY_SHEET_3.value = 5;
		Templates.DIRTY_SHEET_3.anchorX = 0;
		Templates.DIRTY_SHEET_3.anchorY = 0;
		Templates.DIRTY_SHEET_3.templateId = GameObject.generateNewTemplateId();

		Templates.WOOD = new Junk();
		Templates.WOOD.name = "Wood";
		Templates.WOOD.imageTexturePath = "wood.png";
		Templates.WOOD.imageTexture = getGlobalImage(Templates.WOOD.imageTexturePath, true);
		Templates.WOOD.totalHealth = Templates.WOOD.remainingHealth = 16;
		Templates.WOOD.widthRatio = 1f;
		Templates.WOOD.heightRatio = 1f;
		Templates.WOOD.drawOffsetRatioX = 0f;
		Templates.WOOD.drawOffsetRatioY = 0f;
		Templates.WOOD.soundWhenHit = 1f;
		Templates.WOOD.soundWhenHitting = 1f;
		Templates.WOOD.soundDampening = 1f;
		Templates.WOOD.stackable = false;
		Templates.WOOD.weight = 0f;
		Templates.WOOD.value = 8;
		Templates.WOOD.anchorX = 0;
		Templates.WOOD.anchorY = 0;
		Templates.WOOD.templateId = GameObject.generateNewTemplateId();
		Templates.WOOD.bluntDamage = 4;

		Templates.ORE = new Junk();
		Templates.ORE.name = "Ore";
		Templates.ORE.imageTexturePath = "ore.png";
		Templates.ORE.imageTexture = getGlobalImage(Templates.ORE.imageTexturePath, true);
		Templates.ORE.totalHealth = Templates.ORE.remainingHealth = 1;
		Templates.ORE.widthRatio = 0.25f;
		Templates.ORE.heightRatio = 0.25f;
		Templates.ORE.drawOffsetRatioX = 0f;
		Templates.ORE.drawOffsetRatioY = 0f;
		Templates.ORE.soundWhenHit = 1f;
		Templates.ORE.soundWhenHitting = 1f;
		Templates.ORE.soundDampening = 1f;
		Templates.ORE.stackable = false;
		Templates.ORE.weight = 7f;
		Templates.ORE.value = 39;
		Templates.ORE.anchorX = 0;
		Templates.ORE.anchorY = 0;
		Templates.ORE.templateId = GameObject.generateNewTemplateId();
		Templates.ORE.bluntDamage = 6;

	}

}
