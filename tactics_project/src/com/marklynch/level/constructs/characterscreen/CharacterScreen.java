package com.marklynch.level.constructs.characterscreen;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;

public class CharacterScreen implements Draggable, Scrollable {

	public static enum MODE {
		STATS, SKILLS
	};

	public static MODE mode = MODE.STATS;

	public boolean showing = false;

	int listX;
	int listY;
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

		actorY = (int) (Game.halfWindowHeight - Game.level.player.height);

		listX = 0;
		listY = 0;
		listBorder = 16;
		listWidth = 300;

		contentX = listX + listWidth + listBorder * 2;
		contentY = 30;
		contentBorder = 16;

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

		// Content
		float height = 0;

		// Tab Buttons
		for (Button button : tabButtons) {
			button.draw();
		}

		// Actor
		drawActor(Game.level.player, this.actorX, this.actorY);

		if (mode == MODE.STATS) {
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
