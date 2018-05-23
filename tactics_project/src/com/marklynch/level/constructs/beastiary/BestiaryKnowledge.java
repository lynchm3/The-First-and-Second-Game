package com.marklynch.level.constructs.beastiary;

import java.util.HashMap;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;

public class BestiaryKnowledge {

	int templateId;

	// general
	public boolean name;
	public boolean level;
	public boolean image;
	public boolean totalHealth;
	public boolean faction;
	public boolean group;

	public HashMap<HIGH_LEVEL_STATS, Boolean> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Boolean>();
	public HashMap<OFFENSIVE_STATS, Boolean> offensiveStats = new HashMap<OFFENSIVE_STATS, Boolean>();
	public HashMap<OFFENSIVE_STATS, Boolean> defensiveStats = new HashMap<OFFENSIVE_STATS, Boolean>();

	public boolean range;

	// Powers
	public boolean powers;

	public BestiaryKnowledge(int templateId) {
		this.templateId = templateId;
	}

	public void putHighLevel(HIGH_LEVEL_STATS statType, boolean value) {
		highLevelStats.put(statType, value);
	}

	public void putOffensive(OFFENSIVE_STATS statType, boolean value) {
		offensiveStats.put(statType, value);
	}

	public void putDefensive(OFFENSIVE_STATS statType, boolean value) {
		defensiveStats.put(statType, value);
	}

}
