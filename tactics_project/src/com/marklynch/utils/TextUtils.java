package com.marklynch.utils;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Decoration;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.FactionRelationship;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObjectTemplate;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.script.ScriptEvent;
import com.marklynch.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.ui.button.Tooltip;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class TextUtils {

	public final static String splitRegex = "(?<=[\\p{Punct}\\p{Space}|\\p{Space}\\p{Punct}|\\p{Punct}|\\p{Space}])";

	public static void printTextWithImages(float posX, float posY, float maxWidth, boolean wrap, boolean link,
			ArrayList<LevelButton> arrayToAddLinksTo, Object... contents) {
		printTextWithImages(new ArrayList(Arrays.asList(contents)), posX, posY, maxWidth, wrap, link,
				arrayToAddLinksTo);
	}

	public static float[] getDimensions(float maxWidth, Object... contents) {
		return getDimensions(new ArrayList<Object>(Arrays.asList(contents)), maxWidth);
	}

	public static float[] getDimensions(Object[] contents, float maxWidth) {
		return getDimensions(new ArrayList<Object>(Arrays.asList(contents)), maxWidth);
	}

	public static float[] getDimensions(ArrayList<Object> contents, float maxWidth) {

		float[] dimensions = new float[2];
		float offsetX = 0;
		float offsetY = 0;

		for (Object content : contents) {
			if (content instanceof String || content instanceof StringWithColor || content instanceof Integer
					|| content instanceof Float || content instanceof Boolean || content instanceof Tooltip) {

				String string = null;

				if (content instanceof Integer || content instanceof Float || content instanceof Boolean) {
					string = "" + content;
				} else if (content instanceof String) {
					string = (String) content;
				} else if (content instanceof StringWithColor) {
					StringWithColor stringWithColor = (StringWithColor) content;
					string = stringWithColor.string;
				} else if (content instanceof Tooltip) {
					Tooltip tooltip = (Tooltip) content;
					string = tooltip.textWithColor.string;
				}

				String[] stringParts = string.split(splitRegex);

				for (String stringPart : stringParts) {

					float width = Game.font.getWidth(stringPart);
					if (offsetX + width > maxWidth && offsetX != 0) {
						offsetY += 20;
						offsetX = 0;
					}

					offsetX += width;

				}
			} else if (content instanceof Texture) {

				float width = 20;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += width;

			} else if (content instanceof GameObjectTemplate) {

				GameObjectTemplate gameObject = (GameObjectTemplate) content;

				float textWidth = Game.font.getWidth(gameObject.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;
				offsetX += textureWidth;

			} else if (content instanceof Faction) {
				Faction faction = (Faction) content;

				float textWidth = Game.font.getWidth(faction.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;
				offsetX += textureWidth;

			} else if (content instanceof Decoration) {
				Decoration decoration = (Decoration) content;

				float textWidth = Game.font.getWidth(decoration.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				// Name
				offsetX += textWidth;
				offsetX += textureWidth;

			} else if (content instanceof Area) {
				Area area = (Area) content;

				String string = area.name;
				float textWidth = Game.font.getWidth(string);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;

				// Image
				if (area.image != null) {
					offsetX += textureWidth;
				}

			} else if (content instanceof Structure) {
				Structure structure = (Structure) content;

				float textWidth = Game.font.getWidth(structure.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;
				if (structure.image != null) {
					offsetX += textureWidth;
				}

			} else if (content instanceof StructureRoom) {
				StructureRoom structureRoom = (StructureRoom) content;

				String string = structureRoom.name + " (" + structureRoom.structure.name + ")";
				float textWidth = Game.font.getWidth(string);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;

			} else if (content instanceof Color) {
				offsetX += 20;
			} else if (content instanceof ScriptEvent) {

				ScriptEvent scriptEvent = (ScriptEvent) content;

				float textWidth = Game.font.getWidth(scriptEvent.name);
				offsetX += textWidth;

			} else if (content instanceof ScriptTrigger) {

				ScriptTrigger scriptTrigger = (ScriptTrigger) content;

				float textWidth = Game.font.getWidth(scriptTrigger.name);
				offsetX += textWidth;

			} else if (content instanceof Class) {

				Class klass = (Class) content;

				float textWidth = Game.font.getWidth(klass.getSimpleName());
				offsetX += textWidth;

			} else if (content instanceof AIRoutineUtils) {

				AIRoutineUtils ai = (AIRoutineUtils) content;

				float textWidth = Game.font.getWidth(ai.name);
				offsetX += textWidth;

			} else if (content instanceof Square) {

				Square square = (Square) content;
				String string = "Square @ " + square.xInGrid + " , " + square.yInGrid;

				float textWidth = Game.font.getWidth(string);
				offsetX += textWidth;

			} else if (content instanceof FactionRelationship) {

				FactionRelationship factionRelationship = (FactionRelationship) content;
				String string = "" + factionRelationship.source + " -> " + factionRelationship.target + " ("
						+ factionRelationship.relationship + ")";

				float textWidth = Game.font.getWidth(string);
				offsetX += textWidth;

			} else if (content instanceof SpeechPart) {

				SpeechPart speechPart = (SpeechPart) content;
				String string = speechPart.text.get(0);
				if (string.length() > 10) {
					string = string.substring(0, 10) + "...";
				}
				string = "\"" + string + "\"";

				float textWidth = Game.font.getWidth(string);
				offsetX += textWidth;

			} else if (content instanceof Action) {

				Action action = (Action) content;
				String string = action.actionName;
				float textureWidth = 20;

				float textWidth = Game.font.getWidth(string);
				offsetX += textWidth;

				// Image
				if (action.image != null) {
					offsetX += textureWidth * 2;
				}

			} else if (content instanceof Projectile) {

				Projectile projectile = (Projectile) content;
				String string = projectile.name;

				float textWidth = Game.font.getWidth(string);
				offsetX += textWidth;

			} else if (content instanceof Effect) {

				Effect effect = (Effect) content;
				String string = effect.effectName;

				float textWidth = Game.font.getWidth(string);
				offsetX += textWidth;

			}
		}

		dimensions[1] = offsetY + 20;
		if (dimensions[1] > 20) {
			dimensions[0] = maxWidth;
		} else {
			dimensions[0] = offsetX;
		}

		return dimensions;

	}

	public static void printTextWithImages(ArrayList contents, float posX, float posY, float maxWidth, boolean wrap,
			boolean link, ArrayList<LevelButton> arrayToAddLinksTo) {

		if (contents == null)
			return;

		float offsetX = 0;
		float offsetY = 0;
		Game.activeBatch.setColor(Color.WHITE);

		for (Object content : contents) {
			if (content instanceof String || content instanceof StringWithColor || content instanceof Integer
					|| content instanceof Float || content instanceof Boolean || content instanceof Tooltip) {

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
				} else if (content instanceof Tooltip) {
					Tooltip tooltip = (Tooltip) content;
					string = tooltip.textWithColor.string;
					color = tooltip.textWithColor.color;
				}
				Game.activeBatch.setColor(color);

				String[] stringParts = string.split(splitRegex);

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
				TextureUtils.drawTexture((Texture) content, posX + offsetX, posY + offsetY, posX + offsetX + 20,
						posY + offsetY + 20);
				offsetX += width;

			} else if (content instanceof GameObjectTemplate) {

				float startX = posX + offsetX;

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

				if (link)
					Game.activeBatch.setColor(Color.YELLOW);

				Game.font.drawText(Game.activeBatch, gameObject.name, posX + offsetX, posY + offsetY);

				offsetX += textWidth;

				// Image
				float x = posX + offsetX;
				TextureUtils.drawTexture(gameObject.imageTexture, x, posY + offsetY, x + 20, posY + offsetY + 20);
				offsetX += textureWidth;

				float endX = posX + offsetX;

				if (link) {
					arrayToAddLinksTo.add(new Link(startX, posY + offsetY, endX - startX, 20, null, null, "", true,
							true, Color.WHITE, Color.WHITE, gameObject));
				}

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
				TextureUtils.drawTexture(faction.imageTexture, x, posY + offsetY, x + 20, posY + offsetY + 20);
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
				TextureUtils.drawTexture(decoration.imageTexture, x, posY + offsetY, x + 20, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Area) {
				Area area = (Area) content;

				String string = area.name;
				float textWidth = Game.font.getWidth(string);
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

				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image
				if (area.image != null) {
					float x = posX + offsetX;
					TextureUtils.drawTexture(area.image, x, posY + offsetY, x + 20, posY + offsetY + 20);
					offsetX += textureWidth;
				}

			} else if (content instanceof Structure) {
				Structure structure = (Structure) content;

				float textWidth = Game.font.getWidth(structure.name);
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

				Game.font.drawText(Game.activeBatch, structure.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image
				if (structure.image != null) {
					float x = posX + offsetX;
					TextureUtils.drawTexture(structure.image, x, posY + offsetY, x + 20, posY + offsetY + 20);
					offsetX += textureWidth;
				}

			} else if (content instanceof StructureRoom) {
				StructureRoom structureRoom = (StructureRoom) content;

				String string = structureRoom.name + " (" + structureRoom.structure.name + ")";
				float textWidth = Game.font.getWidth(string);
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

				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

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
				float textureWidth = 20;

				Color color = Color.WHITE;
				if (!action.legal) {
					color = color.RED;
				}

				Game.activeBatch.setColor(color);

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image
				if (action.image != null) {
					float x = posX + offsetX + textureWidth;
					TextureUtils.drawTexture(action.image, x, posY + offsetY, x + 20, posY + offsetY + 20, color);
					offsetX += textureWidth * 2;

					if (!action.enabled) {
						if (action.legal) {
							TextureUtils.drawTexture(Action.x, x + 10, posY + offsetY + 10, x + 20, posY + offsetY + 20,
									Color.RED);
						} else {
							TextureUtils.drawTexture(Action.x, x + 10, posY + offsetY + 10, x + 20, posY + offsetY + 20,
									Color.WHITE);

						}
					}

				}

			} else if (content instanceof Projectile) {

				Projectile projectile = (Projectile) content;
				String string = projectile.name;

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Effect) {

				Effect effect = (Effect) content;
				String string = effect.effectName;

				float textWidth = Game.font.getWidth(string);
				Game.font.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			}

		}

	}
}
