package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.objects.units.Actor;

public interface ActionableInInventory {
	public Action getDefaultActionInInventory(Actor performer);

	public ArrayList<Action> getAllActionsInInventory(Actor performer);

}
