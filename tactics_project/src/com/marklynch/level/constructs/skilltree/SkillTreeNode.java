package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.characterscreen.CharacterScreen;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.quickbar.QuickBarSquare;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class SkillTreeNode extends LevelButton {

	public static Texture textureCircle;

	private boolean activated = false;
	public String name;
	public String description;
	public ArrayList<RequirementToMeet> requirementsToMeet = new ArrayList<RequirementToMeet>();
	public ArrayList<SkillTreeNode> linkedSkillTreeNodes = new ArrayList<SkillTreeNode>();
	public ArrayList<SkillTreeNodePower> powerButtons = new ArrayList<SkillTreeNodePower>();
	public ArrayList<SkillTreeNodeStat> statButtons = new ArrayList<SkillTreeNodeStat>();
	public ArrayList<Stat> statsUnlocked = new ArrayList<Stat>();
	public float x, y, circleX1, circleY1, circleX2, circleY2, textX, textY;

	float powerOffsetX = 16;
	float powerOffsetY = 16;
	float statOffsetX = 16;
	float statOffsetY = -16 - SkillTreeNodeStat.statWidth;

	public ArrayList<Power> powersUnlocked = new ArrayList<Power>();;
	public static float circleRadius = 48;
	public static float circleCircumference = circleRadius * 2;
	// public float dragX = 0, dragY = 0;

	public SkillTreeNode(int x, int y) {
		super(x - circleRadius, y - circleRadius, circleCircumference, circleCircumference, null, null, "", true, true,
				Color.TRANSPARENT, Color.WHITE, "BUTTON");
		this.x = x;
		this.y = y;

	}

	// public SkillTreeNode(float x, float y, float width, float height, String
	// enabledTexturePath,
	// String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop,
	// Color buttonColor,
	// Color textColor, String tooltipText) {
	// super(x, y, width, height, enabledTexturePath, disabledTexturePath, text,
	// xFromLeft, yFromTop, buttonColor, textColor,
	// tooltipText);
	// // TODO Auto-generated constructor stub
	// }

	public void init() {
		ArrayList<Object> tooltipItems = new ArrayList<Object>();

		tooltipItems.add(this.name);
		tooltipItems.add(TextUtils.NewLine.NEW_LINE);
		tooltipItems.add(description);

		for (Power power : powersUnlocked) {
			this.powerButtons.add(new SkillTreeNodePower(power, 0, 0));
			tooltipItems.add(TextUtils.NewLine.NEW_LINE);
			tooltipItems.add("    ---------------------------------   ");
			tooltipItems.add(TextUtils.NewLine.NEW_LINE);
			tooltipItems.add(power);
		}

		for (Stat stat : statsUnlocked) {
			this.statButtons.add(new SkillTreeNodeStat(stat, 0, 0));
			tooltipItems.add(TextUtils.NewLine.NEW_LINE);
			// tooltipItems.add("_________________________________");
			tooltipItems.add("    ---------------------------------   ");
			tooltipItems.add("STAT ");
			tooltipItems.add(TextUtils.NewLine.NEW_LINE);
			tooltipItems.add(stat);
		}

		setLocation();

		this.setClickListener(new ClickListener() {

			@Override
			public void click() {

				if (activated || !isAvailable()) {
					return;
				} else {
					activate(Game.level.player);
				}

			}
		});

		this.setTooltipText(tooltipItems);
	}

	public void activate(Actor actor) {
		activated = true;
		for (SkillTreeNodePower skillTreeNodePower : powerButtons) {
			actor.powers.add(skillTreeNodePower.power);
			for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {

				if (skillTreeNodePower.power.passive == false && quickBarSquare.getShortcut() == null) {
					quickBarSquare.setShortcut(skillTreeNodePower.power);
					break;
				}
			}
		}

		for (Stat statUnlocked : statsUnlocked) {
			Level.player.highLevelStats.get(statUnlocked.type).value += statUnlocked.value;
		}

	}

	public void drawLines() {
		for (SkillTreeNode linkedSkillTreeNode : linkedSkillTreeNodes) {
			if (this.activated && linkedSkillTreeNode.activated)
				LineUtils.drawLine(Color.BLUE, this.x, this.y, linkedSkillTreeNode.x, linkedSkillTreeNode.y, 4);
			else if (this.activated || linkedSkillTreeNode.activated)
				LineUtils.drawLine(Color.LIGHT_GRAY, this.x, this.y, linkedSkillTreeNode.x, linkedSkillTreeNode.y, 4);
			else
				LineUtils.drawLine(Color.DARK_GRAY, this.x, this.y, linkedSkillTreeNode.x, linkedSkillTreeNode.y, 4);
		}

	}

	public void drawCircle() {
		if (activated) {
			TextureUtils.drawTexture(textureCircle, circleX1, circleY1, circleX2, circleY2, Color.BLUE);
		} else {
			if (isAvailable()) {
				TextureUtils.drawTexture(textureCircle, circleX1, circleY1, circleX2, circleY2, Color.LIGHT_GRAY);
			} else {
				TextureUtils.drawTexture(textureCircle, circleX1, circleY1, circleX2, circleY2, Color.DARK_GRAY);
			}
		}
		TextUtils.printTextWithImages(textX, textY, Integer.MAX_VALUE, false, null, Color.WHITE, name);

		for (SkillTreeNodePower skillTreeNodePower : powerButtons) {
			skillTreeNodePower.drawBackground();
			skillTreeNodePower.drawPower();
		}

		for (SkillTreeNodeStat skillTreeNodeStat : statButtons) {
			skillTreeNodeStat.drawBackground();
			skillTreeNodeStat.drawStat();
		}

		// if (powersUnlocked.size() != 0) {
		// // ;lkzxdfkjl'
		// }

	}

	public static void loadStaticImages() {
		textureCircle = ResourceUtils.getGlobalImage("skill_tree_circle.png", false);

	}

	@Override
	public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {
		if (super.calculateIfPointInBoundsOfButton(mouseX, mouseY)) {
			return Math.hypot(this.x - mouseX, this.y - mouseY) <= circleRadius;
		}
		return false;
	}

	@Override
	public void updatePosition(float x, float y) {

		super.updatePosition(x - circleRadius, y - circleRadius);

		this.x = x;
		this.y = y;

		for (SkillTreeNodePower skillTreeNodePower : powerButtons) {
			skillTreeNodePower.updatePosition(x + powerOffsetX, y + powerOffsetY);
		}

		for (SkillTreeNodeStat skillTreeNodeStat : statButtons) {
			skillTreeNodeStat.updatePosition(x + statOffsetX, y + statOffsetY);
		}

		setLocation();

	}

	private void setLocation() {

		circleX1 = x - circleRadius;
		circleY1 = y - circleRadius;
		circleX2 = x + circleRadius;
		circleY2 = y + circleRadius;
		textX = x - Game.smallFont.getWidth(name) / 2;
		textY = y - 10;

		for (SkillTreeNodePower skillTreeNodePower : powerButtons) {
			skillTreeNodePower.setLocation(x + powerOffsetX, y + powerOffsetY);
		}

		for (SkillTreeNodeStat skillTreeNodeStat : statButtons) {
			skillTreeNodeStat.setLocation(x + statOffsetX, y + statOffsetY);
		}

		// powerX =

	}

	public boolean isAvailable() {

		if (activated)
			return false;

		for (SkillTreeNode linkedSkillTreeNode : linkedSkillTreeNodes) {
			if (linkedSkillTreeNode.activated) {
				return true;
			}
		}
		return false;
	}

	public class SkillTreeNodeStat extends LevelButton {

		Stat stat;
		public float dragX = 0, dragY = 0;
		public static final float statWidth = 64;
		public float powerHalfWidth = statWidth / 2;
		public float x1, y1, x2, y2;

		public SkillTreeNodeStat(Stat stat, int x, int y) {
			super(x, y, statWidth, statWidth, null, null, "", true, true, Color.TRANSPARENT, Color.WHITE, "");
			this.stat = stat;
			this.x = x;
			this.y = y;
			this.setTooltipText(stat);

		}

		private void setLocation(float x, float y) {
			this.x = x;
			this.y = y;
			this.x1 = x;
			this.y1 = y;
			this.x2 = x + statWidth;
			this.y2 = y + statWidth;

		}

		public void drawBackground() {

			if (activated) {
				TextureUtils.drawTexture(textureCircle, x1, y1, x2, y2, Color.BLUE);
			} else {
				if (isAvailable()) {
					TextureUtils.drawTexture(textureCircle, x1, y1, x2, y2, Color.LIGHT_GRAY);
				} else {
					TextureUtils.drawTexture(textureCircle, x1, y1, x2, y2, Color.DARK_GRAY);
				}
			}
		}

		public void drawStat() {
			TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(this.stat.type), x1, y1, x2, y2);
		}

		@Override
		public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {
			if (super.calculateIfPointInBoundsOfButton(mouseX, mouseY)) {
				return Math.hypot(this.x - mouseX, this.y - mouseY) <= circleRadius;
			}
			return false;
		}
	}

	public class SkillTreeNodePower extends LevelButton implements Draggable, Scrollable {

		Power power;
		public float dragX = 0, dragY = 0;
		public static final float powerWidth = 64;
		public float powerHalfWidth = powerWidth / 2;
		public float circleRadius = powerHalfWidth;
		public float x1, y1, x2, y2;

		public SkillTreeNodePower(Power power, int x, int y) {
			super(x, y, powerWidth, powerWidth, null, null, "", true, true, Color.TRANSPARENT, Color.WHITE, "");
			setTooltipText(power);
			this.power = power;
			this.x = x;
			this.y = y;

		}

		private void setLocation(float x, float y) {
			this.x = x;
			this.y = y;
			this.x1 = x;
			this.y1 = y;
			this.x2 = x + powerWidth;
			this.y2 = y + powerWidth;

		}

		@Override
		public boolean calculateIfPointInBoundsOfButton(float mouseX, float mouseY) {
			if (super.calculateIfPointInBoundsOfButton(mouseX, mouseY)) {
				if (power.passive) {
					return Math.hypot((this.x + circleRadius) - mouseX,
							(this.y + +circleRadius) - mouseY) <= circleRadius;
				} else {
					return true;
				}
			}
			return false;
		}

		public void drawBackground() {

			if (power.passive) {

				if (activated) {
					TextureUtils.drawTexture(textureCircle, x1, y1, x2, y2, Color.BLUE);
				} else {
					if (isAvailable()) {
						TextureUtils.drawTexture(textureCircle, x1, y1, x2, y2, Color.LIGHT_GRAY);
					} else {
						TextureUtils.drawTexture(textureCircle, x1, y1, x2, y2, Color.DARK_GRAY);
					}
				}

			} else {
				QuadUtils.drawQuad(Color.BLACK, x1, y1, x2, y2);
				if (activated) {
					TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2, Color.BLUE);
				} else {
					if (isAvailable()) {
						TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2, Color.LIGHT_GRAY);
					} else {
						TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2, Color.DARK_GRAY);
					}
				}
			}
		}

		public void drawPower() {
			TextureUtils.drawTexture(this.power.image, x1, y1, x2, y2);
		}

		public void drawDragged() {

			if (power.passive || !SkillTreeNode.this.activated)
				return;
			// TextureUtils.drawTexture(this.power.image, x1 + dragX, y1 + dragY, x2 +
			// dragX, y2 + dragY);
			TextureUtils.drawTexture(this.power.image, Mouse.getX() - powerHalfWidth,
					Game.windowHeight - Mouse.getY() - powerHalfWidth, Mouse.getX() + powerHalfWidth,
					Game.windowHeight - Mouse.getY() + powerHalfWidth);
		}

		@Override
		public void scroll(float dragX, float dragY) {

		}

		@Override
		public void drag(float drawOffsetX, float dragOffsetY) {

			if (power.passive || !SkillTreeNode.this.activated)
				return;

			this.dragX = this.dragX + drawOffsetX;
			this.dragY = this.dragY - dragOffsetY;

			float centerX = this.x + this.dragX;
			float centerY = this.y + this.dragY;

			for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {
				if (quickBarSquare.calculateIfPointInBoundsOfButton(centerX, centerY)) {
				} else {
				}
			}
		}

		@Override
		public void dragDropped() {

			if (power.passive || !SkillTreeNode.this.activated)
				return;

			float centerX = Mouse.getX();
			float centerY = Game.windowHeight - Mouse.getY();

			QuickBarSquare quickBarSquareToSwapWith = null;
			for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {
				if (quickBarSquare.calculateIfPointInBoundsOfButton(centerX, centerY)) {
					quickBarSquareToSwapWith = quickBarSquare;
				}
				quickBarSquare.tempSwap = null;
			}

			if (quickBarSquareToSwapWith == null) {

			} else if (power.passive == false && SkillTreeNode.this.activated) {
				quickBarSquareToSwapWith.setShortcut(this.power);
			}

			this.dragX = 0;
			this.dragY = 0;
		}
	}

}
