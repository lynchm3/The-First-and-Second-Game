package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class HidingPlace extends Searchable {

	public ArrayList<Actor> actorsHidingHere = new ArrayList<Actor>();

	public HidingPlace(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean attackable, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float iceResistance, float electricResistance, float poisonResistance, float weight,
			Actor owner, Effect[] effects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight,
				owner, effects);

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		if (performer.hiding && performer.squareGameObjectIsOn == this.squareGameObjectIsOn) {
			actions.add(new ActionStopHiding(performer, this));
		} else {
			actions.add(new ActionHide(performer, this));
		}

		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		// if (performer.equipped != null) {
		// if (performer.straightLineDistanceTo(this.squareGameObjectIsOn) < 2)
		// {
		// actions.add(new ActionDrop(performer, this.squareGameObjectIsOn,
		// performer.equipped));
		// }
		// actions.add(new ActionThrow(performer, this, performer.equipped));
		// }

		return actions;

	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {

		if (this.remainingHealth <= 0)
			return null;
		else
			return new ActionHide(performer, this);
	}

	@Override
	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public void draw1() {
	}

	@Override
	public void draw2() {
		super.draw1();
	}

	@Override
	public HidingPlace makeCopy(Square squareGameObjectIsOn, Actor owner) {
		return new HidingPlace(new String(name), (int) totalHealth, imageTexturePath, squareGameObjectIsOn, inventory,
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, attackable, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, weight, owner, effectsFromInteracting);
	}

	@Override
	public boolean checkIfDestroyed() {
		boolean destroyed = super.checkIfDestroyed();
		if (destroyed) {

			ArrayList<Actor> actorsToRemove = (ArrayList<Actor>) actorsHidingHere.clone();

			for (Actor gameObjectHidingHere : actorsToRemove) {
				new ActionStopHiding(gameObjectHidingHere, this).perform();
				// gameObjectHidingHere.hiding = false;
				// gameObjectHidingHere.hidingPlace = null;
			}
			actorsHidingHere.clear();
		}
		return destroyed;

	}

}
