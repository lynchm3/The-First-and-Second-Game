package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.constructs.area.Place;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Signpost extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Place[] places;

	public ArrayList<Place> upPlaces = new ArrayList<Place>(Place.class);
	public ArrayList<Place> downPlaces = new ArrayList<Place>(Place.class);
	public ArrayList<Place> leftPlaces = new ArrayList<Place>(Place.class);
	public ArrayList<Place> rightPlaces = new ArrayList<Place>(Place.class);

	public static Texture upPoint = ResourceUtils.getGlobalImage("signpost_up_point.png", false);
	public static Texture downPoint = ResourceUtils.getGlobalImage("signpost_down_point.png", false);
	public static Texture leftPoint = ResourceUtils.getGlobalImage("signpost_left_point.png", false);
	public static Texture rightPoint = ResourceUtils.getGlobalImage("signpost_right_point.png", false);

	public Signpost() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Signpost makeCopy(Square square, Actor owner, Place... places) {
		Signpost signpost = new Signpost();
		setInstances(signpost);
		super.setAttributesForCopy(signpost, square, owner);
		signpost.places = places;

		// sort out objects
		for (Place place : places) {
			Direction direction = getDirectionObjectIsIn(place, square);
			if (direction == Direction.UP) {
				System.out.println("Putting obj in up - " + place);
				signpost.upPlaces.add(place);
			} else if (direction == Direction.DOWN) {
				System.out.println("Putting obj in down - " + place);
				signpost.downPlaces.add(place);
			} else if (direction == Direction.LEFT) {
				System.out.println("Putting obj in left - " + place);
				signpost.leftPlaces.add(place);
			} else if (direction == Direction.RIGHT) {
				System.out.println("Putting obj in right - " + place);
				signpost.rightPlaces.add(place);
			}
		}

		signpost.conversation = signpost.createConversation(signpost.generateText());

//		signpost.conversation = signpost.createConversation(new Object[] { GameObject.upTexture, " Shop  ",
//				GameObject.rightTexture, " Estates  ", GameObject.downTexture, " Farm" });

		return signpost;
	}

	public Conversation createConversation(Object[] text) {

		Conversation c = Conversation.createConversation(text, this);
		System.out.println("c =  " + c);

		return c;
	}

	public Direction getDirectionObjectIsIn(Place place, Square from) {
		System.out.println("getDirectionObjectIsIn object = " + place);

		Square to = place.getCentreSquare();

		if (to != null)
			return getDirectionSquareIsIn(to, from);

		return Direction.LEFT;
	}

	public Direction getDirectionSquareIsIn(Square to, Square from) {
		System.out.println("getDirectionObjectIsIn to = " + to);
		System.out.println("getDirectionObjectIsIn from = " + from);

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

		boolean shouldDraw = super.shouldDraw();
		if (!shouldDraw)
			return false;

		// Square part
		float squareWidth = 16f;
		float squareHeight = 16f;
		float squareX1 = this.squareGameObjectIsOn.xInGridPixels + Game.HALF_SQUARE_WIDTH - squareWidth / 2f;
		float squareY1 = this.squareGameObjectIsOn.yInGridPixels;
		float squareX2 = squareX1 + squareWidth;
		float squareY2 = squareY1 + squareHeight;
		QuadUtils.drawQuad(Color.WHITE, squareX1, squareY1, squareX2, squareY2);

		// Pole
		float poleWidth = 8f;
		float poleHeight = Game.SQUARE_HEIGHT - squareHeight;
		float poleX1 = this.squareGameObjectIsOn.xInGridPixels + Game.HALF_SQUARE_WIDTH - poleWidth / 2;
		float poleY1 = squareY2;
		float poleX2 = poleX1 + poleWidth;
		float poleY2 = poleY1 + poleHeight;
		QuadUtils.drawQuad(Color.LIGHT_GRAY, poleX1, poleY1, poleX2, poleY2);

		// Up
		for (int i = 0; i < upPlaces.size(); i++) {

			float y1 = squareY1 - (i + 1) * squareHeight;
			float y2 = y1 + squareHeight;

			QuadUtils.drawQuad(Color.WHITE, squareX1, y1, squareX2, y2);

			TextureUtils.drawTexture(upPlaces.get(i).getIcon(), squareX1, y1, squareX2, y2);

			if (i == upPlaces.size() - 1) {
				y1 -= squareHeight;
				y2 -= squareHeight;
				TextureUtils.drawTexture(upPoint, squareX1, y1, squareX2, y2);
			}
		}

		// Down
		for (int i = 0; i < downPlaces.size(); i++) {

			float y1 = squareY2 + (i) * squareHeight;
			float y2 = y1 + squareHeight;

			QuadUtils.drawQuad(Color.WHITE, squareX1, y1, squareX2, y2);

			TextureUtils.drawTexture(downPlaces.get(i).getIcon(), squareX1, y1, squareX2, y2);

			if (i == downPlaces.size() - 1) {
				y1 += squareHeight;
				y2 += squareHeight;
				TextureUtils.drawTexture(downPoint, squareX1, y1, squareX2, y2);
			}
		}

		// Left
		for (int i = 0; i < leftPlaces.size(); i++) {

			float x1 = squareX1 - (i + 1) * squareWidth;
			float x2 = x1 + squareWidth;

			QuadUtils.drawQuad(Color.WHITE, x1, squareY1, x2, squareY2);

			TextureUtils.drawTexture(leftPlaces.get(i).getIcon(), x1, squareY1, x2, squareY2);

			if (i == leftPlaces.size() - 1) {
				x1 -= squareWidth;
				x2 -= squareWidth;
				TextureUtils.drawTexture(leftPoint, x1, squareY1, x2, squareY2);
			}
		}

		// Right
		for (int i = 0; i < rightPlaces.size(); i++) {

			float x1 = squareX2 + (i) * squareWidth;
			float x2 = x1 + squareWidth;

			QuadUtils.drawQuad(Color.WHITE, x1, squareY1, x2, squareY2);

			TextureUtils.drawTexture(rightPlaces.get(i).getIcon(), x1, squareY1, x2, squareY2);

			if (i == rightPlaces.size() - 1) {
				x1 += squareWidth;
				x2 += squareWidth;
				TextureUtils.drawTexture(rightPoint, x1, squareY1, x2, squareY2);
			}
		}

		return true;
	}

	public Object[] generateText() {

		ArrayList<Object> arrayListOfText = new ArrayList<Object>(Object.class);

		// North
		if (upPlaces.size() > 0) {
			System.out.println("upObjects.size() > 0");
			if (arrayListOfText.size() != 0)
				arrayListOfText.add(TextUtils.NewLine.NEW_LINE);
			System.out.println("NRTH");
			arrayListOfText.add(GameObject.upTexture);
			arrayListOfText.add(" ");
			for (int i = 0; i < upPlaces.size(); i++) {
				arrayListOfText.add(upPlaces.get(i));
				if (i != upPlaces.size() - 1)
					arrayListOfText.add(", ");
			}
		}

		// East
		if (rightPlaces.size() > 0) {
			if (arrayListOfText.size() != 0)
				arrayListOfText.add(TextUtils.NewLine.NEW_LINE);
			arrayListOfText.add(GameObject.rightTexture);
			arrayListOfText.add(" ");
			for (int i = 0; i < rightPlaces.size(); i++) {
				arrayListOfText.add(rightPlaces.get(i));
				if (i != rightPlaces.size() - 1)
					arrayListOfText.add(", ");
			}
		}

		// South
		if (downPlaces.size() > 0) {
			if (arrayListOfText.size() != 0)
				arrayListOfText.add(TextUtils.NewLine.NEW_LINE);
			arrayListOfText.add(GameObject.downTexture);
			arrayListOfText.add(" ");
			for (int i = 0; i < downPlaces.size(); i++) {
				arrayListOfText.add(downPlaces.get(i));
				if (i != downPlaces.size() - 1)
					arrayListOfText.add(", ");
			}
		}

		// West
		if (leftPlaces.size() > 0) {
			if (arrayListOfText.size() != 0)
				arrayListOfText.add(TextUtils.NewLine.NEW_LINE);
			arrayListOfText.add(GameObject.leftTexture);
			arrayListOfText.add(" ");
			for (int i = 0; i < leftPlaces.size(); i++) {
				arrayListOfText.add(leftPlaces.get(i));
				if (i != leftPlaces.size() - 1)
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
