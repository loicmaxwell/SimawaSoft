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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.objects.CustomerModel;
import tools.Tools;

public class EditCustomerController implements Initializable {
	public Utility utility;

	@FXML
	private Label lbl_customerName;
	@FXML
	private Label lbl_idCustomer;
	@FXML
	private TextField txt_idCard;
	@FXML
	private TextField txt_firstname;
	@FXML
	private TextField txt_lastname;
	@FXML
	private TextField txt_email;
	@FXML
	private TextField txt_phone;
	@FXML
	private DatePicker txt_birthdate;
	@FXML
	private Button btn_save;
	@FXML
	private Button btn_cancel;
	@FXML
	private ComboBox<String> cb_documentType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_documentType.getItems().addAll("CNI", "Passport");
		cb_documentType.getSelectionModel().selectFirst();

		Customer customer2Edit = (Customer) Main.sessionData.get("customer");
		if (customer2Edit != null) {
			lbl_customerName.setText(customer2Edit.getFirstname() + " " + customer2Edit.getLastname().toUpperCase());
			txt_idCard.setText(customer2Edit.getId_card());
			cb_documentType.setValue(customer2Edit.getDocumentType());
			txt_firstname.setText(customer2Edit.getFirstname());
			txt_lastname.setText(customer2Edit.getLastname());
			txt_email.setText(customer2Edit.getEmail());
			txt_phone.setText(customer2Edit.getPhone());

			if (txt_birthdate.getValue() != null) {
				txt_birthdate.setValue(LocalDate.parse(customer2Edit.getBirthdate(), Main.formatter));
			}
			if (customer2Edit.getId_customer() != 0) {
				lbl_idCustomer.setText(String.valueOf(customer2Edit.getId_customer()));
			}
		}
	}

	@FXML
	public void cancel(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	public void saveCustomer(ActionEvent event) {
		if(txt_idCard.getText().equals("") || txt_lastname.getText().equals("")){
			Tools.showErrorDialog("Les champs marqués d'une * sont obligatoires !");
		}
		else{
			try {
				Customer customer = new Customer();
				customer.setId_card(txt_idCard.getText());
				customer.setDocumentType(cb_documentType.getValue());
				customer.setFirstname(txt_firstname.getText());
				customer.setLastname(txt_lastname.getText());
				customer.setEmail(txt_email.getText());
				customer.setPhone(txt_phone.getText());
	
				if(txt_birthdate.getValue() != null) {
					customer.setBirthdate(txt_birthdate.getValue().toString());
				}
				if(!lbl_idCustomer.getText().equals("")){
					customer.setId_customer(Integer.parseInt(lbl_idCustomer.getText()));
				}
				
				CustomerModel customerModel = new CustomerModel();
				
				//TODO Message d'erreur pour les champs obligatoires du formulaire
				customerModel.upsertCustomer(customer);
	
				// Fermeture de la fenetre
				((Node)event.getSource()).getScene().getWindow().hide();
	
				// Rafraichir les données Clients
				HomeController.refreshDataCustomer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
