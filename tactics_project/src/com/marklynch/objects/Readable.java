package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionRead;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Readable extends GameObject {

	private Object[] text;
	Conversation conversation;

	public Readable(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, Object[] text, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance, float weight,
			Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance, weight,
				owner);

		this.text = text;
		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartSaveTheWolf = new ConversationPart(this.text,
				new ConversationResponse[] { conversationReponseDone }, this);
		conversation = new Conversation(conversationPartSaveTheWolf);

	}

	public Readable makeCopy(Square square, String name, Object[] text, Actor owner) {
		return new Readable(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, text, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				waterResistance, electricResistance, poisonResistance, weight, owner);
	}

	@Override
	public Readable makeCopy(Square square, Actor owner) {
		return new Readable(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, text, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				waterResistance, electricResistance, poisonResistance, weight, owner);
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

	public void setText(Object[] text) {
		this.text = text;
		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartSaveTheWolf = new ConversationPart(this.text,
				new ConversationResponse[] { conversationReponseDone }, this);
		conversation = new Conversation(conversationPartSaveTheWolf);
	}

	public Object[] getText() {
		return text;
	}

}
