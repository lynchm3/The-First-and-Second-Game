package com.marklynch.level.quest;

import java.util.ArrayList;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.units.Actor;

public class Quest {

	public String name;
	public ArrayList<String> currentObjectives = new ArrayList<String>();
	public ArrayList<String> objectives = new ArrayList<String>();
	public ArrayList<Object> info = new ArrayList<Object>();
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

}
