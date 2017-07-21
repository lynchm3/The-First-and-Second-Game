package com.marklynch.objects;

import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionOpen;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class SwitchForOpenables extends Switch {

	Openable openable;
	SWITCH_TYPE switchType;
	AILine aiLine;

	public enum SWITCH_TYPE {
		OPEN_CLOSE, LOCK_UNLOCK
	}

	public SwitchForOpenables(String name, int health, String imagePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen,
			boolean attackable, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float weight, Actor owner, String actionName, String actionVerb, Openable openable,
			SWITCH_TYPE switchType) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance, weight,
				owner, actionName, actionVerb);
		this.openable = openable;
		this.switchType = switchType;
		if (openable != null)
			this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, this, openable.squareGameObjectIsOn);
	}

	@Override
	public void draw2() {
		super.draw2();
		aiLine.target = this.openable.squareGameObjectIsOn;
		aiLine.draw2();
	}

	@Override
	public void use() {
		if (switchType == SWITCH_TYPE.OPEN_CLOSE) {
			if (openable.open) {
				new ActionClose(this, openable).perform();
			} else {
				new ActionOpen(this, openable).perform();
			}
		} else if (switchType == SWITCH_TYPE.LOCK_UNLOCK) {
			if (openable.locked) {
				new ActionUnlock(this, openable).perform();
			} else {
				new ActionLock(this, openable).perform();
			}
		}

	}

	public SwitchForOpenables makeCopy(Square square, Actor owner, Openable openable, SWITCH_TYPE switchType) {
		return new SwitchForOpenables(new String(name), (int) totalHealth, imageTexturePath, square,
				inventory.makeCopy(), showInventory, canShareSquare, fitsInInventory, canContainOtherObjects,
				blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, weight, owner,
				actionName, actionVerb, openable, switchType);
	}

}
