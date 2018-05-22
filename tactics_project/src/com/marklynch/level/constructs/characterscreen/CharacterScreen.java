package com.marklynch.level.constructs.characterscreen;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
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

	// Resistances
	public static String SLASH_RESISTANCE = "SLASH RESISTANCE";
	public static String BLUNT_RESISTANCE = "BLUNT RESISTANCE";
	public static String PIERCE_RESISTANCE = "PIERCE RESISTANCE";
	public static String FIRE_RESISTANCE = "FIRE RESISTANCE";
	public static String WATER_RESISTANCE = "WATER RESISTANCE";
	public static String ELECTRICAL_RESISTANCE = "ELECTRICAL RESISTANCE";
	public static String POISON_RESISTANCE = "POSION RESISTANCE";
	public static String BLEED_RESISTANCE = "BLEEDING RESISTANCE";
	public static StringWithColor SLASH_RESISTANCE_WHITE = new StringWithColor(SLASH_RESISTANCE, Color.WHITE);
	public static StringWithColor BLUNT_RESISTANCE_WHITE = new StringWithColor(BLUNT_RESISTANCE, Color.WHITE);
	public static StringWithColor PIERCE_RESISTANCE_WHITE = new StringWithColor(PIERCE_RESISTANCE, Color.WHITE);
	public static StringWithColor FIRE_RESISTANCE_WHITE = new StringWithColor(FIRE_RESISTANCE, Color.WHITE);
	public static StringWithColor WATER_RESISTANCE_WHITE = new StringWithColor(WATER_RESISTANCE, Color.WHITE);
	public static StringWithColor ELECTRICAL_RESISTANCE_WHITE = new StringWithColor(ELECTRICAL_RESISTANCE, Color.WHITE);
	public static StringWithColor POISON_RESISTANCE_WHITE = new StringWithColor(POISON_RESISTANCE, Color.WHITE);
	public static StringWithColor BLEED_RESISTANCE_WHITE = new StringWithColor(BLEED_RESISTANCE, Color.WHITE);

	public static LevelButton healthButton;
	// public static LevelButton strengthButton;
	// public static LevelButton dexterityButton;
	// public static LevelButton intelligenceButton;
	// public static LevelButton enduranceButton;
	public static LevelButton slashDamageButton;
	public static LevelButton bluntDamageButton;
	public static LevelButton pierceDamageButton;
	public static LevelButton fireDamageButton;
	public static LevelButton waterDamageButton;
	public static LevelButton electricalDamageButton;
	public static LevelButton poisonDamageButton;
	public static LevelButton bleedDamageButton;
	public static LevelButton healingButton;
	public static LevelButton slashResistanceButton;
	public static LevelButton bluntResistanceButton;
	public static LevelButton pierceResistanceButton;
	public static LevelButton fireResistanceButton;
	public static LevelButton waterResistanceButton;
	public static LevelButton electricalResistanceButton;
	public static LevelButton poisonResistanceButton;
	public static LevelButton bleedResistanceButton;

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

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			LevelButton button = new LevelButton(0, 0, Game.smallFont.getWidth(highLevelStatNames.get(statType)), 30,
					"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
			highLevelStatButtons.put(statType, button);
			buttons.add(button);
		}

		slashDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(SLASH_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(slashDamageButton);
		bluntDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(BLUNT_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(bluntDamageButton);
		pierceDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(PIERCE_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(pierceDamageButton);
		fireDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(FIRE_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(fireDamageButton);
		waterDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(WATER_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(waterDamageButton);
		electricalDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(ELECTRICAL_DAMAGE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(electricalDamageButton);
		poisonDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(POISON_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(poisonDamageButton);
		bleedDamageButton = new LevelButton(0, 0, Game.smallFont.getWidth(BLEED_DAMAGE), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(bleedDamageButton);
		healingButton = new LevelButton(0, 0, Game.smallFont.getWidth(HEALING), 30, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);

		buttons.add(healingButton);
		slashResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(SLASH_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(slashResistanceButton);
		bluntResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(BLUNT_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(bluntResistanceButton);
		pierceResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(PIERCE_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(pierceResistanceButton);
		fireResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(FIRE_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(fireResistanceButton);
		waterResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(WATER_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(waterResistanceButton);
		electricalResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(ELECTRICAL_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(electricalResistanceButton);
		poisonResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(POISON_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(poisonResistanceButton);
		bleedResistanceButton = new LevelButton(0, 0, Game.smallFont.getWidth(BLEED_RESISTANCE), 30,
				"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		buttons.add(bleedResistanceButton);

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

		// contentX = listX + listWidth + listBorder * 2;
		// contentY = 30;
		// contentBorder = 16;

		listItemHeight = 30;

		bottomBorderHeight = 384;

		int drawStatY = statsY;

		healthButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		highLevelStatButtons.get(HIGH_LEVEL_STATS.STRENGTH).updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		highLevelStatButtons.get(HIGH_LEVEL_STATS.DEXTERITY).updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		highLevelStatButtons.get(HIGH_LEVEL_STATS.INTELLIGENCE).updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		highLevelStatButtons.get(HIGH_LEVEL_STATS.ENDURANCE).updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		drawStatY += statsLineHeight;

		slashDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		bluntDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		pierceDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		fireDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		waterDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		electricalDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		poisonDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		bleedDamageButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		healingButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		drawStatY += statsLineHeight;

		slashResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		bluntResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		pierceResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		fireResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		waterResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		electricalResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		poisonResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
		bleedResistanceButton.updatePosition(statValuesX, drawStatY);
		drawStatY += statsLineHeight;
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

		// healthButton.setTooltipText(Level.player.getEffectiveHealthResistanceTooltip());

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			highLevelStatButtons.get(statType).setTooltipText(Level.player.getEffectiveHighLevelStatTooltip(statType));
		}

		slashDamageButton.setTooltipText(Level.player.getEffectiveSlashDamageTooltip());
		bluntDamageButton.setTooltipText(Level.player.getEffectiveBluntDamageTooltip());
		pierceDamageButton.setTooltipText(Level.player.getEffectivePierceDamageTooltip());
		fireDamageButton.setTooltipText(Level.player.getEffectiveFireDamageTooltip());
		waterDamageButton.setTooltipText(Level.player.getEffectiveWaterDamageTooltip());
		electricalDamageButton.setTooltipText(Level.player.getEffectiveElectricalDamageTooltip());
		poisonDamageButton.setTooltipText(Level.player.getEffectivePoisonDamageTooltip());
		bleedDamageButton.setTooltipText(Level.player.getEffectiveBleedDamageTooltip());
		healingButton.setTooltipText(Level.player.getEffectiveHealingTooltip());

		slashResistanceButton.setTooltipText(Level.player.getEffectiveSlashResistanceTooltip());
		bluntResistanceButton.setTooltipText(Level.player.getEffectiveBluntResistanceTooltip());
		pierceResistanceButton.setTooltipText(Level.player.getEffectivePierceResistanceTooltip());
		fireResistanceButton.setTooltipText(Level.player.getEffectiveFireResistanceTooltip());
		waterResistanceButton.setTooltipText(Level.player.getEffectiveWaterResistanceTooltip());
		electricalResistanceButton.setTooltipText(Level.player.getEffectiveElectricalResistanceTooltip());
		poisonResistanceButton.setTooltipText(Level.player.getEffectivePoisonResistanceTooltip());
		bleedResistanceButton.setTooltipText(Level.player.getEffectiveBleedResistanceTooltip());

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

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, STRENGTH_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					"" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.STRENGTH));
			highLevelStatButtons.get(HIGH_LEVEL_STATS.STRENGTH).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.STRENGTH));
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, DEXTERITY_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					"" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.DEXTERITY));
			highLevelStatButtons.get(HIGH_LEVEL_STATS.DEXTERITY).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.DEXTERITY));
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, INTELLIGENCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					"" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.INTELLIGENCE));
			highLevelStatButtons.get(HIGH_LEVEL_STATS.INTELLIGENCE).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.INTELLIGENCE));
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, ENDURANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					"" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.ENDURANCE));
			highLevelStatButtons.get(HIGH_LEVEL_STATS.ENDURANCE).width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.ENDURANCE));
			drawStatY += statsLineHeight;
			drawStatY += statsLineHeight;

			// DAMAGE
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, SLASH_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveSlashDamage());
			slashDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveSlashDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, BLUNT_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBluntDamage());
			bluntDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveBluntDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, PIERCE_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePierceDamage());
			pierceDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectivePierceDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, FIRE_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveFireDamage());
			fireDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveFireDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, WATER_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveWaterDamage());
			waterDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveWaterDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					ELECTRICAL_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveElectricalDamage());
			electricalDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveElectricalDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, POISON_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePoisonDamage());
			poisonDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectivePoisonDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, BLEED_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBleedDamage());
			bleedDamageButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveBleedDamage());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, HEALING_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveHealing());
			healingButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveHealing());
			drawStatY += statsLineHeight;
			drawStatY += statsLineHeight;

			// RESISTANCES
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					SLASH_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveSlashResistance());
			slashResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveSlashResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					BLUNT_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBluntResistance());
			bluntResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveBluntResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					PIERCE_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePierceResistance());
			pierceResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectivePierceResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					FIRE_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveFireResistance());
			fireResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveFireResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					WATER_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveWaterResistance());
			waterResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveWaterResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					ELECTRICAL_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveElectricalResistance());
			electricalResistanceButton.width = Game.smallFont
					.getWidth("" + Level.player.getEffectiveElectricalResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					POISON_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePoisonResistance());
			poisonResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectivePoisonResistance());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					BLEED_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBleedResistance());
			bleedResistanceButton.width = Game.smallFont.getWidth("" + Level.player.getEffectiveBleedResistance());
			drawStatY += statsLineHeight;
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
