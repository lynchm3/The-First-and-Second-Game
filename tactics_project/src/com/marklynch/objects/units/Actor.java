package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AILine;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.ai.utils.AStarSearchHighLevel;
import com.marklynch.ai.utils.AStarSearchSquare;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationTake;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.quest.caveoftheblind.AIRoutineForBlind;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Key;
import com.marklynch.objects.Openable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionFishingStart;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionLift;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionPet;
import com.marklynch.objects.actions.ActionPin;
import com.marklynch.objects.actions.ActionPourContainerInInventory;
import com.marklynch.objects.actions.ActionSellItems;
import com.marklynch.objects.actions.ActionShoutForHelp;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionStopPeeking;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.actions.ActionWait;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Tool;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.Button;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Actor extends GameObject {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
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
	// public transient ArrayList<Fight> hoverFightPreviewFights;

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
	// public float bodyAnchorX;
	// public float bodyAnchorY;
	// public float legsAnchorX;
	// public float legsAnchorY;

	public boolean canOpenDoors = false;
	public boolean canEquipWeapons = false;

	public boolean wasSwappedWithThisTurn = false;

	public int swapCooldown = 0;

	protected transient int highestPathCostSeen = 0;

	public HashMap<GameObject, Investigation> investigationsMap = new HashMap<GameObject, Investigation>();

	public ArrayList<Crime> crimesPerformedThisTurn = new ArrayList<Crime>();
	public ArrayList<Crime> crimesPerformedInLifetime = new ArrayList<Crime>();

	public Texture standingTexture = null;
	public Texture stepLeftTexture = null;
	public Texture stepRightTexture = null;
	public Texture currentStepTexture = null;
	public Texture hairImageTexture = null;
	public Texture armImageTexture = null;
	public float armY = 53;
	public float leftArmDrawX = 46;
	public float leftArmHingeX = 48;
	public float leftArmAngle = 0f;
	public float rightArmDrawX = 74;
	public float rightArmHingeX = 76;
	public float rightArmAngle = 0f;

	public Texture thoughtBubbleImageTextureObject = null;
	public Texture thoughtBubbleImageTextureAction = null;
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

	public ArrayList<GameObject> gameObjectsInInventoryThatBelongToAnother = new ArrayList<GameObject>();

	public int[] requiredEquipmentTemplateIds = new int[0];

	// fishing stuff
	public int fishingProgress;
	public GameObject fishingTarget;
	public AnimationTake fishingAnimation;

	public GameObject choppingTarget;
	public AnimationTake choppingAnimation;
	public GameObject miningTarget;
	public AnimationTake miningAnimation;
	public GameObject diggingTarget;
	public AnimationTake diggingAnimation;

	public static enum HOBBY {
		FISHING, HUNTING, ARCHERY, SPARRING, BOWLS, BALL_GAMES, EATING, DRINKING, SOCIALISING;
	}

	public HOBBY hobbies[] = new HOBBY[] {};

	public Actor() {

		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;

		decorative = false;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public void init(int gold, GameObject[] mustHaves, GameObject[] mightHaves) {

		if (!(this instanceof Player))
			Game.level.actors.add(this);

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

		if (gold <= 0 && this instanceof Human)
			gold = 1;
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
		SPEEECH_BUBBLE_TEXTURE = ResourceUtils.getGlobalImage("speech_bubble.png", false);
		THOUGHT_BUBBLE_TEXTURE = ResourceUtils.getGlobalImage("thought_bubble.png", false);
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

	public final static int aiMaxPathSize = 400;
	Node lastNodeReached = null;

	public AIPath getPathTo(Square target) {

		if (target == null) {

			if (Game.level.activeActor.name.contains("Fisher")) {
				System.out.println("path return null A");
			}
			return null;
		}

		for (Node node1 : this.squareGameObjectIsOn.nodes) {
			for (Node node2 : target.nodes) {
				if (node1 == node2) {
					lastNodeReached = null;
					return getPathAtSquareLevel(target);
				}
			}
		}
		int closestNodeDistance = Integer.MAX_VALUE;
		for (Node node : this.squareGameObjectIsOn.nodes) {
			int tempDistance = straightLineDistanceBetween(this.squareGameObjectIsOn, node.square);
			if (tempDistance < closestNodeDistance) {
				lastNodeReached = node;
				closestNodeDistance = tempDistance;
			}
		}

		int maxPathSize = 1000;
		float bestCost = Float.MAX_VALUE;
		LinkedList<Node> aStarNodesPath = null;

		for (Node node2 : target.nodes) {
			LinkedList<Node> tempAStarNodesPath = new AStarSearchHighLevel().findPath(this, lastNodeReached, node2,
					maxPathSize, target);

			boolean completePath = false;
			if (tempAStarNodesPath != null && tempAStarNodesPath.size() > 0
					&& tempAStarNodesPath.get(tempAStarNodesPath.size() - 1) == node2) {
				completePath = true;
			}

			if (completePath) {
				float thisCost = tempAStarNodesPath.getLast().getCost();
				if (thisCost < bestCost) {
					aStarNodesPath = tempAStarNodesPath;
					bestCost = thisCost;
				}
			}
		}

		if (aStarNodesPath != null)
			return getPathAtSquareLevel(aStarNodesPath.getFirst().square);

		if (Game.level.activeActor.name.contains("Fisher")) {
			System.out.println("path return null B");
		}
		return null;

	}

	public AIPath getPathAtSquareLevel(Square target) {

		if (target == null) {

			if (Game.level.activeActor.name.contains("Fisher")) {
				System.out.println("path return null C");
			}
			return null;
		}

		// ASTARSEACH.FINDPATH
		int maxPathSize = aiMaxPathSize;
		if (this instanceof Player) {
			maxPathSize = 1000;
		}

		LinkedList<Square> aStarNodesPath = new AStarSearchSquare().findPath(this, this.squareGameObjectIsOn, target,
				maxPathSize);

		if (aStarNodesPath != null) {
			ArrayList<Square> squarePath = new ArrayList<Square>();

			for (Square aStarNode : aStarNodesPath) {
				squarePath.add(aStarNode);
			}

			boolean completePath = false;
			if (squarePath.size() == 0 || squarePath.size() == 1 || squarePath.get(squarePath.size() - 1) == target) {
				completePath = true;
			}

			AIPath path = new AIPath(squarePath, squarePath.size(), completePath);

			// if (completePath == false &&
			// straightLineDistanceBetween(this.squareGameObjectIsOn, target) <
			// 30) {
			//
			// if (Game.level.activeActor.name.contains("Fisher")) {
			// System.out.println("path return null D");
			// }
			// return null;
			// }

			return path;
		}

		if (Game.level.activeActor.name.contains("Fisher")) {
			System.out.println("path return null E");
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

		int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
				+ Game.SQUARE_WIDTH * drawOffsetRatioX);
		int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
				+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
		if (primaryAnimation != null) {
			actorPositionXInPixels += primaryAnimation.offsetX;
			actorPositionYInPixels += primaryAnimation.offsetY;
		}

		super.draw1();

		if (helmet != null && !sleeping) {

			int helmetPositionXInPixels = (int) (actorPositionXInPixels + headAnchorX - helmet.anchorX);
			int helmetPositionYInPixels = (int) (actorPositionYInPixels + headAnchorY - helmet.anchorY);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.helmet.imageTexture, alpha, helmetPositionXInPixels, helmetPositionYInPixels,
					helmetPositionXInPixels + helmet.width, helmetPositionYInPixels + helmet.height);
		} else if (hairImageTexture != null) {
			int bodyArmorPositionXInPixels = (actorPositionXInPixels);
			int bodyArmorPositionYInPixels = (actorPositionYInPixels);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.hairImageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + hairImageTexture.getWidth(),
					bodyArmorPositionYInPixels + hairImageTexture.getHeight());
		}

		System.out.println("armImageText = " + armImageTexture);
		if (armImageTexture != null) {

			leftArmAngle -= 0.1f;
			rightArmAngle += 0.1f;

			// left arm
			float leftArmDrawX = actorPositionXInPixels + this.leftArmDrawX;
			float leftArmDrawY = actorPositionYInPixels + this.armY;
			float leftArmHingeX = actorPositionXInPixels + this.leftArmHingeX;

			// right arm
			float rightArmDrawX = actorPositionXInPixels + this.rightArmDrawX;
			float rightArmDrawY = actorPositionYInPixels + this.armY;
			float rightArmHingeX = actorPositionXInPixels + this.rightArmHingeX;

			Matrix4f view = Game.activeBatch.getViewMatrix();

			Game.flush();
			view.translate(new Vector2f(leftArmHingeX, leftArmDrawY));
			view.rotate(leftArmAngle, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-leftArmHingeX, -leftArmDrawY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(this.armImageTexture, 1f, leftArmDrawX, leftArmDrawY,
					leftArmDrawX + armImageTexture.getWidth(), leftArmDrawY + armImageTexture.getHeight());

			Game.flush();
			view.translate(new Vector2f(leftArmHingeX, leftArmDrawY));
			view.rotate(-leftArmAngle, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-leftArmHingeX, -leftArmDrawY));
			Game.activeBatch.updateUniforms();

			Game.flush();
			view.translate(new Vector2f(rightArmHingeX, rightArmDrawY));
			view.rotate(rightArmAngle, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-rightArmHingeX, -rightArmDrawY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(this.armImageTexture, 1f, rightArmDrawX, rightArmDrawY,
					rightArmDrawX + armImageTexture.getWidth(), rightArmDrawY + armImageTexture.getHeight());

			// weapon
			if (equipped != null && !sleeping) {

				int weaponPositionXInPixels = (int) (actorPositionXInPixels + handAnchorX - equipped.anchorX);
				int weaponPositionYInPixels = (int) (actorPositionYInPixels + handAnchorY - equipped.anchorY);
				float alpha = 1.0f;
				TextureUtils.drawTexture(this.equipped.imageTexture, alpha, weaponPositionXInPixels,
						weaponPositionYInPixels, weaponPositionXInPixels + equipped.width,
						weaponPositionYInPixels + equipped.height);
				if (fishingTarget != null && equipped instanceof FishingRod) {
					FishingRod fishingRod = (FishingRod) equipped;
					fishingRod.drawLine(this, weaponPositionXInPixels, weaponPositionYInPixels);
				}
			}

			Game.flush();
			view.translate(new Vector2f(rightArmHingeX, rightArmDrawY));
			view.rotate(-rightArmAngle, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-rightArmHingeX, -rightArmDrawY));
			Game.activeBatch.updateUniforms();

		}

		if (bodyArmor != null && !sleeping) {

			int bodyArmorPositionXInPixels = (actorPositionXInPixels);
			int bodyArmorPositionYInPixels = (actorPositionYInPixels);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.bodyArmor.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + bodyArmor.width,
					bodyArmorPositionYInPixels + bodyArmor.height);
		}

		if (legArmor != null && !sleeping) {

			int legArmorPositionXInPixels = (actorPositionXInPixels);
			int legArmorPositionYInPixels = (actorPositionYInPixels);
			float alpha = 1.0f;
			TextureUtils.drawTexture(this.legArmor.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + legArmor.width,
					legArmorPositionYInPixels + legArmor.height);
		}

	}

	public void drawFishing() {
		int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
				+ Game.SQUARE_WIDTH * drawOffsetRatioX);
		int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
				+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
		if (primaryAnimation != null) {
			actorPositionXInPixels += primaryAnimation.offsetX;
			actorPositionYInPixels += primaryAnimation.offsetY;
		}
		if (equipped != null && !sleeping) {

			int weaponPositionXInPixels = (int) (actorPositionXInPixels + handAnchorX - equipped.anchorX);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels + handAnchorY - equipped.anchorY);

			if (fishingTarget != null && equipped instanceof FishingRod) {
				FishingRod fishingRod = (FishingRod) equipped;
				fishingRod.drawFishingMinigame(this, weaponPositionXInPixels, weaponPositionYInPixels);
			}

		}
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

		// if (primaryAnimation != null) {
		// Game.flush();
		// Game.activeBatch.getViewMatrix()
		// .translate(new Vector2f(primaryAnimation.offsetX,
		// primaryAnimation.offsetY));
		// Game.activeBatch.updateUniforms();
		// }

		super.draw2();

		// Draw mini dialogue
		if (miniDialogue != null && miniDialogue.length() > 0) {

			int expressionBubbleWidth = 64;
			int expressionBubbleHeight = 64;

			float expressionBubblePositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float expressionBubblePositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetRatioY - 64;
			if (primaryAnimation != null) {
				expressionBubblePositionXInPixels += primaryAnimation.offsetX;
				expressionBubblePositionYInPixels += primaryAnimation.offsetY;
			}
			float alphaBubble = 1.0f;

			// TextureUtils.skipNormals = true;

			// if (!this.squareGameObjectIsOn.visibleToPlayer)
			alphaBubble = 0.5f;
			TextureUtils.drawTexture(Actor.SPEEECH_BUBBLE_TEXTURE, alphaBubble, expressionBubblePositionXInPixels,
					expressionBubblePositionYInPixels, expressionBubblePositionXInPixels + expressionBubbleWidth,
					expressionBubblePositionYInPixels + expressionBubbleHeight);

			TextUtils.printTextWithImages(expressionBubblePositionXInPixels + 4, expressionBubblePositionYInPixels + 38,
					Integer.MAX_VALUE, false, null, new Object[] { new StringWithColor(miniDialogue, Color.BLACK) });
		} else {

			// bubble
			if (thoughtBubbleImageTextureObject != null || thoughtBubbleImageTextureAction != null) {

				int expressionBubbleWidth = 64;
				int expressionBubbleHeight = 64;

				float expressionBubblePositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
				float expressionBubblePositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetRatioY
						- 64;
				if (primaryAnimation != null) {
					expressionBubblePositionXInPixels += primaryAnimation.offsetX;
					expressionBubblePositionYInPixels += primaryAnimation.offsetY;
				}
				float alphaBubble = 1.0f;

				// TextureUtils.skipNormals = true;

				// if (!this.squareGameObjectIsOn.visibleToPlayer)
				alphaBubble = 0.5f;
				TextureUtils.drawTexture(Actor.THOUGHT_BUBBLE_TEXTURE, alphaBubble, expressionBubblePositionXInPixels,
						expressionBubblePositionYInPixels, expressionBubblePositionXInPixels + expressionBubbleWidth,
						expressionBubblePositionYInPixels + expressionBubbleHeight);
			}

			// image in bubble 1
			if (thoughtBubbleImageTextureObject != null) {

				int expressionWidth = 32;
				int expressionHeight = 32;
				int realTextureWidth = thoughtBubbleImageTextureObject.getWidth();
				int realTextureHeight = thoughtBubbleImageTextureObject.getHeight();
				if (realTextureWidth >= realTextureHeight) {// knife
					expressionHeight = 32 * realTextureHeight / realTextureWidth;
				} else {
					expressionWidth = 32 * realTextureWidth / realTextureHeight;
				}

				float expressionPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels
						+ (32 - expressionWidth / 2);
				float expressionPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetRatioY
						- expressionHeight;
				if (primaryAnimation != null) {
					expressionPositionXInPixels += primaryAnimation.offsetX;
					expressionPositionYInPixels += primaryAnimation.offsetY;
				}
				float alpha = 1.0f;

				// TextureUtils.skipNormals = true;

				if (!this.squareGameObjectIsOn.visibleToPlayer)
					alpha = 0.5f;
				TextureUtils.drawTexture(thoughtBubbleImageTextureObject, alpha, expressionPositionXInPixels,
						expressionPositionYInPixels, expressionPositionXInPixels + expressionWidth,
						expressionPositionYInPixels + expressionHeight);
				// TextureUtils.skipNormals = false;
			}

			// image in bubble 2
			if (thoughtBubbleImageTextureAction != null) {

				int expressionWidth = 32;
				int expressionHeight = 32;
				int realTextureWidth = thoughtBubbleImageTextureAction.getWidth();
				int realTextureHeight = thoughtBubbleImageTextureAction.getHeight();
				if (realTextureWidth >= realTextureHeight) {// knife
					expressionHeight = 32 * realTextureHeight / realTextureWidth;
				} else {
					expressionWidth = 32 * realTextureWidth / realTextureHeight;
				}

				float expressionPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels
						+ (32 - expressionWidth / 2);
				float expressionPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + drawOffsetRatioY
						- expressionHeight;
				if (primaryAnimation != null) {
					expressionPositionXInPixels += primaryAnimation.offsetX;
					expressionPositionYInPixels += primaryAnimation.offsetY;
				}
				float alpha = 1.0f;

				// TextureUtils.skipNormals = true;

				if (!this.squareGameObjectIsOn.visibleToPlayer)
					alpha = 0.5f;
				TextureUtils.drawTexture(thoughtBubbleImageTextureAction, alpha, expressionPositionXInPixels,
						expressionPositionYInPixels, expressionPositionXInPixels + expressionWidth / 2,
						expressionPositionYInPixels + expressionHeight / 2);
				// TextureUtils.skipNormals = false;
			}
		}
		//
		// if (primaryAnimation != null) {
		// Game.flush();
		// Game.activeBatch.getViewMatrix()
		// .translate(new Vector2f(-primaryAnimation.offsetX,
		// -primaryAnimation.offsetY));
		// Game.activeBatch.updateUniforms();
		// }

		if (Game.showAILines)

		{
			if (aiLine != null) {
				aiLine.draw2();
			}
		}

	}

	@Override
	public void drawUI() {

		if (this.squareGameObjectIsOn == null || this.squareGameObjectIsOn.visibleToPlayer == false)
			return;

		super.drawUI();
	}

	public ArrayList<Float> calculateIdealDistanceFromTargetToAttack(GameObject target) {

		ArrayList<Float> idealDistances = new ArrayList<Float>();
		if (!canEquipWeapons) {
			idealDistances.add(1f);
			return idealDistances;
		}

		ArrayList<Fight> fights = new ArrayList<Fight>();
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

	public void weaponButtonClicked(Weapon weapon) {
		this.equip(weapon);
		equippedWeaponGUID = this.equipped.guid;
	}

	@Override
	public void updateRealtime(int delta) {
		super.updateRealtime(delta);

		// START FISHING
		if (fishingTarget != null && equipped instanceof FishingRod) {
			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
			if (primaryAnimation != null) {
				actorPositionXInPixels += primaryAnimation.offsetX;
				actorPositionYInPixels += primaryAnimation.offsetY;
			}
			int weaponPositionXInPixels = (int) (actorPositionXInPixels + handAnchorX - equipped.anchorX);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels + handAnchorY - equipped.anchorY);
			FishingRod fishingRod = (FishingRod) equipped;
			fishingRod.updateLine(this, weaponPositionXInPixels, weaponPositionYInPixels, delta);
			if (this == Game.level.player) {
				fishingRod.updateFishingMinigame(this, weaponPositionXInPixels, weaponPositionYInPixels, delta);
			}
		}
		// END FISHING
	}

	@Override
	public void update(int delta) {

		// if (this != Game.level.player) {
		//
		// if (fishingTarget != null) {
		// fishingTarget.beingFishedBy = null;
		// fishingTarget.primaryAnimation = null;
		// fishingTarget = null;
		// }
		// if (choppingTarget != null) {
		// choppingTarget.beingChopped = false;
		// choppingTarget.primaryAnimation = null;
		// choppingTarget = null;
		// }
		// if (miningTarget != null) {
		// miningTarget.beingMined = false;
		// miningTarget.primaryAnimation = null;
		// miningTarget = null;
		// }
		// if (diggingTarget != null) {
		// diggingTarget.beingDigged = false;
		// diggingTarget.primaryAnimation = null;
		// diggingTarget = null;
		// }
		// }

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
		}

		// Water Source
		if (this.squareGameObjectIsOn != null && this.squareGameObjectIsOn.inventory.waterBody != null) {
			return new ActionFishingStart(performer, this);
		}

		if (performer.attackers.contains(this)) {
			return new ActionAttack(performer, this);
		}

		if (this instanceof RockGolem) {
			RockGolem rockGolem = (RockGolem) this;
			if (!rockGolem.awake) {
				return new ActionLift(performer, this);
			} else {
				return new ActionAttack(performer, this);
			}
		}

		if (this instanceof AggressiveWildAnimal) {
			return new ActionAttack(performer, this);
		}

		if (this instanceof Animal) {
			if (this.getConversation() != null) {
				return new ActionTalk(performer, this);
			} else {
				return new ActionPet(performer, this);
			}
		}

		if (this instanceof Monster) {
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

		if (sleeping)
			return false;

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

	@Override
	public void clearActions() {
		super.clearActions();
		crimesPerformedThisTurn.clear();
	}

	@Override
	public void attackedBy(Object attacker, Action action) {

		super.attackedBy(attacker, action);

		if (sleeping && Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " woke up" }));
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

		if (this.squareGameObjectIsOn.xInGrid >= Level.gridX1Bounds
				&& this.squareGameObjectIsOn.xInGrid <= Level.gridX2Bounds
				&& this.squareGameObjectIsOn.yInGrid >= Level.gridY1Bounds
				&& this.squareGameObjectIsOn.yInGrid <= Level.gridY2Bounds) {

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
			if (crime.isResolved())
				crimesToRemove.add(crime);
			else if (crime.performer.remainingHealth <= 0)
				crimesToRemove.add(crime);

		}
		for (Crime crime : crimesToRemove) {
			removeWitnessedCrime(crime);
		}
	}

	public void addWitnessedCrime(Crime crime) {

		if (crime.isResolved()) {
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

		if (this instanceof Guard)
			crime.reported = true;

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

		if (accumulatedCrimeSeverityWitnessed.get(criminal) >= 10) {
			this.addAttackerForNearbyFactionMembersIfVisible(criminal);
			this.addAttackerForThisAndGroupMembers(criminal);
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

	public void drawX(boolean onMouse) {

		if (onMouse) {
			TextureUtils.drawTexture(Action.textureX, 1f, UserInputLevel.mouseLastX + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + 16,
					UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
		} else {

			// float squarePositionX = squareGameObjectIsOn.xInGridPixels;
			// float squarePositionY = squareGameObjectIsOn.yInGridPixels;

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
			if (primaryAnimation != null) {
				actorPositionXInPixels += primaryAnimation.offsetX;
				actorPositionYInPixels += primaryAnimation.offsetY;
			}
			TextureUtils.drawTexture(Action.textureX, actorPositionXInPixels + Game.QUARTER_SQUARE_WIDTH,
					actorPositionYInPixels + Game.QUARTER_SQUARE_WIDTH,
					actorPositionXInPixels + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
					actorPositionYInPixels + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
		}

	}

	public void drawAction(Action action, boolean onMouse) {

		if (onMouse) {
			TextureUtils.drawTexture(action.image, 1f, UserInputLevel.mouseLastX + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + 16,
					UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
		} else {

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
			if (primaryAnimation != null) {
				actorPositionXInPixels += primaryAnimation.offsetX;
				actorPositionYInPixels += primaryAnimation.offsetY;
			}
			TextureUtils.drawTexture(action.image, actorPositionXInPixels + Game.QUARTER_SQUARE_WIDTH,
					actorPositionYInPixels + Game.QUARTER_SQUARE_WIDTH,
					actorPositionXInPixels + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
					actorPositionYInPixels + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
		}

	}

	protected Class[] soundClassesToReactTo = new Class[] { Weapon.class, BrokenGlass.class };

	public void createSearchLocationsBasedOnSound(Sound sound) {

		ArrayList<Class> classesArrayList = new ArrayList<Class>(Arrays.asList(soundClassesToReactTo));

		if (this.canSeeGameObject(sound.sourcePerformer))
			return;

		// If asleep then sound has to be nearer for detection.
		// if (actor.sleeping &&
		// actor.straightLineDistanceTo(sound.sourceSquare) > sound.loudness
		// - 2)
		// continue;
		// else
		if (this.sleeping) {
			this.sleeping = false;
			this.aiRoutine.wokenUpCountdown = 5;
			if (Game.level.shouldLog(this))
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " woke up" }));
		}

		if (!this.investigationsMap.containsValue(sound.sourceSquare)) {

			// Check if sound is in passed in list of classes
			boolean soundInTypeList = false;
			if (sound.sourceObject != null) {
				for (Class clazz : soundClassesToReactTo) {
					if (clazz.isInstance(sound.sourceObject)) {
						soundInTypeList = true;
					}
				}
			}

			if (this instanceof Blind && sound.sourceObject != null && sound.sourceObject.getClass() == Bell.class) {
				Blind blind = (Blind) this;
				AIRoutineForBlind aiRoutineForBlind = (AIRoutineForBlind) aiRoutine;

				if (aiRoutineForBlind.meatChunk == null) {
					aiRoutineForBlind.bellSound = sound;
					blind.investigationsMap.clear();
					aiRoutineForBlind.targetSquare = null;
				}

			} else if (sound.actionType == ActionShoutForHelp.class) {
				this.addInvestigation(sound.sourceObject, sound.sourceSquare,
						Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
				if (sound.sourceObject != null && sound.sourceObject.remainingHealth > 0) {
					this.addAttackerForThisAndGroupMembers(sound.sourceObject);
				}
			} else if (!sound.legal) {
				this.addInvestigation(sound.sourcePerformer, sound.sourceSquare,
						Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
			} else if (soundInTypeList == true) {
				this.addInvestigation(sound.sourcePerformer, sound.sourceSquare,
						Investigation.INVESTIGATION_PRIORITY_SOUND_HEARD);
			} else if (sound.loudness >= 5) {
				this.addInvestigation(sound.sourcePerformer, sound.sourceSquare,
						Investigation.INVESTIGATION_PRIORITY_SOUND_HEARD);
			}

		}
	}

	// public Sound getSoundFromSourceType(Class clazz) {
	//
	// // Check for sounds to investigate
	// for (Sound sound : this.squareGameObjectIsOn.sounds) {
	// if (clazz.isInstance(sound.sourceObject)) {
	// return sound;
	// }
	// }
	// return null;
	// }

	public void setAttributesForCopy(String name, Actor actor, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {
		super.setAttributesForCopy(actor, square, null);
		actor.name = name;
		actor.faction = faction;

		actor.area = area;
		actor.bed = bed;
		if (bed != null)
			bed.owner = actor;
		actor.title = title;
		actor.level = level;
		actor.strength = strength;
		actor.dexterity = dexterity;
		actor.intelligence = intelligence;
		actor.endurance = endurance;
		actor.travelDistance = travelDistance;
		actor.sight = sight;
		actor.handAnchorX = handAnchorX;
		actor.handAnchorY = handAnchorY;
		actor.headAnchorX = headAnchorX;
		actor.headAnchorY = headAnchorY;
		actor.canOpenDoors = canOpenDoors;
		actor.canEquipWeapons = canEquipWeapons;
		actor.hairImageTexture = hairImageTexture;
		actor.armImageTexture = armImageTexture;
		if (aiRoutine != null)
			actor.aiRoutine = aiRoutine.getInstance(actor);
		actor.init(gold, mustHaves, mightHaves);
	}
}
