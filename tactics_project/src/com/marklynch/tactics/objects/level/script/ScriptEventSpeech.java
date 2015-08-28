package com.marklynch.tactics.objects.level.script;

import java.util.ArrayList;

import mdesl.graphics.Color;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ScriptEventSpeech extends ScriptEvent {

	public final static String[] editableAttributes = { "name",
			"blockUserInput", "scriptTrigger", "speechParts", "intTest" };

	public ArrayList<SpeechPart> speechParts;
	public ArrayList<Integer> intTest;
	public int speechIndex = 0;

	public ScriptEventSpeech() {
		name = "ScriptEventSpeech";
	}

	public ScriptEventSpeech(boolean blockUserInput,
			ArrayList<SpeechPart> speechParts, ScriptTrigger scriptTrigger) {
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

		public final static String[] editableAttributes = { "actors",
				"positions", "directions", "talker", "text", "inline" };

		public transient ArrayList<Actor> actors = new ArrayList<Actor>();
		public ArrayList<Float> positions = new ArrayList<Float>();
		public ArrayList<Boolean> directions = new ArrayList<Boolean>();
		public transient Actor talker;
		public ArrayList<StringWithColor> text;
		public boolean inline = false;

		// For saving and loading
		public ArrayList<String> actorGUIDs = new ArrayList<String>();
		public String talkerGUID = null;

		public SpeechPart(Actor talker, ArrayList text) {
			super();
			this.talker = talker;
			this.text = text;
			for (Actor actor : actors) {
				actorGUIDs.add(actor.guid);
			}
			talkerGUID = talker.guid;
			inline = true;
		}

		public SpeechPart(ArrayList<Actor> actors, ArrayList<Float> positions,
				ArrayList<Boolean> directions, Actor talker, ArrayList text) {
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

				view.translate(new Vector2f(Game.windowWidth / 2,
						Game.windowHeight / 2));
				view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
				view.translate(new Vector2f(-Game.windowWidth / 2,
						-Game.windowHeight / 2));
				view.translate(new Vector2f(Game.dragX, Game.dragY));

				// update the new view matrix
				Game.activeBatch.updateUniforms();

				float textX1 = talker.squareGameObjectIsOn.x
						* Game.SQUARE_WIDTH + Game.SQUARE_WIDTH;
				float textY1 = talker.squareGameObjectIsOn.y
						* Game.SQUARE_HEIGHT;

				// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128,
				// 128);
				TextUtils.printTextWithImages(text, textX1, textY1, 200);

				// reset the matrix to identity, i.e. "no camera transform"

				Game.activeBatch.flush();
				view.setIdentity();
				Game.activeBatch.updateUniforms();
			} else {
				// NOT INLINE

				QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), 0f,
						Game.windowWidth, 0f, Game.windowHeight);

				float posY = Game.windowHeight / 2f;

				for (int i = 0; i < actors.size(); i++) {

					float alpha = 1f;

					if (actors.get(i) != this.talker) {
						alpha = 0.5f;
					}
					if (directions.get(i).equals(true)) {
						TextureUtils.drawTexture(actors.get(i).imageTexture,
								alpha, positions.get(i),
								positions.get(i) + 256, posY, posY + 256);
					} else {
						TextureUtils.drawTextureBackwards(
								actors.get(i).imageTexture, alpha,
								Game.windowWidth - positions.get(i) - 256,
								Game.windowWidth - positions.get(i), posY,
								posY + 256);
					}
				}

				float textX1 = 300;
				float textX2 = Game.windowWidth - 300;
				float width = textX2 - textX1;
				if (width <= 100)
					width = 100;

				System.out.println("text = " + text);

				// TextureUtils.drawTexture(talker.imageTexture, 0, 0, 128,
				// 128);
				TextUtils.printTextWithImages(text, textX1, posY, width);
			}
		}

		public SpeechPart makeCopy() {

			if (inline) {
				ArrayList<StringWithColor> text = new ArrayList<StringWithColor>();
				for (int i = 0; i < this.text.size(); i++) {
					text.add(this.text.get(i).makeCopy());
				}
				return new SpeechPart(talker, text);
			} else {

				ArrayList<Actor> actors = new ArrayList<Actor>();
				actors.addAll(actors);
				ArrayList<Float> positions = new ArrayList<Float>();
				positions.addAll(positions);
				ArrayList<Boolean> directions = new ArrayList<Boolean>();
				directions.addAll(directions);
				ArrayList<StringWithColor> text = new ArrayList<StringWithColor>();
				for (int i = 0; i < this.text.size(); i++) {
					text.add(this.text.get(i).makeCopy());
				}
				return new SpeechPart(actors, positions, directions, talker,
						text);
			}
		}
	}

	@Override
	public void postLoad() {
		scriptTrigger.postLoad();
	}
}
