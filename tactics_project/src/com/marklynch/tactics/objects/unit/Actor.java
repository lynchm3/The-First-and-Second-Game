package com.marklynch.tactics.objects.unit;

import java.util.Vector;

import org.lwjgl.opengl.GL11;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.ActivityLog;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class Actor extends GameObject {

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	public String title = "";
	public int actorLevel = 1;
	public int distanceMovedThisTurn = 0;
	public int travelDistance = 4;
	public Faction faction;
	public Weapon selectedWeapon = null;

	public Actor(String name, String title, int actorLevel, int health,
			int strength, int dexterity, int intelligence, int endurance,
			String imagePath, Square squareActorIsStandingOn,
			Vector<Weapon> weapons, int travelDistance, Level level) {
		super(name, health, strength, dexterity, intelligence, endurance,
				imagePath, squareActorIsStandingOn, weapons, level);
		this.title = title;
		this.actorLevel = actorLevel;
		this.travelDistance = travelDistance;
	}

	public void calculateReachableSquares(Square[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {

				Path pathToSquare = paths.get(squares[i][j]);
				if (pathToSquare == null
						|| pathToSquare.travelCost > (this.travelDistance - this.distanceMovedThisTurn)) {
					squares[i][j].reachableBySelectedCharater = false;
					// squares[i][j].distanceToSquare = Integer.MAX_VALUE;

				} else {
					squares[i][j].reachableBySelectedCharater = true;
					// squares[i][j].distanceToSquare = pathToSquare.travelCost;
				}

			}
		}
	}

	public void calculateAttackableSquares(Square[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].weaponsThatCanAttack.clear();
			}
		}

		for (Weapon weapon : weapons) {
			weapon.calculateAttackableSquares(squares);
		}
	}

	public static void highlightSelectedCharactersSquares(Level level) {
		level.activeActor.calculatePathToAllSquares(level.squares);
		level.activeActor.calculateReachableSquares(level.squares);
		level.activeActor.calculateAttackableSquares(level.squares);
	}

	public int weaponDistanceTo(Square square) {

		return Math.abs(square.x - this.squareGameObjectIsOn.x)
				+ Math.abs(square.y - this.squareGameObjectIsOn.y);

	}

	public boolean hasRange(int weaponDistance) {
		for (Weapon weapon : weapons) {
			if (weapon.range >= weaponDistance) {
				selectedWeapon = weapon;
				return true;
			}
		}
		return false;
	}

	public void attack(GameObject gameObject) {
		gameObject.remainingHealth -= selectedWeapon.damage;
		this.distanceMovedThisTurn = Integer.MAX_VALUE;
		level.logOnScreen(new ActivityLog("" + this.name + " attacked "
				+ gameObject.name + " with " + selectedWeapon.name + " for "
				+ selectedWeapon.damage + " damage", this.faction));
		if (gameObject.checkIfDestroyed())
			if (gameObject instanceof Actor)
				level.logOnScreen(new ActivityLog("" + this.name + " killed "
						+ gameObject.name, this.faction));
			else
				level.logOnScreen(new ActivityLog("" + this.name
						+ " destroyed a " + gameObject.name, this.faction));
	}

	@Override
	public boolean checkIfDestroyed() {
		if (remainingHealth <= 0) {
			this.squareGameObjectIsOn.gameObject = null;
			this.faction.actors.remove(this);
			return true;
		}
		return false;
	}

	public void moveTo(Square squareToMoveTo) {

		this.squareGameObjectIsOn.gameObject = null;
		this.squareGameObjectIsOn = null;
		this.distanceMovedThisTurn += squareToMoveTo.distanceToSquare;
		this.squareGameObjectIsOn = squareToMoveTo;
		squareToMoveTo.gameObject = level.activeActor;
		Actor.highlightSelectedCharactersSquares(level);
		level.logOnScreen(new ActivityLog("" + this.name + " moved to "
				+ squareToMoveTo, this.faction));

	}

	@Override
	public void draw() {
		super.draw();
		float weaponWidthInPixels = Game.SQUARE_WIDTH / 5;
		float weaponHeightInPixels = Game.SQUARE_HEIGHT / 5;
		for (int i = 0; i < weapons.size(); i++) {

			Weapon weapon = weapons.get(i);
			weapon.imageTexture.bind();

			float weaponPositionXInPixels = this.squareGameObjectIsOn.x
					* (int) Game.SQUARE_WIDTH;
			float weaponPositionYInPixels = this.squareGameObjectIsOn.y
					* (int) Game.SQUARE_HEIGHT + (i * weaponHeightInPixels);

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(weaponPositionXInPixels, weaponPositionYInPixels);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(weaponPositionXInPixels + weaponWidthInPixels,
					weaponPositionYInPixels);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(weaponPositionXInPixels + weaponWidthInPixels,
					weaponPositionYInPixels + weaponHeightInPixels);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(weaponPositionXInPixels, weaponPositionYInPixels
					+ weaponHeightInPixels);
			GL11.glEnd();
		}
	}
}
