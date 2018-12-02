package com.marklynch.level.constructs;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class Faction {

	public String name;

	/**
	 * Map relationships of this faction towards others +-100
	 */
	public transient HashMap<Faction, Integer> relationships = new HashMap<Faction, Integer>();
	public transient ArrayList<Actor> actors = new ArrayList<Actor>();

	public transient Texture imageTexture = null;

	public Faction(String name, String imagePath) {
		this.name = name;
		this.imageTexture = getGlobalImage(imagePath, false);
	}

	@Override
	public String toString() {
		return name;
	}
}
