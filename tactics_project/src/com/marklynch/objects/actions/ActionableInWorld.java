package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.objects.actors.Actor;

public interface ActionableInWorld {
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer);

	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer);

	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer);

}
