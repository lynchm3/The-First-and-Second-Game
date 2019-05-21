package com.marklynch.level.quest.thesecretroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.PavedPathway;
import com.marklynch.level.constructs.area.town.AreaTown;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationForKidnapper;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.constructs.journal.JournalLog;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.quest.thepigs.QuestThePigs;
import com.marklynch.level.squares.NodeList;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Kidnapper;
import com.marklynch.objects.inanimateobjects.Bed;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.RemoteDoor;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;

public class QuestTheSecretRoom extends Quest {
	public Kidnapper kidnapper;

	final String OBJECTIVE_FIND_OUT_WHAT_THE_WOMAN_WANTS = "Find out what the man wants";

	public Objective objectiveKidnapper;
	public Objective objectiveChild;
	// public Objective objectiveHunters;
	// public Objective objectiveEnvironmentalist;

	// Maybe the child is a bit slow
	// Just insinuate what was happening to the kid, obvs dont say in explicit terms

	public JournalLog journalLogMetKidnapper = new JournalLog(
			"I've met a man in town who would like me to help him out.");
	public JournalLog journalLogTheFavour = new JournalLog(
			"He wants me to fetch his child, who's out playing near the pond to the east of town.");
	// How do I reword this
	ConversationForKidnapper conversationForKidnapper;

	public QuestTheSecretRoom() {
		name = "MISSING CHILD";

		// Kidnapper kidnapper = Templates.KIDNAPPER.makeCopy()
		Bed bed = null;
		kidnapper = Templates.KIDNAPPER.makeCopy("KIDNAPPER",
				Game.level.squares[AreaTown.posX + 23][AreaTown.posY + 32], Game.level.factions.townsPeople, bed, 100,
				new GameObject[] {}, new GameObject[] {}, null);
		kidnapper.quest = this;
		ArrayList<Actor> ownershipArrayList = new ArrayList<Actor>(Actor.class);
		ownershipArrayList.add(kidnapper);

		ArrayList<Wall> structureExtraWalls = new ArrayList<Wall>(Wall.class);
		ArrayList<StructureFeature> structureFeatures = new ArrayList<StructureFeature>(StructureFeature.class);
		ArrayList<StructurePath> structurePaths = new ArrayList<StructurePath>(StructurePath.class);
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>(StructureSection.class);
		ArrayList<StructureRoom> structureRooms = new ArrayList<StructureRoom>(StructureRoom.class);
		ArrayList<Square> structureSquaresToRemove = new ArrayList<Square>(Square.class);
		ArrayList<Square> structureEntranceSquares = new ArrayList<Square>(Square.class);

		// Front section
		structureSections.add(new StructureSection("A Cozy Place", AreaTown.posX + 21, AreaTown.posY + 30,
				AreaTown.posX + 29, AreaTown.posY + 36, false, true, kidnapper));

		// Front door
		structureFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Front Door",
				Game.level.squares[AreaTown.posX + 24][AreaTown.posY + 30], false, false, false, kidnapper),
				NodeList.dungeonHouseOuter));

		// Front room
		StructureRoom livingRoom = new StructureRoom("Living Room", AreaTown.posX + 22, AreaTown.posY + 31, false, true,
				ownershipArrayList, new RoomPart(AreaTown.posX + 22, AreaTown.posY + 31, AreaTown.posX + 28, AreaTown.posY + 35));
		structureRooms.add(livingRoom);

		// Front room decorative walls
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][20],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][26],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[46][23],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[51][23],
		// null));

		// Back Section
		structureSections.add(new StructureSection("Secret Room", AreaTown.posX + 19, AreaTown.posY + 36,
				AreaTown.posX + 36, AreaTown.posY + 46, true, true, kidnapper));

		// False wall
		RemoteDoor falseWall = Templates.OPENABLE_WALL.makeCopy("Wall",
				Game.level.squares[AreaTown.posX + 22][AreaTown.posY + 36], false, null);
		structureFeatures.add(new StructureFeature(falseWall, NodeList.dungeonHouseHiddenArea));

		// Back room
		StructureRoom backRoom = new StructureRoom("Back room", AreaTown.posX + 20, AreaTown.posY + 37, false, true,
				ownershipArrayList, 4, new RoomPart(AreaTown.posX + 20, AreaTown.posY + 37, AreaTown.posX + 35, AreaTown.posY + 45));
		structureRooms.add(backRoom);

		// back room decorative walls
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][30],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][34],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[42][34],
		// null));

		// Rat
		Templates.RAT.makeCopy("Rat", Game.level.squares[AreaTown.posX + 25][AreaTown.posY + 39],
				Game.level.factions.rats, null, new GameObject[] {}, new GameObject[] {}, null);

		Game.level.structures.add(new Structure("A Lovely House", structureSections, structureRooms, structurePaths,
				structureFeatures, structureEntranceSquares, ResourceUtils.getGlobalImage("icon_house.png", false),
				AreaTown.posX + 0, AreaTown.posY + 0, AreaTown.posX + 0, AreaTown.posY + 0, true, kidnapper,
				structureSquaresToRemove, structureExtraWalls, Templates.WALL_CAVE, Square.STONE_TEXTURE, 2));

		// Path to town 24,21 -> 24,29
		new PavedPathway(AreaTown.posX + 24, AreaTown.posY + 21, AreaTown.posX + 24, AreaTown.posY + 29);

		// Bushed along path to town
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 23][AreaTown.posY + 25], null);
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 25][AreaTown.posY + 25], null);
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 23][AreaTown.posY + 27], null);
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 25][AreaTown.posY + 27], null);
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 23][AreaTown.posY + 29], null);
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 25][AreaTown.posY + 28], null);
		Templates.BUSH.makeCopy(Level.squares[AreaTown.posX + 25][AreaTown.posY + 29], null);

		// TownSquare Well 24,21
		GameObject well = Templates.WELL.makeCopy(Level.squares[AreaTown.posX + 23][AreaTown.posY + 20], null);
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));

		// Town square pavement
		new PavedPathway(Level.squares[AreaTown.posX + 23][AreaTown.posY + 19],
				Level.squares[AreaTown.posX + 24][AreaTown.posY + 19],
				Level.squares[AreaTown.posX + 25][AreaTown.posY + 19],
				Level.squares[AreaTown.posX + 22][AreaTown.posY + 20],
				Level.squares[AreaTown.posX + 23][AreaTown.posY + 20],
				Level.squares[AreaTown.posX + 24][AreaTown.posY + 20],
				Level.squares[AreaTown.posX + 25][AreaTown.posY + 20],
				Level.squares[AreaTown.posX + 26][AreaTown.posY + 20],
				Level.squares[AreaTown.posX + 22][AreaTown.posY + 21],
				Level.squares[AreaTown.posX + 23][AreaTown.posY + 21],
				Level.squares[AreaTown.posX + 24][AreaTown.posY + 21],
				Level.squares[AreaTown.posX + 25][AreaTown.posY + 21],
				Level.squares[AreaTown.posX + 26][AreaTown.posY + 21],
				Level.squares[AreaTown.posX + 22][AreaTown.posY + 22],
				Level.squares[AreaTown.posX + 23][AreaTown.posY + 22],
				Level.squares[AreaTown.posX + 24][AreaTown.posY + 22],
				Level.squares[AreaTown.posX + 25][AreaTown.posY + 22],
				Level.squares[AreaTown.posX + 26][AreaTown.posY + 22],
				Level.squares[AreaTown.posX + 23][AreaTown.posY + 23],
				Level.squares[AreaTown.posX + 24][AreaTown.posY + 23],
				Level.squares[AreaTown.posX + 25][AreaTown.posY + 23]);

		// Path left of fountain
		new PavedPathway(AreaTown.posX + 4, AreaTown.posY + 21, AreaTown.posX + 21, AreaTown.posY + 21);

		// Path to shop
		new PavedPathway(AreaTown.posX + 4, AreaTown.posY + 4, AreaTown.posX + 4, AreaTown.posY + 21);

		// Path to farm
		new PavedPathway(AreaTown.posX + 4, AreaTown.posY + 21, AreaTown.posX + 4, AreaTown.posY + 62);
		new PavedPathway(AreaTown.posX + 4, AreaTown.posY + 62, AreaTown.posX + 18, AreaTown.posY + 62);
		new PavedPathway(AreaTown.posX + 18, AreaTown.posY + 62, AreaTown.posX + 18, AreaTown.posY + 68);

		// Signpost 5,20
		GameObject signpost = Templates.SIGNPOST.makeCopy(Game.level.squares[AreaTown.posX + 5][AreaTown.posY + 20],
				null, AreaTown.joesShop, QuestThePigs.farmHouseStructure, AreaTown.doctorsShop, AreaList.townForest);

		// "North - Shop, Hunter's Lodge; East - Estates; South - Farm",

		// Trees around town square
		Templates.TREE.makeCopy(Game.level.squares[AreaTown.posX + 22][AreaTown.posY + 19], kidnapper);
		Templates.TREE.makeCopy(Game.level.squares[AreaTown.posX + 22][AreaTown.posY + 23], kidnapper);
		Templates.TREE.makeCopy(Game.level.squares[AreaTown.posX + 26][AreaTown.posY + 19], kidnapper);
		Templates.TREE.makeCopy(Game.level.squares[AreaTown.posX + 26][AreaTown.posY + 23], kidnapper);

		conversationForKidnapper = new ConversationForKidnapper(this);

		links = TextUtils.getLinks(true, this);

		objectiveKidnapper = new Objective("John", kidnapper, null, kidnapper.armImageTexture);
		objectiveChild = new Objective("John", kidnapper, null, kidnapper.armImageTexture);

	}

	@Override
	public void update() {
		if (resolved)
			return;

		super.update();

	}

	@Override
	public boolean update(Actor actor) {
		return false;
	}

	@Override
	public Conversation getConversation(Actor actor) {
		if (resolved)
			return null;
		if (actor == kidnapper) {
			return getConversationForKidnapper(actor);
		}
		return null;
	}

	private Conversation getConversationForKidnapper(Actor actor) {
		conversationForKidnapper.setup();
		return conversationForKidnapper;
	}

}
