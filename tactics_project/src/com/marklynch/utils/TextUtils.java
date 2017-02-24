package com.marklynch.utils;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Decoration;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.FactionRelationship;
import com.marklynch.level.constructs.building.Building;
import com.marklynch.objects.GameObjectTemplate;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.script.ScriptEvent;
import com.marklynch.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.script.trigger.ScriptTrigger;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class TextUtils {

	public static void printTextWithImages(Object[] contents, float posX, float posY, float maxWidth, boolean wrap) {
		printTextWithImages(new ArrayList(Arrays.asList(contents)), posX, posY, maxWidth, wrap);
	}

	public static void printTextWithImages(ArrayList contents, float posX, float posY, float maxWidth, boolean wrap) {

		if (contents == null)
			return;

		float offsetX = 0;
		float offsetY = 0;
		Game.activeBatch.setColor(Color.WHITE);

		for (Object content : contents) {
			if (content instanceof String || content instanceof StringWithColor || content instanceof Integer
					|| content instanceof Float || content instanceof Boolean) {

				String string = null;
				Color color = Color.WHITE;

				if (content instanceof Integer || content instanceof Float || content instanceof Boolean) {
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
						if (wrap) {
							offsetY += 20;
							offsetX = 0;
						} else {
							return;
						}
					}

					// GameObject.font.
					Game.font.drawText(Game.activeBatch, stringPart, posX + offsetX, posY + offsetY);

					offsetX += width;

				}
			} else if (content instanceof Texture) {

				float width = 20;
				if (offsetX + width > maxWidth && offsetX != 0) {
					if (wrap) {
						offsetY += 20;
						offsetX = 0;
					} else {
						return;
					}
				}
				TextureUtils.drawTexture((Texture) content, posX + offsetX, posX + offsetX + 20, posY + offsetY,
						posY + offsetY + 20);
				offsetX += width;

			} else if (content instanceof GameObjectTemplate) {

				GameObjectTemplate gameObject = (GameObjectTemplate) content;

				float textWidth = Game.font.getWidth(gameObject.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					if (wrap) {
						offsetY += 20;
						offsetX = 0;
					} else {
						return;
					}
				}

				// Name
				// Game.activeBatch.setColor(Color.BLACK);
				if (gameObject instanceof Actor) {
					Actor actor = (Actor) gameObject;
					// Game.activeBatch.setColor(actor.faction.color);
					Game.font.drawText(Game.activeBatch, gameObject.name, posX + offsetX, posY + offsetY);
				} else {
					Game.font.drawText(Game.activeBatch, gameObject.name, posX + offsetX, posY + offsetY);
				}
				offsetX += textWidth;

				// Image
				float x = posX + offsetX;
				TextureUtils.drawTexture(gameObject.imageTexture, x, x + 20, posY + offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

				// } else if (content instanceof Weapon || content instanceof
				// WeaponTemplate) {
				//
				// WeaponTemplate weaponTemplate = (WeaponTemplate) content;
				//
				// float textWidth = Game.font.getWidth(weaponTemplate.name);
				// float textureWidth = 20;
				//
				// float width = textWidth + textureWidth;
				// if (offsetX + width > maxWidth && offsetX != 0) {
				// if (wrap) {
				// offsetY += 20;
				// offsetX = 0;
				// } else {
				// return;
				// }
				// }
				//
				// // Name
				// Game.font.drawText(Game.activeBatch, weaponTemplate.name,
				// posX + offsetX, posY + offsetY);
				// offsetX += textWidth;
				//
				// // Image
				// float x = posX + offsetX;
				// TextureUtils.drawTexture(weaponTemplate.imageTexture, x, x +
				// 20, posY + offsetY, posY + offsetY + 20);
				// offsetX += textureWidth;
				//
				// // } else if (content instanceof Weapons) {
				// //
				// // Weapons weapons = (Weapons) content;
				// //
				// // for (Weapon weapon : weapons.weapons) {
				// // // Image
				// // float x = posX + offsetX;
				// // TextureUtils.drawTexture(weapon.imageTexture, x, x + 20,
				// posY
				// // + offsetY, posY + offsetY + 20);
				// // offsetX += 20;
				// // }

			} else if (content instanceof Faction) {
				Faction faction = (Faction) content;

				float textWidth = Game.font.getWidth(faction.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					if (wrap) {
						offsetY += 20;
						offsetX = 0;
					} else {
						return;
					}
				}

				// Name

				// Game.activeBatch.setColor(faction.color);
				Game.font.drawText(Game.activeBatch, faction.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(faction.imageTexture, x, x + 20, posY + offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Decoration) {
				Decoration decoration = (Decoration) content;

				float textWidth = Game.font.getWidth(decoration.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					if (wrap) {
						offsetY += 20;
						offsetX = 0;
					} else {
						return;
					}
				}

				// Name

				Game.font.drawText(Game.activeBatch, decoration.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(decoration.imageTexture, x, x + 20, posY + offsetY, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Building) {
				Building building = (Building) content;

				float textWidth = Game.font.getWidth(building.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					if (wrap) {
						offsetY += 20;
						offsetX = 0;
					} else {
						return;
					}
				}

				// Name

				Game.font.drawText(Game.activeBatch, building.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image

				// float x = posX + offsetX;
				// TextureUtils.drawTexture(building.imageTexture, x, x + 20,
				// posY + offsetY, posY + offsetY + 20);
				// offsetX += textureWidth;

			} else if (content instanceof Color) {

				// COlor

				Color color = (Color) content;

				float x = posX + offsetX;
				QuadUtils.drawQuad(color, x, x + 20, posY + offsetY, posY + offsetY + 20);
				offsetX += 20;

			} else if (content instanceof ScriptEvent) {

				ScriptEvent scriptEvent = (ScriptEvent) content;

				float textWidth = Game.font.getWidth(scriptEvent.name);
				Game.font.drawText(Game.activeBatch, scriptEvent.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof ScriptTrigger) {

				ScriptTrigger scriptTrigger = (ScriptTrigger) content;

				float textWidth = Game.font.getWidth(scriptTrigger.name);
				Game.font.drawText(Game.activeBatch, scriptTrigger.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Class) {

				Class klass = (Class) content;

				float textWidth = Game.font.getWidth(klass.getSimpleName());
				Game.font.drawText(Game.activeBatch, klass.getSimpleName(), posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof AIRoutineUtils) {

				AIRoutineUtils ai = (AIRoutineUtils) content;

				float textWidth = Game.font.getWidth(ai.name);
				Game.font.drawText(Game.activeBatch, ai.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Square) {

				Square square = (Square) content;
				String string = "Square @ " + square.xInGrid + " , " + square.yInGrid;

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof FactionRelationship) {

				FactionRelationship factionRelationship = (FactionRelationship) content;
				String string = "" + factionRelationship.source + " -> " + factionRelationship.target + " ("
						+ factionRelationship.relationship + ")";

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof SpeechPart) {

				SpeechPart speechPart = (SpeechPart) content;
				String string = speechPart.text.get(0);
				if (string.length() > 10) {
					string = string.substring(0, 10) + "...";
				}
				string = "\"" + string + "\"";

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Action) {

				Action action = (Action) content;
				String string = action.actionName;

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			}

		}

	}
}
