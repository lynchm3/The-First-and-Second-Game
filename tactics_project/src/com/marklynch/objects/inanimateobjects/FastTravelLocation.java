package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;

public class FastTravelLocation extends MapLevelGameObject {

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
