package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.objects.actors.Actor;

public interface ActionableInWorld {
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer);

	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer);

	public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer);

}
