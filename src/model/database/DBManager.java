package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.DatabaseException;

public class DBManager {
	
	private static Connection connection = SqliteConnection.connector();
	
	public DBManager() throws DatabaseException, SQLException {
		createTableUsers();
		createTableRooms();
		createTableCustomers();
		createTableTransactions();
	}
	
	
	private static void createTableUsers() throws DatabaseException, SQLException {
		System.out.println("start create table users");
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists users (");
		create.append("id_user INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("firstname varchar(50), ");
		create.append("lastname varchar(50), ");
		create.append("email varchar(100), ");
		create.append("phone varchar(10), ");
		create.append("login varchar(50), ");
		create.append("password varchar(250), ");
		create.append("profile varchar(50)) ");
		update(create.toString());
		
		System.out.println("==> create table Users OK");
		
		// Cr�� l'utilisateur ROOT
		Statement st = connection.createStatement();;
		String sql = "SELECT * FROM users WHERE login = 'root' Limit 1";
		ResultSet rs = st.executeQuery(sql);
		
		if(!rs.next()){		
			System.out.println("Creation de l'utilisateur Root...");
			st.executeUpdate("insert into users(lastname, login,password) values('root', 'root','password123$')");    // run the query
	    	st.close();
	    	st = null;
	    }
		
	}

	private static void createTableTransactions() throws DatabaseException, SQLException {
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists transactions (");
		create.append("id_transaction INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("id_user integer(50), ");
		create.append("id_room integer(50), ");
		create.append("id_customer integer(50), ");
		create.append("type varchar(50), ");
		create.append("periode integer(50), ");
		create.append("total_amount double(50), ");
		create.append("transaction_date timestamp");		
		create.append("reason varchar(250), ");
		
		create.append("foreign key(id_user) references users(id_user), ");
		create.append("foreign key(id_room) references rooms(id_room), ");
		create.append("foreign key(id_customer) references customers(id_customer)) ");
		update(create.toString());		

		System.out.println("==> Table Transactions OK");

	}

	private static void createTableRooms() throws DatabaseException, SQLException {
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists rooms (");
		create.append("id_room INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("room_number varchar(50), ");
		create.append("price double(50), ");
		create.append("status varchar(50), ");
		create.append("size double(50), ");
		create.append("tv boolean(1), ");
		create.append("fan boolean(1)) ");
		update(create.toString());		

		System.out.println("==> Table Rooms OK");
	}

	private static void createTableCustomers() throws DatabaseException, SQLException {
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists customers (");
		create.append("id_customer INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("id_card varchar(50), ");
		create.append("documentType varchar(50), ");
		create.append("firstname varchar(50), ");
		create.append("lastname varchar(50), ");
		create.append("email varchar(100), ");
		create.append("phone varchar(10), ");
		create.append("birthdate varchar(250)) ");
		update(create.toString());

		System.out.println("==> Table Customers OK");
	}
	
	private static synchronized void update(String expression) throws DatabaseException, SQLException
    {
    	Statement st = null;
    	if (connection == null) throw new DatabaseException("No data base connection established");
    	st = connection.createStatement();
    	st.executeUpdate(expression);    // run the query
    	st.close();
    	st = null;
    }
	
	public static Connection getConnection() {
		return connection;
	}


}
