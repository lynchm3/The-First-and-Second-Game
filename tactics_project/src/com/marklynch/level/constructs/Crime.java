package com.marklynch.level.constructs;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Crime {

	public enum TYPE {
		CRIME_TRESPASSING_LEEWAY("Trespassing", 0), CRIME_TRESPASSING("Trespassing", 1), CRIME_NON_COMPLIANCE(
				"Non Compliance", 1), CRIME_DOUSE("Minor Assault", 1), CRIME_VOYEURISM("Voyeurism", 2), CRIME_VANDALISM(
						"Vandalism", 3), CRIME_THEFT("Theft", 4), CRIME_ILLEGAL_MINING("Illegal Mining",
								4), CRIME_ARSON("Arson", 5), CRIME_MANSLAUGHTER("Manslaughter",
										6), CRIME_ASSAULT("Assault", 10), CRIME_MURDER("Murder", 20), NONE("None", 0);
		public final String name;
		public final int severity;

		private TYPE(String name, int severity) {
			this.name = name;
			this.severity = severity;
		}
	}

	// public Action action;
	public Actor performer;
	public Actor victim;
	public boolean hasBeenToldToStop;
	public boolean resolved;
	public boolean reported;
	public GameObject stolenItems[];
	public TYPE type;
	public CopyOnWriteArrayList<GameObject> crimeListeners = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public Crime(Actor performer, Actor visctim, TYPE type, GameObject... stolenItems) {
		super();
		this.performer = performer;
		this.victim = visctim;
		this.resolved = false;
		this.stolenItems = stolenItems;
		this.type = type;
	}

	public Crime(Action action, Actor performer, Actor visctim, TYPE type, CopyOnWriteArrayList<GameObject> stolenItems) {
		this(performer, visctim, type, stolenItems.toArray(new GameObject[stolenItems.size()]));
	}

	public void addCrimeListener(GameObject crimeListener) {
		if (!this.crimeListeners.contains(crimeListener)) {
			crimeListeners.add(crimeListener);
		}

	}

	public void resolve() {
		resolved = true;
		for (GameObject crimeListener : crimeListeners) {
			crimeListener.crimeUpdate(this);
		}
		crimeListeners.clear();
	}

	public boolean isResolved() {
		return resolved;
	}

	// public void setResolved(boolean resolved) {
	// this.resolved = resolved;
	// }

	public void notifyWitnessesOfCrime() {

		if (isResolved())
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
