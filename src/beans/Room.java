package beans;

public class Room {
	private int id_room;
	private int room_number;
	private double price;
	private String status;
	private int floor;
	private double size;
	private int beds;
	private String description;
	private Boolean tv = false;
	private Boolean fan = false;
		
	public Room(int room_number, double price, int size) {
		super();
		this.room_number = room_number;
		this.price = price;
		this.size = size;
	}

	public Room() {
		// TODO Auto-generated constructor stub
	}

	public int getId_room() {
		return id_room;
	}

	public void setId_room(int id_room) {
		this.id_room = id_room;
	}

	public int getRoom_number() {
		return room_number;
	}

	public void setRoom_number(int room_number) {
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

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Boolean getTv() {
		return tv;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
	
}
