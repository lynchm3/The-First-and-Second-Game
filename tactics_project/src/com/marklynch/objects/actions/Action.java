package com.marklynch.objects.actions;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Action {

	String actionName = "Action";
	Actor performer;
	GameObject target;

	public Action(String actionName, Actor performer, GameObject target) {
		super();
		this.actionName = actionName;
		this.performer = performer;
		this.target = target;
	}

}
