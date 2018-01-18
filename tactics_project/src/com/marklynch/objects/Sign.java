package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Sign extends Readable {

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

		Sign readable = new Sign();
		super.setAttributesForCopy(readable, square, owner);
		readable.name = name;
		readable.conversation = readable.createConversation(conversationText);
		return readable;
	}

}
