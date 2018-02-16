package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Bed;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class AreaMinorMine {

	public static Area area;

	public static void createMine() {

		// 280, 87

		ArrayList<Wall> extraWalls = new ArrayList<Wall>();
		ArrayList<GameObject> mineFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> minePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> mineSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> rooms = new ArrayList<StructureRoom>();
		ArrayList<Square> squaresToRemove = new ArrayList<Square>();

		mineSections.add(new StructureSection("Minor Mine", 280, 76, 307, 97, false));

		rooms.add(new StructureRoom("Minor Mine", 281, 77, false, new ArrayList<Actor>(),
				new RoomPart(281, 77, 306, 91), new RoomPart(292, 92, 306, 96)));

		rooms.add(new StructureRoom("Minor Mine Shop", 281, 93, false, new ArrayList<Actor>(),
				new RoomPart(281, 93, 290, 96)));

		// entry to mine
		squaresToRemove.add(Game.level.squares[280][87]);

		// entry to mine shop
		squaresToRemove.add(Game.level.squares[285][92]);

		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[284][91], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[299][79], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[300][82], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[301][81], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[303][78], null));

		Structure mine = new Structure("Minor Mine", mineSections, rooms, minePaths, mineFeatures,
				new ArrayList<Square>(), "map_cave.png", 280, 76, 307, 97, true, null, squaresToRemove, extraWalls,
				Templates.WALL, Square.STONE_TEXTURE, 5);
		Game.level.structures.add(mine);

		Bed bed = Templates.BED.makeCopy(Game.level.squares[303][90], null);
		Actor miner = Templates.MINER.makeCopy("Miner Dan", Game.level.squares[302][90], Level.factions.townsPeople,
				bed, 39,
				new GameObject[] { Templates.PICKAXE.makeCopy(null, null), Templates.LANTERN.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest);

	}

}
