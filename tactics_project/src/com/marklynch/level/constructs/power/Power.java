package com.marklynch.level.constructs.power;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

import mdesl.graphics.Texture;

public abstract class Power {

	public Texture image;
	public String name;
	GameObject source;
	GameObject target;
	Effect[] effects;
	public int loudness;

	public Power(String name, Texture image, GameObject source, GameObject target, Effect[] effects, int loudness) {
		super();
		this.name = name;
		this.image = image;
		this.source = source;
		this.target = target;
		this.effects = effects;
		this.loudness = loudness;
	}

	public abstract void cast(GameObject souce, Square target);

	public abstract ArrayList<Square> getAffectedSquares(Square target);

	public static void loadActionImages() {
		getGlobalImage("action_burn.png");
	}

	public abstract boolean hasRange(int distanceTo);

}
