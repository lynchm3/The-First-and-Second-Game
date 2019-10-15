package com.marklynch.script;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.objects.actors.Actor;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ScriptEventSpeech extends ScriptEvent {

	public final static String[] editableAttributes = { "name", "blockUserInput", "scriptTrigger", "speechParts",
			"intTest" };

	public CopyOnWriteArrayList<SpeechPart> speechParts;
	public CopyOnWriteArrayList<Integer> intTest;
	public int speechIndex = 0;

	public ScriptEventSpeech() {
		name = "ScriptEventSpeech";
	}

	public ScriptEventSpeech(boolean blockUserInput, CopyOnWriteArrayList<SpeechPart> speechParts, ScriptTrigger scriptTrigger) {
		super(blockUserInput, scriptTrigger);
		this.speechParts = speechParts;
		name = "ScriptEventSpeech";
		intTest = new CopyOnWriteArrayList<Integer>();
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

		public transient CopyOnWriteArrayList<Actor> actors = new CopyOnWriteArrayList<Actor>();
		public CopyOnWriteArrayList<Float> positions = new CopyOnWriteArrayList<Float>();
		public CopyOnWriteArrayList<Boolean> directions = new CopyOnWriteArrayList<Boolean>();
		public transient Actor talker;
		public CopyOnWriteArrayList<String> text;
		public boolean inline = false;

		public SpeechPart(Actor talker, CopyOnWriteArrayList<String> text) {
			super();
			this.talker = talker;
			this.text = text;
			for (Actor actor : actors) {
			}
			inline = true;
		}

		public SpeechPart(CopyOnWriteArrayList<Actor> actors, CopyOnWriteArrayList<Float> positions, CopyOnWriteArrayList<Boolean> directions,
				Actor talker, CopyOnWriteArrayList<String> text) {
			super();
			this.actors = actors;
			this.positions = positions;
			this.directions = directions;
			this.talker = talker;
			this.text = text;
			inline = false;

		}

		public void draw() {
			if (inline) {

				// INLINE

				// get the instance of the view matrix for our batch
				Matrix4f view = Game.activeBatch.getViewMatrix();

				// reset the matrix to identity, i.e. "no camera transform"

				Game.flush();
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
				TextUtils.printTextWithImages(textX1, textY1, 200, true, null, Color.WHITE, 1f, text);

				// reset the matrix to identity, i.e. "no camera transform"

				Game.flush();
				view.setIdentity();
				Game.activeBatch.updateUniforms();
			} else {
				// NOT INLINE

				QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), 0f, 0f, Game.windowWidth, Game.windowHeight);

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
				TextUtils.printTextWithImages(textX1, posY, width, true, null, Color.WHITE, 1f, text);
			}
		}

		public SpeechPart makeCopy() {

			if (inline) {
				CopyOnWriteArrayList<String> text = new CopyOnWriteArrayList<String>();
				for (int i = 0; i < this.text.size(); i++) {
					text.add(this.text.get(i));
				}
				return new SpeechPart(talker, text);
			} else {

				CopyOnWriteArrayList<Actor> actors = new CopyOnWriteArrayList<Actor>();
				actors.addAll(actors);
				CopyOnWriteArrayList<Float> positions = new CopyOnWriteArrayList<Float>();
				positions.addAll(positions);
				CopyOnWriteArrayList<Boolean> directions = new CopyOnWriteArrayList<Boolean>();
				directions.addAll(directions);
				CopyOnWriteArrayList<String> text = new CopyOnWriteArrayList<String>();
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
