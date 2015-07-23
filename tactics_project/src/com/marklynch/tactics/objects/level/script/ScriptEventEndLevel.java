package com.marklynch.tactics.objects.level.script;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

public class ScriptEventEndLevel extends ScriptEvent {

	Level level;
	Object[] text;
	boolean completed = false;

	public ScriptEventEndLevel(boolean blockUserInput,
			ScriptTrigger scriptTrigger, Level level, Object[] text) {
		super(blockUserInput, scriptTrigger);
		this.level = level;
		this.text = text;
	}

	@Override
	public boolean checkIfCompleted() {
		return completed;
	}

	@Override
	public void click() {
		level.end();
		completed = true;
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void draw() {

		QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), 0f,
				Game.windowWidth, 0f, Game.windowHeight);

		float posY = Game.windowHeight / 2f;

		float textX1 = 300;
		float textX2 = Game.windowWidth - 300;
		float width = textX2 - textX1;
		if (width <= 100)
			width = 100;

		// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128, 128);
		TextUtils.printTextWithImages(text, textX1, posY, width);
	}
}
