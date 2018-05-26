package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;

public class SkillTree implements Draggable, Scrollable {

	public static enum MODE {
		STATS, SKILLS
	};

	ArrayList<SkillTreeNode> skillTreeNodes = new ArrayList<SkillTreeNode>();

	public static MODE mode = MODE.SKILLS;

	public boolean showing = false;

	// Close button
	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	static LevelButton buttonClose;

	public SkillTree() {
		SkillTreeNode startNode = new SkillTreeNode(true, null, 0, 0);
		// startNode.powerUnlocked = null;
		// startNode.requirementsToMeet.add();
		// startNode.linkedSkillTreeNodes.add();
		// startNode.statsUnlocked
		skillTreeNodes.add(startNode);

		SkillTreeNode hpNode1 = new SkillTreeNode(false, null, 32, 0);
		// hpNode1.powerUnlocked = null;
		// hpNode1.requirementsToMeet.add();
		// hpNode1.statsUnlocked.add(new Stat(Stat.HIGH_LEVEL_STATS.STRENGTH));
		hpNode1.linkedSkillTreeNodes.add(startNode);
		skillTreeNodes.add(hpNode1);
	}

	public static void loadStaticImages() {
	}

	public void resize() {
	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...

		resize();
		generateLinks();
		showing = true;

	}

	public static void generateLinks() {

	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, 0, Game.windowWidth, Game.windowHeight);

		// Tab Buttons
		for (Button button : buttons) {
			button.draw();
		}

		drawTree(0, 0, false);

	}

	public static void drawTree(int x, int y, boolean smallVersion) {

	}

	@Override
	public void scroll(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drag(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

}
