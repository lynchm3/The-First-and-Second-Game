package com.marklynch.tactics.objects.level.script;

import org.lwjgl.opengl.GL11;

import com.marklynch.Game;
import com.marklynch.UserInputLevel;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.utils.TextUtils;

public class InlineSpeechPart {
	public Actor actor;
	public Object[] text;
	public Level level;

	public InlineSpeechPart(Actor actor, Object[] text, Level level) {
		super();
		this.actor = actor;
		this.text = text;
		this.level = level;
	}

	public void draw() {

		// zoom
		GL11.glPushMatrix();

		GL11.glTranslatef(Game.windowWidth / 2, Game.windowHeight / 2, 0);
		GL11.glScalef(UserInputLevel.zoom, UserInputLevel.zoom, 0);
		GL11.glTranslatef(UserInputLevel.dragX, UserInputLevel.dragY, 0);
		GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);

		float textX1 = actor.squareGameObjectIsOn.x * Game.SQUARE_WIDTH
				+ Game.SQUARE_WIDTH;
		float textY1 = actor.squareGameObjectIsOn.y * Game.SQUARE_HEIGHT;

		// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128, 128);
		TextUtils.printTextWithImages(text, textX1, textY1, 200);
		GL11.glPopMatrix();
	}
}
