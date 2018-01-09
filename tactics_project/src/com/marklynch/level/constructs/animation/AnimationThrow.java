package com.marklynch.level.constructs.animation;

import com.marklynch.objects.weapons.Projectile;

public class AnimationThrow extends Animation {

	Projectile projectile;

	public AnimationThrow(Projectile projectile) {
		super();
		blockAI = true;
		this.projectile = projectile;
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		if (projectile.landed) {
			completed = true;
			return;
		}
	}
}
