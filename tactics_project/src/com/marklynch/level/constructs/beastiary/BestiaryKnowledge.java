package com.marklynch.level.constructs.beastiary;

public class BestiaryKnowledge {

	int templateId;

	// general
	public boolean name;
	public boolean level;
	public boolean image;
	public boolean totalHealth;
	public boolean faction;
	public boolean group;

	// stats
	public boolean strength;
	public boolean dexterity;
	public boolean intelligence;
	public boolean endurance;

	// Resistances
	public boolean slashResistance;
	public boolean bluntResistance;
	public boolean pierceResistance;
	public boolean fireResistance;
	public boolean waterResistance;
	public boolean electricalResistance;
	public boolean poisonResistance;
	public boolean bleedResistance;

	// Damage
	public boolean slashDamage;
	public boolean bluntDamage;
	public boolean pierceDamage;
	public boolean fireDamage;
	public boolean waterDamage;
	public boolean electricalDamage;
	public boolean poisonDamage;
	public boolean bleedDamage;
	public boolean healing;
	public boolean range;

	// Powers
	public boolean powers;

	public BestiaryKnowledge(int templateId) {
		this.templateId = templateId;
	}

}
