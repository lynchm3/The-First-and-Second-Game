package com.marklynch.level.constructs;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.level.Level;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class Faction {

	public long id;
	public String name;
	public HashMap<Faction, Integer> relationships = new HashMap<Faction, Integer>();
	public ArrayList<Actor> actors = new ArrayList<Actor>();
	public Texture imageTexture = null;

	public Faction(String name, String imagePath) {
		id = Level.generateNewId(this);
		this.name = name;
		this.imageTexture = getGlobalImage(imagePath, false);
	}

	@Override
	public String toString() {
		return name;
	}

	public static String getStringForSavingFactionRelationships(Object relationships) {
		if (relationships == null)
			return "";

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Faction.class, serializerForFaction);
		Gson gson = gsonBuilder.create();
		String jsonInString = gson.toJson(relationships);
		return jsonInString;
	}

	static JsonSerializer<Faction> serializerForFaction = new JsonSerializer<Faction>() {
		@Override
		public JsonElement serialize(Faction src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};
}
