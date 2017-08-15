package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionFillContainersInInventory;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class WaterSource extends GameObject {

	public Effect[] effectsFromInteracting;

	public WaterSource(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner, Effect[] effectsFromSearching) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner);
		this.effectsFromInteracting = effectsFromSearching;

		// WELL
		// These are basically settings for a WELL at the moment...
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		actions.add(new ActionFillContainersInInventory(performer, this));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public WaterSource makeCopy(Square squareGameObjectIsOn, Actor owner) {
		return new WaterSource(new String(name), (int) totalHealth, imageTexturePath, squareGameObjectIsOn,
				new Inventory(), widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, owner, effectsFromInteracting);
	}

}
