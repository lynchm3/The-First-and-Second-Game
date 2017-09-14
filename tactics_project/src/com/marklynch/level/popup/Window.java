package com.marklynch.level.popup;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class Window implements Draggable {

	public GameObject gameObject;
	public float width;
	public float height;
	public float resistancesTextX;
	public float resistancesImageX;
	public float resistancesY;
	public boolean minimised = false;
	public float drawPositionX, drawPositionY;
	public LevelButton closeButton, minimiseButton, titleBarButton;
	public int titleBarHeight = 20;
	public int borderWidth = 2;
	public static Object[] unknownStats = new Object[] { "??" };
	public Actor actor;

	public Window(GameObject gameObject) {
		this.gameObject = gameObject;
		if (gameObject instanceof Actor)
			actor = (Actor) gameObject;
		drawPositionX = 500;
		drawPositionY = 10;
		this.width = gameObject.imageTexture.getWidth() + borderWidth * 2;
		this.height = gameObject.imageTexture.getHeight() + titleBarHeight + borderWidth;

		if (actor != null) {
			width += 200;
			if (height < 256)
				height = 256;

			resistancesImageX = width - 200 + 8;
			resistancesTextX = width - 200 + 32;
			resistancesY = titleBarHeight + borderWidth;
		}

		closeButton = new LevelButton(drawPositionX + width - 20, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "X", true, true, Color.BLACK, Color.WHITE);
		closeButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.popupPinneds.remove(Window.this);
			}
		});

		minimiseButton = new LevelButton(drawPositionX + width - 40, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "_", true, true, Color.BLACK, Color.WHITE);
		minimiseButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Window.this.minimised = !Window.this.minimised;
			}
		});

		titleBarButton = new LevelButton(drawPositionX, drawPositionY, width, 20f, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE);
		titleBarButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Window.this.minimised = !Window.this.minimised;
			}
		});

		// if (objects.length == 1)
		// this.width = Game.font.getWidth((CharSequence) objects[0]) + 10;
		// this.height = 40;
	}

	public void draw() {

		if (!minimised) {
			// Background
			QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
			// Image
			TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX + borderWidth,
					drawPositionY + titleBarHeight, drawPositionX + gameObject.imageTexture.getWidth(),
					drawPositionY + gameObject.imageTexture.getHeight());

			if (actor != null) {
				drawResistances(actor);
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

	private void drawResistances(Actor actor) {
		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(actor.templateId);

		// Slash resistance

		TextureUtils.drawTexture(getGlobalImage("action_slash.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 20);
		if (bestiaryKnowledge.slashResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY, width - 40,
					false, false, null, "" + gameObject.slashResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY, width - 40,
					false, false, null, unknownStats);

		}

		// Blunt resistance

		TextureUtils.drawTexture(getGlobalImage("action_blunt.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 30, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 30 + 20);
		if (bestiaryKnowledge.bluntResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 30,
					width - 40, false, false, null, "" + gameObject.bluntResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 30,
					width - 40, false, false, null, unknownStats);

		}

		// Pierce resistance

		TextureUtils.drawTexture(getGlobalImage("action_pierce.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 60, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 60 + 20);
		if (bestiaryKnowledge.pierceResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 60,
					width - 40, false, false, null, "" + gameObject.pierceResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 60,
					width - 40, false, false, null, unknownStats);

		}

		// Fire resistance
		TextureUtils.drawTexture(getGlobalImage("action_burn.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 90, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 90 + 20);
		if (bestiaryKnowledge.fireResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 90,
					width - 40, false, false, null, "" + gameObject.fireResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 90,
					width - 40, false, false, null, unknownStats);

		}

		// Water resistance
		TextureUtils.drawTexture(getGlobalImage("action_douse.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 120, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 120 + 20);
		if (bestiaryKnowledge.waterResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 120,
					width - 40, false, false, null, "" + gameObject.waterResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 120,
					width - 40, false, false, null, unknownStats);

		}

		// Electric resistance

		TextureUtils.drawTexture(getGlobalImage("action_electric.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 150, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 150 + 20);
		if (bestiaryKnowledge.electricResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 150,
					width - 40, false, false, null, "" + gameObject.electricResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 150,
					width - 40, false, false, null, unknownStats);

		}

		// Poison resistance
		TextureUtils.drawTexture(getGlobalImage("action_poison.png"), drawPositionX + resistancesImageX,
				drawPositionY + resistancesY + 180, drawPositionX + resistancesImageX + 20,
				drawPositionY + resistancesY + 180 + 20);
		if (bestiaryKnowledge.poisonResistance) {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 180,
					width - 40, false, false, null, "" + gameObject.poisonResistance);

		} else {
			TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + 180,
					width - 40, false, false, null, unknownStats);

		}

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
		if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
				&& mouseY < drawPositionY + height) {
			return true;
		}
		return false;
	}

	public void bringToFront() {
		Game.level.popupPinneds.remove(this);
		Game.level.popupPinneds.add(this);
	}
}
