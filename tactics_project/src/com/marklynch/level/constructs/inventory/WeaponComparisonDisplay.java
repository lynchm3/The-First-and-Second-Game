package com.marklynch.level.constructs.inventory;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class WeaponComparisonDisplay {

	public final static String stringEquipped = "EQUIPPED";

	int weaponWidth;
	int weaponHeight;
	int width;

	int x;
	int y;
	int height;
	int halfWidth;

	int equippedStringX;
	int equippedStringY;
	int weapon1DrawX;
	int weaponDrawY;
	int weapon2DrawX;

	int statsOfEquippedRightX;
	int statsOfHoveredX;
	int iconsX;

	int nameY;
	int slashDamageY;
	int bluntDamageY;
	int pierceDamageY;
	int fireDamageY;
	int waterDamageY;
	int electricalDamageY;
	int poisonDamageY;
	int rangeY;
	int weightY;
	int valueY;

	public static Texture imageSlash;
	public static Texture imagePierce;
	public static Texture imageBlunt;
	public static Texture imageFire;
	public static Texture imageWater;
	public static Texture imageElectrical;
	public static Texture imagePosion;
	public static Texture imageRange;
	public static Texture imageWeight;
	public static Texture imageValue;

	public WeaponComparisonDisplay() {
		resize();
	}

	public static void loadStaticImages() {
		imageSlash = getGlobalImage("action_slash.png");
		imagePierce = getGlobalImage("action_pierce.png");
		imageBlunt = getGlobalImage("action_blunt.png");
		imageFire = getGlobalImage("action_burn.png");
		imageWater = getGlobalImage("action_douse.png");
		imageElectrical = getGlobalImage("action_electric.png");
		imagePosion = getGlobalImage("action_poison.png");
		imageRange = getGlobalImage("range.png");
		imageWeight = getGlobalImage("weight.png");
		imageValue = getGlobalImage("gold.png");
	}

	public void drawStaticUI() {

		if (Game.level.player.inventory.inventorySquareMouseIsOver == null)
			return;

		if (Game.level.player.inventory.inventorySquareMouseIsOver.gameObject == null)
			return;

		// if
		// (!(Game.level.player.inventory.inventorySquareMouseIsOver.gameObject
		// instanceof Weapon))
		// return;

		// if (Game.level.player.equipped == null)
		// return;

		// if (!(Game.level.player.equipped instanceof Weapon))
		// return;

		GameObject weapon1 = Game.level.player.equipped;
		GameObject weapon2 = Game.level.player.inventory.inventorySquareMouseIsOver.gameObject;

		// "Equipped"
		TextUtils.printTextWithImages(equippedStringX, equippedStringY, Integer.MAX_VALUE, false, null, stringEquipped);

		// Squares
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon1DrawX, weaponDrawY, weapon1DrawX + weaponWidth,
				weaponDrawY + weaponHeight);
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon2DrawX, weaponDrawY, weapon2DrawX + weaponWidth,
				weaponDrawY + weaponHeight);

		// Weapon images
		if (weapon1 != null)
			TextureUtils.drawTexture(weapon1.imageTexture, weapon1DrawX, weaponDrawY, weapon1DrawX + weaponWidth,
					weaponDrawY + weaponHeight);
		TextureUtils.drawTexture(weapon2.imageTexture, weapon2DrawX, weaponDrawY, weapon2DrawX + weaponWidth,
				weaponDrawY + weaponHeight);

		Color color1 = Color.WHITE;
		Color color2 = Color.WHITE;

		// Name
		if (weapon1 != null)
			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + weapon1.name), nameY,
					Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + weapon1.name, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, nameY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.name, color2) });

		// Slash Damage
		float equippedSlashDamage = 0;
		if (weapon1 != null)
			equippedSlashDamage = weapon1.slashDamage;
		if (equippedSlashDamage == weapon2.slashDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedSlashDamage < weapon2.slashDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedSlashDamage),
				slashDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + equippedSlashDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, slashDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.slashDamage, color2) });
		TextureUtils.drawTexture(imageSlash, iconsX, slashDamageY, iconsX + 16, slashDamageY + 16);

		// Pierce Damage
		float equippedPierceDamage = 0;
		if (weapon1 != null)
			equippedPierceDamage = weapon1.pierceDamage;
		if (equippedPierceDamage == weapon2.pierceDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedPierceDamage < weapon2.pierceDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedPierceDamage),
				pierceDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + equippedPierceDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, pierceDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.pierceDamage, color2) });
		TextureUtils.drawTexture(imagePierce, iconsX, pierceDamageY, iconsX + 16, pierceDamageY + 16);

		// Blunt Damage
		float equippedBluntDamage = 1;
		if (weapon1 != null)
			equippedBluntDamage = weapon1.bluntDamage;
		if (equippedBluntDamage == weapon2.bluntDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedBluntDamage < weapon2.bluntDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedBluntDamage),
				bluntDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + equippedBluntDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, bluntDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.bluntDamage, color2) });
		TextureUtils.drawTexture(imageBlunt, iconsX, bluntDamageY, iconsX + 16, bluntDamageY + 16);

		// Fire Damage
		float equippedFireDamage = 0;
		if (weapon1 != null)
			equippedBluntDamage = weapon1.fireDamage;
		if (equippedFireDamage == weapon2.fireDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedFireDamage < weapon2.fireDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedFireDamage), fireDamageY,
				Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + equippedFireDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, fireDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.fireDamage, color2) });
		TextureUtils.drawTexture(imageFire, iconsX, fireDamageY, iconsX + 16, fireDamageY + 16);

		// Water Damage
		float equippedWaterDamage = 0;
		if (weapon1 != null)
			equippedWaterDamage = weapon1.waterDamage;
		if (equippedWaterDamage == weapon2.waterDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedWaterDamage < weapon2.waterDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedWaterDamage),
				waterDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + equippedWaterDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, waterDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.waterDamage, color2) });
		TextureUtils.drawTexture(imageWater, iconsX, waterDamageY, iconsX + 16, waterDamageY + 16);

		// Electrical Damage
		float equippedElectricalDamage = 0;
		if (weapon1 != null)
			equippedBluntDamage = weapon1.electricalDamage;
		if (equippedElectricalDamage == weapon2.electricalDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedElectricalDamage < weapon2.electricalDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedElectricalDamage),
				electricalDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + equippedElectricalDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, electricalDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.electricalDamage, color2) });
		TextureUtils.drawTexture(imageElectrical, iconsX, electricalDamageY, iconsX + 16, electricalDamageY + 16);

		// Posion Damage
		float equippedPoisonDamage = 0;
		if (weapon1 != null)
			equippedPoisonDamage = weapon1.poisonDamage;
		if (equippedPoisonDamage == weapon2.poisonDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedPoisonDamage < weapon2.poisonDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedPoisonDamage),
				poisonDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + equippedPoisonDamage, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, poisonDamageY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.poisonDamage, color2) });
		TextureUtils.drawTexture(imagePosion, iconsX, poisonDamageY, iconsX + 16, poisonDamageY + 16);

		// Range
		float equippedMaxRange = 0;
		if (weapon1 != null)
			equippedMaxRange = weapon1.maxRange;
		if (equippedMaxRange == weapon2.maxRange) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedMaxRange < weapon2.maxRange) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedMaxRange), rangeY,
				Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + equippedMaxRange, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, rangeY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.maxRange, color2) });
		TextureUtils.drawTexture(imageRange, iconsX, rangeY, iconsX + 16, rangeY + 16);

		// Weight
		float equippedWeight = 0;
		if (weapon1 != null)
			equippedWeight = weapon1.weight;
		if (equippedWeight == weapon2.weight) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedWeight < weapon2.weight) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedWeight), weightY,
				Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + equippedWeight, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, weightY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.weight, color2) });
		TextureUtils.drawTexture(imageWeight, iconsX, weightY, iconsX + 16, weightY + 16);

		// vlaue
		float equippedValue = 0;
		if (weapon1 != null)
			equippedValue = weapon1.value;
		if (equippedValue == weapon2.value) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (equippedValue < weapon2.value) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}

		TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedValue), valueY,
				Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + equippedValue, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, valueY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.value, color2) });
		TextureUtils.drawTexture(imageValue, iconsX, valueY, iconsX + 16, valueY + 16);

	}

	public void resize() {

		weaponWidth = 128;
		weaponHeight = 128;
		width = weaponWidth * 5;

		x = (int) (Game.windowWidth / 2 - width / 2);
		y = (int) (Game.windowHeight - 256);
		height = 300;
		halfWidth = width / 2;

		equippedStringX = x + 32;
		equippedStringY = y - 32;
		weapon1DrawX = x;
		weaponDrawY = y;
		weapon2DrawX = x + width - weaponWidth;

		statsOfEquippedRightX = x + halfWidth - 10;
		statsOfHoveredX = x + halfWidth + 10;
		iconsX = x + halfWidth - 8;

		nameY = y;
		slashDamageY = y + 16;
		pierceDamageY = y + 32;
		bluntDamageY = y + 48;
		fireDamageY = y + 64;
		waterDamageY = y + 80;
		electricalDamageY = y + 96;
		poisonDamageY = y + 112;
		rangeY = y + 128;
		weightY = y + 144;
		valueY = y + 160;

	}

}
