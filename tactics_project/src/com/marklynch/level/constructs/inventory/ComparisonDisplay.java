package com.marklynch.level.constructs.inventory;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class ComparisonDisplay {

	public final static String stringEquipped = "EQUIPPED";

	int weaponWidth;
	int weaponHeight;
	int width;

	int x;
	int y;
	int fieldHeight = 16;
	int halfWidth;

	int equippedStringX;
	int equippedStringY;
	int weapon1DrawX;
	int weaponDrawY;
	int weapon2DrawX;

	int statsOfEquippedRightX;
	int statsOfHoveredX;
	int iconsX;

	// int nameY;
	// int slashDamageY;
	// int bluntDamageY;
	// int pierceDamageY;
	// int fireDamageY;
	// int waterDamageY;
	// int electricalDamageY;
	// int poisonDamageY;
	// int rangeY;
	// int weightY;
	// int valueY;

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

	public ComparisonDisplay() {
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

		int currentY = y;

		// Name
		if (weapon1 != null)
			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + weapon1.name), currentY,
					Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + weapon1.name, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
				new Object[] { new StringWithColor("" + weapon2.name, color2) });
		currentY += fieldHeight;

		// Slash Damage
		float equippedSlashDamage = 0;
		if (weapon1 != null)
			equippedSlashDamage = weapon1.slashDamage;
		if (equippedSlashDamage != 0 || weapon2.slashDamage != 0) {
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
					currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedSlashDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.slashDamage, color2) });
			TextureUtils.drawTexture(imageSlash, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Pierce Damage
		float equippedPierceDamage = 0;
		if (weapon1 != null)
			equippedPierceDamage = weapon1.pierceDamage;
		if (equippedPierceDamage != 0 || weapon2.pierceDamage != 0) {
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
					currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedPierceDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.pierceDamage, color2) });
			TextureUtils.drawTexture(imagePierce, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Blunt Damage
		float equippedBluntDamage = 1;
		if (weapon1 != null)
			equippedBluntDamage = weapon1.bluntDamage;
		if (equippedBluntDamage != 0 || weapon2.bluntDamage != 0) {
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
					currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedBluntDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.bluntDamage, color2) });
			TextureUtils.drawTexture(imageBlunt, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Fire Damage
		float equippedFireDamage = 0;
		if (weapon1 != null)
			equippedBluntDamage = weapon1.fireDamage;
		if (equippedFireDamage != 0 || weapon2.fireDamage != 0) {
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

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedFireDamage), currentY,
					Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedFireDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.fireDamage, color2) });
			TextureUtils.drawTexture(imageFire, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Water Damage
		float equippedWaterDamage = 0;
		if (weapon1 != null)
			equippedWaterDamage = weapon1.waterDamage;
		if (equippedWaterDamage != 0 || weapon2.waterDamage != 0) {
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
					currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedWaterDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.waterDamage, color2) });
			TextureUtils.drawTexture(imageWater, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Electrical Damage
		float equippedElectricalDamage = 0;
		if (weapon1 != null)
			equippedBluntDamage = weapon1.electricalDamage;
		if (equippedElectricalDamage != 0 || weapon2.electricalDamage != 0) {
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
					currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedElectricalDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.electricalDamage, color2) });
			TextureUtils.drawTexture(imageElectrical, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Posion Damage
		float equippedPoisonDamage = 0;
		if (weapon1 != null)
			equippedPoisonDamage = weapon1.poisonDamage;
		if (equippedPoisonDamage != 0 || weapon2.poisonDamage != 0) {
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
					currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedPoisonDamage, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.poisonDamage, color2) });
			TextureUtils.drawTexture(imagePosion, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Range
		float equippedMaxRange = 0;
		if (weapon1 != null)
			equippedMaxRange = weapon1.maxRange;
		if (equippedMaxRange != 1 || weapon2.maxRange != 1) {
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

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedMaxRange), currentY,
					Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + equippedMaxRange, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.maxRange, color2) });
			TextureUtils.drawTexture(imageRange, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Weight
		float equippedWeight = 0;
		if (weapon1 != null)
			equippedWeight = weapon1.weight;
		if (equippedWeight != 0 || weapon2.weight != 0) {
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

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedWeight), currentY,
					Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + equippedWeight, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.weight, color2) });
			TextureUtils.drawTexture(imageWeight, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// vlaue
		float equippedValue = 0;
		if (weapon1 != null)
			equippedValue = weapon1.value;
		if (equippedValue != 0 || weapon2.value != 0) {
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

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.font.getWidth("" + equippedValue), currentY,
					Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor("" + equippedValue, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null,
					new Object[] { new StringWithColor("" + weapon2.value, color2) });
			TextureUtils.drawTexture(imageValue, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

	}

	public void resize() {

		weaponWidth = 128;
		weaponHeight = 128;
		width = weaponWidth * 5;

		x = (int) (Game.windowWidth / 2 - width / 2);
		y = (int) (Game.windowHeight - 256);
		halfWidth = width / 2;

		equippedStringX = x + 32;
		equippedStringY = y - 32;
		weapon1DrawX = x;
		weaponDrawY = y;
		weapon2DrawX = x + width - weaponWidth;

		statsOfEquippedRightX = x + halfWidth - 10;
		statsOfHoveredX = x + halfWidth + 10;
		iconsX = x + halfWidth - 8;

	}

}
