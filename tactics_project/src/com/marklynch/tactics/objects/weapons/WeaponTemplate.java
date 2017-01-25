package com.marklynch.tactics.objects.weapons;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import mdesl.graphics.Texture;

public class WeaponTemplate {
	public final static String[] editableAttributes = { "name", "imageTexture",
			"damage", "minRange", "maxRange" };

	// attributes
	public float damage = 0;
	public float minRange = 0;
	public float maxRange = 0;

	// image
	public String imagePath = "";
	public transient Texture imageTexture = null;
	public String name;

	public WeaponTemplate(String name, float damage, float minRange, float maxRange,
			String imagePath) {
		super();
		this.name = name;
		this.damage = damage;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.imagePath = imagePath;
		loadImages();
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imagePath);
	}
	
	public Weapon makeWeapon() {
		return new Weapon(new String(name), damage, minRange, maxRange,
				new String(imagePath));
	}
}
