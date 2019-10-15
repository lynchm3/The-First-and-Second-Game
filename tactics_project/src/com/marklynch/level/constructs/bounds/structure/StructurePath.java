package com.marklynch.level.constructs.bounds.structure;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;

public class StructurePath {

	String name;
	public Square[] squares;

	public StructurePath(String name, boolean restricted, boolean restrictedAtNight, CopyOnWriteArrayList<Actor> ownersCopyOnWriteArrayList,
			Square... squares) {
		super();
		this.name = name;
		this.squares = squares;

		if (restricted) {
			for (Square square : squares) {
				square.restricted = true;
			}
		}

		if (restrictedAtNight) {
			for (Square square : squares) {
				square.restrictedAtNight = true;
			}
		}

		if (ownersCopyOnWriteArrayList.size() > 0) {
			for (Square square : squares) {
				square.owners = ownersCopyOnWriteArrayList;
			}
		}
	}

}
