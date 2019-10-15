package com.marklynch.level.constructs;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.concurrent.ConcurrentHashMap;

import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Texture;

public class Faction implements Idable {

	public long id;
	public String name;
	public ConcurrentHashMap<Faction, Integer> relationships = new ConcurrentHashMap<Faction, Integer>();
	public CopyOnWriteArrayList<Actor> actors = new CopyOnWriteArrayList<Actor>(Actor.class);
	public Texture imageTexture = null;

	public Faction() {
	}

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
