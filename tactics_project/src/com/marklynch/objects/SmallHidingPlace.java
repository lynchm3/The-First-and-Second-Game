package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionStopHidingInside;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class SmallHidingPlace extends Searchable {

	public ArrayList<Actor> actorsHidingHere = new ArrayList<Actor>();

	public SmallHidingPlace(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, Effect[] effects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, effects);

		// Settings for BURROW
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = false;

	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
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
	public SmallHidingPlace makeCopy(Square squareGameObjectIsOn, Actor owner) {
		return new SmallHidingPlace(new String(name), (int) totalHealth, imageTexturePath, squareGameObjectIsOn,
				new Inventory(), widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, value, owner, effectsFromInteracting);
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
					new ActionStopHidingInside(gameObjectHidingHere, this).perform();
					// gameObjectHidingHere.hiding = false;
					// gameObjectHidingHere.hidingPlace = null;
				}
				actorsHidingHere.clear();
			} else {
				if (attacker != null) {
					ArrayList<Square> adjacentSquares = this.getAllSquaresWithinDistance(1);
					for (Square adjacentSquare : adjacentSquares) {
						SmallHidingPlace hidingPlace = (SmallHidingPlace) adjacentSquare.inventory
								.getGameObjectOfClass(SmallHidingPlace.class);
						if (hidingPlace != null && attacker instanceof GameObject)
							((GameObject) attacker).addAttackerForThisAndGroupMembers(hidingPlace);
					}
				}
			}
		}
		return destroyed;

	}

}
