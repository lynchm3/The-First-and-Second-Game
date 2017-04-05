package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;

public abstract class Action {

	public String actionName;
	public boolean enabled = true;
	public boolean legal = true;
	public Sound sound;

	public Action(String actionName) {
		super();
		this.actionName = actionName;
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

	public void notifyWitnessesOfCrime(Crime crime) {
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
				if (potentialWitness != null) {
					if (potentialWitness
							.straightLineDistanceTo(crime.performer.squareGameObjectIsOn) <= potentialWitness.sight
							&& potentialWitness.visibleFrom(crime.performer.squareGameObjectIsOn)) {
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

}
