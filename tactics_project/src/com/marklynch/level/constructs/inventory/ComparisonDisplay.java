package com.marklynch.level.constructs.inventory;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.characterscreen.CharacterScreen;
import com.marklynch.level.constructs.rarity.Rarity;
import com.marklynch.objects.armor.BodyArmor;
import com.marklynch.objects.armor.Helmet;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class ComparisonDisplay {

	public final static String stringEquipped = "EQUIPPED";

	int weaponWidth;
	int weaponHeight;
	int width;
	int height;
	int minHeight;

	int x;
	int y;
	int fieldHeight = 16;
	int halfWidth;

	int equippedStringX;
	int equippedStringY;
	int weapon1DrawX;
	int weapon2DrawX;

	int statsOfEquippedRightX;
	int statsOfHoveredX;
	int iconsX;

	public static Texture imageRange;
	public static Texture imageWeight;
	public static Texture imageValue;

	public ComparisonDisplay() {

		weaponWidth = 128;
		weaponHeight = 128;
		minHeight = height = weaponHeight;// + 8;
		width = weaponWidth * 3;

		resize();
	}

	public static void loadStaticImages() {
		imageRange = getGlobalImage("range.png", false);
		imageWeight = getGlobalImage("weight.png", false);
		imageValue = getGlobalImage("gold.png", false);

	}

	// Color backgroundColor = Color.BLACK;
	Color backgroundColor = new Color(0f, 0f, 0f, 0.9f);

	public void drawStaticUI() {

		if (Game.level.player.inventory.inventorySquareMouseIsOver == null)
			return;

		if (Game.level.player.inventory.inventorySquareMouseIsOver.stack.get(0) == null)
			return;

		x = Mouse.getX() + 32;
		y = (int) (Game.windowHeight - Mouse.getY()) + 32;

		// x = (int) (Game.windowWidth / 2 - width / 2);
		// y = (int) (Game.windowHeight - 256);
		halfWidth = width / 2;

		equippedStringX = x + 32;
		equippedStringY = y;
		weapon1DrawX = x;
		weapon2DrawX = x + width - weaponWidth;

		statsOfEquippedRightX = x + halfWidth - 10;
		statsOfHoveredX = x + halfWidth + 10;
		iconsX = x + halfWidth - 8;

		GameObject gameObjectMouseIsOverInInventory = Level.player.inventory.inventorySquareMouseIsOver.stack.get(0);

		GameObject gameObjectEquipped = Level.player.equipped;
		if (gameObjectMouseIsOverInInventory instanceof BodyArmor) {
			gameObjectEquipped = Level.player.bodyArmor;
		} else if (gameObjectMouseIsOverInInventory instanceof LegArmor) {
			gameObjectEquipped = Level.player.legArmor;
		} else if (gameObjectMouseIsOverInInventory instanceof Helmet) {
			gameObjectEquipped = Level.player.helmet;
		}

		QuadUtils.drawQuad(backgroundColor, x, y, x + width, y + height);

		// "Equipped"
		// TextUtils.printTextWithImages(equippedStringX, equippedStringY,
		// Integer.MAX_VALUE, false, null, stringEquipped);

		Color color1 = Color.WHITE;
		Color color2 = Color.WHITE;

		int currentY = y;

		// Name
		if (gameObjectEquipped != null) {
			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + gameObjectEquipped.name),
					currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + gameObjectEquipped.name, color1) });
		}
		TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
				new Object[] { new StringWithColor("" + gameObjectMouseIsOverInInventory.name, color2) });

		currentY += fieldHeight;

		// Description
		if (gameObjectEquipped != null) {
			TextUtils.printTextWithImages(
					statsOfEquippedRightX - Game.smallFont.getWidth("" + gameObjectEquipped.description), currentY,
					Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + gameObjectEquipped.description, color1) });
		}
		TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
				new Object[] { new StringWithColor("" + gameObjectMouseIsOverInInventory.description, color2) });

		currentY += fieldHeight;
		currentY += fieldHeight;

		// Squares
		Color squareOutlineColorForEquipped = Rarity.COMMON.color;
		if (gameObjectEquipped != null) {
			squareOutlineColorForEquipped = gameObjectEquipped.rarity.color;
		}

		TextureUtils.drawTexture(InventorySquare.GREY_TRANSLUCENT_SQUARE, weapon1DrawX, currentY,
				weapon1DrawX + weaponWidth, currentY + weaponHeight,
				InventorySquare.getBackgroundColorForInventorySquare(gameObjectEquipped));
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon1DrawX, currentY, weapon1DrawX + weaponWidth,
				currentY + weaponHeight, squareOutlineColorForEquipped);

		Color squareOutlineColorForObjectMouseIsOverInInventory = gameObjectMouseIsOverInInventory.rarity.color;
		TextureUtils.drawTexture(InventorySquare.GREY_TRANSLUCENT_SQUARE, weapon2DrawX, currentY,
				weapon2DrawX + weaponWidth, currentY + weaponHeight,
				InventorySquare.getBackgroundColorForInventorySquare(gameObjectMouseIsOverInInventory));
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon2DrawX, currentY, weapon2DrawX + weaponWidth,
				currentY + weaponHeight, squareOutlineColorForObjectMouseIsOverInInventory);

		// Images
		if (gameObjectEquipped != null) {
			TextureUtils.drawTexture(gameObjectEquipped.imageTexture, weapon1DrawX, currentY,
					weapon1DrawX + weaponWidth, currentY + weaponHeight);
		}
		TextureUtils.drawTexture(gameObjectMouseIsOverInInventory.imageTexture, weapon2DrawX, currentY,
				weapon2DrawX + weaponWidth, currentY + weaponHeight);

		// Offensive stats
		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			float equippedStat = Level.player.highLevelStats.get(statType).value;
			if (gameObjectEquipped != null)
				equippedStat = gameObjectEquipped.highLevelStats.get(statType).value;
			if (equippedStat != 0 || gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value != 0) {
				if (equippedStat == gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value) {
					color1 = Color.WHITE;
					color2 = Color.WHITE;
				} else if (equippedStat < gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value) {
					color1 = Color.RED;
					color2 = Color.GREEN;
				} else {
					color1 = Color.GREEN;
					color2 = Color.RED;
				}

				TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedStat),
						currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
						new Object[] { new StringWithColor("" + equippedStat, color1) });
				TextUtils
						.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
								new Object[] { new StringWithColor(
										"" + gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value,
										color2) });
				TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(statType), iconsX, currentY,
						iconsX + 16, currentY + 16);
				currentY += fieldHeight;
			}
		}

		// Defensive stats
		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			float equippedStat = Level.player.highLevelStats.get(statType).value;
			if (gameObjectEquipped != null)
				equippedStat = gameObjectEquipped.highLevelStats.get(statType).value;
			if (equippedStat != 0 || gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value != 0) {
				if (equippedStat == gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value) {
					color1 = Color.WHITE;
					color2 = Color.WHITE;
				} else if (equippedStat < gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value) {
					color1 = Color.RED;
					color2 = Color.GREEN;
				} else {
					color1 = Color.GREEN;
					color2 = Color.RED;
				}

				TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedStat),
						currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
						new Object[] { new StringWithColor("" + equippedStat, color1) });
				TextUtils
						.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
								new Object[] { new StringWithColor(
										"" + gameObjectMouseIsOverInInventory.highLevelStats.get(statType).value,
										color2) });
				TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(statType), iconsX, currentY,
						iconsX + 16, currentY + 16);
				currentY += fieldHeight;
			}
		}

		// Range
		float equippedMaxRange = 0;
		if (gameObjectEquipped != null)
			equippedMaxRange = gameObjectEquipped.maxRange;
		if (equippedMaxRange != 1 || gameObjectMouseIsOverInInventory.maxRange != 1) {
			if (equippedMaxRange == gameObjectMouseIsOverInInventory.maxRange) {
				color1 = Color.WHITE;
				color2 = Color.WHITE;
			} else if (equippedMaxRange < gameObjectMouseIsOverInInventory.maxRange) {
				color1 = Color.RED;
				color2 = Color.GREEN;
			} else {
				color1 = Color.GREEN;
				color2 = Color.RED;
			}

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedMaxRange),
					currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + equippedMaxRange, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + gameObjectMouseIsOverInInventory.maxRange, color2) });
			TextureUtils.drawTexture(imageRange, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Weight
		float equippedWeight = 0;
		if (gameObjectEquipped != null)
			equippedWeight = gameObjectEquipped.weight;
		if (equippedWeight != 0 || gameObjectMouseIsOverInInventory.weight != 0) {
			if (equippedWeight == gameObjectMouseIsOverInInventory.weight) {
				color1 = Color.WHITE;
				color2 = Color.WHITE;
			} else if (equippedWeight < gameObjectMouseIsOverInInventory.weight) {
				color1 = Color.RED;
				color2 = Color.GREEN;
			} else {
				color1 = Color.GREEN;
				color2 = Color.RED;
			}

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedWeight),
					currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + equippedWeight, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + gameObjectMouseIsOverInInventory.weight, color2) });
			TextureUtils.drawTexture(imageWeight, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// value
		float equippedValue = 0;
		if (gameObjectEquipped != null)
			equippedValue = gameObjectEquipped.value;
		if (equippedValue != 0 || gameObjectMouseIsOverInInventory.value != 0) {
			if (equippedValue == gameObjectMouseIsOverInInventory.value) {
				color1 = Color.WHITE;
				color2 = Color.WHITE;
			} else if (equippedValue < gameObjectMouseIsOverInInventory.value) {
				color1 = Color.RED;
				color2 = Color.GREEN;
			} else {
				color1 = Color.GREEN;
				color2 = Color.RED;
			}

			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedValue), currentY,
					Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + equippedValue, color1) });
			TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + gameObjectMouseIsOverInInventory.value, color2) });
			TextureUtils.drawTexture(imageValue, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		height = currentY + 32 - y;
		if (height < minHeight)
			height = minHeight;

	}

	public void resize() {

	}

}
