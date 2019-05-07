package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.TextUtils;

public class Signpost extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Object[] objects;

	public ArrayList<Object> upObjects = new ArrayList<Object>(Object.class);
	public ArrayList<Object> downObjects = new ArrayList<Object>(Object.class);
	public ArrayList<Object> leftObjects = new ArrayList<Object>(Object.class);
	public ArrayList<Object> rightObjects = new ArrayList<Object>(Object.class);

	public Signpost() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Signpost makeCopy(Square square, Actor owner, Object... objects) {
		Signpost signpost = new Signpost();
		setInstances(signpost);
		super.setAttributesForCopy(signpost, square, owner);
		this.objects = objects;

		// sort out objects
		for (Object object : objects) {
			Direction direction = getDirectionObjectIsIn(object, square);
			if (direction == Direction.UP) {
				System.out.println("Putting obj in up - " + object);
				upObjects.add(object);
			} else if (direction == Direction.DOWN) {
				System.out.println("Putting obj in down - " + object);
				downObjects.add(object);
			} else if (direction == Direction.LEFT) {
				System.out.println("Putting obj in left - " + object);
				leftObjects.add(object);
			} else if (direction == Direction.RIGHT) {
				System.out.println("Putting obj in right - " + object);
				rightObjects.add(object);
			}
		}

		signpost.conversation = createConversation(generateText());

//		signpost.conversation = signpost.createConversation(new Object[] { GameObject.upTexture, " Shop  ",
//				GameObject.rightTexture, " Estates  ", GameObject.downTexture, " Farm" });

		return signpost;
	}

	public Conversation createConversation(Object[] text) {

		Conversation c = Conversation.createConversation(text, this);
		System.out.println("c =  " + c);

		return c;
	}

	public Direction getDirectionObjectIsIn(Object object, Square from) {
		Square to = null;
		if (object instanceof Area) {
			Area area = (Area) object;
			to = area.centreSquare;
		} else if (object instanceof Structure) {
			Structure structure = (Structure) object;
			to = structure.centreSquare;
		} else if (object instanceof StructureRoom) {
			StructureRoom structureRoom = (StructureRoom) object;
			to = structureRoom.roomParts[0].centreSquare;
		}

		if (to != null)
			return getDirectionSquareIsIn(to, from);

		return Direction.LEFT;
	}

	public Direction getDirectionSquareIsIn(Square to, Square from) {

		int leftRightDifference = to.xInGrid - from.xInGrid;
		int upDownDifference = to.yInGrid - from.yInGrid;
		boolean leftRightDirection = Math.abs(leftRightDifference) >= Math.abs(upDownDifference);
		if (leftRightDirection) {
			if (leftRightDifference < 0)
				return Direction.LEFT;
			else
				return Direction.RIGHT;

		} else {
			if (upDownDifference < 0)
				return Direction.UP;
			else
				return Direction.DOWN;
		}
	}

	@Override
	public boolean draw1() {

		boolean shouldDraw = super.draw1();
		if (!shouldDraw)
			return false;

		// Draw object
		if (squareGameObjectIsOn != null) {
		}
		return true;
	}

	public Object[] generateText() {

		ArrayList<Object> arrayListOfText = new ArrayList<Object>(Object.class);

		// North
		if (upObjects.size() > 0) {
			System.out.println("upObjects.size() > 0");
			if (arrayListOfText.size() != 0)
				arrayListOfText.add(TextUtils.NewLine.NEW_LINE);
			System.out.println("NRTH");
			arrayListOfText.add(GameObject.upTexture);
			arrayListOfText.add(" ");
			for (int i = 0; i < upObjects.size(); i++) {
				arrayListOfText.add(upObjects.get(i));
				if (i != upObjects.size() - 1)
					arrayListOfText.add(", ");
			}
		}

		Object[] conversationText = arrayListOfText.toArray(new Object[arrayListOfText.size()]);
		System.out.println("arrayListOfText.get(0) = " + arrayListOfText.get(0));
		System.out.println("conversationText = " + conversationText);

//		Object[] conversationText = { "WANTED!", TextUtils.NewLine.NEW_LINE, crimesString, TextUtils.NewLine.NEW_LINE,
//				Templates.GOLD.imageTexture, "Reward " + reward };

		return conversationText;

	}

}
