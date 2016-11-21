package model.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	
	public static Connection connector(){
		try {
			//Class.forName("org.sqlite.jdbc");
			Connection con = DriverManager.getConnection("jdbc:sqlite:db_test.db");
			System.out.println("DB Connection Succesfull");
			return con;
			
		} catch (Exception e) {
			System.out.println("Connection not Succesfull");
			System.out.println(e);
			return null;
		}
		
	}
}
