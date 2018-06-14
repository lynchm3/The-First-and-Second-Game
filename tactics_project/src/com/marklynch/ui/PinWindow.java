package com.marklynch.ui;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.characterscreen.CharacterScreen;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class PinWindow implements Draggable {

	public Object object;
	public GameObject gameObject;
	public Actor actor;
	public Square square;
	public float width;
	public float height;

	public boolean minimised = false;
	public float drawPositionX, drawPositionY;
	public LevelButton closeButton, minimiseButton, titleBarButton;
	public int titleBarHeight = 20;
	public int borderWidth = 2;
	public static Object[] unknownStats = new Object[] { "??" };

	// Actor

	// Stats for actor
	public float statsImageX;
	public float statsTextX;
	public float statsY;
	public float resistancesImageX;
	public float resistancesTextX;
	public float resistancesY;

	// Powers for actor
	public float powersImageX;
	public float powersTextX;
	public float powersTitleY;
	public float powersListY;

	// Equipped weapon for actor

	// Practical damage

	public PinWindow(Object object) {
		this.object = object;
		if (object instanceof GameObject) {
			this.gameObject = (GameObject) object;
			if (object instanceof Actor)
				this.actor = (Actor) object;
			if (gameObject.imageTexture != null) {
				this.width = gameObject.imageTexture.getWidth() + borderWidth * 2;
				this.height = gameObject.imageTexture.getHeight() + titleBarHeight + borderWidth;
			} else {
				this.width = gameObject.width + borderWidth * 2;
				this.height = gameObject.height + titleBarHeight + borderWidth;
			}
		} else if (object instanceof Square) {
			this.square = (Square) object;
			this.width = Game.SQUARE_WIDTH;
			this.height = Game.SQUARE_HEIGHT;
		}
		drawPositionX = 500;
		drawPositionY = 10;

		// Sizing if it's a square
		if (square != null) {
			width += 200;
			if (height < 210)
				height = 256;

			// stats
			statsImageX = width - 200 + 8;
			statsTextX = width - 200 + 32;
			statsY = titleBarHeight + borderWidth;

		}

		// Sizing if it's an actor
		if (actor != null) {
			width += 200;
			if (height < 512)
				height = 512;

			// stats
			statsImageX = width - 200 + 8;
			statsTextX = width - 200 + 32;
			statsY = titleBarHeight + borderWidth;

			// resistances
			resistancesImageX = width - 100 + 8;
			resistancesTextX = width - 100 + 32;
			resistancesY = titleBarHeight + borderWidth;

			// powers
			powersImageX = 8;
			powersTextX = 32;
			powersTitleY = 300;
			powersListY = 330;

		}

		closeButton = new LevelButton(drawPositionX + width - 20, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "X", true, true, Color.BLACK, Color.WHITE, "Close window");
		closeButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.pinWindows.remove(PinWindow.this);
			}
		});

		minimiseButton = new LevelButton(drawPositionX + width - 40, drawPositionY, 20f, 20f, "end_turn_button.png",
				"end_turn_button.png", "_", true, true, Color.BLACK, Color.WHITE, "Minimise window");
		minimiseButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				PinWindow.this.minimised = !PinWindow.this.minimised;
			}
		});

		titleBarButton = new LevelButton(drawPositionX, drawPositionY, width, 20f, "end_turn_button.png",
				"end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE, null);
		titleBarButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				PinWindow.this.minimised = !PinWindow.this.minimised;
			}
		});

		// if (objects.length == 1)
		// this.width = Game.font.getWidth((CharSequence) objects[0]) + 10;
		// this.height = 40;
	}

	public void drawLine() {

		if (minimised)
			return;

		if (gameObject == null && square == null)
			return;

		// LINE
		float lineX1 = this.drawPositionX + this.width / 2;
		float lineY1 = drawPositionY + height / 2;
		float objectX = 0;
		float objectY = 0;

		if (gameObject != null && gameObject.squareGameObjectIsOn != null
				&& gameObject.squareGameObjectIsOn.visibleToPlayer) {

			objectX = (gameObject.squareGameObjectIsOn.xInGridPixels);
			if (gameObject.getPrimaryAnimation() != null)
				objectX += gameObject.getPrimaryAnimation().offsetX;

			objectY = (gameObject.squareGameObjectIsOn.yInGridPixels);
			if (gameObject.getPrimaryAnimation() != null)
				objectY += gameObject.getPrimaryAnimation().offsetY;

			// Draw line from window to subject
		} else if (square != null) {

			objectX = square.xInGridPixels;
			objectY = square.yInGridPixels;

		} else {
			return;
		}

		float lineX2 = (Game.windowWidth / 2)
				+ (Game.zoom * (objectX - Game.windowWidth / 2 + Game.getDragXWithOffset() + Game.HALF_SQUARE_WIDTH));
		float lineY2 = (Game.windowHeight / 2)
				+ (Game.zoom * (objectY - Game.windowHeight / 2 + Game.getDragYWithOffset() + Game.HALF_SQUARE_HEIGHT));
		LineUtils.drawLine(Color.BLACK, lineX1, lineY1, lineX2, lineY2, 5);
	}

	public void drawStaticUI() {

		if (!minimised) {
			// QuadUtils.drawQuad(Color.BLACK, drawPositionX,
			// Game.halfWindowWidth, drawPositionY, Game.halfWindowHeight);

			// Background
			QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionY, drawPositionX + width, drawPositionY + height);

			// Image
			if (gameObject != null) {
				if (actor != null) {
					actor.drawActor((int) (drawPositionX + borderWidth), (int) (drawPositionY + titleBarHeight), 1f,
							false, 1, 1, 0f, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, TextureUtils.neutralColor, true);
				} else {
					TextureUtils.drawTexture(gameObject.imageTexture, drawPositionX + borderWidth,
							drawPositionY + titleBarHeight, drawPositionX + gameObject.imageTexture.getWidth(),
							drawPositionY + gameObject.imageTexture.getHeight());
				}
				if (actor != null) {
					drawStats(actor);
				}
			} else if (square != null) {
				TextureUtils.drawTexture(square.imageTexture, drawPositionX + borderWidth,
						drawPositionY + titleBarHeight, drawPositionX + borderWidth + Game.SQUARE_WIDTH,
						drawPositionY + titleBarHeight + Game.SQUARE_HEIGHT);
				drawSquareStats();
			}
		}

		// Titlebar
		titleBarButton.draw();

		// Title bar text
		TextUtils.printTextWithImages(drawPositionX + 2, drawPositionY, width - 40, false, null, Color.WHITE, object);

		// Title bar buttons
		this.closeButton.draw();
		this.minimiseButton.draw();

		// Borders (left,right,bottom)
		if (!minimised) {

			QuadUtils.drawQuad(Color.BLACK, drawPositionX, drawPositionY, drawPositionX + 2, drawPositionY + height);
			QuadUtils.drawQuad(Color.BLACK, drawPositionX + width - 2, drawPositionY, drawPositionX + width,
					drawPositionY + height);
			QuadUtils.drawQuad(Color.BLACK, drawPositionX, drawPositionY + height - 2, drawPositionX + width,
					drawPositionY + height);
		}
	}

	private void drawSquareStats() {

		// Name
		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY, drawPositionX + statsImageX + 20, drawPositionY + statsY + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY, width - 40, false, null,
				Color.WHITE, "Location ", square.name);

		// elevation

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 30, drawPositionX + statsImageX + 20, drawPositionY + statsY + 30 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 30, width - 40, false, null,
				Color.WHITE, "Elevation ", square.elevation);

		// travelCost
		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 60, drawPositionX + statsImageX + 20, drawPositionY + statsY + 60 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 60, width - 40, false, null,
				Color.WHITE, "Movement Cost ", square.travelCost);

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 90, drawPositionX + statsImageX + 20, drawPositionY + statsY + 90 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 90, width - 40, false, null,
				Color.WHITE, "Restricted ", square.restricted);

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 120, drawPositionX + statsImageX + 20, drawPositionY + statsY + 120 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 120, width - 40, false, null,
				Color.WHITE, "Area ", square.areaSquareIsIn);

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 150, drawPositionX + statsImageX + 20, drawPositionY + statsY + 150 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 150, width - 40, false, null,
				Color.WHITE, "Structure ", square.structureSquareIsIn);

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 180, drawPositionX + statsImageX + 20, drawPositionY + statsY + 180 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 180, width - 40, false, null,
				Color.WHITE, "SubStructure ", square.structureSectionSquareIsIn);

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 210, drawPositionX + statsImageX + 20, drawPositionY + statsY + 210 + 20);
		TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 210, width - 40, false, null,
				Color.WHITE, "Room ", square.structureRoomSquareIsIn);
	}

	private void drawStats(Actor actor) {
		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(actor.templateId);

		// GENERAL
		if (bestiaryKnowledge.faction) {
			// TextUtils.printTextWithImages(drawPositionX + borderWidth,
			// drawPositionY + titleBarHeight + actor.imageTexture.getHeight(), 128, false,
			// null, "Faction ",
			// actor.faction);
		}

		if (bestiaryKnowledge.group && actor.group != null) {
			// TextUtils.printTextWithImages(drawPositionX + borderWidth,
			// drawPositionY + titleBarHeight + actor.imageTexture.getHeight() + 30, 128,
			// false, null, "Group ",
			// actor.group.name);
		}

		// Name
		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY, drawPositionX + statsImageX + 20, drawPositionY + statsY + 20);
		if (bestiaryKnowledge.name) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY, width - 40, false, null,
					Color.WHITE, "Name ", actor.name);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY, width - 40, false, null,
					Color.WHITE, "Name ", unknownStats);

		}

		// Level

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 30, drawPositionX + statsImageX + 20, drawPositionY + statsY + 30 + 20);
		if (bestiaryKnowledge.level) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 30, width - 40, false,
					null, Color.WHITE, "Level ", "" + actor.level);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 30, width - 40, false,
					null, Color.WHITE, "Level ", unknownStats);

		}

		// total health

		TextureUtils.drawTexture(getGlobalImage("action_slash.png", false), drawPositionX + statsImageX,
				drawPositionY + statsY + 60, drawPositionX + statsImageX + 20, drawPositionY + statsY + 60 + 20);
		if (bestiaryKnowledge.totalHealth) {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 60, width - 40, false,
					null, Color.WHITE, "Max HP ", "" + actor.totalHealth);

		} else {
			TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + 60, width - 40, false,
					null, Color.WHITE, "Max HP ", unknownStats);

		}

		// HIGH LVL STATS
		TextUtils.printTextWithImages(drawPositionX + statsImageX, drawPositionY + statsY + 120, width - 40, false,
				null, Color.WHITE, "STATS");

		float offsetY = 150;

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {

			TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(statType), drawPositionX + statsImageX,
					drawPositionY + statsY + offsetY, drawPositionX + statsImageX + 20,
					drawPositionY + statsY + offsetY + 20);
			if (bestiaryKnowledge.getHighLevel(statType)) {
				TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + offsetY, width - 40,
						false, null, Color.WHITE, CharacterScreen.highLevelStatNamesShort.get(statType),
						"" + actor.highLevelStats.get(statType).value);
			} else {
				TextUtils.printTextWithImages(drawPositionX + statsTextX, drawPositionY + statsY + offsetY, width - 40,
						false, null, Color.WHITE, CharacterScreen.highLevelStatNamesShort.get(statType), unknownStats);

			}
			offsetY += 30;
		}

		// RESISTANCES
		TextUtils.printTextWithImages(drawPositionX + resistancesImageX, drawPositionY + resistancesY + 30, width - 40,
				false, null, Color.WHITE, "RESISTANCES");

		offsetY = 60;

		for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {

			TextureUtils.drawTexture(CharacterScreen.highLevelStatImages.get(statType),
					drawPositionX + resistancesImageX, drawPositionY + resistancesY + offsetY,
					drawPositionX + resistancesImageX + 20, drawPositionY + resistancesY + offsetY + 20);
			if (bestiaryKnowledge.getHighLevel(statType)) {
				TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + offsetY,
						width - 40, false, null, Color.WHITE, CharacterScreen.highLevelStatNamesShort.get(statType),
						"" + actor.highLevelStats.get(statType).value);
			} else {
				TextUtils.printTextWithImages(drawPositionX + resistancesTextX, drawPositionY + resistancesY + offsetY,
						width - 40, false, null, Color.WHITE, CharacterScreen.highLevelStatNamesShort.get(statType),
						unknownStats);

			}
			offsetY += 30;
		}

		// POWERS
		TextUtils.printTextWithImages(drawPositionX + powersImageX, drawPositionY + powersTitleY, width - 40, false,
				null, Color.WHITE, "POWERS");

		if (bestiaryKnowledge.powers && actor.powers.size() == 0) {
			TextUtils.printTextWithImages(drawPositionX + powersTextX, drawPositionY + powersListY, width - 40, false,
					null, Color.WHITE, "None");
		} else if (bestiaryKnowledge.powers) {
			int powersDrawn = 0;
			for (Power power : actor.powers) {
				TextureUtils.drawTexture(power.image, drawPositionX + powersImageX,
						drawPositionY + powersListY + powersDrawn * 30, drawPositionX + powersImageX + 20,
						drawPositionY + powersListY + powersDrawn * 30 + 20);
				TextUtils.printTextWithImages(drawPositionX + powersTextX,
						drawPositionY + powersListY + powersDrawn * 30, width - 40, false, null, Color.WHITE,
						power.name);
				powersDrawn++;
			}
		} else {
			TextUtils.printTextWithImages(drawPositionX + powersTextX, drawPositionY + powersListY, width - 40, false,
					null, Color.WHITE, unknownStats);
		}

		// PRACTICAL DAMAGE

	}

	public boolean mouseOverCloseButton(float mouseX, float mouseY) {
		return closeButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean mouseOverMinimiseButton(float mouseX, float mouseY) {
		return minimiseButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	public boolean mouseOverInvisibleMinimiseButton(float mouseX, float mouseY) {
		return titleBarButton.calculateIfPointInBoundsOfButton(mouseX, mouseY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		this.drawPositionX += dragX;
		this.drawPositionY -= dragY;
		this.titleBarButton.x += dragX;
		this.titleBarButton.y -= dragY;
		this.closeButton.x += dragX;
		this.closeButton.y -= dragY;
		this.minimiseButton.x += dragX;
		this.minimiseButton.y -= dragY;

		if (drawPositionX < 0) {
			drawPositionX = titleBarButton.x = 0;
			this.closeButton.x = drawPositionX + width - 20;
			this.minimiseButton.x = drawPositionX + width - 40;
		} else if (drawPositionX > Game.windowWidth - 20) {
			drawPositionX = titleBarButton.x = Game.windowWidth - 20;
			this.closeButton.x = drawPositionX + width - 20;
			this.minimiseButton.x = drawPositionX + width - 40;
		}

		if (drawPositionY < 0) {
			drawPositionY = titleBarButton.y = 0;
			this.closeButton.y = drawPositionY;
			this.minimiseButton.y = drawPositionY;
		} else if (drawPositionY > Game.windowHeight - 20) {
			drawPositionY = titleBarButton.y = Game.windowHeight - 20;
			this.closeButton.y = drawPositionY;
			this.minimiseButton.y = drawPositionY;
		}

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (!minimised) {
			if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
					&& mouseY < drawPositionY + height) {
				return true;
			}
		} else {
			if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
					&& mouseY < drawPositionY + titleBarHeight) {
				return true;
			}

		}
		return false;
	}

	public void bringToFront() {
		Game.level.pinWindows.remove(this);
		Game.level.pinWindows.add(this);
	}

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub

	}
}
