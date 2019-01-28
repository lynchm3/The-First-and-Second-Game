package com.marklynch.level.constructs.power;

import com.marklynch.objects.inanimateobjects.GameObject;

public class PowerTelekineticPush10 extends PowerTelekineticPush {

	private static String NAME = "Telekinetic Push";

	public PowerTelekineticPush10() {
		this(null);
	}

	public PowerTelekineticPush10(GameObject source) {
		super(source);
		selectTarget = true;
		this.range = 10;
		this.castLocations = Power.castLocationsLine10;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTelekineticPush10(source);
	}

}
