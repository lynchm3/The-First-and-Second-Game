package com.marklynch.level.constructs;

public class Stat {

	public static enum HIGH_LEVEL_STATS {
		STRENGTH, DEXTERITY, ENDURANCE, INTELLIGENCE, FRIENDLY_FIRE
	};

	public static enum OFFENSIVE_STATS {
		SLASH_DAMAGE, BLUNT_DAMAGE, PIERCE_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, ELECTRICAL_DAMAGE, POISON_DAMAGE, BLEED_DAMAGE, HEALING
	};

	// public static enum DEFENSIVE_STATS {
	// SLASH_DAMAGE, BLUNT_DAMAGE, PIERCE_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE,
	// ELECTRICAL_DAMAGE, POISON_DAMAGE, BLEED_DAMAGE
	// };

	public float value;

	public Stat(float value) {
		super();
		this.value = value;
	}

	public Stat makeCopy() {
		return new Stat(value);
	}

}
