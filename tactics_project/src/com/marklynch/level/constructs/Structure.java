package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.level.constructs.cave.Room;

public abstract class Structure {

	public String name;
	public ArrayList<Room> rooms;
	public boolean seenByPlayer = false;
	// public ArrayList<Square> squares;

	public void draw2() {

	}
}
