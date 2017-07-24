package com.marklynch.level.constructs;

import com.marklynch.Game;
import com.marklynch.objects.actions.ActionSpot;

public class Area {

	public String name;
	public int x1, y1, x2, y2;
	public boolean seenByPlayer = false;

	public Area(String name, int x1, int y1, int x2, int y2) {
		super();
		this.name = name;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				Game.level.squares[i][j].areaSquareIsIn = this;
			}
		}
	}

	public void hasBeenSeenByPlayer() {
		this.seenByPlayer = true;
		new ActionSpot(Game.level.player, this).perform();
	}

}
