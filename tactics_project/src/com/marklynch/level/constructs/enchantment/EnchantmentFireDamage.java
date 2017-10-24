package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

public class EnchantmentFireDamage extends Enchantment {

	// Template id
	public int templateId;

	public EnchantmentFireDamage() {
		this.enchantmentName = "Fire Dmg";
		this.imageTexture = getGlobalImage("effect_bleed.png");
		fireDamage = 10;
	}

	@Override
	public void activate() {

	}

	@Override
	public EnchantmentFireDamage makeCopy() {
		return new EnchantmentFireDamage();
	}
}
