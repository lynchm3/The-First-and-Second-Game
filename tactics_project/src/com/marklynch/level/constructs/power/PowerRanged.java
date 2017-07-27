package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

import mdesl.graphics.Texture;

public class PowerRanged extends Power {

	int range;
	Point[] areaOfEffect;

	public PowerRanged(String name, Texture image, GameObject source, GameObject target, Effect[] effects, int range,
			Point[] areaOfEffect, int loudness, boolean hostile, boolean potentiallyCriminal) {
		super(name, image, source, target, effects, loudness, hostile, potentiallyCriminal);
		this.range = range;
		this.areaOfEffect = areaOfEffect;
	}

	@Override
	public void cast(GameObject source, Square targetSquare) {

		ArrayList<Square> affectedSquares = getAffectedSquares(targetSquare);

		for (Square square : affectedSquares) {
			for (GameObject gameObject : square.inventory.getGameObjects()) {
				for (Effect effect : effects) {
					gameObject.addEffect(effect.makeCopy(source, gameObject));
				}
			}
		}
	}

	@Override
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

	@Override
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

}
