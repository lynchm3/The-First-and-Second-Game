package com.marklynch.objects.actors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionAttack;
import com.marklynch.actions.ActionDouse;
import com.marklynch.actions.ActionFishingStart;
import com.marklynch.actions.ActionHide;
import com.marklynch.actions.ActionIgnite;
import com.marklynch.actions.ActionLift;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionOpenInventoryToPourItems;
import com.marklynch.actions.ActionOpenOtherInventory;
import com.marklynch.actions.ActionPet;
import com.marklynch.actions.ActionSellItems;
import com.marklynch.actions.ActionShoutForHelp;
import com.marklynch.actions.ActionStopHiding;
import com.marklynch.actions.ActionStopPeeking;
import com.marklynch.actions.ActionTalk;
import com.marklynch.actions.ActionViewInfo;
import com.marklynch.actions.ActionWait;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.routines.AIRoutineForBlind;
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
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.primary.AnimationWait;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.armor.BodyArmor;
import com.marklynch.objects.armor.Helmet;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.Arrow;
import com.marklynch.objects.inanimateobjects.BrokenGlass;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Gold;
import com.marklynch.objects.inanimateobjects.HidingPlace;
import com.marklynch.objects.inanimateobjects.Openable;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.button.Button;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Actor extends GameObject {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public final static String[] editableAttributes = { "name", "imageTexture", "faction", "strength", "dexterity",
			"intelligence", "endurance", "totalHealth", "remainingHealth", "travelDistance", "inventory",
			"showInventory", "fitsInInventory", "canContainOtherObjects" };
	private static Texture SPEEECH_BUBBLE_TEXTURE = null;
	private static Texture THOUGHT_BUBBLE_TEXTURE = null;

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	public AIRoutine aiRoutine;
	public String title = "";
	public int travelDistance = 1;
	public int sight = 10;

	public transient int distanceMovedThisTurn = 0;
	public transient boolean showWeaponButtons = false;

	public String activityDescription = "";
	public String miniDialogue = "";

	public GameObject bed;

	public Faction faction;

	public GameObject equippedBeforePickingUpObject = null;

	public Helmet helmet;
	public BodyArmor bodyArmor;
	public LegArmor legArmor;

	public boolean canOpenDoors = false;
	public boolean canEquipWeapons = false;

	public boolean wasSwappedWithThisTurn = false;

	public int swapCooldown = 0;

	protected transient int highestPathCostSeen = 0;

	public HashMap<GameObject, Investigation> investigationsMap = new HashMap<GameObject, Investigation>();

	public ArrayList<Crime> crimesPerformedThisTurn = new ArrayList<Crime>(Crime.class);
	public ArrayList<Crime> crimesPerformedInLifetime = new ArrayList<Crime>(Crime.class);

	public Texture standingTexture = null;
	public Texture stepLeftTexture = null;
	public Texture stepRightTexture = null;
	public Texture currentStepTexture = null;
	public Texture hairImageTexture;
	public Color hairColor = Color.GREEN;// = ResourceUtils.getGlobalImage("hair_1.png", false);

	// Arms
	public Texture armImageTexture;// = ResourceUtils.getGlobalImage("arm.png", false);
	public float shoulderY = 53;
	public float elbowY = 85;
	public float handY = 118f;
	public float leftArmDrawX = 48;
	public float leftArmHingeX = 50;
	public float rightArmDrawX = 76;
	public float rightArmHingeX = 78;

	// head
	int headAnchorX = 64;
	int headAnchorY = 26;

	// Toro
	public Texture torsoImageTexture;// = ResourceUtils.getGlobalImage("hero_upper.png", false);

	// Pelvis
	public Texture pelvisImageTexture;// = ResourceUtils.getGlobalImage("hero_lower.png", false);

	// legs
	public Texture legImageTexture;
	public float hipY = 120;
	public float kneeY = 156;
	public float footY = 192f;
	public float leftLegDrawX = 54;
	public float leftLegHingeX = 58;
	public float rightLegDrawX = 66;
	public float rightLegHingeX = 70;

	public Texture thoughtBubbleImageTextureObject = null;
	public Texture thoughtBubbleImageTextureAction = null;
	public transient Color thoughtBubbleImageTextureActionColor = Color.WHITE;
	public GameObject peekingThrough = null;
	public Square peekSquare = null;

	public int timePerStep = 100;
	public int thisStepTime = timePerStep;

	public ArrayList<Crime> crimesWitnessedUnresolved = new ArrayList<Crime>(Crime.class);
	public ArrayList<Actor> knownCriminals = new ArrayList<Actor>(Actor.class);
	public Map<Actor, ArrayList<Crime>> mapActorToCrimesWitnessed = new HashMap<Actor, ArrayList<Crime>>();
	public Map<Actor, Integer> accumulatedCrimeSeverityWitnessed = new HashMap<Actor, Integer>();
	public Map<Actor, Integer> accumulatedCrimeSeverityUnresolved = new HashMap<Actor, Integer>();
	public int highestAccumulatedUnresolvedCrimeSeverity = 0;
	public Actor criminalWithHighestAccumulatedUnresolvedCrimeSeverity = null;

	public AILine aiLine;

	public ArrayList<Door> doors = new ArrayList<Door>(Door.class);
	public boolean followersShouldFollow = false;

	public boolean sleeping = false;

	public ArrayList<Power> powers = new ArrayList<Power>(Power.class);

	public Area area;

	public ArrayList<GameObject> gameObjectsInInventoryThatBelongToAnother = new ArrayList<GameObject>(
			GameObject.class);

	public int[] requiredEquipmentTemplateIds = new int[0];

	// fishing stuff
	public int fishingProgress;
	public AnimationTake fishingAnimation;

	public static enum HOBBY {
		FISHING, HUNTING, ARCHERY, SPARRING, BOWLS, BALL_GAMES, EATING, DRINKING, SOCIALISING;
	}

	public HOBBY[] hobbies = new HOBBY[] {};

	public Actor() {

		super();

		primaryAnimation = new AnimationWait(this, null);
		Level.animations.add(primaryAnimation);

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;

		decorative = false;

		orderingOnGound = 101;

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
			bed.owner = this;

		ArrayList<Weapon> weapons = getWeaponsInInventory();
		if (weapons.size() > 0 && weapons.get(0) != null) {
			equipped = weapons.get(0);
		}

		if (this.faction != null) {
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
		loadImages();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
		super.postLoad2();
	}

	public final static int aiMaxPathSize = 400;
	Node lastNodeReached = null;
	Square lastPathTarget = null;

	public AIPath getPathTo(Square target) {

		if (target == null) {
			return null;
		}

		if (this.squareGameObjectIsOn.nodes == null || this.squareGameObjectIsOn.nodes.size() == 0
				|| target.nodes == null || target.nodes.size() == 0) {
			lastNodeReached = null;
			lastPathTarget = target;
			return getPathAtSquareLevel(target);
		}

		for (Node node1 : this.squareGameObjectIsOn.nodes) {
			for (Node node2 : target.nodes) {
				if (node1 == node2) {
					lastNodeReached = null;
					lastPathTarget = target;
					return getPathAtSquareLevel(target);
				}
			}
		}

		if (this.squareGameObjectIsOn.node != null) {
			lastNodeReached = this.squareGameObjectIsOn.node;
		} else if (lastNodeReached == null || lastPathTarget != target) {
			int closestNodeDistance = Integer.MAX_VALUE;
			for (Node node : this.squareGameObjectIsOn.nodes) {
				int tempDistance = straightLineDistanceBetween(this.squareGameObjectIsOn, node.square);
				if (tempDistance < closestNodeDistance) {
					lastNodeReached = node;
					closestNodeDistance = tempDistance;
				}
			}
		}

		// ARE WE ON A NODE!?!?!

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

		if (aStarNodesPath != null) {
			lastPathTarget = target;
			return getPathAtSquareLevel(aStarNodesPath.getFirst().square);
		}

		lastPathTarget = target;
		return null;

	}

	public AIPath getPathAtSquareLevel(Square target) {

		if (target == null) {

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
			ArrayList<Square> squarePath = new ArrayList<Square>(Square.class);

			for (Square aStarNode : aStarNodesPath) {
				squarePath.add(aStarNode);
			}

			boolean completePath = false;
			if (squarePath.size() == 0 || squarePath.size() == 1 || squarePath.get(squarePath.size() - 1) == target) {
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
			}
		}
	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		if (!died && remainingHealth <= 0) {
			super.checkIfDestroyed(attacker, action);
			return true;
		}
		return false;
	}

	public transient int actorPositionXInPixels;
	public transient int actorPositionYInPixels;
	public transient float boundsX1;
	public transient float boundsY1;
	public transient float boundsX2;
	public transient float boundsY2;
	public transient float scaleX;
	public transient float scaleY;

	@Override
	public boolean draw1() {

		if (!shouldDraw())
			return false;

		actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels + drawOffsetX);
		actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels + drawOffsetY);

		boundsX1 = actorPositionXInPixels;
		boundsY1 = actorPositionYInPixels;
		boundsX2 = actorPositionXInPixels + width;
		boundsY2 = actorPositionYInPixels + height;

		if (primaryAnimation != null) {
			actorPositionXInPixels += primaryAnimation.offsetX;
			actorPositionYInPixels += primaryAnimation.offsetY;
			boundsX1 = actorPositionXInPixels + primaryAnimation.boundsX1;
			boundsY1 = actorPositionYInPixels + primaryAnimation.boundsY1;
			boundsX2 = actorPositionXInPixels + primaryAnimation.boundsX2;
			boundsY2 = actorPositionYInPixels + primaryAnimation.boundsY2;
		}

		if (primaryAnimation != null && primaryAnimation.getCompleted() == false)
			primaryAnimation.draw1();

		float alpha = 1.0f;

		// TextureUtils.skipNormals = true;

		if (primaryAnimation != null)
			alpha = primaryAnimation.alpha;
		if (!this.squareGameObjectIsOn.visibleToPlayer && this != Game.level.player)
			alpha = 0.5f;

		scaleX = 1;
		scaleY = 1;
		if (primaryAnimation != null) {
			scaleX = primaryAnimation.scaleX;
			scaleY = primaryAnimation.scaleY;
		}

		if (imageTexture != null)
			super.draw1();
		else {

			// Draw actor's shadow
			if (Level.shadowDarkness > 0 && this.squareGameObjectIsOn.structureSquareIsIn == null) {
				drawActor(actorPositionXInPixels, actorPositionYInPixels, Level.shadowDarkness, false, scaleX,
						scaleY * Level.shadowLength, Level.shadowAngle, boundsX1, boundsY1, boundsX2, boundsY2,
						Color.BLACK, false, false, !this.backwards, true, true);
			}

			// Draw actual actor
			Color color = Level.dayTimeOverlayColor;
			if (this.squareGameObjectIsOn.structureSquareIsIn != null)
				color = StructureRoom.roomColor;

			color = calculateColor(color);

			drawActor(actorPositionXInPixels, actorPositionYInPixels, alpha,
					flash || this == Game.gameObjectMouseIsOver || inSoundPreview
							|| (Game.gameObjectMouseIsOver != null
									&& Game.gameObjectMouseIsOver.gameObjectsToHighlight.contains(this)),
					scaleX, scaleY, 0f, boundsX1, boundsY1, boundsX2, boundsY2, color, true, true, this.backwards,
					false, true);
			inSoundPreview = false;
		}
		return true;

	}

	public boolean inSoundPreview;

	public void drawActor(int x, int y, float alpha, boolean highlight, float scaleX, float scaleY, float rotationRad,
			float boundsX1, float boundsY1, float boundsX2, float boundsY2, Color color, boolean drawClothes,
			boolean drawHealthBar, boolean backwards, boolean reverseRotation, boolean useAnimation) {

		Matrix4f view = Game.activeBatch.getViewMatrix();
		float halfWidthAtScale = halfWidth * scaleX;
		float halfHeightAtScale = halfHeight * scaleY;

		if (scaleX != 1 || scaleY != 1) {
			Game.flush();
			view.translate(new Vector2f(x + halfWidthAtScale, y + halfHeightAtScale));
			view.scale(new Vector3f(scaleX, scaleY, 1f));
			view.translate(new Vector2f(-(x + halfWidthAtScale), -(y + halfHeightAtScale)));
			Game.activeBatch.updateUniforms();
		}

		float yToRotateAround = y + height;
		if (primaryAnimation != null)
			yToRotateAround += primaryAnimation.headToToeOffset;

		Game.flush();
		view.translate(new Vector2f(x + halfWidthAtScale, yToRotateAround));
		view.rotate(rotationRad, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-(x + halfWidthAtScale), -(yToRotateAround)));
		Game.activeBatch.updateUniforms();

		// torso rotation
		float torsoAngle = 0;
		if (primaryAnimation != null && useAnimation) {
			torsoAngle = primaryAnimation.torsoAngle;
		}
		Game.flush();
		view.translate(new Vector2f(x + halfWidthAtScale, y + hipY));
		if (!reverseRotation)
			view.rotate(torsoAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-torsoAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-(x + halfWidthAtScale), -(y + hipY)));
		Game.activeBatch.updateUniforms();

		if (drawHealthBar && remainingHealth != totalHealth && remainingHealth > 0) {

			// draw sidebar on square
			float healthPercentage = ((float) remainingHealth) / ((float) totalHealth);
			float healthBarHeightInPixels = height * healthPercentage;
			float healthXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float healthYInPixels = this.squareGameObjectIsOn.yInGridPixels;
			if (primaryAnimation != null) {
				healthXInPixels += primaryAnimation.offsetX;
				healthYInPixels += primaryAnimation.offsetY;
			}

			Color aggressionColor = Color.YELLOW;
			if (thoughtsOnPlayer > 50) {
				aggressionColor = Color.GREEN;
			} else if (thoughtsOnPlayer < -50) {
				aggressionColor = Color.RED;
			}

			// White bit under health bar
			QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), x + 1, y + 1, x + healthWidthInPixels - 1,
					y + height - 1);

			// Colored health bar
			QuadUtils.drawQuad(aggressionColor, x + 1, y + 1, x + healthWidthInPixels - 1,
					y + healthBarHeightInPixels - 1);
		}

		drawBackArm(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
				reverseRotation, useAnimation);

		for (Arrow arrow : arrowsEmbeddedInThis) {

			float arrowWidth = arrow.width;

			if (arrow.backwards) {
				TextureUtils.drawTexture(arrow.textureEmbeddedPoint, alpha,
						this.squareGameObjectIsOn.xInGridPixels + arrow.drawOffsetX + arrowWidth
								+ primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + arrow.drawOffsetY + primaryAnimation.offsetY,
						this.squareGameObjectIsOn.xInGridPixels + arrow.drawOffsetX + primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + arrow.drawOffsetY + arrow.height
								+ primaryAnimation.offsetY,
						color);
			} else {
				TextureUtils.drawTexture(arrow.textureEmbeddedPoint, alpha,
						this.squareGameObjectIsOn.xInGridPixels + arrow.drawOffsetX + primaryAnimation.offsetX
								- arrowWidth,
						this.squareGameObjectIsOn.yInGridPixels + arrow.drawOffsetY + primaryAnimation.offsetY,
						this.squareGameObjectIsOn.xInGridPixels + arrow.drawOffsetX + primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + arrow.drawOffsetY + arrow.height
								+ primaryAnimation.offsetY,
						color);

			}
		}

		if (torsoImageTexture != null) {
			TextureUtils.drawTextureWithinBounds(torsoImageTexture, alpha, x, y, x + width, y + height, boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, color);
			if (highlight) {
				TextureUtils.drawTexture(torsoImageTexture, 0.5f, x, y, x + width, y + height, boundsX1, boundsY1,
						boundsX2, boundsY2, backwards, false, flashColor, false);
			}
		}

		if (highlight)

		{
			TextureUtils.drawTexture(imageTexture, 0.5f, x, y, x + width, y + height, 0, 0, 0, 0, backwards, false,
					flashColor, false);
		} else if (squareGameObjectIsOn.inventory.waterBody != null && !(this instanceof Fish)
				&& imageTexture != null) {

			TextureUtils.drawTexture(imageTexture, 0.5f, x, y, x + width, y + height, 0, 0, 0, 0, backwards, false,
					underWaterColor, false);
			TextureUtils.drawTexture(Templates.WATER_BODY.imageTexture, alpha, x, y, x + width, y + height, backwards);

			// squareGameObjectIsOn.inventory.getGameObjectOfClass(WaterBody.class).draw1();
		}

		for (Arrow arrow : arrowsEmbeddedInThis) {

			float arrowWidth = arrow.width;

			if (arrow.backwards) {
				TextureUtils.drawTexture(arrow.textureEmbedded, alpha,
						this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
								+ arrowWidth + primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
								+ primaryAnimation.offsetY,
						this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
								+ primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
								+ arrow.height + primaryAnimation.offsetY,
						color);
			} else {
				TextureUtils.drawTexture(arrow.textureEmbedded, alpha,
						this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
								- arrowWidth + primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
								+ primaryAnimation.offsetY,
						this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
								+ primaryAnimation.offsetX,
						this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
								+ arrow.height + primaryAnimation.offsetY,
						color);

			}
		}
		if (helmet != null && !sleeping) {

			int helmetPositionXInPixels = (int) ((x) + headAnchorX - helmet.anchorX);
			int helmetPositionYInPixels = (int) ((y) + headAnchorY - helmet.anchorY);

			TextureUtils.drawTextureWithinBounds(this.helmet.imageTexture, alpha, helmetPositionXInPixels,
					helmetPositionYInPixels, helmetPositionXInPixels + helmet.width,
					helmetPositionYInPixels + helmet.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards, false,
					color);
			if (highlight) {
				TextureUtils.drawTexture(this.helmet.imageTexture, 0.5f, helmetPositionXInPixels,
						helmetPositionYInPixels, helmetPositionXInPixels + helmet.width,
						helmetPositionYInPixels + helmet.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
						false, flashColor, false);

			}

		} else if (hairImageTexture != null) {
			int bodyArmorPositionXInPixels = (x);
			int bodyArmorPositionYInPixels = (y);
			TextureUtils.drawTextureWithinBounds(this.hairImageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + hairImageTexture.getWidth(),
					bodyArmorPositionYInPixels + hairImageTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
					backwards, false, hairColor);
			if (highlight) {
				TextureUtils.drawTexture(this.hairImageTexture, 0.5f, bodyArmorPositionXInPixels,
						bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + hairImageTexture.getWidth(),
						bodyArmorPositionYInPixels + hairImageTexture.getHeight(), boundsX1, boundsY1, boundsX2,
						boundsY2, backwards, false, flashColor, false);

			}
		}

		if (bodyArmor != null && bodyArmor.backTexture != null && !sleeping && drawClothes) {

			int bodyArmorPositionXInPixels = (x);
			int bodyArmorPositionYInPixels = (y);

			TextureUtils.drawTextureWithinBounds(this.bodyArmor.backTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels + bodyArmor.width, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels + bodyArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
					false, color);
			if (highlight) {
				TextureUtils.drawTexture(this.bodyArmor.backTexture, 0.5f, bodyArmorPositionXInPixels,
						bodyArmorPositionYInPixels + bodyArmor.width, bodyArmorPositionXInPixels,
						bodyArmorPositionYInPixels + bodyArmor.height, boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		// pelvis
		if (pelvisImageTexture != null) {
			TextureUtils.drawTextureWithinBounds(pelvisImageTexture, alpha, x, y, x + width, y + height, boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, color);

			if (highlight) {
				TextureUtils.drawTexture(pelvisImageTexture, 0.5f, x, y, x + width, y + height, boundsX1, boundsY1,
						boundsX2, boundsY2, backwards, false, flashColor, false);

			}
		}

		if (legArmor != null && !sleeping && drawClothes) {

			int legArmorPositionXInPixels = (x);
			int legArmorPositionYInPixels = (y);
			TextureUtils.drawTextureWithinBounds(this.legArmor.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + legArmor.width,
					legArmorPositionYInPixels + legArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
					false, color);
			if (highlight) {
				TextureUtils.drawTexture(this.legArmor.imageTexture, 0.5f, legArmorPositionXInPixels,
						legArmorPositionYInPixels, legArmorPositionXInPixels + legArmor.width,
						legArmorPositionYInPixels + legArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
						false, flashColor, false);

			}
		}

		drawBackLeg(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
				reverseRotation, useAnimation);
		drawFrontLeg(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
				reverseRotation, useAnimation);

		if (bodyArmor != null && !sleeping && drawClothes) {

			int bodyArmorPositionXInPixels = (x);
			int bodyArmorPositionYInPixels = (y);

			TextureUtils.drawTextureWithinBounds(this.bodyArmor.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + bodyArmor.width,
					bodyArmorPositionYInPixels + bodyArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
					false, color);

			if (highlight) {
				TextureUtils.drawTexture(this.bodyArmor.imageTexture, 0.5f, bodyArmorPositionXInPixels,
						bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + bodyArmor.width,
						bodyArmorPositionYInPixels + bodyArmor.height, boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		if (legArmor != null && legArmor.frontTexture != null && !sleeping && drawClothes) {

			int legArmorPositionXInPixels = (x);
			int legArmorPositionYInPixels = (y);
			TextureUtils.drawTextureWithinBounds(this.legArmor.frontTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + legArmor.width,
					legArmorPositionYInPixels + legArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
					false, color);
			if (highlight) {
				TextureUtils.drawTexture(this.legArmor.frontTexture, 0.5f, legArmorPositionXInPixels,
						legArmorPositionYInPixels, legArmorPositionXInPixels + legArmor.width,
						legArmorPositionYInPixels + legArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards,
						false, flashColor, false);

			}
		}

		drawFrontArm(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
				reverseRotation, useAnimation);

		Game.flush();
		view.translate(new Vector2f(x + halfWidthAtScale, y + hipY));
		if (!reverseRotation)
			view.rotate(-torsoAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(torsoAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-(x + halfWidthAtScale), -(y + hipY)));
		Game.activeBatch.updateUniforms();

		Game.flush();
		view.translate(new Vector2f(x + halfWidthAtScale, yToRotateAround));
		view.rotate(-(rotationRad), new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-(x + halfWidthAtScale), -(yToRotateAround)));
		Game.activeBatch.updateUniforms();

		if (scaleX != 1 || scaleY != 1) {
			Game.flush();
			// Matrix4f view = Game.activeBatch.getViewMatrix();
			view.translate(new Vector2f((x + halfWidthAtScale), (y + halfHeightAtScale)));
			view.scale(new Vector3f(1f / scaleX, 1f / scaleY, 1f));
			view.translate(new Vector2f(-(x + halfWidthAtScale), -(y + halfHeightAtScale)));
			Game.activeBatch.updateUniforms();
		}
	}

	public void drawFrontArm(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {
		if (backwards)
			drawRightArm(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);
		else
			drawLeftArm(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);
	}

	public void drawBackArm(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {
		if (backwards)
			drawLeftArm(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);
		else
			drawRightArm(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);

	}

	public void drawRightArm(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {

		if (armImageTexture == null)
			return;
		float shoulderDrawY = y + this.shoulderY;
		float elbowDrawY = y + this.elbowY;
		float rightShoulderAngle = 0f;
		float rightElbowAngle = 0f;
		float leftShoulderAngle = 0f;
		float leftElbowAngle = 0f;
		if (primaryAnimation != null && useAnimation) {
			rightShoulderAngle = primaryAnimation.rightShoulderAngle;
			rightElbowAngle = primaryAnimation.rightElbowAngle;
			leftShoulderAngle = primaryAnimation.leftShoulderAngle;
			leftElbowAngle = primaryAnimation.leftElbowAngle;
		}

		// right arm
		float rightArmDrawX = x + this.rightArmDrawX;
		float rightArmHingeX = x + this.rightArmHingeX;

		Matrix4f view = Game.activeBatch.getViewMatrix();
		Game.flush();
		view.translate(new Vector2f(rightArmHingeX, shoulderDrawY));
		if (!reverseRotation)
			view.rotate(rightShoulderAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-leftShoulderAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-rightArmHingeX, -shoulderDrawY));
		Game.activeBatch.updateUniforms();

		Game.flush();
		view.translate(new Vector2f(rightArmHingeX, elbowDrawY));
		if (!reverseRotation)
			view.rotate(rightElbowAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-leftElbowAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-rightArmHingeX, -elbowDrawY));
		Game.activeBatch.updateUniforms();

		if (equipped != null && backwards && !sleeping && primaryAnimation != null
				&& primaryAnimation.drawArrowInOffHand == true && useAnimation) {
			drawArrow(rightArmHingeX - Templates.ARROW.anchorX, y + handY, color, backwards);
		}

		TextureUtils.drawTextureWithinBounds(this.armImageTexture, alpha, rightArmDrawX, elbowDrawY,
				rightArmDrawX + armImageTexture.getWidth(), elbowDrawY + armImageTexture.getHeight(), boundsX1,
				boundsY1, boundsX2, boundsY2, false, false, color);
		if (highlight) {

			TextureUtils.drawTexture(this.armImageTexture, 0.5f, rightArmDrawX, elbowDrawY,
					rightArmDrawX + armImageTexture.getWidth(), elbowDrawY + armImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);
		}
		if (bodyArmor != null && bodyArmor.armLowerTexture != null && drawClothes) {
			TextureUtils.drawTextureWithinBounds(this.bodyArmor.armLowerTexture, alpha, rightArmDrawX, elbowDrawY,
					rightArmDrawX + bodyArmor.armLowerTexture.getWidth(),
					elbowDrawY + bodyArmor.armLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, color);
			if (highlight) {
				TextureUtils.drawTexture(this.bodyArmor.armLowerTexture, 0.5f, rightArmDrawX, elbowDrawY,
						rightArmDrawX + bodyArmor.armLowerTexture.getWidth(),
						elbowDrawY + bodyArmor.armLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		if (equipped != null && !backwards && !sleeping
				&& (primaryAnimation == null || primaryAnimation.drawWeapon || !useAnimation)) {
			drawWeapon(rightArmHingeX - equipped.anchorX, y + handY - equipped.anchorY, alpha, highlight, boundsX1,
					boundsY1, boundsX2, boundsY2, color, backwards, useAnimation);
		}

		if (equipped != null && !backwards && !sleeping && primaryAnimation != null
				&& primaryAnimation.drawArrowInMainHand == true
				&& (primaryAnimation == null || primaryAnimation.drawWeapon || !useAnimation)) {
			drawArrow(rightArmHingeX - Templates.ARROW.anchorX, y + handY, color, backwards);
		}

		if (equipped != null && equipped.templateId == Templates.HUNTING_BOW.templateId && !backwards
				&& (primaryAnimation == null || primaryAnimation.drawWeapon || !useAnimation) && alpha == 1f) {
			drawBowString(rightArmHingeX, y + handY, alpha, color, backwards, useAnimation);
		}

		Game.flush();
		view.translate(new Vector2f(rightArmHingeX, elbowDrawY));
		if (!reverseRotation)
			view.rotate(-rightElbowAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(leftElbowAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-rightArmHingeX, -elbowDrawY));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTextureWithinBounds(this.armImageTexture, alpha, rightArmDrawX, shoulderDrawY,
				rightArmDrawX + armImageTexture.getWidth(), shoulderDrawY + armImageTexture.getHeight(), boundsX1,
				boundsY1, boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.armImageTexture, 0.5f, rightArmDrawX, shoulderDrawY,
					rightArmDrawX + armImageTexture.getWidth(), shoulderDrawY + armImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}
		if (bodyArmor != null && bodyArmor.armUpperTexture != null && drawClothes) {

			TextureUtils.drawTextureWithinBounds(bodyArmor.armUpperTexture, alpha, rightArmDrawX, shoulderDrawY,
					rightArmDrawX + bodyArmor.armUpperTexture.getWidth(),
					shoulderDrawY + bodyArmor.armUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
					false, false, color);
			if (highlight) {

				TextureUtils.drawTexture(bodyArmor.armUpperTexture, 0.5f, rightArmDrawX, shoulderDrawY,
						rightArmDrawX + bodyArmor.armUpperTexture.getWidth(),
						shoulderDrawY + bodyArmor.armUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		Game.flush();
		view.translate(new Vector2f(rightArmHingeX, shoulderDrawY));
		if (!reverseRotation)
			view.rotate(-rightShoulderAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(leftShoulderAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-rightArmHingeX, -shoulderDrawY));
		Game.activeBatch.updateUniforms();

	}

	public void drawLeftArm(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {

		if (armImageTexture == null)
			return;

		float rightShoulderAngle = 0f;
		float rightElbowAngle = 0f;
		float leftShoulderAngle = 0f;
		float leftElbowAngle = 0f;
		if (primaryAnimation != null && useAnimation) {
			rightShoulderAngle = primaryAnimation.rightShoulderAngle;
			rightElbowAngle = primaryAnimation.rightElbowAngle;
			leftShoulderAngle = primaryAnimation.leftShoulderAngle;
			leftElbowAngle = primaryAnimation.leftElbowAngle;
		}

		// arms
		float shoulderDrawY = y + this.shoulderY;
		float elbowDrawY = y + this.elbowY;

		// backwards = true;

		// left arm
		float leftArmDrawX = x + this.leftArmDrawX;
		float leftArmHingeX = x + this.leftArmHingeX;

		Matrix4f view = Game.activeBatch.getViewMatrix();
		Game.flush();
		view.translate(new Vector2f(leftArmHingeX, shoulderDrawY));
		if (!reverseRotation)
			view.rotate(leftShoulderAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-rightShoulderAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-leftArmHingeX, -shoulderDrawY));
		Game.activeBatch.updateUniforms();

		Game.flush();
		view.translate(new Vector2f(leftArmHingeX, elbowDrawY));
		if (!reverseRotation)
			view.rotate(leftElbowAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-rightElbowAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-leftArmHingeX, -elbowDrawY));
		Game.activeBatch.updateUniforms();

		if (equipped != null && !backwards && !sleeping && primaryAnimation != null
				&& primaryAnimation.drawArrowInOffHand == true && useAnimation) {
			drawArrow(leftArmHingeX - Templates.ARROW.anchorX, y + handY, color, backwards);
		}

		TextureUtils.drawTextureWithinBounds(this.armImageTexture, alpha, leftArmDrawX, elbowDrawY,
				leftArmDrawX + armImageTexture.getWidth(), elbowDrawY + armImageTexture.getHeight(), boundsX1, boundsY1,
				boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.armImageTexture, 0.5f, leftArmDrawX, elbowDrawY,
					leftArmDrawX + armImageTexture.getWidth(), elbowDrawY + armImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}
		if (bodyArmor != null && bodyArmor.armLowerTexture != null && drawClothes) {

			TextureUtils.drawTextureWithinBounds(bodyArmor.armLowerTexture, alpha, leftArmDrawX, elbowDrawY,
					leftArmDrawX + bodyArmor.armLowerTexture.getWidth(),
					elbowDrawY + bodyArmor.armLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, color);
			if (highlight) {

				TextureUtils.drawTexture(bodyArmor.armLowerTexture, 0.5f, leftArmDrawX, elbowDrawY,
						leftArmDrawX + bodyArmor.armLowerTexture.getWidth(),
						elbowDrawY + bodyArmor.armLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		if (equipped != null && backwards && !sleeping
				&& (primaryAnimation == null || primaryAnimation.drawWeapon || !useAnimation)) {
			drawWeapon(leftArmHingeX - (equipped.width - equipped.anchorX), y + handY - equipped.anchorY, alpha,
					highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, backwards, useAnimation);
		}
		if (equipped != null && backwards && !sleeping && primaryAnimation != null
				&& primaryAnimation.drawArrowInMainHand == true && useAnimation) {
			drawArrow(leftArmHingeX - Templates.ARROW.anchorX, y + handY, color, backwards);
		}

		if (equipped != null && equipped.templateId == Templates.HUNTING_BOW.templateId && backwards
				&& (primaryAnimation == null || primaryAnimation.drawWeapon || !useAnimation) && alpha == 1f) {
			drawBowString(leftArmHingeX, y + handY, alpha, color, backwards, useAnimation);
		}

		Game.flush();
		view.translate(new Vector2f(leftArmHingeX, elbowDrawY));
		if (!reverseRotation)
			view.rotate(-leftElbowAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(rightElbowAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-leftArmHingeX, -elbowDrawY));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTextureWithinBounds(this.armImageTexture, alpha, leftArmDrawX, shoulderDrawY,
				leftArmDrawX + armImageTexture.getWidth(), shoulderDrawY + armImageTexture.getHeight(), boundsX1,
				boundsY1, boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.armImageTexture, 0.5f, leftArmDrawX, shoulderDrawY,
					leftArmDrawX + armImageTexture.getWidth(), shoulderDrawY + armImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}

		if (bodyArmor != null && bodyArmor.armUpperTexture != null && drawClothes) {

			TextureUtils.drawTextureWithinBounds(bodyArmor.armUpperTexture, alpha, leftArmDrawX, shoulderDrawY,
					leftArmDrawX + bodyArmor.armUpperTexture.getWidth(),
					shoulderDrawY + bodyArmor.armUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
					false, false, color);
			if (highlight) {
				TextureUtils.drawTexture(bodyArmor.armUpperTexture, 0.5f, leftArmDrawX, shoulderDrawY,
						leftArmDrawX + bodyArmor.armUpperTexture.getWidth(),
						shoulderDrawY + bodyArmor.armUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		Game.flush();
		view.translate(new Vector2f(leftArmHingeX, shoulderDrawY));
		if (!reverseRotation)
			view.rotate(-leftShoulderAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(rightShoulderAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-leftArmHingeX, -shoulderDrawY));
		Game.activeBatch.updateUniforms();

	}

	public void drawWeapon(float x, float y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean backwards, boolean useAnimation) {

		if (primaryAnimation != null && primaryAnimation.drawEquipped == false && useAnimation)
			return;

		TextureUtils.drawTextureWithinBounds(this.equipped.imageTexture, alpha, x, y, x + equipped.width,
				y + equipped.height, boundsX1, boundsY1, boundsX2, boundsY2, backwards, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.equipped.imageTexture, 0.5f, x, y, x + equipped.width, y + equipped.height,
					boundsX1, boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}

		if (fishingTarget != null && equipped instanceof FishingRod && this != Level.player) {
			FishingRod fishingRod = (FishingRod) equipped;
			fishingRod.drawLine(this, x, y, color);
		}
	}

	public void drawFrontLeg(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {
		if (backwards)
			drawRightLeg(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);
		else
			drawLeftLeg(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);
	}

	public void drawBackLeg(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {
		if (backwards)
			drawLeftLeg(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);
		else
			drawRightLeg(x, y, alpha, highlight, boundsX1, boundsY1, boundsX2, boundsY2, color, drawClothes, backwards,
					reverseRotation, useAnimation);

	}

	public void drawLeftLeg(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {

		if (legImageTexture == null)
			return;
		float leftHipAngle = 0f;
		float leftKneeAngle = 0f;
		float rightHipAngle = 0f;
		float rightKneeAngle = 0f;
		if (primaryAnimation != null && useAnimation) {

			leftHipAngle = primaryAnimation.leftHipAngle;
			leftKneeAngle = primaryAnimation.leftKneeAngle;
			rightHipAngle = primaryAnimation.rightHipAngle;
			rightKneeAngle = primaryAnimation.rightKneeAngle;

		}

		// legs
		float hipDrawY = y + this.hipY;
		float kneeDrawY = y + this.kneeY;

		// backwards = true;

		// left leg
		float leftLegDrawX = x + this.leftLegDrawX;
		float leftLegHingeX = x + this.leftLegHingeX;

		Matrix4f view = Game.activeBatch.getViewMatrix();
		Game.flush();
		view.translate(new Vector2f(leftLegHingeX, hipDrawY));
		if (!reverseRotation)
			view.rotate(leftHipAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-rightHipAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-leftLegHingeX, -hipDrawY));
		Game.activeBatch.updateUniforms();

		Game.flush();
		view.translate(new Vector2f(leftLegHingeX, kneeDrawY));
		if (!reverseRotation)
			view.rotate(leftKneeAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-rightKneeAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-leftLegHingeX, -kneeDrawY));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTextureWithinBounds(this.legImageTexture, alpha, leftLegDrawX, kneeDrawY,
				leftLegDrawX + legImageTexture.getWidth(), kneeDrawY + legImageTexture.getHeight(), boundsX1, boundsY1,
				boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.legImageTexture, 0.5f, leftLegDrawX, kneeDrawY,
					leftLegDrawX + legImageTexture.getWidth(), kneeDrawY + legImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}

		if (legArmor != null && legArmor.legLowerTexture != null && drawClothes) {

			TextureUtils.drawTextureWithinBounds(legArmor.legLowerTexture, 1f, leftLegDrawX, kneeDrawY,
					leftLegDrawX + legArmor.legLowerTexture.getWidth(),
					kneeDrawY + legArmor.legLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, color);
			if (highlight) {

				TextureUtils.drawTexture(legArmor.legLowerTexture, 0.5f, leftLegDrawX, kneeDrawY,
						leftLegDrawX + legArmor.legLowerTexture.getWidth(),
						kneeDrawY + legArmor.legLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		Game.flush();
		view.translate(new Vector2f(leftLegHingeX, kneeDrawY));
		if (!reverseRotation)
			view.rotate(-leftKneeAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(rightKneeAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-leftLegHingeX, -kneeDrawY));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTextureWithinBounds(this.legImageTexture, alpha, leftLegDrawX, hipDrawY,
				leftLegDrawX + legImageTexture.getWidth(), hipDrawY + legImageTexture.getHeight(), boundsX1, boundsY1,
				boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.legImageTexture, 0.5f, leftLegDrawX, hipDrawY,
					leftLegDrawX + legImageTexture.getWidth(), hipDrawY + legImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}

		if (legArmor != null && legArmor.legUpperTexture != null && drawClothes) {

			TextureUtils.drawTextureWithinBounds(legArmor.legUpperTexture, alpha, leftLegDrawX, hipDrawY,
					leftLegDrawX + legArmor.legUpperTexture.getWidth(), hipDrawY + legArmor.legUpperTexture.getHeight(),
					boundsX1, boundsY1, boundsX2, boundsY2, false, false, color);
			if (highlight) {
				TextureUtils.drawTexture(legArmor.legUpperTexture, 0.5f, leftLegDrawX, hipDrawY,
						leftLegDrawX + legArmor.legUpperTexture.getWidth(),
						hipDrawY + legArmor.legUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		Game.flush();
		view.translate(new Vector2f(leftLegHingeX, hipDrawY));
		if (!reverseRotation)
			view.rotate(-leftHipAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(rightHipAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-leftLegHingeX, -hipDrawY));
		Game.activeBatch.updateUniforms();

	}

	public void drawRightLeg(int x, int y, float alpha, boolean highlight, float boundsX1, float boundsY1,
			float boundsX2, float boundsY2, Color color, boolean drawClothes, boolean backwards,
			boolean reverseRotation, boolean useAnimation) {

		if (legImageTexture == null)
			return;
		float leftHipAngle = 0f;
		float leftKneeAngle = 0f;
		float rightHipAngle = 0f;
		float rightKneeAngle = 0f;
		if (primaryAnimation != null && useAnimation) {

			leftHipAngle = primaryAnimation.leftHipAngle;
			leftKneeAngle = primaryAnimation.leftKneeAngle;
			rightHipAngle = primaryAnimation.rightHipAngle;
			rightKneeAngle = primaryAnimation.rightKneeAngle;

		}

		// legs
		float hipDrawY = y + this.hipY;
		float kneeDrawY = y + this.kneeY;

		// backwards = true;

		// right leg
		float rightLegDrawX = x + this.rightLegDrawX;
		float rightLegHingeX = x + this.rightLegHingeX;

		Matrix4f view = Game.activeBatch.getViewMatrix();
		Game.flush();
		view.translate(new Vector2f(rightLegHingeX, hipDrawY));
		if (!reverseRotation)
			view.rotate(rightHipAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-leftHipAngle, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-rightLegHingeX, -hipDrawY));
		Game.activeBatch.updateUniforms();

		Game.flush();
		view.translate(new Vector2f(rightLegHingeX, kneeDrawY));
		if (!reverseRotation)
			view.rotate(rightKneeAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(-leftKneeAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-rightLegHingeX, -kneeDrawY));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTextureWithinBounds(this.legImageTexture, alpha, rightLegDrawX, kneeDrawY,
				rightLegDrawX + legImageTexture.getWidth(), kneeDrawY + legImageTexture.getHeight(), boundsX1, boundsY1,
				boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.legImageTexture, 0.5f, rightLegDrawX, kneeDrawY,
					rightLegDrawX + legImageTexture.getWidth(), kneeDrawY + legImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}

		if (legArmor != null && legArmor.legLowerTexture != null && drawClothes) {

			TextureUtils.drawTextureWithinBounds(legArmor.legLowerTexture, 1f, rightLegDrawX, kneeDrawY,
					rightLegDrawX + legArmor.legLowerTexture.getWidth(),
					kneeDrawY + legArmor.legLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, color);
			if (highlight) {

				TextureUtils.drawTexture(legArmor.legLowerTexture, 0.5f, rightLegDrawX, kneeDrawY,
						rightLegDrawX + legArmor.legLowerTexture.getWidth(),
						kneeDrawY + legArmor.legLowerTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		Game.flush();
		view.translate(new Vector2f(rightLegHingeX, kneeDrawY));
		if (!reverseRotation)
			view.rotate(-rightKneeAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(leftKneeAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-rightLegHingeX, -kneeDrawY));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTextureWithinBounds(this.legImageTexture, alpha, rightLegDrawX, hipDrawY,
				rightLegDrawX + legImageTexture.getWidth(), hipDrawY + legImageTexture.getHeight(), boundsX1, boundsY1,
				boundsX2, boundsY2, false, false, color);
		if (highlight) {
			TextureUtils.drawTexture(this.legImageTexture, 0.5f, rightLegDrawX, hipDrawY,
					rightLegDrawX + legImageTexture.getWidth(), hipDrawY + legImageTexture.getHeight(), boundsX1,
					boundsY1, boundsX2, boundsY2, backwards, false, flashColor, false);

		}

		if (legArmor != null && legArmor.legUpperTexture != null & drawClothes) {

			TextureUtils.drawTextureWithinBounds(legArmor.legUpperTexture, alpha, rightLegDrawX, hipDrawY,
					rightLegDrawX + legArmor.legUpperTexture.getWidth(),
					hipDrawY + legArmor.legUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, color);
			if (highlight) {
				TextureUtils.drawTexture(legArmor.legUpperTexture, 0.5f, rightLegDrawX, hipDrawY,
						rightLegDrawX + legArmor.legUpperTexture.getWidth(),
						hipDrawY + legArmor.legUpperTexture.getHeight(), boundsX1, boundsY1, boundsX2, boundsY2,
						backwards, false, flashColor, false);

			}
		}

		Game.flush();
		view.translate(new Vector2f(rightLegHingeX, hipDrawY));
		if (!reverseRotation)
			view.rotate(-rightHipAngle, new Vector3f(0f, 0f, 1f));
		else
			view.rotate(leftHipAngle, new Vector3f(0f, 0f, 1f));

		view.translate(new Vector2f(-rightLegHingeX, -hipDrawY));
		Game.activeBatch.updateUniforms();

	}

	public void drawArrow(float handX, float handY, Color color, boolean backwards) {
		float alpha = 1.0f;

		float arrowY = handY - primaryAnimation.arrowHandleY;

		if (backwards) {
			TextureUtils.drawTexture(Templates.ARROW.textureLoaded, alpha,
					handX + Templates.ARROW.textureLoaded.getWidth(), arrowY, handX,
					arrowY + Templates.ARROW.textureLoaded.getHeight(), color);

		} else {
			TextureUtils.drawTexture(Templates.ARROW.textureLoaded, alpha, handX, arrowY,
					handX + Templates.ARROW.textureLoaded.getWidth(),
					arrowY + Templates.ARROW.textureLoaded.getHeight(), color);

		}
	}

	Color bowStringColor = new Color(0f, 0f, 0f, 0.32f);

	public void drawBowString(float handX, float handY, float alpha, Color color, boolean backwards,
			boolean useAnimation) {

		float bowStringY = handY - 16;
		float bowStringYPulled = handY - 16;
		if (primaryAnimation != null && useAnimation)
			bowStringYPulled += primaryAnimation.bowStringHandleY;

		float boStringX = handX;

		if (backwards) {
			LineUtils.drawLine(color, boStringX + 56, bowStringY, boStringX, bowStringYPulled, 1);
			LineUtils.drawLine(color, boStringX, bowStringYPulled, boStringX - 56, bowStringY, 1);
		} else {
			LineUtils.drawLine(color, boStringX + 56, bowStringY, boStringX, bowStringYPulled, 1);
			LineUtils.drawLine(color, boStringX, bowStringYPulled, boStringX - 56, bowStringY, 1);
		}
	}

	public void drawFishing(boolean useAnimation) {
		int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels + drawOffsetX);
		int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels + drawOffsetY);
		if (primaryAnimation != null && useAnimation) {
			actorPositionXInPixels += primaryAnimation.offsetX;
			actorPositionYInPixels += primaryAnimation.offsetY;
		}
		if (equipped != null && !sleeping) {

			int weaponPositionXInPixels = (int) (actorPositionXInPixels + rightArmHingeX - equipped.anchorX);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels + handY - equipped.anchorY);

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

		// if (this.remainingHealth <= 0)
		// return;

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
					Integer.MAX_VALUE, false, null, Color.WHITE,
					new Object[] { new StringWithColor(miniDialogue, Color.BLACK) });
		} else {

			// bubble
			if (remainingHealth > 0 && !hiding
					&& (thoughtBubbleImageTextureObject != null || thoughtBubbleImageTextureAction != null)) {

				float torsoAngle = 0;
				if (primaryAnimation != null) {
					torsoAngle = primaryAnimation.torsoAngle;
				}
				Matrix4f view = Game.activeBatch.getViewMatrix();
				Game.flush();
				view.translate(new Vector2f(actorPositionXInPixels + halfWidth, actorPositionYInPixels + hipY));
				view.rotate(torsoAngle, new Vector3f(0f, 0f, 1f));

				view.translate(new Vector2f(-(actorPositionXInPixels + halfWidth), -(actorPositionYInPixels + hipY)));
				Game.activeBatch.updateUniforms();

				int expressionBubbleWidth = 64;
				int expressionBubbleHeight = 64;
				float expressionBubblePositionXInPixels = this.squareGameObjectIsOn.xInGridPixels
						+ Game.SQUARE_WIDTH * drawOffsetRatioX;
				float expressionBubblePositionYInPixels = this.squareGameObjectIsOn.yInGridPixels
						+ Game.SQUARE_WIDTH * drawOffsetRatioY - 64;
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

					float expressionPositionXInPixels = expressionBubblePositionXInPixels + (32 - expressionWidth / 2);
					float expressionPositionYInPixels = expressionBubblePositionYInPixels + 32;
					float alpha = 1.0f;

					// TextureUtils.skipNormals = true;

					if (primaryAnimation != null)
						alpha = primaryAnimation.alpha;
					if (!this.squareGameObjectIsOn.visibleToPlayer)
						alpha = 0.5f;

					// thoughtBubbleImageTextureActionColor

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

					float expressionPositionXInPixels = expressionBubblePositionXInPixels + (32 - expressionWidth / 2);
					float expressionPositionYInPixels = expressionBubblePositionYInPixels + 32;
					float alpha = 1.0f;

					// TextureUtils.skipNormals = true;

					if (primaryAnimation != null)
						alpha = primaryAnimation.alpha;
					if (!this.squareGameObjectIsOn.visibleToPlayer)
						alpha = 0.5f;
					TextureUtils.drawTexture(thoughtBubbleImageTextureAction, alpha, expressionPositionXInPixels,
							expressionPositionYInPixels, expressionPositionXInPixels + expressionWidth / 2,
							expressionPositionYInPixels + expressionHeight / 2, thoughtBubbleImageTextureActionColor);
					// TextureUtils.skipNormals = false;
				}

				Game.flush();
				view.translate(new Vector2f(actorPositionXInPixels + halfWidth, actorPositionYInPixels + hipY));
				view.rotate(-torsoAngle, new Vector3f(0f, 0f, 1f));

				view.translate(new Vector2f(-(actorPositionXInPixels + halfWidth), -(actorPositionYInPixels + hipY)));
				Game.activeBatch.updateUniforms();
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

	public Button getButtonFromMousePosition(float alteredMouseX, float alteredMouseY) {

		return null;
	}

	public void weaponButtonClicked(Weapon weapon) {
		this.equip(weapon);
	}

	// @Override
	// public void updateRealtime(int delta) {
	// super.updateRealtime(delta);
	//
	// // START FISHING
	// if (fishingTarget != null && equipped instanceof FishingRod) {
	// int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
	// + Game.SQUARE_WIDTH * drawOffsetRatioX);
	// int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
	// + Game.SQUARE_HEIGHT * drawOffsetRatioY);
	// if (primaryAnimation != null) {
	// actorPositionXInPixels += primaryAnimation.offsetX;
	// actorPositionYInPixels += primaryAnimation.offsetY;
	// }
	// int weaponPositionXInPixels = (int) (actorPositionXInPixels + rightArmHingeX
	// - equipped.anchorX);
	// int weaponPositionYInPixels = (int) (actorPositionYInPixels + handY -
	// equipped.anchorY);
	// FishingRod fishingRod = (FishingRod) equipped;
	// fishingRod.updateLine(this, weaponPositionXInPixels, weaponPositionYInPixels,
	// delta);
	// if (this == Game.level.player) {
	// fishingRod.updateFishingMinigame(this, weaponPositionXInPixels,
	// weaponPositionYInPixels, delta);
	// }
	// }
	// // END FISHING
	// }

	public void update(int delta) {

		if (this.remainingHealth > 0) {

			if (!(this instanceof Actor))
				clearActions();

			// Remove dead attackers from attackers list
			ArrayList<GameObject> gameObjectsToRemoveFromList = new ArrayList<GameObject>(GameObject.class);
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

		if (squareGameObjectIsOn != null) {
			HidingPlace hidingPlaceAtSameSquare = (HidingPlace) squareGameObjectIsOn.inventory
					.getGameObjectOfClass(HidingPlace.class);
			if (hiding) {
				if (hidingPlaceAtSameSquare == null || hidingPlaceAtSameSquare.remainingHealth <= 0) {
					new ActionStopHiding(this, this.hidingPlace).perform();
				} else if (hidingPlace == hidingPlaceAtSameSquare) {
					// still have same hiding place, do nothing
				} else {
					hidingPlace.gameObjectsHiddenHere.remove(this);
					hidingPlace = (HidingPlace) squareGameObjectIsOn.inventory.getGameObjectOfClass(HidingPlace.class);
					hidingPlace.gameObjectsHiddenHere.add(this);
				}
			} else {
				if (hidingPlaceAtSameSquare != null && hidingPlaceAtSameSquare.remainingHealth > 0) {
					new ActionHide(this, hidingPlaceAtSameSquare).perform();
				}
			}

			// If hiding in a place get the effects
			if (hidingPlace != null) {
				for (Effect effect : hidingPlace.effectsFromInteracting) {
					addEffect(effect.makeCopy(hidingPlace, this));
				}
			}
		}

		runEffects();
		// super.update(delta);

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

		if (remainingHealth <= 0) {
			return new ActionOpenOtherInventory(performer, this);
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

		ArrayList<Action> actions = new ArrayList<Action>(Action.class);

		// if (this.remainingHealth <= 0)
		// return actions;

		if (this == Game.level.player) {
			if (Game.level.player.peekingThrough != null) {
				actions.add(new ActionStopPeeking(performer));
			}

			actions.add(new ActionWait(performer, performer.squareGameObjectIsOn));

			if (equipped != null) {
				actions.addAll(equipped.getAllActionsForEquippedItem(performer));
			}

			actions.add(new ActionOpenInventoryToPourItems(performer, this));
			actions.add(new ActionIgnite(performer, this, null));
			actions.add(new ActionDouse(performer, this));

			if (hiding) {
				actions.add(new ActionStopHiding(this, this.hidingPlace));
			} else {
				HidingPlace hidingPlaceStandingOn = (HidingPlace) this.squareGameObjectIsOn.inventory
						.getGameObjectOfClass(HidingPlace.class);
				if (hidingPlaceStandingOn != null)
					actions.add(new ActionHide(this, hidingPlaceStandingOn));
			}
			actions.add(new ActionViewInfo(performer, this));
		} else {
			// Talk
			if (remainingHealth > 0 && this.getConversation() != null)
				actions.add(new ActionTalk(performer, this));
			// Pet
			if (remainingHealth > 0 && this instanceof Animal) {
				actions.add(new ActionPet(performer, this));
			}
			if (remainingHealth <= 0) {
				actions.add(new ActionOpenOtherInventory(performer, this));
			}
			actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		}

		actions.add(new ActionMove(performer, this.squareGameObjectIsOn, true));

		return actions;
	}

	public boolean hasAttackers() {
		return attackers.size() > 0;
	}

	public boolean hasNearbyAttackers() {

		for (GameObject attacker : attackers) {
			if (straightLineDistanceTo(attacker.squareGameObjectIsOn) <= 10) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<GameObject> getAttackers() {
		return attackers;
	}

	@Override
	public Conversation getConversation() {

		if (remainingHealth <= 0)
			return null;

		Quest quest;
		if (groupOfActors != null) {
			quest = groupOfActors.quest;
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

		if (!(this instanceof Animal) && !(this instanceof Monster)) {
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

		if (groupOfActors != null) {
			for (Actor actor : groupOfActors.getMembers()) {
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

		if (square == null)
			return false;

		if (this == Game.level.player)
			return square.visibleToPlayer;

		return canSeeSquareFromSpecificSquare(this.squareGameObjectIsOn, square);
	}

	public boolean hasKeyForDoor(Openable door) {
		if (door.keys == null)
			return false;

		for (GameObject gameObject : inventory.getGameObjects()) {
			for (GameObject key : door.keys) {
				if (key == gameObject) {
					return true;
				}
			}
		}

		return false;
	}

	public GameObject getKeyFor(Openable door) {
		if (door.keys == null)
			return null;

		for (GameObject gameObject : inventory.getGameObjects()) {
			for (GameObject key : door.keys) {
				if (key == gameObject) {
					return key;
				}
			}
		}

		return null;
	}

	public void clearActions() {
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

		ArrayList<GameObject> hidingPlacesToRemove = new ArrayList<GameObject>(GameObject.class);
		for (GameObject attacker : this.getAttackers()) {
			if (attacker instanceof HidingPlace) {
				hidingPlacesToRemove.add(attacker);
			}
		}
		this.attackers.removeAll(hidingPlacesToRemove);
	}

	public void addInvestigation(GameObject actor, Square square, int priority) {

		Investigation existingInvestigation = this.investigationsMap.get(actor);
		if (existingInvestigation == null) {
			this.investigationsMap.put(actor, new Investigation(actor, square, priority));
		} else if (existingInvestigation.priority <= priority) {
			this.investigationsMap.put(actor, new Investigation(actor, square, priority));
		}
	}

	public void equip(GameObject gameObject) {
		if (canEquipWeapons) {
			this.equipped = gameObject;
		}
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
					ArrayList<GameObject> newStack = new ArrayList<GameObject>(GameObject.class);
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
		ArrayList<Crime> crimesToRemove = new ArrayList<Crime>(Crime.class);
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

		if (this.groupOfActors != null && this.groupOfActors.contains(crime.performer))
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
			ArrayList<Crime> newCrimeList = new ArrayList<Crime>(Crime.class);
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

		if (accumulatedCrimeSeverityUnresolved.get(criminal) != null) {
			accumulatedCrimeSeverityUnresolved.put(criminal,
					accumulatedCrimeSeverityUnresolved.get(criminal) - crime.type.severity);

			if (accumulatedCrimeSeverityUnresolved.get(criminal) == 0)
				accumulatedCrimeSeverityUnresolved.remove(criminal);
		}

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

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels + drawOffsetX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels + drawOffsetY);
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

	protected Class[] soundClassesToReactTo = new Class[] { Weapon.class, BrokenGlass.class };
	public int walkPhase = 0;

	public void createSearchLocationsBasedOnSound(Sound sound) {

		ArrayList<Class> classesArrayList = new ArrayList<Class>(Class.class);
		classesArrayList.addAll(Arrays.asList(soundClassesToReactTo));

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

		actor.travelDistance = travelDistance;
		actor.sight = sight;
		actor.canOpenDoors = canOpenDoors;
		actor.canEquipWeapons = canEquipWeapons;
		actor.hairImageTexture = hairImageTexture;
		actor.armImageTexture = armImageTexture;
		actor.torsoImageTexture = torsoImageTexture;
		actor.pelvisImageTexture = pelvisImageTexture;
		actor.type = type;
		if (aiRoutine != null)
			actor.aiRoutine = aiRoutine.getInstance(actor);
		actor.init(gold, mustHaves, mightHaves);
	}

	@Override
	public float getEffectiveHighLevelStat(HIGH_LEVEL_STATS statType) {
		// Stat stat = highLevelStats.get(statType);
		// return stat.value;

		float result = highLevelStats.get(statType).value;
		if (equipped != null && equipped.highLevelStats.get(statType).value != 0) {
			result += equipped.highLevelStats.get(statType).value;
		}
		if (helmet != null && helmet.highLevelStats.get(statType).value != 0) {
			result += helmet.highLevelStats.get(statType).value;
		}
		if (bodyArmor != null && bodyArmor.highLevelStats.get(statType).value != 0) {
			result += bodyArmor.highLevelStats.get(statType).value;
		}
		if (legArmor != null && legArmor.highLevelStats.get(statType).value != 0) {
			result += legArmor.highLevelStats.get(statType).value;
		}

		if (result < 0 && Stat.OFFENSIVE_STATS.contains(statType))
			result = 0;

		return result;
	}

	@Override
	public ArrayList<Object> getEffectiveHighLevelStatTooltip(HIGH_LEVEL_STATS statType) {
		ArrayList<Object> result = new ArrayList<Object>(Object.class);

		result.add("Inherent " + highLevelStats.get(statType).value);
		if (equipped != null && equipped.highLevelStats.get(statType).value != 0) {
			result.add(TextUtils.NewLine.NEW_LINE);
			result.addAll(equipped.getEffectiveHighLevelStatTooltip(statType));
		}
		if (helmet != null && helmet.highLevelStats.get(statType).value != 0) {
			result.add(TextUtils.NewLine.NEW_LINE);
			result.addAll(helmet.getEffectiveHighLevelStatTooltip(statType));
		}
		if (bodyArmor != null && bodyArmor.highLevelStats.get(statType).value != 0) {
			result.add(TextUtils.NewLine.NEW_LINE);
			result.addAll(bodyArmor.getEffectiveHighLevelStatTooltip(statType));
		}
		if (legArmor != null && legArmor.highLevelStats.get(statType).value != 0) {
			result.add(TextUtils.NewLine.NEW_LINE);
			result.addAll(legArmor.getEffectiveHighLevelStatTooltip(statType));
		}
		return result;
	}

	public boolean hasPower(Class powerClazz) {
		for (Power power : powers) {
			if (powerClazz.isInstance(power)) {
				return true;
			}
		}
		return false;
	}

	public Power getPower(Class powerClazz) {
		for (Power power : powers) {
			if (powerClazz.isInstance(power)) {
				return power;
			}
		}
		return null;
	}
}
