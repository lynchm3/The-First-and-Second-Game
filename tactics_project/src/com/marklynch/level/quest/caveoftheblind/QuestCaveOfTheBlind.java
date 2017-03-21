package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructureHall;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.quest.Quest;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Key;
import com.marklynch.objects.Sign;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Lantern;
import com.marklynch.objects.weapons.Weapon;

public class QuestCaveOfTheBlind extends Quest {

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "SHOULD NOT BE VISIBLE";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";

	// Flags
	boolean talkedToMort = false;
	boolean feedingDemoAvailable = false;
	int playerMinedOres = 0;

	// End
	boolean huntersReleasedFromQuest;

	boolean huntersAndWolvesFought = false;

	// Structure sections
	StructureSection wolfDen;
	StructureSection entrance1;
	StructureSection entrance2;
	StructureSection atrium1;
	StructureSection atrium2;
	StructureSection mortsMine;
	StructureSection mortsRooms;

	// Actors
	Mort mort;
	Blind blind;

	// GameObjects
	Junk ore;
	Weapon serratedSpoon;
	Lantern lantern;
	Wall oreWall;
	Key mortsKey;

	// Squares
	Square troughSquare;
	Square safeSquare;

	// Structure Areas

	// Conversations
	public static Conversation conversationForMort;

	public QuestCaveOfTheBlind() {
		super();

		// Mort and his bed
		Bed mortsBed = Templates.BED.makeCopy(Game.level.squares[35][24]);
		mortsBed.quest = this;
		mortsKey = Templates.KEY.makeCopy(null);
		mortsKey.quest = this;

		mort = Templates.MORT.makeCopy(Game.level.squares[47][21], Game.level.factions.get(1), mortsBed);
		mort.quest = this;
		mort.mortsBell = Templates.DINNER_BELL.makeCopy(null);
		mort.mortsMeatChunk = Templates.MEAT_CHUNK.makeCopy("Meat Chunk", null);
		mort.inventory.add(Templates.CLEAVER.makeCopy(null));
		mort.inventory.add(Templates.LANTERN.makeCopy(null));
		mort.inventory.add(Templates.PICKAXE.makeCopy(null));
		mort.inventory.add(mortsKey);
		mort.inventory.add(mort.mortsBell);
		mort.inventory.add(mort.mortsMeatChunk);
		mort.questCaveOfTheBlind = this;

		// for (GameObject gameObject : mort.inventory.getGameObjects()) {
		// gameObject.quest = this;
		// }

		// Cave
		makeMortsStorage();
		makeCave();
		makeMortsMine();
		makeMortsRoom();
		makeBlind();

		Sign rockWithEtching = Templates.ROCK_WITH_ETCHING.makeCopy(Game.level.squares[45][14], "Rock with etching",
				new Object[] { "SHHHHHhhhhhhhh!!! -Mort" });

		// Add spoons
		serratedSpoon = Templates.SERRATED_SPOON.makeCopy(Game.level.squares[44][11]);
		serratedSpoon.quest = this;
	}

	@Override
	public void update() {

	}

	@Override
	public boolean update(Actor actor) {
		return false;
	}

	@Override
	public Conversation getConversation(Actor actor) {
		if (actor == mort) {
			Conversation conversation = getConversationForMort();
			talkedToMort = true;
			return conversation;
		}
		return null;
	}

	public void setUpConversationReadyToGo() {
		// ConversationResponse conversationReponseEnd = new
		// ConversationResponse("Leave", null);
		//
		// ConversationPart conversationPartTheresEquipment = new
		// ConversationPart(
		// new Object[] {
		// "There's spare equipment 'round back, help yourself! Joe runs a shop
		// to the North if you think you need anything else. Let us know when
		// you're ready." },
		// new ConversationResponse[] { conversationReponseEnd },
		// hunterPack.getLeader());
		//
		// ConversationPart conversationPartSuitYourself = new
		// ConversationPart(new Object[] { "Suit yourself." },
		// new ConversationResponse[] { conversationReponseEnd },
		// hunterPack.getLeader());
		//
		// ConversationResponse conversationResponseNoThanks = new
		// ConversationResponse("No thanks",
		// conversationPartSuitYourself);
		// ConversationResponse conversationResponseYesPlease = new
		// ConversationResponse("Yes please",
		// conversationPartTheresEquipment) {
		// @Override
		// public void select() {
		// super.select();
		// // ADD QUEST TO QUEST LOG IF NO IN HARDCORE MODE
		// // THIS ALSO COMES WITH A TOAST / POPUP SAYING "QUEST STARTED -
		// // PACK HUNTERS"
		// questAcceptedFromHunters = true;
		// }
		// };
		//
		// ConversationPart conversationPartWantToComeHunting = new
		// ConversationPart(
		// new Object[] { "We're just about to head out on the hunt, an extra
		// man wouldn't go amiss." },
		// new ConversationResponse[] { conversationResponseYesPlease,
		// conversationResponseNoThanks },
		// hunterPack.getLeader());
		//
		// conversationHuntersJoinTheHunt = new
		// Conversation(conversationPartWantToComeHunting);

	}

	public void makeCave() {
		ArrayList<GameObject> caveFeatures = new ArrayList<GameObject>();
		ArrayList<Key> keys = new ArrayList();
		keys.add(mortsKey);
		keys.add((Key) Game.level.player.inventory.getGameObectOfClass(Key.class));
		caveFeatures.add(Templates.VEIN.makeCopy(Game.level.squares[42][23]));
		caveFeatures.add(Templates.DOOR.makeCopy(Game.level.squares[41][21], keys, true));
		caveFeatures.add(Templates.DOOR.makeCopy(Game.level.squares[31][21], keys, true));

		ArrayList<StructureHall> cavePaths = new ArrayList<StructureHall>();
		ArrayList<Square> cavePathSquares = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[24][14]);
		cavePathSquares.add(Game.level.squares[25][14]);
		cavePaths.add(new StructureHall("Path", cavePathSquares));
		ArrayList<Square> cavePathSquares2 = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[38][16]);
		cavePathSquares.add(Game.level.squares[39][16]);
		cavePathSquares.add(Game.level.squares[39][15]);
		cavePathSquares.add(Game.level.squares[39][14]);
		cavePathSquares.add(Game.level.squares[40][14]);
		cavePathSquares.add(Game.level.squares[41][14]);
		cavePathSquares.add(Game.level.squares[42][14]);
		cavePaths.add(new StructureHall("Path", cavePathSquares2));
		ArrayList<Square> cavePathSquares3 = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[48][7]);
		cavePathSquares.add(Game.level.squares[49][7]);
		cavePathSquares.add(Game.level.squares[50][7]);
		cavePathSquares.add(Game.level.squares[51][7]);
		cavePathSquares.add(Game.level.squares[53][17]);
		cavePathSquares.add(Game.level.squares[57][18]);
		cavePathSquares.add(Game.level.squares[57][19]);
		cavePathSquares.add(Game.level.squares[57][20]);
		cavePathSquares.add(Game.level.squares[57][21]);
		cavePaths.add(new StructureHall("Path", cavePathSquares3));

		ArrayList<StructureRoom> caveAtriums = new ArrayList<StructureRoom>();
		caveAtriums.add(new StructureRoom("Wolf's Den", 25, 13, 37, 18));
		caveAtriums.add(new StructureRoom("Entrance of the Blind", 43, 7, 47, 14));
		caveAtriums.add(new StructureRoom("Atrium of the Blind", 52, 4, 56, 9));
		caveAtriums.add(new StructureRoom("Atrium of the Blind", 54, 10, 60, 17));
		caveAtriums.add(new StructureRoom("Atrium 2 of the Blind", 54, 22, 60, 25));
		caveAtriums.add(new StructureRoom("Atrium 2 of the Blind", 57, 26, 60, 30));
		caveAtriums.add(new StructureRoom("Morty's Mine", 49, 17, 52, 25));
		caveAtriums.add(new StructureRoom("Morty's Mine", 42, 19, 50, 26));
		caveAtriums.add(new StructureRoom("Morty's Mine", 44, 23, 51, 32));
		caveAtriums.add(new StructureRoom("Morty's Room", 32, 21, 40, 24));
		caveAtriums.add(new StructureRoom("Morty's Stash", 24, 21, 30, 24));

		ArrayList<StructureSection> caveSections = new ArrayList<StructureSection>();
		wolfDen = new StructureSection("Cave of the Blind", 24, 12, 40, 19);
		caveSections.add(wolfDen);
		entrance1 = new StructureSection("Cave of the Blind", 41, 14, 49, 16);
		caveSections.add(entrance1);
		entrance2 = new StructureSection("Cave of the Blind", 41, 5, 49, 13);
		caveSections.add(entrance2);
		atrium1 = new StructureSection("Cave of the Blind", 49, 2, 62, 18);
		caveSections.add(atrium1);
		atrium2 = new StructureSection("Cave of the Blind", 54, 19, 61, 31);
		caveSections.add(atrium2);
		mortsMine = mort.mortsMine = new StructureSection("Cave of the Blind", 41, 17, 53, 33);
		caveSections.add(mortsMine); // Morty's
		// Mine
		mortsRooms = mort.mortsRooms = new StructureSection("Cave of the Blind", 20, 20, 40, 27);
		caveSections.add(mortsRooms); // Morty's
		// Rooms
		Game.level.structures.add(new Structure("Cave of the Blind", caveSections, caveAtriums, cavePaths, caveFeatures,
				new ArrayList<Square>(), null, 0, 0, 0, 0, true));
	}

	public void makeMortsMine() {

		Sign noEntry = Templates.SIGN.makeCopy(Game.level.squares[42][20], "Sign", new Object[] { "PRIVATE! - Mort" });
		noEntry.quest = this;

		GameObject trough = Templates.TROUGH.makeCopy(Game.level.squares[50][25]);
		troughSquare = Game.level.squares[50][25];
		safeSquare = Game.level.squares[44][25];

	}

	public void makeMortsRoom() {
	}

	public void makeMortsStorage() {

		GameObject outsideBlood1 = Templates.BLOOD.makeCopy(Game.level.squares[17][21]);
		GameObject outsideBlood2 = Templates.BLOOD.makeCopy(Game.level.squares[18][21]);
		GameObject outsideBlood3 = Templates.BLOOD.makeCopy(Game.level.squares[19][21]);
		GameObject outsideBlood4 = Templates.BLOOD.makeCopy(Game.level.squares[20][21]);
		GameObject outsideBlood5 = Templates.BLOOD.makeCopy(Game.level.squares[21][21]);
		GameObject outsideBlood6 = Templates.BLOOD.makeCopy(Game.level.squares[22][21]);
		GameObject outsideBlood7 = Templates.BLOOD.makeCopy(Game.level.squares[23][21]);
		GameObject blood = Templates.BLOOD.makeCopy(Game.level.squares[24][21]);
		Corpse carcass1 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[24][21]);
		Corpse carcass2 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[24][21]);
		Corpse carcass3 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[24][21]);
		Corpse carcass4 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[25][21]);
		Corpse carcass5 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[25][21]);
		Corpse carcass6 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[24][22]);
		Corpse carcass7 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[24][24]);
		ore = Templates.ORE.makeCopy(Game.level.squares[24][24]);
		lantern = Templates.LANTERN.makeCopy(Game.level.squares[26][24]);
		GameObject table = Templates.TABLE.makeCopy(Game.level.squares[27][24]);

		// Link to the quest to keep them safe
		blood.quest = this;
		carcass1.quest = this;
		carcass2.quest = this;
		carcass3.quest = this;
		carcass4.quest = this;
		carcass5.quest = this;
		carcass6.quest = this;
		carcass7.quest = this;
		ore.quest = this;
		lantern.quest = this;
		table.quest = this;
	}

	public void makeBlind() {

		// Entrance 2
		blind = Templates.BLIND.makeCopy(Game.level.squares[46][7], Game.level.factions.get(3), entrance2);
		blind.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind.addAttackerForThisAndGroupMembers(Game.level.player);
		blind.quest = this;

		// Atrium 1
		Blind blind1 = Templates.BLIND.makeCopy(Game.level.squares[60][12], Game.level.factions.get(3), atrium1);
		blind1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind1.addAttackerForThisAndGroupMembers(Game.level.player);
		blind1.quest = this;

		Blind blind2 = Templates.BLIND.makeCopy(Game.level.squares[60][15], Game.level.factions.get(3), atrium1);
		blind2.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind2.addAttackerForThisAndGroupMembers(Game.level.player);
		blind2.quest = this;

		// Atrium 2
		Blind blind3 = Templates.BLIND.makeCopy(Game.level.squares[55][23], Game.level.factions.get(3), atrium2);
		blind3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind3.addAttackerForThisAndGroupMembers(Game.level.player);
		blind3.quest = this;

		Blind blind4 = Templates.BLIND.makeCopy(Game.level.squares[60][25], Game.level.factions.get(3), atrium2);
		blind4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind4.addAttackerForThisAndGroupMembers(Game.level.player);
		blind4.quest = this;

		Blind blind5 = Templates.BLIND.makeCopy(Game.level.squares[55][24], Game.level.factions.get(3), atrium2);
		blind5.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind5.addAttackerForThisAndGroupMembers(Game.level.player);
		blind5.quest = this;

		Blind blind6 = Templates.BLIND.makeCopy(Game.level.squares[59][25], Game.level.factions.get(3), atrium2);
		blind6.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind6.addAttackerForThisAndGroupMembers(Game.level.player);
		blind6.quest = this;

		Blind blind7 = Templates.BLIND.makeCopy(Game.level.squares[60][26], Game.level.factions.get(3), atrium2);
		blind7.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind7.addAttackerForThisAndGroupMembers(Game.level.player);
		blind7.quest = this;

		Blind blind8 = Templates.BLIND.makeCopy(Game.level.squares[57][24], Game.level.factions.get(3), atrium2);
		blind8.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind8.addAttackerForThisAndGroupMembers(Game.level.player);
		blind8.quest = this;

		Blind blind9 = Templates.BLIND.makeCopy(Game.level.squares[56][25], Game.level.factions.get(3), atrium2);
		blind9.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind9.addAttackerForThisAndGroupMembers(Game.level.player);
		blind9.quest = this;

		Blind blind10 = Templates.BLIND.makeCopy(Game.level.squares[57][27], Game.level.factions.get(3), atrium2);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind10.addAttackerForThisAndGroupMembers(Game.level.player);
		blind10.quest = this;

		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[57][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[57][25]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[58][24]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[59][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[52][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[51][28]);
	}

	public Conversation getConversationForMort() {

		// Opening
		ConversationPart conversationPartopening = null;
		if (talkedToMort && playerMinedOres > 0) {
			// You mined his ore
			conversationPartopening = new ConversationPart(new Object[] { "Hands off my ore!" },
					new ConversationResponse[] {}, mort);
		} else if (talkedToMort) {
			conversationPartopening = new ConversationPart(new Object[] { "Yes?" }, // annoyed
					new ConversationResponse[] {}, mort);
		} else {
			// Havent talked yet, annoyed by your mere presence
			conversationPartopening = new ConversationPart(
					new Object[] {
							"What are you doing here? Hands off my ore! You should leave, would be terrible if the blind got you!" },
					new ConversationResponse[] {}, mort);
		}

		// General responses
		ConversationResponse conversationResponseTellMeAboutTheBlind = new ConversationResponse(
				"Tell me about the blind.", null);
		ConversationResponse conversationResponseWhereAmI = new ConversationResponse("Where am I?", null);
		ConversationResponse conversationResponseWhoAreYou = new ConversationResponse("Who are you?", null);

		// First level
		ConversationPart conversationPartTheBlind = new ConversationPart(
				new Object[] {
						"They can be pretty vicious. But as long as I feed them they keep to themselves. O... and... eh... they're blind." },
				new ConversationResponse[] {}, mort);

		ConversationPart conversationPartYoureInTheCaveOfTheBlind = new ConversationPart(
				new Object[] { "You're in my mine, don't touch my ore" }, new ConversationResponse[] {}, mort);

		ConversationPart conversationPartImMort = new ConversationPart(
				new Object[] { "I'm Mort, this is my mine, don't touch my ore." }, new ConversationResponse[] {}, mort);

		ConversationResponse conversationReponseEnd = new ConversationResponse("Leave", null);

		// You feed them?
		ConversationResponse conversationResponseYouFeedThem = new ConversationResponse("You feed them?", null);

		// Yup, wanna see?
		ConversationPart conversationPartYupWannaSee = new ConversationPart(new Object[] { "Yup! ...you wanna see?" },
				new ConversationResponse[] {}, mort); // pride? shifty?

		// I already showed you
		ConversationPart conversationPartIAlreadyShowedYou = new ConversationPart(
				new Object[] { "I already showed you!" }, new ConversationResponse[] {}, mort);

		// What do you feed them?
		ConversationResponse conversationResponseWhatDoYouFeedThem = new ConversationResponse("What do you feed them?",
				null);

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
		ConversationPart conversationPartPun1 = new ConversationPart(new Object[] { "EYE couldn't possibly say" },
				new ConversationResponse[] {}, mort);

		// ... 1
		ConversationResponse conversationResponseDotDotDot1 = new ConversationResponse("...What?", null);

		// Pun 2
		ConversationPart conversationPartPun2 = new ConversationPart(new Object[] { "IRISk a lot if I by telling you" },
				new ConversationResponse[] {}, mort);

		// ... 2
		ConversationResponse conversationResponseDotDotDot2 = new ConversationResponse("Why are you talking like that?",
				null);

		// Pun 3
		ConversationPart conversationPartPun3 = new ConversationPart(
				new Object[] { "These will just keep getting Cornea!" }, new ConversationResponse[] {}, mort);

		// ... 3
		ConversationResponse conversationResponseDotDotDot3 = new ConversationResponse("...", null);

		// Pun 4
		ConversationPart conversationPartPun4 = new ConversationPart(
				new Object[] {
						"It's eyeballs, I feed them human eyeballs! ...it's all they'll eat. I've found though,  if I bury the eyes inside the corpse they'll at least eat through a lot of the evidence to get to their prize." },
				new ConversationResponse[] {}, mort);

		// ...4
		// ConversationResponse conversationResponseDotDotDot4 = new
		// ConversationResponse("...", null);

		// Show me
		ConversationResponse conversationResponseShowMe = new ConversationResponse("Show me", null) {
			@Override
			public void select() {
				super.select();
				// Update quest log
				// Set enviromentalist to come watch
				// Hunters on the way
				feedingDemoAvailable = false;
				mort.performingFeedingDemo = true;
			}
		};

		// I'll show you
		ConversationPart illShowYou = new ConversationPart(new Object[] { "I'll show you, wait here..." },
				new ConversationResponse[] {}, mort); // Thin grin

		// FILL IN POINTERS
		// opening
		conversationPartopening.setConversationResponses(new ConversationResponse[] { conversationResponseWhoAreYou,
				conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// opening responses
		conversationResponseWhoAreYou.nextConversationPart = conversationPartImMort;
		conversationResponseWhereAmI.nextConversationPart = conversationPartYoureInTheCaveOfTheBlind;
		conversationResponseTellMeAboutTheBlind.nextConversationPart = conversationPartTheBlind;

		// I'm Mort
		conversationPartImMort.setConversationResponses(new ConversationResponse[] { conversationResponseWhereAmI,
				conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// You're in my mine
		conversationPartYoureInTheCaveOfTheBlind.setConversationResponses(
				new ConversationResponse[] { conversationResponseWhoAreYou, conversationResponseWhereAmI,
						conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		// The blind
		conversationPartTheBlind.setConversationResponses(new ConversationResponse[] { conversationResponseYouFeedThem,
				conversationResponseWhoAreYou, conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind,
				conversationReponseEnd });

		// You Feed them?
		if (feedingDemoAvailable) {
			conversationResponseYouFeedThem.nextConversationPart = conversationPartIAlreadyShowedYou;
		} else {
			conversationResponseYouFeedThem.nextConversationPart = conversationPartYupWannaSee;

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
		conversationPartPun1.setConversationResponses(new ConversationResponse[] { conversationResponseDotDotDot1,
				conversationResponseShowMe, conversationReponseEnd });

		// ... 1
		conversationResponseDotDotDot1.nextConversationPart = conversationPartPun2;

		// Pun 2
		conversationPartPun2.setConversationResponses(new ConversationResponse[] { conversationResponseDotDotDot2,
				conversationResponseShowMe, conversationReponseEnd });

		// ... 2
		conversationResponseDotDotDot2.nextConversationPart = conversationPartPun3;

		// Pun 3
		conversationPartPun3.setConversationResponses(new ConversationResponse[] { conversationResponseDotDotDot3,
				conversationResponseShowMe, conversationReponseEnd });

		// ... 3
		conversationResponseDotDotDot3.nextConversationPart = conversationPartPun4;

		// Pun 4 // You Feed them?
		if (feedingDemoAvailable) {
			conversationPartPun4.setConversationResponses(new ConversationResponse[] {
					conversationResponseWhatDoYouFeedThem, conversationResponseWhoAreYou, conversationResponseWhereAmI,
					conversationResponseTellMeAboutTheBlind, conversationReponseEnd });
		} else {
			conversationPartPun4.setConversationResponses(new ConversationResponse[] {
					conversationResponseWhatDoYouFeedThem, conversationResponseShowMe, conversationResponseWhoAreYou,
					conversationResponseWhereAmI, conversationResponseTellMeAboutTheBlind, conversationReponseEnd });

		}

		// Show me
		conversationResponseShowMe.nextConversationPart = illShowYou;

		// I'll show you
		illShowYou.setConversationResponses(new ConversationResponse[] { conversationReponseEnd });

		return new Conversation(conversationPartopening);
	}

}
