package com.marklynch.objects;

import com.marklynch.level.constructs.actionlisteners.ActionListener;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionRead;
import com.marklynch.objects.units.Actor;

public class Readable extends GameObject {

	Conversation conversation;
	private ActionListener onReadListener;

	public Readable() {
		super();

		// BOOK / SCROLL
		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

	}

	public Readable makeCopy(Square square, String name, Object[] conversationText, Actor owner) {

		this.conversation = this.createConversation(conversationText);
		return new Readable(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				conversationText, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, value, owner, templateId);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		if (this.canShareSquare) {
			return null;
		} else {
			return new ActionRead(performer, this);
		}
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionRead(performer, this);
	}

	@Override
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
		conversation.openingConversationPart.talker = this;
	}

	public void setOnReadListener(ActionListener actionListener) {
		this.onReadListener = actionListener;

	}

	public void wasRead() {
		if (this.onReadListener != null) {
			onReadListener.onRead();
		}
	}

}
