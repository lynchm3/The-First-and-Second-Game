package com.marklynch.ui.button;

import java.lang.reflect.Field;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.editor.AttributesWindow;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class AtributesWindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	AttributesWindow attributesWindow;
	Object object;
	String attribute;

	public AtributesWindowButton(float x, float y, float width, float height,
			Object object, String attribute, boolean xFromLeft,
			boolean yFromTop, AttributesWindow detailsWindow) {
		super(x, y, width, height, null, null, "");
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.attributesWindow = detailsWindow;
		this.object = object;
		this.attribute = attribute;
	}

	@Override
	public void draw() {

		Class<? extends Object> objectClass = object.getClass();

		float realX = x;
		float realY = attributesWindow.y + y;
		if (this.xFromLeft == false)
			realX = attributesWindow.x + attributesWindow.width - x;
		else
			realX = attributesWindow.x + x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;
		else
			realY = attributesWindow.y + y;

		try {
			Field field = objectClass.getField(attribute);
			if (enabled) {
				if (down) {
					QuadUtils.drawQuad(Color.green, realX, realX + width,
							realY, realY + height);
					TextUtils
							.printTextWithImages(new Object[] {
									attribute + ": ", field.get(object) },
									realX, realY);
				} else {
					QuadUtils.drawQuad(Color.blue, realX, realX + width, realY,
							realY + height);
					TextUtils
							.printTextWithImages(new Object[] {
									attribute + ": ", field.get(object) },
									realX, realY);
				}
			} else {

				QuadUtils.drawQuad(Color.red, realX, realX + width, realY,
						realY + height);
				TextUtils.printTextWithImages(new Object[] { attribute + ": ",
						field.get(object) }, realX, realY);
			}
		} catch (Exception e) {
		}

	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2,
			float boundsY1, float boundsY2) {

		float realX = x;
		float realY = attributesWindow.y + y;
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
		float realY = attributesWindow.y + y;
		if (this.xFromLeft == false)
			realX = attributesWindow.x + attributesWindow.width - x;
		else
			realX = attributesWindow.x + x;

		if (this.yFromTop == false)
			realY = Game.windowHeight - y;
		else
			realY = attributesWindow.y + y;

		if (mouseX > realX && mouseX < realX + width && mouseY > realY
				&& mouseY < realY + height) {
			return true;
		}
		return false;
	}

}
