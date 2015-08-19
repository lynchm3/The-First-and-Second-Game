package com.marklynch.tactics.objects.level.script;

import java.util.ArrayList;
import java.util.Vector;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ScriptEventSpeech extends ScriptEvent {

	public Vector<SpeechPart> speechParts;
	public int speechIndex = 0;

	public ScriptEventSpeech(boolean blockUserInput,
			Vector<SpeechPart> speechParts, ScriptTrigger scriptTrigger) {
		super(blockUserInput, scriptTrigger);
		this.speechParts = speechParts;
	}

	@Override
	public boolean checkIfCompleted() {
		if (speechIndex >= speechParts.size())
			return true;
		return false;
	}

	@Override
	public void click() {
		speechIndex++;
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void draw() {
		if (speechIndex < speechParts.size()) {
			speechParts.get(speechIndex).draw();
		}

	}

	public static class SpeechPart {

		public transient Vector<Actor> actors = null;
		public Vector<Float> positions;
		public Vector<DIRECTION> directions;
		public Actor talker;
		public StringWithColor[] text;

		// For saving and loading
		public ArrayList<String> actorGUIDs = new ArrayList<String>();
		public String talkerGUID = null;

		public enum DIRECTION {
			LEFT, RIGHT
		}

		public SpeechPart(Vector<Actor> actors, Vector<Float> positions,
				Vector<DIRECTION> directions, Actor talker,
				StringWithColor[] text) {
			super();
			this.actors = actors;
			this.positions = positions;
			this.directions = directions;
			this.talker = talker;
			this.text = text;
			for (Actor actor : actors) {
				actorGUIDs.add(actor.guid);
			}
			talkerGUID = talker.guid;
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
					TextureUtils.drawTextureBackwards(
							actors.get(i).imageTexture, alpha, Game.windowWidth
									- positions.get(i) - 256, Game.windowWidth
									- positions.get(i), posY, posY + 256);
				}
			}

			float textX1 = 300;
			float textX2 = Game.windowWidth - 300;
			float width = textX2 - textX1;
			if (width <= 100)
				width = 100;

			System.out.println("text = " + text);

			// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128, 128);
			TextUtils.printTextWithImages(text, textX1, posY, width);
		}
	}
}
