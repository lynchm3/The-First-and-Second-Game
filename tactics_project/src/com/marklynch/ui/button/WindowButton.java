package com.marklynch.ui.button;

import java.lang.reflect.Field;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.editor.DetailsWindow;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class WindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	DetailsWindow detailsWindow;
	Object object;
	String attribute;

	public WindowButton(float x, float y, float width, float height,
			Object object, String attribute, boolean xFromLeft,
			boolean yFromTop, DetailsWindow detailsWindow) {
		super(x, y, width, height, null, null, "");
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.detailsWindow = detailsWindow;
		this.object = object;
		this.attribute = attribute;
	}

	@Override
	public void draw() {

		if (object instanceof GameObject) {
			GameObject gameObject = (GameObject) object;

			Class<? extends GameObject> gameObjectClass = gameObject.getClass();
			try {
				Field field = gameObjectClass.getField(attribute);
				text = attribute + ": " + (String) field.get(gameObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = detailsWindow.x + detailsWindow.width - x;
		else
			realX = detailsWindow.x + x;

		if (this.yFromTop == false)
			realY = detailsWindow.y + detailsWindow.height - y;
		else
			realY = detailsWindow.y + y;

		if (enabled) {
			if (down) {
				QuadUtils.drawQuad(Color.green, realX, realX + width, realY,
						realY + height);
				TextUtils.printTextWithImages(new Object[] { text }, realX,
						realY);
			} else {
				QuadUtils.drawQuad(Color.blue, realX, realX + width, realY,
						realY + height);
				TextUtils.printTextWithImages(new Object[] { text }, realX,
						realY);
			}
		} else {

			QuadUtils.drawQuad(Color.red, realX, realX + width, realY, realY
					+ height);
			TextUtils.printTextWithImages(new Object[] { text }, realX, realY);
		}

	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;

		if (enabled) {
			TextureUtils.drawTextureWithinBounds(enabledTexture, 1.0f, realX,
					realX + width, realY, realY + height, boundsX1, boundsX2,
					boundsY1, boundsY2);
		} else {
			TextureUtils.drawTextureWithinBounds(disabledTexture, 1.0f, realX,
					realX + width, realY, realY + height, boundsX1, boundsX2,
					boundsY1, boundsY2);
		}

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {

		float realX = x;
		float realY = y;
		if (this.xFromLeft == false)
			realX = detailsWindow.x + detailsWindow.width - x;
		else
			realX = detailsWindow.x + x;

		if (this.yFromTop == false)
			realY = detailsWindow.y + detailsWindow.height - y;
		else
			realY = detailsWindow.y + y;

		if (mouseX > realX && mouseX < realX + width && mouseY > realY
				&& mouseY < realY + height) {
			return true;
		}
		return false;
	}

}
