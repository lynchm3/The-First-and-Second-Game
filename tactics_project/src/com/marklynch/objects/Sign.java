package com.marklynch.objects;

import com.marklynch.level.Square;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionRead;
import com.marklynch.objects.units.Actor;

public class Sign extends GameObject {

	String text;
	Conversation conversation;

	public Sign(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			String text, float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, widthRatio, heightRatio);

		this.text = text;
		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartSaveTheWolf = new ConversationPart(this.text,
				new ConversationResponse[] { conversationReponseDone }, this);
		conversation = new Conversation(conversationPartSaveTheWolf);

	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Sign(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, text, widthRatio, heightRatio);
	}

	@Override
	public Action getDefaultAction(Actor performer) {
		System.out.println("Sign.getDefaultAction");
		return new ActionRead(performer, this);
	}

	@Override
	public Conversation getConversation() {
		return conversation;
	}

}
