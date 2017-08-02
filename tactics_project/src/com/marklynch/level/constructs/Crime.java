package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class Crime {

	public Action action;
	public ArrayList<Actor> witnesses;
	public Actor performer;
	public Actor visctim;
	public int severity;
	public boolean hasBeenToldToStop;
	// Trespassing = 1,
	// Having to be asked to drop something = 1
	// Vandalism = 4,
	// Theft = 4
	// Attack = 6
	public boolean resolved;
	public GameObject stolenItems[];

	public Crime(Action action, Actor performer, Actor visctim, int severity, GameObject... stolenItems) {
		super();
		this.action = action;
		this.witnesses = new ArrayList<Actor>();
		this.performer = performer;
		this.visctim = visctim;
		this.severity = severity;
		this.resolved = false;
		this.stolenItems = stolenItems;
	}

	public Crime(Action action, Actor performer, Actor visctim, int severity, ArrayList<GameObject> stolenItems) {
		this(action, performer, visctim, severity, stolenItems.toArray(new GameObject[stolenItems.size()]));

		;
	}

}
