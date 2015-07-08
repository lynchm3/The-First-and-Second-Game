package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.TextureUtils;

public class EndTurnButton extends Button {

	public EndTurnButton(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, Level level) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				level);
	}

	@Override
	public void click() {
		level.endTurn();
	}

	@Override
	public void draw() {

		if (enabled)
			TextureUtils.drawTexture(enabledTexture, Game.windowWidth - 210f,
					Game.windowWidth - 210f + width, Game.windowHeight - 110f,
					Game.windowHeight - 110f + height);
		else
			TextureUtils.drawTexture(disabledTexture, Game.windowWidth - 210f,
					Game.windowWidth - 210f + width, Game.windowHeight - 110f,
					Game.windowHeight - 110f + height);
	}
}
