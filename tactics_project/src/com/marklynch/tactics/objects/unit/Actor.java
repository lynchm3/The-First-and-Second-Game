package com.marklynch.tactics.objects.unit;

import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.ActivityLog;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.utils.TextUtils;

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
	public boolean hasAttackedThisTurn = false;

	// Fight preview on hover
	public boolean showHoverFightPreview = false;
	public GameObject hoverFightPreviewDefender = null;
	public Vector<Fight> hoverFightPreviewFights = new Vector<Fight>();

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
			if (weaponDistance >= weapon.minRange
					&& weaponDistance <= weapon.maxRange) {
				selectedWeapon = weapon;
				return true;
			}
		}
		return false;
	}

	public void attack(GameObject gameObject, boolean isCounter) {
		if (hasAttackedThisTurn == true && !isCounter) {
			return;
		}
		gameObject.remainingHealth -= selectedWeapon.damage;
		this.distanceMovedThisTurn = Integer.MAX_VALUE;
		this.hasAttackedThisTurn = true;
		String attackTypeString;
		if (isCounter)
			attackTypeString = "countered";
		else
			attackTypeString = "attacked ";
		level.logOnScreen(new ActivityLog("" + this.name + " "
				+ attackTypeString + " " + gameObject.name + " with "
				+ selectedWeapon.name + " for " + selectedWeapon.damage
				+ " damage", this.faction));

		Actor actor = null;
		if (gameObject instanceof Actor)
			actor = (Actor) gameObject;

		if (gameObject.checkIfDestroyed()) {
			if (gameObject instanceof Actor) {
				level.logOnScreen(new ActivityLog("" + this.name + " killed "
						+ gameObject.name, this.faction));
				((Actor) gameObject).faction.checkIfDestroyed();
			} else {
				level.logOnScreen(new ActivityLog("" + this.name
						+ " destroyed a " + gameObject.name, this.faction));
			}

		}

		if (!isCounter && gameObject.remainingHealth > 0
				&& gameObject instanceof Actor)
			actor.counter(this);

		this.showPow(gameObject);
	}

	public void counter(GameObject gameObject) {
		if (hasRange(this.weaponDistanceTo(gameObject.squareGameObjectIsOn))) {
			attack(gameObject, true);
		}
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

		// draw sidebar on square
		float healthPercentage = ((float) remainingHealth)
				/ ((float) totalHealth);
		float weaponAreaWidthInPixels = Game.SQUARE_WIDTH / 5;
		float weaponAreaHeightInPixels = Game.SQUARE_HEIGHT;
		float healthBarHeightInPixels = Game.SQUARE_HEIGHT * healthPercentage;
		float weaponAreaPositionXInPixels = 0;
		float weaponAreaPositionYInPixels = 0;

		if (this.faction == level.factions.get(0)) {
			weaponAreaPositionXInPixels = this.squareGameObjectIsOn.x
					* (int) Game.SQUARE_WIDTH;
			weaponAreaPositionYInPixels = this.squareGameObjectIsOn.y
					* (int) Game.SQUARE_HEIGHT;
		} else {
			weaponAreaPositionXInPixels = this.squareGameObjectIsOn.x
					* (int) Game.SQUARE_WIDTH + Game.SQUARE_WIDTH
					- weaponAreaWidthInPixels;
			weaponAreaPositionYInPixels = this.squareGameObjectIsOn.y
					* (int) Game.SQUARE_HEIGHT;

		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		// black to white bit under health bar
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		// GL11.glColor4f(this.faction.color.r, this.faction.color.g,
		// this.faction.color.b, 0.5f);
		GL11.glVertex2f(weaponAreaPositionXInPixels + 1,
				weaponAreaPositionYInPixels + 1);
		GL11.glVertex2f(weaponAreaPositionXInPixels + weaponAreaWidthInPixels
				- 1, weaponAreaPositionYInPixels + 1);
		GL11.glVertex2f(weaponAreaPositionXInPixels + weaponAreaWidthInPixels
				- 1, weaponAreaPositionYInPixels + weaponAreaHeightInPixels - 1);
		GL11.glVertex2f(weaponAreaPositionXInPixels + 1,
				weaponAreaPositionYInPixels + weaponAreaHeightInPixels - 1);
		GL11.glEnd();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		// Colored health bar
		GL11.glBegin(GL11.GL_QUADS);
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		GL11.glColor4f(this.faction.color.r, this.faction.color.g,
				this.faction.color.b, 0.5f);
		GL11.glVertex2f(weaponAreaPositionXInPixels + 1,
				weaponAreaPositionYInPixels + 1);
		GL11.glVertex2f(weaponAreaPositionXInPixels + weaponAreaWidthInPixels
				- 1, weaponAreaPositionYInPixels + 1);
		GL11.glVertex2f(weaponAreaPositionXInPixels + weaponAreaWidthInPixels
				- 1, weaponAreaPositionYInPixels + healthBarHeightInPixels - 1);
		GL11.glVertex2f(weaponAreaPositionXInPixels + 1,
				weaponAreaPositionYInPixels + healthBarHeightInPixels - 1);
		GL11.glEnd();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		super.draw();

		// draw weapon icons on square
		float weaponWidthInPixels = Game.SQUARE_WIDTH / 5;
		float weaponHeightInPixels = Game.SQUARE_HEIGHT / 5;
		for (int i = 0; i < weapons.size(); i++) {

			Weapon weapon = weapons.get(i);
			weapon.imageTexture.bind();

			float weaponPositionXInPixels = 0;
			float weaponPositionYInPixels = 0;

			if (this.faction == level.factions.get(0)) {
				weaponPositionXInPixels = this.squareGameObjectIsOn.x
						* (int) Game.SQUARE_WIDTH;
				weaponPositionYInPixels = this.squareGameObjectIsOn.y
						* (int) Game.SQUARE_HEIGHT + (i * weaponHeightInPixels);
			} else {
				weaponPositionXInPixels = this.squareGameObjectIsOn.x
						* (int) Game.SQUARE_WIDTH + Game.SQUARE_WIDTH
						- weaponWidthInPixels;
				weaponPositionYInPixels = this.squareGameObjectIsOn.y
						* (int) Game.SQUARE_HEIGHT + (i * weaponHeightInPixels);

			}

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

		// Draw actor level text
		String actorLevelString = "LVL" + this.actorLevel;
		float actorLevelWidthInPixels = level.font12.getWidth(actorLevelString);// Game.SQUARE_WIDTH
		// / 2;

		float actorLevelPositionXInPixels = (this.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
				+ Game.SQUARE_WIDTH
				- actorLevelWidthInPixels
				- Game.SQUARE_WIDTH / 5;
		float actorLevelPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		level.font12.drawString(actorLevelPositionXInPixels,
				actorLevelPositionYInPixels, actorLevelString, Color.black);

		// draw if you can move and/or attack
		float moveAttackStatusWidthInPixels = level.font12.getWidth("MA");// Game.SQUARE_WIDTH
		float attackStatusWidthInPixels = level.font12.getWidth("A");// Game.SQUARE_WIDTH

		float moveAttackStatusPositionXInPixels = (this.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
				+ Game.SQUARE_WIDTH
				- moveAttackStatusWidthInPixels
				- Game.SQUARE_WIDTH / 5;
		float attackStatusPositionXInPixels = (this.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
				+ Game.SQUARE_WIDTH
				- attackStatusWidthInPixels
				- Game.SQUARE_WIDTH / 5;
		float moveAttackStatusPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT + Game.SQUARE_HEIGHT - 14;

		if (hasAttackedThisTurn == false) {
			if (this.distanceMovedThisTurn < this.travelDistance) {
				level.font12.drawString(moveAttackStatusPositionXInPixels,
						moveAttackStatusPositionYInPixels, "MA", Color.black);
			} else {
				level.font12.drawString(attackStatusPositionXInPixels,
						moveAttackStatusPositionYInPixels, "A", Color.black);
			}
		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		if (this.showHoverFightPreview) {

			float hoverFightPreviewPositionXInPixels = (hoverFightPreviewDefender.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
					+ Game.SQUARE_WIDTH;
			float hoverFightPreviewPositionYInPixels = hoverFightPreviewDefender.squareGameObjectIsOn.y
					* (int) Game.SQUARE_HEIGHT + Game.SQUARE_HEIGHT;

			Object[][] tableContents = new Object[hoverFightPreviewFights
					.size() + 1][5];
			tableContents[0][0] = "Rng";
			tableContents[0][1] = "Wpn";
			tableContents[0][2] = "Dmg";
			tableContents[0][3] = "Wpn";
			tableContents[0][4] = "Dmg";

			for (int i = 0; i < hoverFightPreviewFights.size(); i++) {

				if (hoverFightPreviewFights.get(i).attackerWeapon != null
						&& hoverFightPreviewFights.get(i).defenderWeapon != null) {

					tableContents[i + 1][0] = ""
							+ hoverFightPreviewFights.get(i).range;
					tableContents[i + 1][1] = hoverFightPreviewFights.get(i).attackerWeapon.imageTexture;
					tableContents[i + 1][2] = ""
							+ hoverFightPreviewFights.get(i).attackerWeapon.damage;
					tableContents[i + 1][3] = hoverFightPreviewFights.get(i).defenderWeapon.imageTexture;
					tableContents[i + 1][4] = ""
							+ hoverFightPreviewFights.get(i).defenderWeapon.damage;
				} else if (hoverFightPreviewFights.get(i).attackerWeapon == null
						&& hoverFightPreviewFights.get(i).defenderWeapon != null) {

					tableContents[i + 1][0] = ""
							+ hoverFightPreviewFights.get(i).range;
					tableContents[i + 1][1] = "";
					tableContents[i + 1][2] = "";
					tableContents[i + 1][3] = hoverFightPreviewFights.get(i).defenderWeapon.imageTexture;
					tableContents[i + 1][4] = ""
							+ hoverFightPreviewFights.get(i).defenderWeapon.damage;
				} else if (hoverFightPreviewFights.get(i).attackerWeapon != null
						&& hoverFightPreviewFights.get(i).defenderWeapon == null) {

					tableContents[i + 1][0] = ""
							+ hoverFightPreviewFights.get(i).range;
					tableContents[i + 1][1] = hoverFightPreviewFights.get(i).attackerWeapon.imageTexture;
					tableContents[i + 1][2] = ""
							+ hoverFightPreviewFights.get(i).attackerWeapon.damage;
					tableContents[i + 1][3] = "";
					tableContents[i + 1][4] = "";
				} else if (hoverFightPreviewFights.get(i).attackerWeapon == null
						&& hoverFightPreviewFights.get(i).defenderWeapon == null) {

					tableContents[i + 1][0] = ""
							+ hoverFightPreviewFights.get(i).range;
					tableContents[i + 1][1] = "";
					tableContents[i + 1][2] = "";
					tableContents[i + 1][3] = "";
					tableContents[i + 1][4] = "";
				}

			}

			TextUtils.printTable(tableContents,
					hoverFightPreviewPositionXInPixels,
					hoverFightPreviewPositionYInPixels, 15f, level);

		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}

	public Vector<Integer> calculateIdealDistanceFrom(GameObject target) {

		Vector<Fight> fights = new Vector<Fight>();
		for (Weapon weapon : this.weapons) {
			for (int range = weapon.minRange; range <= weapon.maxRange; range++) {
				Fight fight = new Fight(this, weapon, target,
						target.bestCounterWeapon(this, weapon, range), range);
				fight.projectOutcome();
				fights.add(fight);
			}
		}

		fights.sort(new Fight.FightComparator());

		Vector<Integer> idealDistances = new Vector<Integer>();

		for (Fight fight : fights) {
			idealDistances.add(fight.range);
		}

		return idealDistances;
	}

	public void showHoverFightPreview(GameObject defender) {
		this.showHoverFightPreview = true;
		hoverFightPreviewDefender = defender;
		hoverFightPreviewFights.clear();
		for (Weapon weapon : weapons) {
			// if (defender.squareGameObjectIsOn.weaponsThatCanAttack
			// .contains(weapon)) {

			for (int range = weapon.minRange; range <= weapon.maxRange; range++) {
				Fight fight = new Fight(this, weapon, defender,
						defender.bestCounterWeapon(this, weapon, range), range);
				hoverFightPreviewFights.add(fight);
			}
			// }

		}

	}

	public void hideHoverFightPreview() {
		this.showHoverFightPreview = false;
	}
}
