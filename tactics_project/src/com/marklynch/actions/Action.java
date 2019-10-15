package com.marklynch.actions;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Monster;
import com.marklynch.objects.actors.WildAnimal;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.utils.Texture;

public abstract class Action {
	// public static Texture textureAttack;
	// public static Texture textureBow;
	// public static Texture textureBird;
	// public static Texture textureFishing;
	// public static Texture textureHelp;
	// public static Texture textureLeft;
	// public static Texture textureMine;
	// public static Texture textureMusic;
	// public static Texture texturePatrol;
	// public static Texture textureRight;
	// public static Texture textureWalk;
	// public static Texture textureWrite;
	// public static Texture textureX;
	// public static Texture textureSleep;

	public static Texture textureDouse;
	public static Texture textureSkin;
	public static Texture textureCheckboxChecked;
	public static Texture textureCheckboxUnchecked;

	public static Texture textureAddMapMarker;
	public static Texture textureAttack;
	public static Texture textureBird;
	public static Texture textureBlunt;
	public static Texture textureBow;
	public static Texture textureBurn;
	public static Texture textureBuy;
	public static Texture textureChop;
	public static Texture textureClose;
	public static Texture textureDie;
	public static Texture textureDig;
	public static Texture textureDrink;
	public static Texture textureDrop;
	public static Texture textureEat;
	public static Texture textureEquip;
	public static Texture textureElectrical;
	// public static Texture textureEllipse;
	public static Texture textureFillContainer;
	public static Texture textureFishing;
	public static Texture textureGive;
	public static Texture textureHeal;
	public static Texture textureHelp;
	public static Texture textureHide;
	public static Texture textureLeft;
	public static Texture textureLock;
	public static Texture textureLoiter;
	public static Texture textureLoot;
	public static Texture textureLootAll;
	public static Texture textureMine;
	public static Texture textureWalk;
	public static Texture textureMusic;
	public static Texture textureOpen;
	public static Texture texturePet;
	public static Texture texturePatrol;
	public static Texture texturePierce;
	public static Texture texturePickpocketAll;
	public static Texture texturePickUp;
	public static Texture texturePoison;
	public static Texture textureEmpty;
	public static Texture texturePut;
	public static Texture textureRead;
	public static Texture textureRing;
	public static Texture textureRight;
	public static Texture textureScream;
	public static Texture textureEllipse;
	public static Texture textureSearch;
	public static Texture textureSell;
	public static Texture textureSlash;
	public static Texture textureSleep;
	public static Texture textureSmash;
	public static Texture textureSpot;
	public static Texture textureSquash;
	public static Texture textureStopHiding;
	public static Texture textureTake;
	public static Texture textureTakeAll;
	public static Texture textureTakeBite;
	public static Texture textureTalk;
	public static Texture textureTeleport;
	public static Texture textureThrow;
	public static Texture textureTrespass;
	public static Texture textureUnlock;
	public static Texture textureUse;
	public static Texture textureWrite;
	public static Texture textureStar;
	public static Texture textureX;

	// Disabled Reason
	public String disabledReason = null;
	public static final String ALREADY_BEING_FISHED = "Already being fished";
	public static final String CANT_BE_ATTACKED = "Can't be attacked";
	public static final String CONTAINER_IS_EMPTY = "Container is empty";
	public static final String DOORWAY_BLOCKED = "Doorway blocked";
	public static final String NEED_A_FISHING_ROD = "Need a fishing rod";
	public static final String NEED_A_KEY = "Need a key";
	public static final String NEED_A_KNIFE = "Need a knife";
	public static final String NEED_A_PICKAXE = "Need a pickaxe";
	public static final String NEED_A_SHOVEL = "Need a shovel";
	public static final String NEED_AN_AXE = "Need an axe";
	public static final String NEED_MATCHES_OR_IGNITE_POWER = "Need fire source or ignite power";
	public static final String NEED_WATER_OR_DOUSE_POWER = "Need water source or douse power";
	public static final String NO_SPACE = "No space";
	public static final String NOT_ENOUGH_GOLD = "Not enough gold";
	public static final String NOT_ENOUGH_TRUST = "Not enough trust";
	public static final String NOT_LOCKED = "Not locked";
	public static final String TOO_HEAVY = "Too heavy";

	// Illegal Reason
	public String illegalReason = null;

	public boolean performed = false;
	public static final String ASSAULT = "Assault";
	public static final String ARSON = "Arson";
	public static final String GRAND_THEFT = "Grand Theft";
	public static final String THEFT = "Theft";
	public static final String TRESPASSING = "Trespassing";
	public static final String VANDALISM = "VANDALISM";

	public Actor performer;
	public GameObject gameObjectPerformer;
	public GameObject targetGameObject;
	public Square targetSquare;
	public String actionName;
	public Object targetGameObjectOrSquare;
	public boolean enabled = true;
	public boolean legal = true;
	public Sound sound;
	public boolean movement = false;
	public Texture image;

	public Action(String actionName) {
		super();
		this.actionName = actionName;
	}

	public Action(String actionName, Texture image, GameObject gameObjectPerformer, Object targetGameObjectOrSquare) {
		super();
		this.gameObjectPerformer = gameObjectPerformer;
		if (gameObjectPerformer instanceof Actor) {
			this.performer = (Actor) this.gameObjectPerformer;
		}

		this.targetGameObjectOrSquare = targetGameObjectOrSquare;
		if (targetGameObjectOrSquare instanceof GameObject) {
			this.targetGameObject = (GameObject) targetGameObjectOrSquare;
			this.targetSquare = this.targetGameObject.squareGameObjectIsOn;
		} else if (targetGameObjectOrSquare instanceof Square) {
			this.targetSquare = (Square) targetGameObjectOrSquare;
			this.targetGameObject = targetSquare.inventory.getGameObjectThatCantShareSquare1();
		}

		this.actionName = actionName;
		this.image = image;
	}

	public void perform() {

		performed = true;

		// Cancel fishing
		if (gameObjectPerformer != null && gameObjectPerformer.fishingTarget != null
				&& !(this instanceof ActionFishingStart) && !(this instanceof ActionFishingCompleted)
				&& !(this instanceof ActionFishingInProgress) && !(this instanceof ActionFishingFailed)) {

			FishingRod fishingRod = (FishingRod) gameObjectPerformer.equipped;
			// performer.fishingTarget.primaryAnimation = null;
			gameObjectPerformer.fishingTarget.beingFishedBy = null;
			gameObjectPerformer.fishingTarget = null;
			fishingRod.reset();

			if (gameObjectPerformer == Level.player) {
				Level.pausePlayer();
				if (Level.player.equippedBeforePickingUpObject != null) {
					Level.player.equipped = Level.player.equippedBeforePickingUpObject;
					Level.player.equippedBeforePickingUpObject = null;
				}
			}
		}
	}

	public abstract boolean check();

	public abstract boolean checkRange();

	public boolean recheck() {
		return this.enabled = check();
	}

	// public abstract boolean withinRange();

	public abstract boolean checkLegality();

	public abstract Sound createSound();

	// public void notifyWitnessesOfCrime(Action action, Actor performer, Actor
	// visctim, int severity) {
	//
	// // public CopyOnWriteArrayList<Actor> witnesses;
	// // public boolean resolved;
	//
	// }

	public static void notifyWitnessesOfCrime(Crime crime) {
		crime.notifyWitnessesOfCrime();
	}

	public void trespassingCheck(Actor performer, Square square) {

		if (performer == null || square == null)
			return;

		if (square.restricted() == true && !square.owners.contains(performer)) {
			Crime crime = null;

			Crime.TYPE trespassingType = Crime.TYPE.CRIME_TRESPASSING;
			if (square.restricted == false && square.restrictedAtNight == true && Game.level.hour == 22
					&& Game.level.minute < 10) {
				trespassingType = Crime.TYPE.CRIME_TRESPASSING_LEEWAY;
			}

			if (square.owners.size() > 0)
				crime = new Crime(performer, square.owners.get(0), trespassingType);
			else
				crime = new Crime(performer, null, trespassingType);

			performer.crimesPerformedThisTurn.add(crime);
			performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	public static void loadActionImages() {

		textureDouse = getGlobalImage("action_douse.png", false);
		textureRing = getGlobalImage("action_ring.png", false);
		textureSkin = getGlobalImage("action_skin.png", false);
		textureCheckboxChecked = getGlobalImage("check_box_checked.png", false);
		textureCheckboxUnchecked = getGlobalImage("check_box_unchecked.png", false);

		textureAddMapMarker = getGlobalImage("action_add_map_marker.png", false);
		textureAttack = getGlobalImage("action_attack.png", false);
		textureBird = getGlobalImage("bird.png", false);
		textureBlunt = getGlobalImage("action_blunt.png", false);
		textureBow = getGlobalImage("action_bow.png", false);
		textureBurn = getGlobalImage("action_burn.png", false);
		textureBuy = getGlobalImage("action_buy.png", false);
		textureChop = getGlobalImage("action_chop.png", false);
		textureClose = getGlobalImage("action_close.png", false);
		textureDie = getGlobalImage("action_die.png", false);// untested
		textureDig = getGlobalImage("action_dig.png", false);
		textureDrink = getGlobalImage("action_eat.png", false);
		textureDrop = getGlobalImage("action_drop.png", false);
		textureEat = getGlobalImage("action_eat.png", false);
		textureEquip = getGlobalImage("action_equip.png", false);
		textureElectrical = getGlobalImage("action_electrical.png", false);
		textureFillContainer = getGlobalImage("action_fill_container.png", false);
		textureFishing = getGlobalImage("action_fishing.png", false);
		textureGive = getGlobalImage("action_give.png", false);
		textureHeal = getGlobalImage("action_heal.png", false);
		textureHelp = getGlobalImage("help.png", false);
		textureHide = getGlobalImage("action_hide.png", false);
		textureLeft = getGlobalImage("left.png", false);
		textureLock = getGlobalImage("action_lock.png", false);
		textureLoiter = getGlobalImage("action_loiter.png", false);
		textureLoot = getGlobalImage("action_loot.png", false);
		textureLootAll = getGlobalImage("action_loot_all.png", false);
		textureMine = getGlobalImage("action_mine.png", false);
		textureWalk = getGlobalImage("action_move.png", false);
		textureMusic = getGlobalImage("music.png", false);
		textureOpen = getGlobalImage("action_open.png", false);
		texturePet = getGlobalImage("action_pet.png", false);
		texturePatrol = getGlobalImage("action_patrol.png", false);
		texturePierce = getGlobalImage("action_pierce.png", false);
		texturePickpocketAll = getGlobalImage("action_pickpocket_all.png", false);// untested
		texturePickUp = getGlobalImage("action_pick_up.png", false);
		texturePoison = getGlobalImage("action_posion.png", false);
		textureEmpty = getGlobalImage("action_empty.png", false);
		texturePut = getGlobalImage("action_put.png", false);// untested
		textureRead = getGlobalImage("action_read.png", false);
		textureRing = getGlobalImage("action_ring.png", false);
		textureRight = getGlobalImage("right.png", false);
		textureScream = getGlobalImage("action_scream.png", false);// untested
		textureEllipse = getGlobalImage("action_select_object.png", false);
		textureSearch = getGlobalImage("action_search.png", false);
		textureSell = getGlobalImage("action_sell.png", false);
		textureSlash = getGlobalImage("action_slash.png", false);
		textureSleep = getGlobalImage("action_sleep.png", false);// untested
		textureSmash = getGlobalImage("action_smash.png", false);
		textureSpot = getGlobalImage("action_spot.png", false);// untested
		textureSquash = getGlobalImage("action_squash.png", false);
		textureStopHiding = getGlobalImage("action_stop_hiding.png", false);
		textureTake = getGlobalImage("action_take.png", false);
		textureTakeAll = getGlobalImage("action_take_all.png", false);
		textureTakeBite = getGlobalImage("action_take_bite.png", false);// untested
		textureTalk = getGlobalImage("action_talk.png", false);
		textureTeleport = getGlobalImage("action_teleport.png", false);
		textureThrow = getGlobalImage("action_throw.png", false);
		textureTrespass = getGlobalImage("action_trespass.png", false); // untested
		textureUnlock = getGlobalImage("action_unlock.png", false);
		textureUse = getGlobalImage("action_use.png", false);
		textureWrite = getGlobalImage("action_write.png", false); // untested
		textureStar = getGlobalImage("star.png", false);
		textureX = getGlobalImage("x.png", false);

	}

	public boolean standardAttackLegalityCheck(GameObject performer, GameObject target) {

		if (target == null)
			return true;

		if (target == performer)
			return true;

		if (performer.attackers.contains(target))
			return true;

		// Is Object
		if (target.owner != null && target.owner != performer) {
			illegalReason = VANDALISM;
			return false;
		}

		// Is human
		if (target instanceof Actor) {
			if (!(target instanceof Monster) && !(target instanceof WildAnimal)) {
				illegalReason = ASSAULT;
				return false;
			}
		}

		return true;

	}

	public boolean shouldContinue() {
		return !performed;
	}

//	public Square getTargetSquare() {
//		return targetSquare;
//	}
}
