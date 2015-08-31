package com.marklynch.tactics.objects.level;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;

public class Cat extends Decoration {

	public final double minDistanceFromPeople = 100;
	public final double interactionDistance = 50;

	public Cat(String name, float x, float y, float width, float height,
			boolean background, String imagePath) {
		super(name, x, y, width, height, background, imagePath);

	}

	@Override
	public void update(int delta) {

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

		if (closestDistance < minDistanceFromPeople) {
			double distanceToMoveX = (x1 - closestX2) / closestDistance;
			double distanceToMoveY = (y1 - closestY2) / closestDistance;
			this.x += distanceToMoveX;
			this.y += distanceToMoveY;
		}
	}
}
