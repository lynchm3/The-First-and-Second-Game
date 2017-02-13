package com.marklynch.objects;

import com.marklynch.objects.units.Actor;

public class Quest {

	String questName;
	String questCurrentObjective;
	String questText;

	// Called once per cycle
	public void update() {

	}

	// Called my members of quest when they dont know what to do
	public void update(Actor actor) {

	}

}
