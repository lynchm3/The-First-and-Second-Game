package com.marklynch.level.constructs.mainmenu;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.data.Load;
import com.marklynch.data.Save;
import com.marklynch.editor.Editor;
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

public class MainMenu implements Draggable, Scrollable {

	String continue_ = "Continue";
	String newGame = "New Game";
	String save = "Save";
	String load = "Load";
	String options = "Options";
	String exitGame = "Exit Game";

	static LevelButton buttonContinue;
	static LevelButton buttonNewGame;
	static LevelButton buttonSave;
	static LevelButton buttonLoad;
	static LevelButton buttonOptions;
	static LevelButton buttonExitGame;

	int buttonsX = 100;
	int buttonsY = 100;

	public boolean showing = true;

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

	public MainMenu() {

		buttonExitGame = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", exitGame, true, false, Color.BLACK, Color.WHITE, null);
		buttonExitGame.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.ended = true;
			}
		});
		buttons.add(buttonExitGame);

		buttonOptions = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", options, true, false, Color.BLACK, Color.WHITE, null);
		buttonOptions.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(buttonOptions);

		buttonLoad = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", load, true, false, Color.BLACK, Color.WHITE, null);
		buttonLoad.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Load.load();
			}
		});
		buttons.add(buttonLoad);

		buttonSave = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", save, true, false, Color.BLACK, Color.WHITE, null);
		buttonSave.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Save.save();
			}
		});
		buttons.add(buttonSave);

		buttonNewGame = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", newGame, true, false, Color.BLACK, Color.WHITE, null);
		buttonNewGame.setClickListener(new ClickListener() {
			@Override
			public void click() {

				Load.clearEverything();
				Editor.createDefaultLevel();

			}
		});
		buttons.add(buttonNewGame);

		buttonContinue = new LevelButton(buttonsX, buttonsY + 30 * buttons.size(), 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", continue_, true, false, Color.BLACK, Color.WHITE, null);
		buttonContinue.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.openCloseMainMenu();
			}
		});
		buttons.add(buttonContinue);

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
