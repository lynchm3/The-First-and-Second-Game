package com.marklynch.utils;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;

public class TextUtils {
	// fit font to width, this could be awkward coz they're loaded w/ font sizes

	public static void printTable(Object[][] tableContents, float posX,
			float posY, float rowHeight, Level level) {

		QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), posX, posX
				+ Game.SQUARE_WIDTH, posY, posY + Game.SQUARE_HEIGHT);

		int rowCount = tableContents.length;
		int columnCount = tableContents[0].length;
		float[] columnOffsets = new float[columnCount];

		for (int i = 1; i < columnCount; i++) {
			// Init column width to be at least
			// row height
		}

		for (int i = 1; i < columnCount; i++) {
			columnOffsets[i] = rowHeight + columnOffsets[i - 1] + 5;
			for (int j = 0; j < rowCount; j++) {
				if (tableContents[j][i] instanceof String) {
					int stringWidth = level.font12
							.getWidth((String) tableContents[j][i]);
					if (columnOffsets[i] < stringWidth + columnOffsets[i - 1]
							+ 5) {
						columnOffsets[i] = stringWidth + columnOffsets[i - 1]
								+ 5;
					}
				}
			}
		}

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {

				float x = columnOffsets[j] + posX;
				float y = i * rowHeight + posY;
				if (tableContents[i][j] instanceof String) {
					level.font20.drawString(x, y, (String) tableContents[i][j],
							Color.white);
				} else if (tableContents[i][j] instanceof Texture) {

					TextureUtils.drawTexture((Texture) tableContents[i][j], x,
							x + rowHeight, y, y + rowHeight);
				}
			}
		}
	}

	public static void printTextWithImages(Object[] contents, float posX,
			float posY, Level level) {

		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		//
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		// GL11.glBegin(GL11.GL_QUADS);
		// // GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		// // GL11.glColor4f(this.faction.color.r, this.faction.color.g,
		// // this.faction.color.b, 0.5f);
		// GL11.glVertex2f(posX, posY);
		// GL11.glVertex2f(posX + 500, posY);
		// GL11.glVertex2f(posX + 500, posY + 20);
		// GL11.glVertex2f(posX, posY + 20);
		// GL11.glEnd();
		// GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		int offsetX = 0;

		for (Object content : contents) {
			if (content instanceof String) {
				String string = (String) content;
				level.font20.drawString(posX + offsetX, posY, string,
						Color.white);
				offsetX += level.font20.getWidth(string);

			} else if (content instanceof Texture) {

				float x = posX + offsetX;
				TextureUtils.drawTexture((Texture) content, x, x + 20, posY,
						posY + 20);

			} else if (content instanceof GameObject) {

				GameObject gameObject = (GameObject) content;

				// Name
				if (gameObject instanceof Actor) {
					Actor actor = (Actor) gameObject;

					level.font20.drawString(posX + offsetX, posY,
							gameObject.name, actor.faction.color);
					;
				} else {
					level.font20.drawString(posX + offsetX, posY,
							gameObject.name, Color.gray);
				}
				offsetX += level.font20.getWidth(gameObject.name);

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(gameObject.imageTexture, x, x + 20,
						posY, posY + 20);
				offsetX += 20;

			} else if (content instanceof Faction) {

				Faction faction = (Faction) content;

				// Name

				level.font20.drawString(posX + offsetX, posY, faction.name,
						faction.color);
				offsetX += level.font20.getWidth(faction.name);

				// Image??

				// Image

				float x = posX + offsetX;
				TextureUtils.drawTexture(faction.imageTexture, x, x + 20, posY,
						posY + 20);
				offsetX += 20;

			} else if (content instanceof StringWithColor) {

			}
		}

	}
}
