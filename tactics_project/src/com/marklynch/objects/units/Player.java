package com.marklynch.objects.units;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.animation.AnimationTake;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.constructs.power.PowerBleed;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Orb;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionDie;
import com.marklynch.objects.actions.ActionDiscover;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.popups.Notification;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Player extends Human {

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

	public long lastUpdateRealtime = 0;

	public Player() {
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
	public void updateRealtime(int delta) {
		if (lastUpdateRealtime == Level.lastUpdate)
			return;
		lastUpdateRealtime = Level.lastUpdate;
		super.updateRealtime(delta);
	}

	@Override
	public Player makeCopy(String name, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {

		Player actor = new Player();
		actor.name = name;
		actor.squareGameObjectIsOn = square;
		actor.faction = faction;

		actor.title = title;
		actor.level = level;
		actor.totalHealth = actor.remainingHealth = totalHealth;
		actor.strength = strength;
		actor.dexterity = dexterity;
		actor.intelligence = intelligence;
		actor.endurance = endurance;
		actor.imageTexturePath = imageTexturePath;
		// actor.squareGameObjectIsOn = null;
		actor.travelDistance = travelDistance;
		actor.sight = sight;
		// actor.bed = null;
		// actor.inventory = new Inventory();
		actor.widthRatio = widthRatio;
		actor.heightRatio = heightRatio;
		actor.drawOffsetRatioX = drawOffsetRatioX;
		actor.drawOffsetRatioY = drawOffsetRatioY;
		actor.soundWhenHit = soundWhenHit;
		actor.soundWhenHitting = soundWhenHitting;
		// actor.soundDampening = 1f;
		// actor.stackable = false;
		actor.weight = weight;
		actor.handAnchorX = handAnchorX;
		actor.handAnchorY = handAnchorY;
		actor.headAnchorX = headAnchorX;
		actor.headAnchorY = headAnchorY;
		actor.bodyAnchorX = bodyAnchorX;
		actor.bodyAnchorY = bodyAnchorY;
		actor.legsAnchorX = legsAnchorX;
		actor.legsAnchorY = legsAnchorY;
		actor.canOpenDoors = canOpenDoors;
		actor.canEquipWeapons = canEquipWeapons;
		// gold
		actor.templateId = templateId;

		actor.init(gold, mustHaves, mightHaves);

		// Player actor = new Player(name, title, actorLevel, (int) totalHealth,
		// strength, dexterity, intelligence,
		// endurance, imageTexturePath, square, travelDistance, sight, bed, new
		// Inventory(), widthRatio,
		// heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
		// soundWhenHitting, soundDampening, light,
		// lightHandleX, lightHandlY, stackable, fireResistance,
		// waterResistance, electricResistance,
		// poisonResistance, slashResistance, weight, owner, faction,
		// handAnchorX, handAnchorY, headAnchorX,
		// headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY,
		// gold, mustHaves, mightHaves,
		// templateId);

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

	public void addXP(int xp, Square square) {

		if (square != null) {

			int orbs = xp;

			while (orbs > 0) {
				if (orbs >= 1) {
					Orb orb = Templates.SMALL_ORB.makeCopy(square, null, 1);
					Game.level.player.secondaryAnimations.add(
							new AnimationTake(orb, Game.level.player, square, (float) (Math.random() * 0.25f + 0.25f)));
					Game.level.inanimateObjectsOnGroundToRemove.add(orb);
					orbs -= 1;
				}
				if (orbs >= 5) {
					Orb orb = Templates.MEDIUM_ORB.makeCopy(square, null, 5);
					Game.level.player.secondaryAnimations.add(
							new AnimationTake(orb, Game.level.player, square, (float) (Math.random() * 0.25f + 0.25f)));
					Game.level.inanimateObjectsOnGroundToRemove.add(orb);
					orbs -= 5;
				}
				if (orbs >= 10) {
					Orb orb = Templates.LARGE_ORB.makeCopy(square, null, 10);
					Game.level.player.secondaryAnimations.add(
							new AnimationTake(orb, Game.level.player, square, (float) (Math.random() * 0.25f + 0.25f)));
					Game.level.inanimateObjectsOnGroundToRemove.add(orb);
					orbs -= 10;
				}
			}
		}

		Player.xp += xp;
		Player.xpThisLevel += xp;
		Game.level.activityLogger.addActivityLog(new ActivityLog(
				new Object[] { Game.level.player, " got " + xp + "XP (" + xpThisLevel + "/" + xpPerLevel + ")" }));
		boolean leveledUp = false;
		while (Player.xpThisLevel >= xpPerLevel) {
			level++;
			Player.xpThisLevel -= xpPerLevel;
			leveledUp = true;
		}
		if (leveledUp) {
			Game.level.addNotification(new Notification(new Object[] { Game.level.player, " leveled Up!" },
					Notification.NotificationType.LEVEL_UP, this));
			Game.level.activityLogger
					.addActivityLog(new ActivityLog(new Object[] { Game.level.player, " are now Level " + level }));
		}
	}

	public void drawStaticUI() {
		// XP Bar!
		float percentage = xpThisLevel / xpPerLevel;
		float xpBarWidth = Game.windowWidth * percentage;
		QuadUtils.drawQuad(Color.YELLOW, 0, Game.windowHeight - 20, xpBarWidth, Game.windowHeight);

		// Equipped item!
		Texture squareTexture = InventorySquare.YELLOW_SQUARE; // yellow is used
																// for equipped
																// items in
																// inventory

		float xInPixels = Game.windowWidth - 120;
		float yInPixels = Game.windowHeight - 180 - Game.INVENTORY_SQUARE_HEIGHT;

		TextureUtils.drawTexture(squareTexture, xInPixels, yInPixels, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
				yInPixels + Game.INVENTORY_SQUARE_HEIGHT);

		if (equipped != null) {

			float drawWidth = Game.INVENTORY_SQUARE_WIDTH;
			float drawHeight = Game.INVENTORY_SQUARE_HEIGHT;
			float realTextureWidth = equipped.imageTexture.getWidth();
			float realTextureHeight = equipped.imageTexture.getHeight();
			if (realTextureWidth >= realTextureHeight) {// knife
				drawHeight = Game.INVENTORY_SQUARE_HEIGHT * realTextureHeight / realTextureWidth;
			} else {
				drawWidth = Game.INVENTORY_SQUARE_WIDTH * realTextureWidth / realTextureHeight;
			}

			TextureUtils.drawTexture(equipped.imageTexture, xInPixels, yInPixels, xInPixels + drawWidth,
					yInPixels + drawHeight);

			for (Effect effect : equipped.activeEffectsOnGameObject) {
				TextureUtils.drawTexture(effect.imageTexture, 0.75f, xInPixels, yInPixels,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
			}

			// Count && value
			Integer count = 1;
			if (inventory.equippedStacks.get(equipped.templateId) != null)
				count = inventory.equippedStacks.get(equipped.templateId).size();
			if (count == null)
				count = 1;

			if (equipped instanceof Gold) {
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						new Object[] { equipped.value });

			} else if (count > 1) {
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						new Object[] { count + "x" });
			}
		}

	}

	public void discoveryCheck() {
		for (Square square : squaresVisibleToPlayerOnlyPlayer) {
			for (GameObject discoverableGameObject : square.inventory.getGameObjectsOfClass(Discoverable.class)) {
				Discoverable discoverable = ((Discoverable) discoverableGameObject);
				if (!discoverable.discovered) {
					new ActionDiscover(this, discoverable).perform();
				}
			}
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
					Game.level.squares[x][y].areaSquareIsIn.hasBeenSeenByPlayer(Game.level.squares[x][y]);
				}
				// Seen structure for first time?
				if (Game.level.squares[x][y].structureSquareIsIn != null
						&& Game.level.squares[x][y].structureSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].structureSquareIsIn.hasBeenSeenByPlayer(Game.level.squares[x][y]);
				}
				// Seen room for first time?
				if (Game.level.squares[x][y].structureRoomSquareIsIn != null
						&& Game.level.squares[x][y].structureRoomSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].structureRoomSquareIsIn.hasBeenSeenByPlayer(Game.level.squares[x][y]);
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

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		if (remainingHealth <= 0) {
			destroyedBy = attacker;
			destroyedByAction = action;
			remainingHealth = totalHealth;
			if (!Game.level.gameOver.showing)
				Game.level.openCloseGameOver();
			new ActionDie(this, squareGameObjectIsOn).perform();

			return true;
		}
		return false;
	}

}
