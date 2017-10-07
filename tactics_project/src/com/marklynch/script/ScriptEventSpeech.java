package com.marklynch.script;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class ScriptEventSpeech extends ScriptEvent {

	public final static String[] editableAttributes = { "name", "blockUserInput", "scriptTrigger", "speechParts",
			"intTest" };

	public ArrayList<SpeechPart> speechParts;
	public ArrayList<Integer> intTest;
	public int speechIndex = 0;

	public ScriptEventSpeech() {
		name = "ScriptEventSpeech";
	}

	public ScriptEventSpeech(boolean blockUserInput, ArrayList<SpeechPart> speechParts, ScriptTrigger scriptTrigger) {
		super(blockUserInput, scriptTrigger);
		this.speechParts = speechParts;
		name = "ScriptEventSpeech";
		intTest = new ArrayList<Integer>();
		intTest.add(1);
		intTest.add(2);
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

		public final static String[] editableAttributes = { "actors", "positions", "directions", "talker", "text",
				"inline" };

		public transient ArrayList<Actor> actors = new ArrayList<Actor>();
		public ArrayList<Float> positions = new ArrayList<Float>();
		public ArrayList<Boolean> directions = new ArrayList<Boolean>();
		public transient Actor talker;
		public ArrayList<String> text;
		public boolean inline = false;

		// For saving and loading
		public ArrayList<String> actorsGUIDs = new ArrayList<String>();
		public String talkerGUID = null;

		public SpeechPart(Actor talker, ArrayList<String> text) {
			super();
			this.talker = talker;
			this.text = text;
			for (Actor actor : actors) {
				actorsGUIDs.add(actor.guid);
			}
			talkerGUID = talker.guid;
			inline = true;
		}

		public SpeechPart(ArrayList<Actor> actors, ArrayList<Float> positions, ArrayList<Boolean> directions,
				Actor talker, ArrayList<String> text) {
			super();
			this.actors = actors;
			this.positions = positions;
			this.directions = directions;
			this.talker = talker;
			this.text = text;
			for (Actor actor : actors) {
				actorsGUIDs.add(actor.guid);
			}
			talkerGUID = talker.guid;
			inline = false;

		}

		public void draw() {
			if (inline) {

				// INLINE

				// get the instance of the view matrix for our batch
				Matrix4f view = Game.activeBatch.getViewMatrix();

				// reset the matrix to identity, i.e. "no camera transform"

				Game.activeBatch.flush();
				view.setIdentity();

				view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
				view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
				view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
				view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));

				// update the new view matrix
				Game.activeBatch.updateUniforms();

				float textX1 = talker.squareGameObjectIsOn.xInGridPixels;
				float textY1 = talker.squareGameObjectIsOn.yInGridPixels;

				// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128,
				// 128);
				TextUtils.printTextWithImages(textX1, textY1, 200, true, null, text);

				// reset the matrix to identity, i.e. "no camera transform"

				Game.activeBatch.flush();
				view.setIdentity();
				Game.activeBatch.updateUniforms();
			} else {
				// NOT INLINE

				QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), 0f, Game.windowWidth, 0f, Game.windowHeight);

				float posY = Game.windowHeight / 2f;

				for (int i = 0; i < actors.size(); i++) {

					float alpha = 1f;

					if (actors.get(i) != this.talker) {
						alpha = 0.5f;
					}
					if (directions.get(i).equals(true)) {
						TextureUtils.drawTexture(actors.get(i).imageTexture, alpha, positions.get(i), posY,
								positions.get(i) + 256, posY + 256);
					} else {
						TextureUtils.drawTextureBackwards(actors.get(i).imageTexture, alpha,
								Game.windowWidth - positions.get(i) - 256, posY, Game.windowWidth - positions.get(i),
								posY + 256);
					}
				}

				float textX1 = 300;
				float textX2 = Game.windowWidth - 300;
				float width = textX2 - textX1;
				if (width <= 100)
					width = 100;

				// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128,
				// 128);
				TextUtils.printTextWithImages(textX1, posY, width, true, null, text);
			}
		}

		public SpeechPart makeCopy() {

			if (inline) {
				ArrayList<String> text = new ArrayList<String>();
				for (int i = 0; i < this.text.size(); i++) {
					text.add(this.text.get(i));
				}
				return new SpeechPart(talker, text);
			} else {

				ArrayList<Actor> actors = new ArrayList<Actor>();
				actors.addAll(actors);
				ArrayList<Float> positions = new ArrayList<Float>();
				positions.addAll(positions);
				ArrayList<Boolean> directions = new ArrayList<Boolean>();
				directions.addAll(directions);
				ArrayList<String> text = new ArrayList<String>();
				for (int i = 0; i < this.text.size(); i++) {
					text.add(this.text.get(i));
				}
				return new SpeechPart(actors, positions, directions, talker, text);
			}
		}
	}

	@Override
	public void postLoad() {
		scriptTrigger.postLoad();
	}
}
