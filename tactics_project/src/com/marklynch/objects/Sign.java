package com.marklynch.objects;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Sign extends Readable {

	Conversation conversation;

	public Sign() {
		super();

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;

	}

	@Override
	public Sign makeCopy(Square square, String name, Object[] conversationText, Actor owner) {
		return new Sign(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				conversationText, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, value, owner, templateId);
	}

}
