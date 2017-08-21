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

public class PopupPinned implements Draggable {

	public GameObject gameObject;
	public float width;
	public float height;
	public boolean minimised = false;
	public float drawPositionX, drawPositionY;
	public LevelButton closeButton, minimiseButton, invisibleMinimiseButton;

	public PopupPinned(GameObject gameObject) {
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
				Game.level.popupPinneds.remove(PopupPinned.this);
			}
		});

		minimiseButton = new LevelButton(drawPositionX + width - 40, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "_", true, true, Color.BLACK, Color.WHITE);
		minimiseButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				PopupPinned.this.minimised = !PopupPinned.this.minimised;
			}
		});

		invisibleMinimiseButton = new LevelButton(drawPositionX, drawPositionY, width, 20f, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE);
		invisibleMinimiseButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				PopupPinned.this.minimised = !PopupPinned.this.minimised;
			}
		});

		// if (objects.length == 1)
		// this.width = Game.font.getWidth((CharSequence) objects[0]) + 10;
		// this.height = 40;
	}

	public void draw() {

		if (minimised) {
			QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + 20);
			// TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX,
			// drawPositionY, drawPositionX + width,
			// drawPositionY + height);

		} else {
			QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
			TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX, drawPositionY, drawPositionX + width,
					drawPositionY + height);
		}
		invisibleMinimiseButton.draw();
		TextUtils.printTextWithImages(drawPositionX, drawPositionY, width - 40, false, gameObject);
		this.closeButton.draw();
		this.minimiseButton.draw();
		// TextUtils.printTextWithImages(objects, drawPositionX, drawPositionY,
		// width, true);
	}

	public boolean mouseOverCloseButton(float mouseX, float mouseY) {
		return closeButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean mouseOverMinimiseButton(float mouseX, float mouseY) {
		return minimiseButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean mouseOverInvisibleMinimiseButton(float mouseX, float mouseY) {
		return invisibleMinimiseButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		this.drawPositionX += dragX;
		this.drawPositionY -= dragY;
		this.invisibleMinimiseButton.x += dragX;
		this.invisibleMinimiseButton.y -= dragY;
		this.closeButton.x += dragX;
		this.closeButton.y -= dragY;
		this.minimiseButton.x += dragX;
		this.minimiseButton.y -= dragY;

		if (drawPositionX < 0) {
			drawPositionX = invisibleMinimiseButton.x = 0;
			this.closeButton.x = drawPositionX + width - 20;
			this.minimiseButton.x = drawPositionX + width - 40;
		} else if (drawPositionX > Game.windowWidth - 20) {
			drawPositionX = invisibleMinimiseButton.x = Game.windowWidth - 20;
			this.closeButton.x = drawPositionX + width - 20;
			this.minimiseButton.x = drawPositionX + width - 40;
		}

		if (drawPositionY < 0) {
			drawPositionY = invisibleMinimiseButton.y = 0;
			this.closeButton.y = drawPositionY;
			this.minimiseButton.y = drawPositionY;
		} else if (drawPositionY > Game.windowHeight - 20) {
			drawPositionY = invisibleMinimiseButton.y = Game.windowHeight - 20;
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
}
