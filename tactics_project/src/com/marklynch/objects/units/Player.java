package com.marklynch.objects.units;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.power.PowerBleed;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.popups.Notification;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;

public class Player extends Actor {

	public static AIPath playerPathToDraw = null;
	public static AIPath playerPathToMove = null;
	public static Square playerTargetSquare = null;
	public static Actor playerTargetActor = null;
	public static boolean playerFirstMove = false;
	public static Action playerTargetAction = null;
	public static float xp;
	public static float xpThisLevel;
	public static float xpPerLevel = 55;
	public ArrayList<Square> squaresVisibleToPlayerOnlyPlayer = new ArrayList<Square>();

	public Player(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight,
			GameObject bed, Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX,
			float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, int templateId) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, true, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, gold, mustHaves, mightHaves, templateId);
		hairImageTexture = ResourceUtils.getGlobalImage("hair.png");
		stepLeftTexture = ResourceUtils.getGlobalImage("player_step_left.png");
		stepRightTexture = ResourceUtils.getGlobalImage("player_step_right.png");

		powers.add(new PowerBleed(this));
		powers.add(new PowerInferno(this));
		thoughtsOnPlayer = 100;
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForHunter(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Player makeCopy(String name, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {

		Player actor = new Player(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, new Inventory(), widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX,
				headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, gold, mustHaves, mightHaves,
				templateId);

		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(templateId);

		bestiaryKnowledge.name = true;
		bestiaryKnowledge.level = true;
		bestiaryKnowledge.image = true;
		bestiaryKnowledge.totalHealth = true;
		bestiaryKnowledge.faction = true;
		bestiaryKnowledge.group = true;

		// Stats
		bestiaryKnowledge.strength = true;
		bestiaryKnowledge.dexterity = true;
		bestiaryKnowledge.intelligence = true;
		bestiaryKnowledge.endurance = true;

		// Damage
		bestiaryKnowledge.slashDamage = true;
		bestiaryKnowledge.bluntDamage = true;
		bestiaryKnowledge.pierceDamage = true;
		bestiaryKnowledge.fireDamage = true;
		bestiaryKnowledge.waterDamage = true;
		bestiaryKnowledge.electricDamage = true;
		bestiaryKnowledge.poisonDamage = true;
		bestiaryKnowledge.range = true;

		// Resistances
		bestiaryKnowledge.slashResistance = true;
		bestiaryKnowledge.bluntResistance = true;
		bestiaryKnowledge.pierceResistance = true;
		bestiaryKnowledge.fireResistance = true;
		bestiaryKnowledge.waterResistance = true;
		bestiaryKnowledge.electricResistance = true;
		bestiaryKnowledge.poisonResistance = true;

		// Powers
		bestiaryKnowledge.powers = true;

		return actor;
	}

	public void addXP(int xp) {
		Player.xp += xp;
		Player.xpThisLevel += xp;
		Game.level.activityLogger.addActivityLog(new ActivityLog(
				new Object[] { Game.level.player, " got " + xp + "XP (" + xpThisLevel + "/" + xpPerLevel + ")" }));
		boolean leveledUp = false;
		while (Player.xpThisLevel >= xpPerLevel) {
			actorLevel++;
			Player.xpThisLevel -= xpPerLevel;
			leveledUp = true;
		}
		if (leveledUp) {
			Game.level.notifications.add(new Notification(new Object[] { Game.level.player, " leveled Up!" }));
			Game.level.activityLogger.addActivityLog(
					new ActivityLog(new Object[] { Game.level.player, " are now Level " + actorLevel }));
		}
	}

	public void drawStaticUI() {
		// XP Bar!
		float percentage = xpThisLevel / xpPerLevel;
		float xpBarWidth = Game.windowWidth * percentage;
		QuadUtils.drawQuad(Color.YELLOW, 0, Game.windowHeight - 20, xpBarWidth, Game.windowHeight);

	}

	public void discoveryCheck() {
		for (Square square : squaresVisibleToPlayerOnlyPlayer) {

		}
	}

	public void calculateVisibleSquares(Square square) {

		// for (int x = 0; x < Game.level.squares.length; x++) {
		// for (int y = 0; y < Game.level.squares[0].length; y++) {
		// Game.level.squares[x][y].visibleToSelectedCharacter = false;
		// if (this == Game.level.player) {
		// Game.level.squares[x][y].visibleToPlayer = false;
		// }
		// }
		// }

		for (Square squarePreviouslyVisibleToPlayer : squaresVisibleToPlayerOnlyPlayer) {
			squarePreviouslyVisibleToPlayer.visibleToPlayer = false;
		}

		squaresVisibleToPlayerOnlyPlayer.clear();

		double x1 = square.xInGrid;
		double y1 = square.yInGrid;

		// for (int i = sight; i > 0; i--) {
		ArrayList<Point> furthestVisiblePoints = this.getAllCoordinatesAtDistanceFromSquare(sight, square);
		for (Point point : furthestVisiblePoints) {
			markVisibleSquaresInLineTo(x1 + 0.5d, y1 + 0.5d, point.getX() + 0.5d, point.getY() + 0.5d);
		}
		// }

		// Awkward corner squares (the inner corner ones that u cant tecnically
		// see)
		ArrayList<Square> awkwardSquaresToMakeVisible = new ArrayList<Square>();
		for (Square potentiallyVIsibleSquare : this.getAllSquaresWithinDistance(sight, square)) {

			if (potentiallyVIsibleSquare.visibleToPlayer)
				continue;

			if (!potentiallyVIsibleSquare.inventory.contains(Wall.class))
				continue;

			int visibleNeighbors = 0;

			Square squareAbove = potentiallyVIsibleSquare.getSquareAbove();
			if (squareAbove != null && squareAbove.visibleToPlayer)
				visibleNeighbors++;

			Square squareBelow = potentiallyVIsibleSquare.getSquareBelow();
			if (squareBelow != null && squareBelow.visibleToPlayer)
				visibleNeighbors++;

			Square squareToLeftOf = potentiallyVIsibleSquare.getSquareToLeftOf();
			if (squareToLeftOf != null && squareToLeftOf.visibleToPlayer)
				visibleNeighbors++;

			Square squareToRightOf = potentiallyVIsibleSquare.getSquareToRightOf();
			if (squareToRightOf != null && squareToRightOf.visibleToPlayer)
				visibleNeighbors++;

			if (visibleNeighbors > 1) {
				boolean playerToLeftOfAwkwardWall = false;
				boolean playerToRightOfAwkwardWall = false;
				boolean playerAboveAwkwardWall = false;
				boolean playerBelowAwkwardWall = false;

				if (this.squareGameObjectIsOn.xInGrid < potentiallyVIsibleSquare.xInGrid) {
					playerToLeftOfAwkwardWall = true;
				} else if (this.squareGameObjectIsOn.xInGrid > potentiallyVIsibleSquare.xInGrid) {
					playerToRightOfAwkwardWall = true;
				}
				if (this.squareGameObjectIsOn.yInGrid < potentiallyVIsibleSquare.yInGrid) {
					playerAboveAwkwardWall = true;
				} else if (this.squareGameObjectIsOn.yInGrid > potentiallyVIsibleSquare.yInGrid) {
					playerBelowAwkwardWall = true;
				}

				if (playerToLeftOfAwkwardWall && playerAboveAwkwardWall) {
					if (!Game.level.squares[potentiallyVIsibleSquare.xInGrid - 1][potentiallyVIsibleSquare.yInGrid
							- 1].inventory.blocksLineOfSight()) {
						awkwardSquaresToMakeVisible.add(potentiallyVIsibleSquare);
					}
				} else if (playerToLeftOfAwkwardWall && playerBelowAwkwardWall) {
					if (!Game.level.squares[potentiallyVIsibleSquare.xInGrid - 1][potentiallyVIsibleSquare.yInGrid
							+ 1].inventory.blocksLineOfSight()) {
						awkwardSquaresToMakeVisible.add(potentiallyVIsibleSquare);
					}
				} else if (playerToRightOfAwkwardWall && playerAboveAwkwardWall) {
					if (!Game.level.squares[potentiallyVIsibleSquare.xInGrid + 1][potentiallyVIsibleSquare.yInGrid
							- 1].inventory.blocksLineOfSight()) {
						awkwardSquaresToMakeVisible.add(potentiallyVIsibleSquare);
					}
				} else if (playerToRightOfAwkwardWall && playerBelowAwkwardWall) {
					if (!Game.level.squares[potentiallyVIsibleSquare.xInGrid + 1][potentiallyVIsibleSquare.yInGrid
							+ 1].inventory.blocksLineOfSight()) {
						awkwardSquaresToMakeVisible.add(potentiallyVIsibleSquare);
					}
				}

			}
		}
		for (Square awkwardSquareToMakeVisible : awkwardSquaresToMakeVisible) {
			markSquareAsVisibleToActiveCharacter(awkwardSquareToMakeVisible.xInGrid,
					awkwardSquareToMakeVisible.yInGrid);
		}
	}

	public boolean markSquareAsVisibleToActiveCharacter(int x, int y) {

		if (x < 0)
			return true;
		if (y < 0)
			return true;
		if (x >= Game.level.squares.length)
			return true;
		if (y >= Game.level.squares[0].length)
			return true;

		if (!squaresVisibleToPlayerOnlyPlayer.contains(Game.level.squares[x][y])) {
			Game.level.squares[x][y].visibleToSelectedCharacter = true;
			squaresVisibleToPlayerOnlyPlayer.add(Game.level.squares[x][y]);
			if (this == Game.level.player) {
				Game.level.squares[x][y].visibleToPlayer = true;
				Game.level.squares[x][y].seenByPlayer = true;
				// Seen area for first time?
				if (Game.level.squares[x][y].areaSquareIsIn != null
						&& Game.level.squares[x][y].areaSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].areaSquareIsIn.hasBeenSeenByPlayer();
				}
				// Seen structure for first time?
				if (Game.level.squares[x][y].structureSquareIsIn != null
						&& Game.level.squares[x][y].structureSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].structureSquareIsIn.hasBeenSeenByPlayer();
				}
				// Seen room for first time?
				if (Game.level.squares[x][y].structureRoomSquareIsIn != null
						&& Game.level.squares[x][y].structureRoomSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].structureRoomSquareIsIn.hasBeenSeenByPlayer();
				}
			}
		}

		if (Game.level.squares[x][y] == squareGameObjectIsOn)
			return false;

		return Game.level.squares[x][y].inventory.blocksLineOfSight();
	}

	// SUPERCOVER algorithm
	public void markVisibleSquaresInLineTo(double x0, double y0, double x1, double y1) {
		double vx = x1 - x0;
		double vy = y1 - y0;
		double dx = Math.sqrt(1 + Math.pow((vy / vx), 2));
		double dy = Math.sqrt(1 + Math.pow((vx / vy), 2));
		double ix = Math.floor(x0);
		double iy = Math.floor(y0);
		double sx, ex;

		if (vx < 0) {
			sx = -1;
			ex = (x0 - ix) * dx;
		} else {
			sx = 1;
			ex = (ix + 1 - x0) * dx;
		}

		double sy, ey;
		if (vy < 0) {
			sy = -1;
			ey = (y0 - iy) * dy;
		} else {
			sy = 1;
			ey = (iy + 1 - y0) * dy;
		}

		boolean done = false;
		double len = Math.sqrt(vx * vx + vy * vy);

		while (done == false) {
			if (Math.min(ex, ey) <= len) {
				double rx = ix;
				double ry = iy;
				if (ex < ey) {
					ex = ex + dx;
					ix = ix + sx;
				} else {
					ey = ey + dy;
					iy = iy + sy;
				}
				done = markSquareAsVisibleToActiveCharacter((int) rx, (int) ry);
			} else if (!done) {
				done = true;
				markSquareAsVisibleToActiveCharacter((int) ix, (int) iy);
			}
		}

	}

}
