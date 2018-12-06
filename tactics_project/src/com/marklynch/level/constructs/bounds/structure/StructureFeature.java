package com.marklynch.level.constructs.bounds.structure;

import java.util.Arrays;

import com.marklynch.level.squares.Node;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;

public class StructureFeature {

	GameObject gameObject;
	ArrayList<Node> nodes = new ArrayList<Node>(Node.class);

	public StructureFeature(GameObject gameObject, Node... nodes) {
		super();
		this.gameObject = gameObject;
		this.nodes.clear();
		this.nodes.addAll(Arrays.asList(nodes));
	}

	public StructureFeature(GameObject gameObject, ArrayList<Node> nodes) {
		super();
		this.gameObject = gameObject;
		this.nodes = nodes;
	}

}
