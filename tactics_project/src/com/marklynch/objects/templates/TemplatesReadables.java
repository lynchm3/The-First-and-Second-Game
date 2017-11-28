package com.marklynch.objects.templates;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Readable;
import com.marklynch.objects.Sign;

public class TemplatesReadables {

	public TemplatesReadables() {
		ROCK_WITH_ETCHING = new Sign("Rock with Etching", 1000, "rock_with_etching.png", null, new Inventory(), null, 1,
				1, 0f, 0f, 20f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, 14, null,
				GameObject.generateNewTemplateId());
		Templates.ROCK_WITH_ETCHING = new Sign();
		Templates.ROCK_WITH_ETCHING.name = "Rock with Etching";
		Templates.ROCK_WITH_ETCHING.imageTexturePath = "rock_with_etching.png";
		Templates.ROCK_WITH_ETCHING.totalHealth = Templates.ROCK_WITH_ETCHING.remainingHealth = 200;
		Templates.ROCK_WITH_ETCHING.widthRatio = 1f;
		Templates.ROCK_WITH_ETCHING.heightRatio = 1f;
		Templates.ROCK_WITH_ETCHING.drawOffsetX = 0f;
		Templates.ROCK_WITH_ETCHING.drawOffsetY = 0f;
		Templates.ROCK_WITH_ETCHING.soundWhenHit = 1f;
		Templates.ROCK_WITH_ETCHING.soundWhenHitting = 1f;
		Templates.ROCK_WITH_ETCHING.soundDampening = 1f;
		Templates.ROCK_WITH_ETCHING.stackable = false;
		Templates.ROCK_WITH_ETCHING.weight = 200f;
		Templates.ROCK_WITH_ETCHING.value = 14;
		Templates.ROCK_WITH_ETCHING.anchorX = 0;
		Templates.ROCK_WITH_ETCHING.anchorY = 0;
		Templates.ROCK_WITH_ETCHING.templateId = GameObject.generateNewTemplateId();

		SCROLL = new Readable("Scroll", 1000, "scroll.png", null, new Inventory(), null, 1, 1, 0f, 0f, 20f, 1f, 1f,
				null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, 0, null, GameObject.generateNewTemplateId());
		Templates.MAP_MARKER_GREEN = new MapMarker();
		Templates.MAP_MARKER_GREEN.name = "";
		Templates.MAP_MARKER_GREEN.imageTexturePath = "map_marker_green.png";
		Templates.MAP_MARKER_GREEN.totalHealth = Templates.MAP_MARKER_GREEN.remainingHealth = 1;
		Templates.MAP_MARKER_GREEN.widthRatio = 1f;
		Templates.MAP_MARKER_GREEN.heightRatio = 1f;
		Templates.MAP_MARKER_GREEN.drawOffsetX = 0f;
		Templates.MAP_MARKER_GREEN.drawOffsetY = 0f;
		Templates.MAP_MARKER_GREEN.soundWhenHit = 1f;
		Templates.MAP_MARKER_GREEN.soundWhenHitting = 1f;
		Templates.MAP_MARKER_GREEN.soundDampening = 1f;
		Templates.MAP_MARKER_GREEN.stackable = false;
		Templates.MAP_MARKER_GREEN.weight = 0f;
		Templates.MAP_MARKER_GREEN.value = 0;
		Templates.MAP_MARKER_GREEN.anchorX = 0;
		Templates.MAP_MARKER_GREEN.anchorY = 0;
		Templates.MAP_MARKER_GREEN.templateId = GameObject.generateNewTemplateId();

		SIGN = new Sign("Sign", 5, "sign.png", null, new Inventory(), null, 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 100f, 15f, 128, null, GameObject.generateNewTemplateId());
		Templates.MAP_MARKER_BLUE = new MapMarker();
		Templates.MAP_MARKER_BLUE.name = "";
		Templates.MAP_MARKER_BLUE.imageTexturePath = "map_marker_blue.png";
		Templates.MAP_MARKER_BLUE.totalHealth = Templates.MAP_MARKER_BLUE.remainingHealth = 1;
		Templates.MAP_MARKER_BLUE.widthRatio = 1f;
		Templates.MAP_MARKER_BLUE.heightRatio = 1f;
		Templates.MAP_MARKER_BLUE.drawOffsetX = 0f;
		Templates.MAP_MARKER_BLUE.drawOffsetY = 0f;
		Templates.MAP_MARKER_BLUE.soundWhenHit = 1f;
		Templates.MAP_MARKER_BLUE.soundWhenHitting = 1f;
		Templates.MAP_MARKER_BLUE.soundDampening = 1f;
		Templates.MAP_MARKER_BLUE.stackable = false;
		Templates.MAP_MARKER_BLUE.weight = 0f;
		Templates.MAP_MARKER_BLUE.value = 0;
		Templates.MAP_MARKER_BLUE.anchorX = 0;
		Templates.MAP_MARKER_BLUE.anchorY = 0;
		Templates.MAP_MARKER_BLUE.templateId = GameObject.generateNewTemplateId();

		SIGNPOST = new Sign("Signpost", 50, "signpost.png", null, new Inventory(), null, 1, 1.25f, 0f, -0.25f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 15f, 213, null, GameObject.generateNewTemplateId());
		Templates.MAP_MARKER_SKULL = new MapMarker();
		Templates.MAP_MARKER_SKULL.name = "";
		Templates.MAP_MARKER_SKULL.imageTexturePath = "map_marker_skull.png";
		Templates.MAP_MARKER_SKULL.totalHealth = Templates.MAP_MARKER_SKULL.remainingHealth = 1;
		Templates.MAP_MARKER_SKULL.widthRatio = 1f;
		Templates.MAP_MARKER_SKULL.heightRatio = 1f;
		Templates.MAP_MARKER_SKULL.drawOffsetX = 0f;
		Templates.MAP_MARKER_SKULL.drawOffsetY = 0f;
		Templates.MAP_MARKER_SKULL.soundWhenHit = 1f;
		Templates.MAP_MARKER_SKULL.soundWhenHitting = 1f;
		Templates.MAP_MARKER_SKULL.soundDampening = 1f;
		Templates.MAP_MARKER_SKULL.stackable = false;
		Templates.MAP_MARKER_SKULL.weight = 0f;
		Templates.MAP_MARKER_SKULL.value = 0;
		Templates.MAP_MARKER_SKULL.anchorX = 0;
		Templates.MAP_MARKER_SKULL.anchorY = 0;
		Templates.MAP_MARKER_SKULL.templateId = GameObject.generateNewTemplateId();

		DOCUMENTS = new Readable("Documents", 5, "documents.png", null, new Inventory(), null, 0.5f, 0.5f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, 14, null,
				GameObject.generateNewTemplateId());

		Templates.MAP_MARKER_TREASURE = new MapMarker();
		Templates.MAP_MARKER_TREASURE.name = "";
		Templates.MAP_MARKER_TREASURE.imageTexturePath = "map_marker_treasure.png";
		Templates.MAP_MARKER_TREASURE.totalHealth = Templates.MAP_MARKER_TREASURE.remainingHealth = 1;
		Templates.MAP_MARKER_TREASURE.widthRatio = 1f;
		Templates.MAP_MARKER_TREASURE.heightRatio = 1f;
		Templates.MAP_MARKER_TREASURE.drawOffsetX = 0f;
		Templates.MAP_MARKER_TREASURE.drawOffsetY = 0f;
		Templates.MAP_MARKER_TREASURE.soundWhenHit = 1f;
		Templates.MAP_MARKER_TREASURE.soundWhenHitting = 1f;
		Templates.MAP_MARKER_TREASURE.soundDampening = 1f;
		Templates.MAP_MARKER_TREASURE.stackable = false;
		Templates.MAP_MARKER_TREASURE.weight = 0f;
		Templates.MAP_MARKER_TREASURE.value = 0;
		Templates.MAP_MARKER_TREASURE.anchorX = 0;
		Templates.MAP_MARKER_TREASURE.anchorY = 0;
		Templates.MAP_MARKER_TREASURE.templateId = GameObject.generateNewTemplateId();

	}

}
