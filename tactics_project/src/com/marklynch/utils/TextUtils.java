package com.marklynch.utils;

import org.newdawn.slick.Color;

import com.marklynch.tactics.objects.level.Level;

public class TextUtils {
	// fit font to width, this could be awkward coz they're loaded w/ font sizes

	public static void printTable(String[][] tableContents, float posX,
			float posY, float rowHeight, Level level) {

		int rowCount = tableContents.length;
		int columnCount = tableContents[0].length;
		int[] columnOffsets = new int[columnCount];

		for (int i = 1; i < columnCount; i++) {
			for (int j = 0; j < rowCount; j++) {
				if (columnOffsets[i] < (tableContents[j][i].length() + columnOffsets[i - 1])) {
					columnOffsets[i] = level.font12
							.getWidth(tableContents[j][i])
							+ columnOffsets[i - 1] + 5;
				}
			}
		}

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {

				float x = columnOffsets[j] + posX;
				float y = i * rowHeight + posY;

				level.font12.drawString(x, y, tableContents[i][j], Color.white);
			}
		}
	}
}
