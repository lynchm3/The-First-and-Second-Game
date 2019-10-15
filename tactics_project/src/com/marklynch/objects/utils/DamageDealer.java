package com.marklynch.objects.utils;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;

public interface DamageDealer {
	//
	public float getEffectiveHighLevelStat(HIGH_LEVEL_STATS statType);

	public CopyOnWriteArrayList<Object> getEffectiveHighLevelStatTooltip(HIGH_LEVEL_STATS statType);

}