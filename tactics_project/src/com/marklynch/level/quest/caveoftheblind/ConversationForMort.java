package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;

public class ConversationForMort extends Conversation {

	// Opening
	ConversationPart conversationPartopening;

	// Opening responses
	ConversationResponse conversationResponseTellMeAboutTheBlind;
	ConversationResponse conversationResponseWhereAmI;
	ConversationResponse conversationResponseWhoAreYou;

	// First level
	ConversationPart conversationPartTheBlind;
	ConversationPart conversationPartYoureInTheCaveOfTheBlind;
	ConversationPart conversationPartImMort;
	ConversationResponse conversationReponseEnd;

	// You feed them?
	ConversationResponse conversationResponseYouFeedThem;

	// Yup, wanna see?
	ConversationPart conversationPartYupWannaSee;

	// I already showed you
	ConversationPart conversationPartIAlreadyShowedYou;

	// What do you feed them?
	ConversationResponse conversationResponseWhatDoYouFeedThem;

	// Pun 1
	ConversationPart conversationPartPun1;

	// ... 1
	ConversationResponse conversationResponseDotDotDot1;

	// Pun 2
	ConversationPart conversationPartPun2;

	// ... 2
	ConversationResponse conversationResponseDotDotDot2;

	// Pun 3
	ConversationPart conversationPartPun3;

	// ... 3
	ConversationResponse conversationResponseDotDotDot3;

	// Pun 4
	ConversationPart conversationPartPun4;

	// ...4
	// ConversationResponse conversationResponseDotDotDot4 = new
	// ConversationResponse("...", null);

	// Show me
	ConversationResponse conversationResponseShowMe;

	// I'll show you
	ConversationPart illShowYou;

	QuestCaveOfTheBlind questCaveOfTheBlind;

	public ConversationForMort(final QuestCaveOfTheBlind questCaveOfTheBlind) {
		super(null);
		this.questCaveOfTheBlind = questCaveOfTheBlind;

		// Opening
		conversationPartopening = null;
		if (questCaveOfTheBlind.talkedToMort && questCaveOfTheBlind.playerMinedOres > 0) {
			// You mined his ore
			conversationPartopening = new ConversationPart(new Object[] { "Hands off my ore!" },
					new ConversationResponse[] {}, questCaveOfTheBlind.mort);
		} else if (questCaveOfTheBlind.talkedToMort) {
			conversationPartopening = new ConversationPart(new Object[] { "Yes?" }, // annoyed
					new ConversationResponse[] {}, questCaveOfTheBlind.mort);
		} else {
			// Havent talked yet, annoyed by your mere presence
			conversationPartopening = new ConversationPart(
					new Object[] {
							"What are you doing here? Hands off my ore! You should leave, would be terrible if the blind got you!" },
					new ConversationResponse[] {}, questCaveOfTheBlind.mort);
		}

		// General responses
		conversationResponseTellMeAboutTheBlind = new ConversationResponse("Tell me about the blind.", null);
		conversationResponseWhereAmI = new ConversationResponse("Where am I?", null);
		conversationResponseWhoAreYou = new ConversationResponse("Who are you?", null);

		// First level
		conversationPartTheBlind = new ConversationPart(
				new Object[] {
						"They can be pretty vicious. But as long as I feed them they keep to themselves. O... and... eh... they're blind. They weren't always mind, but they must have turned on eachother at some point, when the hunger got to them." },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		conversationPartYoureInTheCaveOfTheBlind = new ConversationPart(
				new Object[] { "You're in my mine, don't touch my ore" }, new ConversationResponse[] {},
				questCaveOfTheBlind.mort);

		conversationPartImMort = new ConversationPart(new Object[] { "I'm Mort, this is my mine, don't touch my ore." },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		conversationReponseEnd = new ConversationResponse("Leave", null);

		// You feed them?
		conversationResponseYouFeedThem = new ConversationResponse("You feed them?", null);

		// Yup, wanna see?
		conversationPartYupWannaSee = new ConversationPart(new Object[] { "Yup! ...you wanna see?" },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort); // pride?
																			// shifty?

		// I already showed you
		conversationPartIAlreadyShowedYou = new ConversationPart(new Object[] { "I already showed you!" },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		// What do you feed them?
		conversationResponseWhatDoYouFeedThem = new ConversationResponse("What do you feed them?", null);

		// Question - What do you feed them?
		// PUNS -
		// Blind/sight/vision/glasses/contacts/crnea/pupil/iris/eye/division/see/lens/cornea/
		// "O... just bits and pieces"
		// "You should have SEEN that one coming!"
		// "It's dark in there, but that could be for the best, they're not the
		// best LOOKING guys"
		// "They're pretty grotesque, but LOOKS aren't everything!"
		// "How many are there?" - "I'd say there's about 20, 20 odd"
		// "They tend to wander, you should keep an EYE on them"
		// "It's good that someone is keeping an EYE on them"
		// "You must be BATTY if you think you'll make your way to the other
		// side"
		// "I've got this weird MOLE" :P
		// "Some people have just been DYING to meet them!"
		// "IRISk a lot if I by telling you"
		// "EYE couldn't possibly say"

		// You -
		// "Wait, you've been feeding them people?"
		// "...","...","...yes". And then he attacks you? or the conversation
		// just ends...

		// Need a better pun one for the end of the pun line tho. like in
		// simsons treehouse of horror.

		// Pun 1
		conversationPartPun1 = new ConversationPart(new Object[] { "EYE couldn't possibly say" },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		// ... 1
		conversationResponseDotDotDot1 = new ConversationResponse("...What?", null);

		// Pun 2
		conversationPartPun2 = new ConversationPart(new Object[] { "IRISk a lot if I by telling you" },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		// ... 2
		conversationResponseDotDotDot2 = new ConversationResponse("Why are you talking like that?", null);

		// Pun 3
		conversationPartPun3 = new ConversationPart(new Object[] { "These will just keep getting Cornea!" },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		// ... 3
		conversationResponseDotDotDot3 = new ConversationResponse("...", null);

		// Pun 4
		conversationPartPun4 = new ConversationPart(
				new Object[] {
						"It's eyeballs, I feed them human eyeballs! ...it's all they'll eat. I've found though,  if I bury the eyes inside the corpse they'll at least eat through a lot of the evidence to get to their prize." },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort);

		// ...4
		// conversationResponseDotDotDot4 = new
		// ConversationResponse("...", null);

		// Show me
		conversationResponseShowMe = new ConversationResponse("Show me", null) {
			@Override
			public void select() {
				super.select();
				// Update quest log
				// Set enviromentalist to come watch
				// Hunters on the way
				questCaveOfTheBlind.feedingDemoAvailable = false;
				questCaveOfTheBlind.mort.performingFeedingDemo = true;
			}
		};

		// I'll show you
		illShowYou = new ConversationPart(new Object[] { "I'll show you, just keep your distance from them..." },
				new ConversationResponse[] {}, questCaveOfTheBlind.mort); // Thin
																			// grin

		// NEEDS TO BE ADDED
		this.openingConversationPart = this.currentConversationPart = conversationPartopening;
	}

	public void setup() {

		// FILL IN POINTERS
		// opening
		conversationPartopening.setConversationResponses(new ConversationResponse[] { conversationResponseWhoAreYou,
				conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// opening responses
		conversationResponseWhoAreYou.nextConversationPart = conversationPartImMort;
		conversationResponseWhereAmI.nextConversationPart = conversationPartYoureInTheCaveOfTheBlind;
		conversationResponseTellMeAboutTheBlind.nextConversationPart = conversationPartTheBlind;

		// I'm Mort
		conversationPartImMort.setConversationResponses(new ConversationResponse[] { conversationResponseWhoAreYou,
				conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// You're in my mine
		conversationPartYoureInTheCaveOfTheBlind.setConversationResponses(
				new ConversationResponse[] { conversationResponseWhoAreYou, conversationResponseWhereAmI,
						conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// The blind
		conversationPartTheBlind.setConversationResponses(
				new ConversationResponse[] { conversationResponseYouFeedThem, conversationReponseEnd });

		// You Feed them?
		if (questCaveOfTheBlind.feedingDemoAvailable) {
			conversationResponseYouFeedThem.nextConversationPart = conversationPartYupWannaSee;
		} else {
			conversationResponseYouFeedThem.nextConversationPart = conversationPartIAlreadyShowedYou;
		}

		// Yup, wanna see?
		conversationPartYupWannaSee.setConversationResponses(new ConversationResponse[] {
				conversationResponseWhatDoYouFeedThem, conversationResponseShowMe, conversationResponseWhoAreYou,
				conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// I already showed you!
		conversationPartIAlreadyShowedYou.setConversationResponses(new ConversationResponse[] {
				conversationResponseWhatDoYouFeedThem, conversationResponseWhoAreYou, conversationResponseWhereAmI,
				conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// What do you feed them?
		conversationResponseWhatDoYouFeedThem.nextConversationPart = conversationPartPun1;

		// Pun 1
		conversationPartPun1.setConversationResponses(
				new ConversationResponse[] { conversationResponseDotDotDot1, conversationReponseEnd });

		// ... 1
		conversationResponseDotDotDot1.nextConversationPart = conversationPartPun2;

		// Pun 2
		conversationPartPun2.setConversationResponses(
				new ConversationResponse[] { conversationResponseDotDotDot2, conversationReponseEnd });

		// ... 2
		conversationResponseDotDotDot2.nextConversationPart = conversationPartPun3;

		// Pun 3
		conversationPartPun3.setConversationResponses(
				new ConversationResponse[] { conversationResponseDotDotDot3, conversationReponseEnd });

		// ... 3
		conversationResponseDotDotDot3.nextConversationPart = conversationPartPun4;

		// Pun 4 // You Feed them?
		if (questCaveOfTheBlind.feedingDemoAvailable) {
			conversationPartPun4.setConversationResponses(new ConversationResponse[] {
					conversationResponseWhatDoYouFeedThem, conversationResponseShowMe, conversationResponseWhoAreYou,
					conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });
		} else {
			conversationPartPun4.setConversationResponses(new ConversationResponse[] {
					conversationResponseWhatDoYouFeedThem, conversationResponseWhoAreYou, conversationResponseWhereAmI,
					conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		}

		// Show me
		conversationResponseShowMe.nextConversationPart = illShowYou;

		// I'll show you
		illShowYou.setConversationResponses(new ConversationResponse[] { conversationReponseEnd });
	}

}
