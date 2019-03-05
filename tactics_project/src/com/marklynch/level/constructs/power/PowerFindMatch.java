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
import com.marklynch.utils.TextUtils;

public class PowerFindMatch extends Power {

	private static String NAME = "Find Match";

	// Keys to doors, switches to what they activeate, portals to where they go

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
		GameObject topLevelGameObject = Game.gameObjectMouseIsOver;

		// If we don't have direct object, check the sqr for objects with links
		if (targetGameObject == null) {
			if (Game.squareMouseIsOver != null && !(Game.squareMouseIsOver instanceof InventorySquare)) {
				for (GameObject gameObjectOnTargetSquare : Game.squareMouseIsOver.inventory.gameObjects) {
					if (gameObjectOnTargetSquare.linkedObjects.size() > 0) {
						targetGameObject = topLevelGameObject = gameObjectOnTargetSquare;
						break;
					}
				}
			}
		}

		// Still no gameObject :/
		if (targetGameObject == null)
			return;

		// If gameObject has no links, check it's equipped item...
		if (targetGameObject.linkedObjects.size() == 0) {
			if (targetGameObject.equipped != null) {
				targetGameObject = targetGameObject.equipped;
			}
		}

		if (targetGameObject.linkedObjects.size() == 0)
			return;

		Square square = topLevelGameObject.getWorldSquareGameObjectIsOn();
		if (square == null)
			return;

		if (!targetGameObject.discoveredObject)
			return;

		float x1 = 0;
		float y1 = 0;
		x1 = (Game.halfWindowWidth) + (Game.zoom * (square.xInGridPixels + topLevelGameObject.drawOffsetX
				+ topLevelGameObject.halfWidth - Game.halfWindowWidth + Game.getDragXWithOffset()));

		y1 = (Game.halfWindowHeight) + (Game.zoom * (square.yInGridPixels + topLevelGameObject.drawOffsetY
				+ topLevelGameObject.halfHeight - Game.halfWindowHeight + Game.getDragYWithOffset()));

		boolean linkedObjectDiscovered = true;

		for (Object linkedObject : targetGameObject.linkedObjects) {

			float x2 = 0;
			float y2 = 0;
			if (linkedObject instanceof Square) {

				Square linkedSquare = (Square) linkedObject;
				x2 = (Game.halfWindowWidth) + (Game.zoom * (linkedSquare.xInGridPixels + Game.HALF_SQUARE_WIDTH
						- Game.halfWindowWidth + Game.getDragXWithOffset()));

				y2 = (Game.halfWindowHeight) + (Game.zoom * (linkedSquare.yInGridPixels + Game.HALF_SQUARE_HEIGHT
						- Game.halfWindowHeight + Game.getDragYWithOffset()));

			} else {

				GameObject linkedGameObject = (GameObject) linkedObject;
				if (!linkedGameObject.discoveredObject)
					linkedObjectDiscovered = false;
				Square linkedGameObjectSquare = linkedGameObject.getWorldSquareGameObjectIsOn();
				if (linkedGameObjectSquare == null)
					continue;

				x2 = (Game.halfWindowWidth)
						+ (Game.zoom * (linkedGameObjectSquare.xInGridPixels + linkedGameObject.drawOffsetX
								+ linkedGameObject.halfWidth - Game.halfWindowWidth + Game.getDragXWithOffset()));

				y2 = (Game.halfWindowHeight)
						+ (Game.zoom * (linkedGameObjectSquare.yInGridPixels + linkedGameObject.drawOffsetY
								+ linkedGameObject.halfHeight - Game.halfWindowHeight + Game.getDragYWithOffset()));
			}

			LineUtils.drawLine(Color.RED, x1, y1, x2, y2, 1);

			float centerX = (x1 + x2) / 2;
			float centerY = (y1 + y2) / 2;

			Object linkedObjectText = linkedObject;
			if (!linkedObjectDiscovered)
				linkedObjectText = "???";

			TextUtils.printTextWithImages(centerX, centerY, Integer.MAX_VALUE, false, null, Color.WHITE,
					targetGameObject, " / ", linkedObjectText);
		}
	}

}
