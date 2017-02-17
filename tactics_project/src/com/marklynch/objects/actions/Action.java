package com.marklynch.objects.actions;

public class Action {

	public String actionName;
	public boolean enabled = true;

	public Action(String actionName) {
		super();
		this.actionName = actionName;
	}

	public void perform() {

	}

	public boolean check() {
		return true;
	}
}
