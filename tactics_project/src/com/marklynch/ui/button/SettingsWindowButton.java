package com.marklynch.ui.button;

import org.newdawn.slick.Color;

import com.marklynch.editor.SettingsWindow;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public abstract class SettingsWindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	SettingsWindow settingsWindow;

	public String textEntered = "";

	public SettingsWindowButton(float x, float y, float width, float height,
			String text, boolean xFromLeft, boolean yFromTop,
			SettingsWindow settingsWindow) {
		super(x, y, width, height, null, null, text);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.settingsWindow = settingsWindow;
	}

	@Override
	public void draw() {

		// if (object instanceof GameObject) {
		// GameObject gameObject = (GameObject) object;
		//
		// Class<? extends GameObject> gameObjectClass = gameObject.getClass();
		// try {
		// Field field = gameObjectClass.getField(attribute);
		// text = attribute + ": " + field.get(gameObject);
		// } catch (Exception e) {
		// text = attribute;
		// }
		// }

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.green, x, x + width, y, y + height);
				TextUtils.printTextWithImages(new Object[] { text }, x, y);
			} else {
				QuadUtils.drawQuad(Color.blue, x, x + width, y, y + height);
				TextUtils.printTextWithImages(new Object[] { text }, x, y);
			}
		} else {

			QuadUtils.drawQuad(Color.red, x, x + width, y, y + height);
			TextUtils.printTextWithImages(new Object[] { text }, x, y);
		}

	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {

		if (enabled) {
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, x, x
					+ width, y, y + height, boundsX1, boundsX2, boundsY1,
					boundsY2);
		} else {
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, x, x
					+ width, y, y + height, boundsX1, boundsX2, boundsY1,
					boundsY2);
		}

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		if (mouseX > x && mouseX < x + width && mouseY > y
				&& mouseY < y + height) {
			return true;
		}
		return false;
	}

	public abstract void keyTyped(char character);

	public abstract void enterTyped();

	public abstract void backTyped();

	public abstract void depress();
}
