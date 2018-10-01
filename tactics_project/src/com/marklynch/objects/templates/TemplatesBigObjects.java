package com.marklynch.objects.templates;

import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.GameObject;

public class TemplatesBigObjects {

	public TemplatesBigObjects() {

		Templates.BARRICADE = new BigGameObject();
		Templates.BARRICADE.name = "Barricade";
		Templates.BARRICADE.setImageAndExtrapolateSize("barricade.png");
		Templates.BARRICADE.totalHealth = Templates.BARRICADE.remainingHealth = 100;
		Templates.BARRICADE.weight = 87f;
		Templates.BARRICADE.value = 20;
		Templates.BARRICADE.anchorX = 0;
		Templates.BARRICADE.anchorY = 0;
		Templates.BARRICADE.templateId = GameObject.generateNewTemplateId();
		Templates.BARRICADE.flipYAxisInMirror = false;

		Templates.BOULDER = new BigGameObject();
		Templates.BOULDER.name = "Boulder";
		Templates.BOULDER.setImageAndExtrapolateSize("boulder.png");
		Templates.BOULDER.totalHealth = Templates.BOULDER.remainingHealth = 1000;
		Templates.BOULDER.soundDampening = 2f;
		Templates.BOULDER.weight = 200f;
		Templates.BOULDER.value = 17;
		Templates.BOULDER.anchorX = 0;
		Templates.BOULDER.anchorY = 0;
		Templates.BOULDER.templateId = GameObject.generateNewTemplateId();
		Templates.BOULDER.flipYAxisInMirror = false;

		Templates.DUMPSTER = new BigGameObject();
		Templates.DUMPSTER.name = "Dumpster";
		Templates.DUMPSTER.setImageAndExtrapolateSize("skip_with_shadow.png");
		Templates.DUMPSTER.totalHealth = Templates.DUMPSTER.remainingHealth = 100;
		Templates.DUMPSTER.weight = 89f;
		Templates.DUMPSTER.value = 46;
		Templates.DUMPSTER.anchorX = 0;
		Templates.DUMPSTER.anchorY = 0;
		Templates.DUMPSTER.templateId = GameObject.generateNewTemplateId();
		Templates.DUMPSTER.flipYAxisInMirror = false;

		Templates.TROUGH = new BigGameObject();
		Templates.TROUGH.name = "Trough";
		Templates.TROUGH.setImageAndExtrapolateSize("trough.png");
		Templates.TROUGH.totalHealth = Templates.TROUGH.remainingHealth = 29;
		Templates.TROUGH.weight = 32f;
		Templates.TROUGH.value = 27;
		Templates.TROUGH.anchorX = 0;
		Templates.TROUGH.anchorY = 0;
		Templates.TROUGH.templateId = GameObject.generateNewTemplateId();
		Templates.TROUGH.flipYAxisInMirror = false;
	}

}
