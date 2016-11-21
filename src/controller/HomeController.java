package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import beans.Customer;
import beans.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.objects.CustomerModel;

public class HomeController implements Initializable {
	public Utility utility;
	private CustomerModel customerModel;

	@FXML
	private Label userlbl;
	@FXML
	private MenuButton menuBtn_currentUser;
	@FXML
	TableView<Customer> customerTable;	
	@FXML
	TableColumn<Customer, String> cIdCard;	
	@FXML
	TableColumn<Customer, String> cDocumentType;
	@FXML
	TableColumn<Customer, String> cFirstname;
	@FXML
	TableColumn<Customer, String> cLastname;
	@FXML
	TableColumn<Customer, String> cEmail;
	@FXML
	TableColumn<Customer, String> cPhone;
	@FXML
	TableColumn<Customer, String> cBirthdate;
	@FXML
	TextField searchField;
	
	final ObservableList<Customer> customerTableData = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		utility = new Utility();
		customerModel = new CustomerModel();
		
		User current_user = (User) Main.session.get("current_user");
		String firstName = current_user.getFirstname() != null ? current_user.getFirstname() : "";
		String lastName = current_user.getLastname() != null ? current_user.getLastname().toUpperCase() : "";
		if (current_user != null) {
			userlbl.setText("Bienvenue " + firstName + " " + lastName);
			menuBtn_currentUser.setText(firstName + " " +lastName);
		}

		cIdCard.setCellValueFactory(new PropertyValueFactory<Customer, String>("id_card"));
		cDocumentType.setCellValueFactory(new PropertyValueFactory<Customer, String>("documentType"));
		cFirstname.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstname"));
		cLastname.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastname"));
		cEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		cPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
		cBirthdate.setCellValueFactory(new PropertyValueFactory<Customer, String>("birthdate"));	
		
		customerTableData.addAll(customerModel.getAllCustomer()); 
		customerTable.setItems(customerTableData);
		
		// Add listener on search field		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
		    filterCustomerList(oldValue, newValue);
		});
	}

	public void deconnexion(ActionEvent event) throws IOException {
		// Fermeture de la fenetre
		menuBtn_currentUser.getScene().getWindow().hide();
				
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/view/Login.fxml").openStream());
		Scene scene = new Scene(root);	
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}
	

	public void selectCustomer(){
		Customer userSelected = customerTable.getSelectionModel().getSelectedItem();
		System.out.println(userSelected.getFirstname());
		System.out.println(userSelected.getLastname());
	}
	
	
	public void filterCustomerList(String oldValue, String newValue){
		ObservableList<Customer> filteredList = FXCollections.observableArrayList();
		if(newValue.equals("")){
			customerTable.setItems(customerTableData);
		}
		else{
			newValue = newValue.toUpperCase();
			for(Customer aCustomer : customerTable.getItems()){
				if(aCustomer.getFirstname().toUpperCase().contains(newValue) 
					|| aCustomer.getLastname().toUpperCase().contains(newValue) 
					|| aCustomer.getId_card().contains(newValue))
				{					
					filteredList.add(aCustomer);
				}
			}
			customerTable.setItems(filteredList);
		}
	}

}
