package com.marklynch.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.google.gson.Gson;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.utils.ArrayList;

public class Load {

	public static HashMap<Class<?>, ResultSet> resultSetsWithJustIds = new HashMap<Class<?>, ResultSet>();
	public static HashMap<Class<?>, ResultSet> resultSets = new HashMap<Class<?>, ResultSet>();
	public static HashMap<Class<?>, ArrayList<Field>> fieldsForEachClass = new HashMap<Class<?>, ArrayList<Field>>();

	public static Gson loadDeserializerGson;

	static Connection conn;

	public static void load() {

		printPlayerInfo();
		long loadStartTime = System.currentTimeMillis();
		System.out.println("Loading...");

		// Clear current data in mem data
		ArrayList<Long> toRemove = new ArrayList<Long>(Long.class);
		for (Long id : Level.ids.keySet()) {
			if (Level.ids.get(id) instanceof Square) {

			} else if (Level.ids.get(id) instanceof Faction) {

			} else {
				toRemove.add(id);
			}
		}

		for (Long id : toRemove) {
			Level.ids.remove(id);
		}

		for (Square square : Level.squaresToSave) {
			square.inventory = new SquareInventory();
			square.floorImageTexture = square.defaultImageTexture;
		}
		Level.squaresToSave.clear();

		for (Class clazz : Save.classesToSave) {
			if (clazz == Square.class) {

			} else if (clazz == Faction.class) {

			} else {
				try {
					ArrayList<Object> instances = (ArrayList<Object>) clazz.getField("instances").get(null);
					if (instances != null)
						instances.clear();
				} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException
						| SecurityException e) {
					e.printStackTrace();
				}
			}
		}

		Level.actors.clear();

		// End of clearage

		try {
			conn = DriverManager.getConnection(Save.dbConnString);

			if (loadDeserializerGson == null)
				loadDeserializerGson = LoadDeserializerCreator.createLoadDeserializerGson();

			// Squares
			fieldsForEachClass.put(Square.class, Save.getFields(Square.class));
			// System.out.println("fieldsForEachClass.get(Square.class) = " +
			// fieldsForEachClass.get(Square.class));
			resultSetsWithJustIds.put(Square.class, getResultSetWithJustId(Square.class));
			resultSets.put(Square.class, getResultSet(Square.class));
			// System.out.println("resultSets.get(Square.class) = " +
			// resultSets.get(Square.class));
			load1(Square.class);

			// Factions
			fieldsForEachClass.put(Faction.class, Save.getFields(Faction.class));
			resultSetsWithJustIds.put(Faction.class, getResultSetWithJustId(Faction.class));
			resultSets.put(Faction.class, getResultSet(Faction.class));
			load1(Faction.class);

			// GameObjects
			for (Class<?> classToLoad : Save.classesToSave) {
				fieldsForEachClass.put(classToLoad, Save.getFields(classToLoad));
				resultSetsWithJustIds.put(classToLoad, getResultSetWithJustId(classToLoad));
				resultSets.put(classToLoad, getResultSet(classToLoad));
				load1(classToLoad);
			}

			load2(Square.class);
			load2(Faction.class);
			// Gameobject
			for (Class<?> classToLoad : Save.classesToSave) {
				load2(classToLoad);
			}

			Level.lastUpdate = 0;
			Level.player = (Player) Player.instances.get(0);
			Level.lastActorUpdatedIndex = -1;
			Level.startPlayerTurn();
			Level.aiTurn = false;

			if (conn != null)
				conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		;

		long loadEndTime = System.currentTimeMillis();

		System.out.println("Total load time = " + (loadEndTime - loadStartTime));
		printPlayerInfo();
	}

	public static void printPlayerInfo() {
		System.out.println("Player = " + Level.player.toString());
	}

	private static ResultSet getResultSetWithJustId(Class<?> clazz) throws SQLException {

		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery("select id from " + clazz.getSimpleName() + ";");
		return resultSet;
	}

	private static ResultSet getResultSet(Class<?> clazz) throws SQLException {

		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from " + clazz.getSimpleName() + ";");

		return resultSet;
	}

	private static void load1(Class<?> clazz) throws SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ResultSet resultSet = resultSetsWithJustIds.get(clazz);

		if (resultSet == null)
			return;

		while (resultSet.next()) {

			Long objectToLoadId = resultSet.getLong("id");

			Object objectToLoad = Level.ids.get(objectToLoadId);

			// System.out.println("LOAD1 objectToLoad 1 = " + objectToLoad);
			if (objectToLoad == null) {
				objectToLoad = clazz.getDeclaredConstructor().newInstance();
				Level.ids.put(objectToLoadId, objectToLoad);
				ArrayList<Object> instances = null;
				try {
					// System.out.println("class = " + clazz);
					instances = (ArrayList<Object>) clazz.getField("instances").get(null);
					if (instances != null)
						instances.add(objectToLoad);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}

			if (clazz == Actor.class) {
				Level.actors.add((Actor) objectToLoad);
			}
		}

		resultSet.close();

	}

	private static void load2(Class<?> clazz) throws SQLException, IllegalArgumentException, IllegalAccessException {

		ResultSet resultSet = resultSets.get(clazz);
		if (resultSet == null)
			return;

		ArrayList<Field> fields = fieldsForEachClass.get(clazz);

		while (resultSet.next()) {

			Long objectToLoadId = resultSet.getLong("id");

			// Player id
			// if (objectToLoadId != 129651 && objectToLoadId != 9082)
			// continue;

			Object objectToLoad = Level.ids.get(objectToLoadId);
			// System.out.println("LOAD2 =======================");
			// System.out.println("LOAD2 objectToLoad = " + objectToLoad);
			// System.out.println("LOAD2 clazz = " + clazz);
			// System.out.println("LOAD2 fields = " + fields);

			int count = 1;
			for (Field field : fields) {

				// Object value = field.get(objectToLoad);
				Class<?> type = field.getType();
				String value = resultSet.getString(count);
				String fieldName = field.getName();
				// System.out.println("LOAD2 fieldName = " + fieldName + ", type = " + type + ",
				// value = " + value);

				// Non-primitives
				if (type.isAssignableFrom(boolean.class)) {
					field.set(objectToLoad, resultSet.getBoolean(count));
				} else if (type.isAssignableFrom(long.class)) {
					field.set(objectToLoad, resultSet.getLong(count));
				} else if (type.isAssignableFrom(int.class)) {
					field.set(objectToLoad, resultSet.getInt(count));
				} else if (type.isAssignableFrom(float.class)) {
					field.set(objectToLoad, resultSet.getFloat(count));
				} else if (type.isAssignableFrom(double.class)) {
					field.set(objectToLoad, resultSet.getDouble(count));
				} else if (type.isAssignableFrom(Boolean.class)) {
					field.set(objectToLoad, resultSet.getBoolean(count));
				} else if (type.isAssignableFrom(Integer.class)) {
					field.set(objectToLoad, resultSet.getInt(count));
				} else if (type.isAssignableFrom(Long.class)) {
					field.set(objectToLoad, resultSet.getLong(count));
				} else if (type.isAssignableFrom(Float.class)) {
					field.set(objectToLoad, resultSet.getFloat(count));
				} else if (type.isAssignableFrom(Double.class)) {
					field.set(objectToLoad, resultSet.getDouble(count));
				} else if (type.isAssignableFrom(String.class)) {
					field.set(objectToLoad, resultSet.getString(count));
				} else {
					Object deserialized = loadDeserializerGson.fromJson(resultSet.getString(count), type);
					field.set(objectToLoad, deserialized);

					if (objectToLoad instanceof Square) {
						Level.squaresToSave.add((Square) objectToLoad);
					} else if (objectToLoad instanceof Wall) {
						((Wall) objectToLoad).initWall(16f);
					}

					if (objectToLoad instanceof GameObject) {
						GameObject gameObject = (GameObject) objectToLoad;

						gameObject.halfWidth = gameObject.width / 2;
						gameObject.halfHeight = gameObject.height / 2;

					}

				}

				count++;
			}
			// System.out.println("LOAD2 objectToLoad AFTER load = " + objectToLoad);
			// System.out.println("END LOAD2 =======================");
		}

		resultSet.close();

	}
}