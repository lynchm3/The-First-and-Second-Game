package com.marklynch.level.constructs.journal;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Nodes;
import com.marklynch.level.squares.Square;

@SuppressWarnings("serial")
public class AreaList extends ArrayList<Quest> {
	public static Area town;
	public static Area townForest;
	public static Area innerTownForest;
	public static Area mines;

	public AreaList() {

		town = new Area("Town", "map_forest.png", Square.GRASS_TEXTURE, 0, 0, 111, 99, 1, Nodes.townShopCrossRoads,
				Nodes.townCenter, Nodes.townNorth, Nodes.townEast, Nodes.lodgeJunction, Nodes.farmOuterBend,
				Nodes.farmInnerBend);

		Area areaTownForest = new Area("Town Forest", "map_forest.png", Square.DARK_GRASS_TEXTURE, 111, 0, 210, 99, 1,
				Nodes.townEast, Nodes.forestNorth, Nodes.forestNorthEast);
		AreaList.townForest = areaTownForest;
		Level.areas.add(areaTownForest);

		Area areaInnerTownForest = new Area("Inner Town Forest", null, Square.DARK_GRASS_TEXTURE, 146, 33, 180, 63, 2,
				Nodes.townEast, Nodes.forestNorth, Nodes.forestNorthEast);
		AreaList.innerTownForest = areaInnerTownForest;
		Level.areas.add(areaInnerTownForest);
		mines = new Area("Mines", "map_forest.png", Square.GRASS_TEXTURE, 210, 0, 359, 99, 1, Nodes.forestNorthEast,
				Nodes.caveOfTheBlindWest, Nodes.minorMine);

	}

	public void makeQuests() {

	}

	// public void update() {
	// for (Quest quest : this) {
	//
	// }
	// }

}
