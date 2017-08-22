package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionSkin;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Carcass extends GameObject {
	protected String baseName;

	public Carcass(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner);
		baseName = new String(name);
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionSkin(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		actions.add(new ActionSkin(performer, this));

		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		return actions;

	}

	public Carcass makeCopy(String name, Square square, Actor owner, float weight) {
		return new Carcass(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
	}

	public Carcass makeCopy(Square square, Actor owner, float weight) {
		return new Carcass(new String(baseName), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
	}

	@Override
	public void inventoryChanged() {
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
	}

}
