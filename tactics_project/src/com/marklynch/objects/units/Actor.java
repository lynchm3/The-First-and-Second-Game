package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.Quest;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Owner;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionSpot;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.Button;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class Actor extends ActorTemplate implements Owner {

	public final static String[] editableAttributes = { "name", "imageTexture", "faction", "strength", "dexterity",
			"intelligence", "endurance", "totalHealth", "remainingHealth", "travelDistance", "inventory",
			"showInventory", "fitsInInventory", "canContainOtherObjects" };

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

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

	public transient Bed bed;
	public String bedGUID = null;

	public transient Faction faction;
	public String factionGUID = null;

	public Weapon equippedWeapon = null;
	public String equippedWeaponGUID = null;

	public transient Group group;
	private transient ArrayList<Actor> attackers;

	public transient Conversation conversation;

	public boolean wasSwappedWithThisTurn = false;

	public int swapCooldown = 0;

	protected transient int highestPathCostSeen = 0;

	public ArrayList<Square> squaresVisibleToThisCharacter = new ArrayList<Square>();

	public Actor(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			Faction faction, float anchorX, float anchorY) {

		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio);

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

		ArrayList<Weapon> weapons = getWeaponsInInventory();
		//
		// for (Weapon weapon : weapons) {
		// weaponButtons.add(new WeaponButton(0, 0, 50, 50,
		// weapon.imageTexturePath, weapon.imageTexturePath, weapon));
		// }

		if (weapons.size() > 0 && weapons.get(0) != null) {
			equippedWeapon = weapons.get(0);
			equippedWeaponGUID = weapons.get(0).guid;
		}

		attackers = new ArrayList<Actor>();

		this.faction = faction;
		if (this.faction != null) {
			factionGUID = this.faction.guid;
			this.faction.actors.add(this);
		}

		this.anchorX = anchorX;
		this.anchorY = anchorY;
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
					this.equippedWeapon = weapon;
				}
			}
		}
	}

	public void calculateReachableSquares(Square[][] squares) {

		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {

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
			for (int j = 0; j < squares[0].length; j++) {
				squares[i][j].weaponsThatCanAttack.clear();
			}
		}

		for (Weapon weapon : getWeaponsInInventory()) {
			weapon.calculateAttackableSquares(squares);
		}
	}

	public static void calculateReachableSquares() {
		Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
		Game.level.activeActor.calculateReachableSquares(Game.level.squares);
		Game.level.activeActor.calculateAttackableSquares(Game.level.squares);
	}

	public void calculateVisibleSquares() {

		for (int x = 0; x < Game.level.squares.length; x++) {
			for (int y = 0; y < Game.level.squares[0].length; y++) {
				Game.level.squares[x][y].visibleToSelectedCharacter = false;
				if (this == Game.level.player) {
					Game.level.squares[x][y].visibleToPlayer = false;
				}
			}
		}

		squaresVisibleToThisCharacter.clear();

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

		if (!squaresVisibleToThisCharacter.contains(Game.level.squares[x][y])) {
			Game.level.squares[x][y].visibleToSelectedCharacter = true;
			squaresVisibleToThisCharacter.add(Game.level.squares[x][y]);
			if (this == Game.level.player) {
				Game.level.squares[x][y].visibleToPlayer = true;
				Game.level.squares[x][y].seenByPlayer = true;
				// Seen Building
				if (Game.level.squares[x][y].structureSquareIsIn != null
						&& Game.level.squares[x][y].structureSquareIsIn.seenByPlayer == false) {
					Game.level.squares[x][y].structureSquareIsIn.seenByPlayer = true;
					new ActionSpot(this, Game.level.squares[x][y].structureSquareIsIn).perform();
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
				equippedWeapon = weapon;
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
			equippedWeapon = null;
			equippedWeaponGUID = null;
		} else if (potentialWeaponsToEquip.size() == 1) {
			equippedWeapon = potentialWeaponsToEquip.get(0);
			equippedWeaponGUID = potentialWeaponsToEquip.get(0).guid;
		} else {
			ArrayList<Fight> fights = new ArrayList<Fight>();
			for (Weapon weapon : potentialWeaponsToEquip) {
				Fight fight = new Fight(this, weapon, target, targetsWeapon, range);
				fights.add(fight);
			}
			fights.sort(new Fight.FightComparator());
			equippedWeapon = fights.get(0).attackerWeapon;
			equippedWeaponGUID = fights.get(0).attackerWeapon.guid;
		}
	}

	@Override
	public boolean checkIfDestroyed() {
		if (remainingHealth <= 0) {
			// Remove from draw/update
			this.squareGameObjectIsOn.inventory.remove(this);
			// this.faction.actors.remove(this);

			// add a carcass
			GameObject body;
			if (this instanceof WildAnimal)
				body = new Carcass(this.name + " carcass", 5, "carcass.png", this.squareGameObjectIsOn, new Inventory(),
						false, true, false, true, false, false, 0.5f, 0.5f);
			else
				body = new Corpse(this.name + " corpse", 5, "carcass.png", this.squareGameObjectIsOn, new Inventory(),
						false, true, false, true, false, false, 0.5f, 0.5f);

			this.giveAllToTarget(null, body);
			// this.squareGameObjectIsOn.inventory.add(body);
			//
			// if (!Game.level.inanimateObjectsOnGround.contains(body))
			// Game.level.inanimateObjectsOnGround.add(body);

			// screamAudio.playAsSoundEffect(1.0f, 1.0f, false);
			return true;
		}
		return false;
	}

	@Override
	public void draw1() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
			return;

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

		if (this.remainingHealth <= 0)
			return;
		// Draw health

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

		if (equippedWeapon != null) {

			int weaponPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX + anchorX - equippedWeapon.anchorX);
			int weaponPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY + anchorY - equippedWeapon.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.equippedWeapon.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionXInPixels + equippedWeapon.width, weaponPositionYInPixels,
					weaponPositionYInPixels + equippedWeapon.height);

			// int weaponPositionXInPixels = (int)
			// (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
			// + drawOffsetX + ((int) Game.HALF_SQUARE_WIDTH -
			// equippedWeapon.halfWidth));
			// int weaponPositionYInPixels = (int)
			// (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
			// + drawOffsetY + ((int) Game.HALF_SQUARE_HEIGHT -
			// equippedWeapon.halfHeight));
			// float alpha = 1.0f;
			// TextureUtils.drawTexture(this.equippedWeapon.imageTexture, alpha,
			// weaponPositionXInPixels,
			// weaponPositionXInPixels + equippedWeapon.width,
			// weaponPositionYInPixels,
			// weaponPositionYInPixels + equippedWeapon.height);
		}

		// draw anchor
		// QuadUtils.drawQuad(Color.WHITE,
		// this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH +
		// drawOffsetX + anchorX - 5f,
		// this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH +
		// drawOffsetX + anchorX + 5f,
		// this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT +
		// drawOffsetY + anchorY - 5f,
		// this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT +
		// drawOffsetY + anchorY + 5f);

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

		if (this.squareGameObjectIsOn.visibleToPlayer == false)
			return;

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
		Game.level.removeWalkingHighlight();
		Game.level.removeWeaponsThatCanAttackHighlight();
	}

	public void weaponButtonClicked(Weapon weapon) {
		this.equippedWeapon = weapon;
		equippedWeaponGUID = this.equippedWeapon.guid;
	}

	@Override
	public Actor makeCopy(Square square, Faction faction) {

		Actor actor = new Actor(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, null, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, faction, anchorX, anchorY);
		return actor;
	}

	@Override
	public void update(int delta) {

		calculateVisibleSquares();

		// Remove dead attackers from attackers list
		ArrayList<Actor> attackersToRemoveFromList = new ArrayList<Actor>();
		for (Actor actor : attackers) {
			if (actor.remainingHealth <= 0) {
				attackersToRemoveFromList.add(actor);
			}
		}
		for (Actor actor : attackersToRemoveFromList) {
			attackers.remove(actor);
		}

		if (this.remainingHealth > 0) {
			Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
			this.aiRoutine.update();
		}
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

	public void giveAllToTarget(Class clazz, GameObject gameObject) {
		ArrayList<GameObject> gameObjectsToSell = (ArrayList<GameObject>) this.inventory.getGameObjects().clone();
		for (GameObject gameObjectToSell : gameObjectsToSell) {
			if (clazz == null || clazz.isInstance(gameObjectToSell)) {
				if (squareGameObjectIsOn.visibleToPlayer)
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { this, " gave ", gameObjectToSell, " to ", gameObject }));
				this.inventory.remove(gameObjectToSell);
				gameObject.inventory.add(gameObjectToSell);
			}
		}

	}

	public void addAttackerIfVisible(Actor potentialAttacker) {

		if (!this.attackers.contains(potentialAttacker)
				&& this.checkVisibilityBetweenTwoPoints(squareGameObjectIsOn.xInGrid + 0.5d,
						squareGameObjectIsOn.yInGrid + 0.5d, potentialAttacker.squareGameObjectIsOn.xInGrid + 0.5d,
						potentialAttacker.squareGameObjectIsOn.yInGrid + 0.5d)) {
			this.attackers.add(potentialAttacker);
		}
	}

	public void addAttackerAndManageAttackerReferences(GameObject gameObject) {

		// Manage attackers
		if (!(gameObject instanceof Actor))
			return;

		Actor attacker = (Actor) gameObject;

		if (this.group != null && attacker.group != null) {

			for (int i = 0; i < attacker.group.size(); i++) {
				this.group.addAttacker(attacker.group.getMember(i));
			}

			for (int i = 0; i < this.group.size(); i++) {
				attacker.group.addAttacker(this.group.getMember(i));
			}
			attacker.addAttackerIfVisible(this);
			this.addAttackerIfVisible(attacker);

		} else if (this.group != null && attacker.group == null) {

			for (int i = 0; i < this.group.size(); i++) {
				attacker.addAttackerIfVisible(this.group.getMember(i));
			}

			this.group.addAttacker(attacker);
			this.addAttackerIfVisible(attacker);

		} else if (this.group == null && attacker.group != null) {

			for (int i = 0; i < attacker.group.size(); i++) {
				this.addAttackerIfVisible(attacker.group.getMember(i));
			}
			attacker.group.addAttacker(this);
			attacker.addAttackerIfVisible(this);

		} else if (this.group == null && attacker.group == null) {
			attacker.addAttackerIfVisible(this);
			this.addAttackerIfVisible(attacker);

		}

	}

	public void addAttackerAndmanageAttackerReferencesForNearbyAllies(GameObject attacker) {
		for (Actor ally : this.faction.actors) {
			if (ally != this && (this.straightLineDistanceTo(ally.squareGameObjectIsOn) < 10
					|| ally.straightLineDistanceTo(attacker.squareGameObjectIsOn) < 10)) {
				ally.addAttackerAndManageAttackerReferences(attacker);
			}
		}
	}

	public void addAttackerAndmanageAttackerReferencesForNearbyEnemies(GameObject attackerGameObject) {
		if (attackerGameObject instanceof Actor) {
			Actor attacker = (Actor) attackerGameObject;
			for (Actor enemy : attacker.faction.actors) {
				if (enemy != attacker && (this.straightLineDistanceTo(enemy.squareGameObjectIsOn) < 10
						|| enemy.straightLineDistanceTo(attacker.squareGameObjectIsOn) < 10)) {
					enemy.addAttackerAndManageAttackerReferences(this);
				}
			}
		}
	}

	@Override
	public Action getDefaultActionInWorld(Actor performer) {
		if (this == Game.level.player) {
			return null;
		} else if (performer.attackers.contains(this)) {
			if (Game.level.activeActor != null && Game.level.activeActor.equippedWeapon != null
					&& Game.level.activeActor.equippedWeapon
							.hasRange(Game.level.activeActor.straightLineDistanceTo(this.squareGameObjectIsOn))) {
				return new ActionAttack(performer, this);
			}
		} else {
			return new ActionTalk(performer, this);
		}
		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsInWorld(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();
		if (this != Game.level.player) {
			// Talk
			actions.add(new ActionTalk(performer, this));
			// Inherited from object (attack...)
			actions.addAll(super.getAllActionsInWorld(performer));
			// Inherited from squre (move/swap squares)
			actions.addAll(squareGameObjectIsOn.getAllActionsInWorld(performer));
		}

		return actions;
	}

	public boolean hasAttackers() {
		return attackers.size() > 0;
	}

	public ArrayList<Actor> getAttackers() {
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

	public void calculatePathToAllSquares(Square[][] squares) {

		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				squares[i][j].walkingDistanceToSquare = Integer.MAX_VALUE;
			}
		}

		highestPathCostSeen = 0;
		paths.clear();
		Square currentSquare = squareGameObjectIsOn;
		currentSquare.walkingDistanceToSquare = 0;

		Vector<Square> startPath = new Vector<Square>();
		startPath.add(currentSquare);
		paths.put(currentSquare, new Path((Vector<Square>) startPath.clone(), 0));

		for (int i = 0; i <= highestPathCostSeen; i++) {
			// get all paths with that cost
			Vector<Path> pathsWithCurrentCost = new Vector<Path>();
			Vector<Path> pathsVector = new Vector<Path>();
			for (Path path : paths.values()) {
				pathsVector.add(path);
			}
			for (int j = 0; j < pathsVector.size(); j++) {
				if (pathsVector.get(j).travelCost == i)
					pathsWithCurrentCost.add(pathsVector.get(j));
			}

			for (int j = 0; j < pathsWithCurrentCost.size(); j++) {
				Vector<Square> squaresInThisPath = pathsWithCurrentCost.get(j).squares;
				calculatePathToAllSquares2(squares, Direction.UP, squaresInThisPath, i);
				calculatePathToAllSquares2(squares, Direction.RIGHT, squaresInThisPath, i);
				calculatePathToAllSquares2(squares, Direction.DOWN, squaresInThisPath, i);
				calculatePathToAllSquares2(squares, Direction.LEFT, squaresInThisPath, i);

			}
		}
	}

	public void calculatePathToAllSquares2(Square[][] squares, Direction direction, Vector<Square> squaresInThisPath,
			int pathCost) {

		Square newSquare = null;

		Square parentSquare = squaresInThisPath.get(squaresInThisPath.size() - 1);

		if (direction == Direction.UP) {
			if (parentSquare.yInGrid - 1 >= 0) {
				newSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid - 1];
			}
		} else if (direction == Direction.RIGHT) {
			if (parentSquare.xInGrid + 1 < squares.length) {
				newSquare = squares[parentSquare.xInGrid + 1][parentSquare.yInGrid];
			}
		} else if (direction == Direction.DOWN) {

			if (parentSquare.yInGrid + 1 < squares[0].length) {
				newSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid + 1];
			}
		} else if (direction == Direction.LEFT) {
			if (parentSquare.xInGrid - 1 >= 0) {
				newSquare = squares[parentSquare.xInGrid - 1][parentSquare.yInGrid];
			}
		}

		if (newSquare != null && newSquare.inventory.isPassable(this) && !squaresInThisPath.contains(newSquare)
				&& !paths.containsKey(newSquare)) {
			Vector<Square> newPathSquares = (Vector<Square>) squaresInThisPath.clone();
			newPathSquares.add(newSquare);
			int newDistance = pathCost + parentSquare.travelCost;
			newSquare.walkingDistanceToSquare = newDistance;
			if (newDistance > highestPathCostSeen)
				highestPathCostSeen = newDistance;
			Path newPath = new Path(newPathSquares, newDistance);
			paths.put(newSquare, newPath);

			// THEYRE MOCING ON TO THE SAME SQUARE

		}
	}
}
