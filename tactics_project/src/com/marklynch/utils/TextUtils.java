package com.marklynch.utils;


public class TextUtils {
	// fit font to width, this could be awkward coz they're loaded w/ font sizes

	public static void printTable(String[][] tableContents, float posX,
			float posY, float rowHeight, Level level) {
		// Get the max size column to work out the offset for the next one and
		// so forth...

		int rowCount = tableContents.length;
		int columnCount = tableContents[0].length;
		int[] columnOffsets = new int[columnCount];

		for (int i = 1; i < columnCount; i++) {
			for (int j = 0; j < rowCount; j++) {
				if (columnOffsets[i] < (tableContents[j][i].length() + columnOffsets[i - 1])) {
					columnOffsets[i] = tableContents[j][i].length()
							+ columnOffsets[i - 1];
				}
			}
		}

		// for (int i = 0; i < rowCount; i++) {
		// for (int j = 0; j < columnCount; j++) {
		// level.font12.drawString(hoverFightPreviewPositionXInPixels,
		// posX + + i * 15,
		// hoverFightPreviewFights.get(i).range + " " + " " + " "
		// + " " + " VS " + " " + " " + " ", Color.white);
		// }
		// }
	}
}
