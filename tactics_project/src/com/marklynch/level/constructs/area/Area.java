package com.marklynch.level.constructs.area;

import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.actions.ActionSpot;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.Storage;
import com.marklynch.objects.inanimateobjects.WantedPoster;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Area implements Idable, Place {

	public String name;
	public Texture image;
	public int gridX1, gridY1, gridX2, gridY2, gridCenterX, gridCenterY;
	public Square centreSquare;
	public boolean seenByPlayer = false;
	boolean showOnMap = false;
	public int level;
	public Color color;
	public ArrayList<Node> nodes = new ArrayList<Node>(Node.class);
	public Storage lostAndFound;
	public WantedPoster wantedPoster;
	public Long id;

	public Area(String name, String imageString, Texture squareTexture, int gridX1, int gridY1, int gridX2, int gridY2,
			int level, Color color, Node... nodes) {
		super();
		this.id = Level.generateNewId(this);
		this.name = name;
		if (imageString != null)
			this.image = ResourceUtils.getGlobalImage(imageString, false);
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;
		this.gridCenterX = (gridX1 + gridX2) / 2;
		this.gridCenterY = (gridY1 + gridY2) / 2;
		this.centreSquare = Level.squares[gridCenterX][gridCenterY];
		this.level = level;
		this.color = color;
		this.nodes.clear();
		this.nodes.addAll(Arrays.asList(nodes));

		for (int i = gridX1; i <= gridX2; i++) {
			for (int j = gridY1; j <= gridY2; j++) {
				Level.squares[i][j].areaSquareIsIn = this;
				Level.squares[i][j].setFloorImageTexture(squareTexture);
				Level.squares[i][j].nodes = this.nodes;
				for (Node node : this.nodes) {
					node.addSquare(Level.squares[i][j]);
				}

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

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Texture getIcon() {
		return image;
	}

	@Override
	public Square getCentreSquare() {
		return centreSquare;
	}

}
