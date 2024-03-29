package com.marklynch.level.constructs.area;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.Bed;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.ResourceUtils;

public class AreaMinorMine {

	public static Area area;

	public static void createMine() {

		// 280, 87

		CopyOnWriteArrayList<Wall> extraWalls = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> mineFeatures = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> minePaths = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> mineSections = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> rooms = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> squaresToRemove = new CopyOnWriteArrayList<Square>(Square.class);

		mineSections.add(new StructureSection("Minor Mine", 280, 76, 307, 97, false, false));

		rooms.add(new StructureRoom("Minor Mine", 281, 77, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(281, 77, 306, 88), new RoomPart(281, 89, 301, 91), new RoomPart(292, 92, 301, 96)));

		StructureRoom shopRoom = new StructureRoom("What's Mine is Yours*", 281, 93, false, false,
				new CopyOnWriteArrayList<Actor>(Actor.class), new RoomPart(281, 93, 290, 96));
		rooms.add(shopRoom);

		rooms.add(new StructureRoom("Minor Mine Quarters", 303, 90, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(303, 90, 306, 96)));

		// entry to mine
		squaresToRemove.add(Game.level.squares[280][87]);

		// entry to mine shop
		squaresToRemove.add(Game.level.squares[285][92]);
		// entry to mine quarters
		squaresToRemove.add(Game.level.squares[302][91]);

		Templates.VEIN.makeCopy(Game.level.squares[284][91], null, true, Templates.ORE, 0.1f);
		Templates.VEIN.makeCopy(Game.level.squares[299][79], null, true, Templates.ORE, 0.1f);
		Templates.VEIN.makeCopy(Game.level.squares[300][82], null, true, Templates.ORE, 0.1f);
		Templates.VEIN.makeCopy(Game.level.squares[301][81], null, true, Templates.ORE, 0.1f);
		Templates.VEIN.makeCopy(Game.level.squares[303][78], null, true, Templates.ORE, 0.1f);

		Structure mine = new Structure("Minor Mine", mineSections, rooms, minePaths, mineFeatures,
				new CopyOnWriteArrayList<Square>(Square.class), ResourceUtils.getGlobalImage("icon_mine.png", false),
				280, 76, 307, 97, true, null, squaresToRemove, extraWalls,
				Templates.WALL_CAVE, Square.STONE_TEXTURE, 5);
		Game.level.structures.add(mine);

		Bed bed = Templates.BED.makeCopy(Game.level.squares[303][90], null);
		Actor miner = Templates.MINER.makeCopy("Miner Dan", Game.level.squares[302][90], Level.factions.townsPeople,
				bed, 0,
				new GameObject[] {
						/* Templates.PICKAXE.makeCopy(null, null), */Templates.LANTERN.makeCopy(null, null) },
				new GameObject[] {}, AreaList.mines, new int[] { Templates.PICKAXE.templateId },
				new HOBBY[] { HOBBY.HUNTING });

		// 281, 93
		// Trader Jake
		Trader trader = Templates.TRADER.makeCopy("Trader Jake", Game.level.squares[281][94],
				Game.level.factions.townsPeople, Templates.BED.makeCopy(Game.level.squares[303][95], null), 10000,
				new GameObject[] { Templates.PICKAXE.makeCopy(null, null), Templates.PICKAXE.makeCopy(null, null),
						Templates.PICKAXE.makeCopy(null, null), Templates.PICKAXE.makeCopy(null, null),
						Templates.PICKAXE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.mines, new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		GameObject shopSign = Templates.SIGN.makeCopy(Game.level.squares[288][91], trader);
		shopSign.conversation = shopSign.createConversation(new Object[] { shopRoom.name });

		// Random ground pickaxe
		Templates.PICKAXE.makeCopy(Game.level.squares[292][91], null);
		Templates.APPLE.makeCopy(Game.level.squares[293][91], null);
		Templates.FUR.makeCopy(Game.level.squares[294][91], null);
		Templates.HUNTING_BOW.makeCopy(Game.level.squares[295][91], null);

		trader.shopRoom = shopRoom;
		trader.shopSign = shopSign;

	}

}
