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

public class HomeController implements Initializable {
	public Utility utility;

	@FXML
	private Label userlbl;
	@FXML
	private MenuButton menuBtn_currentUser;
	@FXML
	TableView<Customer> customerTable;	
	@FXML
	TableColumn<Customer, Integer> cIdCard;	
	@FXML
	TableColumn<Customer, String> cFirstname;
	@FXML
	TableColumn<Customer, String> cLastname;
	@FXML
	TextField searchField;
	
	final ObservableList<Customer> data = FXCollections.observableArrayList(
			new Customer(10000001, "CNI", "Diesel", "WOUAFO"),
			new Customer(10002256, "CNI", "Maxwell", "KUE"),
			new Customer(10229820, "CNI", "Loic", "MENKOUEN")
	);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		utility = new Utility();
		
		User current_user = (User) Main.session.get("current_user");
		String firstName = current_user.getFirstname() != null ? current_user.getFirstname() : "";
		String lastName = current_user.getLastname() != null ? current_user.getLastname().toUpperCase() : "";
		if (current_user != null) {
			userlbl.setText("Bienvenue " + firstName + " " + lastName);
			menuBtn_currentUser.setText(firstName + " " +lastName);
		}
		
		cIdCard.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id_card"));
		cFirstname.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstname"));
		cLastname.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastname"));
		customerTable.setItems(data);
		
		// Add listener on search field
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
		    filterCustomerList(oldValue, newValue);
		});
	}

	public void getUser(String user) {
		userlbl.setText(user);
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
			customerTable.setItems(data);
		}
		else{
			newValue = newValue.toUpperCase();
			for(Customer aCustomer : customerTable.getItems()){
				if(aCustomer.getFirstname().toUpperCase().contains(newValue) || aCustomer.getLastname().toUpperCase().contains(newValue)){
					filteredList.add(aCustomer);
				}
			}
			customerTable.setItems(filteredList);
		}
	}

}
