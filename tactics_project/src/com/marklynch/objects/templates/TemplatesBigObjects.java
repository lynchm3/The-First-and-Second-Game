package com.marklynch.objects.templates;

import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.GameObject;

public class TemplatesBigObjects {

	public TemplatesBigObjects() {

		Templates.BARRICADE = new BigGameObject();
		Templates.BARRICADE.name = "Barricade";
		Templates.BARRICADE.imageTexturePath = "barricade.png";
		Templates.BARRICADE.totalHealth = Templates.BARRICADE.remainingHealth = 100;
		Templates.BARRICADE.widthRatio = 1f;
		Templates.BARRICADE.heightRatio = 1f;
		Templates.BARRICADE.drawOffsetRatioX = 0f;
		Templates.BARRICADE.drawOffsetRatioY = 0f;
		Templates.BARRICADE.soundWhenHit = 1f;
		Templates.BARRICADE.soundWhenHitting = 1f;
		Templates.BARRICADE.soundDampening = 1f;
		Templates.BARRICADE.stackable = false;
		Templates.BARRICADE.weight = 87f;
		Templates.BARRICADE.value = 20;
		Templates.BARRICADE.anchorX = 0;
		Templates.BARRICADE.anchorY = 0;
		Templates.BARRICADE.templateId = GameObject.generateNewTemplateId();

		Templates.BOULDER = new BigGameObject();
		Templates.BOULDER.name = "Boulder";
		Templates.BOULDER.imageTexturePath = "boulder.png";
		Templates.BOULDER.totalHealth = Templates.BOULDER.remainingHealth = 1000;
		Templates.BOULDER.widthRatio = 1f;
		Templates.BOULDER.heightRatio = 1f;
		Templates.BOULDER.drawOffsetRatioX = 0f;
		Templates.BOULDER.drawOffsetRatioY = 0f;
		Templates.BOULDER.soundWhenHit = 1f;
		Templates.BOULDER.soundWhenHitting = 1f;
		Templates.BOULDER.soundDampening = 1f;
		Templates.BOULDER.stackable = false;
		Templates.BOULDER.weight = 200f;
		Templates.BOULDER.value = 17;
		Templates.BOULDER.anchorX = 0;
		Templates.BOULDER.anchorY = 0;
		Templates.BOULDER.templateId = GameObject.generateNewTemplateId();

		Templates.DUMPSTER = new BigGameObject();
		Templates.DUMPSTER.name = "Dumpster";
		Templates.DUMPSTER.imageTexturePath = "skip_with_shadow.png";
		Templates.DUMPSTER.totalHealth = Templates.DUMPSTER.remainingHealth = 100;
		Templates.DUMPSTER.widthRatio = 1f;
		Templates.DUMPSTER.heightRatio = 1f;
		Templates.DUMPSTER.drawOffsetRatioX = 0f;
		Templates.DUMPSTER.drawOffsetRatioY = 0f;
		Templates.DUMPSTER.soundWhenHit = 1f;
		Templates.DUMPSTER.soundWhenHitting = 1f;
		Templates.DUMPSTER.soundDampening = 1f;
		Templates.DUMPSTER.stackable = false;
		Templates.DUMPSTER.weight = 89f;
		Templates.DUMPSTER.value = 46;
		Templates.DUMPSTER.anchorX = 0;
		Templates.DUMPSTER.anchorY = 0;
		Templates.DUMPSTER.templateId = GameObject.generateNewTemplateId();

		Templates.TROUGH = new BigGameObject();
		Templates.TROUGH.name = "Trough";
		Templates.TROUGH.imageTexturePath = "trough.png";
		Templates.TROUGH.totalHealth = Templates.TROUGH.remainingHealth = 29;
		Templates.TROUGH.widthRatio = 1f;
		Templates.TROUGH.heightRatio = 1f;
		Templates.TROUGH.drawOffsetRatioX = 0f;
		Templates.TROUGH.drawOffsetRatioY = 0f;
		Templates.TROUGH.soundWhenHit = 1f;
		Templates.TROUGH.soundWhenHitting = 1f;
		Templates.TROUGH.soundDampening = 1f;
		Templates.TROUGH.stackable = false;
		Templates.TROUGH.weight = 32f;
		Templates.TROUGH.value = 27;
		Templates.TROUGH.anchorX = 0;
		Templates.TROUGH.anchorY = 0;
		Templates.TROUGH.templateId = GameObject.generateNewTemplateId();
	}

}
