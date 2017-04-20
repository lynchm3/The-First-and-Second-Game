package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionSearch;
import com.marklynch.objects.actions.ActionThrow;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class DropHole extends Searchable {

	public DropHole(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, false, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight,
				owner);

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth < 0)
			return actions;

		actions.add(new ActionSearch(performer, this));

		if (performer.equipped != null) {
			if (performer.straightLineDistanceTo(this.squareGameObjectIsOn) < 2) {
				actions.add(new ActionDrop(performer, this.squareGameObjectIsOn, performer.equipped));
			}
			actions.add(new ActionThrow(performer, this, performer.equipped));
		}

		return actions;

	}

	@Override
	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
		// if (inventory.size() == 0) {
		// return false;
		// } else {
		// new ActionLootAll(Game.level.player, this).perform();
		// return true;
		// }
	}

	@Override
	public DropHole makeCopy(Square square, Actor owner) {
		return new DropHole(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, weight, owner);
	}

}
