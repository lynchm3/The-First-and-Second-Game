package com.marklynch.level.constructs.power;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Texture;

public abstract class Power {

	public Texture image;
	public String name;
	public String description = "POWER";
	public GameObject source;
	public GameObject target;
	public Effect[] effects;
	public int loudness;
	public boolean hostile;
	public boolean potentialyCriminal;
	public boolean selectTarget = false;
	public boolean draws = false;
	public Crime.TYPE crimeSeverity;
	public String disabledReason = null;
	public boolean passive = false;
	public boolean toggledOn = true;
	public boolean activateAtStartOfTurn = false;
	public boolean endsTurn = true;

	public int range;
	public Point[] castLocations;
	public Point[] areaOfEffect;
	public String illegalReason;

	public Power(String name, Texture image, GameObject source, Effect[] effects, int range, Point[] castLocations,
			Point[] areaOfEffect, int loudness, boolean hostile, boolean potentiallyCriminal,
			Crime.TYPE crimeSeverity) {
		this.name = name;
		this.image = image;
		this.source = source;
		this.effects = effects;
		this.range = range;
		this.castLocations = castLocations;
		this.areaOfEffect = areaOfEffect;
		this.loudness = loudness;
		this.hostile = hostile;
		this.potentialyCriminal = potentiallyCriminal;
		this.crimeSeverity = crimeSeverity;
	}

	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {

		CopyOnWriteArrayList<Square> affectedSquares = getAffectedSquares(targetSquare);

		for (Square square : affectedSquares) {
			for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) square.inventory.getGameObjects()) {
				for (Effect effect : effects) {
					gameObject.addEffect(effect.makeCopy(source, gameObject));
				}
			}
		}
	}

	public CopyOnWriteArrayList<Square> getAffectedSquares(Square targetSquare) {
		CopyOnWriteArrayList<Square> affectedSquares = new CopyOnWriteArrayList<Square>(Square.class);
		for (Point point : areaOfEffect) {
			int squareX = targetSquare.xInGrid + point.getX();
			int squareY = targetSquare.yInGrid + point.getY();

			if (Square.inRange(squareX, squareY)) {
				affectedSquares.add(Game.level.squares[squareX][squareY]);
			}
		}

		return affectedSquares;
	}

	public boolean hasRange(int weaponDistanceTo) {

		if (range >= weaponDistanceTo)
			return true;

		return false;
	}

	public boolean squareInCastLocations(GameObject caster, Square potentialCastLocation) {

		if (castLocations == null)
			return true;

		for (Point point : castLocations) {
			int castLocationSquareX = caster.squareGameObjectIsOn.xInGrid + point.getX();
			int castLocationSquareY = caster.squareGameObjectIsOn.yInGrid + point.getY();
			if (Square.squareExists(castLocationSquareX, castLocationSquareY)) {
				Square castLocationSquare = Level.squares[castLocationSquareX][castLocationSquareY];
				if (potentialCastLocation == castLocationSquare) {
					return true;
				}
			}
		}
		return false;
	}

	public static void loadActionImages() {
		getGlobalImage("effect_bleed.png", false);
		getGlobalImage("action_burn.png", false);
		getGlobalImage("action_stop_hiding.png", false);
		getGlobalImage("action_heal.png", false);
		getGlobalImage("action_poison.png", false);
		getGlobalImage("action_unlock.png", false);

	}

	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

	public boolean checkRange(Actor source, Square targetSquare) {
		return true;
	}

	public void log(GameObject performer, Square target2) {
		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ", name }));
	}

	public void drawUI() {

	}

	public abstract Power makeCopy(GameObject source);

	public static Point[] castLocationsLine1 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1) };

	public static Point[] castLocationsLine2 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2) };

	public static Point[] castLocationsLine3 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3) };

	public static Point[] castLocationsLine4 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4) };

	public static Point[] castLocationsLine5 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4)
			//
			, new Point(5, 0), new Point(-5, 0), new Point(0, 5), new Point(0, -5) };

	public static Point[] castLocationsLine6 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4)
			//
			, new Point(5, 0), new Point(-5, 0), new Point(0, 5), new Point(0, -5)
			//
			, new Point(6, 0), new Point(-6, 0), new Point(0, 6), new Point(0, -6) };

	public static Point[] castLocationsLine7 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4)
			//
			, new Point(5, 0), new Point(-5, 0), new Point(0, 5), new Point(0, -5)
			//
			, new Point(6, 0), new Point(-6, 0), new Point(0, 6), new Point(0, -6)
			//
			, new Point(7, 0), new Point(-7, 0), new Point(0, 7), new Point(0, -7) };

	public static Point[] castLocationsLine8 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4)
			//
			, new Point(5, 0), new Point(-5, 0), new Point(0, 5), new Point(0, -5)
			//
			, new Point(6, 0), new Point(-6, 0), new Point(0, 6), new Point(0, -6)
			//
			, new Point(7, 0), new Point(-7, 0), new Point(0, 7), new Point(0, -7)
			//
			, new Point(8, 0), new Point(-8, 0), new Point(0, 8), new Point(0, -8) };

	public static Point[] castLocationsLine9 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4)
			//
			, new Point(5, 0), new Point(-5, 0), new Point(0, 5), new Point(0, -5)
			//
			, new Point(6, 0), new Point(-6, 0), new Point(0, 6), new Point(0, -6)
			//
			, new Point(7, 0), new Point(-7, 0), new Point(0, 7), new Point(0, -7)
			//
			, new Point(8, 0), new Point(-8, 0), new Point(0, 8), new Point(0, -8)
			//
			, new Point(9, 0), new Point(-9, 0), new Point(0, 9), new Point(0, -9) };

	public static Point[] castLocationsLine10 = new Point[] {
			//
			new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)
			//
			, new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2)
			//
			, new Point(3, 0), new Point(-3, 0), new Point(0, 3), new Point(0, -3)
			//
			, new Point(4, 0), new Point(-4, 0), new Point(0, 4), new Point(0, -4)
			//
			, new Point(5, 0), new Point(-5, 0), new Point(0, 5), new Point(0, -5)
			//
			, new Point(6, 0), new Point(-6, 0), new Point(0, 6), new Point(0, -6)
			//
			, new Point(7, 0), new Point(-7, 0), new Point(0, 7), new Point(0, -7)
			//
			, new Point(8, 0), new Point(-8, 0), new Point(0, 8), new Point(0, -8)
			//
			, new Point(9, 0), new Point(-9, 0), new Point(0, 9), new Point(0, -9)
			//
			, new Point(10, 0), new Point(-10, 0), new Point(0, 10), new Point(0, -10) };

	public static Point[] castLocationsOnly2 = new Point[] { //
			new Point(2, 0), new Point(-2, 0), new Point(0, 2), new Point(0, -2) };

}
