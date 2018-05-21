package com.marklynch.objects;

public interface DamageDealer {

	float getEffectiveSlashDamage();

	float getEffectiveBluntDamage();

	float getEffectivePierceDamage();

	float getEffectiveFireDamage();

	float getEffectiveWaterDamage();

	float getEffectiveElectricalDamage();

	float getEffectivePoisonDamage();

	float getEffectiveHealing();

	float getEffectiveBleedDamage();

	String getEffectiveSlashDamageTooltip();

	String getEffectiveBluntDamageTooltip();

	String getEffectivePierceDamageTooltip();

	String getEffectiveFireDamageTooltip();

	String getEffectiveWaterDamageTooltip();

	String getEffectiveElectricalDamageTooltip();

	String getEffectivePoisonDamageTooltip();

	String getEffectiveHealingTooltip();

	String getEffectiveBleedDamageTooltip();

}