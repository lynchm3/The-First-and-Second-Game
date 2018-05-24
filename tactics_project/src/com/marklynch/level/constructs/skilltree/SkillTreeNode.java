package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;

public class SkillTreeNode {

	public boolean activated;
	public ArrayList<RequirementToMeet> requirementsToMeet;
	public ArrayList<SkillTreeNode> linkedSkillTreeNodes;
	public Power powerUnlocked;
	public ArrayList<Stat> statsUnlocked;
	public int x, y;

}
