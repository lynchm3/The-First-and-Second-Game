package com.marklynch.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.routines.AIRoutine;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.tactics.objects.weapons.Weapons;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SelectionWindowButton;
import com.marklynch.utils.ClassUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;

public class AttributeSelectionWindow<T> {

	ArrayList<T> objects;
	ArrayList<T> selectedObjects = new ArrayList<T>();
	public ArrayList<SelectionWindowButton> buttons = new ArrayList<SelectionWindowButton>();
	public boolean multi = false;
	public Editor editor;

	public AttributeSelectionWindow(final ArrayList<T> objects, boolean multi,
			final Editor editor, final Object ownerOfAttribute) {
		this.multi = multi;
		this.objects = objects;
		this.editor = editor;
		for (int i = 0; i < objects.size(); i++) {

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
								.getField(editor.attributeToEditName);
						Class type;

						ArrayList arrayList = null;
						if (field.getType().isAssignableFrom(ArrayList.class)) {
							arrayList = (ArrayList) field
									.get(editor.objectToEdit);
							type = arrayList.get(editor.attributeToEditIndex)
									.getClass();
						} else {
							type = field.getType();
						}

						if (type.isAssignableFrom(GameObject.class)) {
							GameObject gameObject = (GameObject) objects
									.get(index);
							if (arrayList == null) {
								field.set(ownerOfAttribute, gameObject);
								if (ClassUtils.classContainsField(objectClass,
										editor.attributeToEditName + "GUID")) {
									Field guidField = objectClass
											.getField(editor.attributeToEditName
													+ "GUID");
									if (guidField != null) {
										guidField.set(ownerOfAttribute,
												gameObject.guid);
									}
								}
							} else {
								arrayList.set(editor.attributeToEditIndex,
										gameObject);
								if (ClassUtils.classContainsField(objectClass,
										editor.attributeToEditName + "GUIDs")) {
									Field guidField = objectClass
											.getField(editor.attributeToEditName
													+ "GUIDs");
									ArrayList arrayListGUIDs = (ArrayList) guidField
											.get(editor.objectToEdit);
									arrayListGUIDs.set(
											editor.attributeToEditIndex,
											gameObject.guid);

								}
							}
							editor.stopEditingAttribute();
						} else if (type.isAssignableFrom(Faction.class)) {// faction
							Faction faction = (Faction) objects.get(index);

							if (editor.objectToEdit instanceof Actor) {
								Actor actor = (Actor) editor.objectToEdit;
								actor.faction.actors.remove(actor);
								faction.actors.add(actor);
								actor.faction = faction;
							} else {
								field.set(ownerOfAttribute, faction);
								try {
									Field guidField = objectClass
											.getField(editor.attributeToEditName
													+ "GUID");
									if (guidField != null) {
										guidField.set(ownerOfAttribute,
												faction.guid);
									}
								} catch (Exception e) {

								}
							}
							editor.stopEditingAttribute();
						} else if (type.isAssignableFrom(Square.class)) {

							Square square = (Square) objects.get(index);

							field.set(ownerOfAttribute, square);
							try {
								Field guidField = objectClass
										.getField(editor.attributeToEditName
												+ "GUID");
								if (guidField != null) {
									guidField
											.set(ownerOfAttribute, square.guid);
								}
							} catch (Exception e) {

							}
							editor.stopEditingAttribute();
						} else if (type.isAssignableFrom(ScriptEvent.class)) {
							ScriptEvent scriptEvent = (ScriptEvent) objects
									.get(index);
							field.set(ownerOfAttribute, scriptEvent);
							try {
								Field guidField = objectClass
										.getField(editor.attributeToEditName
												+ "GUID");
								if (guidField != null) {
									guidField.set(ownerOfAttribute,
											scriptEvent.guid);
								}
							} catch (Exception e) {

							}
							editor.stopEditingAttribute();
						} else if (type.isAssignableFrom(Actor.class)) {
							Actor actor = (Actor) objects.get(index);
							if (arrayList == null) {
								field.set(ownerOfAttribute, actor);
								try {
									Field guidField = objectClass
											.getField(editor.attributeToEditName
													+ "GUID");
									if (guidField != null) {
										guidField.set(ownerOfAttribute,
												actor.guid);
									}
								} catch (Exception e) {

								}
							} else {
								arrayList.set(editor.attributeToEditIndex,
										actor);
								if (ClassUtils.classContainsField(objectClass,
										editor.attributeToEditName + "GUIDs")) {
									Field guidField = objectClass
											.getField(editor.attributeToEditName
													+ "GUIDs");
									ArrayList arrayListGUIDs = (ArrayList) guidField
											.get(editor.objectToEdit);
									arrayListGUIDs.set(
											editor.attributeToEditIndex,
											actor.guid);

								}
							}
							editor.stopEditingAttribute();
						} else if (type.isAssignableFrom(Color.class)) {// color

							Color color = (Color) objects.get(index);

							field.set(editor.objectToEdit, new Color(color));
							editor.stopEditingAttribute();

						} else if (type.isAssignableFrom(Texture.class)) {// texture
							Texture texture = (Texture) objects.get(index);
							field.set(editor.objectToEdit, objects.get(index));
							try {
								Field guidField = objectClass
										.getField(editor.attributeToEditName
												+ "Path");
								if (guidField != null) {
									guidField
											.set(ownerOfAttribute,
													ResourceUtils
															.getPathForTexture(texture));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							editor.stopEditingAttribute();

						} else if (type.isAssignableFrom(Weapons.class)) {// weapons

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
								weapons.weapons.add(weapon.makeWeapon());
							}
							ownerOfAttribute.getClass().getField("weapons")
									.set(ownerOfAttribute, weapons);
						} else if (type.isAssignableFrom(ScriptTrigger.class)) {// script
																				// trigger
							ScriptTrigger scriptTrigger = (ScriptTrigger) objects
									.get(index);
							ownerOfAttribute
									.getClass()
									.getField("scriptTrigger")
									.set(ownerOfAttribute,
											scriptTrigger.makeCopy());
							editor.stopEditingAttribute();
						} else if (type.isAssignableFrom(AIRoutine.class)) {// script
																		// trigger
							AIRoutine ai = (AIRoutine) objects.get(index);
							ownerOfAttribute.getClass().getField("ai")
									.set(ownerOfAttribute, ai.makeCopy());
							editor.stopEditingAttribute();
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
		QuadUtils.drawQuad(Color.WHITE, 0, Game.windowWidth, 0,
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
