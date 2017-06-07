package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AStarNode;
import com.marklynch.ai.utils.AStarSearch;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.objects.Bed;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Key;
import com.marklynch.objects.Openable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionDie;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.tools.Tool;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.Button;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Actor extends GameObject {

	public final static String[] editableAttributes = { "name", "imageTexture", "faction", "strength", "dexterity",
			"intelligence", "endurance", "totalHealth", "remainingHealth", "travelDistance", "inventory",
			"showInventory", "fitsInInventory", "canContainOtherObjects" };

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	protected int strength;
	protected int dexterity;
	protected int intelligence;
	protected int endurance;
	public String title = "";
	public int actorLevel = 1;
	public int travelDistance = 4;
	public int sight = 4;

	public transient int distanceMovedThisTurn = 0;
	public transient boolean showWeaponButtons = false;

	// buttons
	// public transient ArrayList<Button> buttons;
	// public transient AttackButton attackButton = null;
	// public transient AttackButton pushButton = null;
	// public transient float buttonsAnimateCurrentTime = 0f;
	// public final transient float buttonsAnimateMaxTime = 200f;

	// weapon buttons
	// public transient ArrayList<Button> weaponButtons;

	// Fight preview on hover
	// public transient boolean showHoverFightPreview = false;
	// public transient GameObject hoverFightPreviewDefender = null;
	// public transient Vector<Fight> hoverFightPreviewFights;

	public transient AIRoutine aiRoutine;

	public String activityDescription = "";
	public String miniDialogue = "";

	public transient Bed bed;
	public String bedGUID = null;

	public transient Faction faction;
	public String factionGUID = null;

	public GameObject equipped = null;
	public GameObject equippedBeforePickingUpObject = null;
	public String equippedWeaponGUID = null;

	public Helmet helmet;
	public BodyArmor bodyArmor;
	public LegArmor legArmor;
	public float handAnchorX;
	public float handAnchorY;
	public float headAnchorX;
	public float headAnchorY;
	public float bodyAnchorX;
	public float bodyAnchorY;
	public float legsAnchorX;
	public float legsAnchorY;

	public transient Conversation conversation;

	public boolean wasSwappedWithThisTurn = false;

	public int swapCooldown = 0;

	protected transient int highestPathCostSeen = 0;

	public ArrayList<Square> squaresVisibleToPlayerOnlyPlayer = new ArrayList<Square>();
	public HashMap<GameObject, Investigation> investigationsMap = new HashMap<GameObject, Investigation>();

	public ArrayList<Action> actionsPerformedThisTurn = new ArrayList<Action>();
	public ArrayList<Crime> crimesPerformedThisTurn = new ArrayList<Crime>();
	public ArrayList<Crime> crimesPerformedInLifetime = new ArrayList<Crime>();

	public Texture thoughtBubbleImageTexture = null;
	public Square lastSquare = null;

	// public ArrayList<Crime> crimesWitnessed;
	public Map<Actor, ArrayList<Crime>> crimesWitnessed = new HashMap<Actor, ArrayList<Crime>>();

	public Actor(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY) {
		super(name, health, imagePath, squareActorIsStandingOn, inventory, showInventory, false, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight,
				owner);
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.title = title;
		this.actorLevel = actorLevel;
		this.travelDistance = travelDistance;
		this.sight = sight;

		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;

		this.title = title;
		this.actorLevel = actorLevel;
		this.travelDistance = travelDistance;

		this.bed = bed;
		if (bed != null)
			this.bedGUID = bed.guid;
		if (bed != null)
			bed.owner = this;

		ArrayList<Weapon> weapons = getWeaponsInInventory();
		//
		// for (Weapon weapon : weapons) {
		// weaponButtons.add(new WeaponButton(0, 0, 50, 50,
		// weapon.imageTexturePath, weapon.imageTexturePath, weapon));
		// }

		if (weapons.size() > 0 && weapons.get(0) != null) {
			equipped = weapons.get(0);
			equippedWeaponGUID = weapons.get(0).guid;
		}

		attackers = new ArrayList<GameObject>();

		this.faction = faction;
		if (this.faction != null) {
			factionGUID = this.faction.guid;
			this.faction.actors.add(this);
		}

		this.handAnchorX = handAnchorX;
		this.handAnchorY = handAnchorY;
		this.headAnchorX = headAnchorX;
		this.headAnchorY = headAnchorY;
		this.bodyAnchorX = bodyAnchorX;
		this.bodyAnchorY = bodyAnchorY;
		this.legsAnchorX = legsAnchorX;
		this.legsAnchorY = legsAnchorY;

		drawOffsetX = -32;
		drawOffsetY = -64;

		this.lastSquare = this.squareGameObjectIsOn;

		for (GameObject gameObject : inventory.getGameObjects()) {
			gameObject.owner = this;
		}
	}

	@Override
	public void postLoad1() {

		super.postLoad1();

		ArrayList<Weapon> weapons = getWeaponsInInventory();

		if (weapons.size() > 0 && weapons.get(0) != null) {
			equippedWeaponGUID = weapons.get(0).guid;
		}

		loadImages();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
		super.postLoad2();

		// faction
		if (factionGUID != null) {
			for (Faction faction : Game.level.factions) {
				if (factionGUID.equals(faction.guid)) {
					this.faction = faction;
					if (!faction.actors.contains(this)) {
						faction.actors.add(this);
					}
				}
			}
		}

		// bed
		if (bedGUID != null) {
			for (GameObject gameObject : Game.level.inanimateObjectsOnGround) {
				if (bedGUID.equals(gameObject.guid)) {
					this.bed = (Bed) gameObject;
				}
			}
		}

		// equippedWeapon
		if (equippedWeaponGUID != null) {
			for (Weapon weapon : this.getWeaponsInInventory()) {
				if (equippedWeaponGUID.equals(weapon.guid)) {
					this.equipped = weapon;
				}
			}
		}
	}

	public Path getPathTo(Square target) {

		if (target == null || (target.inventory.canBeMovedTo() == false)) {

			return null;
		}

		// ASTARSEACH.FINDPATH

		LinkedList<AStarNode> aStarNodesPath = (LinkedList<AStarNode>) new AStarSearch()
				.findPath(this.squareGameObjectIsOn, target);

		if (aStarNodesPath != null) {
			Vector<Square> squarePath = new Vector<Square>();

			for (AStarNode aStarNode : aStarNodesPath) {
				squarePath.add((Square) aStarNode);
			}

			Path path = new Path(squarePath, squarePath.size());
			return path;
		}

		return null;

	}

	public Path getPathIfCanReachInOneTurn(Square target) {

		if (this.straightLineDistanceTo(target) > this.travelDistance)
			return null;

		Path path = getPathTo(target);
		if (path != null && this.travelDistance >= path.travelCost) {
			return path;
		}

		return null;
	}

	// public void calculateReachableSquares(Square[][] squares) {
	//
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	//
	// Path pathToSquare = paths.get(squares[i][j]);
	// if (pathToSquare == null
	// || pathToSquare.travelCost > (this.travelDistance -
	// this.distanceMovedThisTurn)) {
	// squares[i][j].reachableBySelectedCharater = false;
	// // squares[i][j].distanceToSquare = Integer.MAX_VALUE;
	//
	// } else {
	// squares[i][j].reachableBySelectedCharater = true;
	// // squares[i][j].distanceToSquare = pathToSquare.travelCost;
	// }
	//
	// }
	// }
	// }

	// public void calculateAttackableSquares(Square[][] squares) {
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	// squares[i][j].weaponsThatCanAttack.clear();
	// }
	// }
	//
	// for (Weapon weapon : getWeaponsInInventory()) {
	// weapon.calculateAttackableSquares(squares);
	// }
	// }

	public void calculateVisibleSquares() {

		for (int x = 0; x < Game.level.squares.length; x++) {
			for (int y = 0; y < Game.level.squares[0].length; y++) {
				Game.level.squares[x][y].visibleToSelectedCharacter = false;
				if (this == Game.level.player) {
					Game.level.squares[x][y].visibleToPlayer = false;
				}
			}
		}

		squaresVisibleToPlayerOnlyPlayer.clear();

		double x1 = this.squareGameObjectIsOn.xInGrid + 0.5d;
		double y1 = this.squareGameObjectIsOn.yInGrid + 0.5d;

		for (int i = sight; i > 0; i--) {
			ArrayList<Point> furthestVisiblePoints = this.getAllCoordinatesAtDistance(i);
			for (Point point : furthestVisiblePoints) {
				markVisibleSquaresInLineToo(x1, y1, point.getX() + 0.5d, point.getY() + 0.5d);
			}
		}
	}

	// SUPERCOVER algorithm
	public void markVisibleSquaresInLineToo(double x0, double y0, double x1, double y1) {
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
				// Seen Building
				if (Game.level.squares[x][y].structureSquareIsIn != null
						&& Game.level.squares[x][y].structureSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].structureSquareIsIn.hasBeenSeenByPlayer();
				}
			}
		}

		if (Game.level.squares[x][y] == squareGameObjectIsOn)
			return false;

		return Game.level.squares[x][y].inventory.blocksLineOfSight();
	}

	public boolean hasRange(int weaponDistance) {
		for (Weapon weapon : getWeaponsInInventory()) {
			if (weaponDistance >= weapon.getEffectiveMinRange() && weaponDistance <= weapon.getEffectiveMaxRange()) {
				// selectedWeapon = weapon;
				return true;
			}
		}
		return false;
	}

	public void equipBestWeapon(GameObject target) {

		// take countering in to account, this is quite an interesting issue,
		// coz I know the enemy is going to pick the best weapon to counter
		// with....
		// weird...

		int range = this.straightLineDistanceTo(target.squareGameObjectIsOn);
		for (Weapon weapon : getWeaponsInInventory()) {
			if (range >= weapon.getEffectiveMinRange() && range <= weapon.getEffectiveMaxRange()) {
				equipped = weapon;
				equippedWeaponGUID = weapon.guid;
			}
		}
	}

	public void equipBestWeaponForCounter(GameObject target, Weapon targetsWeapon) {

		ArrayList<Weapon> potentialWeaponsToEquip = new ArrayList<Weapon>();

		int range = this.straightLineDistanceTo(target.squareGameObjectIsOn);
		for (Weapon weapon : getWeaponsInInventory()) {
			if (range >= weapon.getEffectiveMinRange() && range <= weapon.getEffectiveMaxRange()) {
				potentialWeaponsToEquip.add(weapon);
			}
		}

		if (potentialWeaponsToEquip.size() == 0) {
			equipped = null;
			equippedWeaponGUID = null;
		} else if (potentialWeaponsToEquip.size() == 1) {
			equipped = potentialWeaponsToEquip.get(0);
			equippedWeaponGUID = potentialWeaponsToEquip.get(0).guid;
		} else {
			ArrayList<Fight> fights = new ArrayList<Fight>();
			for (Weapon weapon : potentialWeaponsToEquip) {
				Fight fight = new Fight(this, weapon, target, targetsWeapon, range);
				fights.add(fight);
			}
			fights.sort(new Fight.FightComparator());
			equipped = fights.get(0).attackerWeapon;
			equippedWeaponGUID = fights.get(0).attackerWeapon.guid;
		}
	}

	@Override
	public boolean checkIfDestroyed(GameObject attacker) {
		if (remainingHealth <= 0) {
			super.checkIfDestroyed(attacker);
			new ActionDie(this, squareGameObjectIsOn).perform();
			for (Crime crime : crimesPerformedInLifetime) {
				for (Actor witness : crime.witnesses) {
					witness.crimesWitnessed.remove(this);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void draw1() {

		// if (this.squareGameObjectIsOn.visibleToPlayer == false &&
		// persistsWhenCantBeSeen == false)
		// return;
		//
		// if (!this.squareGameObjectIsOn.seenByPlayer)
		// return;

		if (this.remainingHealth <= 0)
			return;
		// Draw health
		if (hiding && this != Game.level.player)
			return;

		if (remainingHealth != totalHealth) {

			// draw sidebar on square
			float healthPercentage = (remainingHealth) / (totalHealth);
			float weaponAreaWidthInPixels = Game.SQUARE_WIDTH / 20;
			float weaponAreaHeightInPixels = Game.SQUARE_HEIGHT;
			float healthBarHeightInPixels = Game.SQUARE_HEIGHT * healthPercentage;
			float weaponAreaPositionXInPixels = 0;
			float weaponAreaPositionYInPixels = 0;

			if (this.faction == Game.level.factions.get(0)) {
				weaponAreaPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
				weaponAreaPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;
			} else {
				weaponAreaPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
						+ Game.SQUARE_WIDTH - weaponAreaWidthInPixels;
				weaponAreaPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

			}

			// White bit under health bar
			QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), weaponAreaPositionXInPixels + 1,
					weaponAreaPositionXInPixels + weaponAreaWidthInPixels - 1, weaponAreaPositionYInPixels + 1,
					weaponAreaPositionYInPixels + weaponAreaHeightInPixels - 1);

			// Colored health bar
			QuadUtils.drawQuad(new Color(this.faction.color.r, this.faction.color.g, this.faction.color.b),
					weaponAreaPositionXInPixels + 1, weaponAreaPositionXInPixels + weaponAreaWidthInPixels - 1,
					weaponAreaPositionYInPixels + 1, weaponAreaPositionYInPixels + healthBarHeightInPixels - 1);
		}

		super.draw1();

		if (equipped != null) {

			int weaponPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX + handAnchorX - equipped.anchorX);
			int weaponPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY + handAnchorY - equipped.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.equipped.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionXInPixels + equipped.width, weaponPositionYInPixels,
					weaponPositionYInPixels + equipped.height);
		}

		if (helmet != null) {

			int helmetPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX + headAnchorX - helmet.anchorX);
			int helmetPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY + headAnchorY - helmet.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.helmet.imageTexture, alpha, helmetPositionXInPixels,
					helmetPositionXInPixels + helmet.width, helmetPositionYInPixels,
					helmetPositionYInPixels + helmet.height);
		}

		// draw anchor
		// QuadUtils.drawQuad(Color.WHITE,
		// this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH +
		// drawOffsetX + headAnchorX - 5f,
		// this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH +
		// drawOffsetX + headAnchorX + 5f,
		// this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT +
		// drawOffsetY + headAnchorY - 5f,
		// this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT +
		// drawOffsetY + headAnchorY + 5f);

		// TextureUtils.skipNormals = false;

		int actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
		int actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

		// body shoulder coords, arm shoulder coords
		int armPositionXInPixels = actorPositionXInPixels + 65 - 11;
		int armPositionYInPixels = actorPositionYInPixels + 41 - 13;

		int rotateXInPixels = actorPositionXInPixels + 65;
		int rotateYInPixels = actorPositionYInPixels + 41;

		int equippedWeaponPositionXInPixels = armPositionXInPixels + 70 - 20;
		int equippedWeaponPositionYInPixels = armPositionYInPixels + 110 - 86;

		float alpha = 1.0f;

		if (hasAttackedThisTurn == true && this.faction != null && Game.level.currentFactionMoving == this.faction) {
			alpha = 0.5f;
		}

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(rotateXInPixels, rotateYInPixels));
		view.rotate((float) Math.toRadians(60), new Vector3f(0f, 0f, 1f));
		// view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-rotateXInPixels, -rotateYInPixels));
		Game.activeBatch.updateUniforms();

		Game.activeBatch.flush();
		view.translate(new Vector2f(rotateXInPixels, rotateYInPixels));
		view.rotate((float) Math.toRadians(-60), new Vector3f(0f, 0f, 1f));
		// view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-rotateXInPixels, -rotateYInPixels));
		Game.activeBatch.updateUniforms();

	}

	@Override
	public void draw2() {

		// if (this.squareGameObjectIsOn.visibleToPlayer == false)
		// return;

		if (this.remainingHealth <= 0)
			return;

		super.draw2();

		// Draw activity text
		if (activityDescription != null && activityDescription.length() > 0) {
			float activityX1 = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
			float activityX2 = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ Game.font.getWidth(activityDescription);
			float activityY1 = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_WIDTH + 60;
			float activityY2 = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_WIDTH + 80;
			QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 0.5f), activityX1, activityX2, activityY1, activityY2);
			TextUtils.printTextWithImages(new Object[] { activityDescription }, activityX1, activityY1,
					Integer.MAX_VALUE, false);
		}

		// Draw mini dialogue
		if (miniDialogue != null && miniDialogue.length() > 0) {
			float miniDialogueX1 = (this.squareGameObjectIsOn.xInGrid + 1) * (int) Game.SQUARE_WIDTH;
			float miniDialogueX2 = (this.squareGameObjectIsOn.xInGrid + 1) * (int) Game.SQUARE_WIDTH
					+ Game.font.getWidth(miniDialogue);
			float miniDialogueY1 = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_WIDTH;
			float miniDialogueY2 = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_WIDTH + 20;
			QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 0.5f), miniDialogueX1, miniDialogueX2, miniDialogueY1,
					miniDialogueY2);
			TextUtils.printTextWithImages(new Object[] { miniDialogue }, miniDialogueX1, miniDialogueY1,
					Integer.MAX_VALUE, false);
		}

		if (thoughtBubbleImageTexture != null) {
			int expressionPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX);
			int expressionPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY);
			expressionPositionYInPixels -= 128;
			int expressionWidth = 128;
			int expressionHeight = 128;
			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			TextureUtils.drawTexture(thoughtBubbleImageTexture, alpha, expressionPositionXInPixels,
					expressionPositionXInPixels + expressionWidth, expressionPositionYInPixels,
					expressionPositionYInPixels + expressionHeight);
			// TextureUtils.skipNormals = false;
		}

	}

	@Override
	public void drawUI() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false)
			return;
		super.drawUI();
	}

	@Override
	public void drawStaticUI() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false)
			return;

	}

	public Vector<Float> calculateIdealDistanceFrom(GameObject target) {

		Vector<Fight> fights = new Vector<Fight>();
		for (Weapon weapon : getWeaponsInInventory()) {
			for (float range = weapon.getEffectiveMinRange(); range <= weapon.getEffectiveMaxRange(); range++) {
				Fight fight = new Fight(this, weapon, target, target.bestCounterWeapon(this, weapon, range), range);
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

	public Button getButtonFromMousePosition(float alteredMouseX, float alteredMouseY) {

		return null;
	}

	public void unselected() {
		// Game.level.removeWalkingHighlight();
		// Game.level.removeWeaponsThatCanAttackHighlight();
	}

	public void weaponButtonClicked(Weapon weapon) {
		this.equipped = weapon;
		equippedWeaponGUID = this.equipped.guid;
	}

	public Actor makeCopy(Square square, Faction faction, Bed bed) {

		Actor actor = new Actor(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, iceResistance, electricResistance,
				poisonResistance, weight, owner

				, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX,
				legsAnchorY);
		return actor;
	}

	@Override
	public void update(int delta) {
		clearActions();

		if (this.remainingHealth > 0) {

			// Remove dead attackers from attackers list
			ArrayList<GameObject> gameObjectsToRemoveFromList = new ArrayList<GameObject>();
			for (GameObject gameObject : attackers) {
				if (gameObject.remainingHealth <= 0) {
					gameObjectsToRemoveFromList.add(gameObject);
				}
			}

			for (GameObject gameObject : gameObjectsToRemoveFromList) {
				attackers.remove(gameObject);
			}

			// Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
			if (aiRoutine != null)
				this.aiRoutine.update();
		}

		// If hifing in a place get the effects
		if (hidingPlace != null) {
			for (Effect effect : hidingPlace.effectsFromInteracting) {
				addEffect(effect.makeCopy(hidingPlace, this));
			}
		}
		activateEffects();
	}

	public void sellAllToTarget(Class clazz, GameObject gameObject) {
		ArrayList<GameObject> gameObjectsToSell = (ArrayList<GameObject>) this.inventory.getGameObjects().clone();
		for (GameObject gameObjectToSell : gameObjectsToSell) {
			if (clazz == null || clazz.isInstance(gameObjectToSell)) {
				if (squareGameObjectIsOn.visibleToPlayer)
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { this, " sold ", gameObjectToSell, " to ", gameObject }));
				this.inventory.remove(gameObjectToSell);
				gameObject.inventory.add(gameObjectToSell);
			}
		}

	}

	private void addAttackerIfVisible(GameObject potentialAttacker) {

		if (!this.attackers.contains(potentialAttacker)
				&& straightLineDistanceTo(potentialAttacker.squareGameObjectIsOn) < sight
				&& canSeeGameObject(potentialAttacker) && potentialAttacker != this) {
			this.attackers.add(potentialAttacker);
			potentialAttacker.addAttackerForThisAndGroupMembers(this);
		}
	}

	public void addAttackerForNearbyFactionMembersIfVisible(GameObject attacker) {
		for (Actor ally : this.faction.actors) {
			ally.addAttackerIfVisible(attacker);
		}
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		if (this == Game.level.player) {
			return null;
		} else if (performer.attackers.contains(this)) {
			ActionAttack actionAttack = new ActionAttack(performer, this);
			if (actionAttack.enabled && actionAttack.legal) {
				return actionAttack;
			}
		} else {
			return new ActionTalk(performer, this);
		}
		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();
		if (this != Game.level.player) {
			// Talk
			actions.add(new ActionTalk(performer, this));
			// Inherited from object (attack...)
			actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
			// Inherited from squre (move/swap squares)
			actions.addAll(squareGameObjectIsOn.getAllActionsPerformedOnThisInWorld(performer));
		}

		if (this == Game.level.player) {

			// self action
			if (equipped instanceof Tool) {
				Tool tool = (Tool) equipped;
				Action utilityAction = tool.getUtilityAction(performer);
				if (utilityAction != null) {
					actions.add(utilityAction);
				}
			}

			if (hiding) {
				actions.add(new ActionStopHiding(this, this.hidingPlace));
			} else {
				HidingPlace hidingPlaceStandingOn = (HidingPlace) this.squareGameObjectIsOn.inventory
						.getGameObjectOfClass(HidingPlace.class);
				if (hidingPlaceStandingOn != null)
					actions.add(new ActionHide(this, hidingPlaceStandingOn));
			}
		}

		return actions;
	}

	public boolean hasAttackers() {
		return attackers.size() > 0;
	}

	public ArrayList<GameObject> getAttackers() {
		return attackers;
	}

	@Override
	public Conversation getConversation() {

		Quest quest;
		if (group != null) {
			quest = group.quest;
		} else {
			quest = this.quest;
		}

		if (quest != null) {
			Conversation questConversation = null;
			questConversation = quest.getConversation(this);

			if (questConversation != null) {
				return questConversation;
			}

		}

		return this.conversation;
	}

	public boolean canSeeGameObjectFromSpecificSquare(Square sourceSquare, GameObject gameObject) {

		if (this.straightLineDistanceTo(gameObject.squareGameObjectIsOn) > 1 && gameObject.hiding)
			return false;

		return canSeeSquareFromSpecificSquare(sourceSquare, gameObject.squareGameObjectIsOn);
	}

	public boolean canSeeSquareFromSpecificSquare(Square sourceSquare, Square targetSquare) {

		if (this.straightLineDistanceBetween(sourceSquare, targetSquare) > sight)
			return false;

		if (!this.visibilityBetween(sourceSquare, targetSquare))
			return false;

		return true;
	}

	public boolean canSeeGameObject(GameObject gameObject) {
		return canSeeGameObjectFromSpecificSquare(this.squareGameObjectIsOn, gameObject);
	}

	public boolean canSeeSquare(Square square) {
		return canSeeSquareFromSpecificSquare(this.squareGameObjectIsOn, square);
	}

	public boolean hasKeyForDoor(Openable door) {
		if (door.keys == null)
			return false;

		for (GameObject gameObject : inventory.getGameObjects()) {
			for (Key key : door.keys) {
				if (key == gameObject) {
					return true;
				}
			}
		}

		return false;
	}

	public Key getKeyFor(Openable door) {
		if (door.keys == null)
			return null;

		for (GameObject gameObject : inventory.getGameObjects()) {
			for (Key key : door.keys) {
				if (key == gameObject) {
					return key;
				}
			}
		}

		return null;
	}

	public void clearActions() {
		for (Action action : actionsPerformedThisTurn) {
			if (action.sound != null) {
				for (Square destinationSquare : action.sound.destinationSquares) {
					destinationSquare.sounds.remove(action.sound);
				}
			}
		}
		actionsPerformedThisTurn.clear();
		crimesPerformedThisTurn.clear();
	}

	@Override
	public void attacked(Object attacker) {
		super.attacked(attacker);

		if (attacker instanceof Actor) {
			Actor actor = (Actor) attacker;
			if (canSeeGameObject(actor)) {
				actor.addAttackerForThisAndGroupMembers(this);
				actor.addAttackerForNearbyFactionMembersIfVisible(this);
				this.addAttackerForNearbyFactionMembersIfVisible(actor);
			}
			HidingPlace hidingPlace = (HidingPlace) actor.squareGameObjectIsOn.inventory
					.getGameObjectOfClass(HidingPlace.class);
			if (hidingPlace != null) {
				// actor.addAttackerForThisAndGroupMembers(this);
				// actor.addAttackerForNearbyFactionMembersIfVisible(this);
				this.addAttackerForNearbyFactionMembersIfVisible(hidingPlace);
			}
			this.addInvestigation(actor, actor.squareGameObjectIsOn, Investigation.INVESTIGATION_PRIORITY_ATTACKED);
		}
	}

	public void addInvestigation(GameObject actor, Square square, int priority) {
		// TODO Auto-generated method stub

		Investigation existingInvestigation = this.investigationsMap.get(actor);
		if (existingInvestigation == null) {
			this.investigationsMap.put(actor, new Investigation(actor, square, priority));
		} else if (existingInvestigation.priority <= priority) {
			this.investigationsMap.put(actor, new Investigation(actor, square, priority));
		}
	}

	public int getEffectiveStrength() {
		return strength;
	}

	public int getEffectiveDexterity() {
		return dexterity;
	}

	public int getEffectiveIntelligence() {
		return intelligence;
	}

	public int getEffectiveEndurance() {
		return endurance;
	}

	// public static void calculateReachableSquares() {
	// // Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
	// // Game.level.activeActor.calculateReachableSquares(Game.level.squares);
	// // Game.level.activeActor.calculateAttackableSquares(Game.level.squares);
	// }

	// public void calculatePathToSquare(Square[][] squares) {
	//
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	// squares[i][j].walkingDistanceToSquare = Integer.MAX_VALUE;
	// }
	// }
	// // paths
	// // public transient HashMap<Square, Path> paths = new HashMap<Square,
	// // Path>();
	//
	// highestPathCostSeen = 0;
	// Square currentSquare = squareGameObjectIsOn;
	// currentSquare.walkingDistanceToSquare = 0;
	//
	// Vector<Square> startPath = new Vector<Square>();
	// startPath.add(currentSquare);
	// Path bestPath = new Path((Vector<Square>) startPath.clone(), 0);
	//
	// for (int i = 0; i <= highestPathCostSeen; i++) {
	// // get all paths with that cost
	// Vector<Path> pathsWithCurrentCost = new Vector<Path>();
	// Vector<Path> pathsVector = new Vector<Path>();
	// pathsVector.add(bestPath);
	// for (int j = 0; j < pathsVector.size(); j++) {
	// if (pathsVector.get(j).travelCost == i)
	// pathsWithCurrentCost.add(pathsVector.get(j));
	// }
	//
	// for (int j = 0; j < pathsWithCurrentCost.size(); j++) {
	// Vector<Square> squaresInThisPath = pathsWithCurrentCost.get(j).squares;
	// calculatePathToAllSquares2(squares, Direction.UP, squaresInThisPath, i);
	// calculatePathToAllSquares2(squares, Direction.RIGHT, squaresInThisPath,
	// i);
	// calculatePathToAllSquares2(squares, Direction.DOWN, squaresInThisPath,
	// i);
	// calculatePathToAllSquares2(squares, Direction.LEFT, squaresInThisPath,
	// i);
	//
	// }
	// }
	// }
	//
	// public void calculatePathToAllSquares2(Square[][] squares, Direction
	// direction, Vector<Square> squaresInThisPath,
	// int pathCost) {
	//
	// Square newSquare = null;
	//
	// Square parentSquare = squaresInThisPath.get(squaresInThisPath.size() -
	// 1);
	//
	// if (direction == Direction.UP) {
	// if (parentSquare.yInGrid - 1 >= 0) {
	// newSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid - 1];
	// }
	// } else if (direction == Direction.RIGHT) {
	// if (parentSquare.xInGrid + 1 < squares.length) {
	// newSquare = squares[parentSquare.xInGrid + 1][parentSquare.yInGrid];
	// }
	// } else if (direction == Direction.DOWN) {
	//
	// if (parentSquare.yInGrid + 1 < squares[0].length) {
	// newSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid + 1];
	// }
	// } else if (direction == Direction.LEFT) {
	// if (parentSquare.xInGrid - 1 >= 0) {
	// newSquare = squares[parentSquare.xInGrid - 1][parentSquare.yInGrid];
	// }
	// }
	//
	// if (newSquare != null && newSquare.inventory.isPassable(this) &&
	// !squaresInThisPath.contains(newSquare)
	// && !paths.containsKey(newSquare)) {
	// Vector<Square> newPathSquares = (Vector<Square>)
	// squaresInThisPath.clone();
	// newPathSquares.add(newSquare);
	// int newDistance = pathCost + parentSquare.travelCost;
	// newSquare.walkingDistanceToSquare = newDistance;
	// if (newDistance > highestPathCostSeen)
	// highestPathCostSeen = newDistance;
	// Path newPath = new Path(newPathSquares, newDistance);
	// paths.put(newSquare, newPath);
	//
	// // THEYRE MOCING ON TO THE SAME SQUARE
	//
	// }
	// }
}
