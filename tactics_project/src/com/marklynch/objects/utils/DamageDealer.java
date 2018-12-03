package com.marklynch.objects.utils;

import java.util.ArrayList;

import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;

public interface DamageDealer {
	//
	public float getEffectiveHighLevelStat(HIGH_LEVEL_STATS statType);

	public ArrayList<Object> getEffectiveHighLevelStatTooltip(HIGH_LEVEL_STATS statType);

}