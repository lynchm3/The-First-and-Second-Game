package com.marklynch.level.constructs;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class Faction implements Idable {

	public long id;
	public String name;
	public HashMap<Faction, Integer> relationships = new HashMap<Faction, Integer>();
	public ArrayList<Actor> actors = new ArrayList<Actor>();
	public Texture imageTexture = null;

	public Faction(String name, String imagePath) {
		id = Level.generateNewId(this);
		this.name = name;
		this.imageTexture = getGlobalImage(imagePath, false);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Long getId() {
		return id;
	}
}
