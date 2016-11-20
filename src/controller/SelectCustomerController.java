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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SelectCustomerController implements Initializable {
	
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
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cIdCard.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id_card"));
		cFirstname.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstname"));
		cLastname.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastname"));
		customerTable.setItems(data);
		
		// Add listener on search field
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
		    filterCustomerList(oldValue, newValue);
		});
		
		
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
