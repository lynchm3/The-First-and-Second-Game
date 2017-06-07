package com.marklynch.level.constructs;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class Investigation implements Comparable {

	public static final int INVESTIGATION_PRIORITY_KEEP_TRACK = 1;
	public static final int INVESTIGATION_PRIORITY_CRIME_HEARD = 2;
	public static final int INVESTIGATION_PRIORITY_ATTACK_HEARD = 3;
	public static final int INVESTIGATION_PRIORITY_CRIME_SEEN = 4;
	public static final int INVESTIGATION_PRIORITY_ATTACK_SEEN = 5;
	public static final int INVESTIGATION_PRIORITY_ATTACKED = 6;

	public Actor actor;
	public Square square;
	public int priority;

	public Investigation(Actor actor, Square square, int priority) {
		super();
		this.actor = actor;
		this.square = square;
		this.priority = priority;
	}

	@Override
	public int compareTo(Object object) {

		if (object instanceof Investigation) {
			Investigation otherInvestigation = (Investigation) object;
			return otherInvestigation.priority - this.priority;
		}

		return 0;
	}

}
