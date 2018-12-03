package com.marklynch.data;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class Load {

	public static HashMap<Class<?>, ResultSet> resultSets = new HashMap<Class<?>, ResultSet>();
	public static HashMap<Class<?>, ArrayList<Field>> fieldsForEachClass = new HashMap<Class<?>, ArrayList<Field>>();

	public static void load() {
		long loadStartTime = System.currentTimeMillis();
		// System.out.println("Starting load @ " + loadStartTime);

		for (Class<?> classToLoad : Save.classesToSave) {
			resultSets.put(classToLoad, getResultSet(GameObject.class));
			fieldsForEachClass.put(classToLoad, Save.getFields(GameObject.class));
			load1(classToLoad);
		}
		for (Class<?> classToSave : Save.classesToSave) {
			load2(classToSave);
		}

		Level.player = (Player) Player.instances.get(0);
		long loadEndTime = System.currentTimeMillis();
		// System.out.println("Ending load @ " + loadEndTime);
		System.out.println("Total load time = " + (loadEndTime - loadStartTime));
	}

	private static ResultSet getResultSet(Class<?> clazz) {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(Save.dbConn);
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from " + clazz.getSimpleName() + ";");

			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void load1(Class<?> clazz) {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");

			ResultSet resultSet = resultSets.get(clazz);
			ArrayList<Field> fields = fieldsForEachClass.get(clazz);

			while (resultSet.next()) {

				Long objectToLoadId = resultSet.getLong("id");
				Object objectToLoad = Level.ids.get(objectToLoadId);
				if (objectToLoad == null) {
					Level.ids.put(objectToLoadId, objectToLoad);
					objectToLoad = clazz.getDeclaredConstructor().newInstance();
					ArrayList<Object> instances = null;
					try {
						instances = (ArrayList<Object>) clazz.getField("instances").get(null);
						if (instances != null)
							instances.add(objectToLoad);
					} catch (NoSuchFieldException e) {

					}
				}

				int count = 1;
				for (Field field : fields) {

					// Object value = field.get(objectToLoad);
					Class<?> type = field.getType();
					// rs.getC

					// System.out.println("Adding " + field.getName() + " @ " + count);

					// Phase 1 (primitives, textures...)
					if (type.isAssignableFrom(Boolean.class)) {
						field.set(objectToLoad, resultSet.getBoolean(count));
					} else if (type.isAssignableFrom(Long.class)) {
						field.set(objectToLoad, resultSet.getLong(count));
					} else if (type.isAssignableFrom(Integer.class)) {
						field.set(objectToLoad, resultSet.getInt(count));
					} else if (type.isAssignableFrom(String.class)) {
						field.set(objectToLoad, resultSet.getString(count));
					} else if (type.isAssignableFrom(Float.class)) {
						field.set(objectToLoad, resultSet.getFloat(count));
					} else if (type.isAssignableFrom(Texture.class)) {
						String texturePath = resultSet.getString(count);
						field.set(objectToLoad, ResourceUtils.getGlobalImage(texturePath, true));
					}

					count++;
				}
			}

			resultSet.close();
			conn.close();

		} catch (Exception e) {
			System.err.println("load1 error");
			e.printStackTrace();
		}

	}

	private static void load2(Class<?> clazz) {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");

			ResultSet resultSet = resultSets.get(clazz);
			ArrayList<Field> fields = fieldsForEachClass.get(clazz);

			while (resultSet.next()) {

				Long objectToLoadId = resultSet.getLong("id");
				Object objectToLoad = Level.ids.get(objectToLoadId);

				int count = 1;
				for (Field field : fields) {

					// Object value = field.get(objectToLoad);
					Class<?> type = field.getType();

					// Non-primitives
					if (type.isAssignableFrom(SquareInventory.class)) {
						// TODO
					} else if (type.isAssignableFrom(Square.class)) {
						Long squareId = resultSet.getLong(count);
						if (squareId != 0) {
							Square square = (Square) Level.ids.get(squareId);
							field.set(objectToLoad, square);
						}
					} else if (type.isAssignableFrom(Quest.class)) {
						Long squareId = resultSet.getLong(count);
						if (squareId != 0) {
							Quest square = (Quest) Level.ids.get(squareId);
							field.set(objectToLoad, square);
						}

					} else if (type.isAssignableFrom(GroupOfActors.class)) {
						Long squareId = resultSet.getLong(count);
						if (squareId != 0) {
							GroupOfActors square = (GroupOfActors) Level.ids.get(squareId);
							field.set(objectToLoad, square);
						}

					} else if (type.isAssignableFrom(Action.class)) {
						Long squareId = resultSet.getLong(count);
						if (squareId != 0) {
							Action square = (Action) Level.ids.get(squareId);
							field.set(objectToLoad, square);
						}

					} else if (type.isAssignableFrom(Enhancement.class)) {
						Long squareId = resultSet.getLong(count);
						if (squareId != 0) {
							Enhancement square = (Enhancement) Level.ids.get(squareId);
							field.set(objectToLoad, square);
						}

					} else if (type.isAssignableFrom(HashMap.class)) {

					} else if (type.isAssignableFrom(ArrayList.class)) {

					} else if (type.isAssignableFrom(Object.class)) {

					} else if (type.isAssignableFrom(GameObject[].class)) {

					} else {
						// nulls
					}

					count++;
				}
			}

			resultSet.close();
			conn.close();

		} catch (

		Exception e) {
			System.err.println("load2 error");
			e.printStackTrace();
		}

	}
}