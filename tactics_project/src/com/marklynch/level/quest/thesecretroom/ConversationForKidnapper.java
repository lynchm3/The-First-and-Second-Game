package com.marklynch.level.quest.thesecretroom;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;

public class ConversationForKidnapper extends Conversation {

	// Opening
	ConversationPart conversationPartopening1;
	ConversationPart conversationPartopening2;

	// Opening responses
	ConversationResponse conversationResponseWhatsTheFavour;

	// The Favour
	ConversationPart conversationPartTheFavour;

	// I guess so
	ConversationResponse conversationResponseIGuessSo;

	// Why don't you do it?

	QuestTheSecretRoom questTheSecretRoom;

	public ConversationForKidnapper(final QuestTheSecretRoom questTheSecretRoom) {
		super(null, questTheSecretRoom.kidnapper, true);
		this.questTheSecretRoom = questTheSecretRoom;

		conversationPartopening1 = new ConversationPart(new Object[] { "Hello there! Could you do me a favour?" },
				new ConversationResponse[] {}, questTheSecretRoom.kidnapper, this.questTheSecretRoom) {
			@Override
			public void shown() {
				super.shown();
				System.out.println("SHOWN() 1");
				questTheSecretRoom.addJournalLog(questTheSecretRoom.journalLogMetKidnapper);
				questTheSecretRoom.addObjective(questTheSecretRoom.objectiveKidnapper);

			}
		};

		conversationPartopening2 = new ConversationPart(new Object[] { "Hello there!" }, // annoyed
				new ConversationResponse[] {}, questTheSecretRoom.kidnapper, this.questTheSecretRoom);

		conversationResponseWhatsTheFavour = new ConversationResponse("What's the favour?", null);

		conversationPartTheFavour = new ConversationPart(new Object[] {
				"My son, he's out playing near the pond to the east of town. Could you fetch him for me? I always have awful trouble getting him to come in in the evenings, if he gives you trouble you can bribe him with this chocolate. Does the trick every time!" },
				new ConversationResponse[] {}, questTheSecretRoom.kidnapper, this.questTheSecretRoom) {
			@Override
			public void shown() {
				super.shown();
				System.out.println("SHOWN() 2");
				questTheSecretRoom.addJournalLog(questTheSecretRoom.journalLogTheFavour);
				questTheSecretRoom.addObjective(questTheSecretRoom.objectiveChild);

			}
		};

		conversationResponseIGuessSo = new ConversationResponse("Eh... I guess so", null);
	}

	public void setup() {

		// FILL IN POINTERS
		// opening

		ConversationPart conversationPartopening;

		if (!this.questTheSecretRoom.haveJournalLog(this.questTheSecretRoom.journalLogMetKidnapper)) {
			conversationPartopening1
					.setConversationResponses(new ConversationResponse[] { conversationResponseWhatsTheFavour });
			conversationPartopening = conversationPartopening1;
		} else {
			conversationPartopening2
					.setConversationResponses(new ConversationResponse[] { conversationResponseWhatsTheFavour });
			conversationPartopening = conversationPartopening2;

		}

		// opening responses
		conversationResponseWhatsTheFavour.nextConversationPart = conversationPartTheFavour;

		// I'm Kidnapper
		conversationPartTheFavour.setConversationResponses(new ConversationResponse[] { conversationResponseIGuessSo });

		this.openingConversationPart = this.currentConversationPart = conversationPartopening;
	}

}
