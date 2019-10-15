package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionChangeAppearance;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;

public class MapMarker extends MapLevelGameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public static Texture RED_MAP_MARKER_TEXTURE;
	public static Texture TREASURE_MAP_MARKER_TEXTURE;
	public static Texture SKULL_MAP_MARKER_TEXTURE;
	public static Texture GREEN_MAP_MARKER_TEXTURE;
	public static Texture BLUE_MAP_MARKER_TEXTURE;

	public static CopyOnWriteArrayList<Texture> MAP_MARKER_TEXTURES = new CopyOnWriteArrayList<Texture>(Texture.class);

	public String baseName = "";
	public CopyOnWriteArrayList<Link> links;

	public MapMarker() {
		super();
		name = "Marker";
		links = new CopyOnWriteArrayList<Link>(Link.class);
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
	public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInInventory(Actor performer) {
		CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>(Action.class);
		actions.add(new ActionChangeAppearance(this));
		return actions;

	}

	@Override
	public Action getDefaultActionPerformedOnThisInInventory(Actor performer) {
		return new ActionChangeAppearance(this);

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
