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
	public boolean inRange = true;
	public boolean legal = true;
	public Sound sound;
	public boolean movement = false;
	public Texture image;
	public static Texture x;
	public static Texture textureEllipse;
	public static Texture textureWalk;

	public Action(String actionName) {
		super();
		this.actionName = actionName;
	}

	public Action(String actionName, String imageName) {
		super();
		this.actionName = actionName;
		this.image = getGlobalImage(imageName);
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
		getGlobalImage("action_add_map_marker.png");
		getGlobalImage("action_attack.png");
		getGlobalImage("action_blunt.png");
		getGlobalImage("action_burn.png");
		getGlobalImage("action_chop.png");
		getGlobalImage("action_close.png");
		getGlobalImage("action_die.png");// untested
		getGlobalImage("action_dig.png");
		getGlobalImage("action_drop.png");
		getGlobalImage("action_eat.png");
		getGlobalImage("action_equip.png");
		getGlobalImage("action_electric.png");
		getGlobalImage("action_fill_container.png");
		getGlobalImage("action_give.png");
		getGlobalImage("action_heal.png");
		getGlobalImage("action_hide.png");
		getGlobalImage("left.png");
		getGlobalImage("action_lock.png");
		getGlobalImage("action_loiter.png");
		getGlobalImage("action_loot_all.png");
		getGlobalImage("action_mine.png");
		textureWalk = getGlobalImage("action_move.png");
		getGlobalImage("action_open.png");
		getGlobalImage("action_pet.png");
		getGlobalImage("action_pierce.png");
		getGlobalImage("action_pickpocket_all.png");// untested
		getGlobalImage("action_pick_up.png");
		getGlobalImage("action_posion.png");
		getGlobalImage("action_pour.png");
		getGlobalImage("action_put.png");// untested
		getGlobalImage("action_read.png");
		getGlobalImage("action_ring.png");
		getGlobalImage("right.png");
		getGlobalImage("action_scream.png");// untested
		textureEllipse = getGlobalImage("action_select_object.png");
		getGlobalImage("action_search.png");
		getGlobalImage("action_slash.png");
		getGlobalImage("action_sleep.png");// untested
		getGlobalImage("action_smash.png");
		getGlobalImage("action_spot.png");// untested
		getGlobalImage("action_squash.png");
		getGlobalImage("action_stop_hiding.png");
		getGlobalImage("action_take.png");
		getGlobalImage("action_take_all.png");
		getGlobalImage("action_take_bite.png");// untested
		getGlobalImage("action_talk.png");
		getGlobalImage("action_teleport.png");
		getGlobalImage("action_throw.png");
		getGlobalImage("action_trespass.png"); // untested
		getGlobalImage("action_unlock.png");
		getGlobalImage("action_use.png");
		getGlobalImage("action_write.png"); // untested
		getGlobalImage("star.png");
		x = getGlobalImage("x.png");

	}
}
