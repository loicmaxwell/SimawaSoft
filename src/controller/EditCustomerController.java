package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditCustomerController implements Initializable {
	@FXML
	private Label lbl_customerName;	
	@FXML
	private	TextField txt_idCard;
	@FXML
	private	TextField txt_firstname;
	@FXML
	private	TextField txt_lastname;
	@FXML
	private	TextField txt_email;
	@FXML
	private	TextField txt_phone;
	@FXML
	private	DatePicker txt_birthdate;
	@FXML
	private	Button btn_save;
	@FXML
	private	Button btn_cancel;
	@FXML
	private ComboBox<String> cb_typeDocument;	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_typeDocument.getItems().addAll("CNI","Passport");
		
	}
	
	
}
