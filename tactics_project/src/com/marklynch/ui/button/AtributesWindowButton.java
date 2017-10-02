package com.marklynch.ui.button;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.utils.ClassUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class AtributesWindowButton extends Button {

	boolean xFromLeft;
	boolean yFromTop;
	AttributesDialog attributesWindow;
	public Object object;
	String attribute;
	int index;

	public AtributesWindowButton(float x, float y, float width, float height, Object object, String attribute,
			boolean xFromLeft, boolean yFromTop, AttributesDialog detailsWindow, int index) {
		super(x, y, width, height, null, null, "");
		this.xFromLeft = xFromLeft;
		this.yFromTop = yFromTop;
		this.attributesWindow = detailsWindow;
		this.object = object;
		this.attribute = attribute;
		this.index = index;
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

		if (!ClassUtils.classContainsField(objectClass, attribute)) {

			if (enabled) {
				if (down) {
					QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY, realY + height);
					TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null,
							new Object[] { attribute });
				} else {
					QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
					TextUtils.printTextWithImages(realX, realY, 200, false, null, new Object[] { attribute });
				}
			} else {

				QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY + height);
				TextUtils.printTextWithImages(realX, realY, 200, false, null, new Object[] { attribute });
			}

		} else {

			try {

				Field field = null;
				ArrayList arrayList = null;

				field = objectClass.getField(attribute);

				if (field.getType().isAssignableFrom(ArrayList.class)) {
					arrayList = (ArrayList) field.get(object);
				}

				if (arrayList != null) {
					if (enabled) {
						if (down) {
							QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY, realY + height);
							TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null,
									new Object[] { attribute + "[" + index + "]: ", arrayList.get(index) });
						} else {
							QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
							TextUtils.printTextWithImages(realX, realY, 200, false, null,
									new Object[] { attribute + "[" + index + "]: ", arrayList.get(index) });
						}
					} else {

						QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY + height);
						TextUtils.printTextWithImages(realX, realY, 200, false, null,
								new Object[] { attribute + "[" + index + "]: ", arrayList.get(index) });
					}

				} else {
					if (enabled) {
						if (down) {
							QuadUtils.drawQuad(Color.BLACK, realX, realX + width, realY, realY + height);
							TextUtils.printTextWithImages(realX, realY, Integer.MAX_VALUE, true, null,
									new Object[] { attribute + ": ", field.get(object) });
						} else {
							QuadUtils.drawQuad(Color.DARK_GRAY, realX, realX + width, realY, realY + height);
							TextUtils.printTextWithImages(realX, realY, 200, false, null,
									new Object[] { attribute + ": ", field.get(object) });
						}
					} else {

						QuadUtils.drawQuad(Color.RED, realX, realX + width, realY, realY + height);
						TextUtils.printTextWithImages(realX, realY, 200, false, null,
								new Object[] { attribute + ": ", field.get(object) });
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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

		if (mouseX > realX && mouseX < realX + width && mouseY > realY && mouseY < realY + height) {
			return true;
		}
		return false;
	}

	@Override
	public void drawWithinBounds(float boundsX1, float boundsX2, float boundsY1, float boundsY2) {
		// TODO Auto-generated method stub

	}

}
