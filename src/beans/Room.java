package beans;

public class Room {
	private int id_room;
	private String room_number;
	private double price;
	private String status;
	private String size;
	private Boolean tv;
	private Boolean fan;
	
	public Room() {
		// TODO Auto-generated constructor stub
	}

	public int getId_room() {
		return id_room;
	}

	public void setId_room(int id_room) {
		this.id_room = id_room;
	}

	public String getRoom_number() {
		return room_number;
	}

	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Boolean getTv() {
		return tv;
	}

	public void setTv(Boolean tv) {
		this.tv = tv;
	}

	public Boolean getFan() {
		return fan;
	}

	public void setFan(Boolean fan) {
		this.fan = fan;
	}
	
	
}
