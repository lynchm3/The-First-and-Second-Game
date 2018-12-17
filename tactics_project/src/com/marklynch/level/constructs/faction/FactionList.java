package com.marklynch.level.constructs.faction;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;

@SuppressWarnings("serial")
public class FactionList extends ArrayList<Faction> {
	public static Faction player;
	public static Faction townsPeople;
	public static Faction wolves;
	public static Faction blind;
	public static Faction rockGolems;
	public static Faction rats;
	public static Faction buns;
	public static Faction foxes;
	public static Faction outsiders;

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
