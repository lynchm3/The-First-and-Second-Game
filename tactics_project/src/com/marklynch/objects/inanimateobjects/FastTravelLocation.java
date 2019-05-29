package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class FastTravelLocation extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public static Texture FAST_TRAVEL_LOCATION_TEXTURE;

	public static ArrayList<Texture> MAP_MARKER_TEXTURES = new ArrayList<Texture>(Texture.class);

	public ArrayList<Link> links;

	public FastTravelLocation() {
		super();
		links = new ArrayList<Link>(Link.class);
		links.addAll(TextUtils.getLinks(true, this));
		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;
		attackable = false;
		type = "Fast Travel Location";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

//	@Override
//	public boolean draw1() {
////		return false;
//	}
//
//	@Override
//	public void draw2() {
//
//	}
//
//	@Override
//	public void draw3() {
//
//	}
//
//	@Override
//	public void drawUI() {
//
//	}

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
		float drawPositionX2 = drawPositionX1 + (int) Game.SQUARE_WIDTH;
		float drawPositionY2 = drawPositionY1 + (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(imageTexture, alpha, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);

		if (name.length() > 0) {
			TextUtils.printTextWithImages(drawPositionX1, drawPositionY1 + Game.HALF_SQUARE_HEIGHT,
					(int) Game.SQUARE_WIDTH, false, null, Color.WHITE, 1f, new Object[] { name });
		}

	}

	public FastTravelLocation makeCopy(Square square, Actor owner, String locationName) {
		FastTravelLocation fastTravelLocation = new FastTravelLocation();
		setInstances(fastTravelLocation);
		super.setAttributesForCopy(fastTravelLocation, square, owner);
		fastTravelLocation.name = "Fast Travel (" + locationName + ")";
		return fastTravelLocation;
	}

	public static void loadStaticImages() {

		FAST_TRAVEL_LOCATION_TEXTURE = ResourceUtils.getGlobalImage("fast_travel_location.png", true);

	}

}
