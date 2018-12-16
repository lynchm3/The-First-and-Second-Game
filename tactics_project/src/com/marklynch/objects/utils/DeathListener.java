package com.marklynch.objects.utils;

import com.marklynch.objects.inanimateobjects.GameObject;

public interface DeathListener {

	public void thisThingDied(GameObject deadThing);

	public Long getId();

}
