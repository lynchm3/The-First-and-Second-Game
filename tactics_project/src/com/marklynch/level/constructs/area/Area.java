package com.marklynch.level.constructs.area;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import com.marklynch.utils.Texture;

public class Area {

	public String name;
	public Texture image;
	public int gridX1, gridY1, gridX2, gridY2, gridCenterX, gridCenterY;
	public Square centreSuqare;
	public boolean seenByPlayer = false;
	boolean showOnMap = false;
	public int level;

	public Area(String name, String imageString, Texture squareTexture, int gridX1, int gridY1, int gridX2, int gridY2,
			int level) {
		super();
		this.name = name;
		if (imageString != null)
			this.image = ResourceUtils.getGlobalImage(imageString);
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;
		this.gridCenterX = (gridX1 + gridX2) / 2;
		this.gridCenterY = (gridY1 + gridY2) / 2;
		this.centreSuqare = Game.level.squares[gridCenterX][gridCenterY];
		this.level = level;

		for (int i = gridX1; i <= gridX2; i++) {
			for (int j = gridY1; j <= gridY2; j++) {
				Game.level.squares[i][j].areaSquareIsIn = this;
				Game.level.squares[i][j].imageTexture = squareTexture;
			}
		}
	}

	public void hasBeenSeenByPlayer(Square squareSeen) {
		this.seenByPlayer = true;
		this.showOnMap = true;
		new ActionSpot(Game.level.player, this, squareSeen).perform();
	}

	public void drawUI() {

		if (!showOnMap && !Game.fullVisiblity)
			return;

		// 40sqrs is ideal

		if (this.image == null)
			return;

		int squarePositionX1 = (gridX1 + ((gridX2 - gridX1 - 40) / 2)) * (int) Game.SQUARE_WIDTH;
		int squarePositionY1 = (gridY1 + ((gridY2 - gridY1 - 40) / 2)) * (int) Game.SQUARE_HEIGHT;
		int squarePositionX2 = (40 + gridX1 + ((gridX2 - gridX1 - 40) / 2)) * (int) Game.SQUARE_WIDTH;
		int squarePositionY2 = (40 + gridY1 + ((gridY2 - gridY1 - 40) / 2)) * (int) Game.SQUARE_HEIGHT;
		float drawPositionX1 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX1 - Game.windowWidth / 2 + Game.getDragXWithOffset()));
		float drawPositionY1 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY1 - Game.windowHeight / 2 + Game.getDragYWithOffset()));
		float drawPositionX2 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX2 - Game.windowWidth / 2 + Game.getDragXWithOffset()));
		float drawPositionY2 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY2 - Game.windowHeight / 2 + Game.getDragYWithOffset()));
		TextureUtils.drawTexture(image, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);

	}

}
