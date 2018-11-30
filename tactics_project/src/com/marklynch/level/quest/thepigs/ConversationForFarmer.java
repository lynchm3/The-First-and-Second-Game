package com.marklynch.level.quest.thepigs;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.actors.Human;

public class ConversationForFarmer extends Conversation {

	// Opening
	ConversationPart conversationPartopening;

	// Opening responses
	ConversationResponse conversationResponseHi;
	ConversationResponse conversationResponseHowsTheFarm;
	ConversationResponse conversationResponseSeenAnyDemons;

	// First level Answers
	ConversationPart conversationPartHowsItGoing;
	ConversationPart conversationPartCantComplain;
	ConversationPart conversationPartNoDemonsAroundHere;

	// Response to hows it going
	ConversationResponse conversationResponseImGood;

	// Well thanks
	ConversationPart conversationPartWellThanks;

	QuestThePigs questThePigs;
	Human farmer;

	public ConversationForFarmer(final QuestThePigs questThePigs, Human farmer) {
		super(null, farmer, true);
		this.questThePigs = questThePigs;
		this.farmer = farmer;

		// Opening
		conversationPartopening = new ConversationPart(new Object[] { "Hello there!" }, new ConversationResponse[] {},
				farmer, this.questThePigs);

		// General responses
		conversationResponseHi = new ConversationResponse("Hi!", null);
		conversationResponseHowsTheFarm = new ConversationResponse("How's the farm?", null);
		conversationResponseSeenAnyDemons = new ConversationResponse("Seen any demons about?", null);

		// First level parts
		conversationPartHowsItGoing = new ConversationPart(new Object[] { "How's it going?" },
				new ConversationResponse[] {}, farmer, this.questThePigs);

		conversationPartCantComplain = new ConversationPart(
				new Object[] { "Can't complain, I've got me pigs and me turnips and me lovely wife." },
				new ConversationResponse[] {}, farmer, this.questThePigs);

		conversationPartNoDemonsAroundHere = new ConversationPart(new Object[] { "Nope, can't say that I have." },
				new ConversationResponse[] {}, farmer, this.questThePigs);

		// I'm good, I like your farm!"
		conversationResponseImGood = new ConversationResponse("I'm good, I like your farm!", null);

		// Well Thanks!
		conversationPartWellThanks = new ConversationPart(new Object[] { "Well Thanks!" },
				new ConversationResponse[] {}, farmer, this.questThePigs);

		// NEEDS TO BE ADDED
		this.openingConversationPart = this.currentConversationPart = conversationPartopening;
	}

	public void setup() {

		// FILL IN POINTERS
		// opening
		conversationPartopening.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons });

		// Hello there
		conversationResponseHi.nextConversationPart = conversationPartHowsItGoing;
		conversationResponseHowsTheFarm.nextConversationPart = conversationPartCantComplain;
		conversationResponseSeenAnyDemons.nextConversationPart = conversationPartNoDemonsAroundHere;

		// Farmer - how's it goinf
		conversationPartHowsItGoing.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons });

		// You're in my mine
		conversationPartCantComplain.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons });

		// I'm Mort
		conversationPartNoDemonsAroundHere.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons });

		this.currentConversationPart = this.openingConversationPart;
	}

}
