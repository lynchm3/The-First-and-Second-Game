package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
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
	public boolean minimised = false;
	public float drawPositionX, drawPositionY;
	public LevelButton closeButton, minimiseButton, titleBarButton;

	public Window(GameObject gameObject) {
		this.gameObject = gameObject;
		drawPositionX = 500;
		drawPositionY = 10;
		this.width = gameObject.imageTexture.getWidth();
		this.height = gameObject.imageTexture.getHeight();

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
			TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX, drawPositionY, drawPositionX + width,
					drawPositionY + height);
		}

		// Titlebar
		titleBarButton.draw();

		// Title bar text
		TextUtils.printTextWithImages(drawPositionX + 2, drawPositionY, width - 40, false, false, gameObject);

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
