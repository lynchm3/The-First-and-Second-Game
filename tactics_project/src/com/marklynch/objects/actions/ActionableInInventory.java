package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.objects.units.Actor;

public interface ActionableInInventory {
	public Action getDefaultActionPerformedOnThisInInventory(Actor performer);

	public ArrayList<Action> getAllActionsPerformedOnThisInInventory(Actor performer);

}
