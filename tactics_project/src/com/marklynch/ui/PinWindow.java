package com.marklynch.ui;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class PinWindow implements Draggable {

	public GameObject gameObject;
	public float width;
	public float height;

	public boolean minimised = false;
	public float drawPositionX, drawPositionY;
	public LevelButton closeButton, minimiseButton, titleBarButton;
	public int titleBarHeight = 20;
	public int borderWidth = 2;
	public static Object[] unknownStats = new Object[] { "??" };
	public Actor actor;

	// Actor

	// Stats for actor
	public float statsImageX;
	public float statsTextX;
	public float statsY;
	public float resistancesImageX;
	public float resistancesTextX;
	public float resistancesY;

	// Powers for actor
	public float powersImageX;
	public float powersTextX;
	public float powersTitleY;
	public float powersListY;

	// Equipped weapon for actor

	// Practical damage

	public PinWindow(GameObject gameObject) {
		this.gameObject = gameObject;
		if (gameObject instanceof Actor)
			actor = (Actor) gameObject;
		drawPositionX = 500;
		drawPositionY = 10;
		this.width = gameObject.imageTexture.getWidth() + borderWidth * 2;
		this.height = gameObject.imageTexture.getHeight() + titleBarHeight + borderWidth;

		if (actor != null) {
			width += 200;
			if (height < 512)
				height = 512;

			// stats
			statsImageX = width - 200 + 8;
			statsTextX = width - 200 + 32;
			statsY = titleBarHeight + borderWidth;

			// resistances
			resistancesImageX = width - 100 + 8;
			resistancesTextX = width - 100 + 32;
			resistancesY = titleBarHeight + borderWidth;

			// powers
			powersImageX = 8;
			powersTextX = 32;
			powersTitleY = 300;
			powersListY = 330;

		}

		closeButton = new LevelButton(drawPositionX + width - 20, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "X", true, true, Color.BLACK, Color.WHITE, "Close window");
		closeButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.popupPinneds.remove(PinWindow.this);
			}
		});

		minimiseButton = new LevelButton(drawPositionX + width - 40, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "_", true, true, Color.BLACK, Color.WHITE, "Minimise window");
		minimiseButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				PinWindow.this.minimised = !PinWindow.this.minimised;
			}
		});

		titleBarButton = new LevelButton(drawPositionX, drawPositionY, width, 20f, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		titleBarButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				PinWindow.this.minimised = !PinWindow.this.minimised;
			}
		});

		// if (objects.length == 1)
		// this.width = Game.font.getWidth((CharSequence) objects[0]) + 10;
		// this.height = 40;
	}

	public void drawLine() {
		if (!minimised && gameObject.squareGameObjectIsOn != null && gameObject.squareGameObjectIsOn.visibleToPlayer) {

			// LINE
			float lineX1 = this.drawPositionX + this.width / 2;
			float lineY1 = drawPositionY + height / 2;

			int gameObjectX = (gameObject.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_HEIGHT);
			int gameObjectY = (gameObject.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT);
			float lineX2 = (Game.windowWidth / 2) + (Game.zoom
					* (gameObjectX - Game.windowWidth / 2 + Game.getDragXWithOffset() + Game.HALF_SQUARE_WIDTH));
			float lineY2 = (Game.windowHeight / 2) + (Game.zoom
					* (gameObjectY - Game.windowHeight / 2 + Game.getDragYWithOffset() + Game.HALF_SQUARE_HEIGHT));

			// Draw line from window to subject
			LineUtils.drawLine(Color.BLACK, lineX1, lineY1, lineX2, lineY2, 5);
		}
	}

	public void drawStaticUI() {

		if (!minimised) {
			// QuadUtils.drawQuad(Color.BLACK, drawPositionX,
			// Game.halfWindowWidth, drawPositionY, Game.halfWindowHeight);

			// Background
			QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);

			// GameObject Image
			TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX + borderWidth,
					drawPositionY + titleBarHeight, drawPositionX + gameObject.imageTexture.getWidth(),
					drawPositionY + gameObject.imageTexture.getHeight());

			if (actor != null) {
				drawStats(actor);
			}
		}

		// Titlebar
		titleBarButton.draw();

		// Title bar text
		TextUtils.printTextWithImages(drawPositionX + 2, drawPositionY, width - 40, false, false, null, gameObject);

		// Title bar buttons
		this.closeButton.draw();
		this.minimiseButton.draw();

		// Borders (left,right,bottom)
		if (!minimised) {

			QuadUtils.drawQuad(Color.BLACK, drawPositionX, drawPositionX + 2, drawPositionY, drawPositionY + height);
			QuadUtils.drawQuad(Color.BLACK, drawPositionX + width - 2, drawPositionX + width, drawPositionY,
					drawPositionY + height);
			QuadUtils.drawQuad(Color.BLACK, drawPositionX, drawPositionX + width, drawPositionY + height - 2,
					drawPositionY + height);
		}
	}

	private void drawStats(Actor actor) {
		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(actor.templateId);

		// GENERAL

		// Faction Image
		// TextureUtils.drawTexture(actor.faction.imageTexture, drawPositionX +
		// borderWidth,
		// drawPositionY + titleBarHeight + actor.imageTexture.getHeight(),
		// drawPositionX + borderWidth + 20,
		// drawPositionY + titleBarHeight + actor.imageTexture.getHeight() +
		// 20);
		if (bestiaryKnowledge.faction) {
			TextUtils.printTextWithImages(drawPositionX + borderWidth,
					drawPositionY + titleBarHeight + actor.imageTexture.getHeight(), 128, false, false, null,
					actor.faction);
		}

		// Name
		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY, drawPositionX + statsImageX + 20, drawPositionY + statsY + 20);
		if (bestiaryKnowledge.name) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY, width - 40, false, false,
					null, actor.name);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY, width - 40, false, false,
					null, unknownStats);

		}

		// Level

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY + 30, drawPositionX + statsImageX + 20, drawPositionY + statsY + 30 + 20);
		if (bestiaryKnowledge.level) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 30, width - 40, false,
					false, null, "" + actor.actorLevel);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 30, width - 40, false,
					false, null, unknownStats);

		}

		// total health

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY + 60, drawPositionX + statsImageX + 20, drawPositionY + statsY + 60 + 20);
		if (bestiaryKnowledge.totalHealth) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 60, width - 40, false,
					false, null, "" + actor.totalHealth);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 60, width - 40, false,
					false, null, unknownStats);

		}

		// STATS
		TextUtils.printTextWithImages(drawPositionX + statsImageX, drawPositionY + statsY + 120, width - 40, false,
				false, null, "STATS");

		// strength

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY + 150, drawPositionX + statsImageX + 20, drawPositionY + statsY + 150 + 20);
		if (bestiaryKnowledge.strength) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 150, width - 40, false,
					false, null, "" + actor.strength);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 150, width - 40, false,
					false, null, unknownStats);

		}

		// dexterity

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY + 180, drawPositionX + statsImageX + 20, drawPositionY + statsY + 180 + 20);
		if (bestiaryKnowledge.dexterity) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 180, width - 40, false,
					false, null, "" + actor.dexterity);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 180, width - 40, false,
					false, null, unknownStats);

		}

		// intelligence

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY + 210, drawPositionX + statsImageX + 20, drawPositionY + statsY + 210 + 20);
		if (bestiaryKnowledge.intelligence) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 210, width - 40, false,
					false, null, "" + actor.intelligence);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 210, width - 40, false,
					false, null, unknownStats);

		}

		// endurance

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + statsImageX,
				drawPositionY + statsY + 240, drawPositionX + statsImageX + 20, drawPositionY + statsY + 240 + 20);
		if (bestiaryKnowledge.endurance) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 240, width - 40, false,
					false, null, "" + actor.endurance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 240, width - 40, false,
					false, null, unknownStats);

		}

		// RESISTANCES
		TextUtils.printTextWithImages(drawPositionX + resistancesImageX, drawPositionY + resistancesY + 30, width - 40,
				false, false, null, "RESISTANCES");

		// Slash resistance

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 60, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 60 + 20);
		if (bestiaryKnowledge.slashResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 60,
					width - 40, false, false, null, "" + gameObject.slashResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 60,
					width - 40, false, false, null, unknownStats);

		}

		// Blunt resistance

		TextureUtils.drawTexture(getGlobalImage("action_blunt.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 90, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 90 + 20);
		if (bestiaryKnowledge.bluntResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 90,
					width - 40, false, false, null, "" + gameObject.bluntResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 90,
					width - 40, false, false, null, unknownStats);

		}

		// Pierce resistance

		TextureUtils.drawTexture(getGlobalImage("action_pierce.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 120, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 120 + 20);
		if (bestiaryKnowledge.pierceResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 120,
					width - 40, false, false, null, "" + gameObject.pierceResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 120,
					width - 40, false, false, null, unknownStats);

		}

		// Fire resistance
		TextureUtils.drawTexture(getGlobalImage("action_burn.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 150, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 150 + 20);
		if (bestiaryKnowledge.fireResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 150,
					width - 40, false, false, null, "" + gameObject.fireResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 150,
					width - 40, false, false, null, unknownStats);

		}

		// Water resistance
		TextureUtils.drawTexture(getGlobalImage("action_douse.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 180, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 180 + 20);
		if (bestiaryKnowledge.waterResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 180,
					width - 40, false, false, null, "" + gameObject.waterResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 180,
					width - 40, false, false, null, unknownStats);

		}

		// Electric resistance

		TextureUtils.drawTexture(getGlobalImage("action_electric.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 210, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 210 + 20);
		if (bestiaryKnowledge.electricResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 210,
					width - 40, false, false, null, "" + gameObject.electricResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 210,
					width - 40, false, false, null, unknownStats);

		}

		// Poison resistance
		TextureUtils.drawTexture(getGlobalImage("action_poison.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 240, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 240 + 20);
		if (bestiaryKnowledge.poisonResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 240,
					width - 40, false, false, null, "" + gameObject.poisonResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 240,
					width - 40, false, false, null, unknownStats);

		}

		// POWERS
		TextUtils.printTextWithImages(drawPositionX + powersImageX, drawPositionY + powersTitleY, width - 40, false,
				false, null, "POWERS");

		if (bestiaryKnowledge.powers && actor.powers.size() == 0) {
			TextUtils.printTextWithImages(drawPositionX + powersTextX, drawPositionY + powersListY, width - 40, false,
					false, null, "None");
		} else if (bestiaryKnowledge.powers) {
			int powersDrawn = 0;
			for (Power power : actor.powers) {
				TextureUtils.drawTexture(power.image, drawPositionX + powersImageX,
						drawPositionY + powersListY + powersDrawn * 30, drawPositionX + powersImageX + 20,
						drawPositionY + powersListY + powersDrawn * 30 + 20);
				TextUtils.printTextWithImages(drawPositionX + powersTextX,
						drawPositionY + powersListY + powersDrawn * 30, width - 40, false, false, null, power.name);
				powersDrawn++;
			}
		} else {
			TextUtils.printTextWithImages(drawPositionX + powersTextX, drawPositionY + powersListY, width - 40, false,
					false, null, unknownStats);
		}

		// PRACTICAL DAMAGE

	}

	public boolean mouseOverCloseButton(float mouseX, float mouseY) {
		return closeButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean mouseOverMinimiseButton(float mouseX, float mouseY) {
		return minimiseButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean mouseOverInvisibleMinimiseButton(float mouseX, float mouseY) {
		return titleBarButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		this.drawPositionX += dragX;
		this.drawPositionY -= dragY;
		this.titleBarButton.x += dragX;
		this.titleBarButton.y -= dragY;
		this.closeButton.x += dragX;
		this.closeButton.y -= dragY;
		this.minimiseButton.x += dragX;
		this.minimiseButton.y -= dragY;

		if (drawPositionX < 0) {
			drawPositionX = titleBarButton.x = 0;
			this.closeButton.x = drawPositionX + width - 20;
			this.minimiseButton.x = drawPositionX + width - 40;
		} else if (drawPositionX > Game.windowWidth - 20) {
			drawPositionX = titleBarButton.x = Game.windowWidth - 20;
			this.closeButton.x = drawPositionX + width - 20;
			this.minimiseButton.x = drawPositionX + width - 40;
		}

		if (drawPositionY < 0) {
			drawPositionY = titleBarButton.y = 0;
			this.closeButton.y = drawPositionY;
			this.minimiseButton.y = drawPositionY;
		} else if (drawPositionY > Game.windowHeight - 20) {
			drawPositionY = titleBarButton.y = Game.windowHeight - 20;
			this.closeButton.y = drawPositionY;
			this.minimiseButton.y = drawPositionY;
		}

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (!minimised) {
			if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
					&& mouseY < drawPositionY + height) {
				return true;
			}
		} else {
			if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
					&& mouseY < drawPositionY + titleBarHeight) {
				return true;
			}

		}
		return false;
	}

	public void bringToFront() {
		Game.level.popupPinneds.remove(this);
		Game.level.popupPinneds.add(this);
	}
}
