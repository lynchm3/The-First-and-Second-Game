package com.marklynch.objects.weapons;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class LegArmor extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public LegArmor(String name, String imagePath, float health, Square squareGameObjectIsOn, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float iceResistance, float electricResistance, float poisonResistance, Actor owner,
			float anchorX, float anchorY) {

		super(name, (int) health, imagePath, squareGameObjectIsOn, new Inventory(), false, true, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, 1, owner);

		this.owner = owner;
		this.anchorX = anchorX;
		this.anchorY = anchorY;

	}

	@Override
	public Action getDefaultActionInInventory(Actor performer) {
		return new ActionEquip(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsInInventory(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionEquip(performer, this));
		actions.add(new ActionDrop(performer, performer.squareGameObjectIsOn, this));
		return actions;
	}

	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public LegArmor makeCopy(Square square, Actor owner) {
		return new LegArmor(new String(name), imageTexturePath, totalHealth, square, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, owner,
				anchorX, anchorY);
	}
}
