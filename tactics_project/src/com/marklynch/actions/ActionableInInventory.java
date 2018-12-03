package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.objects.actors.Actor;

public interface ActionableInInventory {
	public Action getDefaultActionPerformedOnThisInInventory(Actor performer);

	public ArrayList<Action> getAllActionsPerformedOnThisInInventory(Actor performer);

}
