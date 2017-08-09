package com.marklynch.level.popup;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupActionButton extends PopupButton {

	public Action action;

	public PopupActionButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Action action, Popup popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, xFromLeft, yFromTop, action, popup, text);
		this.action = action;
	}

	@Override
	public void draw() {

		float realX = x + popup.drawPositionX;
		float realY = y + popup.drawPositionY;

		if (this.xFromLeft == false)
			realX = popup.drawPositionX - x;

		if (this.yFromTop == false)
			realY = popup.drawPositionY - y;

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.GREEN, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
			} else {
				if (highlighted) {
					QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY, realY + height);
					TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
					if (action.sound != null && action.sound.destinationSquares != null) {
						Matrix4f view = Game.activeBatch.getViewMatrix();
						view.setIdentity();
						view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
						view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
						view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
						view.translate(new Vector2f(Game.dragX, Game.dragY));
						Game.activeBatch.updateUniforms();
						Game.activeBatch.setColor(Color.WHITE);
						for (Square square : action.sound.destinationSquares) {
							square.drawSoundHighlight();
						}
						Game.activeBatch.flush();
						view.setIdentity();
						Game.activeBatch.updateUniforms();

					}
				} else {
					QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
					TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
				}
			}
		} else {

			QuadUtils.drawQuad(Color.LIGHT_GRAY, realX, realX + width, realY, realY + height);
			TextUtils.printTextWithImages(new Object[] { object }, realX, realY, Integer.MAX_VALUE, true);
		}

	}

}
