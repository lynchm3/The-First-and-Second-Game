package com.marklynch.ui.popups;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;

public class PopupMenu implements Draggable, Scrollable {

	public float width;
	public ArrayList<PopupMenuButton> buttons = new ArrayList<PopupMenuButton>();
	public Level level;
	public Square square;
	public PopupMenuButton selectSquareButton;
	public float drawPositionX, drawPositionY;
	public float scrollOffsetY = 0;

	public Button highlightedButton;
	public int highlightedButtonIndex = 0;

	public PopupMenu(float width, Level level, Square square) {
		this.width = width;
		this.level = level;
		this.square = square;

	}

	public void draw() {
		if (square instanceof InventorySquare) {
			drawPositionX = ((InventorySquare) square).xInPixels;
			drawPositionY = ((InventorySquare) square).yInPixels + scrollOffsetY;
		} else {
			float squarePositionX = square.xInGridPixels;
			float squarePositionY = square.yInGridPixels;
			drawPositionX = (Game.windowWidth / 2)
					+ (Game.zoom * (squarePositionX - Game.windowWidth / 2 + Game.getDragXWithOffset()));
			drawPositionY = (Game.windowHeight / 2)
					+ (Game.zoom * (squarePositionY - Game.windowHeight / 2 + Game.getDragYWithOffset()))
					+ scrollOffsetY;
		}

		// QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width,
		// drawPositionY, Game.windowHeight);
		for (Button button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (PopupMenuButton button : buttons) {
			button.down = false;
			// button.depress();
		}

	}

	// public PopupMenuButton getButton(Object object) {
	// for (PopupMenuButton button : buttons) {
	// if (button.object == object)
	// return button;
	// }
	// return null;
	// }

	public void moveHighLightUp() {
		highlightedButton.removeHighlight();
		this.highlightedButtonIndex--;
		if (highlightedButtonIndex < 0)
			highlightedButtonIndex = buttons.size() - 1;
		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlight();
	}

	public void moveHighLightDown() {
		highlightedButton.removeHighlight();
		this.highlightedButtonIndex++;
		if (highlightedButtonIndex >= buttons.size())
			highlightedButtonIndex = 0;
		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlight();

	}

	public void clickHighlightedButton() {
		highlightedButton.click();
	}

	public void clearHighlights() {

		if (highlightedButton != null) {
			highlightedButton.removeHighlight();
		}

	}

	@Override
	public void scroll(float dragX, float dragY) {
		drag(dragX, dragY);

	}

	@Override
	public void drag(float dragX, float dragY) {
		this.scrollOffsetY -= dragY;
		fixScroll(dragY);
		// resize2();S

	}

	private void fixScroll(float dragY) {

		// Check mouse still over a button
		boolean stillOverAButton = false;
		for (Button button : buttons) {
			if (button.calculateIfPointInBoundsOfButton(Mouse.getX(), (int) Game.windowHeight - Mouse.getY() + dragY)) {
				stillOverAButton = true;
				break;
			}
		}

		if (!stillOverAButton) {
			this.scrollOffsetY += dragY;
			return;
		}

		// check not too far off orginial poss
		if (scrollOffsetY > 0) {
			scrollOffsetY = 0;
			return;
		}
		//
		float minimumScrollY = -(buttons.size() * buttons.get(0).height) + Game.SQUARE_HEIGHT;
		if (scrollOffsetY < minimumScrollY) {
			scrollOffsetY = minimumScrollY;
			return;
		}
	}

	@Override
	public void dragDropped() {

	}

}
