package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import beans.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.objects.CustomerModel;
import tools.Tools;

public class EditRoomController implements Initializable {
	public Utility utility;

	@FXML
	private Label lbl_roomNumber;
	//@FXML
	//private Label lbl_idCustomer;
	@FXML
	private TextField txt_num;
	@FXML
	private TextField txt_price;
	@FXML
	private ComboBox<String> cb_status;
	@FXML
	private TextField txt_size;
	@FXML
	private CheckBox cb_tv;
	@FXML
	private CheckBox cb_fan;
	@FXML
	private Button btn_cancel;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_status.getItems().addAll("Disponible", "Indisponible");
		cb_status.getSelectionModel().selectFirst();

	}

	@FXML
	public void cancel(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	public void saveRoom(ActionEvent event) {
		System.out.println("save room");
	}
}
