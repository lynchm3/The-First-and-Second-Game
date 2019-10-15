package com.marklynch.level.constructs.beastiary;

import java.util.concurrent.ConcurrentHashMap;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;

public class BestiaryKnowledge {

	int templateId;

	// general
	public boolean name;
	public boolean level;
	public boolean image;
	public boolean totalHealth;
	public boolean faction;
	public boolean groupOfActors;

	public ConcurrentHashMap<HIGH_LEVEL_STATS, Boolean> highLevelStats = new ConcurrentHashMap<HIGH_LEVEL_STATS, Boolean>();

	public boolean range;

	// Powers
	public boolean powers;

	public BestiaryKnowledge(int templateId) {
		this.templateId = templateId;
		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			putHighLevel(statType, false);
		}
	}

	public void putHighLevel(HIGH_LEVEL_STATS statType, boolean value) {
		highLevelStats.put(statType, value);
	}

	public boolean getHighLevel(HIGH_LEVEL_STATS statType) {
		return highLevelStats.get(statType);
	}

}
