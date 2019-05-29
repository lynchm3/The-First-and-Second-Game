package com.marklynch.objects.actors;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionDie;
import com.marklynch.actions.ActionDiscover;
import com.marklynch.actions.ActionSpot;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.rarity.Rarity;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.FastTravelLocation;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Gold;
import com.marklynch.objects.inanimateobjects.Orb;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.popups.Notification;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class Player extends Human {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public static AIPath playerPathToMouse = null;
	public static AIPath playerPathToMove = null;
	// public static Square playerTargetSquare = null;
	// public static Actor playerTargetActor = null;
	public static boolean playerFirstMove = false;
	public static Action playerTargetAction = null;
	public static float xp;
	public static float xpThisLevel;
	public static float xpPerLevel = 55;
	public transient ArrayList<Square> squaresVisibleToPlayer = new ArrayList<Square>(Square.class);
	public transient ArrayList<Square> squaresPlayerCanCastTo = new ArrayList<Square>(Square.class);

	public transient long lastUpdateRealtime = 0;

	public Player() {
		stepLeftTexture = ResourceUtils.getGlobalImage("player_step_left.png", false);
		stepRightTexture = ResourceUtils.getGlobalImage("player_step_right.png", false);
		thoughtsOnPlayer = 100;
		sight = 20;
		type = "Player";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
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
	public void update(int delta) {
		super.update(delta);
	}

	// @Override
	// public void updateRealtime(int delta) {
	// if (lastUpdateRealtime == Level.lastUpdate)
	// return;
	// lastUpdateRealtime = Level.lastUpdate;
	// super.updateRealtime(delta);
	// }

	@Override
	public Player makeCopy(String name, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds,
			HOBBY[] hobbies) {

		Player actor = new Player();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.requiredEquipmentTemplateIds = requiredEquipmentTemplateIds;
		actor.hobbies = hobbies;

		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(templateId);

		bestiaryKnowledge.name = true;
		bestiaryKnowledge.level = true;
		bestiaryKnowledge.image = true;
		bestiaryKnowledge.totalHealth = true;
		bestiaryKnowledge.faction = true;
		bestiaryKnowledge.groupOfActors = true;

		// Stats
		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
			bestiaryKnowledge.putHighLevel(statType, true);
		}

		// Powers
		bestiaryKnowledge.powers = true;

		return actor;
	}

	public void addXP(int xp, Square square) {

		if (square != null) {

			int orbs = xp;

			while (orbs > 0) {
				if (orbs >= 1) {
					Orb orb = Templates.SMALL_ORB.makeCopy(null, null, 1);

					float x = square.xInGridPixels + orb.drawOffsetX;
					float y = square.yInGridPixels + orb.drawOffsetY;

					Level.addSecondaryAnimation(new AnimationTake(orb, Game.level.player, x, y,
							(float) (Math.random() * 0.25f + 0.75f), -0f, -0f, null));

					// Game.level.inanimateObjectsOnGround.add(orb);
					orbs -= 1;
				}
				if (orbs >= 5) {
					Orb orb = Templates.MEDIUM_ORB.makeCopy(null, null, 5);

					float x = square.xInGridPixels + orb.drawOffsetX;
					float y = square.yInGridPixels + orb.drawOffsetY;
					Level.addSecondaryAnimation(new AnimationTake(orb, Game.level.player, x, y,
							(float) (Math.random() * 0.25f + 0.75f), 0f, 0f, null));
					// Game.level.inanimateObjectsOnGround.add(orb);
					orbs -= 5;
				}
				if (orbs >= 10) {
					Orb orb = Templates.LARGE_ORB.makeCopy(null, null, 10);

					float x = square.xInGridPixels + orb.drawOffsetX;
					float y = square.yInGridPixels + orb.drawOffsetY;
					Level.addSecondaryAnimation(new AnimationTake(orb, Game.level.player, x, y,
							(float) (Math.random() * 0.25f + 0.75f), 0f, 0f, null));
					// Game.level.inanimateObjectsOnGround.add(orb);
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

	public transient LevelButton buttonEquippedWeaponAction = new LevelButton(110, 140, Game.INVENTORY_SQUARE_WIDTH,
			30f,
			//
			"end_turn_button.png", "end_turn_button.png", "ACTION", false, false,
			//
			Color.BLACK, Color.WHITE, null);

	@Override
	public void drawStaticUI() {

		super.drawStaticUI();// dmg numbers probably

		// Draw powers that draw
		for (Power power : this.powers) {
			if (power.draws) {
				power.drawUI();
			}
		}

		// XP Bar!
		float percentage = xpThisLevel / xpPerLevel;
		float xpBarWidth = Game.windowWidth * percentage;
		QuadUtils.drawQuad(Color.YELLOW, 0, Game.windowHeight - 20, xpBarWidth, Game.windowHeight);

		// Trespassing notification
		if (this.squareGameObjectIsOn.restricted() == true && !this.squareGameObjectIsOn.owners.contains(this)) {

			float xInPixels = Game.windowWidth - 110;
			float yInPixels = Game.windowHeight - 140 + Game.INVENTORY_SQUARE_WIDTH;
			QuadUtils.drawQuad(Color.RED, xInPixels, yInPixels, Game.windowWidth, yInPixels + 30);
			TextUtils.printTextWithImages(xInPixels, yInPixels, Integer.MAX_VALUE, false, null, Color.BLACK, 1f,
					"TRESPASSING");

		}

		// Equipped item!
		Color squareColor = Rarity.COMMON.color;
		if (equipped != null) {
			squareColor = equipped.rarity.color;
		}

		float xInPixels = Game.windowWidth - 110;
		float yInPixels = Game.windowHeight - 140 - Game.INVENTORY_SQUARE_HEIGHT;

		Color backgroundColor = InventorySquare.getBackgroundColorForInventorySquare(equipped);
		TextureUtils.drawTexture(InventorySquare.GREY_TRANSLUCENT_SQUARE, xInPixels, yInPixels,
				xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT, backgroundColor);
		TextureUtils.drawTexture(InventorySquare.WHITE_SQUARE, xInPixels, yInPixels,
				xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT, squareColor);

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
						Color.WHITE, 1f, new Object[] { equipped.value });

			} else if (count > 1) {
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						Color.WHITE, 1f, new Object[] { count + "x" });
			}

			// Action button for the equipped item
			final Action actionForEquipped = equipped.getDefaultActionForEquippedItem(this, this.squareGameObjectIsOn);
			if (actionForEquipped != null) {
				buttonEquippedWeaponAction.textParts = new Object[] { actionForEquipped.actionName };
				buttonEquippedWeaponAction.setClickListener(new ClickListener() {
					@Override
					public void click() {
						actionForEquipped.perform();
					}
				});
				buttonEquippedWeaponAction.draw();
			}
		}

	}

	public void hiddenObjectDiscoveryCheck() {
		for (Square square : squaresVisibleToPlayer) {
			for (GameObject gameObject : square.inventory.getGameObjects()) {
				if (!gameObject.discoveredObject && gameObject.level <= this.level) {
					new ActionDiscover(this, gameObject).perform();
				}
			}
		}
	}

	public void clearVisibleAndCastableSquares() {
		for (Square squarePreviouslyVisibleToPlayer : squaresVisibleToPlayer) {
			squarePreviouslyVisibleToPlayer.visibleToPlayer = false;
		}
		for (Square squarePreviouslyVisibleToPlayer : squaresPlayerCanCastTo) {
			squarePreviouslyVisibleToPlayer.playerCanCastTo = false;
		}

		squaresVisibleToPlayer.clear();
		squaresPlayerCanCastTo.clear();
	}

	public void calculateVisibleAndCastableSquares(Square square) {

		if (Game.editorMode)
			return;

//		Utils.printStackTrace();

		// for (int x = 0; x < Game.level.squares.length; x++) {
		// for (int y = 0; y < Game.level.squares[0].length; y++) {
		// Game.level.squares[x][y].visibleToSelectedCharacter = false;
		// if (this == Game.level.player) {
		// Game.level.squares[x][y].visibleToPlayer = false;
		// }
		// }
		// }

		clearVisibleAndCastableSquares();

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
		ArrayList<Square> awkwardSquaresToMakeVisible = new ArrayList<Square>(Square.class);
		for (Square potentiallyVIsibleSquare : this.getAllSquaresWithinDistance(0, sight, square)) {

			if (potentiallyVIsibleSquare.visibleToPlayer)
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
			markSquareAsVisibleToPlayer(awkwardSquareToMakeVisible.xInGrid, awkwardSquareToMakeVisible.yInGrid);
			markSquareAsCastableForPlayer(awkwardSquareToMakeVisible.xInGrid, awkwardSquareToMakeVisible.yInGrid);

		}
	}

	public boolean markSquareAsVisibleToPlayer(int x, int y) {

		if (x < 0)
			return true;
		if (y < 0)
			return true;
		if (x >= Game.level.squares.length)
			return true;
		if (y >= Game.level.squares[0].length)
			return true;

		if (!squaresVisibleToPlayer.contains(Game.level.squares[x][y])) {
			squaresVisibleToPlayer.add(Game.level.squares[x][y]);
			Game.level.squares[x][y].visibleToPlayer = true;
			if (!Game.level.squares[x][y].seenByPlayer) {

				// Haven't seen sqr before
				Game.level.squares[x][y].seenByPlayer = true;
				Game.level.squares[x][y].updateSquaresToSave();
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

				if (Game.level.squares[x][y].inventory.containsGameObjectOfType(FastTravelLocation.class)) {
					System.out.println("Spotted FastTravelLocation");
					new ActionSpot(Game.level.player,
							Game.level.squares[x][y].inventory.getGameObjectOfClass(FastTravelLocation.class),
							Game.level.squares[x][y]).perform();
				}
			}
		}

		if (Game.level.squares[x][y] == squareGameObjectIsOn)
			return false;

		return Game.level.squares[x][y].inventory.blocksLineOfSight();
	}

	public boolean markSquareAsCastableForPlayer(int x, int y) {

		if (x < 0)
			return true;
		if (y < 0)
			return true;
		if (x >= Game.level.squares.length)
			return true;
		if (y >= Game.level.squares[0].length)
			return true;

		if (!squaresPlayerCanCastTo.contains(Game.level.squares[x][y])) {
			squaresPlayerCanCastTo.add(Game.level.squares[x][y]);
			Game.level.squares[x][y].playerCanCastTo = true;
		}

		if (Game.level.squares[x][y] == squareGameObjectIsOn)
			return false;

		return Game.level.squares[x][y].inventory.blocksLineOfSight()
				|| Game.level.squares[x][y].inventory.blocksCasting();
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
		boolean doneForCastingChecks = false;
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
				done = markSquareAsVisibleToPlayer((int) rx, (int) ry);
				if (!doneForCastingChecks)
					doneForCastingChecks = markSquareAsCastableForPlayer((int) rx, (int) ry);
			} else if (!done) {
				done = true;
				markSquareAsVisibleToPlayer((int) ix, (int) iy);
				if (!doneForCastingChecks)
					markSquareAsCastableForPlayer((int) ix, (int) iy);
				doneForCastingChecks = true;
			}
		}

	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		if (remainingHealth <= 0) {
			// died = true;
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

	public static boolean inFight() {
		for (GameObject attacker : Level.player.attackers) {
			if (attacker.squareGameObjectIsOn != null && attacker.remainingHealth > 0
					&& Level.player.straightLineDistanceTo(attacker.squareGameObjectIsOn) < 20) {
				return true;
			}
		}
		return false;
	}

}
