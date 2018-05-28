package com.marklynch.level.constructs.characterscreen;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;

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
	public static String HEALTH_SHORT = "HP ";
	public static String STRENGTH_SHORT = "STR";
	public static String DEXTERITY_SHORT = "DEX";
	public static String INTELLIGENCE_SHORT = "INT";
	public static String ENDURANCE_SHORT = "ENDE";

	public static HashMap<HIGH_LEVEL_STATS, LevelButton> highLevelStatButtons = new HashMap<HIGH_LEVEL_STATS, LevelButton>();
	public static HashMap<HIGH_LEVEL_STATS, String> highLevelStatNames = new HashMap<HIGH_LEVEL_STATS, String>();
	public static HashMap<HIGH_LEVEL_STATS, String> highLevelStatNamesShort = new HashMap<HIGH_LEVEL_STATS, String>();
	public static HashMap<HIGH_LEVEL_STATS, Texture> highLevelStatImages = new HashMap<HIGH_LEVEL_STATS, Texture>();

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
	public static String SLASH_SHORT = "SLSH";
	public static String BLUNT_SHORT = "BLNT";
	public static String PIERCE_SHORT = "PRCE";
	public static String FIRE_SHORT = "FIRE";
	public static String WATER_SHORT = "WATR";
	public static String ELECTRICAL_SHORT = "ELEC";
	public static String POISON_SHORT = "PSN ";
	public static String BLEED_SHORT = "BLD ";
	public static String HEALING_SHORT = "HEAL";
	public static HashMap<OFFENSIVE_STATS, LevelButton> offensiveStatButtons = new HashMap<OFFENSIVE_STATS, LevelButton>();
	public static HashMap<OFFENSIVE_STATS, String> offensiveStatNames = new HashMap<OFFENSIVE_STATS, String>();
	public static HashMap<OFFENSIVE_STATS, String> offensiveStatNamesShort = new HashMap<OFFENSIVE_STATS, String>();
	public static HashMap<OFFENSIVE_STATS, Texture> offensiveStatImages = new HashMap<OFFENSIVE_STATS, Texture>();

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
	public static HashMap<OFFENSIVE_STATS, LevelButton> defensiveStatButtons = new HashMap<OFFENSIVE_STATS, LevelButton>();
	public static HashMap<OFFENSIVE_STATS, String> defensiveStatNames = new HashMap<OFFENSIVE_STATS, String>();
	public static HashMap<OFFENSIVE_STATS, Texture> defensiveStatImages = new HashMap<OFFENSIVE_STATS, Texture>();

	public static LevelButton healthButton;

	public static MODE mode = MODE.STATS;

	public boolean showing = false;

	// int statsX;
	// int statLabelsX;
	// int statValuesX;
	// int statsY;
	// int statsLineHeight;
	// int listBorder;
	// int listWidth;
	//
	// int contentX;
	// int contentY;
	// int contentBorder;
	//
	// float activeDrawPosition;
	// float resolvedDrawPosition;

	// transient static int bottomBorderHeight;

	// transient int actorX;
	// transient int actorY;

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
				"end_turn_button.png", "", true, true, Color.TRANSPARENT, Color.WHITE, null);
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

		highLevelStatNamesShort.put(HIGH_LEVEL_STATS.STRENGTH, STRENGTH_SHORT);
		highLevelStatNamesShort.put(HIGH_LEVEL_STATS.DEXTERITY, DEXTERITY_SHORT);
		highLevelStatNamesShort.put(HIGH_LEVEL_STATS.INTELLIGENCE, INTELLIGENCE_SHORT);
		highLevelStatNamesShort.put(HIGH_LEVEL_STATS.ENDURANCE, ENDURANCE_SHORT);

		offensiveStatNamesShort.put(OFFENSIVE_STATS.SLASH_DAMAGE, SLASH_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.BLUNT_DAMAGE, BLUNT_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.PIERCE_DAMAGE, PIERCE_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.FIRE_DAMAGE, FIRE_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.WATER_DAMAGE, WATER_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, ELECTRICAL_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.POISON_DAMAGE, POISON_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.BLEED_DAMAGE, BLEED_SHORT);
		offensiveStatNamesShort.put(OFFENSIVE_STATS.HEALING, HEALING_SHORT);

		highLevelStatImages.put(HIGH_LEVEL_STATS.STRENGTH, getGlobalImage("action_slash.png", false));
		highLevelStatImages.put(HIGH_LEVEL_STATS.DEXTERITY, getGlobalImage("action_slash.png", false));
		highLevelStatImages.put(HIGH_LEVEL_STATS.INTELLIGENCE, getGlobalImage("action_slash.png", false));
		highLevelStatImages.put(HIGH_LEVEL_STATS.ENDURANCE, getGlobalImage("action_slash.png", false));

		offensiveStatImages.put(OFFENSIVE_STATS.SLASH_DAMAGE, getGlobalImage("action_slash.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.BLUNT_DAMAGE, getGlobalImage("action_blunt.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.PIERCE_DAMAGE, getGlobalImage("action_pierce.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.FIRE_DAMAGE, getGlobalImage("action_burn.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.WATER_DAMAGE, getGlobalImage("action_douse.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, getGlobalImage("action_electrical.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.POISON_DAMAGE, getGlobalImage("action_poison.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.BLEED_DAMAGE, getGlobalImage("action_bleed.png", false));
		offensiveStatImages.put(OFFENSIVE_STATS.HEALING, getGlobalImage("action_healing.png", false));

		defensiveStatImages.put(OFFENSIVE_STATS.SLASH_DAMAGE, getGlobalImage("action_slash.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.BLUNT_DAMAGE, getGlobalImage("action_blunt.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.PIERCE_DAMAGE, getGlobalImage("action_pierce.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.FIRE_DAMAGE, getGlobalImage("action_burn.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.WATER_DAMAGE, getGlobalImage("action_douse.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, getGlobalImage("action_electrical.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.POISON_DAMAGE, getGlobalImage("action_poison.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.BLEED_DAMAGE, getGlobalImage("action_bleed.png", false));
		defensiveStatImages.put(OFFENSIVE_STATS.HEALING, getGlobalImage("action_healing.png", false));

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(highLevelStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.TRANSPARENT, Color.WHITE, null);
			highLevelStatButtons.put(statType, button);
			buttons.add(button);
		}

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(offensiveStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.TRANSPARENT, Color.WHITE, null);
			offensiveStatButtons.put(statType, button);
			buttons.add(button);
		}

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(defensiveStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.TRANSPARENT, Color.WHITE, null);
			defensiveStatButtons.put(statType, button);
			buttons.add(button);
		}

		// buttonClose = new LevelButton(Game.halfWindowWidth - 25f, bottomBorderHeight,
		// 70f, 30f, "end_turn_button.png",
		// "end_turn_button.png", "CLOSE", true, false, Color.BLACK, Color.WHITE, null);
		// buttonClose.setClickListener(new ClickListener() {
		// @Override
		// public void click() {
		// Game.level.openCloseCharacterScreen();
		// }
		// });
		// buttons.add(buttonClose);
	}

	public static void loadStaticImages() {
	}

	public void resize() {
	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...

		resize();
		showing = true;

		generateLinks();

	}

	public static void generateLinks() {

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

		drawStats(0, 0, false);

	}

	public static void drawStats(int x, int y, boolean smallVersion) {

		int actorX = 0 + x;
		int actorY = (int) (Game.halfWindowHeight - Game.level.player.height) + y;
		float actorScale = 2f;

		int statsX = 200 + x;
		int statLabelsX = statsX;
		int statValuesX = statsX + 200;
		int statsY = 100 + y;
		int statsLineHeight = 30;
		int drawStatY = statsY;
		HashMap<HIGH_LEVEL_STATS, String> highLevelStatNamesToUse = highLevelStatNames;
		HashMap<OFFENSIVE_STATS, String> offensiveStatNamesToUse = offensiveStatNames;
		HashMap<OFFENSIVE_STATS, String> defensiveStatNamesToUse = defensiveStatNames;

		if (smallVersion) {
			actorY = 0 + x;
			actorScale = 1f;
			statsX = 0 + x;
			statLabelsX = statsX;
			statValuesX = statsX + 50;
			statsY = 128 + y;
			statsLineHeight = 30;
			drawStatY = statsY;
			highLevelStatNamesToUse = highLevelStatNamesShort;
			offensiveStatNamesToUse = offensiveStatNamesShort;
			defensiveStatNamesToUse = offensiveStatNamesShort;
		}

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

		// DRAW STUFF

		drawStatY += statsLineHeight;

		// Actor
		// drawActor(Game.level.player, actorX, actorY);
		Level.player.drawActor(actorX, actorY, 1, false, actorScale);

		drawStatY = statsY;

		// BASIC STATS
		TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE, HEALTH);
		TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
				Level.player.remainingHealth + "/" + Level.player.totalHealth);
		healthButton.width = Game.smallFont.getWidth(Level.player.remainingHealth + "/" + Level.player.totalHealth);
		drawStatY += statsLineHeight;

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
					highLevelStatNamesToUse.get(statType));
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
					"" + Level.player.getEffectiveHighLevelStat(statType));
			highLevelStatButtons.get(statType).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveHighLevelStat(statType));
			drawStatY += statsLineHeight;
		}

		drawStatY += statsLineHeight;

		// DAMAGE

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
					offensiveStatNamesToUse.get(statType));
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
					"" + Level.player.getEffectiveOffensiveStat(statType));
			offensiveStatButtons.get(statType).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveOffensiveStat(statType));
			drawStatY += statsLineHeight;
		}
		drawStatY += statsLineHeight;

		// RESISTANCES

		for (OFFENSIVE_STATS statType : OFFENSIVE_STATS.values()) {
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
					defensiveStatNamesToUse.get(statType));
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null, Color.WHITE,
					"" + Level.player.getEffectiveDefensiveStat(statType));
			defensiveStatButtons.get(statType).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveDefensiveStat(statType));
			drawStatY += statsLineHeight;
		}
		drawStatY += statsLineHeight;

	}

	@Override
	public void scroll(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drag(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub
		
	}

}
