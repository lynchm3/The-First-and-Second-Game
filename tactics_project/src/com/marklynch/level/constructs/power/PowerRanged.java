package com.marklynch.level.constructs.power;

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
	public void cast(GameObject source, Square targtSquare) {
		for (Point point : areaOfEffect) {
			int squareX = targtSquare.xInGrid + point.getX();
			int squareY = targtSquare.yInGrid + point.getY();

			if (Square.inRange(squareX, squareY)) {
				Square square = Game.level.squares[squareX][squareY];
				for (GameObject gameObject : square.inventory.getGameObjects()) {
					for (Effect effect : effects) {
						gameObject.addEffect(effect.makeCopy(source, gameObject));
					}
				}
			}
		}

	}

}
