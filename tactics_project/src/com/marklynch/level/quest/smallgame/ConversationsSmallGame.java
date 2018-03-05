package com.marklynch.level.quest.smallgame;

import com.marklynch.Game;
import com.marklynch.level.constructs.journal.QuestList;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.conversation.LeaveConversationListener;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionGiveItems;
import com.marklynch.objects.units.Actor;

public class ConversationsSmallGame {

	static QuestSmallGame quest;

	// Conversations for bill
	public static Conversation conversationRangerImNotSpying;
	public static Conversation conversationRangerSaveTheWolf;

	// Conversations for hunter
	public static Conversation conversationHuntersJoinTheHunt;
	public static Conversation conversationHuntersReadyToGo;
	public static Conversation conversationHuntersOnlyHuntersGetLoot;

	// Wolves
	public static Conversation conversationWolfTheyPlot;
	public static Conversation conversationWolfTheyCome;
	public static Conversation conversationWolfISurvivedNoThanksToYou;
	public static Conversation conversationWolfThankYou;

	public static void createConversations() {
		// Bill
		setUpConversationImNotSpying();
		setUpConversationSaveTheWolf();

		// Hunters
		setUpConversationReady();
		setUpConversationHunterOpening();
		setUpConversationYouDidntHelp();

		// Wolves
		setUpConversationTheyPlot();
		setUpConversationTheyCome();
		setUpConversationISurvivedNoThanksToYou();
		setUpConversationThankYou();
	}

	private static void setUpConversationThankYou() {
		conversationWolfThankYou = quest.superWolf.createConversation("Thank you");
		conversationWolfThankYou.openingConversationPart.leaveConversationListener = new LeaveConversationListener() {
			@Override
			public void leave() {
				for (Actor wolf : quest.wolfPack.getMembers()) {
					wolf.thoughtsOnPlayer = 100;
				}
				quest.resolve(quest.wolfPack.getLeader().squareGameObjectIsOn);
			}
		};
		conversationWolfThankYou.openingConversationPart.quests.add(quest);
	}

	private static void setUpConversationISurvivedNoThanksToYou() {
		conversationWolfISurvivedNoThanksToYou = quest.superWolf.createConversation("We survived without your aid.");
		conversationWolfISurvivedNoThanksToYou.openingConversationPart.leaveConversationListener = new LeaveConversationListener() {
			@Override
			public void leave() {
				quest.resolve(quest.wolfPack.getLeader().squareGameObjectIsOn);
			}
		};
		conversationWolfISurvivedNoThanksToYou.openingConversationPart.quests.add(quest);

	}

	private static void setUpConversationTheyCome() {
		conversationWolfTheyCome = quest.superWolf.createConversation("They come");
		conversationWolfTheyCome.openingConversationPart.quests.add(quest);
	}

	private static void setUpConversationTheyPlot() {
		conversationWolfTheyPlot = quest.superWolf.createConversation("They plot");
		conversationWolfTheyPlot.openingConversationPart.leaveConversationListener = new LeaveConversationListener() {
			@Override
			public void leave() {
				quest.addJournalLog(quest.journalLogSeenWolves);
				quest.addJournalLog(quest.journalLogTalkedToWolves);
				quest.addObjective(quest.objectiveWolves);
				quest.addObjective(quest.objectiveHunters);
			}
		};
		conversationWolfTheyPlot.openingConversationPart.quests.add(quest);
	}

	public static void setUpConversationHunterOpening() {

		ConversationPart conversationPartTheresEquipment = new ConversationPart(
				new Object[] {
						"There's should be some spare equipment 'round back, help yourself! Joe runs a shop to the North if you think you need anything else. Let us know when you're ready." },
				new ConversationResponse[] {}, quest.hunterPack.getLeader(), quest);

		ConversationPart conversationPartSuitYourself = new ConversationPart(new Object[] { "Suit yourself." },
				new ConversationResponse[] {}, quest.hunterPack.getLeader(), quest);

		ConversationResponse conversationResponseNoThanks = new ConversationResponse("No thanks",
				conversationPartSuitYourself) {
			@Override
			public void select() {
				super.select();
				// ADD QUEST TO QUEST LOG IF NO IN HARDCORE MODE
				// THIS ALSO COMES WITH A TOAST / POPUP SAYING "QUEST STARTED -
				// PACK HUNTERS"

				quest.addJournalLog(quest.journalLogSeenHunters);
				quest.addObjective(quest.objectiveHunters);

			}
		};
		ConversationResponse conversationResponseYesPlease = new ConversationResponse("Yes please",
				conversationPartTheresEquipment) {
			@Override
			public void select() {
				super.select();
				// ADD QUEST TO QUEST LOG IF NO IN HARDCORE MODE
				// THIS ALSO COMES WITH A TOAST / POPUP SAYING "QUEST STARTED -
				// PACK HUNTERS"

				quest.addJournalLog(quest.journalLogSeenHunters);
				quest.addJournalLog(quest.journalLogAgreedToJoinHunters);

				quest.addObjective(quest.objectiveHunters);
				// Add marker for weapons only if theyre on the square
				for (GameObject weapon : quest.weaponsBehindLodge) {
					if (quest.squareBehindLodge.inventory.contains(weapon)) {
						quest.addObjective(quest.objectiveWeaponsBehindLodge);
						weapon.owner = null;
					}
				}

			}
		};

		ConversationPart conversationPartWantToComeHunting = new ConversationPart(
				new Object[] { "We're just about to head out on the hunt, an extra man wouldn't go amiss." },
				new ConversationResponse[] { conversationResponseYesPlease, conversationResponseNoThanks },
				quest.hunterPack.getLeader(), quest);

		conversationHuntersJoinTheHunt = new Conversation(conversationPartWantToComeHunting,
				quest.hunterPack.getLeader(), true);

	}

	private static void setUpConversationImNotSpying() {

		// Environmentalist could have emoticon over his head showing his
		// feelings
		// Anime style
		// try it out
		ConversationPart conversationPartImNotSpying = new ConversationPart(
				new Object[] { "What? NO! I'm not spying! You're spying!", quest.superWolf, quest.hunterBrent,
						quest.rangerBill },
				new ConversationResponse[] {}, quest.rangerBill, quest);

		conversationPartImNotSpying.leaveConversationListener = new LeaveConversationListener() {

			@Override
			public void leave() {
				quest.addJournalLog(quest.journalLogRangerWasSpying);
				quest.addObjective(quest.objectiveEnvironmentalist);
				quest.addObjective(quest.objectiveHunters);

			}

		};

		conversationRangerImNotSpying = new Conversation(conversationPartImNotSpying,
				quest.hunterPack.getLeader(), true);
	}

	private static void setUpConversationSaveTheWolf() {

		ConversationPart conversationPartSaveTheWolf = new ConversationPart(
				new Object[] {
						"You can't take part in this. She's not just a normal wolf, she's an intelligent being. Smarter than those knuckleheads that want to mount her on a wall anyway." },
				new ConversationResponse[] {}, quest.rangerBill, quest);
		// Would like to use the word sentient or something for the above

		conversationPartSaveTheWolf.leaveConversationListener = new LeaveConversationListener() {

			@Override
			public void leave() {
				quest.addObjective(quest.objectiveEnvironmentalist);
				for (GameObject gameObject : quest.weaponsBehindLodge) {
					if (quest.rangerBill.inventory.contains(gameObject)) {
						new ActionGiveItems(quest.rangerBill, Game.level.player, false, gameObject).perform();

					}
				}
				if (quest.haveJournalLog(quest.journalLogRangerWasSpying)) {
					quest.addJournalLog(quest.journalLogSaveTheWolfVariant2);
				} else {
					quest.addJournalLog(quest.journalLogSaveTheWolfVariant1);
				}
			}

		};
		conversationRangerSaveTheWolf = new Conversation(conversationPartSaveTheWolf,
				quest.hunterPack.getLeader(), true);

	}

	public static void setUpConversationReady() {

		ConversationPart conversationAlrightLetsGo = new ConversationPart(
				new Object[] {
						"Alright! The cave is to the east, past the forest, at the entrance to a now defunct mining operation." },
				new ConversationResponse[] {}, quest.hunterPack.getLeader(), quest, QuestList.questCaveOfTheBlind);

		ConversationPart conversationPartWellHurryOn = new ConversationPart(new Object[] { "Well hurry on!" },
				new ConversationResponse[] {}, quest.hunterPack.getLeader(), quest);

		ConversationResponse conversationResponseNotYet = new ConversationResponse("Not yet",
				conversationPartWellHurryOn);
		ConversationResponse conversationResponseReady = new ConversationResponse("Ready!", conversationAlrightLetsGo) {
			@Override
			public void select() {
				super.select();
				// Update quest log
				// Set ranger to come watch
				// Hunters on the way
				quest.addObjective(quest.objectiveWolves);
				if (Game.level.fullQuestList.questCaveOfTheBlind.started == false) {
					Game.level.fullQuestList.questCaveOfTheBlind
							.addObjective(Game.level.fullQuestList.questCaveOfTheBlind.objectiveCave);
					Game.level.fullQuestList.questCaveOfTheBlind
							.addObjective(Game.level.fullQuestList.questCaveOfTheBlind.objectiveHunters);
					Game.level.fullQuestList.questCaveOfTheBlind
							.addJournalLog(Game.level.fullQuestList.questCaveOfTheBlind.journalLogHeardFromHunters);
				}
				quest.addJournalLog(quest.journalLogSetOffWithHunters);
			}
		};

		ConversationPart conversationPartReadyToGo = new ConversationPart(new Object[] { "Ready to go, pal?" },
				new ConversationResponse[] { conversationResponseReady, conversationResponseNotYet },
				quest.hunterPack.getLeader(), quest);

		conversationHuntersReadyToGo = new Conversation(conversationPartReadyToGo, quest.hunterPack.getLeader(), true);

	}

	private static void setUpConversationYouDidntHelp() {
		// Really like the "Now fuck off!" bit.
		ConversationPart conversationPartOnlyHuntersGetLoot = new ConversationPart(
				new Object[] { "Only hunters get loot. Now fuck off!" }, new ConversationResponse[] {},
				quest.hunterPack.getLeader(), quest);
		conversationPartOnlyHuntersGetLoot.leaveConversationListener = new LeaveConversationListener() {

			@Override
			public void leave() {
				quest.addJournalLog(quest.journalLogToldToFuckOffByHunters);
				quest.resolve(quest.hunterPack.getLeader().squareGameObjectIsOn);
			}
		};

		conversationHuntersOnlyHuntersGetLoot = new Conversation(conversationPartOnlyHuntersGetLoot,
				quest.hunterPack.getLeader(), true);

	}

}
