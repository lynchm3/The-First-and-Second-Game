package com.marklynch.level.constructs.inventory;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.characterscreen.CharacterScreen;
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
	int weaponDrawY;
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
		weaponDrawY = y + 32;
		weapon2DrawX = x + width - weaponWidth;

		statsOfEquippedRightX = x + halfWidth - 10;
		statsOfHoveredX = x + halfWidth + 10;
		iconsX = x + halfWidth - 8;

		GameObject gameObject2 = Level.player.inventory.inventorySquareMouseIsOver.stack.get(0);

		GameObject gameObject1 = Level.player.equipped;
		if (gameObject2 instanceof BodyArmor) {
			gameObject1 = Level.player.bodyArmor;
		} else if (gameObject2 instanceof LegArmor) {
			gameObject1 = Level.player.legArmor;
		} else if (gameObject2 instanceof Helmet) {
			gameObject1 = Level.player.helmet;
		}

		QuadUtils.drawQuad(backgroundColor, x, y, x + width, y + height);

		// "Equipped"
		// TextUtils.printTextWithImages(equippedStringX, equippedStringY,
		// Integer.MAX_VALUE, false, null, stringEquipped);

		// Squares
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon1DrawX, weaponDrawY, weapon1DrawX + weaponWidth,
				weaponDrawY + weaponHeight);
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon2DrawX, weaponDrawY, weapon2DrawX + weaponWidth,
				weaponDrawY + weaponHeight);

		// Weapon images
		if (gameObject1 != null)
			TextureUtils.drawTexture(gameObject1.imageTexture, weapon1DrawX, weaponDrawY, weapon1DrawX + weaponWidth,
					weaponDrawY + weaponHeight);
		TextureUtils.drawTexture(gameObject2.imageTexture, weapon2DrawX, weaponDrawY, weapon2DrawX + weaponWidth,
				weaponDrawY + weaponHeight);

		Color color1 = Color.WHITE;
		Color color2 = Color.WHITE;

		int currentY = y;

		// Name
		if (gameObject1 != null)
			TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + gameObject1.name),
					currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor("" + gameObject1.name, color1) });
		TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
				new Object[] { new StringWithColor("" + gameObject2.name, color2) });
		currentY += fieldHeight;
		currentY += fieldHeight;

		// Offensive stats
		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			float equippedStat = Level.player.highLevelStats.get(statType).value;
			if (gameObject1 != null)
				equippedStat = gameObject1.highLevelStats.get(statType).value;
			if (equippedStat != 0 || gameObject2.highLevelStats.get(statType).value != 0) {
				if (equippedStat == gameObject2.highLevelStats.get(statType).value) {
					color1 = Color.WHITE;
					color2 = Color.WHITE;
				} else if (equippedStat < gameObject2.highLevelStats.get(statType).value) {
					color1 = Color.RED;
					color2 = Color.GREEN;
				} else {
					color1 = Color.GREEN;
					color2 = Color.RED;
				}

				TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedStat),
						currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
						new Object[] { new StringWithColor("" + equippedStat, color1) });
				TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
						new Object[] {
								new StringWithColor("" + gameObject2.highLevelStats.get(statType).value, color2) });
				TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(statType), iconsX, currentY,
						iconsX + 16, currentY + 16);
				currentY += fieldHeight;
			}
		}

		// Defensive stats
		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			float equippedStat = Level.player.highLevelStats.get(statType).value;
			if (gameObject1 != null)
				equippedStat = gameObject1.highLevelStats.get(statType).value;
			if (equippedStat != 0 || gameObject2.highLevelStats.get(statType).value != 0) {
				if (equippedStat == gameObject2.highLevelStats.get(statType).value) {
					color1 = Color.WHITE;
					color2 = Color.WHITE;
				} else if (equippedStat < gameObject2.highLevelStats.get(statType).value) {
					color1 = Color.RED;
					color2 = Color.GREEN;
				} else {
					color1 = Color.GREEN;
					color2 = Color.RED;
				}

				TextUtils.printTextWithImages(statsOfEquippedRightX - Game.smallFont.getWidth("" + equippedStat),
						currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
						new Object[] { new StringWithColor("" + equippedStat, color1) });
				TextUtils.printTextWithImages(statsOfHoveredX, currentY, Integer.MAX_VALUE, false, null, Color.WHITE,
						new Object[] {
								new StringWithColor("" + gameObject2.highLevelStats.get(statType).value, color2) });
				TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(statType), iconsX, currentY,
						iconsX + 16, currentY + 16);
				currentY += fieldHeight;
			}
		}

		// Range
		float equippedMaxRange = 0;
		if (gameObject1 != null)
			equippedMaxRange = gameObject1.maxRange;
		if (equippedMaxRange != 1 || gameObject2.maxRange != 1) {
			if (equippedMaxRange == gameObject2.maxRange) {
				color1 = Color.WHITE;
				color2 = Color.WHITE;
			} else if (equippedMaxRange < gameObject2.maxRange) {
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
					new Object[] { new StringWithColor("" + gameObject2.maxRange, color2) });
			TextureUtils.drawTexture(imageRange, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// Weight
		float equippedWeight = 0;
		if (gameObject1 != null)
			equippedWeight = gameObject1.weight;
		if (equippedWeight != 0 || gameObject2.weight != 0) {
			if (equippedWeight == gameObject2.weight) {
				color1 = Color.WHITE;
				color2 = Color.WHITE;
			} else if (equippedWeight < gameObject2.weight) {
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
					new Object[] { new StringWithColor("" + gameObject2.weight, color2) });
			TextureUtils.drawTexture(imageWeight, iconsX, currentY, iconsX + 16, currentY + 16);
			currentY += fieldHeight;
		}

		// value
		float equippedValue = 0;
		if (gameObject1 != null)
			equippedValue = gameObject1.value;
		if (equippedValue != 0 || gameObject2.value != 0) {
			if (equippedValue == gameObject2.value) {
				color1 = Color.WHITE;
				color2 = Color.WHITE;
			} else if (equippedValue < gameObject2.value) {
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
					new Object[] { new StringWithColor("" + gameObject2.value, color2) });
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
