package com.marklynch.ai.utils;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.LineUtils;

public class AILine {

	mdesl.graphics.Color color;
	Actor source;
	GameObject target;

	public AILine(mdesl.graphics.Color red, Actor source, GameObject target) {
		super();
		this.color = red;
		this.source = source;
		this.target = target;
	}

	public void draw2() {
		// TODO Auto-generated method stub
		float x1 = this.source.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
		float y1 = this.source.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;
		float x2 = this.target.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
		float y2 = this.target.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;

		System.out.println("gridx1 = " + this.source.squareGameObjectIsOn.xInGrid);
		System.out.println("gridy1 = " + this.source.squareGameObjectIsOn.yInGrid);
		System.out.println("gridx2 = " + this.target.squareGameObjectIsOn.xInGrid);
		System.out.println("gridy2 = " + this.target.squareGameObjectIsOn.yInGrid);

		LineUtils.drawLine(color, x1, y1, x2, y2, 10f);
		// LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 10f);
		// QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 0.5f), x1, y1, x2,
		// y2);

	}
}
