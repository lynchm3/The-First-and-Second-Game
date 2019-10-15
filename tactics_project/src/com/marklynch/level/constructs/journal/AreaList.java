package com.marklynch.level.constructs.journal;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.area.town.AreaTown;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;

@SuppressWarnings("serial")
public class AreaList extends CopyOnWriteArrayList<Quest> {
	public static Area town;
	public static Area townForest;
	public static Area innerTownForest;
	public static Area mines;

	Color colorR = new Color(1f, 0f, 0f, 0.05f);
	Color colorG = new Color(0f, 1f, 0f, 0.05f);
	Color colorB = new Color(0f, 0f, 1f, 0.05f);

	public AreaList() {

		town = new Area("Town", "map_town.png", ResourceUtils.getGlobalImage("icon_town.png", false),
				Square.GRASS_TEXTURE, 0, 0, 110, 99, 1, colorR);
		Level.areas.add(town);

		townForest = new Area("Town Forest", "map_forest.png", ResourceUtils.getGlobalImage("icon_forest.png", false),
				Square.GRASS_TEXTURE, 111, 0, 209, 99, 1, colorG);
		Level.areas.add(townForest);

		AreaList.innerTownForest = new Area("Inner Town Forest", null,
				ResourceUtils.getGlobalImage("icon_forest.png", false), Square.GRASS_TEXTURE, 146, 33, 180, 63, 2,
				colorR);
		Level.areas.add(innerTownForest);

		mines = new Area("Mines", "map_cave.png", ResourceUtils.getGlobalImage("icon_cave.png", false),
				Square.GRASS_TEXTURE, 210, 0, 359, 99, 1, colorB);
		Level.areas.add(mines);

	}

	public static void buildAreas() {
		new AreaTown();

		Templates.FAST_TRAVEL_LOCATION.makeCopy(Level.squares[120][20], null, "Town Forest");

	}

	public void makeQuests() {

	}

	public static void draw() {

		if (!Game.areaColors)
			return;

		for (Area area : Level.areas) {
			QuadUtils.drawQuad(area.color, area.gridX1 * Game.SQUARE_WIDTH, area.gridY1 * Game.SQUARE_HEIGHT,
					(area.gridX2 + 1) * Game.SQUARE_WIDTH, (area.gridY2 + 1) * Game.SQUARE_HEIGHT);
		}
	}

	// public void update() {
	// for (Quest quest : this) {
	//
	// }
	// }

}
