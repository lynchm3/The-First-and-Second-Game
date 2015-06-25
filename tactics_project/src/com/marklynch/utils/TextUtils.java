package com.marklynch.utils;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Level;

public class TextUtils {
	// fit font to width, this could be awkward coz they're loaded w/ font sizes

	public static void printTable(Object[][] tableContents, float posX,
			float posY, float rowHeight, Level level) {

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
					level.font12.drawString(x, y, (String) tableContents[i][j],
							Color.white);
				} else if (tableContents[i][j] instanceof Texture) {

					Texture texture = (Texture) tableContents[i][j];
					texture.bind();

					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0, 0);
					GL11.glVertex2f(x, y);
					GL11.glTexCoord2f(1, 0);
					GL11.glVertex2f(x + rowHeight, y);
					GL11.glTexCoord2f(1, 1);
					GL11.glVertex2f(x + rowHeight, y + rowHeight);
					GL11.glTexCoord2f(0, 1);
					GL11.glVertex2f(x, y + rowHeight);
					GL11.glEnd();
				}
			}
		}
	}
}
