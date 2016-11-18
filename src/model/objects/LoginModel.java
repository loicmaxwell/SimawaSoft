package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Main;
import beans.User;
import model.database.DBManager;



public class LoginModel {
	Connection connection;
	
	public LoginModel() {
		connection = DBManager.getConnection();
		if(connection == null) System.exit(1);
	}
	
	public boolean isDbConnected(){
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isLogin(String login, String pass) throws SQLException{				
		try {
			String sql = "SELECT * FROM users WHERE login = ? AND password = ? Limit 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, login);
			ps.setString(2, pass);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				User current_user = new User();
				current_user.setId_user(rs.getInt("id_user"));
				current_user.setFirstname(rs.getString("firstname"));
				current_user.setLastname(rs.getString("lastName"));
				current_user.setEmail(rs.getString("email"));
				current_user.setPhone(rs.getInt("phone"));
				current_user.setLogin(rs.getString("login"));
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
}
