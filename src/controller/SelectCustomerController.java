package controller;

import java.net.URL;
import java.util.ResourceBundle;

import beans.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SelectCustomerController implements Initializable {
	
	@FXML
	TableView<Customer> tableCustomer;	
	@FXML
	TableColumn<Customer, Integer> cIdCard;	
	@FXML
	TableColumn<Customer, String> cFirstname;
	@FXML
	TableColumn<Customer, String> cLastname;
	
	final ObservableList<Customer> data = FXCollections.observableArrayList(
			new Customer(10000001, "CNI", "Diesel", "WOUAFO"),
			new Customer(10002256, "CNI", "Maxwell", "KUE"),
			new Customer(10229820, "CNI", "Loic", "MENKOUEN")
	);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cIdCard.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id_card"));
		cFirstname.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstname"));
		cLastname.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastname"));
		tableCustomer.setItems(data);
	}

	public void selectCustomer(){
		Customer userSelected = tableCustomer.getSelectionModel().getSelectedItem();
		System.out.println(userSelected.getFirstname());
		System.out.println(userSelected.getLastname());
	}
}
