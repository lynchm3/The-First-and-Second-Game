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

		questBetweenTheWalls = new QuestBetweenTheWalls();
		Level.fullQuestList.add(questBetweenTheWalls);

		questTheSecretRoom = new QuestTheSecretRoom();
		Level.fullQuestList.add(questTheSecretRoom);

		// PUZZLE ROOMS
		int x = 100;
		int y = 100;

		ArrayList<StructurePath> paths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>();
		structureSections.add(new StructureSection("Puzzle Structure Section", x, y, x + 100, y + 100, false));
		ArrayList<StructureRoom> puzzleStructureRooms = new ArrayList<StructureRoom>();
		puzzleStructureRooms.add(new PuzzleRoomMovingBridge(x + 1, y + 1));
		puzzleStructureRooms.add(new PuzzleRoomMineCart(x + 1, y + 22));
		puzzleStructureRooms.add(new PuzzleRoomCrumblingWall(x + 22, y + 10));
		ArrayList<StructureFeature> features = new ArrayList<StructureFeature>();
		ArrayList<Square> entrances = new ArrayList<Square>();
		entrances.add(Level.squares[x][y + 10]);
		ArrayList<Square> squaresToRemove = new ArrayList<Square>();
		// top left entrance
		squaresToRemove.add(Level.squares[x][y + 10]);
		squaresToRemove.add(Level.squares[x][y + 11]);
		// entrance between bridge nad minecart
		squaresToRemove.add(Level.squares[x + 10][y + 21]);
		squaresToRemove.add(Level.squares[x + 11][y + 21]);
		// minecart 1 to minecart 2
		squaresToRemove.add(Level.squares[x + 14][y + 41]);
		// 2nd top left entrance
		// squaresToRemove.add(Level.squares[x][y + 31]);
		// squaresToRemove.add(Level.squares[x][y + 32]);
		ArrayList<Wall> extraWalls = new ArrayList<Wall>();

		Structure puzzleStructure = new Structure("Puzzle Structure", structureSections, puzzleStructureRooms, paths,
				features, entrances, "building2.png", x, y, x + 100, y + 100, true, null, squaresToRemove, extraWalls,
				Templates.WALL, Square.STONE_TEXTURE, 10);

	}

	// public void update() {
	// for (Quest quest : this) {
	//
	// }
	// }

}
