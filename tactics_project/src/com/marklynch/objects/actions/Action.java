package com.marklynch.objects.actions;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Texture;

public abstract class Action {

	public String actionName;
	public boolean enabled = true;
	public boolean legal = true;
	public Sound sound;
	public boolean movement = false;
	public Texture image;
	public static Texture x;

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
		int searchBoxX1 = crime.performer.squareGameObjectIsOn.xInGrid - 10;
		if (searchBoxX1 < 0)
			searchBoxX1 = 0;

		int searchBoxX2 = crime.performer.squareGameObjectIsOn.xInGrid + 10;
		if (searchBoxX2 > Game.level.width - 1)
			searchBoxX2 = Game.level.width - 1;

		int searchBoxY1 = crime.performer.squareGameObjectIsOn.yInGrid - 10;
		if (searchBoxY1 < 0)
			searchBoxY1 = 0;

		int searchBoxY2 = crime.performer.squareGameObjectIsOn.yInGrid + 10;
		if (searchBoxY2 > Game.level.height - 1)
			searchBoxY2 = Game.level.height - 1;

		for (int i = searchBoxX1; i <= searchBoxX2; i++) {
			for (int j = searchBoxY1; j <= searchBoxY2; j++) {
				Actor potentialWitness = (Actor) Game.level.squares[i][j].inventory.getGameObjectOfClass(Actor.class);
				if (potentialWitness != null && potentialWitness != crime.performer) {
					if (potentialWitness.canSeeGameObject(crime.performer)) {
						crime.witnesses.add(potentialWitness);
						ArrayList<Crime> crimes = potentialWitness.crimesWitnessed.get(crime.performer);
						if (crimes != null) {
							crimes.add(crime);
						} else {
							crimes = new ArrayList<Crime>();
							crimes.add(crime);
							potentialWitness.crimesWitnessed.put(crime.performer, crimes);
						}
					}
				}
			}
		}
		// public ArrayList<Actor> witnesses;
		// public boolean resolved;

	}

	public void trespassingCheck(Action action, Actor performer, Square square) {
		if (square.restricted == true && !square.owners.contains(performer)) {
			float loudness = 0;
			if (action.sound != null)
				loudness = action.sound.loudness;
			ActionTrespass actionTrespass = new ActionTrespass(performer, square, loudness);
			Crime crime = null;
			if (square.owners.size() > 0)
				crime = new Crime(actionTrespass, performer, square.owners.get(0), 0);
			else
				crime = new Crime(actionTrespass, performer, null, 0);

			performer.crimesPerformedThisTurn.add(crime);
			performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	public static void loadActionImages() {
		getGlobalImage("action_attack.png");
		getGlobalImage("action_burn.png");
		getGlobalImage("action_close.png");
		getGlobalImage("action_die.png");// untested
		getGlobalImage("action_drop.png");
		getGlobalImage("action_equip.png");
		getGlobalImage("action_give.png");
		getGlobalImage("action_hide.png");
		getGlobalImage("action_lock.png");
		getGlobalImage("action_loiter.png");
		getGlobalImage("action_loot_all.png");
		getGlobalImage("action_mine.png");
		getGlobalImage("action_move.png");
		getGlobalImage("action_open.png");
		getGlobalImage("action_pickpocket_all.png");// untested
		getGlobalImage("action_pick_up.png");
		getGlobalImage("action_put.png");// untested
		getGlobalImage("action_read.png");
		getGlobalImage("action_ring.png");
		getGlobalImage("action_scream.png");// untested
		getGlobalImage("action_search.png");
		getGlobalImage("action_sleep.png");// untested
		getGlobalImage("action_smash.png");
		getGlobalImage("action_spot.png");// untested
		getGlobalImage("action_stop_hiding.png");
		getGlobalImage("action_take.png");
		getGlobalImage("action_take_all.png");
		getGlobalImage("action_take_bite.png");// untested
		getGlobalImage("action_talk.png");
		getGlobalImage("action_teleport.png");
		getGlobalImage("action_throw.png");
		getGlobalImage("action_trespass.png"); // untested
		getGlobalImage("action_unlock.png");
		getGlobalImage("action_write.png"); // untested
		x = getGlobalImage("x.png");

	}
}
