package com.marklynch.level.constructs.beastiary;

public class BestiaryKnowledge {

	int templateId;

	// general
	public boolean name;
	public boolean image;
	public boolean totalHealth;

	// Damage
	public boolean slashDamage;
	public boolean bluntDamage;
	public boolean pierceDamage;
	public boolean fireDamage;
	public boolean waterDamage;
	public boolean electricDamage;
	public boolean poisonDamage;
	public boolean range;

	// Resistances
	public boolean slashResistance;
	public boolean bluntResistance;
	public boolean pierceResistance;
	public boolean fireResistance;
	public boolean waterResistance;
	public boolean electricResistance;
	public boolean poisonResistance;

	public BestiaryKnowledge(int templateId) {
		this.templateId = templateId;
	}

}
