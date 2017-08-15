package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class HidingPlace extends Searchable {

	public ArrayList<Actor> actorsHidingHere = new ArrayList<Actor>();

	public HidingPlace(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean attackable, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance,
			float slashResistance, float weight, Actor owner, Effect[] effects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner, effects);
		canBePickedUp = false;

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
		return new ActionHide(performer, this);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionAttack(performer, this);
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
		super.draw2();
	}

	@Override
	public HidingPlace makeCopy(Square squareGameObjectIsOn, Actor owner) {
		return new HidingPlace(new String(name), (int) totalHealth, imageTexturePath, squareGameObjectIsOn,
				new Inventory(), showInventory, canShareSquare, fitsInInventory, canContainOtherObjects,
				blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner, effectsFromInteracting);
	}

	// @Override
	// public void attacked(Object attacker) {
	// super.attacked(attacker);
	// for (Actor actor : actorsHidingHere) {
	// rr
	// }
	// }

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);
		if (destroyed) {

			if (actorsHidingHere.size() > 0) {
				ArrayList<Actor> actorsToRemove = (ArrayList<Actor>) actorsHidingHere.clone();

				for (Actor gameObjectHidingHere : actorsToRemove) {
					if (attacker instanceof GameObject) {
						((GameObject) attacker).addAttackerForThisAndGroupMembers(gameObjectHidingHere);
					}
					new ActionStopHiding(gameObjectHidingHere, this).perform();
					// gameObjectHidingHere.hiding = false;
					// gameObjectHidingHere.hidingPlace = null;
				}
				actorsHidingHere.clear();
			} else {
				if (attacker != null) {
					ArrayList<Square> adjacentSquares = this.getAllSquaresWithinDistance(1);
					for (Square adjacentSquare : adjacentSquares) {
						HidingPlace hidingPlace = (HidingPlace) adjacentSquare.inventory
								.getGameObjectOfClass(HidingPlace.class);
						if (hidingPlace != null && attacker instanceof GameObject)
							((GameObject) attacker).addAttackerForThisAndGroupMembers(hidingPlace);
					}
				}
			}
		}
		return destroyed;

	}

}
