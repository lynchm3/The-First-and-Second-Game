package com.marklynch.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;

public class SQLiteTest {
	public static void main(String[] args) throws Exception {
		saveGameObjects();
	}

	public final static String tblGameObject = "GameObject";

	public static void saveGameObjects() {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement stat = conn.createStatement();

			new Templates();
			GameObject apple = Templates.APPLE.makeCopy(null, null);
			stat.executeUpdate("DROP TABLE IF EXISTS " + tblGameObject + ";");
			stat.executeUpdate("CREATE TABLE gameobject (id, name);");
			PreparedStatement prep = conn.prepareStatement("insert into " + tblGameObject + " values (?, ?);");

			for (GameObject gameObject : GameObject.instances) {
				prep.setLong(1, gameObject.id);
				prep.setString(2, gameObject.name);
				prep.addBatch();
			}

			// prep.setString(1, "Turing");
			// prep.setString(2, "computers");
			// prep.addBatch();

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = stat.executeQuery("select * from " + tblGameObject + ";");
			while (rs.next()) {
				System.out.println("id = " + rs.getLong("id"));
				System.out.println("name = " + rs.getString("name"));
			}
			rs.close();
			conn.close();

		} catch (Exception e) {

		}

	}

}