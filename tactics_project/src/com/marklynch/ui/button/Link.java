package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionPin;

import mdesl.graphics.Color;

public class Link extends LevelButton {

	Object object;

	public Link(float x, float y, float width, float height, String enabledTexturePath, String disabledTexturePath,
			String text, boolean xFromLeft, boolean yFromTop, Color buttonColor, Color textColor, Object object) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text, xFromLeft, yFromTop, buttonColor,
				textColor, "View more details");
		this.object = object;
		this.setClickListener(defaultClickListener);
	}

	private ClickListener defaultClickListener = new ClickListener() {

		@Override
		public void click() {
			if (object instanceof GameObject) {
				new ActionPin(Game.level.player, (GameObject) object).perform();
			} else {
				// I dunno
			}
		}
	};

}
