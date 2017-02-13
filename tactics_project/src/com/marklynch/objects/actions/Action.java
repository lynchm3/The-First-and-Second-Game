package com.marklynch.objects.actions;

import com.marklynch.objects.units.Actor;

public class Action {

	String actionName = "Action???";
	Actor performer;
	Object target;

	public Action(String actionName, Actor performer, Object target) {
		super();
		this.actionName = actionName;
		this.performer = performer;
		this.target = target;
	}

	public void perform() {

	}

}
