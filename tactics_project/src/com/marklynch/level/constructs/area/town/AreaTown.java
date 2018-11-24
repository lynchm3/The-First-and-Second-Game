package com.marklynch.level.constructs.area.town;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.BodyOfWater;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.PavedPathway;
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
import com.marklynch.objects.Storage;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WantedPoster;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.HOBBY;
import com.marklynch.objects.units.Doctor;
import com.marklynch.objects.units.Trader;

public class AreaTown {

	public static int posX = 0, posY = 0;

	public AreaTown() {

		Templates.MIRROR.makeCopy(Game.level.squares[posX + 27][posY + 31], null);

		// Trader Joe
		Trader trader = Templates.TRADER.makeCopy("Trader Joe", Game.level.squares[posX + 7][posY + 1],
				Game.level.factions.townsPeople, Templates.BED.makeCopy(Game.level.squares[posX + 16][posY + 1], null),
				10000, new GameObject[] { Templates.APRON.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		// Some ground hatchets
		Templates.VEIN.makeCopy(Game.level.squares[posX + 2][posY + 3], null, false, Templates.ORE, 0.1f);

		// (Game.level.squares[posX + 2][posY + 3], null);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 3][posY + 3], QuestList.questSmallGame.hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 3][posY + 6], QuestList.questSmallGame.hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 5][posY + 6], null);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 1][posY + 6], null);
		Templates.BLOOD.makeCopy(Game.level.squares[posX + 5][posY + 6], null);
		trader.inventory.add(Templates.KATANA.makeCopy(null, null));
		trader.inventory.add(Templates.HATCHET.makeCopy(null, null));
		trader.inventory.add(Templates.HUNTING_BOW.makeCopy(null, null));

		// Some tree to the left
		Storage treeContainer = Templates.TREE_CONTAINER.makeCopy(Game.level.squares[posX + 0][posY + 8], false, null);
		treeContainer.inventory.add(Templates.HATCHET.makeCopy(null, null));
		GameObject readableTree = Templates.TREE_READABLE.makeCopy(Game.level.squares[posX + 1][posY + 9], null);
		readableTree.conversation = readableTree.createConversation(new Object[] { "MARK WAS HERE" });
		Templates.LEAVES.makeCopy(Game.level.squares[posX + 1][posY + 8], null);

		// Joe's shop
		ArrayList<Square> entranceSquares = new ArrayList<Square>(
				Arrays.asList(new Square[] { Game.level.squares[posX + 4][posY + 4] }));
		ArrayList<StructureFeature> shopFeatures = new ArrayList<StructureFeature>();
		shopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Shop Door",
				Game.level.squares[posX + 5][posY + 4], false, false, false, trader), Nodes.townShopOuter));
		shopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Private Quarters Door",
				Game.level.squares[posX + 11][posY + 4], false, true, true, trader), Nodes.townShopInner));
		ArrayList<StructureRoom> shopAtriums = new ArrayList<StructureRoom>();
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", posX + 6, posY + 1, false, false,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { trader })),
				new Node[] { Nodes.townShopInner, Nodes.townShopOuter },
				new RoomPart(posX + 6, posY + 1, posX + 10, posY + 4)));
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", posX + 12, posY + 1, true, false,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { trader })), new Node[] { Nodes.townShopInner },
				new RoomPart(posX + 12, posY + 1, posX + 16, posY + 4)));
		ArrayList<StructureSection> shopSections = new ArrayList<StructureSection>();
		shopSections
				.add(new StructureSection("Trader Joe's Shop", posX + 5, posY + 0, posX + 17, posY + 5, false, false));
		Structure joesShop = new Structure("Trader Joe's Shop", shopSections, shopAtriums,
				new ArrayList<StructurePath>(), shopFeatures, entranceSquares, "building2.png", posX + 640,
				posY + 640 + 1664, posX + -100, posY + -100 + 868, true, trader, new ArrayList<Square>(),
				new ArrayList<Wall>(), Templates.WALL, Square.STONE_TEXTURE, 2);
		Game.level.structures.add(joesShop);
		GameObject joesShopSign = Templates.SIGN.makeCopy(Game.level.squares[posX + 6][posY + 6], trader);
		joesShopSign.conversation = joesShopSign.createConversation(new Object[] { joesShop.name });
		joesShopSign.name = joesShop.name + " sign";
		Templates.SHOP_COUNTER.makeCopy(Game.level.squares[posX + 7][posY + 1], null);
		trader.shopRoom = shopAtriums.get(0);
		trader.shopSign = joesShopSign;

		// Boxws beside Joe's shop
		Templates.CRATE.makeCopy(Level.squares[posX + 18][posY + 0], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 18][posY + 1], false, null);
		Templates.SHOVEL.makeCopy(Level.squares[posX + 18][posY + 2], null);
		// Templates.CRATE.makeCopy("Crate", Level.squares[posX + 18][posY + 2], false,
		// null);
		Templates.CRATE.makeCopy(Level.squares[posX + 18][posY + 3], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 18][posY + 4], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 19][posY + 0], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 19][posY + 1], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 19][posY + 2], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 19][posY + 3], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 20][posY + 2], false, null);

		// Doctor's Practice
		Doctor doctor = Templates.DOCTOR.makeCopy("Doctor Mike", Game.level.squares[posX + 7 + 35][posY + 1 + 3],
				Game.level.factions.townsPeople,
				Templates.BED.makeCopy(Game.level.squares[posX + 16 + 35][posY + 1 + 3], null), 10000,
				new GameObject[] { Templates.APRON.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] { HOBBY.HUNTING });
		ArrayList<Square> doctorsEntranceSquares = new ArrayList<Square>(
				Arrays.asList(new Square[] { Game.level.squares[posX + 4 + 35][posY + 4 + 3] }));
		ArrayList<StructureFeature> doctorsShopFeatures = new ArrayList<StructureFeature>();
		doctorsShopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Shop Door",
				Game.level.squares[posX + 5 + 35][posY + 4 + 3], false, false, false, doctor), Nodes.townShopOuter));
		doctorsShopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Private Quarters Door",
				Game.level.squares[posX + 11 + 35][posY + 4 + 3], false, true, true, doctor), Nodes.townShopInner));
		ArrayList<StructureRoom> doctorsShopAtriums = new ArrayList<StructureRoom>();
		doctorsShopAtriums.add(new StructureRoom("Doctor Mike's Practice", posX + 6 + 35, posY + 1 + 3, false, false,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { doctor })),
				new Node[] { Nodes.townShopInner, Nodes.townShopOuter },
				new RoomPart(posX + 6 + 35, posY + 1 + 3, posX + 10 + 35, posY + 4 + 3)));
		doctorsShopAtriums.add(new StructureRoom("Doctor Mike's Practice", posX + 12 + 35, posY + 1 + 3, true, false,
				new ArrayList<Actor>(Arrays.asList(new Actor[] { doctor })), new Node[] { Nodes.townShopInner },
				new RoomPart(posX + 12 + 35, posY + 1 + 3, posX + 16 + 35, posY + 4 + 3)));
		ArrayList<StructureSection> doctorsShopSections = new ArrayList<StructureSection>();
		doctorsShopSections.add(new StructureSection("Doctor Mike's Practice", posX + 5 + 35, posY + 0 + 3,
				posX + 17 + 35, posY + 5 + 3, false, false));
		Structure doctorsShop = new Structure("Doctor Mike's Practice", doctorsShopSections, doctorsShopAtriums,
				new ArrayList<StructurePath>(), doctorsShopFeatures, doctorsEntranceSquares, "building2.png",
				posX + 640 + 35, posY + 640 + 1664 + 3, posX + -100 + 35, posY + -100 + 868 + 3, true, doctor,
				new ArrayList<Square>(), new ArrayList<Wall>(), Templates.WALL, Square.STONE_TEXTURE, 2);
		Game.level.structures.add(doctorsShop);
		GameObject doctorsShopSign = Templates.SIGN.makeCopy(Game.level.squares[posX + 6 + 35][posY + 6 + 3], doctor);
		doctorsShopSign.conversation = doctorsShopSign.createConversation(new Object[] { doctorsShop.name });
		doctorsShopSign.name = doctorsShop.name + " sign";
		Templates.SHOP_COUNTER.makeCopy(Game.level.squares[posX + 7 + 35][posY + 1 + 3], null);
		doctor.shopRoom = doctorsShopAtriums.get(0);
		doctor.shopSign = doctorsShopSign;
		// Path from cotors office to well
		new PavedPathway(posX + 24, posY + 7, posX + 38, posY + 7);
		new PavedPathway(posX + 24, posY + 8, posX + 24, posY + 18);

		// Wanted Poster
		WantedPoster wantedPoster = Templates.WANTED_POSTER.makeCopy(Game.level.squares[posX + 27][posY + 8],
				"Wanter Poster", new ArrayList<Crime>(), trader);
		AreaList.town.wantedPoster = wantedPoster;
		AreaList.townForest.wantedPoster = wantedPoster;
		AreaList.innerTownForest.wantedPoster = wantedPoster;
		AreaList.mines.wantedPoster = wantedPoster;

		// Lost and found
		Storage lostAndFound = Templates.LOST_AND_FOUND.makeCopy(Game.level.squares[posX + 75][posY + 53], false, null);
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

		// Floor game objects
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
		new BodyOfWater(posX + 90, posY + 30, posX + 100, posY + 37);
		Templates.FISH.makeCopy("Fish", Game.level.squares[posX + 107][posY + 34], FactionList.buns, null,
				new GameObject[] {}, new GameObject[] {}, null);
		Templates.CHEST.makeCopy(Game.level.squares[posX + 107][posY + 33], false, null);
		Templates.CRATE.makeCopy(Game.level.squares[posX + 106][posY + 31], false, null);
		// new BodyOfWater(105, 30, 106, 37);

		// River
		new BodyOfWater(posX + 0, posY + 15, posX + 3, posY + 15);
		new BodyOfWater(posX + 5, posY + 15, posX + 23, posY + 15);
		new BodyOfWater(posX + 25, posY + 15, posX + 90, posY + 15);
		new BodyOfWater(posX + 92, posY + 15, posX + 92, posY + 29);

		Game.level.squares[posX + 91][posY + 15].floorImageTexture = Square.STONE_TEXTURE;

	}

}
