package com.marklynch.tactics.objects.unit;

import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;

public class Actor extends GameObject {

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	public String title = "";
	public int actorLevel = 1;
	public int distanceMovedThisTurn = 0;
	public int travelDistance = 4;
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
		level.logOnScreen(new ActivityLog(new Object[] {

		this, " " + attackTypeString + " ", gameObject, " with ",
				selectedWeapon.imageTexture,
				" for " + selectedWeapon.damage + " damage" }));

		Actor actor = null;
		if (gameObject instanceof Actor)
			actor = (Actor) gameObject;

		if (gameObject.checkIfDestroyed()) {
			if (gameObject instanceof Actor) {
				level.logOnScreen(new ActivityLog(new Object[] { this,
						" killed ", gameObject }));
				((Actor) gameObject).faction.checkIfDestroyed();
			} else {
				level.logOnScreen(new ActivityLog(new Object[] { this,
						" destroyed a ", gameObject }));
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
		level.logOnScreen(new ActivityLog(new Object[] { this,
				" moved to " + squareToMoveTo }));

	}

	@Override
	public void draw() {

		if (level.activeActor != null
				&& level.activeActor.showHoverFightPreview
				&& level.activeActor.hoverFightPreviewDefender == this) {

		} else {
			// HEALTH COLORZ HERE YO

			// draw sidebar on square
			float healthPercentage = (remainingHealth) / (totalHealth);
			float weaponAreaWidthInPixels = Game.SQUARE_WIDTH / 5;
			float weaponAreaHeightInPixels = Game.SQUARE_HEIGHT;
			float healthBarHeightInPixels = Game.SQUARE_HEIGHT
					* healthPercentage;
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

			// White bit under health bar
			QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f),
					weaponAreaPositionXInPixels + 1,
					weaponAreaPositionXInPixels + weaponAreaWidthInPixels - 1,
					weaponAreaPositionYInPixels + 1,
					weaponAreaPositionYInPixels + weaponAreaHeightInPixels - 1);

			// Colored health bar
			QuadUtils.drawQuad(new Color(this.faction.color.r,
					this.faction.color.g, this.faction.color.b),
					weaponAreaPositionXInPixels + 1,
					weaponAreaPositionXInPixels + weaponAreaWidthInPixels - 1,
					weaponAreaPositionYInPixels + 1,
					weaponAreaPositionYInPixels + healthBarHeightInPixels - 1);
		}
		super.draw();
		if (level.activeActor != null
				&& level.activeActor.showHoverFightPreview
				&& level.activeActor.hoverFightPreviewDefender == this) {

		} else {
			// draw weapon icons on square
			float weaponWidthInPixels = Game.SQUARE_WIDTH / 5;
			float weaponHeightInPixels = Game.SQUARE_HEIGHT / 5;
			for (int i = 0; i < weapons.size(); i++) {

				Weapon weapon = weapons.get(i);

				float weaponPositionXInPixels = 0;
				float weaponPositionYInPixels = 0;

				if (this.faction == level.factions.get(0)) {
					weaponPositionXInPixels = this.squareGameObjectIsOn.x
							* (int) Game.SQUARE_WIDTH;
					weaponPositionYInPixels = this.squareGameObjectIsOn.y
							* (int) Game.SQUARE_HEIGHT
							+ (i * weaponHeightInPixels);
				} else {
					weaponPositionXInPixels = this.squareGameObjectIsOn.x
							* (int) Game.SQUARE_WIDTH + Game.SQUARE_WIDTH
							- weaponWidthInPixels;
					weaponPositionYInPixels = this.squareGameObjectIsOn.y
							* (int) Game.SQUARE_HEIGHT
							+ (i * weaponHeightInPixels);

				}
				TextureUtils.drawTexture(weapon.imageTexture,
						weaponPositionXInPixels, weaponPositionXInPixels
								+ weaponWidthInPixels, weaponPositionYInPixels,
						weaponPositionYInPixels + weaponHeightInPixels);
			}
		}

		// Draw actor level text
		String actorLevelString = "LVL" + this.actorLevel;
		float actorLevelWidthInPixels = level.font12.getWidth(actorLevelString);
		float actorLevelPositionXInPixels = (this.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
				+ Game.SQUARE_WIDTH
				- actorLevelWidthInPixels
				- Game.SQUARE_WIDTH / 5;
		float actorLevelPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		level.font12.drawString(actorLevelPositionXInPixels,
				actorLevelPositionYInPixels, actorLevelString, Color.black);

		// draw indicators of whether you can move and/or attack
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
	}

	@Override
	public void draw2() {
		super.draw2();

		// Hover fight preview
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		if (this.showHoverFightPreview) {

			float hoverFightPreviewPositionXInPixels = (hoverFightPreviewDefender.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH);
			float hoverFightPreviewPositionYInPixels = hoverFightPreviewDefender.squareGameObjectIsOn.y
					* (int) Game.SQUARE_HEIGHT;

			Object[][] tableContents = new Object[hoverFightPreviewFights
					.size()][4];
			// tableContents[0][0] = "Rng";
			// tableContents[0][1] = "Wpn";
			// tableContents[0][2] = "Dmg";
			// tableContents[0][3] = "Wpn";
			// tableContents[0][4] = "Dmg";

			for (int i = 0; i < hoverFightPreviewFights.size(); i++) {

				if (hoverFightPreviewFights.get(i).attackerWeapon != null
						&& hoverFightPreviewFights.get(i).defenderWeapon != null) {

					// tableContents[i][0] = ""
					// + hoverFightPreviewFights.get(i).range;
					tableContents[i][0] = hoverFightPreviewFights.get(i).attackerWeapon.imageTexture;
					tableContents[i][1] = ""
							+ hoverFightPreviewFights.get(i).attackerWeapon.damage;
					tableContents[i][2] = hoverFightPreviewFights.get(i).defenderWeapon.imageTexture;
					tableContents[i][3] = ""
							+ hoverFightPreviewFights.get(i).defenderWeapon.damage;
				} else if (hoverFightPreviewFights.get(i).attackerWeapon == null
						&& hoverFightPreviewFights.get(i).defenderWeapon != null) {

					// tableContents[i][0] = ""
					// + hoverFightPreviewFights.get(i).range;
					tableContents[i][0] = "";
					tableContents[i][1] = "";
					tableContents[i][2] = hoverFightPreviewFights.get(i).defenderWeapon.imageTexture;
					tableContents[i][3] = ""
							+ hoverFightPreviewFights.get(i).defenderWeapon.damage;
				} else if (hoverFightPreviewFights.get(i).attackerWeapon != null
						&& hoverFightPreviewFights.get(i).defenderWeapon == null) {

					// tableContents[i][0] = ""
					// + hoverFightPreviewFights.get(i).range;
					tableContents[i][0] = hoverFightPreviewFights.get(i).attackerWeapon.imageTexture;
					tableContents[i][1] = ""
							+ hoverFightPreviewFights.get(i).attackerWeapon.damage;
					tableContents[i][2] = "";
					tableContents[i][3] = "";
				} else if (hoverFightPreviewFights.get(i).attackerWeapon == null
						&& hoverFightPreviewFights.get(i).defenderWeapon == null) {

					// tableContents[i][0] = ""
					// + hoverFightPreviewFights.get(i).range;
					tableContents[i][0] = "";
					tableContents[i][1] = "";
					tableContents[i][2] = "";
					tableContents[i][3] = "";
				}

			}

			// BG white
			QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 1.0f),
					hoverFightPreviewPositionXInPixels,
					hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// Target image
			TextureUtils.drawTexture(
					hoverFightPreviewFights.get(0).defender.imageTexture,
					hoverFightPreviewPositionXInPixels,
					hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// VS image
			TextureUtils.drawTexture(this.vsTexture,
					hoverFightPreviewPositionXInPixels,
					hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 1.0f),
			// hoverFightPreviewPositionXInPixels,
			// hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH,
			// hoverFightPreviewPositionYInPixels,
			// hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// Attacker Widths of bars
			float attackerTotalHealthWidth = Game.SQUARE_WIDTH / 2;

			float attackerCurrentHealthWidth = (Game.SQUARE_WIDTH / 2)
					* (this.remainingHealth / totalHealth);

			float attackerPotentialHealthLossWidth = (Game.SQUARE_WIDTH / 2)
					* (hoverFightPreviewFights.get(0).damageTakenByAttacker / totalHealth);

			float attackerPotentialRemainingHealthWidth = attackerCurrentHealthWidth
					- attackerPotentialHealthLossWidth;

			// Attacker Positions of bars
			float attackerTotalHealthX = this.hoverFightPreviewDefender.squareGameObjectIsOn.x
					* Game.SQUARE_WIDTH;

			float attackerCurrentHealthX = attackerTotalHealthX;

			float attackerPotentialRemainingHealthX = attackerCurrentHealthX;

			float attackerPotentialHealthLossX = attackerPotentialRemainingHealthX
					+ attackerPotentialRemainingHealthWidth;

			// Attacker remaining potential health
			QuadUtils.drawQuad(this.faction.color,
					attackerPotentialRemainingHealthX,
					attackerPotentialRemainingHealthX
							+ attackerPotentialRemainingHealthWidth,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			float alpha = Game.getTime() % 2000f;
			if (alpha <= 1000) {
				alpha = alpha / 1000;
			} else {
				alpha = (2000 - alpha) / 1000;
			}

			// Attacker potential health loss
			QuadUtils.drawQuad(new Color(this.faction.color.r,
					this.faction.color.g, this.faction.color.b, alpha),
					attackerPotentialHealthLossX, attackerPotentialHealthLossX
							+ attackerPotentialHealthLossWidth,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// Widths of bars
			float defenderTotalHealthWidth = Game.SQUARE_WIDTH / 2;

			float defenderCurrentHealthWidth = (Game.SQUARE_WIDTH / 2)
					* (this.hoverFightPreviewDefender.remainingHealth / totalHealth);

			float defenderPotentialHealthLossWidth = (Game.SQUARE_WIDTH / 2)
					* (hoverFightPreviewFights.get(0).damageTakenByDefender / totalHealth);

			float defenderPotentialRemainingHealthWidth = defenderCurrentHealthWidth
					- defenderPotentialHealthLossWidth;

			// Positions of bars
			float defenderTotalHealthX = this.hoverFightPreviewDefender.squareGameObjectIsOn.x
					* Game.SQUARE_WIDTH + Game.SQUARE_WIDTH / 2f;

			float defenderCurrentHealthX = defenderTotalHealthX;

			float defenderPotentialRemainingHealthX = defenderCurrentHealthX;

			float defenderPotentialHealthLossX = defenderPotentialRemainingHealthX
					+ defenderPotentialRemainingHealthWidth;

			Color color = null;
			if (this.hoverFightPreviewDefender.faction != null) {
				color = this.hoverFightPreviewDefender.faction.color;
			} else {
				color = new Color(0.5f, 0.5f, 0.5f);
			}

			// Attacker remaining potential health
			QuadUtils.drawQuad(color, defenderPotentialRemainingHealthX,
					defenderPotentialRemainingHealthX
							+ defenderPotentialRemainingHealthWidth,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// Attacker potential health loss
			QuadUtils.drawQuad(new Color(color.r, color.g, color.b, alpha),
					defenderPotentialHealthLossX, defenderPotentialHealthLossX
							+ defenderPotentialHealthLossWidth,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// line down the middle
			float linePositionX = hoverFightPreviewPositionXInPixels
					+ Game.SQUARE_WIDTH / 2f - 0.5f;
			QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 1.0f),
					linePositionX, linePositionX + 1,
					hoverFightPreviewPositionYInPixels,
					hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// TextUtils.printTable(tableContents,
			// hoverFightPreviewPositionXInPixels,
			// hoverFightPreviewPositionYInPixels, 20f, level);

		}

	}

	public Vector<Float> calculateIdealDistanceFrom(GameObject target) {

		Vector<Fight> fights = new Vector<Fight>();
		for (Weapon weapon : this.weapons) {
			for (float range = weapon.minRange; range <= weapon.maxRange; range++) {
				Fight fight = new Fight(this, weapon, target,
						target.bestCounterWeapon(this, weapon, range), range);
				fights.add(fight);
			}
		}

		fights.sort(new Fight.FightComparator());

		Vector<Float> idealDistances = new Vector<Float>();

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

			for (float range = weapon.minRange; range <= weapon.maxRange; range++) {
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
