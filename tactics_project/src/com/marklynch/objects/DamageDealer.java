package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;

public interface DamageDealer {

	public float getEffectiveOffensiveStat(OFFENSIVE_STATS statType);

	public ArrayList<Object> getEffectiveOffensiveStatTooltip(OFFENSIVE_STATS statType);

}