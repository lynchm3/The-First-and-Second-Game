package com.marklynch.level.constructs.journal;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.puzzleroom.PuzzleRoomMovingBridge;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.quest.betweenthewalls.QuestBetweenTheWalls;
import com.marklynch.level.quest.caveoftheblind.QuestCaveOfTheBlind;
import com.marklynch.level.quest.smallgame.QuestSmallGame;
import com.marklynch.level.quest.thepigs.QuestThePigs;
import com.marklynch.level.quest.thesecretroom.QuestTheSecretRoom;

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

		new PuzzleRoomMovingBridge();
	}

	// public void update() {
	// for (Quest quest : this) {
	//
	// }
	// }

}
