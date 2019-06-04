package com.marklynch.objects.inanimateobjects;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;
import com.marklynch.utils.Utils;

public class MapLevelGameObject extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public boolean draw1() {
		return false;
	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw3() {

	}

	@Override
	public void drawUI() {

	}

	@Override
	public void drawStaticUI() {

		float alpha = 1;
		if (Game.zoom > Game.zoomLevels[Game.MAP_MODE_ZOOM_LEVEL_INDEX])
			alpha = 0.5f;

		float squarePositionX1 = this.squareGameObjectIsOn.xInGridPixels;
		float squarePositionY1 = this.squareGameObjectIsOn.yInGridPixels;
		float drawPositionX1 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX1 - Game.windowWidth / 2 + Game.getDragXWithOffset()))
				- (Game.HALF_SQUARE_WIDTH - Game.HALF_SQUARE_WIDTH * Game.zoom);
		float drawPositionY1 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY1 - Game.windowHeight / 2 + Game.getDragYWithOffset()))
				- (Game.SQUARE_HEIGHT - Game.SQUARE_HEIGHT * Game.zoom) - (Game.HALF_SQUARE_HEIGHT * Game.zoom);
		float drawPositionX2 = drawPositionX1 + (int) this.width;
		float drawPositionY2 = drawPositionY1 + (int) this.height;
		TextureUtils.drawTexture(imageTexture, alpha, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);

		if (name.length() > 0) {
			TextUtils.printTextWithImages(drawPositionX1, drawPositionY1 + Game.HALF_SQUARE_HEIGHT, Integer.MAX_VALUE,
					false, null, Color.WHITE, 1f, new Object[] { name });
		}

	}

	public boolean checkIfPointOnGameObject() {

		if (Game.zoom > Game.zoomLevels[Game.MAP_MODE_ZOOM_LEVEL_INDEX])
			return false;

		float mouseX = Mouse.getX();
		float mouseY = Game.windowHeight - Mouse.getY();

//		point.x -= x;
//		point.y -= y;

		float squarePositionX1 = this.squareGameObjectIsOn.xInGridPixels;
		float squarePositionY1 = this.squareGameObjectIsOn.yInGridPixels;
		float drawPositionX1 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX1 - Game.windowWidth / 2 + Game.getDragXWithOffset()))
				- (Game.HALF_SQUARE_WIDTH - Game.HALF_SQUARE_WIDTH * Game.zoom);
		float drawPositionY1 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY1 - Game.windowHeight / 2 + Game.getDragYWithOffset()))
				- (Game.SQUARE_HEIGHT - Game.SQUARE_HEIGHT * Game.zoom) - (Game.HALF_SQUARE_HEIGHT * Game.zoom);
		float drawPositionX2 = drawPositionX1 + (int) this.width;
		float drawPositionY2 = drawPositionY1 + (int) this.height;

		mouseX -= drawPositionX1;
		mouseY -= drawPositionY1;

//		if (this.backwards) {
//			// how do i flip it?
//			point.x = this.width - point.x;
//		}

		// FirstCheckBounding box :P

		if (mouseX > 0 && mouseX < this.width && mouseY > 0 && mouseY < this.height) {
			Color color = Utils.getPixel(this.imageTexture, (int) mouseX, (int) mouseY);
			if (color != null && color.a > 0) {
				System.out.println("MOUSEOVER = TRUE");
				return true;
			}
		}
		return false;
	}
}
