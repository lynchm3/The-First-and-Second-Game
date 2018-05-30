package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
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

	public static ArrayList<SkillTreeNode> skillTreeNodes = new ArrayList<SkillTreeNode>();

	public static MODE mode = MODE.SKILLS;

	public boolean showing = false;

	// Close button
	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	static LevelButton buttonClose;

	public ArrayList<SkillTreeNode> activateAtStart = new ArrayList<SkillTreeNode>();

	public SkillTree() {

		// Respite
		SkillTreeNode respite = new SkillTreeNode(128, 128);
		activateAtStart.add(respite);
		respite.name = "Respite";
		respite.description = "Respite";
		respite.powersUnlocked.add(new PowerRespite(null));
		skillTreeNodes.add(respite);

		// Grabber
		SkillTreeNode grabber = new SkillTreeNode(128, 256);
		activateAtStart.add(grabber);
		grabber.name = "Grabber";
		grabber.description = "Grabber";
		grabber.powersUnlocked.add(new PowerGrabber(null));
		skillTreeNodes.add(grabber);
		respite.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(respite);

		// Superpeek
		SkillTreeNode superPeek = new SkillTreeNode(256, 256);
		superPeek.name = "Superpeek";
		superPeek.description = "Superpeek";
		superPeek.powersUnlocked.add(new PowerSuperPeek(null));
		skillTreeNodes.add(superPeek);
		superPeek.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(superPeek);

		// Spark
		SkillTreeNode spark = new SkillTreeNode(128, 256 + 128);
		activateAtStart.add(spark);
		spark.name = "Spark";
		spark.description = "Spark";
		spark.powersUnlocked.add(new PowerSpark(null));
		skillTreeNodes.add(spark);
		spark.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(spark);

		// Fire Damage +1
		SkillTreeNode fire1 = new SkillTreeNode(256, 256 + 198);
		fire1.name = "Fire +1";
		fire1.description = "Fire +1";
		grabber.statsUnlocked.add(Stat.OFFENSIVE_STATS.FIRE_DAMAGE);
		skillTreeNodes.add(fire1);
		spark.linkedSkillTreeNodes.add(fire1);
		fire1.linkedSkillTreeNodes.add(spark);

		// Inferno
		SkillTreeNode inferno = new SkillTreeNode(128, 256 + 256);
		inferno.name = "Inferno";
		inferno.description = "Inferno";
		inferno.powersUnlocked.add(new PowerInferno(null));
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

	public static Color background = new Color(0f, 0f, 0f, 0.75f);

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(background, 0, 0, Game.windowWidth, Game.windowHeight);

		// Tab Buttons
		for (Button button : buttons) {
			button.draw();
		}

		drawTree(0, 0, false);

		Game.level.quickBar.drawStaticUI();

	}

	public static void drawTree(int x, int y, boolean smallVersion) {

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.drawLines();
		}
		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.drawCircle();
		}

	}

	@Override
	public void scroll(float dragX, float dragY) {
		// System.out.println("SKILL TREE . SCROLL");
		// drag(dragX, dragY);

		// zooming buttons? fuck...
	}

	@Override
	public void drag(float dragX, float dragY) {
		// this.offsetX -= dragX;
		// this.offsetY -= dragY;

		// System.out.println("SKILL TREE . DRAG");

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {

			skillTreeNode.updatePosition(skillTreeNode.x + dragX, skillTreeNode.y - dragY);
		}

		// fixScroll();
		// resize2();
	}

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub

	}

}
