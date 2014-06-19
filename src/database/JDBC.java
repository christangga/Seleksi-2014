/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author christangga
 */
public class JDBC {

	private static final float SCALE = (float) 6.89655172;
	
	public JDBC() {}
	
	public static float distance(String asal, String tujuan) {
		float jarak = 0;
		try {
		/* 
		* Load the JDBC driver and establish a connection. 
		*/
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/id";
			Connection conn = DriverManager.getConnection(url, "postgres", "postgres");
			/* 
			 * Create a statement and execute a select query. 
			 */
			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery("SELECT ST_Distance( (SELECT ST_AsText(geom) FROM capital WHERE name LIKE \'" + asal + "\'), (SELECT ST_AsText(geom) FROM capital WHERE name LIKE \'" + tujuan + "\') ) as dist;");
			while (r.next()) {
				/* 
				 * Retrieve the geometry as an object then cast it to the geometry type. 
				 * Print things out. 
				 */
				jarak = r.getFloat("dist") * SCALE;
			}
			s.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jarak;
	}
	
}
