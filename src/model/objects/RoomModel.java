package model.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Room;

public class RoomModel {
	Connection connection;
	
	public ArrayList<Room> getAllRoom() {
		ArrayList<Room> allRoom = new ArrayList<Room>();
		try {

			String sql = "SELECT * FROM Rooms ";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Room aRoom = new Room();
				
				aRoom.setId_room(rs.getInt("id_room"));
				aRoom.setRoom_number(rs.getString("room_number").trim());
				aRoom.setPrice(rs.getDouble("price"));
				aRoom.setStatus(rs.getString("status").trim());
				aRoom.setSize(rs.getString("size").trim());
				aRoom.setTv(rs.getBoolean("tv"));
				aRoom.setFan(rs.getBoolean("fan"));

				allRoom.add(aRoom);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allRoom;
	}
}
