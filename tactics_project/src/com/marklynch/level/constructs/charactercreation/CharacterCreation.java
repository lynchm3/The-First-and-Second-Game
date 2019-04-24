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

	String left = "<";
	String right = ">";

	static LevelButton buttonHair;
	static LevelButton buttonHairLeft;
	static LevelButton buttonHairRight;

	static LevelButton buttonSkin;
	static LevelButton buttonSkinLeft;
	static LevelButton buttonSkinRight;

	static LevelButton buttonOnTorso;
	static LevelButton buttonOnTorsoLeft;
	static LevelButton buttonOnTorsoRight;

	static LevelButton buttonOnLegs;
	static LevelButton buttonOnLegsLeft;
	static LevelButton buttonOnLegsRight;

	static LevelButton buttonOnHead;
	static LevelButton buttonOnHeadLeft;
	static LevelButton buttonOnHeadRight;

	static LevelButton buttonWeapon;
	static LevelButton buttonWeaponLeft;
	static LevelButton buttonWeaponRight;

	static LevelButton buttonItem;
	static LevelButton buttonItemLeft;
	static LevelButton buttonItemRight;

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

		float mainButtonCount = 0;

		buttonItem = new LevelButton(buttonsX, buttonsY + 30f * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", item, true, false, Color.BLACK, Color.WHITE, null);
		buttonItem.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonItem);

		buttonItemLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonItemLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonItemLeft);

		buttonItemRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonItemRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonItemRight);

		mainButtonCount++;

		buttonWeapon = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", weapon, true, false, Color.BLACK, Color.WHITE, null);
		buttonWeapon.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonWeapon);

		buttonWeaponLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonWeaponLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonWeaponLeft);

		buttonWeaponRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonWeaponRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonWeaponRight);

		mainButtonCount++;

		buttonOnHead = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", onHead, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnHead.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnHead);

		buttonOnHeadLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnHeadLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnHeadLeft);

		buttonOnHeadRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnHeadRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnHeadRight);

		mainButtonCount++;

		buttonOnLegs = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", onLegs, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnLegs.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnLegs);

		buttonOnLegsLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnLegsLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnLegsLeft);

		buttonOnLegsRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnLegsRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnLegsRight);

		mainButtonCount++;

		buttonOnTorso = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", onTorso, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnTorso.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnTorso);

		buttonOnTorsoLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnTorsoLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnTorsoLeft);

		buttonOnTorsoRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnTorsoRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOnTorsoRight);

		mainButtonCount++;

		buttonSkin = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", skin, true, false, Color.BLACK, Color.WHITE, null);
		buttonSkin.setClickListener(new ClickListener() {
			@Override
			public void click() {

			}
		});
		buttons.add(buttonSkin);

		buttonSkinLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonSkinLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonSkinLeft);

		buttonSkinRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonSkinRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonSkinRight);

		mainButtonCount++;

		buttonHair = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", hair, true, false, Color.BLACK, Color.WHITE, null);
		buttonHair.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonHair);

		buttonHairLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonHairLeft);

		buttonHairRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonHairRight);

		mainButtonCount++;

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
