package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public abstract class SecondaryAnimation extends Animation {

	public SecondaryAnimation(GameObject performer, OnCompletionListener onCompletionListener, Square[] targetSquares,
			Square targetSquare, GameObject projectileObject, Action action, Actor shooter, GameObject weapon,
			boolean alwaysRun, Object... objectsInvolved) {
		super(performer, onCompletionListener, targetSquares, targetSquare, projectileObject, action, shooter, weapon,
				alwaysRun, objectsInvolved);
	}

	@Override
	public void runCompletionAlorightm(boolean wait) {
		if (completed)
			return;

		completed = true;

		childRunCompletionAlgorightm(wait);

		Level.blockingAnimations.remove(this);

		Level.animations.remove(this);

		Level.secondaryAnimations.remove(this);

		if (onCompletionListener != null)
			onCompletionListener.animationComplete(performer);
	}

}
