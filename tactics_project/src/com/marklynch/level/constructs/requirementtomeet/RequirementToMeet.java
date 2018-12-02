package com.marklynch.level.constructs.requirementtomeet;

import com.marklynch.objects.actors.Actor;

public abstract class RequirementToMeet {

	public abstract boolean isRequirementMet(Actor actor);

	public abstract String getText();

}
