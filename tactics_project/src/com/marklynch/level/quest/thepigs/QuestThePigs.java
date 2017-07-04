package com.marklynch.level.quest.thepigs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructurePath;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Wall;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Pig;

public class QuestThePigs extends Quest {

	// Structure sections
	StructureSection penSection;
	StructureRoom penRoom;

	// Actors
	Pig larry, wendy, jane, steve, prescilla;
	// Farmer farmer;

	// GameObjects
	// Mud mud;
	// DungPile dungPile;
	// Trough trough;
	// Swill swill;
	Tree tree;

	// Conversations
	ConversationForFarmer conversationForFarmer;

	public QuestThePigs() {
		super();

		// Pigs
		larry = Templates.PIG.makeCopy(Game.level.squares[30][74], Game.level.factions.get(1), null);

		// tree
		// cute, larry owns the tree
		tree = Templates.TREE.makeCopy(Game.level.squares[30][75], larry);

		// Bed farmersBed = Templates.BED.makeCopy(Game.level.squares[3][3]);
		// farmersBed.quest = this;
		// farmer = Templates.FARMER.makeCopy(Game.level.squares[2][2],
		// Game.level.factions.get(1), farmersBed);
		// farmer.quest = this;}
		//
		// // Conversations
		// conversationForFarmer = new ConversationForFarmer(this);

		// Structure
		ArrayList<Wall> farmWalls = new ArrayList<Wall>();
		ArrayList<GameObject> farmFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> farmPaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> farmSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> farmRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> squaresToRemove = new ArrayList<Square>();

		penSection = new StructureSection("Pen", 28, 72, 46, 82, false, larry);
		penRoom = new StructureRoom("Pen", 29, 73, false, new ArrayList<Actor>(), new RoomPart(29, 73, 45, 81));

		farmSections.add(penSection);
		farmRooms.add(penRoom);

		Game.level.structures.add(new Structure("Farm", farmSections, farmRooms, farmPaths, farmFeatures,
				new ArrayList<Square>(), null, 0, 0, 0, 0, true, larry, squaresToRemove, farmWalls));
	}

	@Override
	public void update() {

	}

	@Override
	public boolean update(Actor actor) {
		return false;
	}

	@Override
	public Conversation getConversation(Actor actor) {
		// if (actor == farmer) {
		// conversationForFarmer.setup();
		// return conversationForFarmer;
		// }
		return null;
	}
}
