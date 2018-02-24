package com.marklynch.level.constructs.journal;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.area.AreaTown;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Nodes;
import com.marklynch.level.squares.Square;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;

@SuppressWarnings("serial")
public class AreaList extends ArrayList<Quest> {
	public static Area town;
	public static Area townForest;
	public static Area innerTownForest;
	public static Area mines;

	Color colorR = new Color(1f, 0f, 0f, 0.05f);
	Color colorG = new Color(0f, 1f, 0f, 0.05f);
	Color colorB = new Color(0f, 0f, 1f, 0.05f);

	public AreaList() {

		town = new Area("Town", "map_forest.png", Square.GRASS_TEXTURE, 0, 0, 111, 99, 1, colorR,
				Nodes.townShopCrossRoads, Nodes.townCenter, Nodes.townNorth, Nodes.townEast, Nodes.lodgeJunction,
				Nodes.farmOuterBend, Nodes.farmInnerBend);
		Level.areas.add(town);

		townForest = new Area("Town Forest", "map_forest.png", Square.DARK_GRASS_TEXTURE, 111, 0, 210, 99, 1, colorG,
				Nodes.townEast, Nodes.forestNorth, Nodes.forestNorthEast);
		Level.areas.add(townForest);

		AreaList.innerTownForest = new Area("Inner Town Forest", null, Square.DARK_GRASS_TEXTURE, 146, 33, 180, 63, 2,
				colorR, Nodes.townEast, Nodes.forestNorth, Nodes.forestNorthEast);
		Level.areas.add(innerTownForest);

		mines = new Area("Mines", "map_forest.png", Square.GRASS_TEXTURE, 210, 0, 359, 99, 1, colorB,
				Nodes.forestNorthEast, Nodes.caveOfTheBlindWest, Nodes.minorMine);
		Level.areas.add(mines);

	}

	public static void buildAreas() {
		new AreaTown();
	}

	public void makeQuests() {

	}

	public static void draw() {
		for (Area area : Level.areas) {
			QuadUtils.drawQuad(area.color, area.gridX1 * Game.SQUARE_WIDTH, area.gridY1 * Game.SQUARE_HEIGHT,
					area.gridX2 * Game.SQUARE_WIDTH, area.gridY2 * Game.SQUARE_HEIGHT);
		}
	}

	// public void update() {
	// for (Quest quest : this) {
	//
	// }
	// }

}
