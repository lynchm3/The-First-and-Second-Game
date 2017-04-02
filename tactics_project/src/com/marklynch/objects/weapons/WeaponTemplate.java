package com.marklynch.objects.weapons;

import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class WeaponTemplate extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	// attributes
	public float slashDamage = 0;
	public float pierceDamage = 0;
	public float bluntDamage = 0;
	public float fireDamage = 0; // fire/purify/clean
	public float waterDamage = 0; // water/life
	public float electricalDamage = 0; // lightning/light/electrical/speed
	public float poisonDamage = 0;// poison/ground/contaminate/neutralize/slow/corruption
	public float minRange = 0;
	public float maxRange = 0;

	public WeaponTemplate(String name, float damage, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, Actor owner) {

		super(name, (int) health, imagePath, squareGameObjectIsOn, new Inventory(), false, true, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, owner);

		this.slashDamage = damage;
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public float getEffectiveSlashDamage() {
		return slashDamage;
	}

	public float getEffectivePierceDamage() {
		return pierceDamage;
	}

	public float getEffectiveBluntDamage() {
		return bluntDamage;
	}

	public float getEffectiveFireDamage() {
		return fireDamage;
	}

	public float getEffectiveWaterDamage() {
		return waterDamage;
	}

	public float getEffectiveElectricalDamage() {
		return electricalDamage;
	}

	public float getEffectivePoisonDamage() {
		return poisonDamage;
	}

	public float getEffectiveMinRange() {
		return minRange;
	}

	public float getEffectiveMaxRange() {
		return maxRange;
	}

	public float getTotalDamage() {
		return slashDamage + pierceDamage + bluntDamage + fireDamage + waterDamage + electricalDamage + poisonDamage;
	}

	public float getTotalEffectiveDamage() {
		return getEffectiveSlashDamage() + getEffectivePierceDamage() + getEffectiveBluntDamage()
				+ getEffectiveFireDamage() + getEffectiveWaterDamage() + getEffectiveElectricalDamage()
				+ getEffectivePoisonDamage();
	}

	@Override
	public Weapon makeCopy(Square square, Actor owner) {
		return new Weapon(new String(name), slashDamage, minRange, maxRange, imageTexturePath, totalHealth, square,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, owner

				, anchorX, anchorY);
	}
}
