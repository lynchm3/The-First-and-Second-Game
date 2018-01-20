package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AILine;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.ai.utils.AStarNode;
import com.marklynch.ai.utils.AStarSearch;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Key;
import com.marklynch.objects.Openable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionLift;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionPet;
import com.marklynch.objects.actions.ActionPin;
import com.marklynch.objects.actions.ActionPourContainerInInventory;
import com.marklynch.objects.actions.ActionSellItems;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionStopPeeking;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.actions.ActionWait;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Tool;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.Button;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Actor extends GameObject {

	public final static String[] editableAttributes = { "name", "imageTexture", "faction", "strength", "dexterity",
			"intelligence", "endurance", "totalHealth", "remainingHealth", "travelDistance", "inventory",
			"showInventory", "fitsInInventory", "canContainOtherObjects" };
	private static Texture SPEEECH_BUBBLE_TEXTURE = null;
	private static Texture THOUGHT_BUBBLE_TEXTURE = null;

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	public int strength;
	public int dexterity;
	public int intelligence;
	public int endurance;
	public String title = "";
	public int actorLevel = 1;
	public int travelDistance = 1;
	public int sight = 10;

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

	public transient GameObject bed;
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

	public boolean canOpenDoors = false;
	public boolean canEquipWeapons = false;

	public boolean wasSwappedWithThisTurn = false;

	public int swapCooldown = 0;

	protected transient int highestPathCostSeen = 0;

	public HashMap<GameObject, Investigation> investigationsMap = new HashMap<GameObject, Investigation>();

	public ArrayList<Action> actionsPerformedThisTurn = new ArrayList<Action>();
	public ArrayList<Crime> crimesPerformedThisTurn = new ArrayList<Crime>();
	public ArrayList<Crime> crimesPerformedInLifetime = new ArrayList<Crime>();

	public Texture standingTexture = null;
	public Texture stepLeftTexture = null;
	public Texture stepRightTexture = null;
	public Texture currentStepTexture = null;
	public Texture hairImageTexture = null;
	public Texture thoughtBubbleImageTexture = null;
	public Square lastSquare = null;
	public GameObject peekingThrough = null;
	public Square peekSquare = null;

	public int timePerStep = 100;
	public int thisStepTime = timePerStep;

	public ArrayList<Crime> crimesWitnessedUnresolved = new ArrayList<Crime>();
	public ArrayList<Actor> knownCriminals = new ArrayList<Actor>();
	public Map<Actor, ArrayList<Crime>> mapActorToCrimesWitnessed = new HashMap<Actor, ArrayList<Crime>>();
	public Map<Actor, Integer> accumulatedCrimeSeverityWitnessed = new HashMap<Actor, Integer>();
	public Map<Actor, Integer> accumulatedCrimeSeverityUnresolved = new HashMap<Actor, Integer>();
	public int highestAccumulatedUnresolvedCrimeSeverity = 0;
	public Actor criminalWithHighestAccumulatedUnresolvedCrimeSeverity = null;

	public AILine aiLine;

	public ArrayList<Door> doors = new ArrayList<Door>();
	public boolean followersShouldFollow = false;

	public boolean sleeping = false;

	public AIPath path;

	public ArrayList<Power> powers = new ArrayList<Power>();

	public Area area;

	public int thoughtsOnPlayer = 0;

	public Actor() {

		super();

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		decorative = false;
		attackable = true;
	}

	public void init(int gold, GameObject[] mustHaves, GameObject[] mightHaves) {
		super.init();

		if (bed != null)
			this.bedGUID = bed.guid;
		if (bed != null)
			bed.owner = this;

		ArrayList<Weapon> weapons = getWeaponsInInventory();
		if (weapons.size() > 0 && weapons.get(0) != null) {
			equipped = weapons.get(0);
			equippedWeaponGUID = weapons.get(0).guid;
		}

		if (this.faction != null) {
			factionGUID = this.faction.guid;
			this.faction.actors.add(this);
		}

		if (gold > 0)
			inventory.add(Templates.GOLD.makeCopy(null, this, gold));

		this.lastSquare = this.squareGameObjectIsOn;

		for (GameObject gameObject : mustHaves) {
			this.inventory.add(gameObject);
		}

		for (GameObject gameObject : mightHaves) {
			if (Math.random() > 0.8d)
				this.inventory.add(gameObject);
		}

		for (GameObject gameObject : inventory.getGameObjects()) {
			gameObject.owner = this;
		}
	}

	public static void loadStaticImages() {
		SPEEECH_BUBBLE_TEXTURE = ResourceUtils.getGlobalImage("speech_bubble.png");
		THOUGHT_BUBBLE_TEXTURE = ResourceUtils.getGlobalImage("thought_bubble.png");
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
					this.bed = gameObject;
				}
			}
		}

		// equippedWeapon
		if (equippedWeaponGUID != null) {
			for (Weapon weapon : this.getWeaponsInInventory()) {
				if (equippedWeaponGUID.equals(weapon.guid)) {
					this.equip(weapon);
				}
			}
		}
	}

	public AIPath getPathTo(Square target) {

		// if (this instanceof Hunter && this == this.group.getLeader()) {
		// System.out.println(this + " getPathTo " + target);
		// System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		// // Thread.currentThread().getStackTrace();
		// }

		if (target == null) {
			return null;
		}

		// if (this instanceof Player && !target.seenByPlayer) {
		//
		// } else if (target.inventory.canBeMovedTo() == false) {
		// return null;
		// }

		// ASTARSEACH.FINDPATH
		int maxPathSize = 100;
		if (this instanceof Player) {
			maxPathSize = 1000;
		}

		LinkedList<AStarNode> aStarNodesPath = new AStarSearch().findPath(this, this.squareGameObjectIsOn, target,
				maxPathSize);

		if (aStarNodesPath != null) {
			Vector<Square> squarePath = new Vector<Square>();

			for (AStarNode aStarNode : aStarNodesPath) {
				squarePath.add((Square) aStarNode);
			}

			boolean completePath = false;
			if (squarePath.size() > 0 && squarePath.lastElement() == target) {
				completePath = true;
			}

			AIPath path = new AIPath(squarePath, squarePath.size(), completePath);
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

	@Override
	public boolean hasRange(int weaponDistance) {
		if (weaponDistance == 1)
			return true;

		if (!canEquipWeapons) {
			if (weaponDistance > 1)
				return false;
			return true;
		}

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

		if (!canEquipWeapons)
			return;

		int range = this.straightLineDistanceTo(target.squareGameObjectIsOn);
		for (Weapon weapon : getWeaponsInInventory()) {
			if (range >= weapon.getEffectiveMinRange() && range <= weapon.getEffectiveMaxRange()) {
				equip(weapon);
				equippedWeaponGUID = weapon.guid;
			}
		}
	}

	public void equipBestWeaponForCounter(GameObject target, Weapon targetsWeapon) {

		if (!canEquipWeapons)
			return;

		ArrayList<Weapon> potentialWeaponsToEquip = new ArrayList<Weapon>();

		int range = this.straightLineDistanceTo(target.squareGameObjectIsOn);
		for (Weapon weapon : getWeaponsInInventory()) {
			if (range >= weapon.getEffectiveMinRange() && range <= weapon.getEffectiveMaxRange()) {
				potentialWeaponsToEquip.add(weapon);
			}
		}

		if (potentialWeaponsToEquip.size() == 0) {
			equip(null);
			equippedWeaponGUID = null;
		} else if (potentialWeaponsToEquip.size() == 1) {
			equip(potentialWeaponsToEquip.get(0));
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
	public boolean checkIfDestroyed(Object attacker, Action action) {
		if (remainingHealth <= 0) {
			super.checkIfDestroyed(attacker, action);
			this.faction.checkIfDestroyed();
			return true;
		}
		return false;
	}

	float healthWidthInPixels = Game.SQUARE_WIDTH / 20;
	float healthHeightInPixels = Game.SQUARE_HEIGHT;

	@Override
	public void draw1() {

		// Don't draw if dead
		if (this.remainingHealth <= 0)
			return;

		// Don't draw if hiding
		if (hiding && this != Game.level.player)
			return;

		if (!Game.fullVisiblity && this != Game.level.player) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}
		// Draw health

		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(animation.offsetX, animation.offsetY));
		Game.activeBatch.updateUniforms();

		// if (remainingHealth != totalHealth) {

		// draw sidebar on square
		float healthPercentage = (remainingHealth) / (totalHealth);
		float healthBarHeightInPixels = Game.SQUARE_HEIGHT * healthPercentage;
		float healthXInPixels = this.squareGameObjectIsOn.xInGridPixels;
		float healthYInPixels = this.squareGameObjectIsOn.yInGridPixels;

		Color color = Color.YELLOW;
		if (thoughtsOnPlayer > 50) {
			color = Color.GREEN;
		} else if (thoughtsOnPlayer < -50) {
			color = Color.RED;
		}

		// White bit under health bar
		QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), healthXInPixels + 1, healthYInPixels + 1,
				healthXInPixels + healthWidthInPixels - 1, healthYInPixels + healthHeightInPixels - 1);

		// Colored health bar
		QuadUtils.drawQuad(color, healthXInPixels + 1, healthYInPixels + 1, healthXInPixels + healthWidthInPixels - 1,
				healthYInPixels + healthBarHeightInPixels - 1);
		// }

		super.draw1();

		// hand anchor
		QuadUtils.drawQuad(Color.BLACK, this.squareGameObjectIsOn.xInGridPixels + handAnchorX - 3,
				this.squareGameObjectIsOn.yInGridPixels + handAnchorY - 3,
				this.squareGameObjectIsOn.xInGridPixels + handAnchorX + 3,
				this.squareGameObjectIsOn.yInGridPixels + handAnchorY + 3);

		// weapon
		if (equipped != null && !sleeping) {

			int weaponPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ drawOffsetX * Game.SQUARE_WIDTH + handAnchorX - equipped.anchorX);
			int weaponPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ drawOffsetY * Game.SQUARE_HEIGHT + handAnchorY - equipped.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.equipped.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionYInPixels, weaponPositionXInPixels + equipped.width,
					weaponPositionYInPixels + equipped.height);
		}

		if (helmet != null && !sleeping) {

			int helmetPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ drawOffsetX * Game.SQUARE_WIDTH + headAnchorX - helmet.anchorX);
			int helmetPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ drawOffsetY * Game.SQUARE_HEIGHT + headAnchorY - helmet.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.helmet.imageTexture, alpha, helmetPositionXInPixels, helmetPositionYInPixels,
					helmetPositionXInPixels + helmet.width, helmetPositionYInPixels + helmet.height);
		} else if (hairImageTexture != null) {
			int bodyArmorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ drawOffsetX * Game.SQUARE_WIDTH + bodyAnchorX - 0);
			int bodyArmorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ drawOffsetY * Game.SQUARE_HEIGHT + bodyAnchorY - 0);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.hairImageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + hairImageTexture.getWidth(),
					bodyArmorPositionYInPixels + hairImageTexture.getHeight());
		}

		if (bodyArmor != null && !sleeping) {

			int bodyArmorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ drawOffsetX * Game.SQUARE_WIDTH + bodyAnchorX - bodyArmor.anchorX);
			int bodyArmorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ drawOffsetY * Game.SQUARE_HEIGHT + bodyAnchorY - bodyArmor.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.bodyArmor.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + bodyArmor.width,
					bodyArmorPositionYInPixels + bodyArmor.height);
		}

		if (legArmor != null && !sleeping) {

			int legArmorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ drawOffsetX * Game.SQUARE_WIDTH + legsAnchorX - legArmor.anchorX);
			int legArmorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ drawOffsetY * Game.SQUARE_WIDTH + legsAnchorY - legArmor.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.legArmor.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + legArmor.width,
					legArmorPositionYInPixels + legArmor.height);
		}

		float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
		float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels;

		// body shoulder coords, arm shoulder coords
		float armPositionXInPixels = actorPositionXInPixels + 65 - 11;
		float armPositionYInPixels = actorPositionYInPixels + 41 - 13;

		float rotateXInPixels = actorPositionXInPixels + 65;
		float rotateYInPixels = actorPositionYInPixels + 41;

		float equippedWeaponPositionXInPixels = armPositionXInPixels + 70 - 20;
		float equippedWeaponPositionYInPixels = armPositionYInPixels + 110 - 86;

		float alpha = 1.0f;

		if (hasAttackedThisTurn == true && this.faction != null && Game.level.currentFactionMoving == this.faction) {
			alpha = 0.5f;
		}

		Game.activeBatch.flush();
		view.translate(new Vector2f(-animation.offsetX, -animation.offsetY));
		Game.activeBatch.updateUniforms();

	}

	@Override
	public void draw2() {

		// if (this.squareGameObjectIsOn.visibleToPlayer == false)
		// return;

		if (this.remainingHealth <= 0)
			return;

		if (!Game.fullVisiblity && this != Game.level.player) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(animation.offsetX, animation.offsetY));
		Game.activeBatch.updateUniforms();

		super.draw2();

		// Draw activity text
		// if (activityDescription != null && activityDescription.length() > 0)
		// {
		// float activityX1 = this.squareGameObjectIsOn.xInGridPixels;
		// float activityX2 = this.squareGameObjectIsOn.xInGridPixels +
		// Game.font.getWidth(activityDescription);
		// float activityY1 = this.squareGameObjectIsOn.yInGridPixels - 20;
		// float activityY2 = this.squareGameObjectIsOn.yInGridPixels;
		// QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 0.5f), activityX1,
		// activityY1, activityX2, activityY2);
		// TextUtils.printTextWithImages(activityX1, activityY1,
		// Integer.MAX_VALUE, false, null,
		// new Object[] { activityDescription });
		// }

		// Draw mini dialogue
		if (miniDialogue != null && miniDialogue.length() > 0) {

			int expressionBubbleWidth = 64;
			int expressionBubbleHeight = 64;

			float expressionBubblePositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float expressionBubblePositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetY - 64;
			float alphaBubble = 1.0f;

			// TextureUtils.skipNormals = true;

			// if (!this.squareGameObjectIsOn.visibleToPlayer)
			alphaBubble = 0.5f;
			TextureUtils.drawTexture(Actor.SPEEECH_BUBBLE_TEXTURE, alphaBubble, expressionBubblePositionXInPixels,
					expressionBubblePositionYInPixels, expressionBubblePositionXInPixels + expressionBubbleWidth,
					expressionBubblePositionYInPixels + expressionBubbleHeight);

			TextUtils.printTextWithImages(expressionBubblePositionXInPixels + 4, expressionBubblePositionYInPixels + 38,
					Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor(miniDialogue, Color.BLACK) });
		} else if (thoughtBubbleImageTexture != null) {

			int expressionBubbleWidth = 64;
			int expressionBubbleHeight = 64;

			float expressionBubblePositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float expressionBubblePositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetY - 64;
			float alphaBubble = 1.0f;

			// TextureUtils.skipNormals = true;

			// if (!this.squareGameObjectIsOn.visibleToPlayer)
			alphaBubble = 0.5f;
			TextureUtils.drawTexture(Actor.THOUGHT_BUBBLE_TEXTURE, alphaBubble, expressionBubblePositionXInPixels,
					expressionBubblePositionYInPixels, expressionBubblePositionXInPixels + expressionBubbleWidth,
					expressionBubblePositionYInPixels + expressionBubbleHeight);

			int expressionWidth = 32;
			int expressionHeight = 32;
			int realTextureWidth = thoughtBubbleImageTexture.getWidth();
			int realTextureHeight = thoughtBubbleImageTexture.getHeight();
			if (realTextureWidth >= realTextureHeight) {// knife
				expressionHeight = 32 * realTextureHeight / realTextureWidth;
			} else {
				expressionWidth = 32 * realTextureWidth / realTextureHeight;
			}

			float expressionPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels + (32 - expressionWidth / 2);
			float expressionPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetY
					- expressionHeight;
			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			TextureUtils.drawTexture(thoughtBubbleImageTexture, alpha, expressionPositionXInPixels,
					expressionPositionYInPixels, expressionPositionXInPixels + expressionWidth,
					expressionPositionYInPixels + expressionHeight);
			// TextureUtils.skipNormals = false;
		}

		Game.activeBatch.flush();
		view.translate(new Vector2f(-animation.offsetX, -animation.offsetY));
		Game.activeBatch.updateUniforms();

		if (Game.showAILines) {
			if (aiLine != null) {
				aiLine.draw2();
			}
		}

	}

	@Override
	public void drawUI() {

		if (this.squareGameObjectIsOn == null || this.squareGameObjectIsOn.visibleToPlayer == false)
			return;

		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(animation.offsetX, animation.offsetY));
		Game.activeBatch.updateUniforms();

		super.drawUI();

		Game.activeBatch.flush();
		view.translate(new Vector2f(-animation.offsetX, -animation.offsetY));
		Game.activeBatch.updateUniforms();
	}

	public Vector<Float> calculateIdealDistanceFromTargetToAttack(GameObject target) {

		Vector<Float> idealDistances = new Vector<Float>();
		if (!canEquipWeapons) {
			idealDistances.add(1f);
			return idealDistances;
		}

		Vector<Fight> fights = new Vector<Fight>();
		for (Weapon weapon : getWeaponsInInventory()) {
			for (float range = weapon.getEffectiveMinRange(); range <= weapon.getEffectiveMaxRange(); range++) {
				Fight fight = new Fight(this, weapon, target, target.bestCounterWeapon(this, weapon, range), range);
				fights.add(fight);
			}
		}

		fights.sort(new Fight.FightComparator());

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
		this.equip(weapon);
		equippedWeaponGUID = this.equipped.guid;
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

			// clean up witnessed crimes list
			manageWitnessedCrimes();

			for (GameObject gameObject : gameObjectsToRemoveFromList) {
				attackers.remove(gameObject);
			}

			// Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
			if (aiRoutine != null)
				this.aiRoutine.update();
		}

		// If hiding in a place get the effects
		if (hidingPlace != null) {
			for (Effect effect : hidingPlace.effectsFromInteracting) {
				addEffect(effect.makeCopy(hidingPlace, this));
			}
		}
		super.update(delta);
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
			return new ActionWait(performer, performer.squareGameObjectIsOn);
		} else if (performer.attackers.contains(this)) {
			return new ActionAttack(performer, this);
		} else if (this instanceof RockGolem) {
			RockGolem rockGolem = (RockGolem) this;
			if (!rockGolem.awake) {
				return new ActionLift(performer, this);
			} else {
				return new ActionAttack(performer, this);
			}
		} else if (this instanceof AggressiveWildAnimal) {
			return new ActionAttack(performer, this);
		} else if (this instanceof Animal) {
			if (this.getConversation() != null) {
				return new ActionTalk(performer, this);
			} else {
				return new ActionPet(performer, this);
			}
		} else if (this instanceof Monster) {
			return new ActionAttack(performer, this);
		}
		return new ActionTalk(performer, this);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		if (this == Game.level.player) {
			if (Game.level.player.peekingThrough != null) {
				return new ActionStopPeeking(performer);
			} else {
				return new ActionWait(performer, performer.squareGameObjectIsOn);
			}
		} else {
			return new ActionMove(performer, this.squareGameObjectIsOn, true);
		}
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		if (this == Game.level.player) {
			if (Game.level.player.peekingThrough != null) {
				actions.add(new ActionStopPeeking(performer));
			}

			actions.add(new ActionWait(performer, performer.squareGameObjectIsOn));

			// self action
			if (equipped instanceof Tool) {
				Tool tool = (Tool) equipped;
				Action utilityAction = tool.getUtilityAction(performer);
				if (utilityAction != null) {
					actions.add(utilityAction);
				}
			}

			actions.add(new ActionPourContainerInInventory(performer, performer));

			if (hiding) {
				actions.add(new ActionStopHiding(this, this.hidingPlace));
			} else {
				HidingPlace hidingPlaceStandingOn = (HidingPlace) this.squareGameObjectIsOn.inventory
						.getGameObjectOfClass(HidingPlace.class);
				if (hidingPlaceStandingOn != null)
					actions.add(new ActionHide(this, hidingPlaceStandingOn));
			}
			actions.add(new ActionPin(performer, this));
		} else {
			// Talk
			if (this.getConversation() != null)
				actions.add(new ActionTalk(performer, this));
			if (this instanceof Animal) {
				actions.add(new ActionPet(performer, this));
			}
			actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		}

		actions.add(new ActionMove(performer, this.squareGameObjectIsOn, true));

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

		if (this.knownCriminals.contains(Game.level.player)) {
			return null;
		}

		if (quest != null) {
			Conversation questConversation = null;
			questConversation = quest.getConversation(this);
			if (questConversation != null) {
				questConversation.originalConversationTarget = this;
				return questConversation;
			}

		}

		if (!(this instanceof NonHuman)) {
			Conversation conversation = Conversation.createConversation("Hello!", this);
			conversation.originalConversationTarget = this;
			return conversation;
		}

		return null;
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

		if (canSeeGameObjectFromSpecificSquare(this.squareGameObjectIsOn, gameObject))
			return true;

		if (group != null) {
			for (Actor actor : group.getMembers()) {
				if (actor == this)
					continue;
				if (actor.canSeeGameObjectFromSpecificSquare(this.squareGameObjectIsOn, gameObject)) {
					return true;
				}

			}
		}

		return false;
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
	public void attackedBy(Object attacker, Action action) {

		super.attackedBy(attacker, action);

		sleeping = false;
		removeHidingPlacesFromAttackersList();// prioritize actors

		// update attackers list
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

			// Add investigation of where you were attacked from in case we lose
			// sight
			this.addInvestigation(actor, actor.squareGameObjectIsOn, Investigation.INVESTIGATION_PRIORITY_ATTACKED);
		}
	}

	public void removeHidingPlacesFromAttackersList() {

		ArrayList<GameObject> hidingPlacesToRemove = new ArrayList<GameObject>();
		for (GameObject attacker : this.getAttackers()) {
			if (attacker instanceof HidingPlace) {
				hidingPlacesToRemove.add(attacker);
			}
		}
		this.attackers.removeAll(hidingPlacesToRemove);
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

	public void equip(GameObject gameObject) {
		if (canEquipWeapons)
			this.equipped = gameObject;
	}

	public void setMiniDialogue(String miniDialogue, GameObject target) {
		this.miniDialogue = miniDialogue;
		if (Game.level.shouldLog(this)) {
			if (target != null)
				Game.level
						.logOnScreen(new ActivityLog(new Object[] { this, " @", target, ": \"", miniDialogue, "\"" }));
			else
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, ": \"", miniDialogue, "\"" }));
		}
	}

	public boolean onScreen() {

		int gridX1Bounds = (int) (((Game.windowWidth / 2) - Game.getDragXWithOffset()
				- (Game.windowWidth / 2) / Game.zoom) / Game.SQUARE_WIDTH);
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		int gridX2Bounds = (int) (gridX1Bounds + ((Game.windowWidth / Game.SQUARE_WIDTH)) / Game.zoom) + 2;
		if (gridX2Bounds >= Game.level.width)
			gridX2Bounds = Game.level.width - 1;

		int gridY1Bounds = (int) (((Game.windowHeight / 2) - Game.getDragYWithOffset()
				- (Game.windowHeight / 2) / Game.zoom) / Game.SQUARE_HEIGHT);
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		int gridY2Bounds = (int) (gridY1Bounds + ((Game.windowHeight / Game.SQUARE_HEIGHT)) / Game.zoom) + 2;
		if (gridY2Bounds >= Game.level.height)
			gridY2Bounds = Game.level.height - 1;

		if (this.squareGameObjectIsOn.xInGrid >= gridX1Bounds && this.squareGameObjectIsOn.xInGrid <= gridX2Bounds
				&& this.squareGameObjectIsOn.yInGrid >= gridY1Bounds
				&& this.squareGameObjectIsOn.yInGrid <= gridY2Bounds) {

			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	public int getCarriedGoldValue() {
		int carriedGoldValue = 0;
		for (GameObject gold : inventory.getGameObjectsOfClass(Gold.class)) {
			carriedGoldValue += gold.value;
		}
		return carriedGoldValue;
	}

	public void addToCarriedGoldValue(int toAdd) {

		GameObject gold = inventory.getGameObjectOfClass(Gold.class);
		if (!(this instanceof Trader))
			gold.value += toAdd;
	}

	public void removeFromCarriedGoldValue(int toRemove) {
		for (GameObject gold : inventory.getGameObjectsOfClass(Gold.class)) {

			if (this instanceof Trader) {

			} else if (gold.value > toRemove) {
				gold.value -= toRemove;
				break;
			} else {
				toRemove -= gold.value;
				gold.value = 0;
				inventory.remove(gold);
			}
		}
	}

	public boolean sellItemsMarkedToSell(Actor buyer) {

		HashMap<Integer, ArrayList<GameObject>> gameObjectStacks = new HashMap<Integer, ArrayList<GameObject>>();
		for (GameObject gameObject : (ArrayList<GameObject>) inventory.gameObjects.clone()) {
			if (gameObject.toSell == true) {
				if (gameObjectStacks.containsKey(gameObject.templateId)) {
					gameObjectStacks.get(gameObject.templateId).add(gameObject);
				} else {
					ArrayList<GameObject> newStack = new ArrayList<GameObject>();
					newStack.add(gameObject);
					gameObjectStacks.put(gameObject.templateId, newStack);
				}
			}
		}

		for (ArrayList<GameObject> stack : gameObjectStacks.values()) {
			ActionSellItems actionSellItems = new ActionSellItems(this, buyer, stack);

			int maxCanAfford = Math.floorDiv(buyer.getCarriedGoldValue(), stack.get(0).value);
			int amtToSell = Math.min(maxCanAfford, stack.size());

			actionSellItems.qty = amtToSell;
			actionSellItems.perform();
		}

		return true;
	}

	// public ArrayList<Crime> crimesWitnessed = new ArrayList<Crime>();
	// public ArrayList<Actor> knownCriminals = new ArrayList<Actor>();
	// public Map<Actor, ArrayList<Crime>> mapActorToCrimesWitnessed = new
	// HashMap<Actor, ArrayList<Crime>>();
	// public Map<Actor, Integer> accumulatedCrimeSeverityWitnessed = new
	// HashMap<Actor, Integer>();

	public void manageWitnessedCrimes() {
		ArrayList<Crime> crimesToRemove = new ArrayList<Crime>();
		for (Crime crime : crimesWitnessedUnresolved) {
			if (crime.resolved)
				crimesToRemove.add(crime);
			else if (crime.performer.remainingHealth <= 0)
				crimesToRemove.add(crime);

		}
		for (Crime crime : crimesToRemove) {
			removeWitnessedCrime(crime);
		}
	}

	public void addWitnessedCrime(Crime crime) {

		if (crime.resolved) {
			return;
		}

		if (crimesWitnessedUnresolved.contains(crime)) {
			return;
		}

		if (crime.performer == this)
			return;

		if (this.group != null && this.group.contains(crime.performer))
			return;

		Actor criminal = crime.performer;

		crimesWitnessedUnresolved.add(crime);

		if (!knownCriminals.contains(criminal))
			knownCriminals.add(criminal);

		if (mapActorToCrimesWitnessed.containsKey(criminal)) {
			mapActorToCrimesWitnessed.get(criminal).add(crime);
		} else {
			ArrayList<Crime> newCrimeList = new ArrayList<Crime>();
			newCrimeList.add(crime);
			mapActorToCrimesWitnessed.put(crime.performer, newCrimeList);
		}

		// Accumulated severity
		if (accumulatedCrimeSeverityWitnessed.containsKey(criminal)) {
			accumulatedCrimeSeverityWitnessed.put(criminal,
					accumulatedCrimeSeverityWitnessed.get(criminal) + crime.type.severity);
		} else {
			accumulatedCrimeSeverityWitnessed.put(criminal, crime.type.severity);

		}

		// Accumulated severity of unresolved crimes
		if (accumulatedCrimeSeverityUnresolved.containsKey(criminal)) {
			accumulatedCrimeSeverityUnresolved.put(criminal,
					accumulatedCrimeSeverityUnresolved.get(criminal) + crime.type.severity);
		} else {
			accumulatedCrimeSeverityUnresolved.put(criminal, crime.type.severity);

		}

		updateHighestUnresolvedCrimeSeverity();
	}

	public void removeWitnessedCrime(Crime crime) {

		if (!crimesWitnessedUnresolved.contains(crime)) {
			return;
		}

		Actor criminal = crime.performer;

		crimesWitnessedUnresolved.remove(crime);

		mapActorToCrimesWitnessed.get(criminal).remove(crime);

		if (mapActorToCrimesWitnessed.get(criminal).size() == 0) {
			knownCriminals.remove(criminal);
			mapActorToCrimesWitnessed.remove(criminal);
		}

		accumulatedCrimeSeverityUnresolved.put(criminal,
				accumulatedCrimeSeverityUnresolved.get(criminal) - crime.type.severity);

		if (accumulatedCrimeSeverityUnresolved.get(criminal) == 0)
			accumulatedCrimeSeverityUnresolved.remove(criminal);

		updateHighestUnresolvedCrimeSeverity();
	}

	public void updateHighestUnresolvedCrimeSeverity() {

		highestAccumulatedUnresolvedCrimeSeverity = 0;
		criminalWithHighestAccumulatedUnresolvedCrimeSeverity = null;
		for (Actor criminal : accumulatedCrimeSeverityUnresolved.keySet()) {
			if (accumulatedCrimeSeverityUnresolved.get(criminal) > highestAccumulatedUnresolvedCrimeSeverity) {
				highestAccumulatedUnresolvedCrimeSeverity = accumulatedCrimeSeverityUnresolved.get(criminal);
				criminalWithHighestAccumulatedUnresolvedCrimeSeverity = criminal;
			}
		}

	}
}
