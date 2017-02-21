package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.objects.units.Actor;

public interface ActionableInWorld {
	public Action getDefaultActionInWorld(Actor performer);

	public ArrayList<Action> getAllActionsInWorld(Actor performer);

}
