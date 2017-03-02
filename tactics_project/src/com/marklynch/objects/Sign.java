package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionRead;
import com.marklynch.objects.units.Actor;

public class Sign extends GameObject {

	private Object[] text;
	Conversation conversation;

	public Sign(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, Object[] text, float widthRatio,
			float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio);

		this.text = text;
		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartSaveTheWolf = new ConversationPart(this.text,
				new ConversationResponse[] { conversationReponseDone }, this);
		conversation = new Conversation(conversationPartSaveTheWolf);

	}

	@Override
	public Sign makeCopy(Square square) {
		return new Sign(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, text, widthRatio, heightRatio);
	}

	@Override
	public Action getDefaultActionInWorld(Actor performer) {
		return new ActionRead(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.addAll(super.getAllActionsInWorld(performer));
		actions.add(new ActionRead(performer, this));
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
