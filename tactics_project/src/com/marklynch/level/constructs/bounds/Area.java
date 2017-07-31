package com.marklynch.level.constructs.bounds;

import com.marklynch.Game;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Area {

	public String name;
	public Texture image;
	public int gridX1, gridY1, gridX2, gridY2;
	public boolean seenByPlayer = false;
	boolean showOnMap = false;

	public Area(String name, String imageString, Texture squareTexture, int gridX1, int gridY1, int gridX2,
			int gridY2) {
		super();
		this.name = name;
		if (imageString != null)
			this.image = ResourceUtils.getGlobalImage(imageString);
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;

		for (int i = gridX1; i <= gridX2; i++) {
			for (int j = gridY1; j <= gridY2; j++) {
				Game.level.squares[i][j].areaSquareIsIn = this;
				Game.level.squares[i][j].imageTexture = squareTexture;
			}
		}
	}

	public void hasBeenSeenByPlayer() {
		this.seenByPlayer = true;
		this.showOnMap = true;
		new ActionSpot(Game.level.player, this).perform();
	}

	public void drawUI() {

		if (!showOnMap)
			return;

		// 40sqrs is ideal

		if (this.image == null)
			return;

		int squarePositionX1 = (gridX1 + ((gridX2 - gridX1 - 40) / 2)) * (int) Game.SQUARE_WIDTH;
		int squarePositionY1 = (gridY1 + ((gridY2 - gridY1 - 40) / 2)) * (int) Game.SQUARE_HEIGHT;
		int squarePositionX2 = (40 + gridX1 + ((gridX2 - gridX1 - 40) / 2)) * (int) Game.SQUARE_WIDTH;
		int squarePositionY2 = (40 + gridY1 + ((gridY2 - gridY1 - 40) / 2)) * (int) Game.SQUARE_HEIGHT;
		float drawPositionX1 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX1 - Game.windowWidth / 2 + Game.dragX));
		float drawPositionY1 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY1 - Game.windowHeight / 2 + Game.dragY));
		float drawPositionX2 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX2 - Game.windowWidth / 2 + Game.dragX));
		float drawPositionY2 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY2 - Game.windowHeight / 2 + Game.dragY));
		TextureUtils.drawTexture(image, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);
		Game.activeBatch.flush();

	}

}
