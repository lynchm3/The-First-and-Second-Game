package com.marklynch.level.constructs.adventurelog;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.quest.betweenthewalls.QuestBetweenTheWalls;
import com.marklynch.level.quest.caveoftheblind.QuestCaveOfTheBlind;
import com.marklynch.level.quest.smallgame.QuestSmallGame;
import com.marklynch.level.quest.thepigs.QuestThePigs;
import com.marklynch.level.quest.thesecretroom.QuestTheSecretRoom;

@SuppressWarnings("serial")
public class QuestList extends ArrayList<Quest> {
	public QuestSmallGame questSmallGame;
	public QuestCaveOfTheBlind questCaveOfTheBlind;
	public QuestThePigs questThePigs;
	public QuestBetweenTheWalls questBetweenTheWalls;
	public QuestTheSecretRoom questTheSecretRoom;

	public QuestList() {

	}

	public void makeQuests() {

		questSmallGame = new QuestSmallGame();
		add(questSmallGame);
		AdventureLog.activeQuest = questSmallGame;
		AdventureLog.questToDisplayInAdventureLog = questSmallGame;

		questCaveOfTheBlind = new QuestCaveOfTheBlind();
		add(questCaveOfTheBlind);

		questThePigs = new QuestThePigs();
		Level.quests.add(questThePigs);

		questBetweenTheWalls = new QuestBetweenTheWalls();
		Level.quests.add(questBetweenTheWalls);

		questTheSecretRoom = new QuestTheSecretRoom();
		Level.quests.add(questTheSecretRoom);
	}

}
