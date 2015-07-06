package com.marklynch.tactics.objects.unit;

import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.AttackButton;
import com.marklynch.ui.button.Button;
import com.marklynch.utils.FormattingUtils;
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
	public boolean showWeaponSelection = false;

	// buttons
	public ArrayList<Button> buttons = new ArrayList<Button>();
	public AttackButton attackButton = null;
	public AttackButton pushButton = null;
	public float buttonsAnimateCurrentTime = 0f;
	public float buttonsAnimateMaxTime = 200f;

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

		this.attackButton = new AttackButton(0, 0, 50, 50, "attack.png",
				"attack.png", level);

		this.pushButton = new AttackButton(0, 0, 50, 50, "attack.png",
				"attack.png", level);

		buttons.add(attackButton);
		buttons.add(pushButton);
		attackButton.enabled = true;
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

		if (this.faction == level.factions.get(0)) {
			level.undoList.clear();
			level.undoButton.enabled = false;
		}
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

		if (squareToMoveTo == this.squareGameObjectIsOn)
			return;

		Square oldSquare = this.squareGameObjectIsOn;
		int distanceTraveled = squareToMoveTo.distanceToSquare;
		this.squareGameObjectIsOn.gameObject = null;
		this.distanceMovedThisTurn += squareToMoveTo.distanceToSquare;
		this.squareGameObjectIsOn = squareToMoveTo;
		squareToMoveTo.gameObject = level.activeActor;
		Actor.highlightSelectedCharactersSquares(level);
		level.logOnScreen(new ActivityLog(new Object[] { this,
				" moved to " + squareToMoveTo }));

		if (this.faction == level.factions.get(0)) {
			level.undoList.push(new Move(this, oldSquare, squareToMoveTo,
					distanceTraveled));
			level.undoButton.enabled = true;
		}
	}

	@Override
	public void draw() {

		if (level.activeActor != null
				&& level.activeActor.showHoverFightPreview
				&& level.activeActor.hoverFightPreviewDefender == this) {

		} else {
			// HEALTH COLORZ HERE YO

			if (level.activeActor != null
					&& level.activeActor.showHoverFightPreview) {

			} else {

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
						weaponAreaPositionXInPixels + weaponAreaWidthInPixels
								- 1, weaponAreaPositionYInPixels + 1,
						weaponAreaPositionYInPixels + weaponAreaHeightInPixels
								- 1);

				// Colored health bar
				QuadUtils.drawQuad(new Color(this.faction.color.r,
						this.faction.color.g, this.faction.color.b),
						weaponAreaPositionXInPixels + 1,
						weaponAreaPositionXInPixels + weaponAreaWidthInPixels
								- 1, weaponAreaPositionYInPixels + 1,
						weaponAreaPositionYInPixels + healthBarHeightInPixels
								- 1);
			}

			super.draw();

			if (level.activeActor != null
					&& level.activeActor.showHoverFightPreview) {

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
									+ weaponWidthInPixels,
							weaponPositionYInPixels, weaponPositionYInPixels
									+ weaponHeightInPixels);
				}
			}

			// Draw actor level text
			String actorLevelString = "L" + this.actorLevel;
			float actorLevelWidthInPixels = level.font12
					.getWidth(actorLevelString);
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
							moveAttackStatusPositionYInPixels, "MA",
							Color.black);
				} else {
					level.font12
							.drawString(attackStatusPositionXInPixels,
									moveAttackStatusPositionYInPixels, "A",
									Color.black);
				}
			}
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
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

			// // BG white
			// QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f),
			// hoverFightPreviewPositionXInPixels,
			// hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH,
			// hoverFightPreviewPositionYInPixels,
			// hoverFightPreviewPositionYInPixels + Game.SQUARE_HEIGHT);

			// Target image
			TextureUtils.drawTexture(
					this.hoverFightPreviewDefender.imageTexture,
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

			float[] previewPositionYs = new float[hoverFightPreviewFights
					.size()];
			float previewHeight = 28f;
			if (hoverFightPreviewFights.size() == 1) {
				previewPositionYs[0] = 50f + hoverFightPreviewPositionYInPixels;
			} else if (hoverFightPreviewFights.size() == 2) {
				previewPositionYs[0] = 24f + hoverFightPreviewPositionYInPixels;
				previewPositionYs[1] = 76f + hoverFightPreviewPositionYInPixels;
			} else {
				float posY = 11f + hoverFightPreviewPositionYInPixels;
				for (int i = 0; i < previewPositionYs.length; i++) {
					previewPositionYs[i] = posY;
					posY += 11f + 28f;
				}
			}

			for (int i = 0; i < hoverFightPreviewFights.size(); i++) {

				// // BG white under attacker health bar
				// QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f),
				// hoverFightPreviewPositionXInPixels,
				// hoverFightPreviewPositionXInPixels - 10,
				// previewPositionYs[i], previewPositionYs[i] + 28);
				//
				// // BG white under defender health bar
				// QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f),
				// hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH,
				// hoverFightPreviewPositionXInPixels + Game.SQUARE_WIDTH
				// + 10, previewPositionYs[i],
				// previewPositionYs[i] + 28);

				// Attacker Widths of bars

				float attackerTotalHealthWidth = ((Game.SQUARE_WIDTH + 20) / 2);

				float attackerCurrentHealthWidth = ((Game.SQUARE_WIDTH + 20) / 2)
						* (this.remainingHealth / totalHealth);

				float attackerPotentialHealthLossWidth = ((Game.SQUARE_WIDTH + 20) / 2)
						* (hoverFightPreviewFights.get(i).damageTakenByAttacker / totalHealth);

				float attackerPotentialRemainingHealthWidth = attackerCurrentHealthWidth
						- attackerPotentialHealthLossWidth;

				// Attacker Positions of bars
				float attackerTotalHealthX = this.hoverFightPreviewDefender.squareGameObjectIsOn.x
						* (Game.SQUARE_WIDTH) - 10;

				float attackerCurrentHealthX = attackerTotalHealthX;

				float attackerPotentialRemainingHealthX = attackerCurrentHealthX;

				float attackerPotentialHealthLossX = attackerPotentialRemainingHealthX
						+ attackerPotentialRemainingHealthWidth;

				// Attacker draw total health
				QuadUtils.drawQuad(new Color(this.faction.color.r,
						this.faction.color.g, this.faction.color.b, 0.25f),
						attackerTotalHealthX, attackerTotalHealthX
								+ attackerTotalHealthWidth,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// Attacker draw potential remaining health
				QuadUtils.drawQuad(this.faction.color,
						attackerPotentialRemainingHealthX,
						attackerPotentialRemainingHealthX
								+ attackerPotentialRemainingHealthWidth,
						previewPositionYs[i], previewPositionYs[i] + 28);

				float attackerHealthLossAlpha = 0f;

				if (hoverFightPreviewFights.get(i).damageTakenByAttacker >= this.remainingHealth) {
					attackerHealthLossAlpha = Game.getTime() % 1000f;
					if (attackerHealthLossAlpha <= 500) {
						attackerHealthLossAlpha = attackerHealthLossAlpha / 500;
					} else {
						attackerHealthLossAlpha = (1000 - attackerHealthLossAlpha) / 500;
					}

				} else {
					attackerHealthLossAlpha = Game.getTime() % 2000f;
					if (attackerHealthLossAlpha <= 1000) {
						attackerHealthLossAlpha = attackerHealthLossAlpha / 1000;
					} else {
						attackerHealthLossAlpha = (2000 - attackerHealthLossAlpha) / 1000;
					}
				}

				// Attacker draw potential health loss
				QuadUtils.drawQuad(new Color(this.faction.color.r,
						this.faction.color.g, this.faction.color.b,
						attackerHealthLossAlpha), attackerPotentialHealthLossX,
						attackerPotentialHealthLossX
								+ attackerPotentialHealthLossWidth,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// Attacker line 1
				QuadUtils.drawQuad(Color.white,
						attackerPotentialRemainingHealthX,
						attackerPotentialRemainingHealthX + 1,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// Attacker line 2
				QuadUtils.drawQuad(Color.white,
						attackerPotentialRemainingHealthX
								+ attackerPotentialRemainingHealthWidth,
						attackerPotentialRemainingHealthX
								+ attackerPotentialRemainingHealthWidth + 1,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// Attacker line 3
				QuadUtils.drawQuad(Color.white, attackerPotentialHealthLossX
						+ attackerPotentialHealthLossWidth,
						attackerPotentialHealthLossX
								+ attackerPotentialHealthLossWidth + 1,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// attacker skull symbol
				// if (hoverFightPreviewFights.get(i).damageTakenByAttacker >=
				// this.remainingHealth) {
				// TextureUtils
				// .drawTexture(
				// skullTexture,
				// this.hoverFightPreviewDefender.squareGameObjectIsOn.x
				// * (Game.SQUARE_WIDTH) - 32,
				// this.hoverFightPreviewDefender.squareGameObjectIsOn.x
				// * (Game.SQUARE_WIDTH),
				// previewPositionYs[i] - 2,
				// previewPositionYs[i] + 30);
				// }

				// attacker weapon
				TextureUtils
						.drawTexture(
								this.hoverFightPreviewFights.get(i).attackerWeapon.imageTexture,
								this.hoverFightPreviewDefender.squareGameObjectIsOn.x
										* (Game.SQUARE_WIDTH) - 32,
								this.hoverFightPreviewDefender.squareGameObjectIsOn.x
										* (Game.SQUARE_WIDTH),
								previewPositionYs[i] - 2,
								previewPositionYs[i] + 30);

				// attacker hit chance
				level.font12
						.drawString(
								this.hoverFightPreviewDefender.squareGameObjectIsOn.x
										* (Game.SQUARE_WIDTH),
								previewPositionYs[i],
								this.hoverFightPreviewFights.get(i).chanceOfHittingDefender
										+ "%", Color.black);

				// attacker damage
				String attackerDamageString = FormattingUtils
						.formatFloatRemoveUnneccessaryDigits(this.hoverFightPreviewFights
								.get(i).damageTakenByDefender)
						+ "×"
						+ FormattingUtils
								.formatFloatRemoveUnneccessaryDigits(this.hoverFightPreviewFights
										.get(i).damageTakenByDefenderMultiplier);

				level.font12.drawString(
						this.hoverFightPreviewDefender.squareGameObjectIsOn.x
								* (Game.SQUARE_WIDTH),
						previewPositionYs[i] + 14, attackerDamageString,
						Color.black);

				// attacker weapon
				TextureUtils
						.drawTexture(
								this.hoverFightPreviewFights.get(i).attackerWeapon.imageTexture,
								this.hoverFightPreviewDefender.squareGameObjectIsOn.x
										* (Game.SQUARE_WIDTH) - 32,
								this.hoverFightPreviewDefender.squareGameObjectIsOn.x
										* (Game.SQUARE_WIDTH),
								previewPositionYs[i] - 2,
								previewPositionYs[i] + 30);

				// attacker advantage/disadvantage
				if (this.hoverFightPreviewFights.get(i).advantage == Fight.Advantage.ATTACKER_ADVANTAGE) {
					TextureUtils
							.drawTexture(
									upTexture,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH) - 20,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH) - 4,
									previewPositionYs[i] + 14,
									previewPositionYs[i] + 30);
				} else if (this.hoverFightPreviewFights.get(i).advantage == Fight.Advantage.DEFENDER_ADVANTAGE) {
					TextureUtils
							.drawTexture(
									downTexture,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH) - 20,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH) - 4,
									previewPositionYs[i] + 14,
									previewPositionYs[i] + 30);
				}

				// Attacker skull symbol
				if (hoverFightPreviewFights.get(i).damageTakenByAttacker >= hoverFightPreviewFights
						.get(i).attacker.remainingHealth) {
					TextureUtils
							.drawTexture(
									skullTexture,
									attackerHealthLossAlpha,
									hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH) - 48,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH) - 16,
									previewPositionYs[i] - 2,
									previewPositionYs[i] + 30);
				}

				// Defender Widths of bars
				float defenderTotalHealthWidth = ((Game.SQUARE_WIDTH + 20) / 2);

				float defenderCurrentHealthWidth = ((Game.SQUARE_WIDTH + 20) / 2)
						* (this.hoverFightPreviewDefender.remainingHealth / this.hoverFightPreviewDefender.totalHealth);

				float defenderPotentialHealthLossWidth = ((Game.SQUARE_WIDTH + 20) / 2)
						* (hoverFightPreviewFights.get(i).damageTakenByDefender / this.hoverFightPreviewDefender.totalHealth);

				float defenderPotentialRemainingHealthWidth = defenderCurrentHealthWidth
						- defenderPotentialHealthLossWidth;

				float defenderCurrentMissingHealthWidth = ((Game.SQUARE_WIDTH + 20) / 2)
						* ((this.hoverFightPreviewDefender.totalHealth - this.hoverFightPreviewDefender.remainingHealth) / this.hoverFightPreviewDefender.totalHealth);

				// Defender Positions of bars
				float defenderTotalHealthX = this.hoverFightPreviewDefender.squareGameObjectIsOn.x
						* Game.SQUARE_WIDTH + Game.SQUARE_WIDTH / 2f;

				float defenderCurrentHealthX = defenderTotalHealthX
						+ defenderCurrentMissingHealthWidth;

				float defenderPotentialRemainingHealthX = defenderCurrentHealthX
						+ defenderPotentialHealthLossWidth;

				float defenderPotentialHealthLossX = defenderTotalHealthX
						+ defenderCurrentMissingHealthWidth;

				Color color = null;
				if (this.hoverFightPreviewDefender.faction != null) {
					color = this.hoverFightPreviewDefender.faction.color;
				} else {
					color = new Color(0.25f, 0.25f, 0.25f);
				}

				// defender draw total health
				QuadUtils.drawQuad(new Color(color.r, color.g, color.b, 0.25f),
						defenderTotalHealthX, defenderTotalHealthX
								+ defenderTotalHealthWidth,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// defender remaining potential health
				QuadUtils.drawQuad(color, defenderPotentialRemainingHealthX,
						defenderPotentialRemainingHealthX
								+ defenderPotentialRemainingHealthWidth,
						previewPositionYs[i], previewPositionYs[i] + 28);

				float defenderHealthLossAlpha = 0f;

				if (hoverFightPreviewFights.get(i).damageTakenByDefender >= hoverFightPreviewFights
						.get(0).defender.remainingHealth) {
					defenderHealthLossAlpha = Game.getTime() % 1000f;
					if (defenderHealthLossAlpha <= 500) {
						defenderHealthLossAlpha = defenderHealthLossAlpha / 500;
					} else {
						defenderHealthLossAlpha = (1000 - defenderHealthLossAlpha) / 500;
					}

				} else {
					defenderHealthLossAlpha = Game.getTime() % 2000f;
					if (defenderHealthLossAlpha <= 1000) {
						defenderHealthLossAlpha = defenderHealthLossAlpha / 1000;
					} else {
						defenderHealthLossAlpha = (2000 - defenderHealthLossAlpha) / 1000;
					}
				}

				// defender potential health loss
				QuadUtils.drawQuad(new Color(color.r, color.g, color.b,
						defenderHealthLossAlpha), defenderPotentialHealthLossX,
						defenderPotentialHealthLossX
								+ defenderPotentialHealthLossWidth,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// defender line 1
				QuadUtils.drawQuad(Color.white, defenderPotentialHealthLossX,
						defenderPotentialHealthLossX + 1, previewPositionYs[i],
						previewPositionYs[i] + 28);

				// defender line 2
				QuadUtils.drawQuad(Color.white,
						defenderPotentialRemainingHealthX
								+ defenderPotentialRemainingHealthWidth,
						defenderPotentialRemainingHealthX
								+ defenderPotentialRemainingHealthWidth + 1,
						previewPositionYs[i], previewPositionYs[i] + 28);

				// defender line 3
				QuadUtils.drawQuad(Color.white, defenderPotentialHealthLossX
						+ defenderPotentialHealthLossWidth,
						defenderPotentialHealthLossX
								+ defenderPotentialHealthLossWidth + 1,
						previewPositionYs[i], previewPositionYs[i] + 28);

				if (this.hoverFightPreviewFights.get(i).defenderWeapon == null) {

				} else {

					// defender hit chance
					level.font12
							.drawString(
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH)
											+ (Game.SQUARE_WIDTH)
											- level.font12.getWidth(this.hoverFightPreviewFights
													.get(i).chanceOfHittingAttacker
													+ "%"),
									previewPositionYs[i],
									this.hoverFightPreviewFights.get(i).chanceOfHittingAttacker
											+ "%", Color.black);

					// defender damage
					String defenderDamageString = FormattingUtils
							.formatFloatRemoveUnneccessaryDigits(this.hoverFightPreviewFights
									.get(i).damageTakenByAttacker)
							+ "×"
							+ FormattingUtils
									.formatFloatRemoveUnneccessaryDigits(this.hoverFightPreviewFights
											.get(i).damageTakenByAttackerMultiplier);

					level.font12
							.drawString(
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH)
											+ (Game.SQUARE_WIDTH)
											- level.font12
													.getWidth(defenderDamageString),
									previewPositionYs[i] + 14,
									defenderDamageString, Color.black);

					// defender weapon
					if (this.hoverFightPreviewFights.get(i).defenderWeapon != null) {
						TextureUtils
								.drawTexture(
										this.hoverFightPreviewFights.get(i).defenderWeapon.imageTexture,
										this.hoverFightPreviewDefender.squareGameObjectIsOn.x
												* (Game.SQUARE_WIDTH)
												+ (Game.SQUARE_WIDTH),
										this.hoverFightPreviewDefender.squareGameObjectIsOn.x
												* (Game.SQUARE_WIDTH)
												+ (Game.SQUARE_WIDTH) + 32,
										previewPositionYs[i] - 2,
										previewPositionYs[i] + 30);
					}

					// attacker advantage/disadvantage
					if (this.hoverFightPreviewFights.get(i).advantage == Fight.Advantage.DEFENDER_ADVANTAGE) {
						TextureUtils
								.drawTexture(
										upTexture,
										this.hoverFightPreviewDefender.squareGameObjectIsOn.x
												* (Game.SQUARE_WIDTH)
												+ (Game.SQUARE_WIDTH) + 12,
										this.hoverFightPreviewDefender.squareGameObjectIsOn.x
												* (Game.SQUARE_WIDTH)
												+ (Game.SQUARE_WIDTH) + 28,
										previewPositionYs[i] + 14,
										previewPositionYs[i] + 30);
					} else if (this.hoverFightPreviewFights.get(i).advantage == Fight.Advantage.ATTACKER_ADVANTAGE) {
						TextureUtils
								.drawTexture(
										downTexture,
										this.hoverFightPreviewDefender.squareGameObjectIsOn.x
												* (Game.SQUARE_WIDTH)
												+ (Game.SQUARE_WIDTH) + 12,
										this.hoverFightPreviewDefender.squareGameObjectIsOn.x
												* (Game.SQUARE_WIDTH)
												+ (Game.SQUARE_WIDTH) + 28,
										previewPositionYs[i] + 14,
										previewPositionYs[i] + 30);
					}
				}

				// Defender skull symbol
				if (hoverFightPreviewFights.get(i).damageTakenByDefender >= hoverFightPreviewFights
						.get(i).defender.remainingHealth) {
					TextureUtils
							.drawTexture(
									skullTexture,
									defenderHealthLossAlpha,
									hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH)
											+ (Game.SQUARE_WIDTH) + 16,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH)
											+ (Game.SQUARE_WIDTH) + 48,
									previewPositionYs[i] - 2,
									previewPositionYs[i] + 30);
				}

				// line down the middle
				float linePositionX = hoverFightPreviewPositionXInPixels
						+ Game.SQUARE_WIDTH / 2f - 0.5f;
				QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 1.0f),
						linePositionX, linePositionX + 1, previewPositionYs[i],
						previewPositionYs[i] + 28);

				// X symbol because it's out of range
				if (hoverFightPreviewFights.get(i).reachable == false) {
					TextureUtils
							.drawTexture(
									this.xTexture,
									hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH)
											+ Game.SQUARE_WIDTH / 2 - 16,
									this.hoverFightPreviewDefender.squareGameObjectIsOn.x
											* (Game.SQUARE_WIDTH)
											+ Game.SQUARE_WIDTH / 2 + 16,
									previewPositionYs[i] - 2,
									previewPositionYs[i] + 30);
				}

			}

			// fight symbol
			TextureUtils
					.drawTexture(
							fightTexture,
							this.hoverFightPreviewDefender.squareGameObjectIsOn.x
									* (Game.SQUARE_WIDTH)
									+ Game.SQUARE_WIDTH
									/ 2f - 16,
							this.hoverFightPreviewDefender.squareGameObjectIsOn.x
									* (Game.SQUARE_WIDTH)
									+ Game.SQUARE_WIDTH
									/ 2f + 16,
							this.hoverFightPreviewDefender.squareGameObjectIsOn.y
									* (Game.SQUARE_HEIGHT) - 40,
							this.hoverFightPreviewDefender.squareGameObjectIsOn.y
									* (Game.SQUARE_HEIGHT) - 8);

			// TextUtils.printTable(tableContents,
			// hoverFightPreviewPositionXInPixels,
			// hoverFightPreviewPositionYInPixels, 20f, level);

		}

		// actor buttons
		if (level.activeActor == this && this.faction == level.factions.get(0)) {

			// animationX
			buttonsAnimateCurrentTime += Game.delta;
			float animationEndPointX = this.squareGameObjectIsOn.x
					* Game.SQUARE_WIDTH + Game.SQUARE_WIDTH;
			float animationStartPointX = animationEndPointX
					- (buttons.size() * 50);
			float animationDistanceX = animationEndPointX
					- animationStartPointX;
			if (buttonsAnimateCurrentTime > buttonsAnimateMaxTime) {
				buttonsAnimateCurrentTime = buttonsAnimateMaxTime;
			}
			float animationOffsetX = (buttonsAnimateCurrentTime / buttonsAnimateMaxTime)
					* animationDistanceX;
			float buttonsX = animationStartPointX + animationOffsetX;
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).x = buttonsX + 50 * i;
				buttons.get(i).y = this.squareGameObjectIsOn.y
						* Game.SQUARE_HEIGHT + Game.SQUARE_HEIGHT;
				buttons.get(i).drawWithinBounds(
						animationEndPointX,
						animationEndPointX + (buttons.size() * 50),
						this.squareGameObjectIsOn.y * Game.SQUARE_HEIGHT
								+ Game.SQUARE_HEIGHT,
						this.squareGameObjectIsOn.y * Game.SQUARE_HEIGHT
								+ Game.SQUARE_HEIGHT + 50);
			}
		}

		// System.out.println("showWeaponSelection = " + showWeaponSelection);

		if (showWeaponSelection) {
			for (int i = 0; i < weapons.size(); i++) {
				TextureUtils.drawTexture(weapons.get(i).imageTexture,
						attackButton.x, attackButton.x + attackButton.width,
						attackButton.y + attackButton.height
								+ attackButton.height * i, attackButton.y
								+ attackButton.height + attackButton.height * i
								+ attackButton.height);
			}
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
				// if (fight.reachable)
				hoverFightPreviewFights.add(fight);
			}
			// }

		}

	}

	public void hideHoverFightPreview() {
		this.showHoverFightPreview = false;
	}

	public void attackClicked() {
		showWeaponSelection = !showWeaponSelection;
	}

	public Button getButtonFromMousePosition(float alteredMouseX,
			float alteredMouseY) {

		for (Button button : this.buttons) {
			if (alteredMouseX > button.x
					&& alteredMouseX < button.x + button.width
					&& alteredMouseY > button.y
					&& alteredMouseY < button.y + button.height) {

				return button;
			}
		}

		return null;
	}

	public void unselected() {
		buttonsAnimateCurrentTime = 0;
		this.showWeaponSelection = false;
		level.removeWalkingHighlight();
		level.removeWeaponsThatCanAttackHighlight();
		level.activeActor.hideHoverFightPreview();
	}
}
