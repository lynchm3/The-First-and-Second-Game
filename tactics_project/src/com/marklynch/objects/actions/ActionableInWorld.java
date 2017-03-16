package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.objects.units.Actor;

public interface ActionableInWorld {
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer);

	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer);

}
