package com.marklynch.level.quest;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.journal.JournalLog;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.popups.Notification;

public class Quest {

	public String name;
	public ArrayList<Objective> currentObjectives = new ArrayList<Objective>();
	public ArrayList<JournalLog> logList = new ArrayList<JournalLog>();
	public ArrayList<ConversationPart> conversationLog = new ArrayList<ConversationPart>();
	public boolean started = false;
	public boolean resolved = false;
	public int turnStarted;
	public int turnUpdated;
	public boolean updatedSinceLastViewed;

	// Called my members of quest when they dont know what to do
	public boolean update(Actor actor) {
		return false;
	}

	public Conversation getConversation(Actor actor) {
		return null;
	}

	public void update() {

	}

	public void addObjective(Objective objective) {

		if (resolved)
			return;

		if (!currentObjectives.contains(objective)) {

			if (started) {
				hasBeenUpdated();
			} else {
				start();
			}
			currentObjectives.add(objective);
			Journal.createButtonsToTrackObjectives();
		}
	}

	public boolean haveObjective(Objective objective) {
		return currentObjectives.contains(objective);
	}

	public void addInfo(JournalLog info) {

		if (resolved)
			return;

		if (!logList.contains(info)) {

			if (started) {
				hasBeenUpdated();
			} else {
				start();
			}

			info.setTurn(Game.level.turn);
			info.setSquare(Game.level.player.squareGameObjectIsOn);
			info.setArea(Game.level.player.squareGameObjectIsOn.areaSquareIsIn);
			logList.add(info);
		}

	}

	public void addConversationPart(ConversationPart conversationPart) {

		if (resolved)
			return;

		if (!conversationLog.contains(conversationPart)) {

			conversationPart.setTurn(Game.level.turn);
			conversationPart.setSquare(Game.level.player.squareGameObjectIsOn);
			conversationPart.setArea(Game.level.player.squareGameObjectIsOn.areaSquareIsIn);
			conversationLog.add(conversationPart);
		}

	}

	public boolean haveInfo(JournalLog info) {
		return logList.contains(info);
	}

	public void start() {
		if (started || resolved)
			return;
		started = true;
		Game.level.notifications.add(new Notification(new Object[] { "Quest ", this, " started" }));
		Game.level.activityLogger
				.addActivityLog(new ActivityLog(new Object[] { Game.level.player, " started quest ", this }));
		turnStarted = turnUpdated = Level.turn;
		updatedSinceLastViewed = true;
	}

	public void hasBeenUpdated() {

		if (resolved)
			return;

		if (turnUpdated < Level.turn) {
			Game.level.notifications.add(new Notification(new Object[] { "Quest ", this, " updated" }));
			Game.level.activityLogger.addActivityLog(new ActivityLog(new Object[] { "Quest ", this, " was updated" }));
			turnUpdated = Level.turn;
			updatedSinceLastViewed = true;
		}
	}

	public void resolve() {
		resolved = true;
		Game.level.notifications.add(new Notification(new Object[] { "Quest ", this, " resolved!" }));
		Game.level.player.addXP(15);
	}

}
