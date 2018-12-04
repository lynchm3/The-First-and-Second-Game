package com.marklynch.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Player;

public class Load {

	public static HashMap<Class<?>, ResultSet> resultSetsWithJustIds = new HashMap<Class<?>, ResultSet>();
	public static HashMap<Class<?>, ResultSet> resultSets = new HashMap<Class<?>, ResultSet>();
	public static HashMap<Class<?>, ArrayList<Field>> fieldsForEachClass = new HashMap<Class<?>, ArrayList<Field>>();

	public static Gson loadDeserializerGson;

	static Connection conn;

	public static void load() {
		long loadStartTime = System.currentTimeMillis();
		System.out.println("Loading...");

		try {
			conn = DriverManager.getConnection(Save.dbConnString);

			if (loadDeserializerGson == null)
				loadDeserializerGson = LoadDeserializerCreatore.createLoadDeserializerGson();

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

			Level.player = (Player) Player.instances.get(0);

			if (conn != null)
				conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		long loadEndTime = System.currentTimeMillis();
		System.out.println("Total load time = " + (loadEndTime - loadStartTime));
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

			// Player id
			// if (objectToLoadId != 129651)
			// continue;

			// System.out.println("LOAD1 clazz = " + clazz);

			Object objectToLoad = Level.ids.get(objectToLoadId);
			if (objectToLoad == null) {
				objectToLoad = clazz.getDeclaredConstructor().newInstance();
				Level.ids.put(objectToLoadId, objectToLoad);
				ArrayList<Object> instances = null;
				try {
					instances = (ArrayList<Object>) clazz.getField("instances").get(null);
					if (instances != null)
						instances.add(objectToLoad);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
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
			Object objectToLoad = Level.ids.get(objectToLoadId);

			int count = 1;
			for (Field field : fields) {

				// Object value = field.get(objectToLoad);
				Class<?> type = field.getType();

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
					loadDeserializerGson.fromJson(resultSet.getString(count), type);
				}

				count++;
			}
		}

		resultSet.close();

	}
}