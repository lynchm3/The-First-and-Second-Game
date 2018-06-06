package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationDrop;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionDropItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Drop";
	Square square;
	GameObject[] objects;

	public ActionDropItems(GameObject performer, Square square, ArrayList<GameObject> objects) {
		this(performer, square, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionDropItems(GameObject performer, Square square, GameObject... objects) {
		this(performer, square, objects, false);
	}

	public ActionDropItems(GameObject performer, Square square, GameObject[] objects, boolean doesnothing) {
		super(ACTION_NAME, "right.png");
		super.gameObjectPerformer = this.gameObjectPerformer = performer;
		this.square = square;
		this.objects = objects;

		if (!check()) {
			enabled = false;
		} else {

			actionName = ACTION_NAME + " " + objects[0].name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		int amountToDrop = Math.min(objects.length, qty);

		if (amountToDrop == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (gameObjectPerformer.squareGameObjectIsOn.onScreen()
				&& gameObjectPerformer.squareGameObjectIsOn.visibleToPlayer) {
			objects[0].setPrimaryAnimation(
					new AnimationDrop(objects[0].name, gameObjectPerformer, this, square, objects[0], 0.5f));
		}

		for (int i = 0; i < amountToDrop; i++) {

			GameObject object = objects[i];
			// if (!performer.inventory.contains(object)) {
			// }

			if (gameObjectPerformer instanceof Actor) {
				Actor actor = (Actor) gameObjectPerformer;

				if (actor.equipped == object) {
					if (actor.inventory.contains(actor.equippedBeforePickingUpObject)) {
						actor.equip(actor.equippedBeforePickingUpObject);
					} else if (actor.inventory.containsDuplicateOf(object)) {
						actor.equip(actor.inventory.getDuplicateOf(object));
					} else {
						actor.equip(null);
					}
					actor.equippedBeforePickingUpObject = null;
				}
				if (actor.helmet == object)
					actor.helmet = null;
				if (actor.bodyArmor == object)
					actor.bodyArmor = null;
				if (actor.legArmor == object)
					actor.legArmor = null;
			}

			gameObjectPerformer.inventory.remove(object);

			// if inventory is open, we're not doing animattion, just throw it
			// on in there
			// if (Game.level.openInventories.size() > 0) {
			if (square.inventory.contains(Searchable.class)) {
				Searchable searchable = (Searchable) square.inventory.getGameObjectOfClass(Searchable.class);
				searchable.inventory.add(object);
			} else {

				if (gameObjectPerformer instanceof Actor) {
					square.inventory.add(object);
				} else {
					Game.level.inanimateObjectsToAdd.add(new InanimateObjectToAddOrRemove(object, square));
				}
			}

			if (Game.level.player.inventory.groundDisplay != null)
				Game.level.player.inventory.groundDisplay.refreshGameObjects();

		}

		if (Game.level.shouldLog(gameObjectPerformer)) {
			String amountToDropString = "";
			if (amountToDrop > 1)
				amountToDropString = "x" + amountToDrop;
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { gameObjectPerformer, " dropped ", objects[0], amountToDropString }));
		}

		if (gameObjectPerformer.inventory.groundDisplay != null)
			gameObjectPerformer.inventory.groundDisplay.refreshGameObjects();

		gameObjectPerformer.actionsPerformedThisTurn.add(this);

		// if (performer == Game.level.player)
		// Game.level.endTurn();

		if (gameObjectPerformer instanceof Actor)
			trespassingCheck(this, (Actor) gameObjectPerformer, gameObjectPerformer.squareGameObjectIsOn);
	}

	@Override
	public boolean check() {

		if (objects == null || objects.length == 0 || objects[0] == null)
			return false;

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.inventory.contains(objects[0]) && actor.equipped != objects[0]) {
				actionName = ACTION_NAME + " " + objects[0].name + " (can't reach)";
				return false;
			}
		} else {
			if (!gameObjectPerformer.inventory.contains(objects[0])) {
				actionName = ACTION_NAME + " " + objects[0].name + " (can't reach)";
				return false;
			}

		}

		if (!square.inventory.canShareSquare && !objects[0].canShareSquare) {
			actionName = ACTION_NAME + " " + objects[0].name + " (no space)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (gameObjectPerformer.straightLineDistanceTo(square) > 1) {
			actionName = ACTION_NAME + " " + objects[0].name + " (can't reach)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
