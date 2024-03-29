package com.marklynch.level.constructs.charactercreation;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.objects.actors.Human;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;

public class CharacterCreation implements Draggable, Scrollable {

	String hairType = "Hair Type";
	String hairColor = "Hair Color";
	String skin = "Skin";
	String onTorso = "Jumper";
	String onLegs = "Pants";
	String onHead = "Hat";
	String weapon = "Weapon";
	String item = "Item";

	String left = "<";
	String right = ">";

	static LevelButton buttonHairType;
	static LevelButton buttonHairTypeLeft;
	static LevelButton buttonHairTypeRight;
	static int hairTypeIndex = 0;

	static LevelButton buttonHairColor;
	static LevelButton buttonHairColorLeft;
	static LevelButton buttonHairColorRight;
	static int hairColorIndex = 0;

	static LevelButton buttonSkin;
	static LevelButton buttonSkinLeft;
	static LevelButton buttonSkinRight;

	static LevelButton buttonOnTorso;
	static LevelButton buttonOnTorsoLeft;
	static LevelButton buttonOnTorsoRight;
	static int onTorsoColorIndex = 0;

	static LevelButton buttonOnLegs;
	static LevelButton buttonOnLegsLeft;
	static LevelButton buttonOnLegsRight;
	static int onLegsColorIndex = 0;

	static LevelButton buttonOnHead;
	static LevelButton buttonOnHeadLeft;
	static LevelButton buttonOnHeadRight;
	static int onHeadColorIndex = 0;

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

	public static CopyOnWriteArrayList<LevelButton> buttons = new CopyOnWriteArrayList<LevelButton>();

	public CopyOnWriteArrayList<Link> logLinks = new CopyOnWriteArrayList<Link>();
	public CopyOnWriteArrayList<Link> conversationLinks = new CopyOnWriteArrayList<Link>();
	public CopyOnWriteArrayList<Link> objectiveLinks = new CopyOnWriteArrayList<Link>();

//	public static Texture textureMainMenu;

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
//				itemTypeIndex--;
//				if (itemTypeIndex < 0) {
//					itemTypeIndex = Human.itemTextures.size() - 1;
//				}
//				Level.player.itemImageTexture = Human.itemTextures.get(itemTypeIndex);
			}
		});
		buttons.add(buttonItemLeft);

		buttonItemRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonItemRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
//				itemTypeIndex++;
//				if (itemTypeIndex >= Human.itemTextures.size()) {
//					itemTypeIndex = 0;
//				}
//				Level.player.itemImageTexture = Human.itemTextures.get(itemTypeIndex);
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
//				weaponTypeIndex--;
//				if (weaponTypeIndex < 0) {
//					weaponTypeIndex = Human.weaponTextures.size() - 1;
//				}
//				Level.player.weaponTexture = Human.weaponTextures.get(weaponTypeIndex);
			}
		});
		buttons.add(buttonWeaponLeft);

		buttonWeaponRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonWeaponRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
//				weaponTypeIndex++;
//				if (weaponTypeIndex >= Human.weaponTextures.size()) {
//					weaponTypeIndex = 0;
//				}
//				Level.player.weaponTexture = Human.weaponTextures.get(weaponTypeIndex);
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
				onHeadColorIndex--;
				if (onHeadColorIndex < 0) {
					onHeadColorIndex = Human.helmetColors.size() - 1;
				}
				Level.player.helmetColor = Human.helmetColors.get(onHeadColorIndex);
			}
		});
		buttons.add(buttonOnHeadLeft);

		buttonOnHeadRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnHeadRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
				onHeadColorIndex++;
				if (onHeadColorIndex >= Human.helmetColors.size()) {
					onHeadColorIndex = 0;
				}
				Level.player.helmetColor = Human.helmetColors.get(onHeadColorIndex);
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
				onLegsColorIndex--;
				if (onLegsColorIndex < 0) {
					onLegsColorIndex = Human.legArmorColors.size() - 1;
				}
				Level.player.legArmorColor = Human.legArmorColors.get(onLegsColorIndex);
			}
		});
		buttons.add(buttonOnLegsLeft);

		buttonOnLegsRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnLegsRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
				onLegsColorIndex++;
				if (onLegsColorIndex >= Human.legArmorColors.size()) {
					onLegsColorIndex = 0;
				}
				Level.player.legArmorColor = Human.legArmorColors.get(hairColorIndex);
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
				onTorsoColorIndex--;
				if (onTorsoColorIndex < 0) {
					onTorsoColorIndex = Human.bodyArmors.size() - 1;
				}
				Level.player.bodyArmor = Human.bodyArmors.get(onTorsoColorIndex);
			}
		});
		buttons.add(buttonOnTorsoLeft);

		buttonOnTorsoRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonOnTorsoRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
				onTorsoColorIndex++;
				if (onTorsoColorIndex >= Human.bodyArmors.size()) {
					onTorsoColorIndex = 0;
				}
				Level.player.bodyArmor = Human.bodyArmors.get(onTorsoColorIndex);
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
				hairTypeIndex--;
				if (hairTypeIndex < 0) {
					hairTypeIndex = Human.hairTextures.size() - 1;
				}
				Level.player.hairImageTexture = Human.hairTextures.get(hairTypeIndex);
			}
		});
		buttons.add(buttonSkinLeft);

		buttonSkinRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonSkinRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
				hairColorIndex++;
				if (hairColorIndex >= Human.hairTextures.size()) {
					hairColorIndex = 0;
				}
				Level.player.hairColor = Human.hairColors.get(hairColorIndex);
			}
		});
		buttons.add(buttonSkinRight);

		mainButtonCount++;

		buttonHairColor = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", hairColor, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairColor.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonHairColor);

		buttonHairColorLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairColorLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
				hairColorIndex--;
				if (hairColorIndex < 0) {
					hairColorIndex = Human.hairColors.size() - 1;
				}
				Level.player.hairColor = Human.hairColors.get(hairColorIndex);
			}
		});
		buttons.add(buttonHairColorLeft);

		buttonHairColorRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairColorRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
				hairColorIndex++;
				if (hairColorIndex >= Human.hairColors.size()) {
					hairColorIndex = 0;
				}
				Level.player.hairColor = Human.hairColors.get(hairColorIndex);
			}
		});
		buttons.add(buttonHairColorRight);

		mainButtonCount++;

		buttonHairType = new LevelButton(buttonsX, buttonsY + 30 * mainButtonCount, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", hairType, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairType.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonHairType);

		buttonHairTypeLeft = new LevelButton(buttonsX - 10, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", left, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairTypeLeft.setClickListener(new ClickListener() {
			@Override
			public void click() {
				hairTypeIndex--;
				if (hairTypeIndex < 0) {
					hairTypeIndex = Human.hairTextures.size() - 1;
				}
				Level.player.hairImageTexture = Human.hairTextures.get(hairTypeIndex);
			}
		});
		buttons.add(buttonHairTypeLeft);

		buttonHairTypeRight = new LevelButton(buttonsX + 70, buttonsY + 30 * mainButtonCount, 10f, 30f,
				"end_turn_button.png", "end_turn_button.png", right, true, false, Color.BLACK, Color.WHITE, null);
		buttonHairTypeRight.setClickListener(new ClickListener() {
			@Override
			public void click() {
				hairTypeIndex++;
				if (hairTypeIndex >= Human.hairTextures.size()) {
					hairTypeIndex = 0;
				}
				Level.player.hairImageTexture = Human.hairTextures.get(hairTypeIndex);
			}
		});
		buttons.add(buttonHairTypeRight);

		mainButtonCount++;

		resize();
	}

	public static void loadStaticImages() {
//		textureMainMenu = ResourceUtils.getGlobalImage("main_menu.png", false);
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

		// Black cover
		QuadUtils.drawQuad(translucentBlack, 0, 0, Game.windowWidth, Game.windowHeight);

		QuadUtils.drawQuad(Color.BLACK, 0, 0, blackBarWidth, Game.windowHeight);

//		TextureUtils.drawTexture(textureMainMenu, titleX, titleY, titleX + 640, titleY + 128);

		// Buttons
		for (Button button : buttons) {
			button.draw();
		}

		// Draw actor
		float actorScale = 2f;
		int actorX = (int) (Game.halfWindowWidth);// - Game.level.player.width / 2 * actorScale);
		int actorY = (int) (Game.halfWindowHeight);// - Game.level.player.height / 2 * actorScale);

		Level.player.drawActor(actorX, actorY, 1, false, actorScale, actorScale, 0f, Integer.MIN_VALUE,
				Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, TextureUtils.neutralColor, true, false,
				Level.player.backwards, false, false);
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
