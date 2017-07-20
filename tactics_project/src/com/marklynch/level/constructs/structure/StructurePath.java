package com.marklynch.level.constructs.structure;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class StructurePath {

	String name;
	Square[] squares;

	public StructurePath(String name, boolean restricted, ArrayList<Actor> ownersArrayList, Square... squares) {
		super();
		this.name = name;
		this.squares = squares;

		if (restricted) {
			for (Square square : squares) {
				square.restricted = true;
			}
		}

		if (ownersArrayList.size() > 0) {
			for (Square square : squares) {
				square.owners = ownersArrayList;
			}
		}
	}

}
