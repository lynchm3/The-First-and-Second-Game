package com.marklynch.level.constructs.power;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.Texture;

public abstract class Power {

	public Texture image;
	public String name;
	public GameObject source;
	public GameObject target;
	public Effect[] effects;
	public int loudness;
	public boolean hostile;
	public boolean potentialyCriminal;
	public Crime.TYPE crimeSeverity;
	public String disabledReason = "";

	int range;
	Point[] areaOfEffect;

	public Power(String name, Texture image, GameObject source, Effect[] effects, int range, Point[] areaOfEffect,
			int loudness, boolean hostile, boolean potentiallyCriminal, Crime.TYPE crimeSeverity) {
		this.name = name;
		this.image = image;
		this.source = source;
		this.effects = effects;
		this.range = range;
		this.areaOfEffect = areaOfEffect;
		this.loudness = loudness;
		this.hostile = hostile;
		this.potentialyCriminal = potentiallyCriminal;
		this.crimeSeverity = crimeSeverity;
	}

	public void cast(Actor source, Square targetSquare) {

		ArrayList<Square> affectedSquares = getAffectedSquares(targetSquare);

		for (Square square : affectedSquares) {
			for (GameObject gameObject : square.inventory.getGameObjects()) {
				for (Effect effect : effects) {
					gameObject.addEffect(effect.makeCopy(source, gameObject));
				}
			}
		}
	}

	public ArrayList<Square> getAffectedSquares(Square targetSquare) {
		ArrayList<Square> affectedSquares = new ArrayList<Square>();
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

		// if (getEffectiveMinRange() == 1 && weaponDistanceTo == 0)
		// return true;
		//
		// if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <=
		// getEffectiveMaxRange()) {
		// return true;
		// }
		// return false;
	}

	public static void loadActionImages() {
		getGlobalImage("effect_bleed.png");
		getGlobalImage("action_burn.png");
		getGlobalImage("action_stop_hiding.png");
		getGlobalImage("action_heal.png");
		getGlobalImage("action_poison.png");
		getGlobalImage("action_unlock.png");

	}

	public boolean check(Actor source, Square targetSquare) {
		return true;
	}

	public boolean checkRange(Actor source, Square targetSquare) {
		return true;
	}

	public void log(Actor performer, Square target2) {
		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ", name }));
	}

}
