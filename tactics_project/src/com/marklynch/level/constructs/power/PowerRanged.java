package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class PowerRanged extends Power {

	int range;
	Point[] areaOfEffect;

	public PowerRanged(String name, GameObject source, GameObject target, Effect[] effects, int range,
			Point[] areaOfEffect) {
		super(name, source, target, effects);
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

}
