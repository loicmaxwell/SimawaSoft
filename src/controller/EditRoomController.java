package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import beans.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.objects.RoomModel;
import tools.Tools;

public class EditRoomController implements Initializable {
	public Utility utility;

	@FXML
	private Label lbl_roomNumber;
	@FXML
	private Label lbl_idRoom;
	@FXML
	private TextField txt_num;
	@FXML
	private TextField txt_price;
	@FXML
	private ComboBox<String> cb_status;
	@FXML
	private TextField txt_floor;
	@FXML
	private TextField txt_size;
	@FXML
	private TextField txt_beds;
	@FXML
	private javafx.scene.control.TextArea txt_description;
	@FXML
	private CheckBox cb_tv;
	@FXML
	private CheckBox cb_fan;
	@FXML
	private Button btn_cancel;
	@FXML
	private Button btn_save;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_status.getItems().addAll("Disponible", "Indisponible");
		cb_status.getSelectionModel().selectFirst();
		
		Room room2Edit = (Room) Main.sessionData.get("room");
		if (room2Edit != null) {
			txt_num.setText(room2Edit.getRoom_number()+"");
			lbl_roomNumber.setText(room2Edit.getRoom_number()+"");
			txt_price.setText(room2Edit.getPrice()+"");
			cb_status.setValue(room2Edit.getStatus());
			txt_floor.setText(room2Edit.getFloor()+"");
			txt_size.setText(room2Edit.getSize()+"");
			txt_beds.setText(room2Edit.getBeds()+"");
			txt_description.setText(room2Edit.getDescription()+"");
			cb_tv.setSelected(room2Edit.getTv());
			cb_fan.setSelected(room2Edit.getFan());
			
			if (room2Edit.getId_room() != 0) {
				lbl_idRoom.setText(String.valueOf(room2Edit.getId_room()));
			}
		}

	}

	@FXML
	public void cancel(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	public void saveRoom(ActionEvent event) {
		if(txt_num.getText().equals("") || txt_price.getText().equals("") || txt_size.getText().equals("")){
			Tools.showErrorDialog("Les champs marqués d'une * sont obligatoires !");
		}
		else{
			try {
				Room room = new Room();
				room.setRoom_number(Integer.parseInt(txt_num.getText()));
				room.setPrice(Double.parseDouble(txt_price.getText()));
				room.setStatus(cb_status.getValue());
				room.setFloor(Integer.parseInt(txt_floor.getText()));
				room.setSize(Double.parseDouble(txt_size.getText()));
				room.setBeds(Integer.parseInt(txt_beds.getText()));
				room.setDescription(txt_description.getText());
				room.setTv(cb_tv.isSelected());
				room.setFan(cb_fan.isSelected());
				
				if(!lbl_idRoom.getText().equals("")){
					room.setId_room(Integer.parseInt(lbl_idRoom.getText()));
				}
				
				RoomModel roomModel = new RoomModel();
		
				//TODO Message d'erreur pour les champs obligatoires du formulaire
				roomModel.upsertRoom(room);
				
				// Fermeture de la fenetre
				((Node)event.getSource()).getScene().getWindow().hide();
	
				// Rafraichir les données Clients
				HomeController.refreshDataRooms();
			} catch (Exception e) {
				Tools.showErrorDialog("Un ou plusieurs champ(s) invalide(s)");
				e.printStackTrace();
			}
		}
	}
}
