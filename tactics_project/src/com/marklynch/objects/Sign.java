package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Sign extends Readable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Sign() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;

		persistsWhenCantBeSeen = true;

	}

	@Override
	public Sign makeCopy(Square square, String name, Object[] conversationText, Actor owner) {

		Sign readable = new Sign();
		instances.add(readable);
		super.setAttributesForCopy(readable, square, owner);
		readable.name = name;
		readable.conversation = readable.createConversation(conversationText);
		return readable;
	}

}
