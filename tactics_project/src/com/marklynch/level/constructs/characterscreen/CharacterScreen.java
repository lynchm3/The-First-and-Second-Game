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

	public static String SLASH_DAMAGE = "SLASH DAMAGE";
	public static String BLUNT_DAMAGE = "BLUNT DAMAGE";
	public static String PIERCE_DAMAGE = "PIERCE DAMAGE";
	public static String FIRE_DAMAGE = "FIRE DAMAGE";
	public static String WATER_DAMAGE = "WATER DAMAGE";
	public static String ELECTRICAL_DAMAGE = "ELECTRICAL DAMAGE";
	public static String POSION_DAMAGE = "POSION DAMAGE";
	public static String BLEEDING_DAMAGE = "BLEEDING DAMAGE";
	public static String HEALING = "HEALING";
	public static StringWithColor SLASH_DAMAGE_WHITE = new StringWithColor(SLASH_DAMAGE, Color.WHITE);
	public static StringWithColor BLUNT_DAMAGE_WHITE = new StringWithColor(BLUNT_DAMAGE, Color.WHITE);
	public static StringWithColor PIERCE_DAMAGE_WHITE = new StringWithColor(PIERCE_DAMAGE, Color.WHITE);
	public static StringWithColor FIRE_DAMAGE_WHITE = new StringWithColor(FIRE_DAMAGE, Color.WHITE);
	public static StringWithColor WATER_DAMAGE_WHITE = new StringWithColor(WATER_DAMAGE, Color.WHITE);
	public static StringWithColor ELECTRICAL_DAMAGE_WHITE = new StringWithColor(ELECTRICAL_DAMAGE, Color.WHITE);
	public static StringWithColor POSION_DAMAGE_WHITE = new StringWithColor(POSION_DAMAGE, Color.WHITE);
	public static StringWithColor BLEEDING_DAMAGE_WHITE = new StringWithColor(BLEEDING_DAMAGE, Color.WHITE);
	public static StringWithColor HEALING_WHITE = new StringWithColor(HEALING, Color.WHITE);

	public static String SLASH_RESISTANCE = "SLASH RESISTANCE";
	public static String BLUNT_RESISTANCE = "BLUNT RESISTANCE";
	public static String PIERCE_RESISTANCE = "PIERCE RESISTANCE";
	public static String FIRE_RESISTANCE = "FIRE RESISTANCE";
	public static String WATER_RESISTANCE = "WATER RESISTANCE";
	public static String ELECTRICAL_RESISTANCE = "ELECTRICAL RESISTANCE";
	public static String POSION_RESISTANCE = "POSION RESISTANCE";
	public static String BLEEDING_RESISTANCE = "BLEEDING RESISTANCE";
	public static StringWithColor SLASH_RESISTANCE_WHITE = new StringWithColor(SLASH_RESISTANCE, Color.WHITE);
	public static StringWithColor BLUNT_RESISTANCE_WHITE = new StringWithColor(BLUNT_RESISTANCE, Color.WHITE);
	public static StringWithColor PIERCE_RESISTANCE_WHITE = new StringWithColor(PIERCE_RESISTANCE, Color.WHITE);
	public static StringWithColor FIRE_RESISTANCE_WHITE = new StringWithColor(FIRE_RESISTANCE, Color.WHITE);
	public static StringWithColor WATER_RESISTANCE_WHITE = new StringWithColor(WATER_RESISTANCE, Color.WHITE);
	public static StringWithColor ELECTRICAL_RESISTANCE_WHITE = new StringWithColor(ELECTRICAL_RESISTANCE, Color.WHITE);
	public static StringWithColor POSION_RESISTANCE_WHITE = new StringWithColor(POSION_RESISTANCE, Color.WHITE);
	public static StringWithColor BLEEDING_RESISTANCE_WHITE = new StringWithColor(BLEEDING_RESISTANCE, Color.WHITE);

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

			// BASIC STATS
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
			drawStatY += statsLineHeight;

			// DAMAGE
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, SLASH_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveSlashDamage() + " " + Level.player.getEffectiveSlashDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, BLUNT_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBluntDamage() + " " + Level.player.getEffectiveBluntDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, PIERCE_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePierceDamage() + " " + Level.player.getEffectivePierceDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, FIRE_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveFireDamage() + " " + Level.player.getEffectiveFireDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, WATER_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveWaterDamage() + " " + Level.player.getEffectiveWaterDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					ELECTRICAL_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveElectricalDamage() + " "
							+ Level.player.getEffectiveElectricalDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, POSION_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePoisonDamage() + " " + Level.player.getEffectivePoisonDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					BLEEDING_DAMAGE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBleedDamage() + " " + Level.player.getEffectiveBleedDamageTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null, HEALING_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveHealing() + " " + Level.player.getEffectiveHealingTooltip());
			drawStatY += statsLineHeight;
			drawStatY += statsLineHeight;

			// RESISTANCES
			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					SLASH_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveSlashResistance() + " "
							+ Level.player.getEffectiveSlashResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					BLUNT_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBluntResistance() + " "
							+ Level.player.getEffectiveBluntResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					PIERCE_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePierceResistance() + " "
							+ Level.player.getEffectivePierceResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					FIRE_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveFireResistance() + " " + Level.player.getEffectiveFireResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					WATER_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveWaterResistance() + " "
							+ Level.player.getEffectiveWaterResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					ELECTRICAL_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveElectricalResistance() + " "
							+ Level.player.getEffectiveElectricalResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					POSION_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectivePoisonResistance() + " "
							+ Level.player.getEffectivePoisonResistanceTooltip());
			drawStatY += statsLineHeight;

			TextUtils.printTextWithImages(statLabelsX, drawStatY, Integer.MAX_VALUE, false, null,
					BLEEDING_RESISTANCE_WHITE);
			TextUtils.printTextWithImages(statValuesX, drawStatY, Integer.MAX_VALUE, false, null,
					Level.player.getEffectiveBleedResistance() + " "
							+ Level.player.getEffectiveBleedResistanceTooltip());
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
