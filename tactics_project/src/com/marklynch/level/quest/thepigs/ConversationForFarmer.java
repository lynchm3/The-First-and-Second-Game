package com.marklynch.level.quest.thepigs;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.units.Farmer;

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

	// End reponse
	ConversationResponse conversationReponseEnd;

	// Response to hows it going
	ConversationResponse conversationResponseImGood;

	// Well thanks
	ConversationPart conversationPartWellThanks;

	QuestThePigs questThePigs;
	Farmer farmer;

	public ConversationForFarmer(final QuestThePigs questCaveOfTheBlind, Farmer farmer) {
		super(null);
		this.questThePigs = questThePigs;
		this.farmer = farmer;

		// Opening
		conversationPartopening = new ConversationPart(new Object[] { "Hello there!" }, new ConversationResponse[] {},
				farmer);

		// General responses
		conversationResponseHi = new ConversationResponse("Hi!", null);
		conversationResponseHowsTheFarm = new ConversationResponse("How's the farm?", null);
		conversationResponseSeenAnyDemons = new ConversationResponse("Seen any demons about?", null);

		// First level parts
		conversationPartHowsItGoing = new ConversationPart(new Object[] { "How's it going?" },
				new ConversationResponse[] {}, farmer);

		conversationPartCantComplain = new ConversationPart(
				new Object[] { "Can't complain, I've got me pigs and me turnips and me lovely wife." },
				new ConversationResponse[] {}, farmer);

		conversationPartNoDemonsAroundHere = new ConversationPart(new Object[] { "Nope, can't say that I have." },
				new ConversationResponse[] {}, farmer);

		// End reponse
		conversationReponseEnd = new ConversationResponse("Leave", null);

		// I'm good, I like your farm!"
		conversationResponseImGood = new ConversationResponse("I'm good, I like your farm!", null);

		// Well Thanks!
		conversationPartWellThanks = new ConversationPart(new Object[] { "Well Thanks!" },
				new ConversationResponse[] {}, farmer);

		// NEEDS TO BE ADDED
		this.openingConversationPart = this.currentConversationPart = conversationPartopening;
	}

	public void setup() {

		// FILL IN POINTERS
		// opening
		conversationPartopening.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons, conversationReponseEnd });

		// Hello there
		conversationResponseHi.nextConversationPart = conversationPartHowsItGoing;
		conversationResponseHowsTheFarm.nextConversationPart = conversationPartCantComplain;
		conversationResponseSeenAnyDemons.nextConversationPart = conversationPartNoDemonsAroundHere;

		// Farmer - how's it goinf
		conversationPartHowsItGoing.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons, conversationReponseEnd });

		// You're in my mine
		conversationPartCantComplain.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons, conversationReponseEnd });

		// I'm Mort
		conversationPartNoDemonsAroundHere.setConversationResponses(new ConversationResponse[] { conversationResponseHi,
				conversationResponseHowsTheFarm, conversationResponseSeenAnyDemons, conversationReponseEnd });

		this.currentConversationPart = this.openingConversationPart;
	}

}
