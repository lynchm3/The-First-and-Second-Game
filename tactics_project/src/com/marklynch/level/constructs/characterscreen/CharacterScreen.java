package com.marklynch.level.constructs.characterscreen;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
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

	// High level stats
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
	public HashMap<HIGH_LEVEL_STATS, LevelButton> highLevelStatButtons = new HashMap<HIGH_LEVEL_STATS, LevelButton>();
	public HashMap<HIGH_LEVEL_STATS, String> highLevelStatNames = new HashMap<HIGH_LEVEL_STATS, String>();

	// DMG and Healing
	public static String SLASH_DAMAGE = "SLASH DAMAGE";
	public static String BLUNT_DAMAGE = "BLUNT DAMAGE";
	public static String PIERCE_DAMAGE = "PIERCE DAMAGE";
	public static String FIRE_DAMAGE = "FIRE DAMAGE";
	public static String WATER_DAMAGE = "WATER DAMAGE";
	public static String ELECTRICAL_DAMAGE = "ELECTRICAL DAMAGE";
	public static String POISON_DAMAGE = "POISON DAMAGE";
	public static String BLEED_DAMAGE = "BLEED DAMAGE";
	public static String HEALING = "HEALING";
	public static StringWithColor SLASH_DAMAGE_WHITE = new StringWithColor(SLASH_DAMAGE, Color.WHITE);
	public static StringWithColor BLUNT_DAMAGE_WHITE = new StringWithColor(BLUNT_DAMAGE, Color.WHITE);
	public static StringWithColor PIERCE_DAMAGE_WHITE = new StringWithColor(PIERCE_DAMAGE, Color.WHITE);
	public static StringWithColor FIRE_DAMAGE_WHITE = new StringWithColor(FIRE_DAMAGE, Color.WHITE);
	public static StringWithColor WATER_DAMAGE_WHITE = new StringWithColor(WATER_DAMAGE, Color.WHITE);
	public static StringWithColor ELECTRICAL_DAMAGE_WHITE = new StringWithColor(ELECTRICAL_DAMAGE, Color.WHITE);
	public static StringWithColor POISON_DAMAGE_WHITE = new StringWithColor(POISON_DAMAGE, Color.WHITE);
	public static StringWithColor BLEED_DAMAGE_WHITE = new StringWithColor(BLEED_DAMAGE, Color.WHITE);
	public static StringWithColor HEALING_WHITE = new StringWithColor(HEALING, Color.WHITE);
	public HashMap<OFFENSIVE_STATS, LevelButton> offensiveStatButtons = new HashMap<OFFENSIVE_STATS, LevelButton>();
	public HashMap<OFFENSIVE_STATS, String> offensiveStatNames = new HashMap<OFFENSIVE_STATS, String>();

	// Resistances
	public static String SLASH_RESISTANCE = "SLASH RESISTANCE";
	public static String BLUNT_RESISTANCE = "BLUNT RESISTANCE";
	public static String PIERCE_RESISTANCE = "PIERCE RESISTANCE";
	public static String FIRE_RESISTANCE = "FIRE RESISTANCE";
	public static String WATER_RESISTANCE = "WATER RESISTANCE";
	public static String ELECTRICAL_RESISTANCE = "ELECTRICAL RESISTANCE";
	public static String POISON_RESISTANCE = "POSION RESISTANCE";
	public static String BLEED_RESISTANCE = "BLEEDING RESISTANCE";
	public static String HEALING_RESISTANCE = "HEALING RESISTANCE";
	public static StringWithColor SLASH_RESISTANCE_WHITE = new StringWithColor(SLASH_RESISTANCE, Color.WHITE);
	public static StringWithColor BLUNT_RESISTANCE_WHITE = new StringWithColor(BLUNT_RESISTANCE, Color.WHITE);
	public static StringWithColor PIERCE_RESISTANCE_WHITE = new StringWithColor(PIERCE_RESISTANCE, Color.WHITE);
	public static StringWithColor FIRE_RESISTANCE_WHITE = new StringWithColor(FIRE_RESISTANCE, Color.WHITE);
	public static StringWithColor WATER_RESISTANCE_WHITE = new StringWithColor(WATER_RESISTANCE, Color.WHITE);
	public static StringWithColor ELECTRICAL_RESISTANCE_WHITE = new StringWithColor(ELECTRICAL_RESISTANCE, Color.WHITE);
	public static StringWithColor POISON_RESISTANCE_WHITE = new StringWithColor(POISON_RESISTANCE, Color.WHITE);
	public static StringWithColor BLEED_RESISTANCE_WHITE = new StringWithColor(BLEED_RESISTANCE, Color.WHITE);
	public static StringWithColor HEALING_RESISTANCE_WHITE = new StringWithColor(HEALING_RESISTANCE, Color.WHITE);
	public HashMap<OFFENSIVE_STATS, LevelButton> defensiveStatButtons = new HashMap<OFFENSIVE_STATS, LevelButton>();
	public HashMap<OFFENSIVE_STATS, String> defensiveStatNames = new HashMap<OFFENSIVE_STATS, String>();

	public static LevelButton healthButton;

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
	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	static LevelButton buttonClose;

	public CharacterScreen() {
		// resize();
		healthButton = new LevelButton(0, 0, Game.smallFont.getWidth(HEALTH), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(healthButton);
		highLevelStatNames.put(HIGH_LEVEL_STATS.STRENGTH, STRENGTH);
		highLevelStatNames.put(HIGH_LEVEL_STATS.DEXTERITY, DEXTERITY);
		highLevelStatNames.put(HIGH_LEVEL_STATS.INTELLIGENCE, INTELLIGENCE);
		highLevelStatNames.put(HIGH_LEVEL_STATS.ENDURANCE, ENDURANCE);

		offensiveStatNames.put(OFFENSIVE_STATS.SLASH_DAMAGE, SLASH_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.BLUNT_DAMAGE, BLUNT_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.PIERCE_DAMAGE, PIERCE_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.FIRE_DAMAGE, FIRE_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.WATER_DAMAGE, WATER_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, ELECTRICAL_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.POISON_DAMAGE, POISON_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.BLEED_DAMAGE, BLEED_DAMAGE);
		offensiveStatNames.put(OFFENSIVE_STATS.HEALING, HEALING);

		defensiveStatNames.put(OFFENSIVE_STATS.SLASH_DAMAGE, SLASH_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.BLUNT_DAMAGE, BLUNT_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.PIERCE_DAMAGE, PIERCE_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.FIRE_DAMAGE, FIRE_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.WATER_DAMAGE, WATER_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, ELECTRICAL_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.POISON_DAMAGE, POISON_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.BLEED_DAMAGE, BLEED_RESISTANCE);
		defensiveStatNames.put(OFFENSIVE_STATS.HEALING, HEALING_RESISTANCE);

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(highLevelStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
			highLevelStatButtons.put(statType, button);
			buttons.add(button);
		}

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(offensiveStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
			offensiveStatButtons.put(statType, button);
			buttons.add(button);
		}

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(defensiveStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
			defensiveStatButtons.put(statType, button);
			buttons.add(button);
		}

		buttonClose = new LevelButton(Game.halfWindowWidth - 25f, bottomBorderHeight, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", "CLOSE", true, false, Color.BLACK, Color.WHITE, null);
		buttonClose.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.openCloseCharacterScreen();
			}
		});
		buttons.add(buttonClose);
	}

	public static void loadStaticImages() {
	}

	public void resize() {

		actorX = 0;
		actorY = (int) (Game.halfWindowHeight - Game.level.player.height);

		statsX = 200;
		statLabelsX = statsX;
		statValuesX = statsX + 200;
		statsY = 100;
		statsLineHeight = 30;

		listBorder = 16;
		listWidth = 300;

		bottomBorderHeight = 384;

		int drawStatY = statsY;

		healthButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			highLevelStatButtons.get(statType).updatePosition(statValuesX, drawStatY);
			drawStatY += statsLineHeight;
		}

		drawStatY += statsLineHeight;

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			offensiveStatButtons.get(statType).updatePosition(statValuesX, drawStatY);
			drawStatY += statsLineHeight;
		}

		drawStatY += statsLineHeight;

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			defensiveStatButtons.get(statType).updatePosition(statValuesX, drawStatY);
			drawStatY += statsLineHeight;
		}

		drawStatY += statsLineHeight;
	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...

		resize();
		showing = true;

		generateLinks();

	}

	public void generateLinks() {

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			highLevelStatButtons.get(statType).setTooltipText(Level.player.getEffectiveHighLevelStatTooltip(statType));
		}

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			offensiveStatButtons.get(statType).setTooltipText(Level.player.getEffectiveOffensiveStatTooltip(statType));
		}

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			defensiveStatButtons.get(statType).setTooltipText(Level.player.getEffectiveDefensiveStatTooltip(statType));
		}

	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, 0, Game.windowWidth, Game.windowHeight);

		// Tab Buttons
		for (Button button : buttons) {
			button.draw();
		}

		// Actor
		drawActor(Game.level.player, this.actorX, this.actorY);

		if (mode == MODE.STATS) {

			int drawStatY = statsY;

			// BASIC STATS
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, HEALTH_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.remainingHealth + "/" + Level.player.totalHealth);
			healthButton.width = Game.smallFont.getWidth(Level.player.remainingHealth + "/" + Level.player.totalHealth);
			drawStatY += statsLineHeight;

			for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
				TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
						highLevelStatNames.get(statType));
				TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
						"" + Level.player.getEffectiveHighLevelStat(statType));
				highLevelStatButtons.get(statType).width = Game.smallFont
						.getWidth("" + Level.player.getEffectiveHighLevelStat(statType));
				drawStatY += statsLineHeight;
			}

			drawStatY += statsLineHeight;

			// DAMAGE

			for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
				TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
						offensiveStatNames.get(statType));
				TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
						"" + Level.player.getEffectiveOffensiveStat(statType));
				offensiveStatButtons.get(statType).width = Game.smallFont
						.getWidth("" + Level.player.getEffectiveOffensiveStat(statType));
				drawStatY += statsLineHeight;
			}
			drawStatY += statsLineHeight;

			// RESISTANCES

			for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
				TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
						defensiveStatNames.get(statType));
				TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
						"" + Level.player.getEffectiveDefensiveStat(statType));
				defensiveStatButtons.get(statType).width = Game.smallFont
						.getWidth("" + Level.player.getEffectiveDefensiveStat(statType));
				drawStatY += statsLineHeight;
			}
			drawStatY += statsLineHeight;

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
