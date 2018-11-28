package com.marklynch;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.FileUtils;

public class SaveAndLoad {

	private static Gson gson = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapterFactory(new FactionTypeAdapterFactory())
			.registerTypeAdapter(GameObject.class, new InterfaceAdapter<GameObject>()).create();

	public static void save() {

		// Gson gson = new
		// GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(new
		// FactionTypeAdapterFactory())
		// .registerTypeAdapter(GameObject.class, new
		// InterfaceAdapter<GameObject>()).create();

		// .registerTypeAdapterFactory(
		// new
		// ScriptTriggerActorSelected.ScriptTriggerActorSelectedAdapterFactory())
		// .registerTypeAdapter(Object.class,
		// new SubClassFriendlyAdapter<Object>())
		// .registerTypeAdapter(ScriptEvent.class, new
		// SubClassFriendlyAdapter<ScriptEvent>())
		// .registerTypeAdapter(ScriptTrigger.class, new
		// SubClassFriendlyAdapter<ScriptTrigger>())
		// .registerTypeAdapter(AIRoutineUtils.class, new
		// SubClassFriendlyAdapter<AIRoutineUtils>()).create();

		String json = gson.toJson(Game.level);
		FileUtils.saveFile(json);
	}

	public static void load() {
		// Gson gson = new GsonBuilder().setPrettyPrinting()
		// .registerTypeAdapterFactory(new
		// SaveAndLoad.FactionTypeAdapterFactory())
		// .registerTypeAdapterFactory(new
		// SaveAndLoad.GameObjectTypeAdapterFactory()).create();
		// .registerTypeAdapterFactory(
		// new
		// ScriptTriggerActorSelected.ScriptTriggerActorSelectedAdapterFactory())
		// .registerTypeAdapter(Object.class,
		// new SubClassFriendlyAdapter<>())
		// .registerTypeAdapter(ScriptEvent.class, new
		// SubClassFriendlyAdapter<ScriptEvent>())
		// .registerTypeAdapter(ScriptTrigger.class, new
		// SubClassFriendlyAdapter<ScriptTrigger>())
		// .registerTypeAdapter(AIRoutineUtils.class, new
		// SubClassFriendlyAdapter<AIRoutineUtils>())
		// .create();
		String json = FileUtils.openFile();
		// FileUtils.saveFile(json);
		if (json != null) {
			Game.level = gson.fromJson(json, Level.class);
			Game.level.postLoad();
			Game.level.loadImages();
		}

	}

	public static class FactionTypeAdapterFactory extends CustomizedTypeAdapterFactory<Faction> {
		public FactionTypeAdapterFactory() {
			super(Faction.class);
		}

		@Override
		protected void beforeWrite(Faction object) {
		}

		@Override
		protected Faction afterRead(Faction object) {
			return object;
		}
	}

	public static class GameObjectTypeAdapterFactory extends CustomizedTypeAdapterFactory<GameObject> {
		public GameObjectTypeAdapterFactory() {
			super(GameObject.class);
		}

		@Override
		protected void beforeWrite(GameObject object) {
		}

		@Override
		protected GameObject afterRead(GameObject object) {
			return object;
		}
	}

	final static class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

		@Override
		public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
			final JsonObject wrapper = new JsonObject();
			wrapper.addProperty("type", object.getClass().getName());
			wrapper.add("data", context.serialize(object));
			return wrapper;
		}

		@Override
		public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context)
				throws JsonParseException {
			final JsonObject wrapper = (JsonObject) elem;
			final JsonElement typeName = get(wrapper, "type");
			final JsonElement data = get(wrapper, "data");
			final Type actualType = typeForName(typeName);
			return context.deserialize(data, actualType);
		}

		private Type typeForName(final JsonElement typeElem) {
			try {
				return Class.forName(typeElem.getAsString());
			} catch (ClassNotFoundException e) {
				throw new JsonParseException(e);
			}
		}

		private JsonElement get(final JsonObject wrapper, String memberName) {
			final JsonElement elem = wrapper.get(memberName);
			if (elem == null)
				throw new JsonParseException(
						"no '" + memberName + "' member found in what was expected to be an interface wrapper");
			return elem;
		}

		// @Override
		// public T deserialize(JsonElement arg0, Type arg1,
		// JsonDeserializationContext arg2) throws JsonParseException {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public JsonElement serialize(T arg0, Type arg1,
		// JsonSerializationContext arg2) {
		// // TODO Auto-generated method stub
		// return null;
		// }
	}
}
