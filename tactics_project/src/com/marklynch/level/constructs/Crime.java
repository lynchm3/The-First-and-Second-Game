package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class Crime {

	public Action action;
	public Actor performer;
	public Actor visctim;
	public boolean hasBeenToldToStop;

	public enum TYPE {
		CRIME_TRESPASSING("Trespassing", 1), CRIME_NON_COMPLIANCE("Non Compliance", 1), CRIME_DOUSE("Minor Assault",
				1), CRIME_VOYEURISM("Voyeurism", 2), CRIME_VANDALISM("Vandalism", 3), CRIME_THEFT("Theft",
						4), CRIME_ARSON("Arson", 5), CRIME_MANSLAUGHTER("Manslaughter",
								6), CRIME_ASSAULT("Assault", 10), CRIME_MURDER("Murder", 20), NONE("None", 0);
		public final String name;
		public final int severity;

		private TYPE(String name, int severity) {
			this.name = name;
			this.severity = severity;
		}
	}

	// final public static int TYPE.CRIME_TRESPASSING = 1;
	// final public static int TYPE.CRIME_NON_COMPLIANCE = 1;
	// final public static int TYPE.CRIME_DOUSE = 1;
	// final public static int TYPE.CRIME_VOYEURISM = 2;
	// final public static int TYPE.CRIME_VANDALISM = 3;
	// final public static int TYPE.CRIME_THEFT = 4;
	// final public static int TYPE.CRIME_ARSON = 5;
	// final public static int TYPE.CRIME_MANSLAUGHTER = 5;
	// final public static int TYPE.CRIME_ASSAULT = 10;

	public boolean resolved;
	public GameObject stolenItems[];
	public TYPE type;

	public Crime(Action action, Actor performer, Actor visctim, TYPE type, GameObject... stolenItems) {
		super();
		this.action = action;
		this.performer = performer;
		this.visctim = visctim;
		this.resolved = false;
		this.stolenItems = stolenItems;
		this.type = type;
	}

	public Crime(Action action, Actor performer, Actor visctim, TYPE type, ArrayList<GameObject> stolenItems) {
		this(action, performer, visctim, type, stolenItems.toArray(new GameObject[stolenItems.size()]));
	}

	public void addCrimeListener(CrimeListener wantedPoster) {
		// TODO Auto-generated method stub

	}

	public interface CrimeListener {
		public void crimwUpdate(Crime crime);
	}

	public void resolve() {

	}

	public void notifyWitnessesOfCrime() {

		if (resolved)
			return;
		if (performer.remainingHealth <= 0)
			return;

		int searchBoxX1 = performer.squareGameObjectIsOn.xInGrid - 10;
		if (searchBoxX1 < 0)
			searchBoxX1 = 0;

		int searchBoxX2 = performer.squareGameObjectIsOn.xInGrid + 10;
		if (searchBoxX2 > Game.level.width - 1)
			searchBoxX2 = Game.level.width - 1;

		int searchBoxY1 = performer.squareGameObjectIsOn.yInGrid - 10;
		if (searchBoxY1 < 0)
			searchBoxY1 = 0;

		int searchBoxY2 = performer.squareGameObjectIsOn.yInGrid + 10;
		if (searchBoxY2 > Game.level.height - 1)
			searchBoxY2 = Game.level.height - 1;

		for (int i = searchBoxX1; i <= searchBoxX2; i++) {
			for (int j = searchBoxY1; j <= searchBoxY2; j++) {
				Actor potentialWitness = (Actor) Game.level.squares[i][j].inventory.getGameObjectOfClass(Actor.class);
				if (potentialWitness != null && potentialWitness != performer
						&& !performer.crimesWitnessedUnresolved.contains(this)) {
					if (potentialWitness.canSeeGameObject(performer)) {
						potentialWitness.addWitnessedCrime(this);
					}
				}
			}
		}
	}

}
