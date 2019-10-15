package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;

public class FastTravelLocation extends MapLevelGameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public static Texture FAST_TRAVEL_LOCATION_TEXTURE;

	public static CopyOnWriteArrayList<Texture> MAP_MARKER_TEXTURES = new CopyOnWriteArrayList<Texture>(Texture.class);

	public CopyOnWriteArrayList<Link> links;

	public FastTravelLocation() {
		super();
		links = new CopyOnWriteArrayList<Link>(Link.class);
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
		fastTravelLocation.name = locationName;// "Fast Travel (" + locationName + ")";
		return fastTravelLocation;
	}

	@Override
	public void drawStaticUI() {
		if (this.squareGameObjectIsOn.seenByPlayer) {
			super.drawStaticUI();
		}
	}

	public static void loadStaticImages() {

		FAST_TRAVEL_LOCATION_TEXTURE = ResourceUtils.getGlobalImage("fast_travel_location.png", true);

	}

}
