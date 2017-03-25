package com.marklynch.objects.actions;

import com.marklynch.level.constructs.Sound;

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
}
