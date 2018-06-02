package com.marklynch.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.FactionRelationship;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.actions.Action;
import com.marklynch.script.ScriptEvent;
import com.marklynch.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.ui.button.Link;
import com.marklynch.ui.button.Tooltip;

public class TextUtils {

	public final static String splitRegex = "(?<=[\\p{Punct}\\p{Space}|\\p{Space}\\p{Punct}|\\p{Punct}|\\p{Space}])";

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
			if (content instanceof NewLine) {
				offsetY += 20;
				offsetX = 0;
			} else if (content instanceof String || content instanceof StringWithColor || content instanceof Integer
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

					float width = Game.smallFont.getWidth(stringPart);
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

			} else if (content instanceof GameObject) {

				GameObject gameObject = (GameObject) content;

				float textWidth = Game.smallFont.getWidth(gameObject.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += width;

			} else if (content instanceof Faction) {
				Faction faction = (Faction) content;

				float textWidth = Game.smallFont.getWidth(faction.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				if (offsetX + width > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;
				offsetX += textureWidth;

			} else if (content instanceof Area) {
				Area area = (Area) content;

				String string = area.name;
				float textWidth = Game.smallFont.getWidth(string);
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

				float textWidth = Game.smallFont.getWidth(structure.name);
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
				float textWidth = Game.smallFont.getWidth(string);
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

				float textWidth = Game.smallFont.getWidth(scriptEvent.name);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof ScriptTrigger) {

				ScriptTrigger scriptTrigger = (ScriptTrigger) content;

				float textWidth = Game.smallFont.getWidth(scriptTrigger.name);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof Class) {

				Class klass = (Class) content;

				float textWidth = Game.smallFont.getWidth(klass.getSimpleName());
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof AIRoutineUtils) {

				AIRoutineUtils ai = (AIRoutineUtils) content;

				float textWidth = Game.smallFont.getWidth(ai.name);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof Square) {

				Square square = (Square) content;
				String string = square.name;

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				offsetX += textWidth;

			} else if (content instanceof FactionRelationship) {

				FactionRelationship factionRelationship = (FactionRelationship) content;
				String string = "" + factionRelationship.source + " -> " + factionRelationship.target + " ("
						+ factionRelationship.relationship + ")";

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof SpeechPart) {

				SpeechPart speechPart = (SpeechPart) content;
				String string = speechPart.text.get(0);
				if (string.length() > 10) {
					string = string.substring(0, 10) + "...";
				}
				string = "\"" + string + "\"";

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof Action) {

				Action action = (Action) content;
				String string = action.actionName;
				float textureWidth = 20;

				float textWidth = Game.smallFont.getWidth(string);
				offsetX += textWidth;

				// Image
				if (action.image != null) {
					offsetX += textureWidth * 2;
				}

			} else if (content instanceof Effect) {

				Effect effect = (Effect) content;
				String string = effect.effectName;

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof Quest) {

				Quest quest = (Quest) content;
				String string = quest.name;

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += textWidth;

			} else if (content instanceof Objective) {
				Objective objective = (Objective) content;
				float totalWidth = Game.smallFont.getWidth(objective.text) + 20;
				if (offsetX + totalWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				offsetX += totalWidth;

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

	public static ArrayList<Link> getLinks(boolean thisVariableDoesNothing, Object... contents) {
		return getLinks(new ArrayList<Object>(Arrays.asList(contents)));
	}

	public static ArrayList<Link> getLinks(Object[] contents) {
		return getLinks(new ArrayList<Object>(Arrays.asList(contents)));
	}

	public static ArrayList<Link> getLinks(ArrayList<Object> contents) {

		ArrayList<Link> links = new ArrayList<Link>();
		for (Object content : contents) {
			if (content instanceof MapMarker) {
				GameObject gameObject = (GameObject) content;

				float textWidth = Game.smallFont.getWidth(gameObject.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				links.add(new Link(0, 0, width, 20, null, null, "", true, true, Color.WHITE, Color.WHITE, content,
						"Go to marker"));
			} else if (content instanceof GameObject) {
				GameObject gameObject = (GameObject) content;

				float textWidth = Game.smallFont.getWidth(gameObject.name);
				float textureWidth = 20;

				float width = textWidth + textureWidth;
				links.add(new Link(0, 0, width, 20, null, null, "", true, true, Color.WHITE, Color.WHITE, content,
						"Pin details"));
			} else if (content instanceof Quest) {
				Quest quest = (Quest) content;

				float width = Game.smallFont.getWidth(quest.name);

				links.add(new Link(0, 0, width, 20, null, null, "", true, true, Color.WHITE, Color.WHITE, content,
						"View quest"));

			} else if (content instanceof Square) {

				Square square = (Square) content;

				float width = Game.smallFont.getWidth(square.name);

				links.add(new Link(0, 0, width, 20, null, null, "", true, true, Color.WHITE, Color.WHITE, content,
						"Go to square"));

			} else if (content instanceof Objective) {
				Objective objective = (Objective) content;

				links.add(new Link(0, 0, objective.width, 20, null, null, "", true, true, Color.WHITE, Color.WHITE,
						content, "Go to objective"));

			}
		}

		return links;

	}

	public static void printTextWithImages(float posX, float posY, float maxWidth, boolean wrap, ArrayList<Link> links,
			Color defaultColor, Object... contents) {
		printTextWithImages(new ArrayList<Object>(Arrays.asList(contents)), posX, posY, maxWidth, wrap, defaultColor,
				links);
	}

	public static class NewLine {
		public static final NewLine NEW_LINE = new NewLine();
	}

	public static void printTextWithImages(ArrayList<Object> contents, float posX, float posY, float maxWidth,
			boolean wrap, Color defaultColor, ArrayList<Link> links) {

		if (contents == null)
			return;

		int linkIndex = 0;

		float offsetX = 0;
		float offsetY = 0;
		Game.activeBatch.setColor(1, 1, 1, 1);

		for (Object content : contents) {

			if (content instanceof NewLine) {
				offsetY += 20;
				offsetX = 0;
			} else if (content instanceof String || content instanceof StringWithColor || content instanceof Integer
					|| content instanceof Float || content instanceof Boolean || content instanceof Tooltip) {

				String string = null;
				Color color = defaultColor;

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

					float width = Game.smallFont.getWidth(stringPart);
					if (offsetX + width > maxWidth && offsetX != 0) {
						if (wrap) {
							offsetY += 20;
							offsetX = 0;
						} else {
							return;
						}
					}

					Game.smallFont.drawText(Game.activeBatch, stringPart, posX + offsetX, posY + offsetY);

					// Game.font1.drawString(posX + offsetX, posY + offsetY,
					// stringPart, org.newdawn.slick.Color.black);
					// Game.font2.drawString(posX + offsetX, posY + offsetY,
					// stringPart, org.newdawn.slick.Color.green);

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

			} else if (content instanceof GameObject) {

				// float startX = posX + offsetX;

				GameObject gameObject = (GameObject) content;

				float textWidth = Game.smallFont.getWidth(gameObject.name);
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

				if (links != null) {
					Game.activeBatch.setColor(Color.YELLOW);
					links.get(linkIndex).updatePosition(posX + offsetX, posY + offsetY);
					linkIndex++;
				}

				// Text
				Game.smallFont.drawText(Game.activeBatch, gameObject.name, posX + offsetX, posY + offsetY);

				offsetX += textWidth;

				// Image
				float x = posX + offsetX;
				TextureUtils.drawTexture(gameObject.imageTexture, x, posY + offsetY, x + 20, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Faction) {
				Faction faction = (Faction) content;

				float textWidth = Game.smallFont.getWidth(faction.name);
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
				Game.smallFont.drawText(Game.activeBatch, faction.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(faction.imageTexture, x, posY + offsetY, x + 20, posY + offsetY + 20);
				offsetX += textureWidth;

			} else if (content instanceof Area) {
				Area area = (Area) content;

				String string = area.name;
				float textWidth = Game.smallFont.getWidth(string);
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

				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image
				if (area.image != null) {
					float x = posX + offsetX;
					TextureUtils.drawTexture(area.image, x, posY + offsetY, x + 20, posY + offsetY + 20);
					offsetX += textureWidth;
				}

			} else if (content instanceof Structure) {
				Structure structure = (Structure) content;

				float textWidth = Game.smallFont.getWidth(structure.name);
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

				Game.smallFont.drawText(Game.activeBatch, structure.name, posX + offsetX, posY + offsetY);
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
				float textWidth = Game.smallFont.getWidth(string);
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

				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Color) {

				// COlor

				Color color = (Color) content;

				float x = posX + offsetX;
				QuadUtils.drawQuad(color, x, posY + offsetY, x + 20, posY + offsetY + 20);
				offsetX += 20;

			} else if (content instanceof ScriptEvent) {

				ScriptEvent scriptEvent = (ScriptEvent) content;

				float textWidth = Game.smallFont.getWidth(scriptEvent.name);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, scriptEvent.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof ScriptTrigger) {

				ScriptTrigger scriptTrigger = (ScriptTrigger) content;

				float textWidth = Game.smallFont.getWidth(scriptTrigger.name);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, scriptTrigger.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Class) {

				Class klass = (Class) content;

				float textWidth = Game.smallFont.getWidth(klass.getSimpleName());
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, klass.getSimpleName(), posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof AIRoutineUtils) {

				AIRoutineUtils ai = (AIRoutineUtils) content;

				float textWidth = Game.smallFont.getWidth(ai.name);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, ai.name, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Square) {

				Square square = (Square) content;
				String string = square.name;

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				if (links != null) {
					Game.activeBatch.setColor(Color.YELLOW);
					links.get(linkIndex).updatePosition(posX + offsetX, posY + offsetY);
					linkIndex++;
				}

				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof FactionRelationship) {

				FactionRelationship factionRelationship = (FactionRelationship) content;
				String string = "" + factionRelationship.source + " -> " + factionRelationship.target + " ("
						+ factionRelationship.relationship + ")";

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof SpeechPart) {

				SpeechPart speechPart = (SpeechPart) content;
				String string = speechPart.text.get(0);
				if (string.length() > 10) {
					string = string.substring(0, 10) + "...";
				}
				string = "\"" + string + "\"";

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
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

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}
				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

				// Image
				if (action.image != null) {
					float x = posX + offsetX + textureWidth;
					TextureUtils.drawTexture(action.image, x, posY + offsetY, x + 20, posY + offsetY + 20, color);
					offsetX += textureWidth * 2;

					if (!action.enabled) {
						if (action.legal) {
							TextureUtils.drawTexture(Action.textureX, x + 10, posY + offsetY + 10, x + 20,
									posY + offsetY + 20, Color.RED);
						} else {
							TextureUtils.drawTexture(Action.textureX, x + 10, posY + offsetY + 10, x + 20,
									posY + offsetY + 20, Color.WHITE);

						}
					}

				}

			} else if (content instanceof Effect) {

				Effect effect = (Effect) content;
				String string = effect.effectName;

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Quest) {

				Quest quest = (Quest) content;
				String string = quest.name;

				float textWidth = Game.smallFont.getWidth(string);
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				if (links != null) {
					Game.activeBatch.setColor(Color.YELLOW);
					links.get(linkIndex).updatePosition(posX + offsetX, posY + offsetY);
					linkIndex++;
				}
				Game.smallFont.drawText(Game.activeBatch, string, posX + offsetX, posY + offsetY);
				offsetX += textWidth;

			} else if (content instanceof Power) {

				Power power = (Power) content;
				float textWidth = Game.smallFont.getWidth(power.name);
				float textureWidth = 20;
				if (offsetX + textWidth > maxWidth && offsetX != 0) {
					offsetY += 20;
					offsetX = 0;
				}

				if (links != null) {
					Game.activeBatch.setColor(Color.YELLOW);
					links.get(linkIndex).updatePosition(posX + offsetX, posY + offsetY);
					linkIndex++;
				}

				// Name
				Game.smallFont.drawText(Game.activeBatch, power.name, posX + offsetX, posY + offsetY);

				offsetX += Game.smallFont.getWidth(power.name) + 10;

				// Image
				if (power.image != null) {
					float x = posX + offsetX;
					TextureUtils.drawTexture(power.image, x, posY + offsetY, x + 20, posY + offsetY + 20);
					offsetX += textureWidth;
				}

				// Newline
				offsetY += 20;
				offsetX = 0;

				// Description

				String[] stringParts = power.description.split(splitRegex);

				for (String stringPart : stringParts) {

					float width = Game.smallFont.getWidth(stringPart);
					if (offsetX + width > maxWidth && offsetX != 0) {
						if (wrap) {
							offsetY += 20;
							offsetX = 0;
						} else {
							return;
						}
					}

					Game.smallFont.drawText(Game.activeBatch, stringPart, posX + offsetX, posY + offsetY);

					offsetX += width;

				}

				if (power.range != 0) {

					// Newline
					offsetY += 20;
					offsetX = 0;

					// Range
					Game.smallFont.drawText(Game.activeBatch, "RANGE " + power.range, posX + offsetX, posY + offsetY);
				}

				// Newline
				offsetY += 20;
				offsetX = 0;

				// AOE
				if (power.areaOfEffect != null && power.areaOfEffect.length > 0) {
					// Game.smallFont.drawText(Game.activeBatch, "AOE", posX + offsetX, posY +
					// offsetY);
					float lowestX = 0f;
					float lowestY = 0f;
					float highestX = 0f;
					float highestY = 0f;
					float width = 0f;
					float height = 0f;

					for (Point point : power.areaOfEffect) {
						if (point.getX() < lowestX)
							lowestX = point.getX();

						if (point.getY() < lowestY)
							lowestY = point.getY();

						if (point.getX() > highestX)
							highestX = point.getX();

						if (point.getY() > highestY)
							highestY = point.getY();
					}

					width = highestX - lowestX + 1f;
					height = highestY - lowestY + 1f;

					for (Point point : power.areaOfEffect) {

						float x = posX + offsetX + (point.getX() - lowestX) * 20f;
						float y = posY + offsetY + (point.getY() - lowestY) * 20f;
						TextureUtils.drawTexture(Square.WHITE_SQUARE, x, y, x + 20f, y + 20f);

					}

					// NEW LINE(s)
					offsetY += height * 20d;
					offsetX = 0;

				}

				if (power.passive) {
					Game.smallFont.drawText(Game.activeBatch, "PASSIVE", posX + offsetX, posY + offsetY);
				} else {
					Game.smallFont.drawText(Game.activeBatch, "ACTIVE", posX + offsetX, posY + offsetY);
				}

				// Newline
				offsetY += 20;
				offsetX = 0;

				if (power.crimeSeverity == Crime.TYPE.NONE) {
					Game.smallFont.drawText(Game.activeBatch, "LEGAL", posX + offsetX, posY + offsetY);

				} else {
					Game.smallFont.drawText(Game.activeBatch, "ILLEGAL", posX + offsetX, posY + offsetY);
				}

				// Newline
				offsetY += 20;
				offsetX = 0;
				Game.smallFont.drawText(Game.activeBatch, "NOISE " + power.loudness, posX + offsetX, posY + offsetY);

			}

		}
		Game.activeBatch.setColor(1, 1, 1, 1);

	}

	public void drawAOEForTextUtils(int posX, int posY, int offsetX, int offsetY, Power power) {

	}

	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null)
			return false;

		final int length = searchStr.length();
		if (length == 0)
			return true;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return true;
		}
		return false;
	}
}
