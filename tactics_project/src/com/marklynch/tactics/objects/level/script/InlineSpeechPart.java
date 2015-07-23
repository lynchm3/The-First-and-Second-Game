package com.marklynch.tactics.objects.level.script;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
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
		// get the instance of the view matrix for our batch
		Matrix4f view = GameObject.batch.getViewMatrix();

		// reset the matrix to identity, i.e. "no camera transform"

		GameObject.batch.flush();
		view.setIdentity();

		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2,
				-Game.windowHeight / 2));
		view.translate(new Vector2f(Game.dragX, Game.dragY));

		// update the new view matrix
		GameObject.batch.updateUniforms();

		float textX1 = actor.squareGameObjectIsOn.x * Game.SQUARE_WIDTH
				+ Game.SQUARE_WIDTH;
		float textY1 = actor.squareGameObjectIsOn.y * Game.SQUARE_HEIGHT;

		// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128, 128);
		TextUtils.printTextWithImages(text, textX1, textY1, 200);

		// reset the matrix to identity, i.e. "no camera transform"

		GameObject.batch.flush();
		view.setIdentity();
		GameObject.batch.updateUniforms();
	}
}
