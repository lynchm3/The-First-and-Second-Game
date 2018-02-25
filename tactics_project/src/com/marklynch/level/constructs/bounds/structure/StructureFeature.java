package com.marklynch.level.constructs.bounds.structure;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.level.squares.Node;
import com.marklynch.objects.GameObject;

public class StructureFeature {

	GameObject gameObject;
	ArrayList<Node> nodes;

	public StructureFeature(GameObject gameObject, Node... nodes) {
		super();
		this.gameObject = gameObject;
		this.nodes = new ArrayList<>(Arrays.asList(nodes));
	}

	public StructureFeature(GameObject gameObject, ArrayList<Node> nodes) {
		super();
		this.gameObject = gameObject;
		this.nodes = nodes;
	}

}
