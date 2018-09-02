package com.marklynch.level.constructs.journal;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.puzzleroom.PuzzleRoomCrumblingWall;
import com.marklynch.level.constructs.bounds.structure.puzzleroom.PuzzleRoomMineCart;
import com.marklynch.level.constructs.bounds.structure.puzzleroom.PuzzleRoomMineCart2;
import com.marklynch.level.constructs.bounds.structure.puzzleroom.PuzzleRoomMovingBridge;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.quest.betweenthewalls.QuestBetweenTheWalls;
import com.marklynch.level.quest.caveoftheblind.QuestCaveOfTheBlind;
import com.marklynch.level.quest.smallgame.QuestSmallGame;
import com.marklynch.level.quest.thepigs.QuestThePigs;
import com.marklynch.level.quest.thesecretroom.QuestTheSecretRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Wall;
import com.marklynch.objects.templates.Templates;

@SuppressWarnings("serial")
public class QuestList extends ArrayList<Quest> {
	public static QuestSmallGame questSmallGame;
	public static QuestCaveOfTheBlind questCaveOfTheBlind;
	public static QuestThePigs questThePigs;
	public static QuestBetweenTheWalls questBetweenTheWalls;
	public static QuestTheSecretRoom questTheSecretRoom;

	public QuestList() {

	}

	public void makeQuests() {

		questCaveOfTheBlind = new QuestCaveOfTheBlind();
		add(questCaveOfTheBlind);

		questSmallGame = new QuestSmallGame();
		add(questSmallGame);
		questCaveOfTheBlind.objectiveHunters = new Objective("The Hunters", questSmallGame.hunterBrent, null,
				questSmallGame.hunterBrent.imageTexture);

		questThePigs = new QuestThePigs();
		Level.fullQuestList.add(questThePigs);

		questBetweenTheWalls = new QuestBetweenTheWalls(10, 10);
		Level.fullQuestList.add(questBetweenTheWalls);

		questTheSecretRoom = new QuestTheSecretRoom(10, 10);
		Level.fullQuestList.add(questTheSecretRoom);

		// PUZZLE ROOMS
		int puzzleRoomsX = 100;
		int puzzleRoomsY = 100;

		ArrayList<StructurePath> paths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>();
		structureSections.add(new StructureSection("Puzzle Structure Section", puzzleRoomsX, puzzleRoomsY, puzzleRoomsX + 100, puzzleRoomsY + 100, false));
		ArrayList<StructureRoom> puzzleStructureRooms = new ArrayList<StructureRoom>();
		puzzleStructureRooms.add(new PuzzleRoomMovingBridge(puzzleRoomsX + 1, puzzleRoomsY + 1));
		puzzleStructureRooms.add(new PuzzleRoomMineCart(puzzleRoomsX + 1, puzzleRoomsY + 22));
		puzzleStructureRooms.add(new PuzzleRoomMineCart2(puzzleRoomsX + 15, puzzleRoomsY + 30));
		puzzleStructureRooms.add(new PuzzleRoomCrumblingWall(puzzleRoomsX + 22, puzzleRoomsY + 10));
		ArrayList<StructureFeature> features = new ArrayList<StructureFeature>();
		ArrayList<Square> entrances = new ArrayList<Square>();
		entrances.add(Level.squares[puzzleRoomsX][puzzleRoomsY + 10]);
		ArrayList<Square> squaresToRemove = new ArrayList<Square>();
		// top left entrance
		squaresToRemove.add(Level.squares[puzzleRoomsX][puzzleRoomsY + 10]);
		squaresToRemove.add(Level.squares[puzzleRoomsX][puzzleRoomsY + 11]);
		// entrance between bridge nad minecart
		squaresToRemove.add(Level.squares[puzzleRoomsX + 10][puzzleRoomsY + 21]);
		squaresToRemove.add(Level.squares[puzzleRoomsX + 11][puzzleRoomsY + 21]);
		// minecart 1 to minecart 2
		squaresToRemove.add(Level.squares[puzzleRoomsX + 14][puzzleRoomsY + 41]);
		// 2nd top left entrance
		// squaresToRemove.add(Level.squares[x][y + 31]);
		// squaresToRemove.add(Level.squares[x][y + 32]);
		ArrayList<Wall> extraWalls = new ArrayList<Wall>();
		extraWalls.add(Templates.FALSE_WALL.makeCopy(Level.squares[puzzleRoomsX + 1 + 1][puzzleRoomsY + 22 + 0], null));

		Structure puzzleStructure = new Structure("Puzzle Structure", structureSections, puzzleStructureRooms, paths,
				features, entrances, "building2.png", puzzleRoomsX, puzzleRoomsY, puzzleRoomsX + 100, puzzleRoomsY + 100, true, null, squaresToRemove, extraWalls,
				Templates.WALL, Square.STONE_TEXTURE, 10);

	}

	// public void update() {
	// for (Quest quest : this) {
	//
	// }
	// }

}
