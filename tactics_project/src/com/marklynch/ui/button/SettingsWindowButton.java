package com.marklynch.ui.button;

import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import com.marklynch.utils.Color;

public abstract class SettingsWindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;

	public String textEntered = "";

	public Object object;

	public SettingsWindowButton(float x, float y, float width, float height, Object object, boolean xFromLeft,
			boolean yFromTop) {
		super(x, y, width, height, null, null, object);
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.object = object;
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
				QuadUtils.drawQuad(Color.BLACK, x, y, x + width, y + height);
				TextUtils.printTextWithImages(x, y, Integer.MAX_VALUE, true, null, Color.WHITE, new Object[] { text });
			} else {
				QuadUtils.drawQuad(Color.DARK_GRAY, x, y, x + width, y + height);
				TextUtils.printTextWithImages(x, y, 200, false, null, Color.WHITE, new Object[] { text });
			}
		} else {

			QuadUtils.drawQuad(Color.RED, x, y, x + width, y + height);
			TextUtils.printTextWithImages(x, y, 200, false, null, Color.WHITE, new Object[] { text });
		}

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			return true;
		}
		return false;
	}

	public abstract void keyTyped(char character);

	public abstract void enterTyped();

	public abstract void backTyped();

	public abstract void depress();
}
