package com.marklynch.level.constructs.area.town;

import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.BodyOfWater;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.PavedPathway;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.constructs.faction.FactionList;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.Doctor;
import com.marklynch.objects.actors.Follower;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Storage;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.inanimateobjects.WantedPoster;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ResourceUtils;

public class AreaTown {

	public static int posX = 0, posY = 0;
	public static Structure joesShop;
	public static Structure doctorsShop;
	public static Follower follower;

	public AreaTown() {

		Templates.FAST_TRAVEL_LOCATION.makeCopy(Level.squares[posX + 73][posY + 32], null, "Town");

		Templates.MIRROR.makeCopy(Game.level.squares[posX + 27][posY + 31], null);

		// Trader Joe
		Trader trader = Templates.TRADER.makeCopy("Trader Joe", Game.level.squares[posX + 7][posY + 1],
				Game.level.factions.townsPeople, Templates.BED.makeCopy(Game.level.squares[posX + 16][posY + 1], null),
				10000, new GameObject[] { Templates.APRON.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		// Some ground hatchets
		Templates.VEIN.makeCopy(Game.level.squares[posX + 2][posY + 3], null, false, Templates.ORE, 0.1f);

		// (Game.level.squares[posX + 2][posY + 3], null);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 5][posY + 6], null);
		Templates.HATCHET.makeCopy(Game.level.squares[posX + 1][posY + 6], null);
		Templates.BLOOD.makeCopy(Game.level.squares[posX + 5][posY + 6], null, 1);
		trader.inventory.add(Templates.KATANA.makeCopy(null, null));
		trader.inventory.add(Templates.HATCHET.makeCopy(null, null));
		trader.inventory.add(Templates.HUNTING_BOW.makeCopy(null, null));

		// Some tree to the left
		Storage treeContainer = Templates.TREE_CONTAINER.makeCopy(Game.level.squares[posX + 0][posY + 8], false,
				trader);
		treeContainer.inventory.add(Templates.HATCHET.makeCopy(null, null));
		GameObject readableTree = Templates.TREE_READABLE.makeCopy(Game.level.squares[posX + 1][posY + 9], trader);
		readableTree.conversation = readableTree.createConversation(new Object[] { "MARK WAS HERE" });
		Templates.LEAVES.makeCopy(Game.level.squares[posX + 1][posY + 8], null);

		// Joe's shop
		ArrayList<Square> entranceSquares = new ArrayList<Square>(Square.class,
				Arrays.asList(new Square[] { Game.level.squares[posX + 4][posY + 4] }));
		ArrayList<StructureFeature> shopFeatures = new ArrayList<StructureFeature>(StructureFeature.class);
		shopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Shop Door",
				Game.level.squares[posX + 5][posY + 4], false, false, false, trader)));
		shopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Private Quarters Door",
				Game.level.squares[posX + 11][posY + 4], false, true, true, trader)));
		ArrayList<StructureRoom> shopAtriums = new ArrayList<StructureRoom>(StructureRoom.class);
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", posX + 6, posY + 1, false, false,
				new ArrayList<Actor>(Actor.class, Arrays.asList(new Actor[] { trader })),
				new RoomPart(posX + 6, posY + 1, posX + 10, posY + 4)));
		shopAtriums.add(new StructureRoom("Trader Joe's Shop", posX + 12, posY + 1, true, false,
				new ArrayList<Actor>(Actor.class, Arrays.asList(new Actor[] { trader })),
				new RoomPart(posX + 12, posY + 1, posX + 16, posY + 4)));
		ArrayList<StructureSection> shopSections = new ArrayList<StructureSection>(StructureSection.class);
		shopSections
				.add(new StructureSection("Trader Joe's Shop", posX + 5, posY + 0, posX + 17, posY + 5, false, false));
		joesShop = new Structure("Trader Joe's Shop", shopSections, shopAtriums,
				new ArrayList<StructurePath>(StructurePath.class), shopFeatures, entranceSquares,
				Templates.GOLD.imageTexture, posX + 5, posY + 0, posX + 17, posY + 5, true, trader,
				new ArrayList<Square>(Square.class), new ArrayList<Wall>(Wall.class), Templates.WALL_BUILDING,
				Square.STONE_TEXTURE, 2);
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
		GameObject createWithEtching = Templates.CRATE_WITH_ETCHING.makeCopy(Level.squares[posX + 20][posY + 3], false,
				null);
		createWithEtching.conversation = createWithEtching.createConversation(new Object[] { "For Velentine Shop" });

		// Follower
		Follower follower = Templates.FOLLOWER.makeCopy("???", Game.level.squares[posX + 14][posY + 17],
				FactionList.townsPeople, Templates.BED.makeCopy(Game.level.squares[posX + 15][posY + 17], null), 32,
				new GameObject[] {}, new GameObject[] {}, AreaList.town, new int[] {}, new HOBBY[] {});

		// Doctor's Practice
		Doctor doctor = Templates.DOCTOR.makeCopy("Doctor Mike", Game.level.squares[posX + 7 + 35][posY + 1 + 3],
				Game.level.factions.townsPeople,
				Templates.BED.makeCopy(Game.level.squares[posX + 16 + 35][posY + 1 + 3], null), 10000,
				new GameObject[] { Templates.APRON.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] { HOBBY.HUNTING });
		ArrayList<Square> doctorsEntranceSquares = new ArrayList<Square>(Square.class,
				Arrays.asList(new Square[] { Game.level.squares[posX + 4 + 35][posY + 4 + 3] }));
		ArrayList<StructureFeature> doctorsShopFeatures = new ArrayList<StructureFeature>(StructureFeature.class);
		doctorsShopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Shop Door",
				Game.level.squares[posX + 5 + 35][posY + 4 + 3], false, false, false, doctor)));
		doctorsShopFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Private Quarters Door",
				Game.level.squares[posX + 11 + 35][posY + 4 + 3], false, true, true, doctor)));
		ArrayList<StructureRoom> doctorsShopAtriums = new ArrayList<StructureRoom>(StructureRoom.class);
		doctorsShopAtriums.add(new StructureRoom("Doctor Mike's Practice", posX + 6 + 35, posY + 1 + 3, false, false,
				new ArrayList<Actor>(Actor.class, Arrays.asList(new Actor[] { doctor })),
				new RoomPart(posX + 6 + 35, posY + 1 + 3, posX + 10 + 35, posY + 4 + 3)));
		doctorsShopAtriums.add(new StructureRoom("Doctor Mike's Practice", posX + 12 + 35, posY + 1 + 3, true, false,
				new ArrayList<Actor>(Actor.class, Arrays.asList(new Actor[] { doctor })),
				new RoomPart(posX + 12 + 35, posY + 1 + 3, posX + 16 + 35, posY + 4 + 3)));
		ArrayList<StructureSection> doctorsShopSections = new ArrayList<StructureSection>(StructureSection.class);
		doctorsShopSections.add(new StructureSection("Doctor Mike's Practice", posX + 5 + 35, posY + 0 + 3,
				posX + 17 + 35, posY + 5 + 3, false, false));
		doctorsShop = new Structure("Doctor Mike's Practice", doctorsShopSections, doctorsShopAtriums,
				new ArrayList<StructurePath>(StructurePath.class), doctorsShopFeatures, doctorsEntranceSquares,
				ResourceUtils.getGlobalImage("icon_doctor.png", false), posX + 40, posY + 3, posX + 52, posY + 8, true,
				doctor, new ArrayList<Square>(Square.class), new ArrayList<Wall>(Wall.class), Templates.WALL_CAVE,
				Square.STONE_TEXTURE, 2);
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

		// Lumberjack
		Actor lumberjack = Templates.LUMBERJACK.makeCopy("Lumberjack Ian", Game.level.squares[posX + 100][posY + 9],
				Level.factions.townsPeople, null, 0, new GameObject[] { Templates.HATCHET.makeCopy(null, null) },
				new GameObject[] {}, AreaList.mines, new int[] { Templates.HATCHET.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		lumberjack.area = AreaList.townForest;

		// Wanted Poster
		WantedPoster wantedPoster = Templates.WANTED_POSTER.makeCopy(Game.level.squares[posX + 27][posY + 8],
				"Wanter Poster", new ArrayList<Crime>(Crime.class), trader);
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
		// Diggable 1
		GameObject mound1 = Templates.MOUND.makeCopy(Level.squares[posX + 13][posY + 8], null);
		mound1.level = 50;
		mound1.inventory.add(Templates.ROCK.makeCopy(null, null));
		mound1.inventory.add(Templates.ROCK.makeCopy(null, null));
		mound1.inventory.add(Templates.GOLD.makeCopy(null, null, 13));
		Game.level.squares[posX + 13][posY + 8].setFloorImageTexture(Square.DIGGABLE_GRASS_TEXTURE);
		Templates.TREE.makeCopy(Game.level.squares[posX + 14][posY + 8], trader);
		Templates.TREE.makeCopy(Game.level.squares[posX + 19][posY + 3], trader);
		Templates.TREE.makeCopy(Game.level.squares[posX + 18][posY + 13], trader);
		Templates.TREE.makeCopy(Game.level.squares[posX + 9][posY + 16], trader);
		Templates.TREE.makeCopy(Game.level.squares[posX + 12][posY + 8], trader);
		Templates.TREE.makeCopy(Game.level.squares[posX + 27][posY + 3], trader);
		Templates.TREE.makeCopy(Game.level.squares[posX + 23][posY + 5], trader);
		GameObject mound2 = Templates.MOUND.makeCopy(Level.squares[posX + 23][posY + 5], null);
		mound2.level = 50;
		mound2.inventory.add(Templates.ROCK.makeCopy(null, null));
		mound2.inventory.add(Templates.GOLD.makeCopy(null, null, 23));
		Game.level.squares[posX + 23][posY + 5].setFloorImageTexture(Square.DIGGABLE_GRASS_TEXTURE);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 17][posY + 19], null);

		// Lake
		new BodyOfWater(posX + 90, posY + 30, posX + 100, posY + 37);
		Templates.FISH.makeCopy("Fish", Game.level.squares[posX + 107][posY + 34], FactionList.buns, null,
				new GameObject[] {}, new GameObject[] {}, null);
		Templates.CHEST.makeCopy(Game.level.squares[posX + 107][posY + 33], false, null);
		Templates.CRATE.makeCopy(Game.level.squares[posX + 106][posY + 31], false, null);
		Templates.MESSAGE_IN_A_JAR.makeCopy(Game.level.squares[posX + 89][posY + 34], null);
		Templates.MESSAGE_IN_A_JAR.makeCopy(Game.level.squares[posX + 90][posY + 34], null);
		// new BodyOfWater(105, 30, 106, 37);

		// River
		new BodyOfWater(posX + 0, posY + 15, posX + 3, posY + 15);
		new BodyOfWater(posX + 5, posY + 15, posX + 23, posY + 15);
		new BodyOfWater(posX + 25, posY + 15, posX + 90, posY + 15);
		new BodyOfWater(posX + 92, posY + 15, posX + 92, posY + 18);
		new BodyOfWater(posX + 92, posY + 21, posX + 92, posY + 29);

		// bridges
		Game.level.squares[posX + 91][posY + 15].setFloorImageTexture(Square.STONE_TEXTURE);
		Game.level.squares[posX + 92][posY + 19].setFloorImageTexture(Square.STONE_TEXTURE);
		Game.level.squares[posX + 92][posY + 20].setFloorImageTexture(Square.STONE_TEXTURE);

	}

}
