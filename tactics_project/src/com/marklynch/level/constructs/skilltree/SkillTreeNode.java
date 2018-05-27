package com.marklynch.level.constructs.skilltree;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class SkillTreeNode extends LevelButton {

	public static Texture textureCircle;

	public boolean activated = false;
	public String name;
	public String description;
	public ArrayList<RequirementToMeet> requirementsToMeet = new ArrayList<RequirementToMeet>();
	public ArrayList<SkillTreeNode> linkedSkillTreeNodes = new ArrayList<SkillTreeNode>();
	public ArrayList<Power> powerUnlocked = new ArrayList<Power>();
	public ArrayList<Stat> statsUnlocked = new ArrayList<Stat>();
	public float x, y, circleX1, circleY1, circleX2, circleY2, textX, textY;
	public static float circleRadius = 48;
	public static float circleCircumference = circleRadius * 2;

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
		setLocation();
	}

	private void setLocation() {
		circleX1 = x - circleRadius;
		circleY1 = y - circleRadius;
		circleX2 = x + circleRadius;
		circleY2 = y + circleRadius;
		textX = x - Game.smallFont.getWidth(name) / 2;
		textY = y - 10;

	}

	public void drawLines() {
		for (SkillTreeNode linkedSkillTreeNode : linkedSkillTreeNodes) {
			LineUtils.drawLine(Color.WHITE, this.x, this.y, linkedSkillTreeNode.x, linkedSkillTreeNode.y, 4);
		}

	}

	public void drawCircles() {
		TextureUtils.drawTexture(textureCircle, circleX1, circleY1, circleX2, circleY2);
		TextUtils.printTextWithImages(textX, textY, Integer.MAX_VALUE, false, null, Color.WHITE, name);
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
		setLocation();

	}

}
