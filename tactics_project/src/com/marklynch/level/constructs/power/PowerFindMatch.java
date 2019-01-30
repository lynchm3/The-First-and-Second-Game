package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.ResourceUtils;

public class PowerFindMatch extends Power {

	private static String NAME = "Find Match";

	public PowerFindMatch() {
		this(null);
	}

	public PowerFindMatch(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("find_match.png", false), source, new Effect[] {}, Integer.MAX_VALUE,
				null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		passive = true;
		endsTurn = false;
		draws = true;
//		selectTarget = true;
//		activateAtStartOfTurn = false;
		toggledOn = true;
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

//	@Override
//	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {
//
//		Power playersFindMathchPower = Level.player.getPower(PowerFindMatch.class);
//		if (playersFindMathchPower == null)
//			return;
//
//		if (targetGameObject == null && targetSquare != null) {
//			for (GameObject gameObjectOnTargetSquare : targetSquare.inventory.gameObjects) {
//				if (gameObjectOnTargetSquare.linkedGameObjects.size() > 0) {
//					targetGameObject = gameObjectOnTargetSquare;
//					break;
//				}
//			}
//		}
//
//		if (playersFindMathchPower.target == null && targetGameObject != null) {
//			playersFindMathchPower.target = targetToMatch = targetGameObject;
//			playersFindMathchPower.toggledOn = true;
//			playersFindMathchPower.selectTarget = false;
//		} else {
//			playersFindMathchPower.target = targetToMatch = null;
//			playersFindMathchPower.toggledOn = false;
//			playersFindMathchPower.selectTarget = true;
//		}
//	}

	@Override
	public void log(GameObject performer, Square target2) {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ",
		// name }));
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerFindMatch(source);
	}

	@Override
	public void drawUI() {

		if (toggledOn == false)
			return;

		GameObject targetGameObject = Game.gameObjectMouseIsOver;

		// If we don't have direct object, check the sqr for objects with links
		if (targetGameObject == null) {
			if (Game.squareMouseIsOver != null && !(Game.squareMouseIsOver instanceof InventorySquare)) {
				for (GameObject gameObjectOnTargetSquare : Game.squareMouseIsOver.inventory.gameObjects) {
					if (gameObjectOnTargetSquare.linkedGameObjects.size() > 0) {
						targetGameObject = gameObjectOnTargetSquare;
						break;
					}
				}
			}
		}

		// Still no gameObject :/
		if (targetGameObject == null)
			return;

		// If gameObject has no links, check it's equipped item...
		if (targetGameObject.linkedGameObjects.size() == 0) {
			if (targetGameObject.equipped != null)
				targetGameObject = targetGameObject.equipped;
		}

		if (targetGameObject.linkedGameObjects.size() == 0)
			return;

		Square square = targetGameObject.getWorldSquareGameObjectIsOn();
		if (square == null)
			return;
		float x1 = (Game.halfWindowWidth) + (Game.zoom * (square.xInGridPixels + targetGameObject.drawOffsetX
				+ targetGameObject.halfWidth - Game.halfWindowWidth + Game.getDragXWithOffset()));

		float y1 = (Game.halfWindowHeight) + (Game.zoom * (square.yInGridPixels + targetGameObject.drawOffsetY
				+ targetGameObject.halfHeight - Game.halfWindowHeight + Game.getDragYWithOffset()));

		System.out.println("targetGameObject.drawOffsetX = " + targetGameObject.drawOffsetX);
		System.out.println("targetGameObject.halfWidth = " + targetGameObject.halfWidth);

		for (GameObject linkedGameObject : targetGameObject.linkedGameObjects) {
			Square linkedGameObjectSquare = linkedGameObject.getWorldSquareGameObjectIsOn();
			if (linkedGameObjectSquare == null)
				continue;

			float x2 = (Game.halfWindowWidth)
					+ (Game.zoom * (linkedGameObjectSquare.xInGridPixels + linkedGameObject.drawOffsetX
							+ linkedGameObject.halfWidth - Game.halfWindowWidth + Game.getDragXWithOffset()));

			float y2 = (Game.halfWindowHeight)
					+ (Game.zoom * (linkedGameObjectSquare.yInGridPixels + linkedGameObject.drawOffsetY
							+ linkedGameObject.halfHeight - Game.halfWindowHeight + Game.getDragYWithOffset()));

			LineUtils.drawLine(Color.RED, x1, y1, x2, y2, 1);
		}
	}

}
