package com.marklynch.level.constructs;

import java.util.HashMap;
import java.util.Set;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;

public class Stats {
	public HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();

	public void put(HIGH_LEVEL_STATS statType, Stat stat) {
		highLevelStats.put(statType, stat);
	}

	public Stat get(HIGH_LEVEL_STATS statType) {
		return highLevelStats.get(statType);
	}

	public Set<HIGH_LEVEL_STATS> keySet() {
		return highLevelStats.keySet();
	}

	@Override
	public String toString() {
		return "Stats [highLevelStats=" + highLevelStats + "]";
	}

}
