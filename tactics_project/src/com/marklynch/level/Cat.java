package com.marklynch.level;

import com.marklynch.Game;
import com.marklynch.level.constructs.Faction;
import com.marklynch.objects.units.Actor;

public class Cat extends Decoration {

	public final double minDistanceFromPeople = 500;
	public final double interactionDistance = 300;

	public CatState state = CatState.WAITING;

	public enum CatState {
		WAITING, WANDERING, RUNNING
	}

	double wanderingTargetX = 0d;
	double wanderingTargetY = 0d;

	public Cat(String name, float x, float y, float width, float height, boolean background, String imagePath) {
		super(name, x, y, width, height, background, imagePath);

	}

	@Override
	public void update(double delta) {

		// find closest actor
		// if closer than 100 px move in opposite direction to them

		// weird effect having some stuff turn based on some stuff not

		double closestDistance = Integer.MAX_VALUE;
		Actor closestActor = null;
		double closestX2 = Integer.MAX_VALUE;
		double closestY2 = Integer.MAX_VALUE;
		double x1 = x + width / 2;
		double y1 = y + height / 2;

		for (Faction faction : Game.level.factions) {
			for (Actor actor : faction.actors) {
				// Draw object
				int actorPositionXInPixels = actor.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
				int actorPositionYInPixels = actor.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;
				double x2 = actorPositionXInPixels + 64;
				double y2 = actorPositionYInPixels + 64;
				double distanceToActor = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
				if (distanceToActor < closestDistance) {
					closestDistance = distanceToActor;
					closestActor = actor;
					closestX2 = x2;
					closestY2 = y2;
				}
			}
		}

		if (closestDistance < minDistanceFromPeople) {
			state = CatState.RUNNING;
			if (closestDistance == 0)
				closestDistance = 1;
			double distanceToMoveX = ((x1 - closestX2) / closestDistance) * delta;
			double distanceToMoveY = ((y1 - closestY2) / closestDistance) * delta;

			double potentialX = this.x + distanceToMoveX;
			if (potentialX > 0 && potentialX < (Game.level.width - 1) * Game.SQUARE_WIDTH)
				this.x = (float) potentialX;

			double potentialY = this.y + distanceToMoveY;
			if (potentialY > 0 && potentialY < (Game.level.height - 1) * Game.SQUARE_HEIGHT)
				this.y = (float) potentialY;

			// Math.random() * 1000 == 0

		} else {

			if (state == CatState.RUNNING)
				state = CatState.WAITING;

			if (state == CatState.WANDERING) {
				double distanceToWanderTarget = Math.sqrt((x1 - this.wanderingTargetX) * (x1 - this.wanderingTargetX)
						+ (y1 - this.wanderingTargetY) * (y1 - this.wanderingTargetY));
				if (distanceToWanderTarget < 10) {
					state = CatState.WAITING;
				} else {
					double distanceToMoveX = -((x1 - this.wanderingTargetX) / distanceToWanderTarget) * delta * 0.1;
					double distanceToMoveY = -((y1 - this.wanderingTargetY) / distanceToWanderTarget) * delta * 0.1;
					this.x += distanceToMoveX;
					this.y += distanceToMoveY;
				}

			} else if (((int) (Math.random() * 1000)) == 1) {
				state = CatState.WANDERING;
				double maxX = (Game.level.width - 1) * Game.SQUARE_WIDTH;
				double maxY = (Game.level.height - 1) * Game.SQUARE_HEIGHT;
				this.wanderingTargetX = Math.random() * maxX;
				this.wanderingTargetY = Math.random() * maxY;

			}
		}
	}
}
