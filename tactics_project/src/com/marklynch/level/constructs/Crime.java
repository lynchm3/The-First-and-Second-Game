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

	final public static int CRIME_SEVERITY_TRESPASSING = 1;
	final public static int CRIME_SEVERITY_REFUSE_TO_DROP = 1;
	final public static int CRIME_SEVERITY_DOUSE = 1;
	final public static int CRIME_SEVERITY_SPY = 2;
	final public static int CRIME_SEVERITY_VANDALISM = 4;
	final public static int CRIME_SEVERITY_THEFT = 4;
	final public static int CRIME_SEVERITY_ARSON = 5;
	final public static int CRIME_SEVERITY_MANSLAUGHTER = 5;
	final public static int CRIME_SEVERITY_ATTACK = 10;

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
