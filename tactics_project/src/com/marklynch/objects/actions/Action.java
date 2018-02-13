package com.marklynch.objects.actions;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;

public abstract class Action {

	public String actionName;
	public boolean enabled = true;
	// public boolean inRange = true;
	public boolean legal = true;
	public Sound sound;
	public boolean movement = false;
	public Texture image;
	public static Texture x;
	public static Texture textureEllipse;
	public static Texture textureWalk;

	public String disabledReason = "";

	public Action(String actionName) {
		super();
		this.actionName = actionName;
	}

	public Action(String actionName, String imageName) {
		super();
		this.actionName = actionName;
		this.image = getGlobalImage(imageName, false);
	}

	public abstract void perform();

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
		if (square.restricted == true && !square.owners.contains(performer)) {
			float loudness = 0;
			if (action.sound != null)
				loudness = action.sound.loudness;
			ActionTrespass actionTrespass = new ActionTrespass(performer, square, loudness);
			Crime crime = null;
			if (square.owners.size() > 0)
				crime = new Crime(actionTrespass, performer, square.owners.get(0), Crime.TYPE.CRIME_TRESPASSING);
			else
				crime = new Crime(actionTrespass, performer, null, Crime.TYPE.CRIME_TRESPASSING);

			performer.crimesPerformedThisTurn.add(crime);
			performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	public static void loadActionImages() {
		getGlobalImage("action_add_map_marker.png", false);
		getGlobalImage("action_attack.png", false);
		getGlobalImage("action_blunt.png", false);
		getGlobalImage("action_burn.png", false);
		getGlobalImage("action_chop.png", false);
		getGlobalImage("action_close.png", false);
		getGlobalImage("action_die.png", false);// untested
		getGlobalImage("action_dig.png", false);
		getGlobalImage("action_drop.png", false);
		getGlobalImage("action_eat.png", false);
		getGlobalImage("action_equip.png", false);
		getGlobalImage("action_electric.png", false);
		getGlobalImage("action_fill_container.png", false);
		getGlobalImage("action_give.png", false);
		getGlobalImage("action_heal.png", false);
		getGlobalImage("action_hide.png", false);
		getGlobalImage("left.png", false);
		getGlobalImage("action_lock.png", false);
		getGlobalImage("action_loiter.png", false);
		getGlobalImage("action_loot_all.png", false);
		getGlobalImage("action_mine.png", false);
		textureWalk = getGlobalImage("action_move.png", false);
		getGlobalImage("action_open.png", false);
		getGlobalImage("action_pet.png", false);
		getGlobalImage("action_pierce.png", false);
		getGlobalImage("action_pickpocket_all.png", false);// untested
		getGlobalImage("action_pick_up.png", false);
		getGlobalImage("action_posion.png", false);
		getGlobalImage("action_pour.png", false);
		getGlobalImage("action_put.png", false);// untested
		getGlobalImage("action_read.png", false);
		getGlobalImage("action_ring.png", false);
		getGlobalImage("right.png", false);
		getGlobalImage("action_scream.png", false);// untested
		textureEllipse = getGlobalImage("action_select_object.png", false);
		getGlobalImage("action_search.png", false);
		getGlobalImage("action_slash.png", false);
		getGlobalImage("action_sleep.png", false);// untested
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
		getGlobalImage("action_write.png", false); // untested
		getGlobalImage("star.png", false);
		x = getGlobalImage("x.png", false);

	}
}
