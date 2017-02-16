package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.objects.units.Actor;

public interface Actionable {
	public Action getDefaultAction(Actor performer);

	public ArrayList<Action> getAllActions(Actor performer);

}
