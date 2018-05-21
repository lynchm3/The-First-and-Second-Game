package com.marklynch.level.constructs.characterscreen;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

public class CharacterScreen implements Draggable, Scrollable {

	public static enum MODE {
		STATS, SKILLS
	};

	public static String HEALTH = "HEALTH";
	public static String STRENGTH = "STRENGTH";
	public static String DEXTERITY = "DEXTERITY";
	public static String INTELLIGENCE = "INTELLIGENCE";
	public static String ENDURANCE = "ENDURANCE";
	public static StringWithColor HEALTH_WHITE = new StringWithColor(HEALTH, Color.WHITE);
	public static StringWithColor STRENGTH_WHITE = new StringWithColor(STRENGTH, Color.WHITE);
	public static StringWithColor DEXTERITY_WHITE = new StringWithColor(DEXTERITY, Color.WHITE);
	public static StringWithColor INTELLIGENCE_WHITE = new StringWithColor(INTELLIGENCE, Color.WHITE);
	public static StringWithColor ENDURANCE_WHITE = new StringWithColor(ENDURANCE, Color.WHITE);

	public static MODE mode = MODE.STATS;

	public boolean showing = false;

	int statsX;
	int statLabelsX;
	int statValuesX;
	int statsY;
	int statsLineHeight;
	int listBorder;
	int listWidth;

	int contentX;
	int contentY;
	int contentBorder;

	int listItemHeight;

	float activeDrawPosition;
	float resolvedDrawPosition;

	transient static int bottomBorderHeight;

	transient int actorX = 100;
	transient int actorY = 0;

	public ArrayList<Link> logLinks = new ArrayList<Link>();
	public ArrayList<Link> conversationLinks = new ArrayList<Link>();
	public ArrayList<Link> objectiveLinksTopRight = new ArrayList<Link>();
	public ArrayList<Link> questLinksTopRight = new ArrayList<Link>();
	public ArrayList<Link> markerLinksTopRight = new ArrayList<Link>();
	public ArrayList<Link> markerLinksInJournal = new ArrayList<Link>();

	// Close button
	public static ArrayList<LevelButton> tabButtons = new ArrayList<LevelButton>();
	static LevelButton buttonClose;

	public CharacterScreen() {
		// resize();

		buttonClose = new LevelButton(Game.halfWindowWidth - 25f, bottomBorderHeight, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", "CLOSE", true, false, Color.BLACK, Color.WHITE, null);
		buttonClose.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.openCloseCharacterScreen();
			}
		});
		tabButtons.add(buttonClose);
	}

	public static void loadStaticImages() {
	}

	public void resize() {

		actorX = 0;
		actorY = (int) (Game.halfWindowHeight - Game.level.player.height);

		statsX = 200;
		statLabelsX = statsX;
		statValuesX = statsX + 100;
		statsY = 100;
		statsLineHeight = 30;

		listBorder = 16;
		listWidth = 300;

		// contentX = listX + listWidth + listBorder * 2;
		// contentY = 30;
		// contentBorder = 16;

		listItemHeight = 30;

		bottomBorderHeight = 384;

	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...

		resize();
		showing = true;

		generateLinks();

	}

	public void generateLinks() {

	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, 0, Game.windowWidth, Game.windowHeight);

		// Tab Buttons
		for (Button button : tabButtons) {
			button.draw();
		}

		// Actor
		drawActor(Game.level.player, this.actorX, this.actorY);

		if (mode == MODE.STATS) {

			int drawStatY = statsY;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, HEALTH_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.remainingHealth + "/" + Level.player.totalHealth);
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, STRENGTH_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.strength);
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, DEXTERITY_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.dexterity);
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, INTELLIGENCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.intelligence);
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, ENDURANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.endurance);
			drawStatY += statsLineHeight;

			// Level.player.remainingHealth;
			// Level.player.totalHealth;
			// Level.player.strength;
			// Level.player.dexterity;
			// Level.player.intelligence;
			// Level.player.endurance;
		}
	}

	@Override
	public void scroll(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drag(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	public void drawActor(Actor actor, int x, int y) {
		actor.drawActor(x, y, 1, false, 2f);
	}

}
