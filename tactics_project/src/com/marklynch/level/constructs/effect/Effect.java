package com.marklynch.level.constructs.effect;

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
import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.DamageDealer;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public abstract class Effect implements DamageDealer {

	public transient String logString;

	public String effectName;

	public GameObject source;

	public GameObject target;

	public int totalTurns;

	public int turnsRemaining;

	public Texture imageTexture;

	public transient HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();

	public Effect() {

		highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FRIENDLY_FIRE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(0));

	}

	public abstract void activate();

	public abstract Effect makeCopy(GameObject source, GameObject target);

	public void draw2() {
		// Draw object

		if (!target.squareGameObjectIsOn.visibleToPlayer)
			return;

		if (target.squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * target.drawOffsetRatioX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * target.drawOffsetRatioY);
			if (target != null && target.getPrimaryAnimation() != null) {
				actorPositionXInPixels += target.getPrimaryAnimation().offsetX;
				actorPositionYInPixels += target.getPrimaryAnimation().offsetY;
			}

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			// if (!target.squareGameObjectIsOn.visibleToPlayer)
			// alpha = 0.5f;
			// if (target.hiding)
			alpha = 0.75f;

			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + target.width, actorPositionYInPixels + target.height, target.backwards);
			// TextureUtils.skipNormals = false;
		}
	}

	public static void loadEffectImages() {
		getGlobalImage("effect_bleed.png", false);
		getGlobalImage("effect_burn.png", false);
		getGlobalImage("effect_poison.png", false);
		getGlobalImage("effect_wet.png", false);
	}

	@Override
	public float getEffectiveHighLevelStat(HIGH_LEVEL_STATS statType) {
		float result = highLevelStats.get(statType).value;
		return result;
	}

	@Override
	public ArrayList<Object> getEffectiveHighLevelStatTooltip(HIGH_LEVEL_STATS statType) {
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(effectName + " " + highLevelStats.get(statType).value);
		return result;
	}

	public static String getStringForSavingEffects(ArrayList<Effect> effects) {

		if (effects.size() == 0)
			return "";

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Effect.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectBleed.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectBurning.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectCurse.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectHeal.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectPoison.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectWet.class, serializerForEffect);
		// gsonBuilder.registerTypeAdapter(GameObject.class, serializerForGameObject);
		// gsonBuilder.registerTypeAdapter(Actor.class, serializerForGameObject);
		// gsonBuilder.registerTypeAdapter(Texture.class, serializerForTexture);
		Gson gson = gsonBuilder.create();

		System.out.println("logString = " + effects.get(0).logString);
		System.out.println("effectName = " + effects.get(0).effectName);
		System.out.println("source = " + effects.get(0).source);
		System.out.println("target = " + effects.get(0).target);
		System.out.println("totalTurns = " + effects.get(0).totalTurns);
		System.out.println("turnsRemaining = " + effects.get(0).turnsRemaining);
		System.out.println("imageTexture = " + effects.get(0).imageTexture);
		String jsonInString = gson.toJson(effects);
		System.out.println("jsonString = " + jsonInString);
		return jsonInString;
	}

	static JsonSerializer<Effect> serializerForEffect = new JsonSerializer<Effect>() {
		@Override
		public JsonElement serialize(Effect src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("effectName", src.effectName);
			if (src.source != null)
				jsonObject.addProperty("source", src.source.id);
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

}
