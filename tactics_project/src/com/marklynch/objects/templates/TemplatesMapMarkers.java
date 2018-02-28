package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;

public class TemplatesMapMarkers {

	public TemplatesMapMarkers() {

		Templates.MAP_MARKER_RED = new MapMarker();
		Templates.MAP_MARKER_RED.name = "";
		Templates.MAP_MARKER_RED.imageTexturePath = "map_marker_red.png";
		Templates.MAP_MARKER_RED.imageTexture = getGlobalImage(Templates.MAP_MARKER_RED.imageTexturePath, true);
		Templates.MAP_MARKER_RED.totalHealth = Templates.MAP_MARKER_RED.remainingHealth = 1;
		Templates.MAP_MARKER_RED.widthRatio = 1f;
		Templates.MAP_MARKER_RED.heightRatio = 1f;
		Templates.MAP_MARKER_RED.drawOffsetRatioX = 0f;
		Templates.MAP_MARKER_RED.drawOffsetRatioY = 0f;
		Templates.MAP_MARKER_RED.soundWhenHit = 1f;
		Templates.MAP_MARKER_RED.soundWhenHitting = 1f;
		Templates.MAP_MARKER_RED.soundDampening = 1f;
		Templates.MAP_MARKER_RED.stackable = false;
		Templates.MAP_MARKER_RED.weight = 0f;
		Templates.MAP_MARKER_RED.value = 0;
		Templates.MAP_MARKER_RED.anchorX = 0;
		Templates.MAP_MARKER_RED.anchorY = 0;
		Templates.MAP_MARKER_RED.templateId = GameObject.generateNewTemplateId();
		Templates.MAP_MARKER_RED.flipYAxisInMirror = false;

		Templates.MAP_MARKER_GREEN = new MapMarker();
		Templates.MAP_MARKER_GREEN.name = "";
		Templates.MAP_MARKER_GREEN.imageTexturePath = "map_marker_green.png";
		Templates.MAP_MARKER_GREEN.imageTexture = getGlobalImage(Templates.MAP_MARKER_GREEN.imageTexturePath, true);
		Templates.MAP_MARKER_GREEN.totalHealth = Templates.MAP_MARKER_GREEN.remainingHealth = 1;
		Templates.MAP_MARKER_GREEN.widthRatio = 1f;
		Templates.MAP_MARKER_GREEN.heightRatio = 1f;
		Templates.MAP_MARKER_GREEN.drawOffsetRatioX = 0f;
		Templates.MAP_MARKER_GREEN.drawOffsetRatioY = 0f;
		Templates.MAP_MARKER_GREEN.soundWhenHit = 1f;
		Templates.MAP_MARKER_GREEN.soundWhenHitting = 1f;
		Templates.MAP_MARKER_GREEN.soundDampening = 1f;
		Templates.MAP_MARKER_GREEN.stackable = false;
		Templates.MAP_MARKER_GREEN.weight = 0f;
		Templates.MAP_MARKER_GREEN.value = 0;
		Templates.MAP_MARKER_GREEN.anchorX = 0;
		Templates.MAP_MARKER_GREEN.anchorY = 0;
		Templates.MAP_MARKER_GREEN.templateId = GameObject.generateNewTemplateId();
		Templates.MAP_MARKER_GREEN.flipYAxisInMirror = false;

		Templates.MAP_MARKER_BLUE = new MapMarker();
		Templates.MAP_MARKER_BLUE.name = "";
		Templates.MAP_MARKER_BLUE.imageTexturePath = "map_marker_blue.png";
		Templates.MAP_MARKER_BLUE.imageTexture = getGlobalImage(Templates.MAP_MARKER_BLUE.imageTexturePath, true);
		Templates.MAP_MARKER_BLUE.totalHealth = Templates.MAP_MARKER_BLUE.remainingHealth = 1;
		Templates.MAP_MARKER_BLUE.widthRatio = 1f;
		Templates.MAP_MARKER_BLUE.heightRatio = 1f;
		Templates.MAP_MARKER_BLUE.drawOffsetRatioX = 0f;
		Templates.MAP_MARKER_BLUE.drawOffsetRatioY = 0f;
		Templates.MAP_MARKER_BLUE.soundWhenHit = 1f;
		Templates.MAP_MARKER_BLUE.soundWhenHitting = 1f;
		Templates.MAP_MARKER_BLUE.soundDampening = 1f;
		Templates.MAP_MARKER_BLUE.stackable = false;
		Templates.MAP_MARKER_BLUE.weight = 0f;
		Templates.MAP_MARKER_BLUE.value = 0;
		Templates.MAP_MARKER_BLUE.anchorX = 0;
		Templates.MAP_MARKER_BLUE.anchorY = 0;
		Templates.MAP_MARKER_BLUE.templateId = GameObject.generateNewTemplateId();
		Templates.MAP_MARKER_BLUE.flipYAxisInMirror = false;

		Templates.MAP_MARKER_SKULL = new MapMarker();
		Templates.MAP_MARKER_SKULL.name = "";
		Templates.MAP_MARKER_SKULL.imageTexturePath = "map_marker_skull.png";
		Templates.MAP_MARKER_SKULL.imageTexture = getGlobalImage(Templates.MAP_MARKER_SKULL.imageTexturePath, true);
		Templates.MAP_MARKER_SKULL.totalHealth = Templates.MAP_MARKER_SKULL.remainingHealth = 1;
		Templates.MAP_MARKER_SKULL.widthRatio = 1f;
		Templates.MAP_MARKER_SKULL.heightRatio = 1f;
		Templates.MAP_MARKER_SKULL.drawOffsetRatioX = 0f;
		Templates.MAP_MARKER_SKULL.drawOffsetRatioY = 0f;
		Templates.MAP_MARKER_SKULL.soundWhenHit = 1f;
		Templates.MAP_MARKER_SKULL.soundWhenHitting = 1f;
		Templates.MAP_MARKER_SKULL.soundDampening = 1f;
		Templates.MAP_MARKER_SKULL.stackable = false;
		Templates.MAP_MARKER_SKULL.weight = 0f;
		Templates.MAP_MARKER_SKULL.value = 0;
		Templates.MAP_MARKER_SKULL.anchorX = 0;
		Templates.MAP_MARKER_SKULL.anchorY = 0;
		Templates.MAP_MARKER_SKULL.templateId = GameObject.generateNewTemplateId();
		Templates.MAP_MARKER_SKULL.flipYAxisInMirror = false;

		Templates.MAP_MARKER_TREASURE = new MapMarker();
		Templates.MAP_MARKER_TREASURE.name = "";
		Templates.MAP_MARKER_TREASURE.imageTexturePath = "map_marker_treasure.png";
		Templates.MAP_MARKER_TREASURE.imageTexture = getGlobalImage(Templates.MAP_MARKER_TREASURE.imageTexturePath,
				true);
		Templates.MAP_MARKER_TREASURE.totalHealth = Templates.MAP_MARKER_TREASURE.remainingHealth = 1;
		Templates.MAP_MARKER_TREASURE.widthRatio = 1f;
		Templates.MAP_MARKER_TREASURE.heightRatio = 1f;
		Templates.MAP_MARKER_TREASURE.drawOffsetRatioX = 0f;
		Templates.MAP_MARKER_TREASURE.drawOffsetRatioY = 0f;
		Templates.MAP_MARKER_TREASURE.soundWhenHit = 1f;
		Templates.MAP_MARKER_TREASURE.soundWhenHitting = 1f;
		Templates.MAP_MARKER_TREASURE.soundDampening = 1f;
		Templates.MAP_MARKER_TREASURE.stackable = false;
		Templates.MAP_MARKER_TREASURE.weight = 0f;
		Templates.MAP_MARKER_TREASURE.value = 0;
		Templates.MAP_MARKER_TREASURE.anchorX = 0;
		Templates.MAP_MARKER_TREASURE.anchorY = 0;
		Templates.MAP_MARKER_TREASURE.templateId = GameObject.generateNewTemplateId();
		Templates.MAP_MARKER_TREASURE.flipYAxisInMirror = false;

	}

}
