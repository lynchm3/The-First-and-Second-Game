package com.marklynch.tactics.objects.weapons;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;

import mdesl.graphics.Texture;

public class WeaponTemplate extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"strength", "dexterity", "intelligence", "endurance", "totalHealth", "remainingHealth", "owner",
			"inventory", "showInventory" };

	// attributes
	public float damage = 0;
	public float minRange = 0;
	public float maxRange = 0;

	// image
	public String imagePath = "";
	public transient Texture imageTexture = null;
	public String name;

	public WeaponTemplate(String name, float damage, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn) {

		super(name, (int) health, imagePath, squareGameObjectIsOn, null, null, false);

		this.damage = damage;
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	@Override
	public void loadImages() {
		this.imageTexture = getGlobalImage(imagePath);
	}

	public Weapon makeWeapon() {
		return new Weapon(new String(name), damage, minRange, maxRange, imagePath, owner, totalHealth,
				squareGameObjectIsOn);
	}
}
