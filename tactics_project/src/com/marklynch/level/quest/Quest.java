package com.marklynch.level.quest;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.level.constructs.journal.JournalLog;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.Link;
import com.marklynch.ui.popups.Notification;

public class Quest {

	public long id;
	public String name;
	public ArrayList<Objective> allObjectives = new ArrayList<Objective>();
	public ArrayList<Objective> currentObjectives = new ArrayList<Objective>();
	public ArrayList<JournalLog> logList = new ArrayList<JournalLog>();
	public ArrayList<ConversationPart> conversationLog = new ArrayList<ConversationPart>();
	public boolean started = false;
	public boolean resolved = false;
	public int turnStarted;
	public int turnUpdated;
	public boolean updatedSinceLastViewed;
	public ArrayList<Link> links;

	// Called my members of quest when they dont know what to do
	public boolean update(Actor actor) {
		return false;
	}

	public Conversation getConversation(Actor actor) {
		return null;
	}

	public void update() {
		for (Objective objective : allObjectives) {
			if (objective.gameObject != null) {
				if (objective.gameObject.remainingHealth <= 0) {
					if (objective.gameObject.squareGameObjectIsOn.visibleToPlayer) {
						objective.objectiveDestroyedAndWitnessed = true;
					}
				}
			}
		}
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
			Journal.createButtonsForTrackedStuffInTopRight();
		}
	}

	public boolean haveObjective(Objective objective) {
		return currentObjectives.contains(objective);
	}

	public void addJournalLog(JournalLog journalLog) {

		if (resolved)
			return;

		if (!logList.contains(journalLog)) {

			if (started) {
				hasBeenUpdated();
			} else {
				start();
			}

			journalLog.setTurn(Game.level.turn);
			journalLog.setSquare(Game.level.player.squareGameObjectIsOn);
			journalLog.setArea(Game.level.player.squareGameObjectIsOn.areaSquareIsIn);
			logList.add(journalLog);
		}

	}

	public void addConversationPart(ConversationPart conversationPart) {

		if (resolved)
			return;

		if (!conversationLog.contains(conversationPart)) {

			conversationPart.setTurnAndSquareAndArea(Game.level.turn, Game.level.player.squareGameObjectIsOn,
					Game.level.player.squareGameObjectIsOn.areaSquareIsIn);
			conversationLog.add(conversationPart);
		}

	}

	public boolean haveJournalLog(JournalLog journalLog) {
		return logList.contains(journalLog);
	}

	public void start() {
		if (started || resolved)
			return;
		Journal.questsToTrack.add(this);
		started = true;
		Game.level.addNotification(new Notification(new Object[] { "Quest ", this, " started" },
				Notification.NotificationType.QUEST_STARTED, this));
		Game.level.activityLogger
				.addActivityLog(new ActivityLog(new Object[] { Game.level.player, " started quest ", this }));
		turnStarted = turnUpdated = Level.turn;
		updatedSinceLastViewed = true;
	}

	boolean isFirstQuest() {
		for (Quest quest : Level.fullQuestList) {
			if (quest.started)
				return false;
		}
		return true;
	}

	public void hasBeenUpdated() {

		if (resolved)
			return;

		if (turnUpdated < Level.turn) {
			Game.level.addNotification(new Notification(new Object[] { "Quest ", this, " updated" },
					Notification.NotificationType.QUEST_UPDATED, this));
			Game.level.activityLogger.addActivityLog(new ActivityLog(new Object[] { "Quest ", this, " was updated" }));
			turnUpdated = Level.turn;
			updatedSinceLastViewed = true;
		}
	}

	public void resolve(Square square) {
		resolved = true;
		Game.level.addNotification(new Notification(new Object[] { "Quest ", this, " resolved!" },
				Notification.NotificationType.QUEST_RESOLVED, this));
		Game.level.player.addXP(15, square);
	}

}
