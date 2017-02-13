package com.marklynch.level.constructs;

public class FactionRelationship {

	public int relationship = 0;
	public transient Faction source;
	public transient Faction target;
	public transient String[] editableAttributes = { "relationship" };

	public FactionRelationship(int relationship, Faction source, Faction target) {
		super();
		this.relationship = relationship;
		this.source = source;
		this.target = target;
	}

	public void postLoad(Faction source, Faction target) {
		this.source = source;
		this.target = target;
	}

}
