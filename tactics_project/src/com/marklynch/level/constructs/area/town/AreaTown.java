package com.marklynch.level.constructs.area.town;

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

	public AreaTown(int posX, int posY) {

		Templates.MIRROR.makeCopy(Game.level.squares[posX + 27][posY + 31], null);

		// Trader Joe
		Trader trader = Templates.TRADER.makeCopy("Trader Joe", Game.level.squares[posX + 7][posY + 1],
				Game.level.factions.townsPeople, Templates.BED.makeCopy(Game.level.squares[posX + 16][posY + 1], null),
				10000, new GameObject[] { Templates.APRON.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		// Some ground hatchets
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 3][posY + 3], QuestList.questSmallGame.hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 3][posY + 6], QuestList.questSmallGame.hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 5][posY + 6], null);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 1][posY + 6], null);
		Templates.BLOOD.makeCopy(Game.level.squares[posX + 5][posY + 6], null);
		trader.inventory.add(Templates.KATANA.makeCopy(null, null));
		trader.inventory.add(Templates.HATCHET.makeCopy(null, null));
		trader.inventory.add(Templates.HUNTING_BOW.makeCopy(null, null));

		// Joe's shop
		ArrayList<Square> entranceSquares = new ArrayList<Square>(
				Arrays.asList(new Square[] { Game.level.squares[posX + 4][posY + 4] }));
		ArrayList<StructureFeature> shopFeatures = new ArrayList<StructureFeature>();
		shopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Shop Door",
				Game.level.squares[posX + 5][posY + 4], false, false, false, trader), Nodes.townShopOuter));
		shopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Private Quarters Door",
				Game.level.squares[posX + 11][posY + 4], false, true, true, trader), Nodes.townShopInner));
		ArrayList<StructureRoom> shopAtriums = new ArrayList<StructureRoom>();
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", posX + 6, posY + 1, false,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { trader })),
				new Node[] { Nodes.townShopInner, Nodes.townShopOuter },
				new RoomPart(posX + 6, posY + 1, posX + 10, posY + 4)));
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", posX + 12, posY + 1, true,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { trader })), new Node[] { Nodes.townShopInner },
				new RoomPart(posX + 12, posY + 1, posX + 16, posY + 4)));
		ArrayList<StructureSection> shopSections = new ArrayList<StructureSection>();
		shopSections.add(new StructureSection("Super Wolf's Den", posX + 5, posY + 0, posX + 17, posY + 5, false));
		Structure joesShop = new Structure("Trader Joe's Shop", shopSections, shopAtriums,
				new ArrayList<StructurePath>(), shopFeatures, entranceSquares, "building2.png", posX + 640,
				posY + 640 + 1664, posX + -100, posY + -100 + 868, true, trader, new ArrayList<Square>(),
				new ArrayList<Wall>(), Templates.WALL, Square.STONE_TEXTURE, 2);
		Game.level.structures.add(joesShop);
		Sign joesShopSign = Templates.SIGN.makeCopy(Game.level.squares[posX + 6][posY + 6], joesShop.name + " sign",
				new Object[] { joesShop.name }, trader);
		Templates.SHOP_COUNTER.makeCopy(Game.level.squares[posX + 7][posY + 1], null);
		trader.shopRoom = shopAtriums.get(0);
		trader.shopSign = joesShopSign;

		// Wanted Poster
		WantedPoster wantedPoster = Templates.WANTED_POSTER.makeCopy(Game.level.squares[posX + 27][posY + 8],
				"Wanter Poster", new ArrayList<Crime>(), trader);
		AreaList.town.wantedPoster = wantedPoster;
		AreaList.townForest.wantedPoster = wantedPoster;
		AreaList.innerTownForest.wantedPoster = wantedPoster;
		AreaList.mines.wantedPoster = wantedPoster;

		// Lost and found
		Storage lostAndFound = Templates.LOST_AND_FOUND.makeCopy("Town Lost and Found",
				Game.level.squares[posX + 75][posY + 53], false, null);
		AreaList.town.lostAndFound = lostAndFound;
		AreaList.townForest.lostAndFound = lostAndFound;
		AreaList.innerTownForest.lostAndFound = lostAndFound;
		AreaList.mines.lostAndFound = lostAndFound;
		// trader.wantedPoster = wantedPoster;
		// ArrayList<Square> doorLocations2 = new ArrayList<Square>();
		// doorLocations2.add(Game.level.squares[7][9]);
		// // doorLocations2.add(Game.level.squares[11][9]);
		// Game.level.structures.add(new Building("Hunting Lodge", 7, 7, 11, 11,
		// doorLocations2));

		// Add a game object
		// Templates.DUMPSTER.makeCopy(Game.level.squares[4][2], null);
		Templates.FUR.makeCopy(Game.level.squares[posX + 0][posY + 7], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[posX + 0][posY + 8], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 1][posY + 2], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 14][posY + 8], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 19][posY + 3], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 18][posY + 13], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 9][posY + 16], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 12][posY + 8], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 27][posY + 3], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 23][posY + 5], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 17][posY + 19], null);

		// Lake
		new BodyOfWater(posX + 105, 30, posX + 110, 37);
		Templates.FISH.makeCopy("Fish", Game.level.squares[posX + 107][posY + 34], FactionList.buns, null,
				new GameObject[] {}, new GameObject[] {}, null);
		Templates.CHEST.makeCopy("Chest", Game.level.squares[posX + 107][posY + 33], false, null);
		Templates.CRATE.makeCopy("Crate", Game.level.squares[posX + 106][posY + 31], false, null);
		// new BodyOfWater(105, 30, 106, 37);

	}

}
