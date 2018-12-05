package com.marklynch.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.routines.AIRoutineForBlind;
import com.marklynch.ai.routines.AIRoutineForDoctor;
import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForMort;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.objects.inanimateobjects.Switch.SWITCH_TYPE;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.Color;
import com.marklynch.utils.Texture;

public class SaveSerializationCreator {

	public static Gson createSaveSerializerGson() {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Texture.class, serializerForTexture);
		gsonBuilder.registerTypeAdapter(Faction.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(Crime.class, serializerForCrime);
		gsonBuilder.registerTypeAdapter(Investigation.class, serializerForInvestigation);
		gsonBuilder.registerTypeAdapter(Sound.class, serializerForSound);
		gsonBuilder.registerTypeAdapter(AILine.class, serializerForAILine);
		gsonBuilder.registerTypeAdapter(Enhancement.class, serializerForEnhancement);
		gsonBuilder.registerTypeAdapter(Inventory.class, serializerForInventory);
		gsonBuilder.registerTypeAdapter(SquareInventory.class, serializerForInventory);
		gsonBuilder.registerTypeAdapter(Square.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(Node.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(SWITCH_TYPE.class, serializerForSWITCH_TYPE);
		gsonBuilder.registerTypeAdapter(Color.class, serializerForColor);
		gsonBuilder.registerTypeAdapter(GroupOfActors.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(SwitchListener.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(Area.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(StructureSection.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(Direction.class, serializerForDirection);
		// gsonBuilder.registerTypeAdapter(Stat.class, serializerForStat);

		// Add serializers for all GamObjects, Effects and aiRoutines
		ArrayList<Class<?>> gameObjectClasses = new ArrayList<Class<?>>();
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.actors"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.inanimateobjects"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.tools"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.armor"));
		System.out.println("gameObjectClasses = " + gameObjectClasses);
		for (Class<?> clazz : gameObjectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForIdable);
		}
		gsonBuilder.registerTypeAdapter(Seesaw.SeesawPart.class, serializerForIdable);

		// Effects
		ArrayList<Class<?>> effectClasses = PackageUtils.getClasses("com.marklynch.level.constructs.effect");
		for (Class<?> clazz : effectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForEffect);
		}

		// AI Routines
		ArrayList<Class<?>> aiRoutineClasses = PackageUtils.getClasses("com.marklynch.ai.routines");
		for (Class<?> clazz : aiRoutineClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForAIRoutine);
		}

		// Power
		ArrayList<Class<?>> powerClasses = PackageUtils.getClasses("com.marklynch.level.constructs.power");
		for (Class<?> clazz : powerClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForPower);
		}

		// Quests
		ArrayList<Class<?>> questClasses = PackageUtils.getClasses("com.marklynch.level.quest");
		for (Class<?> clazz : questClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForIdable);
		}

		// Structure Room
		ArrayList<Class<?>> structureRoomClasses = PackageUtils
				.getClasses("com.marklynch.level.constructs.bounds.structure.structureroom");
		for (Class<?> clazz : structureRoomClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForIdable);
		}

		Gson gson = gsonBuilder.create();
		return gson;
	}

	static JsonSerializer<Idable> serializerForIdable = new JsonSerializer<Idable>() {
		@Override
		public JsonElement serialize(Idable src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.getId());
		}
	};

	// static JsonSerializer<Stat> serializerForStat = new JsonSerializer<Stat>() {
	// @Override
	// public JsonElement serialize(Stat src, Type type, JsonSerializationContext
	// context) {
	// return new JsonPrimitive(src.value);
	// }
	// };

	static JsonSerializer<SWITCH_TYPE> serializerForSWITCH_TYPE = new JsonSerializer<SWITCH_TYPE>() {
		@Override
		public JsonElement serialize(SWITCH_TYPE src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	};

	static JsonSerializer<Direction> serializerForDirection = new JsonSerializer<Direction>() {
		@Override
		public JsonElement serialize(Direction src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	};

	static JsonSerializer<Power> serializerForPower = new JsonSerializer<Power>() {
		@Override
		public JsonElement serialize(Power src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.getClass().getName());
		}
	};

	static JsonSerializer<Texture> serializerForTexture = new JsonSerializer<Texture>() {
		@Override
		public JsonElement serialize(Texture src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.path);
		}
	};

	static JsonSerializer<Color> serializerForColor = new JsonSerializer<Color>() {
		@Override
		public JsonElement serialize(Color src, Type type, JsonSerializationContext context) {
			JsonArray jsonArray = new JsonArray();
			jsonArray.add(new JsonPrimitive(src.r));
			jsonArray.add(new JsonPrimitive(src.g));
			jsonArray.add(new JsonPrimitive(src.b));
			jsonArray.add(new JsonPrimitive(src.a));
			return jsonArray;
		}
	};

	static JsonSerializer<Effect> serializerForEffect = new JsonSerializer<Effect>() {
		@Override
		public JsonElement serialize(Effect src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("class", src.getClass().getName());
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

	static JsonSerializer<AIRoutine> serializerForAIRoutine = new JsonSerializer<AIRoutine>() {
		@Override
		public JsonElement serialize(AIRoutine src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("class", src.getClass().getName());
			if (src.actor != null)
				jsonObject.addProperty("actor", src.actor.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
			if (src.targetSquare != null)
				jsonObject.addProperty("target", src.targetSquare.id);
			jsonObject.addProperty("searchCooldown", src.searchCooldown);
			if (src.searchCooldownActor != null)
				jsonObject.addProperty("searchCooldownActor", src.searchCooldownActor.id);
			jsonObject.addProperty("escapeCooldown", src.escapeCooldown);
			if (src.escapeCooldownAttacker != null)
				jsonObject.addProperty("escapeCooldownAttacker", src.escapeCooldownAttacker.id);
			jsonObject.addProperty("wokenUpCountdown", src.wokenUpCountdown);
			jsonObject.addProperty("state", "" + src.state);
			jsonObject.addProperty("keepInBounds", src.keepInBounds);
			jsonObject.add("areaBounds", Save.saveSerializerGson.toJsonTree(src.areaBounds));
			jsonObject.add("sectionBounds", Save.saveSerializerGson.toJsonTree(src.sectionBounds));
			jsonObject.add("roomBounds", Save.saveSerializerGson.toJsonTree(src.roomBounds));
			jsonObject.add("squareBounds", Save.saveSerializerGson.toJsonTree(src.squareBounds));
			jsonObject.addProperty("currentHobby", "" + src.currentHobby);
			if (src.actorToKeepTrackOf != null)
				jsonObject.addProperty("actorToKeepTrackOf", src.actorToKeepTrackOf.id);
			if (src.lastLocationSeenActorToKeepTrackOf != null)
				jsonObject.addProperty("lastLocationSeenActorToKeepTrackOf", src.lastLocationSeenActorToKeepTrackOf.id);
			jsonObject.addProperty("sleepCounter", src.sleepCounter);
			jsonObject.add("ignoreList", Save.saveSerializerGson.toJsonTree(src.ignoreList));

			if (src instanceof AIRoutineForBlind) {
				// public Sound bellSound = null;
				AIRoutineForBlind aiRoutineForBlind = (AIRoutineForBlind) src;

				jsonObject.addProperty("blind", aiRoutineForBlind.blind.id);
				if (aiRoutineForBlind.meatChunk != null)
					jsonObject.addProperty("meatChunk", aiRoutineForBlind.meatChunk.id);
				if (aiRoutineForBlind.originalMeatChunkSquare != null)
					jsonObject.addProperty("originalMeatChunkSquare", aiRoutineForBlind.originalMeatChunkSquare.id);
				jsonObject.addProperty("hangry", aiRoutineForBlind.hangry);
				jsonObject.addProperty("timeSinceEating", aiRoutineForBlind.timeSinceEating);
				jsonObject.addProperty("failedToGetPathToBellCount", aiRoutineForBlind.failedToGetPathToBellCount);
				jsonObject.addProperty("failedToGetPathToFoodCount", aiRoutineForBlind.failedToGetPathToFoodCount);
				if (aiRoutineForBlind.bellSound != null)
					jsonObject.add("bellSound", Save.saveSerializerGson.toJsonTree(aiRoutineForBlind.bellSound));
			} else if (src instanceof AIRoutineForGuard) {
				jsonObject.addProperty("patrolIndex", ((AIRoutineForGuard) src).patrolIndex);
			} else if (src instanceof AIRoutineForTrader) {
				jsonObject.addProperty("trader", ((AIRoutineForTrader) src).trader.id);
			} else if (src instanceof AIRoutineForHerbivoreWildAnimal) {
				jsonObject.addProperty("hidingCount", ((AIRoutineForHerbivoreWildAnimal) src).hidingCount);
			} else if (src instanceof AIRoutineForMort) {
				AIRoutineForMort aiRoutineForMort = (AIRoutineForMort) src;
				jsonObject.addProperty("mort", aiRoutineForMort.mort.id);
				jsonObject.addProperty("rangBellAsLastResort", aiRoutineForMort.rangBellAsLastResort);
				jsonObject.addProperty("retreatedToRoom", aiRoutineForMort.retreatedToRoom);
				jsonObject.addProperty("feedingDemoState", "" + aiRoutineForMort.feedingDemoState);
			} else if (src instanceof AIRoutineForThief) {
				// int theftCooldown = 0;
				jsonObject.addProperty("theftCooldown", ((AIRoutineForThief) src).theftCooldown);
			} else if (src instanceof AIRoutineForRockGolem) {
				jsonObject.addProperty("rockGolem", ((AIRoutineForRockGolem) src).rockGolem.id);
			} else if (src instanceof AIRoutineForGuard) {
				jsonObject.addProperty("guard", ((AIRoutineForGuard) src).guard.id);
			} else if (src instanceof AIRoutineForDoctor) {
				jsonObject.addProperty("doctor", ((AIRoutineForDoctor) src).doctor.id);
			}

			return jsonObject;
		}
	};

	static JsonSerializer<Crime> serializerForCrime = new JsonSerializer<Crime>() {
		@Override
		public JsonElement serialize(Crime src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.performer != null)
				jsonObject.addProperty("performer", src.performer.id);
			if (src.victim != null)
				jsonObject.addProperty("victim", src.victim.id);
			jsonObject.addProperty("hasBeenToldToStop", src.hasBeenToldToStop);
			jsonObject.addProperty("resolved", src.resolved);
			jsonObject.addProperty("reported", src.reported);
			jsonObject.add("stolenItems", Save.saveSerializerGson.toJsonTree(src.stolenItems));
			jsonObject.addProperty("type", "" + src.type);
			jsonObject.add("crimeListeners", Save.saveSerializerGson.toJsonTree(src.crimeListeners));
			return jsonObject;
		}
	};

	static JsonSerializer<Investigation> serializerForInvestigation = new JsonSerializer<Investigation>() {
		@Override
		public JsonElement serialize(Investigation src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.actor != null)
				jsonObject.addProperty("actor", src.actor.id);
			if (src.square != null)
				jsonObject.addProperty("square", src.square.id);
			jsonObject.addProperty("priority", src.priority);
			return jsonObject;
		}
	};

	static JsonSerializer<Sound> serializerForSound = new JsonSerializer<Sound>() {
		@Override
		public JsonElement serialize(Sound src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.sourcePerformer != null)
				jsonObject.addProperty("sourcePerformer", src.sourcePerformer.id);
			if (src.sourceObject != null)
				jsonObject.addProperty("sourceObject", src.sourceObject.id);
			if (src.sourceSquare != null)
				jsonObject.addProperty("sourceSquare", src.sourceSquare.id);
			jsonObject.add("destinationSquares", Save.saveSerializerGson.toJsonTree(src.destinationSquares));
			jsonObject.addProperty("loudness", src.loudness);
			jsonObject.addProperty("legal", src.legal);
			if (src.actionType != null)
				jsonObject.addProperty("actionType", src.actionType.getName());
			return jsonObject;
		}
	};

	static JsonSerializer<AILine> serializerForAILine = new JsonSerializer<AILine>() {
		@Override
		public JsonElement serialize(AILine src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.source != null)
				jsonObject.addProperty("source", src.source.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
			jsonObject.addProperty("aiLineType", "" + src.aiLineType);
			return jsonObject;
		}
	};

	static JsonSerializer<Enhancement> serializerForEnhancement = new JsonSerializer<Enhancement>() {
		@Override
		public JsonElement serialize(Enhancement src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();

			jsonObject.add("highLevelStats", Save.saveSerializerGson.toJsonTree(src.highLevelStats));
			if (src.imageTexture != null)
				jsonObject.addProperty("imageTexture", src.imageTexture.path);
			jsonObject.addProperty("minRange", src.minRange);
			jsonObject.addProperty("maxRange", src.maxRange);
			jsonObject.add("effect", Save.saveSerializerGson.toJsonTree(src.effect));
			jsonObject.addProperty("templateId", src.templateId);
			jsonObject.addProperty("type", "" + src.type);
			return jsonObject;
		}
	};

	static JsonSerializer<Inventory> serializerForInventory = new JsonSerializer<Inventory>() {
		@Override
		public JsonElement serialize(Inventory src, Type type, JsonSerializationContext context) {
			JsonArray jsonArray = new JsonArray();

			for (GameObject gameObject : src.gameObjects) {
				jsonArray.add(new JsonPrimitive(gameObject.id));
			}

			// jsonArray.

			// JsonObject jsonObject = new JsonObject();

			return jsonArray;
			// return jsonObject;
		}
	};

}
