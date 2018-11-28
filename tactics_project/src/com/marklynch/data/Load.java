package com.marklynch.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class Load {

	public static void load() {
		loadPrimitivesForType(GameObject.class);
		// loadType(Door.class);
	}

	private static void loadPrimitivesForType(Class clazz) {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = conn.createStatement();

			ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getFields()));
			ArrayList<Field> declaredFields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));

			// Remove transient and static fields, don't want to save them
			for (Field field : (ArrayList<Field>) fields.clone()) {
				if (
				//
				Modifier.isTransient(field.getModifiers())
						//
						|| Modifier.isStatic(field.getModifiers())
						//
						|| (!declaredFields.contains(field) && !field.getName().equals("id")))
				//
				{
					fields.remove(field);
				}
			}

			ArrayList<Object> instances = null;
			try {
				instances = (ArrayList<Object>) clazz.getField("instances").get(null);
			} catch (NoSuchFieldException e) {

			}

			ResultSet rs = statement.executeQuery("select * from " + clazz.getSimpleName() + ";");

			// Insert primites
			while (rs.next()) {

				// IF ITS ID NOT ALREADY LOADED...

				Long objectToLoadId = rs.getLong("id");
				System.out.println("objectToLoadId = " + objectToLoadId);
				Object objectToLoad = Level.ids.get(objectToLoadId);
				if (objectToLoad == null) {
					objectToLoad = clazz.getDeclaredConstructor().newInstance();
					if (instances != null)
						instances.add(objectToLoad);
				}

				int count = 1;
				for (Field field : fields) {

					// Object value = field.get(objectToLoad);
					Class type = field.getType();
					System.out.println("field = " + field);
					// rs.getC

					// System.out.println("Adding " + field.getName() + " @ " + count);

					// Phase 1 (primitives, textures...)
					if (type.isAssignableFrom(Boolean.class)) {
						field.set(objectToLoad, rs.getBoolean(count));
					} else if (type.isAssignableFrom(Long.class)) {
						field.set(objectToLoad, rs.getLong(count));
					} else if (type.isAssignableFrom(Integer.class)) {
						field.set(objectToLoad, rs.getInt(count));
					} else if (type.isAssignableFrom(String.class)) {
						field.set(objectToLoad, rs.getString(count));
					} else if (type.isAssignableFrom(Float.class)) {
						field.set(objectToLoad, rs.getFloat(count));
					} else if (type.isAssignableFrom(Texture.class)) {
						String texturePath = rs.getString(count);
						field.set(objectToLoad, ResourceUtils.getGlobalImage(texturePath, true));
					}

					// Non-primitives
					if (type.isAssignableFrom(SquareInventory.class)) {
						// TODO
					} else if (type.isAssignableFrom(Square.class)) {
						Long squareId = rs.getLong(count);
						if (squareId != 0) {
							System.out.println("squareId = " + squareId);
							Square square = (Square) Level.ids.get(squareId);
							field.set(objectToLoad, square);
							System.out.println("square = " + square);
							System.out.println("square.inventory =" + square.inventory);
							square.inventory.add((GameObject) objectToLoad);
						}

					}

					count++;
				}
			}

			rs.close();
			conn.close();

		} catch (Exception e) {
			System.err.println("loadType() error");
			e.printStackTrace();
		}

	}

	// NON-NORMAL FIELDS
	// if(value instanceof Square)
	//
	// {
	// preparedStatement.setLong(count, ((Square) value).id);
	// }else if(value instanceof InventorySquare)
	// {
	// preparedStatement.setString(count, "TODO InventroySquare class");
	// }else if(value instanceof Actor)
	// {
	// preparedStatement.setString(count, "TODO Actor class");
	// }else if(value instanceof HidingPlace)
	// {
	// preparedStatement.setString(count, "TODO HidingPlace class");
	// }else if(value instanceof Quest)
	// {
	// preparedStatement.setLong(count, ((Quest) value).id);
	// }else if(value instanceof GameObject[])
	// {
	// preparedStatement.setString(count, "TODO GameObject[]");
	// }else if(value instanceof Group)
	// {
	// preparedStatement.setLong(count, ((Group) value).id);
	// }else if(value instanceof Action)
	// {
	// preparedStatement.setLong(count, ((Action) value).id);
	// }else if(value instanceof Enhancement)
	// {
	// preparedStatement.setLong(count, ((Enhancement) value).id);
	// }else if(value instanceof HashMap<?,?>)
	// {
	// // Highlevelstats, may need to create a class HighLevelStats, yey.
	// preparedStatement.setString(count, "TODO HashMap<?, ?> class");
	// }else if(value instanceof ArrayList<?>)
	// {
	// // effects array, actions this turn array
	// preparedStatement.setString(count, "TODO ArrayList<?> class");
	// }

}