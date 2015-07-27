package com.marklynch.utils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Decoration;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.tactics.objects.weapons.Weapons;

public class TextUtils {

	public static void printTextWithImages(Object[] contents, float posX,
			float posY) {
		printTextWithImages(contents, posX, posY, Float.MAX_VALUE);
	}

	public static void printTextWithImages(Object[] contents, float posX,
			float posY, float maxWidth) {

		float offsetX = 0;
		float offsetY = 0;
		Game.activeBatch.setColor(Color.WHITE);

		for (Object content : contents) {
			if (content instanceof String || content instanceof StringWithColor
					|| content instanceof Integer || content instanceof Float
					|| content instanceof Boolean) {

				String string = null;
				Color color = Color.WHITE;

				if (content instanceof Integer || content instanceof Float
						|| content instanceof Boolean) {
					string = "" + content;
				} else if (content instanceof String) {
					string = (String) content;
				} else if (content instanceof StringWithColor) {
					StringWithColor stringWithColor = (StringWithColor) content;
					string = stringWithColor.string;
					color = stringWithColor.color;
				}
				Game.activeBatch.setColor(color);

				String[] stringParts = string
						.split("(?<=[\\p{Punct}\\p{Space}|\\p{Space}\\p{Punct}|\\p{Punct}|\\p{Space}])");

				for (String stringPart : stringParts) {

					float width = Game.font.getWidth(stringPart);
					if (offsetX + width > maxWidth && offsetX != 0) {
						offsetY += 20;
						offsetX = 0;
					}

					// GameObject.font.
					Game.font.drawText(Game.activeBatch, stringPart, posX
							+ offsetX, posY + offsetY);

					offsetX += width;

				}
			} else if (content instanceof Texture) {

				float width = 20;
				if (offsetX + width > maxWidth) {
					offsetY += 20;
					offsetX = 0;
				}
				TextureUtils.drawTexture((Texture) content, posX + offsetX,
						posX + offsetX + 20, posY + offsetY, posY + offsetY
								+ 20);
				offsetX += width;

			} else if (content instanceof GameObject) {

				GameObject gameObject = (GameObject) content;

				float textWidth = Game.font.getWidth(gameObject.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth) {
					offsetY += 20;
					offsetX = 0;
				}

				// Name
				Game.activeBatch.setColor(Color.GRAY);
				if (gameObject instanceof Actor) {
					Actor actor = (Actor) gameObject;
					Game.activeBatch.setColor(actor.faction.color);
					Game.font.drawText(Game.activeBatch, gameObject.name,
							posX + offsetX, posY + offsetY);
				} else {
					Game.font.drawText(Game.activeBatch, gameObject.name,
							posX + offsetX, posY + offsetY);
				}
				offsetX += textWidth;

				// Image
				float x = posX + offsetX;
				TextureUtils.drawTexture(gameObject.imageTexture, x, x + 20,
						posY + offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Weapon) {

				Weapon weapon = (Weapon) content;

				float textWidth = Game.font.getWidth(weapon.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth) {
					offsetY += 20;
					offsetX = 0;
				}

				// Name
				Game.font.drawText(Game.activeBatch, weapon.name, posX
						+ offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image
				float x = posX + offsetX;
				TextureUtils.drawTexture(weapon.imageTexture, x, x + 20, posY
						+ offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Weapons) {

				Weapons weapons = (Weapons) content;

				for (Weapon weapon : weapons.weapons) {
					// Image
					float x = posX + offsetX;
					TextureUtils.drawTexture(weapon.imageTexture, x, x + 20,
							posY + offsetY, posY + offsetY + 20);
					offsetX += 20;
				}

			} else if (content instanceof Faction) {
				Faction faction = (Faction) content;

				float textWidth = Game.font.getWidth(faction.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth) {
					offsetY += 20;
					offsetX = 0;
				}

				// Name

				Game.activeBatch.setColor(faction.color);
				Game.font.drawText(Game.activeBatch, faction.name, posX
						+ offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(faction.imageTexture, x, x + 20, posY
						+ offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Decoration) {
				Decoration decoration = (Decoration) content;

				float textWidth = Game.font.getWidth(decoration.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth) {
					offsetY += 20;
					offsetX = 0;
				}

				// Name

				Game.font.drawText(Game.activeBatch, decoration.name,
						posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(decoration.imageTexture, x, x + 20,
						posY + offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Color) {

				// COlor

				Color color = (Color) content;

				float x = posX + offsetX;
				QuadUtils.drawQuad(color, x, x + 20, posY + offsetY, posY
						+ offsetY + 20);
				offsetX += 20;

			}
		}

	}
}
