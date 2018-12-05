package com.marklynch.data;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.routines.AIRoutine.STATE;
import com.marklynch.ai.routines.AIRoutineForBlind;
import com.marklynch.ai.routines.AIRoutineForDoctor;
import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForMort;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventoryParent;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.Blind;
import com.marklynch.objects.actors.Doctor;
import com.marklynch.objects.actors.Guard;
import com.marklynch.objects.actors.Mort;
import com.marklynch.objects.actors.RockGolem;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.MeatChunk;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.objects.inanimateobjects.WaterBody;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class LoadDeserializerCreator {

	public static Gson createLoadDeserializerGson() {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Texture.class, deserializerForTexture);
		gsonBuilder.registerTypeAdapter(Faction.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(GroupOfActors.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(SwitchListener.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Square.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Node.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Area.class, deserializerForIdable);
		// gsonBuilder.registerTypeAdapter(Crime.class, deserializerForCrime);
		// gsonBuilder.registerTypeAdapter(Investigation.class,
		// deserializerForInvestigation);
		// gsonBuilder.registerTypeAdapter(Sound.class, deserializerForSound);
		// gsonBuilder.registerTypeAdapter(AILine.class, deserializerForAILine);
		// gsonBuilder.registerTypeAdapter(Enhancement.class,
		// deserializerForEnhancement);
		gsonBuilder.registerTypeAdapter(Inventory.class, deserializerForInventory);
		gsonBuilder.registerTypeAdapter(SquareInventory.class, deserializerForInventory);
		// gsonBuilder.registerTypeAdapter(SWITCH_TYPE.class,
		// deserializerForSWITCH_TYPE);
		// gsonBuilder.registerTypeAdapter(Color.class, deserializerForColor);
		// gsonBuilder.registerTypeAdapter(StructureSection.class,
		// deserializerForIdable);
		// gsonBuilder.registerTypeAdapter(Direction.class, deserializerForDirection);
		// gsonBuilder.registerTypeAdapter(Stat.class, deserializerForStat);

		// Add deserializers for all GamObjects, Effects and aiRoutines
		ArrayList<Class<?>> gameObjectClasses = new ArrayList<Class<?>>();
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.actors"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.inanimateobjects"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.tools"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.armor"));
		for (Class<?> clazz : gameObjectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForIdable);
		}
		gsonBuilder.registerTypeAdapter(Seesaw.SeesawPart.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Idable.class, deserializerForIdable);
		System.out.println("Deserializer - gameObjectClasses = " + gameObjectClasses);

		// Effects
		ArrayList<Class> effectClasses = new ArrayList<Class>(Class.class);
		effectClasses.addAll(PackageUtils.getClasses("com.marklynch.level.constructs.effect"));
		for (Class<?> clazz : effectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForEffect);
		}

		// AI Routines
		ArrayList<Class> aiRoutineClasses = new ArrayList<Class>(Class.class);
		aiRoutineClasses.addAll(PackageUtils.getClasses("com.marklynch.ai.routines"));
		for (Class<?> clazz : aiRoutineClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForAIRoutine);
		}

		// // Power
		ArrayList<Class> powerClasses = new ArrayList<Class>(Class.class);
		powerClasses.addAll(PackageUtils.getClasses("com.marklynch.level.constructs.power"));
		for (Class<?> clazz : powerClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForPower);
		}

		// Quests
		ArrayList<Class> questClasses = new ArrayList<Class>(Class.class);
		questClasses.addAll(PackageUtils.getClasses("com.marklynch.level.quest"));
		for (Class<?> clazz : questClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForIdable);
		}

		// Structure Room
		ArrayList<Class> structureRoomClasses = new ArrayList<Class>(Class.class);
		structureRoomClasses
				.addAll(PackageUtils.getClasses("com.marklynch.level.constructs.bounds.structure.structureroom"));
		for (Class<?> clazz : structureRoomClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForIdable);
		}

		Gson gson = gsonBuilder.create();
		return gson;

	}

	// change serialization for specific types
	public static JsonDeserializer<Object> deserializerForIdable = new JsonDeserializer<Object>() {
		@Override
		public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Object o = Level.ids.get(json.getAsLong());
			// System.out.println("ID to serialize = " + json.getAsLong());
			// System.out.println("Deserialized Idable = " + o);
			return o;
		}
	};

	// Texture
	public static JsonDeserializer<Texture> deserializerForTexture = new JsonDeserializer<Texture>() {
		@Override
		public Texture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return ResourceUtils.getGlobalImage(json.getAsString(), true);
		}
	};

	// Effect
	public static JsonDeserializer<Effect> deserializerForEffect = new JsonDeserializer<Effect>() {
		@Override
		public Effect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			Effect effect = null;

			JsonObject jsonObject = json.getAsJsonObject();
			String classString = jsonObject.get("class").getAsString();
			Class<?> clazz;
			try {
				clazz = Class.forName(classString);
				effect = (Effect) clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			effect.effectName = jsonObject.get("effectName").getAsString();

			JsonElement sourceJson = jsonObject.get("source");
			if (sourceJson != null)
				effect.source = (GameObject) Level.ids.get(sourceJson.getAsLong());

			JsonElement targetJson = jsonObject.get("target");
			if (targetJson != null)
				effect.target = (GameObject) Level.ids.get(targetJson.getAsLong());

			effect.totalTurns = jsonObject.get("totalTurns").getAsInt();
			effect.turnsRemaining = jsonObject.get("turnsRemaining").getAsInt();

			JsonElement imageTextureJson = jsonObject.get("imageTexture");
			if (imageTextureJson != null)
				effect.imageTexture = ResourceUtils.getGlobalImage(imageTextureJson.getAsString(), true);

			return effect;
		}
	};

	// AIRoutine
	public static JsonDeserializer<AIRoutine> deserializerForAIRoutine = new JsonDeserializer<AIRoutine>() {
		@Override
		public AIRoutine deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			AIRoutine aiRoutine = null;

			JsonObject jsonObject = json.getAsJsonObject();
			String classString = jsonObject.get("class").getAsString();
			Class<?> clazz;
			try {
				clazz = Class.forName(classString);
				aiRoutine = (AIRoutine) clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

			JsonElement actorJson = jsonObject.get("actor");
			if (actorJson != null)
				aiRoutine.actor = (Actor) Level.ids.get(actorJson.getAsLong());

			JsonElement targetJson = jsonObject.get("target");
			if (targetJson != null)
				aiRoutine.target = (GameObject) Level.ids.get(targetJson.getAsLong());

			JsonElement targetSquareJson = jsonObject.get("targetSquare");
			if (targetSquareJson != null)
				aiRoutine.targetSquare = (Square) Level.ids.get(targetSquareJson.getAsLong());

			aiRoutine.searchCooldown = jsonObject.get("searchCooldown").getAsInt();

			JsonElement searchCooldownActorJson = jsonObject.get("searchCooldownActor");
			if (searchCooldownActorJson != null)
				aiRoutine.actor = (Actor) Level.ids.get(searchCooldownActorJson.getAsLong());

			aiRoutine.escapeCooldown = jsonObject.get("escapeCooldown").getAsInt();

			JsonElement escapeCooldownAttackerJson = jsonObject.get("escapeCooldownAttacker");
			if (escapeCooldownAttackerJson != null)
				aiRoutine.actor = (Actor) Level.ids.get(escapeCooldownAttackerJson.getAsLong());

			aiRoutine.wokenUpCountdown = jsonObject.get("wokenUpCountdown").getAsInt();

			JsonElement stateJson = jsonObject.get("state");
			aiRoutine.state = Load.loadDeserializerGson.fromJson(stateJson, STATE.class);

			aiRoutine.keepInBounds = jsonObject.get("keepInBounds").getAsBoolean();

			JsonElement areaBoundsJson = jsonObject.get("areaBounds");

			// System.out.println("aiRoutine.areaBounds.getClass() = " +
			// aiRoutine.areaBounds.getClass());
			// System.out.println("areaBoundsJson = " + areaBoundsJson);
			// System.out.println("areaBoundsJson.getAsString().isEmpty() = " +
			// areaBoundsJson.getAsString().isEmpty());

			// if (areaBoundsJson != null && !areaBoundsJson.getAsString().isEmpty())
			aiRoutine.areaBounds = Load.loadDeserializerGson.fromJson(areaBoundsJson, aiRoutine.areaBounds.getClass());

			JsonElement sectionBoundsJson = jsonObject.get("sectionBounds");
			aiRoutine.sectionBounds = Load.loadDeserializerGson.fromJson(sectionBoundsJson,
					aiRoutine.sectionBounds.getClass());

			JsonElement roomBoundsJson = jsonObject.get("roomBounds");
			// System.out.println("roomBoundsJson = " + roomBoundsJson);
			aiRoutine.roomBounds = Load.loadDeserializerGson.fromJson(roomBoundsJson, aiRoutine.roomBounds.getClass());

			JsonElement squareBoundsJson = jsonObject.get("squareBounds");
			aiRoutine.squareBounds = Load.loadDeserializerGson.fromJson(squareBoundsJson,
					aiRoutine.squareBounds.getClass());

			JsonElement currentHobbyJson = jsonObject.get("currentHobby");
			aiRoutine.currentHobby = Load.loadDeserializerGson.fromJson(currentHobbyJson, HOBBY.class);

			JsonElement actorToKeepTrackOfJson = jsonObject.get("actorToKeepTrackOf");
			if (actorToKeepTrackOfJson != null)
				aiRoutine.actorToKeepTrackOf = (Actor) Level.ids.get(actorToKeepTrackOfJson.getAsLong());

			aiRoutine.lastLocationSeenActorToKeepTrackOf = Load.loadDeserializerGson
					.fromJson(jsonObject.get("lastLocationSeenActorToKeepTrackOf"), Square.class);

			aiRoutine.sleepCounter = jsonObject.get("sleepCounter").getAsInt();

			JsonElement ignoreListJson = jsonObject.get("ignoreList");
			aiRoutine.ignoreList = Load.loadDeserializerGson.fromJson(jsonObject.get("ignoreList"),
					aiRoutine.ignoreList.getClass());

			if (aiRoutine instanceof AIRoutineForBlind) {

				AIRoutineForBlind aiRoutineForBlind = (AIRoutineForBlind) aiRoutine;

				aiRoutineForBlind.blind = (Blind) Level.ids.get(jsonObject.get("blind").getAsLong());

				JsonElement meatChunkJson = jsonObject.get("meatChunk");
				if (meatChunkJson != null)
					aiRoutineForBlind.meatChunk = (MeatChunk) Level.ids.get(meatChunkJson.getAsLong());

				JsonElement originalMeatChunkSquareJson = jsonObject.get("originalMeatChunkSquare");
				if (originalMeatChunkSquareJson != null)
					aiRoutineForBlind.originalMeatChunkSquare = (Square) Level.ids
							.get(originalMeatChunkSquareJson.getAsLong());

				aiRoutineForBlind.hangry = jsonObject.get("hangry").getAsBoolean();
				aiRoutineForBlind.timeSinceEating = jsonObject.get("timeSinceEating").getAsInt();
				aiRoutineForBlind.failedToGetPathToBellCount = jsonObject.get("failedToGetPathToBellCount").getAsInt();
				aiRoutineForBlind.failedToGetPathToFoodCount = jsonObject.get("failedToGetPathToFoodCount").getAsInt();

				JsonElement bellSoundJson = jsonObject.get("bellSound");
				aiRoutineForBlind.bellSound = Load.loadDeserializerGson.fromJson(bellSoundJson, Sound.class);

			} else if (aiRoutine instanceof AIRoutineForGuard) {
				((AIRoutineForGuard) aiRoutine).patrolIndex = jsonObject.get("patrolIndex").getAsInt();
			} else if (aiRoutine instanceof AIRoutineForHerbivoreWildAnimal) {
				((AIRoutineForHerbivoreWildAnimal) aiRoutine).hidingCount = jsonObject.get("hidingCount").getAsInt();
			} else if (aiRoutine instanceof AIRoutineForTrader) {
				AIRoutineForTrader aiRoutineForTrader = (AIRoutineForTrader) aiRoutine;
				aiRoutineForTrader.trader = (Trader) Level.ids.get(jsonObject.get("trader").getAsLong());
			} else if (aiRoutine instanceof AIRoutineForMort) {
				AIRoutineForMort aiRoutineForMort = (AIRoutineForMort) aiRoutine;
				aiRoutineForMort.mort = (Mort) Level.ids.get(jsonObject.get("mort").getAsLong());
				aiRoutineForMort.rangBellAsLastResort = jsonObject.get("rangBellAsLastResort").getAsBoolean();
				aiRoutineForMort.retreatedToRoom = jsonObject.get("retreatedToRoom").getAsBoolean();
				aiRoutineForMort.feedingDemoState = Load.loadDeserializerGson
						.fromJson(jsonObject.get("feedingDemoState"), aiRoutineForMort.feedingDemoState.getClass());
			} else if (aiRoutine instanceof AIRoutineForThief) {
				((AIRoutineForThief) aiRoutine).theftCooldown = jsonObject.get("theftCooldown").getAsInt();
			} else if (aiRoutine instanceof AIRoutineForRockGolem) {
				AIRoutineForRockGolem aiRoutineForRockGolem = (AIRoutineForRockGolem) aiRoutine;
				aiRoutineForRockGolem.rockGolem = (RockGolem) Level.ids.get(jsonObject.get("rockGolem").getAsLong());
			} else if (aiRoutine instanceof AIRoutineForGuard) {
				AIRoutineForGuard aiRoutineForguard = (AIRoutineForGuard) aiRoutine;
				aiRoutineForguard.guard = (Guard) Level.ids.get(jsonObject.get("guard").getAsLong());
			} else if (aiRoutine instanceof AIRoutineForDoctor) {
				AIRoutineForDoctor aiRoutineFordoctor = (AIRoutineForDoctor) aiRoutine;
				aiRoutineFordoctor.doctor = (Doctor) Level.ids.get(jsonObject.get("doctor").getAsLong());
			}
			return aiRoutine;
		}
	};

	// Power
	public static JsonDeserializer<Power> deserializerForPower = new JsonDeserializer<Power>() {
		@Override
		public Power deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			Power power = null;
			String classString = json.getAsString();
			Class<?> clazz;

			try {
				clazz = Class.forName(classString);
				power = (Power) clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return power;
		}
	};

	// Inventory
	public static JsonDeserializer<Inventory> deserializerForInventory = new JsonDeserializer<Inventory>() {
		@Override
		public Inventory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			Inventory inventory;
			if (typeOfT == SquareInventory.class) {
				inventory = new SquareInventory();
			} else {
				inventory = new Inventory();
			}

			JsonObject jsonObject = json.getAsJsonObject();
			Type typeToken = new TypeToken<ArrayList<GameObject>>() {
			}.getType();

			inventory.gameObjects = Load.loadDeserializerGson.fromJson(jsonObject.get("gameObjects"), typeToken);

			inventory.parent = (InventoryParent) Level.ids.get(jsonObject.get("parent").getAsLong());

			if (inventory instanceof SquareInventory) {
				SquareInventory squareInventory = (SquareInventory) inventory;
				squareInventory.square = (Square) Level.ids.get(jsonObject.get("square").getAsLong());
				squareInventory.canShareSquare = jsonObject.get("canShareSquare").getAsBoolean();

				JsonElement j = jsonObject.get("gameObjectThatCantShareSquare");
				if (j != null)
					squareInventory.gameObjectThatCantShareSquare = (GameObject) Level.ids.get(j.getAsLong());

				j = jsonObject.get("actor");
				if (j != null)
					squareInventory.actor = (Actor) Level.ids.get(jsonObject.get("actor").getAsLong());

				j = jsonObject.get("door");
				if (j != null)
					squareInventory.door = (Door) Level.ids.get(jsonObject.get("door").getAsLong());

				j = jsonObject.get("waterBody");
				if (j != null)
					squareInventory.waterBody = (WaterBody) Level.ids.get(jsonObject.get("waterBody").getAsLong());

				squareInventory.gameObjectsGround = Load.loadDeserializerGson
						.fromJson(jsonObject.get("gameObjectsGround"), typeToken);
				squareInventory.gameObjectsNonGround = Load.loadDeserializerGson
						.fromJson(jsonObject.get("gameObjectsNonGround"), typeToken);

			}

			return inventory;
		}
	};
}
