package com.marklynch.level.constructs.rarity;

import com.marklynch.utils.Color;

public class Rarity {

	public final static Rarity GOD = new Rarity(1, Color.WHITE);
	public final static Rarity LEGEND = new Rarity(2, new Color(241, 236, 111));
	public final static Rarity HERO = new Rarity(3, Color.GREEN);
	public final static Rarity FIGHTER = new Rarity(4, Color.BLUE);
	public final static Rarity COMMON = new Rarity(5, Color.DARK_GRAY);

	// a nice red 240,122,121

	public final int ranking; // (1 is rarest)
	public final Color color;

	public Rarity(int ranking, Color color) {
		super();
		this.ranking = ranking;
		this.color = color;
	}

}
