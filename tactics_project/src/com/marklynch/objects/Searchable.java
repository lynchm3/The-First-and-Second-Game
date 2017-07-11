package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionSearch;
import com.marklynch.objects.actions.ActionThrow;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Searchable extends GameObject {

	public Effect[] effectsFromInteracting;

	public Searchable(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean attackable, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance, float weight,
			Actor owner, Effect[] effectsFromSearching) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance, weight,
				owner);
		this.effectsFromInteracting = effectsFromSearching;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		actions.add(new ActionSearch(performer, this));

		if (attackable)
			actions.add(new ActionAttack(performer, this));

		if (performer.equipped != null) {
			if (performer.straightLineDistanceTo(this.squareGameObjectIsOn) < 2) {
				actions.add(new ActionDrop(performer, this.squareGameObjectIsOn, performer.equipped));
			}
			actions.add(new ActionThrow(performer, this, performer.equipped));
		}

		return actions;

	}

	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public Searchable makeCopy(Square squareGameObjectIsOn, Actor owner) {
		return new Searchable(new String(name), (int) totalHealth, imageTexturePath, squareGameObjectIsOn, inventory,
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, attackable, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				waterResistance, electricResistance, poisonResistance, weight, owner, effectsFromInteracting);
	}

}
