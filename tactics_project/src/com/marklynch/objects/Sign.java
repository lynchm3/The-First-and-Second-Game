package com.marklynch.objects;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Sign extends Readable {

	Conversation conversation;

	public Sign(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			Conversation conversation, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, conversation, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner);

		if (conversation != null) {
			this.conversation = conversation;
			conversation.openingConversationPart.talker = this;
		}

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

	}

	@Override
	public Sign makeCopy(Square square, String name, Conversation conversation, Actor owner) {
		return new Sign(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				conversation, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, owner);
	}

}
