package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Room;
import model.database.DBManager;

public class RoomModel {
	Connection connection;
	
	public RoomModel() {
		connection = DBManager.getConnection();
		if (connection == null)
			System.exit(1);
	}
	
	/***************************************
	 * Insert or Update a Room
	 * @param room
	 * @return the room inserted or updated
	 ***************************************/
	public Room upsertRoom(Room room) {
		try {
			String sql = null;
			PreparedStatement ps;

			// if the id_room is not specified, it is an insert
			if (room.getId_room() == 0) {
				System.out.println("INSERT Room...");
				sql = "INSERT INTO Rooms(room_number, price, status, floor, size, beds, description, tv, fan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				ps = connection.prepareStatement(sql);
			} else {
				System.out.println("UPDATE Room...");
				sql = "UPDATE Rooms SET room_number=?, price=?, status=?, floor=?, size=?, beds=?, description=?, tv=?, fan=? WHERE id_room=?";
				ps = connection.prepareStatement(sql);
				ps.setInt(10, room.getId_room());
			}
			ps.setInt(1, room.getRoom_number());
			ps.setDouble(2, room.getPrice());
			ps.setString(3, room.getStatus());
			ps.setInt(4, room.getFloor());
			ps.setDouble(5, room.getSize());
			ps.setInt(6, room.getBeds());
			ps.setString(7, room.getDescription());
			ps.setBoolean(8, room.getTv());
			ps.setBoolean(9, room.getFan());
			ps.executeUpdate();

			//After insert get id of the room, add to the room variable then return
			if (room.getId_room() == 0) {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");
				 if (rs.next()) {
					 room.setId_room(rs.getInt(1));
				 }
			}
			return room;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*****************************
	 * GET ALL ROOMs
	 * @return List of Room
	 *****************************/
	public ArrayList<Room> getAllRoom() {
		ArrayList<Room> allRooms = new ArrayList<Room>();
		try {

			String sql = "SELECT * FROM Rooms ";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Room aRoom = new Room();				
				aRoom.setId_room(rs.getInt("id_room"));
				aRoom.setRoom_number(rs.getInt("room_number"));
				aRoom.setPrice(rs.getDouble("price"));
				aRoom.setStatus(rs.getString("status"));	
				aRoom.setFloor(rs.getInt("floor"));
				aRoom.setSize(rs.getInt("size"));	
				aRoom.setBeds(rs.getInt("beds"));
				aRoom.setDescription(rs.getString("description"));
				aRoom.setTv(rs.getBoolean("tv"));
				aRoom.setFan(rs.getBoolean("fan"));

				allRooms.add(aRoom);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allRooms;
	}

	/*****************************
	 * GET A ROOM
	 * @param id_room
	 * @return a Room OR null
	 *****************************/
	public Room getRoom(int id_room) {
		Room aRoom = null;
		try {
			String sql = "SELECT * FROM Rooms WHERE id_room = ? Limit 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id_room);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				aRoom = new Room();
				aRoom.setId_room(rs.getInt("id_room"));
				aRoom.setRoom_number(rs.getInt("room_number"));
				aRoom.setPrice(rs.getDouble("price"));
				aRoom.setStatus(rs.getString("status"));	
				aRoom.setFloor(rs.getInt("floor"));
				aRoom.setSize(rs.getInt("size"));	
				aRoom.setBeds(rs.getInt("beds"));
				aRoom.setDescription(rs.getString("description"));
				aRoom.setTv(rs.getBoolean("tv"));
				aRoom.setFan(rs.getBoolean("fan"));
			} 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return aRoom;
	}
	
	/*****************************
	 * DELETE A ROOM
	 * @param id_room
	 * @return Boolean true if OK
	 *****************************/
	public Boolean deleteRoom(int id_room) {
		try {
			String sql = "DELETE FROM Rooms WHERE id_room = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id_room);
			ps.executeUpdate();			
			return true;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}
}