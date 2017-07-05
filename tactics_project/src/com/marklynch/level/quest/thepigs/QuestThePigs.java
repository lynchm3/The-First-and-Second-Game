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
import com.marklynch.objects.Door;
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
		larry = Templates.PIG.makeCopy("Larry", Game.level.squares[30][76], Game.level.factions.get(1), null);
		larry.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		wendy = Templates.PIG.makeCopy("Wendy", Game.level.squares[39][74], Game.level.factions.get(1), null);
		wendy.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		jane = Templates.PIG.makeCopy("Jane", Game.level.squares[34][78], Game.level.factions.get(1), null);
		jane.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		steve = Templates.PIG.makeCopy("Steve", Game.level.squares[35][74], Game.level.factions.get(1), null);
		steve.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		prescilla = Templates.PIG.makeCopy("Prescilla", Game.level.squares[31][80], Game.level.factions.get(1), null);
		prescilla.inventory.add(Templates.CLEAVER.makeCopy(null, null));

		// trees
		// cute, larry owns the tree
		Templates.TREE.makeCopy(Game.level.squares[30][75], larry);
		Templates.TREE.makeCopy(Game.level.squares[32][79], larry);
		Templates.TREE.makeCopy(Game.level.squares[38][74], larry);

		// Bow on the ground
		Templates.HUNTING_BOW.makeCopy(Game.level.squares[33][73], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[34][73], null);
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

		// gate
		Door gate = Templates.GATE.makeCopy("Gate", Game.level.squares[32][72], false, larry);
		farmFeatures.add(gate);

		penSection = new StructureSection("Pen", 28, 72, 46, 82, false, larry);
		penRoom = new StructureRoom("Pen", 29, 73, false, new ArrayList<Actor>(), new RoomPart(29, 73, 45, 81));

		farmSections.add(penSection);
		farmRooms.add(penRoom);

		Game.level.structures
				.add(new Structure("Pen", farmSections, farmRooms, farmPaths, farmFeatures, new ArrayList<Square>(),
						null, 0, 0, 0, 0, true, larry, squaresToRemove, farmWalls, Templates.FENCE, "mud.png"));
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
