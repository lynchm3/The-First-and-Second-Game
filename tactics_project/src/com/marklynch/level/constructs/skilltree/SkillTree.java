package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.power.PowerBleed;
import com.marklynch.level.constructs.power.PowerDash;
import com.marklynch.level.constructs.power.PowerDouse;
import com.marklynch.level.constructs.power.PowerGrabber;
import com.marklynch.level.constructs.power.PowerHealRanged;
import com.marklynch.level.constructs.power.PowerHealSelf;
import com.marklynch.level.constructs.power.PowerHealTouch;
import com.marklynch.level.constructs.power.PowerIgnite;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.constructs.power.PowerPoisonThrowingKnives;
import com.marklynch.level.constructs.power.PowerRespite;
import com.marklynch.level.constructs.power.PowerSpiritBag;
import com.marklynch.level.constructs.power.PowerSuperPeek;
import com.marklynch.level.constructs.power.PowerTelekineticPush;
import com.marklynch.level.constructs.power.PowerTeleportOther;
import com.marklynch.level.constructs.power.PowerTimePlusSixHours;
import com.marklynch.level.constructs.power.PowerUnlock;
import com.marklynch.level.constructs.skilltree.SkillTreeNode.SkillTreeNodePower;
import com.marklynch.level.constructs.skilltree.SkillTreeNode.SkillTreeNodeStat;
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
		SkillTreeNode respite = new SkillTreeNode(2816, 1792);
		activateAtStart.add(respite);
		respite.name = "Respite";
		respite.description = "\"The human body requires 8 hours of sleep per night, or at least 5 secs between fights.\" - Unknown";
		respite.powersUnlocked.add(new PowerRespite(null));
		skillTreeNodes.add(respite);

		// Heal Self
		SkillTreeNode healSelf = new SkillTreeNode(2560, 1792);
		healSelf.name = "Heal Self";
		healSelf.description = "\"There's nothing wrong with some self-love\" - Unknown";
		healSelf.powersUnlocked.add(new PowerHealSelf(null));
		skillTreeNodes.add(healSelf);
		respite.linkedSkillTreeNodes.add(healSelf);
		healSelf.linkedSkillTreeNodes.add(respite);

		// Heal Touch
		SkillTreeNode healTouch = new SkillTreeNode(2560, 1536);
		healTouch.name = "Heal Touch";
		healTouch.description = "\"Love thy neighbor\" - God";
		healTouch.powersUnlocked.add(new PowerHealTouch(null));
		skillTreeNodes.add(healTouch);
		healTouch.linkedSkillTreeNodes.add(healSelf);
		healSelf.linkedSkillTreeNodes.add(healTouch);

		// Heal Other
		SkillTreeNode healOther = new SkillTreeNode(2816, 1536);
		healOther.name = "Heal Ranged";
		healOther.description = "\"Love evveryone\" - God, probably";
		healOther.powersUnlocked.add(new PowerHealRanged(null));
		healOther.statsUnlocked.add(new Stat(HIGH_LEVEL_STATS.DEXTERITY, 10));
		skillTreeNodes.add(healOther);
		healTouch.linkedSkillTreeNodes.add(healOther);
		healOther.linkedSkillTreeNodes.add(healTouch);

		// Grabber
		SkillTreeNode grabber = new SkillTreeNode(256, 512);
		activateAtStart.add(grabber);
		grabber.name = "Grabber";
		grabber.description = "Grabber";
		grabber.powersUnlocked.add(new PowerGrabber(null));
		skillTreeNodes.add(grabber);
		// respite.linkedSkillTreeNodes.add(grabber);
		// grabber.linkedSkillTreeNodes.add(respite);

		// Dash
		SkillTreeNode dash = new SkillTreeNode(512, 512);
		activateAtStart.add(dash);
		dash.name = "Dash";
		dash.description = "Dash";
		dash.powersUnlocked.add(new PowerDash(null));
		skillTreeNodes.add(dash);
		dash.linkedSkillTreeNodes.add(grabber);
		grabber.linkedSkillTreeNodes.add(dash);

		// Spirit bag
		SkillTreeNode spiritBag = new SkillTreeNode(512, 1792);
		activateAtStart.add(spiritBag);
		spiritBag.name = "spiritBag";
		spiritBag.description = "spiritBag";
		spiritBag.powersUnlocked.add(new PowerSpiritBag(null));
		skillTreeNodes.add(spiritBag);

		// Superpeek
		SkillTreeNode superPeek = new SkillTreeNode(5120, 1024);
		activateAtStart.add(superPeek);
		superPeek.name = "Superpeek";
		superPeek.description = "Superpeek";
		superPeek.powersUnlocked.add(new PowerSuperPeek(null));
		skillTreeNodes.add(superPeek);

		// Ignite
		SkillTreeNode ignite = new SkillTreeNode(4096, 1536);
		// activateAtStart.add(ignite);
		ignite.name = "Ignite";
		ignite.description = "Ignite";
		ignite.powersUnlocked.add(new PowerIgnite(null));
		skillTreeNodes.add(ignite);

		// Douse
		SkillTreeNode douse = new SkillTreeNode(3840, 1536);
		douse.name = "douse";
		douse.description = "douse";
		douse.powersUnlocked.add(new PowerDouse(null));
		skillTreeNodes.add(douse);
		ignite.linkedSkillTreeNodes.add(douse);
		douse.linkedSkillTreeNodes.add(ignite);

		// Fire Damage +1
		// SkillTreeNode fire1 = new SkillTreeNode(512, 512 + 512);
		// fire1.name = "Fire +1";
		// fire1.description = "Fire +1";
		// grabber.statsUnlocked.add(Stat.HIGH_LEVEL_STATS.FIRE_DAMAGE);
		// skillTreeNodes.add(fire1);
		// spark.linkedSkillTreeNodes.add(fire1);
		// fire1.linkedSkillTreeNodes.add(spark);

		// Inferno
		SkillTreeNode inferno = new SkillTreeNode(4096, 1792);
		activateAtStart.add(inferno);
		inferno.name = "Inferno";
		inferno.description = "Inferno";
		inferno.powersUnlocked.add(new PowerInferno(null));
		skillTreeNodes.add(inferno);
		ignite.linkedSkillTreeNodes.add(inferno);
		inferno.linkedSkillTreeNodes.add(ignite);

		// Poison Knife
		SkillTreeNode poisonKnives = new SkillTreeNode(1280, 1024);
		activateAtStart.add(poisonKnives);
		poisonKnives.name = "Poison Knives";
		poisonKnives.description = "";
		poisonKnives.powersUnlocked.add(new PowerPoisonThrowingKnives(null));
		skillTreeNodes.add(poisonKnives);

		// Bleed
		SkillTreeNode bleed = new SkillTreeNode(1280, 1280);
		bleed.name = "Bleed";
		bleed.description = "";
		bleed.powersUnlocked.add(new PowerBleed(null));
		skillTreeNodes.add(bleed);
		bleed.linkedSkillTreeNodes.add(poisonKnives);
		poisonKnives.linkedSkillTreeNodes.add(bleed);

		// Time +6 hrs
		SkillTreeNode timePlus6 = new SkillTreeNode(3328, 2816);
		activateAtStart.add(timePlus6);
		timePlus6.name = "timePlus6";
		timePlus6.description = "";
		timePlus6.powersUnlocked.add(new PowerTimePlusSixHours(null));
		skillTreeNodes.add(timePlus6);

		// Unlock
		SkillTreeNode unlock = new SkillTreeNode(3328, 3072);
		unlock.name = "unlock";
		unlock.description = "";
		unlock.powersUnlocked.add(new PowerUnlock(null));
		skillTreeNodes.add(unlock);
		timePlus6.linkedSkillTreeNodes.add(unlock);
		unlock.linkedSkillTreeNodes.add(timePlus6);

		// Telekinteic push
		SkillTreeNode telekineticPush = new SkillTreeNode(3072, 3072);
		activateAtStart.add(telekineticPush);
		telekineticPush.name = "Telekinetic Push";
		telekineticPush.description = "\"The bigger they are, the harder they fall\" - Stranger";
		telekineticPush.powersUnlocked.add(new PowerTelekineticPush(null));
		skillTreeNodes.add(telekineticPush);
		telekineticPush.linkedSkillTreeNodes.add(unlock);
		unlock.linkedSkillTreeNodes.add(telekineticPush);

		// Teleportation +1
		SkillTreeNode teleportation1 = new SkillTreeNode(2816, 3072);
		activateAtStart.add(teleportation1);
		teleportation1.name = "Teleport";
		teleportation1.description = "\"Teleportation! WOW!\" - Stranger";
		teleportation1.powersUnlocked.add(new PowerTeleportOther(null));
		skillTreeNodes.add(teleportation1);
		teleportation1.linkedSkillTreeNodes.add(telekineticPush);
		telekineticPush.linkedSkillTreeNodes.add(teleportation1);

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

		Matrix4f view = Game.activeBatch.getViewMatrix();
		// Black cover
		Game.activeBatch.flush();
		view.setIdentity();
		Game.activeBatch.updateUniforms();
		QuadUtils.drawQuad(background, 0, 0, Game.windowWidth, Game.windowHeight);
		Game.activeBatch.flush();

		view.setIdentity();
		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(zoom, zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
		view.translate(new Vector2f(getDragXWithOffset(), getDragYWithOffset()));
		Game.activeBatch.updateUniforms();

		for (Button button : buttons) {
			button.draw();
		}

		drawTree(0, 0, false);

		Game.activeBatch.flush();
		view.setIdentity();
		Game.activeBatch.updateUniforms();
		Game.level.quickBar.drawStaticUI();

		if (UserInputLevel.draggableMouseIsOver instanceof SkillTreeNodePower) {
			((SkillTreeNodePower) UserInputLevel.draggableMouseIsOver).drawDragged();
		}

	}

	public static void drawTree(int x, int y, boolean smallVersion) {

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.drawLines();
		}
		for (SkillTreeNode skillTreeNode : skillTreeNodes) {
			skillTreeNode.drawCircle();
		}

	}

	float zoom = 1;

	@Override
	public void scroll(float dragX, float dragY) {

		if (dragY > 0 && zoom > 0.1f) {
			zoom -= 0.1f;
		} else if (dragY < 0) {
			if (zoom + 0.1f >= 1f) {
				zoom = 1f;
			} else {
				zoom += 0.1f;
			}

		}
	}

	@Override
	public void drag(float dragX, float dragY) {
		// this.offsetX -= dragX;
		// this.offsetY -= dragY;

		for (SkillTreeNode skillTreeNode : skillTreeNodes) {

			skillTreeNode.updatePosition(skillTreeNode.x + dragX / zoom, skillTreeNode.y - dragY / zoom);
		}

		// fixScroll();
		// resize2();
	}

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		// TODO Auto-generated method stub
		float mouseXTransformed = (((Game.windowWidth / 2) - getDragXWithOffset() - (Game.windowWidth / 2) / zoom)
				+ (mouseX) / zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - getDragYWithOffset() - (Game.windowHeight / 2) / zoom)
				+ (((Game.windowHeight - mouseY)) / zoom));

		for (SkillTreeNode node : SkillTree.skillTreeNodes) {
			for (SkillTreeNodePower powerButton : node.powerButtons) {

				if (powerButton.calculateIfPointInBoundsOfButton(mouseXTransformed, mouseYTransformed))
					return powerButton;
			}
			for (SkillTreeNodeStat statButton : node.statButtons) {

				if (statButton.calculateIfPointInBoundsOfButton(mouseXTransformed, mouseYTransformed))
					return statButton;
			}

			if (node.calculateIfPointInBoundsOfButton(mouseXTransformed, mouseYTransformed))
				return node;
		}
		return null;
	}

	private float getDragXWithOffset() {
		return 0;
	}

	private float getDragYWithOffset() {
		return 0;
	}

	public Draggable getDraggable() {
		float mouseXTransformed = (((Game.windowWidth / 2) - getDragXWithOffset() - (Game.windowWidth / 2) / zoom)
				+ (Mouse.getX()) / zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - getDragYWithOffset() - (Game.windowHeight / 2) / zoom)
				+ (((Game.windowHeight - Mouse.getY())) / zoom));

		for (SkillTreeNode skillTreeNode : SkillTree.skillTreeNodes) {
			for (SkillTreeNodePower skillTreeNodePower : skillTreeNode.powerButtons) {
				if (skillTreeNodePower.calculateIfPointInBoundsOfButton(mouseXTransformed, mouseYTransformed)) {
					return skillTreeNodePower;
				}
			}
		}
		return Level.skillTree;
	}

}
