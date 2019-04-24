package com.marklynch.level.constructs.charactercreation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class CharacterCreation implements Draggable, Scrollable {

	String hair = "Hair";
	String skin = "Skin";
	String onTorso = "On Torso";
	String onLegs = "On Legs";
	String onHead = "On Head";
	String weapon = "Weapon";
	String item = "Item";

	static LevelButton buttonHair;
	static LevelButton buttonSkin;
	static LevelButton buttonOnTorso;
	static LevelButton buttonOnLegs;
	static LevelButton buttonOnHead;
	static LevelButton buttonWeapon;
	static LevelButton buttonItem;

	int buttonsX = 100;
	int buttonsY = 100;

	public boolean showing = false;

	int titleX;
	int titleY;

	transient static int bottomBorderHeight;

	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();

	public ArrayList<Link> logLinks = new ArrayList<Link>();
	public ArrayList<Link> conversationLinks = new ArrayList<Link>();
	public ArrayList<Link> objectiveLinks = new ArrayList<Link>();

	public static Texture textureMainMenu;

	Color translucentBlack = new Color(0f, 0f, 0f, 0.5f);
	public final static float blackBarWidth = 300;

	public CharacterCreation() {

		buttonItem = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", item, true, false, Color.BLACK, Color.WHITE, null);
		buttonItem.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonItem);

		buttonWeapon = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", weapon, true, false, Color.BLACK, Color.WHITE, null);
		buttonWeapon.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonWeapon);

		buttonOnHead = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", onHead, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnHead.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnHead);

		buttonOnLegs = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", onLegs, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnLegs.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnLegs);

		buttonOnTorso = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", onTorso, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnTorso.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnTorso);

		buttonSkin = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", skin, true, false, Color.BLACK, Color.WHITE, null);
		buttonSkin.setClickListener(new ClickListener() {
			@Override
			public void click() {

			}
		});
		buttons.add(buttonSkin);

		buttonHair = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", hair, true, false, Color.BLACK, Color.WHITE, null);
		buttonHair.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonHair);

		resize();
	}

	public static void loadStaticImages() {
		textureMainMenu = ResourceUtils.getGlobalImage("main_menu.png", false);
	}

	public void resize() {

		titleX = (int) (Game.windowWidth / 2 - 640 / 2);
		titleY = (int) (Game.windowHeight / 2 - 128 / 2);
		bottomBorderHeight = 384;

	}

	public void open() {
		resize();
		showing = true;

	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(translucentBlack, 0, 0, Game.windowWidth, Game.windowHeight);

		QuadUtils.drawQuad(Color.BLACK, 0, 0, blackBarWidth, Game.windowHeight);

		TextureUtils.drawTexture(textureMainMenu, titleX, titleY, titleX + 640, titleY + 128);

		// Buttons
		for (Button button : buttons) {
			button.draw();
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

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub

	}

}
