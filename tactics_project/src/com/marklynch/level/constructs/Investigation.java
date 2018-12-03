package com.marklynch.level.constructs;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class Investigation implements Comparable<Investigation> {

	public static final int INVESTIGATION_PRIORITY_KEEP_TRACK = 1;
	public static final int INVESTIGATION_PRIORITY_SOUND_HEARD = 2;
	public static final int INVESTIGATION_PRIORITY_CRIME_HEARD = 3;
	public static final int INVESTIGATION_PRIORITY_ATTACK_HEARD = 4;
	public static final int INVESTIGATION_PRIORITY_CRIME_SEEN = 5;
	public static final int INVESTIGATION_PRIORITY_ATTACK_SEEN = 6;
	public static final int INVESTIGATION_PRIORITY_ATTACKED = 7;

	public GameObject actor;
	public Square square;
	public int priority;

	public Investigation(GameObject actor, Square square, int priority) {
		super();
		this.actor = actor;
		this.square = square;
		this.priority = priority;
	}

	@Override
	public int compareTo(Investigation otherInvestigation) {
		return otherInvestigation.priority - this.priority;
	}

}
