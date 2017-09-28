package com.marklynch.level.quest;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.adventurelog.AdventureInfo;
import com.marklynch.level.constructs.adventurelog.Objective;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.popups.PopupToast;

public class Quest {

	public String name;
	public ArrayList<Objective> currentObjectives = new ArrayList<Objective>();
	public ArrayList<AdventureInfo> infoList = new ArrayList<AdventureInfo>();
	public ArrayList<Conversation> conversationLog = new ArrayList<Conversation>();
	public boolean started = false;
	public boolean resolved = false;
	public int turnStarted;
	public int turnUpdated;

	// Called once per cycle
	public void update() {

	}

	// Called my members of quest when they dont know what to do
	public boolean update(Actor actor) {
		return false;
	}

	public Conversation getConversation(Actor actor) {
		return null;
	}

	public void addObjective(Objective objective) {
		if (!currentObjectives.contains(objective)) {
			currentObjectives.add(objective);
		}
	}

	public void addInfo(AdventureInfo info) {
		if (!infoList.contains(info)) {
			info.setTurn(Game.level.turn);
			infoList.add(info);
		}

	}

	public void start() {
		started = true;
		Game.level.popupToasts.add(new PopupToast(new Object[] { "Quest " + name + " started!" }));
		turnStarted = turnUpdated = Level.turn;
	}

}
