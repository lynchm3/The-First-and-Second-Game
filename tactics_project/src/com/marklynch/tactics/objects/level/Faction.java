package com.marklynch.tactics.objects.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.marklynch.tactics.objects.unit.Actor;

public class Faction {
	
	public String name;
	public Map<Faction, Integer> relationships = new HashMap<Faction, Integer>();
	public Vector<Actor> actors = new Vector<Actor>();
	
	public Faction(String name)
	{
		this.name = name;
	}
}
