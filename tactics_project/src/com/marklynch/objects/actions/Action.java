package com.marklynch.objects.actions;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.utils.Texture;

public abstract class Action {

	public GameObject gameObjectPerformer;

	public String actionName;
	public boolean enabled = true;
	// public boolean inRange = true;
	public boolean legal = true;
	public Sound sound;
	public boolean movement = false;
	public Texture image;

	public static Texture textureAttack;
	public static Texture textureBow;
	public static Texture textureBird;
	public static Texture textureEllipse;
	public static Texture textureFishing;
	public static Texture textureHelp;
	public static Texture textureLeft;
	public static Texture textureMine;
	public static Texture textureMusic;
	public static Texture texturePatrol;
	public static Texture textureRight;
	public static Texture textureWalk;
	public static Texture textureWrite;
	public static Texture textureX;
	public static Texture textureSleep;

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
	public static final String NEED_MATCHES_OR_IGNITE_POWER = "Need Fire source or Ignite Power";
	public static final String NO_SPACE = "No space";
	public static final String NOT_ENOUGH_GOLD = "Not enough gold";
	public static final String NOT_ENOUGH_TRUST = "Not enough trust";
	public static final String NOT_LOCKED = "Not locked";
	public static final String TOO_HEAVY = "Too heavy";

	// Illegal Reason
	public String illegalReason = null;
	public static final String ASSAULT = "Assault";
	public static final String THEFT = "Theft";
	public static final String TRESPASSING = "Trespassing";
	public static final String VANDALISM = "VANDALISM";

	public Action(String actionName) {
		super();
		this.actionName = actionName;
	}

	public Action(String actionName, String imageName) {
		super();
		this.actionName = actionName;
		this.image = getGlobalImage(imageName, false);
	}

	public void perform() {
		// Cancel fishing
		if (gameObjectPerformer != null && gameObjectPerformer.fishingTarget != null
				&& !(this instanceof ActionFishingStart) && !(this instanceof ActionFishingCompleted)
				&& !(this instanceof ActionFishingInProgress) && !(this instanceof ActionFishingFailed)) {

			FishingRod fishingRod = (FishingRod) gameObjectPerformer.equipped;
			// gameObjectPerformer.fishingTarget.primaryAnimation = null;
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
	// // public ArrayList<Actor> witnesses;
	// // public boolean resolved;
	//
	// }

	public static void notifyWitnessesOfCrime(Crime crime) {
		crime.notifyWitnessesOfCrime();
	}

	public void trespassingCheck(Action action, Actor performer, Square square) {
		if (square.restricted() == true && !square.owners.contains(performer)) {
			float loudness = 0;
			if (action.sound != null)
				loudness = action.sound.loudness;
			ActionTrespass actionTrespass = new ActionTrespass(performer, square, loudness);
			Crime crime = null;

			Crime.TYPE trespassingType = Crime.TYPE.CRIME_TRESPASSING;
			if (square.restricted == false && square.restrictedAtNight == true && Game.level.hour == 22
					&& Game.level.minute < 10) {
				trespassingType = Crime.TYPE.CRIME_TRESPASSING_LEEWAY;
			}

			if (square.owners.size() > 0)
				crime = new Crime(actionTrespass, performer, square.owners.get(0), trespassingType);
			else
				crime = new Crime(actionTrespass, performer, null, trespassingType);

			performer.crimesPerformedThisTurn.add(crime);
			performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	public static void loadActionImages() {
		getGlobalImage("action_add_map_marker.png", false);
		textureAttack = getGlobalImage("action_attack.png", false);
		textureBird = getGlobalImage("bird.png", false);
		getGlobalImage("action_blunt.png", false);
		textureBow = getGlobalImage("action_bow.png", false);
		getGlobalImage("action_burn.png", false);
		getGlobalImage("action_buy.png", false);
		getGlobalImage("action_chop.png", false);
		getGlobalImage("action_close.png", false);
		getGlobalImage("action_die.png", false);// untested
		getGlobalImage("action_dig.png", false);
		getGlobalImage("action_drop.png", false);
		getGlobalImage("action_eat.png", false);
		getGlobalImage("action_equip.png", false);
		getGlobalImage("action_electrical.png", false);
		getGlobalImage("action_fill_container.png", false);
		textureFishing = getGlobalImage("action_fishing.png", false);
		getGlobalImage("action_give.png", false);
		getGlobalImage("action_heal.png", false);
		textureHelp = getGlobalImage("help.png", false);
		getGlobalImage("action_hide.png", false);
		textureLeft = getGlobalImage("left.png", false);
		getGlobalImage("action_lock.png", false);
		getGlobalImage("action_loiter.png", false);
		getGlobalImage("action_loot_all.png", false);
		textureMine = getGlobalImage("action_mine.png", false);
		textureWalk = getGlobalImage("action_move.png", false);
		textureMusic = getGlobalImage("music.png", false);
		getGlobalImage("action_open.png", false);
		getGlobalImage("action_pet.png", false);
		texturePatrol = getGlobalImage("action_patrol.png", false);
		getGlobalImage("action_pierce.png", false);
		getGlobalImage("action_pickpocket_all.png", false);// untested
		getGlobalImage("action_pick_up.png", false);
		getGlobalImage("action_posion.png", false);
		getGlobalImage("action_pour.png", false);
		getGlobalImage("action_put.png", false);// untested
		getGlobalImage("action_read.png", false);
		getGlobalImage("action_ring.png", false);
		textureRight = getGlobalImage("right.png", false);
		getGlobalImage("action_scream.png", false);// untested
		textureEllipse = getGlobalImage("action_select_object.png", false);
		getGlobalImage("action_search.png", false);
		getGlobalImage("action_sell.png", false);
		getGlobalImage("action_slash.png", false);
		textureSleep = getGlobalImage("action_sleep.png", false);// untested
		getGlobalImage("action_smash.png", false);
		getGlobalImage("action_spot.png", false);// untested
		getGlobalImage("action_squash.png", false);
		getGlobalImage("action_stop_hiding.png", false);
		getGlobalImage("action_take.png", false);
		getGlobalImage("action_take_all.png", false);
		getGlobalImage("action_take_bite.png", false);// untested
		getGlobalImage("action_talk.png", false);
		getGlobalImage("action_teleport.png", false);
		getGlobalImage("action_throw.png", false);
		getGlobalImage("action_trespass.png", false); // untested
		getGlobalImage("action_unlock.png", false);
		getGlobalImage("action_use.png", false);
		textureWrite = getGlobalImage("action_write.png", false); // untested
		getGlobalImage("star.png", false);
		textureX = getGlobalImage("x.png", false);

	}

	public boolean standardAttackLegalityCheck(GameObject performer, GameObject target) {

		if (target == null)
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
			if (!(target instanceof Monster) && !(target instanceof AggressiveWildAnimal)) {
				illegalReason = ASSAULT;
				return false;
			}
		}

		return true;

	}
}
