package com.marklynch.level.constructs.faction;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;

@SuppressWarnings("serial")
public class FactionList extends ArrayList<Faction> {
	public Faction player;
	public Faction townsPeople;
	public Faction wolves;
	public Faction blind;
	public Faction rockGolems;
	public Faction rats;
	public Faction buns;
	public Faction foxes;
	public Faction outsiders;

	public FactionList() {

	}

	public void makeFactions() {

		// Add factions
		player = new Faction("Player", "faction_blue.png");
		Level.factions.add(player);

		townsPeople = new Faction("Townspeople", "faction_red.png");
		Level.factions.add(townsPeople);

		wolves = new Faction("Wolves", "wolf.png");
		Level.factions.add(wolves);

		blind = new Faction("Blind", "blind.png");
		Level.factions.add(blind);

		rockGolems = new Faction("Rock Golem", "blind.png");
		Level.factions.add(rockGolems);

		rats = new Faction("Rats", "blind.png");
		Level.factions.add(rats);

		buns = new Faction("Buns", "blind.png");
		Level.factions.add(buns);

		foxes = new Faction("Foxes", "blind.png");
		Level.factions.add(foxes);

		outsiders = new Faction("Outsiders", "blind.png");
		Level.factions.add(outsiders);
	}

}
