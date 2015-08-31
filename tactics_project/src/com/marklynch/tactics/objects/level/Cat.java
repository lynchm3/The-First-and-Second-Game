package com.marklynch.tactics.objects.level;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;

public class Cat extends Decoration {

	public final double minDistanceFromPeople = 500;
	public final double interactionDistance = 300;

	public Cat(String name, float x, float y, float width, float height,
			boolean background, String imagePath) {
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
				int actorPositionXInPixels = actor.squareGameObjectIsOn.x
						* (int) Game.SQUARE_WIDTH;
				int actorPositionYInPixels = actor.squareGameObjectIsOn.y
						* (int) Game.SQUARE_HEIGHT;
				double x2 = actorPositionXInPixels + 64;
				double y2 = actorPositionYInPixels + 64;
				double distanceToActor = Math.sqrt((x1 - x2) * (x1 - x2)
						+ (y1 - y2) * (y1 - y2));
				if (distanceToActor < closestDistance) {
					closestDistance = distanceToActor;
					closestActor = actor;
					closestX2 = x2;
					closestY2 = y2;
				}
			}
		}

		System.out.println("closestDistance = " + closestDistance);

		if (closestDistance < minDistanceFromPeople) {
			if (closestDistance == 0)
				closestDistance = 1;
			double distanceToMoveX = ((x1 - closestX2) / closestDistance)
					* delta;
			double distanceToMoveY = ((y1 - closestY2) / closestDistance)
					* delta;

			double potentialX = this.x + distanceToMoveX;
			if (potentialX > 0
					&& potentialX < Game.level.width * Game.SQUARE_WIDTH)
				this.x = (float) potentialX;

			double potentialY = this.y + distanceToMoveY;
			if (potentialY > 0
					&& potentialY < Game.level.height * Game.SQUARE_HEIGHT)
				this.y = (float) potentialY;

			System.out.println("distanceToMoveX = " + distanceToMoveX);
			System.out.println("distanceToMoveY = " + distanceToMoveY);
			System.out.println("this.x = " + this.x);
			System.out.println("this.y = " + this.y);
		}
	}
}
