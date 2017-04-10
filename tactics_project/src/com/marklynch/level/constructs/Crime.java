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
	// Trespassing = 1, Vandalism = 2,
	// Theft = 2, Attack = 3
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
