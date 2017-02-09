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
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists users (");
		create.append("id_user INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("firstname VARCHAR(50), ");
		create.append("lastname VARCHAR(50), ");
		create.append("email VARCHAR(100), ");
		create.append("phone VARCHAR(10), ");
		create.append("login VARCHAR(50), ");
		create.append("password VARCHAR(250), ");
		create.append("profile VARCHAR(50), ");
		create.append("createdDate TIMESTAMP DEFAULT current_TIMESTAMP) ");
		update(create.toString());	
		
		// Créer l'utilisateur ROOT
		Statement st = connection.createStatement();;
		String sql = "SELECT * FROM users WHERE login = 'root' Limit 1";
		ResultSet rs = st.executeQuery(sql);
		
		if(!rs.next()){
			st.executeUpdate("insert into users(lastname, login, password) values('root', 'root','password123$')");    // run the query
			st.executeUpdate("insert into users(firstname, lastname, login, password) values('Maxwell', 'KUE', 'kuem','kuem')");    // run the query
	    	st.close();
	    	st = null;
	    }		
	}

	private static void createTableRooms() throws DatabaseException, SQLException {
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists rooms (");
		create.append("id_room INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("room_number VARCHAR(50), ");
		create.append("price DOUBLE(50), ");
		create.append("status VARCHAR(50), ");
		create.append("floor INTEGER, ");
		create.append("size DOUBLE(50), ");
		create.append("beds INTEGER, ");
		create.append("description VARCHAR(255), ");
		create.append("tv BOOLEAN(1), ");
		create.append("fan BOOLEAN(1), ");
		create.append("createdDate TIMESTAMP DEFAULT current_TIMESTAMP) ");
		update(create.toString());
	}

	private static void createTableCustomers() throws DatabaseException, SQLException {
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists customers (");
		create.append("id_customer INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("id_card VARCHAR(50), ");
		create.append("documentType VARCHAR(50), ");
		create.append("firstname VARCHAR(50), ");
		create.append("lastname VARCHAR(50), ");
		create.append("email VARCHAR(100), ");
		create.append("phone VARCHAR(10), ");
		create.append("birthdate VARCHAR(250), ");
		create.append("createdDate TIMESTAMP DEFAULT current_TIMESTAMP) ");
		update(create.toString());
	}

	private static void createTableTransactions() throws DatabaseException, SQLException {
		StringBuilder create = new StringBuilder();
		create.append("create table if not exists transactions (");
		create.append("id_transaction INTEGER PRIMARY KEY AUTOINCREMENT, ");
		create.append("id_user INTEGER(50), ");
		create.append("id_room INTEGER(50), ");
		create.append("id_customer INTEGER(50), ");
		create.append("type VARCHAR(50), ");
		create.append("total_amount DOUBLE(50), ");
		create.append("checkInDate TIMESTAMP, ");	
		create.append("checkOutDate TIMESTAMP, ");		
		create.append("reason VARCHAR(250), ");
		create.append("createdDate TIMESTAMP DEFAULT current_TIMESTAMP, ");
		
		create.append("foreign key(id_user) references users(id_user), ");
		create.append("foreign key(id_room) references rooms(id_room), ");
		create.append("foreign key(id_customer) references customers(id_customer))");
		update(create.toString());
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