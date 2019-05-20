package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionChangeAppearance;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class MapMarker extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public static Texture RED_MAP_MARKER_TEXTURE;
	public static Texture TREASURE_MAP_MARKER_TEXTURE;
	public static Texture SKULL_MAP_MARKER_TEXTURE;
	public static Texture GREEN_MAP_MARKER_TEXTURE;
	public static Texture BLUE_MAP_MARKER_TEXTURE;

	public static ArrayList<Texture> MAP_MARKER_TEXTURES = new ArrayList<Texture>(Texture.class);

	public String baseName = "";
	public ArrayList<Link> links;

	public MapMarker() {
		super();
		name = "Marker";
		links = new ArrayList<Link>(Link.class);
		links.addAll(TextUtils.getLinks(true, this));
		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;
		attackable = false;
		type = "Marker";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInInventory(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>(Action.class);
		actions.add(new ActionChangeAppearance(this));
		return actions;

	}

	@Override
	public Action getDefaultActionPerformedOnThisInInventory(Actor performer) {
		return new ActionChangeAppearance(this);

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
		float drawPositionX2 = drawPositionX1 + (int) Game.SQUARE_WIDTH;
		float drawPositionY2 = drawPositionY1 + (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(imageTexture, alpha, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);

		if (baseName.length() > 0) {
			TextUtils.printTextWithImages(drawPositionX1, drawPositionY1 + Game.HALF_SQUARE_HEIGHT,
					(int) Game.SQUARE_WIDTH, false, null, Color.WHITE, 1f, new Object[] { baseName });
		}

	}

	@Override
	public MapMarker makeCopy(Square square, Actor owner) {
		MapMarker mapMarker = new MapMarker();
		setInstances(mapMarker);
		super.setAttributesForCopy(mapMarker, square, owner);
		mapMarker.baseName = "";
		mapMarker.name = "Marker";
		return mapMarker;
	}

	public static void loadStaticImages() {

		RED_MAP_MARKER_TEXTURE = ResourceUtils.getGlobalImage("map_marker_red.png", true);
		TREASURE_MAP_MARKER_TEXTURE = ResourceUtils.getGlobalImage("map_marker_treasure.png", true);
		SKULL_MAP_MARKER_TEXTURE = ResourceUtils.getGlobalImage("map_marker_skull.png", true);
		GREEN_MAP_MARKER_TEXTURE = ResourceUtils.getGlobalImage("map_marker_green.png", true);
		BLUE_MAP_MARKER_TEXTURE = ResourceUtils.getGlobalImage("map_marker_blue.png", true);

		MAP_MARKER_TEXTURES.add(RED_MAP_MARKER_TEXTURE);
		MAP_MARKER_TEXTURES.add(TREASURE_MAP_MARKER_TEXTURE);
		MAP_MARKER_TEXTURES.add(SKULL_MAP_MARKER_TEXTURE);
		MAP_MARKER_TEXTURES.add(GREEN_MAP_MARKER_TEXTURE);
		MAP_MARKER_TEXTURES.add(BLUE_MAP_MARKER_TEXTURE);

	}

}
