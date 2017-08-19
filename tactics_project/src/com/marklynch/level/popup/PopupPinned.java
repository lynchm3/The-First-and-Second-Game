package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class PopupPinned implements Draggable {

	public GameObject gameObject;
	public float width;
	public float height;
	public float drawPositionX, drawPositionY;
	public LevelButton closeButton;

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

		// if (objects.length == 1)
		// this.width = Game.font.getWidth((CharSequence) objects[0]) + 10;
		// this.height = 40;
	}

	public void draw() {

		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
		TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX, drawPositionY, drawPositionX + width,
				drawPositionY + height);
		this.closeButton.draw();
		// TextUtils.printTextWithImages(objects, drawPositionX, drawPositionY,
		// width, true);
	}

	public boolean mouseOverCloseButton(float mouseX, float mouseY) {
		return closeButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		this.drawPositionX += dragX;
		this.drawPositionY -= dragY;
		this.closeButton.x += dragX;
		this.closeButton.y -= dragY;

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		System.out.println("mouseX = " + mouseX);
		System.out.println("mouseY = " + mouseY);
		System.out.println("drawPositionX = " + drawPositionX);
		System.out.println("drawPositionY = " + drawPositionY);
		System.out.println("width = " + width);
		System.out.println("height = " + height);
		if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
				&& mouseY < drawPositionY + height) {

			System.out.println("mouseX = " + mouseX);
			System.out.println("mouseY = " + mouseY);

			return true;
		}
		return false;
	}
}
