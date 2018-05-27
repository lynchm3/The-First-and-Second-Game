package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.power.PowerGrabber;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.constructs.power.PowerRespite;
import com.marklynch.level.constructs.power.PowerSpark;
import com.marklynch.level.constructs.power.PowerSuperPeek;
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

	static ArrayList<SkillTreeNode> skillTreeNodes = new ArrayList<SkillTreeNode>();

	public static MODE mode = MODE.SKILLS;

	public boolean showing = false;

	// Close button
	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	static LevelButton buttonClose;

	SkillTreeNode respite;
	SkillTreeNode grabber;

	public SkillTree() {

		// Respite
		SkillTreeNode respite = new SkillTreeNode(128, 128);
		respite.activated = true;
		respite.name = "Respite";
		respite.description = "Respite";
		// respite.requirementsToMeet.add();
		// respite.linkedSkillTreeNodes.add();
		respite.powerUnlocked.add(new PowerRespite(null));
		// respite.statsUnlocked.add();
		skillTreeNodes.add(respite);

		// Grabber
		SkillTreeNode grabber = new SkillTreeNode(128, 256);
		grabber.activated = true;
		grabber.name = "Grabber";
		grabber.description = "Grabber";
		// grabber.requirementsToMeet.add();
		// grabber.linkedSkillTreeNodes.add();
		grabber.powerUnlocked.add(new PowerGrabber(null));
		// grabber.statsUnlocked.add();
		skillTreeNodes.add(grabber);
		respite.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(respite);

		// Superpeek
		SkillTreeNode superPeek = new SkillTreeNode(256, 256);
		superPeek.activated = true;
		superPeek.name = "Superpeek";
		superPeek.description = "Superpeek";
		// grabber.requirementsToMeet.add();
		// grabber.linkedSkillTreeNodes.add();
		superPeek.powerUnlocked.add(new PowerSuperPeek(null));
		// grabber.statsUnlocked.add();
		skillTreeNodes.add(superPeek);
		superPeek.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(superPeek);

		// Spark
		SkillTreeNode spark = new SkillTreeNode(128, 256 + 128);
		spark.activated = true;
		spark.name = "Spark";
		spark.description = "Spark";
		// grabber.requirementsToMeet.add();
		// grabber.linkedSkillTreeNodes.add();
		spark.powerUnlocked.add(new PowerSpark(null));
		// grabber.statsUnlocked.add();
		skillTreeNodes.add(spark);
		spark.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(spark);

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.init();
			buttons.add(skillTreeNode);
		}

		// Fire Damage +1
		SkillTreeNode fire1 = new SkillTreeNode(256, 256 + 198);
		fire1.activated = true;
		fire1.name = "Fire +1";
		fire1.description = "Fire +1";
		// grabber.requirementsToMeet.add();
		// grabber.linkedSkillTreeNodes.add();
		fire1.powerUnlocked.add(new PowerInferno(null));
		// grabber.statsUnlocked.add();
		skillTreeNodes.add(fire1);
		spark.linkedSkillTreeNodes.add(fire1);
		fire1.linkedSkillTreeNodes.add(spark);

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.init();
			buttons.add(skillTreeNode);
		}

		// Inferno
		SkillTreeNode inferno = new SkillTreeNode(128, 256 + 256);
		inferno.activated = true;
		inferno.name = "Inferno";
		inferno.description = "Inferno";
		// grabber.requirementsToMeet.add();
		// grabber.linkedSkillTreeNodes.add();
		inferno.powerUnlocked.add(new PowerInferno(null));
		// grabber.statsUnlocked.add();
		skillTreeNodes.add(inferno);
		fire1.linkedSkillTreeNodes.add(inferno);
		inferno.linkedSkillTreeNodes.add(fire1);

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.init();
			buttons.add(skillTreeNode);
		}
	}

	public static void loadStaticImages() {
		SkillTreeNode.loadStaticImages();
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
		QuadUtils.drawQuad(Color.TRANSPARENT, 0, 0, Game.windowWidth, Game.windowHeight);

		// Tab Buttons
		for (Button button : buttons) {
			button.draw();
		}

		drawTree(0, 0, false);

	}

	public static void drawTree(int x, int y, boolean smallVersion) {

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.drawLines();
		}
		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.drawCircles();
		}

	}

	@Override
	public void scroll(float dragX, float dragY) {
		System.out.println("SKILL TREE . SCROLL");
		drag(dragX, dragY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		// this.offsetX -= dragX;
		// this.offsetY -= dragY;

		System.out.println("SKILL TREE . DRAG");

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {

			skillTreeNode.updatePosition(skillTreeNode.x + dragX, skillTreeNode.y - dragY);
		}

		// fixScroll();
		// resize2();
	}

}
