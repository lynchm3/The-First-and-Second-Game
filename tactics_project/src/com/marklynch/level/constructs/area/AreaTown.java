package com.marklynch.level.constructs.area;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.level.constructs.BodyOfWater;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.faction.FactionList;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.constructs.journal.QuestList;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Nodes;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Sign;
import com.marklynch.objects.Storage;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WantedPoster;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.HOBBY;
import com.marklynch.objects.units.Trader;

public class AreaTown {

	public AreaTown() {

		Templates.MIRROR.makeCopy(Game.level.squares[27][31], null);

		// Trader Joe
		Trader trader = Templates.TRADER.makeCopy("Trader Joe", Game.level.squares[7][1],
				Game.level.factions.townsPeople, Templates.BED.makeCopy(Game.level.squares[16][1], null), 10000,
				new GameObject[] { Templates.APRON.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		// Some ground hatchets
		Templates.HATCHET.makeCopy(Game.level.squares[3][3], QuestList.questSmallGame.hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[3][6], QuestList.questSmallGame.hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[5][6], null);
		Templates.HATCHET.makeCopy(Game.level.squares[1][6], null);
		Templates.BLOOD.makeCopy(Game.level.squares[5][6], null);

		// Joe's shop
		ArrayList<Square> entranceSquares = new ArrayList<Square>(
				Arrays.asList(new Square[] { Game.level.squares[4][4] }));

		ArrayList<StructureFeature> shopFeatures = new ArrayList<StructureFeature>();
		shopFeatures.add(new StructureFeature(
				Templates.DOOR.makeCopy("Shop Door", Game.level.squares[5][4], false, false, false, trader),
				Nodes.townShopOuter));
		shopFeatures.add(new StructureFeature(
				Templates.DOOR.makeCopy("Private Quarters Door", Game.level.squares[11][4], false, true, true, trader),
				Nodes.townShopInner));

		ArrayList<StructureRoom> shopAtriums = new ArrayList<StructureRoom>();
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", 6, 1, false,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { trader })),
				new Node[] { Nodes.townShopInner, Nodes.townShopOuter }, new RoomPart(6, 1, 10, 4)));
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", 12, 1, true,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { trader })), new Node[] { Nodes.townShopInner },
				new RoomPart(12, 1, 16, 4)));
		ArrayList<StructureSection> shopSections = new ArrayList<StructureSection>();
		shopSections.add(new StructureSection("Super Wolf's Den", 5, 0, 17, 5, false));
		Structure joesShop = new Structure("Trader Joe's Shop", shopSections, shopAtriums,
				new ArrayList<StructurePath>(), shopFeatures, entranceSquares, "building2.png", 640, 640 + 1664, -100,
				-100 + 868, true, trader, new ArrayList<Square>(), new ArrayList<Wall>(), Templates.WALL,
				Square.STONE_TEXTURE, 2);
		Game.level.structures.add(joesShop);

		Sign joesShopSign = Templates.SIGN.makeCopy(Game.level.squares[6][6], joesShop.name + " sign",
				new Object[] { joesShop.name }, trader);

		WantedPoster wantedPoster = Templates.WANTED_POSTER.makeCopy(Game.level.squares[27][8], "Wanter Poster",
				new ArrayList<Crime>(), trader);
		AreaList.town.wantedPoster = wantedPoster;
		AreaList.townForest.wantedPoster = wantedPoster;
		AreaList.innerTownForest.wantedPoster = wantedPoster;
		AreaList.mines.wantedPoster = wantedPoster;

		Storage lostAndFound = Templates.LOST_AND_FOUND.makeCopy("Town Lost and Found", Game.level.squares[75][53],
				false, null);
		AreaList.town.lostAndFound = lostAndFound;
		AreaList.townForest.lostAndFound = lostAndFound;
		AreaList.innerTownForest.lostAndFound = lostAndFound;
		AreaList.mines.lostAndFound = lostAndFound;

		trader.inventory.add(Templates.KATANA.makeCopy(null, null));
		trader.inventory.add(Templates.HATCHET.makeCopy(null, null));
		trader.inventory.add(Templates.HUNTING_BOW.makeCopy(null, null));
		Templates.SHOP_COUNTER.makeCopy(Game.level.squares[7][1], null);

		trader.shopRoom = shopAtriums.get(0);
		trader.shopSign = joesShopSign;
		// trader.wantedPoster = wantedPoster;
		// ArrayList<Square> doorLocations2 = new ArrayList<Square>();
		// doorLocations2.add(Game.level.squares[7][9]);
		// // doorLocations2.add(Game.level.squares[11][9]);
		// Game.level.structures.add(new Building("Hunting Lodge", 7, 7, 11, 11,
		// doorLocations2));

		// Add a game object
		// Templates.DUMPSTER.makeCopy(Game.level.squares[4][2], null);
		Templates.FUR.makeCopy(Game.level.squares[0][7], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[0][8], null);
		Templates.TREE.makeCopy(Game.level.squares[1][2], null);
		Templates.TREE.makeCopy(Game.level.squares[14][8], null);
		Templates.TREE.makeCopy(Game.level.squares[19][3], null);
		Templates.TREE.makeCopy(Game.level.squares[18][13], null);
		Templates.TREE.makeCopy(Game.level.squares[9][16], null);
		Templates.TREE.makeCopy(Game.level.squares[12][8], null);
		Templates.TREE.makeCopy(Game.level.squares[27][3], null);
		Templates.TREE.makeCopy(Game.level.squares[23][5], null);
		Templates.BUSH.makeCopy(Game.level.squares[17][19], null);

		new BodyOfWater(105, 30, 110, 37);
		Templates.FISH.makeCopy("Fish", Game.level.squares[107][34], FactionList.buns, null, new GameObject[] {},
				new GameObject[] {}, null);
		Templates.CHEST.makeCopy("Chest", Game.level.squares[107][33], false, null);
		Templates.CRATE.makeCopy("Crate", Game.level.squares[106][31], false, null);
		// new BodyOfWater(105, 30, 106, 37);

	}

}
