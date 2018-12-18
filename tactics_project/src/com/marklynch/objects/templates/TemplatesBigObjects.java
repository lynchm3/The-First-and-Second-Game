package com.marklynch.objects.templates;

import com.marklynch.objects.inanimateobjects.GameObject;

public class TemplatesBigObjects {

	public TemplatesBigObjects() {

		Templates.BARRICADE = new GameObject();
		Templates.BARRICADE.name = "Barricade";
		Templates.BARRICADE.setImageAndExtrapolateSize("barricade.png");
		Templates.BARRICADE.totalHealth = Templates.BARRICADE.remainingHealth = 100;
		Templates.BARRICADE.weight = 150f;
		Templates.BARRICADE.value = 20;
		Templates.BARRICADE.anchorX = 0;
		Templates.BARRICADE.anchorY = 0;
		Templates.BARRICADE.templateId = GameObject.generateNewTemplateId();
		Templates.BARRICADE.flipYAxisInMirror = false;
		Templates.BARRICADE.canShareSquare = false;
		Templates.BARRICADE.fitsInInventory = false;
		Templates.BARRICADE.persistsWhenCantBeSeen = true;

		Templates.BOULDER = new GameObject();
		Templates.BOULDER.name = "Boulder";
		Templates.BOULDER.setImageAndExtrapolateSize("boulder.png");
		Templates.BOULDER.totalHealth = Templates.BOULDER.remainingHealth = 1000;
		Templates.BOULDER.soundDampening = 2f;
		Templates.BOULDER.weight = 500f;
		Templates.BOULDER.value = 17;
		Templates.BOULDER.anchorX = 0;
		Templates.BOULDER.anchorY = 0;
		Templates.BOULDER.templateId = GameObject.generateNewTemplateId();
		Templates.BOULDER.flipYAxisInMirror = false;
		Templates.BOULDER.canShareSquare = false;
		Templates.BOULDER.blocksLineOfSight = true;
		Templates.BOULDER.fitsInInventory = false;
		Templates.BOULDER.persistsWhenCantBeSeen = true;
		Templates.BOULDER.makeIndestructible();

		Templates.DUMPSTER = new GameObject();
		Templates.DUMPSTER.name = "Dumpster";
		Templates.DUMPSTER.setImageAndExtrapolateSize("skip_with_shadow.png");
		Templates.DUMPSTER.totalHealth = Templates.DUMPSTER.remainingHealth = 100;
		Templates.DUMPSTER.weight = 140f;
		Templates.DUMPSTER.value = 46;
		Templates.DUMPSTER.anchorX = 0;
		Templates.DUMPSTER.anchorY = 0;
		Templates.DUMPSTER.templateId = GameObject.generateNewTemplateId();
		Templates.DUMPSTER.flipYAxisInMirror = false;
		Templates.DUMPSTER.canShareSquare = false;
		Templates.DUMPSTER.fitsInInventory = false;
		Templates.DUMPSTER.persistsWhenCantBeSeen = true;

		Templates.TROUGH = new GameObject();
		Templates.TROUGH.name = "Trough";
		Templates.TROUGH.setImageAndExtrapolateSize("trough.png");
		Templates.TROUGH.totalHealth = Templates.TROUGH.remainingHealth = 29;
		Templates.TROUGH.weight = 46f;
		Templates.TROUGH.value = 27;
		Templates.TROUGH.anchorX = 0;
		Templates.TROUGH.anchorY = 0;
		Templates.TROUGH.templateId = GameObject.generateNewTemplateId();
		Templates.TROUGH.flipYAxisInMirror = false;
		Templates.TROUGH.canShareSquare = false;
		Templates.TROUGH.fitsInInventory = false;
		Templates.TROUGH.persistsWhenCantBeSeen = true;
	}

}
