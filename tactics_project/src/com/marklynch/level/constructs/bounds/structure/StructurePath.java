package com.marklynch.level.constructs.bounds.structure;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class StructurePath {

	String name;
	Square[] squares;
	ArrayList<Node> nodes;

	public StructurePath(String name, boolean restricted, boolean restrictedAtNight, ArrayList<Actor> ownersArrayList,
			Node[] nodes, Square... squares) {
		super();
		this.name = name;
		this.squares = squares;
		this.nodes = new ArrayList<>(Arrays.asList(nodes));

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

		if (ownersArrayList.size() > 0) {
			for (Square square : squares) {
				square.owners = ownersArrayList;
			}
		}

		if (this.nodes != null && this.nodes.size() > 0) {
			for (Square square : squares) {
				square.nodes = this.nodes;
				for (Node node : this.nodes) {
					node.addSquare(square);
				}
			}
		}
	}

}
