package com.marklynch.level.constructs;

import java.util.ArrayList;
import java.util.HashMap;

public class Stat {

	public static enum HIGH_LEVEL_STATS {
		STRENGTH, DEXTERITY, ENDURANCE, INTELLIGENCE, FRIENDLY_FIRE,
		//
		SLASH_DAMAGE, BLUNT_DAMAGE, PIERCE_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, ELECTRICAL_DAMAGE, POISON_DAMAGE, BLEED_DAMAGE, HEALING,
		//
		SLASH_RES, BLUNT_RES, PIERCE_RES, FIRE_RES, WATER_RES, ELECTRICAL_RES, POISON_RES, BLEED_RES, HEALING_RES
	};

	public final static ArrayList<HIGH_LEVEL_STATS> GENERAL_STATS = new ArrayList<HIGH_LEVEL_STATS>();

	public final static ArrayList<HIGH_LEVEL_STATS> OFFENSIVE_STATS = new ArrayList<HIGH_LEVEL_STATS>();

	public final static ArrayList<HIGH_LEVEL_STATS> DEFENSIVE_STATS = new ArrayList<HIGH_LEVEL_STATS>();

	public static HashMap<HIGH_LEVEL_STATS, HIGH_LEVEL_STATS> offensiveStatToDefensiveStatMap;

	// public static enum HIGH_LEVEL_STATS {
	//
	// };

	// public static enum DEFENSIVE_STATS {
	// SLASH_DAMAGE, BLUNT_DAMAGE, PIERCE_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE,
	// ELECTRICAL_DAMAGE, POISON_DAMAGE, BLEED_DAMAGE
	// };

	public float value;
	public HIGH_LEVEL_STATS type;

	public Stat(float value) {
		super();

		this.value = value;
	}

	public Stat(HIGH_LEVEL_STATS type, float value) {
		super();

		this.type = type;
		this.value = value;
	}

	public static void init() {

		GENERAL_STATS.add(HIGH_LEVEL_STATS.STRENGTH);
		GENERAL_STATS.add(HIGH_LEVEL_STATS.DEXTERITY);
		GENERAL_STATS.add(HIGH_LEVEL_STATS.ENDURANCE);
		GENERAL_STATS.add(HIGH_LEVEL_STATS.INTELLIGENCE);
		GENERAL_STATS.add(HIGH_LEVEL_STATS.FRIENDLY_FIRE);

		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.SLASH_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.BLUNT_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.PIERCE_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.FIRE_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.WATER_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.POISON_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.BLEED_DAMAGE);
		OFFENSIVE_STATS.add(HIGH_LEVEL_STATS.HEALING);

		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.SLASH_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.BLUNT_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.PIERCE_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.FIRE_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.WATER_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.ELECTRICAL_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.POISON_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.BLEED_RES);
		DEFENSIVE_STATS.add(HIGH_LEVEL_STATS.HEALING_RES);

		offensiveStatToDefensiveStatMap = new HashMap<HIGH_LEVEL_STATS, HIGH_LEVEL_STATS>();
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, HIGH_LEVEL_STATS.SLASH_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, HIGH_LEVEL_STATS.BLUNT_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, HIGH_LEVEL_STATS.PIERCE_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, HIGH_LEVEL_STATS.FIRE_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.WATER_DAMAGE, HIGH_LEVEL_STATS.WATER_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, HIGH_LEVEL_STATS.ELECTRICAL_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.POISON_DAMAGE, HIGH_LEVEL_STATS.POISON_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.BLEED_DAMAGE, HIGH_LEVEL_STATS.BLEED_RES);
		offensiveStatToDefensiveStatMap.put(HIGH_LEVEL_STATS.HEALING, HIGH_LEVEL_STATS.HEALING_RES);

	}

	public Stat(float value, HIGH_LEVEL_STATS type) {
		super();
		this.value = value;
		this.type = type;
	}

	public Stat makeCopy() {
		return new Stat(value);
	}

}
