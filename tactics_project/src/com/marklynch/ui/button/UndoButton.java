package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.TextureUtils;

public class UndoButton extends Button {

	public UndoButton(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, Level level) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				level);
	}

	@Override
	public void click() {
		if (enabled)
			level.undo();

	}

	@Override
	public void draw() {

		if (enabled)
			TextureUtils.drawTexture(enabledTexture, Game.windowWidth - 420f,
					Game.windowWidth - 420f + width, Game.windowHeight - 110f,
					Game.windowHeight - 110f + height);
		else
			TextureUtils.drawTexture(disabledTexture, Game.windowWidth - 420f,
					Game.windowWidth - 420f + width, Game.windowHeight - 110f,
					Game.windowHeight - 110f + height);
	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {
		if (mouseX > Game.windowWidth - 420f
				&& mouseX < Game.windowWidth - 420f + width
				&& mouseY > Game.windowHeight - 110f
				&& mouseY < Game.windowHeight - 110f + height) {
			return true;
		}
		return false;
	}

}
