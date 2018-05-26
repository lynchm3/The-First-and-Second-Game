package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;

public class SkillTreeNode {

	public boolean activated;
	String name = "";
	public ArrayList<RequirementToMeet> requirementsToMeet = new ArrayList<RequirementToMeet>();
	public ArrayList<SkillTreeNode> linkedSkillTreeNodes = new ArrayList<SkillTreeNode>();
	public Power powerUnlocked;
	// public HashMap<Stat> statsUnlocked;
	public int x, y;

	public SkillTreeNode(boolean activated, Power powerUnlocked, int x, int y) {
		super();
		this.activated = activated;
		this.powerUnlocked = powerUnlocked;
		this.x = x;
		this.y = y;
	}

}
