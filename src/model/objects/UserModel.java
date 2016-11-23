package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Main;
import beans.User;
import model.database.DBManager;

public class UserModel {
	Connection connection;

	public UserModel() {
		connection = DBManager.getConnection();
		if (connection == null)
			System.exit(1);
	}

	/************************************************
	 * Check if the connexion to the Database is open
	 * @return
	 ************************************************/
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**************************************
	 * Check if the authentification is OK
	 * @param login
	 * @param pwd
	 * @return
	 * @throws SQLException
	 **************************************/
	public boolean isLogin(String login, String pwd) throws SQLException {
		try {
			String sql = "SELECT * FROM users WHERE login = ? AND password = ? Limit 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, login);
			ps.setString(2, pwd);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User current_user = new User();
				current_user.setId_user(rs.getInt("id_user"));
				current_user.setFirstname(rs.getString("firstname"));
				current_user.setLastname(rs.getString("lastName"));
				current_user.setEmail(rs.getString("email"));
				current_user.setPhone(rs.getString("phone"));
				current_user.setLogin(rs.getString("login"));
				current_user.setPassword(rs.getString("password"));
				current_user.setProfile(rs.getString("profile"));

				Main.session.put("current_user", current_user);
				return true;

			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/***************************************
	 * Insert or Update a User
	 * @param user
	 * @return the user inserted or updated
	 ***************************************/
	public User upsertUser(User user) {
		try {
			String sql = null;
			PreparedStatement ps;

			// if the id_user is not specified, it is an insert
			if (user.getId_user() == 0) {
				sql = "INSERT INTO Users(firstname, lastname, email, phone, login, password, profile) VALUES (?, ?, ?, ?, ?, ?, ?)";
				ps = connection.prepareStatement(sql);
			} else {
				System.out.println("UPDATE User...");
				sql = "UPDATE Users SET firstname=?, lastname=?, email=?, phone=?, login=?, password=?, profile=? WHERE id_user=?";
				ps = connection.prepareStatement(sql);
				ps.setInt(8, user.getId_user());
			}
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPhone());
			ps.setString(5, user.getLogin());
			ps.setString(6, user.getPassword());
			ps.setString(7, user.getProfile());
			ps.executeUpdate();
			
			if (user.getId_user() == 0) {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");
				 if (rs.next()) {
					 user.setId_user(rs.getInt(1));
				 }
			}
			return user;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/*****************************
	 * GET ALL USER
	 * @return List of User
	 *****************************/
	public ArrayList<User> getAllUser() {
		ArrayList<User> allUser = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM Users ";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				User aUser = new User();
				aUser.setId_user(rs.getInt("id_user"));
				aUser.setFirstname(rs.getString("firstname"));
				aUser.setLastname(rs.getString("lastName"));
				aUser.setEmail(rs.getString("email"));
				aUser.setPhone(rs.getString("phone"));
				aUser.setLogin(rs.getString("login"));
				aUser.setPassword(rs.getString("password"));
				aUser.setProfile(rs.getString("profile"));

				allUser.add(aUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allUser;
	}

	/*****************************
	 * GET A User
	 * @param id_user
	 * @return the User OR null
	 *****************************/
	public User getUser(int id_user) {
		User aUser = null;
		try {
			String sql = "SELECT * FROM users WHERE id_user = ? Limit 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id_user);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				aUser = new User();
				aUser.setId_user(rs.getInt("id_user"));
				aUser.setFirstname(rs.getString("firstname"));
				aUser.setLastname(rs.getString("lastName"));
				aUser.setEmail(rs.getString("email"));
				aUser.setPhone(rs.getString("phone"));
				aUser.setLogin(rs.getString("login"));
				aUser.setPassword(rs.getString("password"));
				aUser.setProfile(rs.getString("profile"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return aUser;
	}
}
