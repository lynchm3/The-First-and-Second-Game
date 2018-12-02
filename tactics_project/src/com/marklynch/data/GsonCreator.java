package com.marklynch.data;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleed;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.effect.EffectCurse;
import com.marklynch.level.constructs.effect.EffectHeal;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class GsonCreator {

	public static Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Faction.class, serializerForFaction);
		gsonBuilder.registerTypeAdapter(Effect.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectBleed.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectBurning.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectCurse.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectHeal.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectPoison.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectWet.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(GameObject.class, serializerForGameObject);
		gsonBuilder.registerTypeAdapter(Actor.class, serializerForActor);
		gsonBuilder.registerTypeAdapter(Texture.class, serializerForTexture);
		Gson gson = gsonBuilder.create();
		return gson;
	}

	static JsonSerializer<Faction> serializerForFaction = new JsonSerializer<Faction>() {
		@Override
		public JsonElement serialize(Faction src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};

	static JsonSerializer<Effect> serializerForEffect = new JsonSerializer<Effect>() {
		@Override
		public JsonElement serialize(Effect src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("effectName", src.effectName);
			if (src.source != null)
				jsonObject.addProperty("source", src.source.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
			jsonObject.addProperty("totalTurns", src.totalTurns);
			jsonObject.addProperty("turnsRemaining", src.turnsRemaining);
			jsonObject.addProperty("imageTexture", src.imageTexture.path);
			return jsonObject;
		}
	};

	static JsonSerializer<GameObject> serializerForGameObject = new JsonSerializer<GameObject>() {
		@Override
		public JsonElement serialize(GameObject src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};

	static JsonSerializer<Actor> serializerForActor = new JsonSerializer<Actor>() {
		@Override
		public JsonElement serialize(Actor src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};

	static JsonSerializer<Texture> serializerForTexture = new JsonSerializer<Texture>() {
		@Override
		public JsonElement serialize(Texture src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("path", src.path);
			return jsonObject;
		}
	};

	// static JsonSerializer<AIRoutine> serializerForAIRoutine = new
	// JsonSerializer<AIRoutine>() {
	// @Override
	// public JsonElement serialize(AIRoutine src, Type type,
	// JsonSerializationContext context) {
	// JsonObject jsonObject = new JsonObject();
	// return jsonObject;
	// }
	// };

}
