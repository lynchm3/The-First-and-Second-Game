package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.objects.actors.Actor;

public interface ActionableInInventory {
	public Action getDefaultActionPerformedOnThisInInventory(Actor performer);

	public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInInventory(Actor performer);

}
