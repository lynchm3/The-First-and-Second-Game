package com.marklynch.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;

import mdesl.graphics.Color;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.tactics.objects.weapons.Weapons;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SelectionWindowButton;
import com.marklynch.utils.QuadUtils;

public class SelectionWindow<T> {

	ArrayList<T> objects;
	ArrayList<T> selectedObjects = new ArrayList<T>();
	public ArrayList<SelectionWindowButton> buttons = new ArrayList<SelectionWindowButton>();
	public boolean multi = false;
	public Editor editor;

	public SelectionWindow(final ArrayList<T> objects, boolean multi,
			final Editor editor, final Object ownerOfAttribute) {
		this.multi = multi;
		this.objects = objects;
		this.editor = editor;
		for (int i = 0; i < objects.size(); i++) {

			System.out.println("LOOP");

			final int index = i;

			float x = i / ((int) Game.windowHeight / 30) * 200;
			float y = i % (Game.windowHeight / 30) * 30;

			final SelectionWindowButton selectionWindowButton = new SelectionWindowButton(
					x, y, 190, 30, null, null, "", true, true, objects.get(i));
			if (multi && selectedObjects != null
					&& selectedObjects.contains(objects.get(i)))
				selectionWindowButton.down = true;

			selectionWindowButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					try {
						Class<? extends Object> objectClass = editor.objectToEdit
								.getClass();
						Field field = objectClass
								.getField(editor.attributeToEdit);

						if (field.getType().isAssignableFrom(Faction.class)) {// faction

							Actor actor = (Actor) editor.objectToEdit;
							Faction faction = (Faction) objects.get(index);
							actor.faction.actors.remove(actor);
							faction.actors.add(actor);
							actor.faction = faction;
							editor.stopEditingAttribute();
						} else if (field.getType()
								.isAssignableFrom(Actor.class)) {
							Actor actor = (Actor) objects.get(index);
							ownerOfAttribute.getClass().getField("actor")
									.set(ownerOfAttribute, actor);
							ownerOfAttribute.getClass().getField("actorGUID")
									.set(ownerOfAttribute, actor.guid);
							editor.stopEditingAttribute();
						} else if (field.getType()
								.isAssignableFrom(Color.class)) {// color

							Color color = (Color) objects.get(index);

							field.set(editor.objectToEdit, new Color(color));
							editor.stopEditingAttribute();

						} else if (field.getType().isAssignableFrom(
								Texture.class)) {// texture
							field.set(editor.objectToEdit, objects.get(index));
							editor.stopEditingAttribute();

						} else if (field.getType().isAssignableFrom(
								Weapons.class)) {// weapons

							if (selectionWindowButton.down == true) {
								selectionWindowButton.down = false;
								selectedObjects
										.remove(selectionWindowButton.object);
							} else {
								selectionWindowButton.down = true;
								selectedObjects
										.add((T) selectionWindowButton.object);
							}

							Weapons weapons = new Weapons();
							for (T selectedObject : selectedObjects) {
								Weapon weapon = (Weapon) selectedObject;
								weapons.weapons.add(weapon.makeCopy());
							}
							ownerOfAttribute.getClass().getField("weapons")
									.set(ownerOfAttribute, weapons);
						} else if (field.getType().isAssignableFrom(
								ScriptTrigger.class)) {// script trigger
							ScriptTrigger scriptTrigger = (ScriptTrigger) objects
									.get(index);
							ownerOfAttribute
									.getClass()
									.getField("scriptTrigger")
									.set(ownerOfAttribute,
											scriptTrigger.makeCopy());
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("AGHHHH");
					}

					editor.settingsWindow.update();

				}
			};
			buttons.add(selectionWindowButton);
		}
	}

	public void draw() {
		// faction
		QuadUtils.drawQuad(Color.BLACK, 0, Game.windowWidth, 0,
				Game.windowHeight);
		for (SelectionWindowButton button : buttons) {
			button.draw();
		}

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		for (Button button : buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}
		return null;
	}
}
