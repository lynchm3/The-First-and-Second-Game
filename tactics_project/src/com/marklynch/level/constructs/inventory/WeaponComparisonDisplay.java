package com.marklynch.level.constructs.inventory;

import com.marklynch.Game;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class WeaponComparisonDisplay {

	Weapon weapon;

	int x = 500;
	int y = 500;
	int width = 500;
	int height = 300;
	int halfWidth = width / 2;

	int weaponWidth = 128;
	int weaponHeight = 128;
	int weapon1DrawX = x;
	int weaponDrawY = y;
	int weapon2DrawX = x + width - weaponWidth;

	int stats1RightX = x + halfWidth - 4;
	int stats2X = x + halfWidth + 4;

	int nameY = y;
	int slashDamageY = y + 16;
	int bluntDamageY = y + 32;
	int pierceDamageY = y + 48;
	int fireDamageY = y + 64;
	int waterDamageY = y + 80;
	int electricalDamageY = y + 96;
	int poisonDamageY = y + 112;
	int rangeY = y + 128;

	public void drawStaticUI() {

		if (Game.level.player.inventory.inventorySquareMouseIsOver == null)
			return;

		if (Game.level.player.inventory.inventorySquareMouseIsOver.gameObject == null)
			return;

		if (!(Game.level.player.inventory.inventorySquareMouseIsOver.gameObject instanceof Weapon))
			return;

		if (Game.level.player.equipped == null)
			return;

		if (!(Game.level.player.equipped instanceof Weapon))
			return;

		Weapon weapon1 = (Weapon) Game.level.player.equipped;
		Weapon weapon2 = (Weapon) Game.level.player.inventory.inventorySquareMouseIsOver.gameObject;

		// Square
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon1DrawX, weaponDrawY, weapon1DrawX + weaponWidth,
				weaponDrawY + weaponHeight);
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, weapon2DrawX, weaponDrawY, weapon2DrawX + weaponWidth,
				weaponDrawY + weaponHeight);

		// Weapon images
		TextureUtils.drawTexture(weapon1.imageTexture, weapon1DrawX, weaponDrawY, weapon1DrawX + weaponWidth,
				weaponDrawY + weaponHeight);
		TextureUtils.drawTexture(weapon2.imageTexture, weapon2DrawX, weaponDrawY, weapon2DrawX + weaponWidth,
				weaponDrawY + weaponHeight);

		Color color1 = Color.WHITE;
		Color color2 = Color.WHITE;

		// Name
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.name), nameY, Integer.MAX_VALUE,
				false, new Object[] { new StringWithColor("" + weapon1.name, color1) });
		TextUtils.printTextWithImages(stats2X, nameY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.name, color2) });

		// Slash Damage
		if (weapon1.slashDamage == weapon2.slashDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.slashDamage < weapon2.slashDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.slashDamage), slashDamageY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.slashDamage, color1) });
		TextUtils.printTextWithImages(stats2X, slashDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.slashDamage, color2) });

		// Blunt Damage
		if (weapon1.bluntDamage == weapon2.bluntDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.bluntDamage < weapon2.bluntDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.bluntDamage), bluntDamageY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.bluntDamage, color1) });
		TextUtils.printTextWithImages(stats2X, bluntDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.bluntDamage, color2) });

		// Pierce Damage
		if (weapon1.pierceDamage == weapon2.pierceDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.pierceDamage < weapon2.pierceDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.pierceDamage), pierceDamageY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.pierceDamage, color1) });
		TextUtils.printTextWithImages(stats2X, pierceDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.pierceDamage, color2) });

		// Fire Damage
		if (weapon1.fireDamage == weapon2.fireDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.fireDamage < weapon2.fireDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.fireDamage), fireDamageY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.fireDamage, color1) });
		TextUtils.printTextWithImages(stats2X, fireDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.fireDamage, color2) });

		// Water Damage
		if (weapon1.waterDamage == weapon2.waterDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.waterDamage < weapon2.waterDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.waterDamage), waterDamageY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.waterDamage, color1) });
		TextUtils.printTextWithImages(stats2X, waterDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.waterDamage, color2) });

		// Electrical Damage
		if (weapon1.electricalDamage == weapon2.electricalDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.electricalDamage < weapon2.electricalDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.electricalDamage),
				electricalDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon1.electricalDamage, color1) });
		TextUtils.printTextWithImages(stats2X, electricalDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.electricalDamage, color2) });

		// Posion Damage
		if (weapon1.poisonDamage == weapon2.poisonDamage) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.poisonDamage < weapon2.poisonDamage) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.poisonDamage), poisonDamageY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.poisonDamage, color1) });
		TextUtils.printTextWithImages(stats2X, poisonDamageY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.poisonDamage, color2) });

		// Range
		if (weapon1.maxRange == weapon2.maxRange) {
			color1 = Color.WHITE;
			color2 = Color.WHITE;
		} else if (weapon1.maxRange < weapon2.maxRange) {
			color1 = Color.RED;
			color2 = Color.GREEN;
		} else {
			color1 = Color.GREEN;
			color2 = Color.RED;
		}
		TextUtils.printTextWithImages(stats1RightX - Game.font.getWidth("" + weapon1.maxRange), rangeY,
				Integer.MAX_VALUE, false, new Object[] { new StringWithColor("" + weapon1.maxRange, color1) });
		TextUtils.printTextWithImages(stats2X, rangeY, Integer.MAX_VALUE, false,
				new Object[] { new StringWithColor("" + weapon2.maxRange, color2) });

	}

}
