package com.marklynch.tactics.objects.level.script;

import java.util.ArrayList;
import java.util.Vector;

import mdesl.graphics.Color;

import com.marklynch.CustomizedTypeAdapterFactory;
import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class SpeechPart {

	public transient Vector<Actor> actors = null;
	public Vector<Float> positions;
	public Vector<DIRECTION> directions;
	public Actor talker;
	public Object[] text;
	public transient Level level = null;

	// For saving and loading
	public ArrayList<String> actorGUIDs = new ArrayList<String>();
	public String talkerGUID = null;

	public enum DIRECTION {
		LEFT, RIGHT
	}

	public SpeechPart(Vector<Actor> actors, Vector<Float> positions,
			Vector<DIRECTION> directions, Actor talker, Object[] text,
			Level level) {
		super();
		this.actors = actors;
		this.positions = positions;
		this.directions = directions;
		this.talker = talker;
		this.text = text;
		this.level = level;
	}

	public void draw() {

		QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), 0f,
				Game.windowWidth, 0f, Game.windowHeight);

		float posY = Game.windowHeight / 2f;

		for (int i = 0; i < actors.size(); i++) {

			float alpha = 1f;

			if (actors.get(i) != this.talker) {
				alpha = 0.5f;
			}
			if (directions.get(i).equals(DIRECTION.RIGHT)) {
				TextureUtils.drawTexture(actors.get(i).imageTexture, alpha,
						positions.get(i), positions.get(i) + 256, posY,
						posY + 256);
			} else {
				TextureUtils.drawTextureBackwards(actors.get(i).imageTexture,
						alpha, Game.windowWidth - positions.get(i) - 256,
						Game.windowWidth - positions.get(i), posY, posY + 256);
			}
		}

		float textX1 = 300;
		float textX2 = Game.windowWidth - 300;
		float width = textX2 - textX1;
		if (width <= 100)
			width = 100;

		// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128, 128);
		TextUtils.printTextWithImages(text, textX1, posY, width);
	}

	public static class SpeechPartTypeAdapterFactory extends
			CustomizedTypeAdapterFactory<SpeechPart> {
		public SpeechPartTypeAdapterFactory() {
			super(SpeechPart.class);
		}

		@Override
		protected void beforeWrite(SpeechPart object) {
			for (Actor actor : object.actors) {
				object.actorGUIDs.add(actor.guid);
			}

			object.talkerGUID = object.talker.guid;
		}

		@Override
		protected SpeechPart afterRead(SpeechPart object) {
			System.out.println("SpeechPart afterRead()");
			object.actors = new Vector<Actor>();

			for (int i = 0; i < object.actorGUIDs.size(); i++) {
				object.actors.add(Game.level
						.findActorFromGUID(object.actorGUIDs.get(i)));
			}

			object.talker = Game.level.findActorFromGUID(object.talkerGUID);

			object.level = Game.level;
			return object;
		}
	}
}
