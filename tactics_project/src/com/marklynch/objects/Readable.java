package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionRead;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Readable extends GameObject {

	Conversation conversation;

	public Readable(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			Conversation conversation, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner);

		if (conversation != null) {
			this.conversation = conversation;
			conversation.openingConversationPart.talker = this;
		}

	}

	public Readable makeCopy(Square square, String name, Conversation conversation, Actor owner) {
		return new Readable(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				conversation, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, owner);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionRead(performer, this);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionAttack(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionRead(performer, this));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	@Override
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
		conversation.openingConversationPart.talker = this;
	}

}
