package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import beans.Customer;
import beans.Room;
import beans.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.objects.CustomerModel;
import model.objects.RoomModel;
import tools.Tools;

public class HomeController implements Initializable {
	public Utility utility;
	private CustomerModel customerModel;
	private RoomModel roomModel;
	
	//***** HEADER ****
	@FXML
	private Label userlbl;
	@FXML
	private MenuButton menuBtn_currentUser;
	
	//***** TAB - CUSTOMER *****
	@FXML
	TextField searchCustomer;
	@FXML
	Button btnAddCustomer;
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
	
	//***** TAB - ROOM *****
	@FXML
	TextField searchRoom;
	@FXML
	Button btnAddRoom;
	@FXML
	TableView<Room> roomTable;
	@FXML
	TableColumn<Customer, String> cRoomNumber;	
	@FXML
	TableColumn<Customer, String> cPrice;
	@FXML
	TableColumn<Customer, String> cStatus;
	@FXML
	TableColumn<Customer, String> cSize;
	@FXML
	TableColumn<Customer, String> cTv;
	@FXML
	TableColumn<Customer, String> cFan;	

	final ObservableList<Room> roomTableData = FXCollections.observableArrayList();	
	final ObservableList<Customer> customerTableData = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		utility = new Utility();
		customerModel = new CustomerModel();
		roomModel = new RoomModel();
		
		User current_user = (User) Main.session.get("current_user");
		String firstName = current_user.getFirstname() != null ? current_user.getFirstname() : "";
		String lastName = current_user.getLastname() != null ? current_user.getLastname().toUpperCase() : "";
		if (current_user != null) {
			userlbl.setText("Bienvenue " + firstName + " " + lastName);
			menuBtn_currentUser.setText(firstName + " " +lastName);
		}
		
		//***** CUSTOMER *****
		cIdCard.setCellValueFactory(new PropertyValueFactory<Customer, String>("id_card"));
		cDocumentType.setCellValueFactory(new PropertyValueFactory<Customer, String>("documentType"));
		cFirstname.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstname"));
		cLastname.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastname"));
		cEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		cPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
		cBirthdate.setCellValueFactory(new PropertyValueFactory<Customer, String>("birthdate"));	
		
		customerTableData.addAll(customerModel.getAllCustomer()); 
		customerTable.setItems(customerTableData);
		customerTable.setRowFactory(new Callback<TableView<Customer>, TableRow<Customer>>() {
			@Override
			public TableRow<Customer> call(TableView<Customer> tableView) {
				final TableRow<Customer> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				contextMenu.setId("contextMenu");
				final MenuItem deleteMenuItem = new MenuItem("Supprimer");
				final MenuItem editMenuItem = new MenuItem("Modifier");

				// Set context menu on row, but use a binding to make it only show for non-empty rows:
				row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				
				contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);

				deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Alert alert = Tools.showConfirmationDialog("Voulez-vous vraiment supprimer ce client?");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.CANCEL) {
							alert.hide();
						} else {
							//TODO action to delete a customer
							Customer customer = customerTable.getItems().get(row.getIndex()).getCustomer();
							System.out.println(customer.getFirstname() + " deleted");
						}
					}
				});

				editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//TODO action to edit a customer
						Customer customer = customerTable.getItems().get(row.getIndex()).getCustomer();
						System.out.println(customer.getFirstname() + " edited");
					}
				});

				return row;
			}
		});
		
		// Add listener on search field		
		searchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
		    filterCustomerList(oldValue, newValue);
		});
		
		
		//***** ROOM *****
		cRoomNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("room_number"));
		cPrice.setCellValueFactory(new PropertyValueFactory<Customer, String>("price"));
		cStatus.setCellValueFactory(new PropertyValueFactory<Customer, String>("status"));
		cSize.setCellValueFactory(new PropertyValueFactory<Customer, String>("size"));
		cTv.setCellValueFactory(new PropertyValueFactory<Customer, String>("tv"));
		cFan.setCellValueFactory(new PropertyValueFactory<Customer, String>("fan"));	
		
		roomTableData.addAll(roomModel.getAllRoom()); 
		roomTable.setItems(roomTableData);
		
		// Add listener on search field		
		searchRoom.textProperty().addListener((observable, oldValue, newValue) -> {
			filterRoomList(oldValue, newValue);
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
	
	public void filterRoomList(String oldValue, String newValue){
		ObservableList<Room> filteredList = FXCollections.observableArrayList();
		if(newValue.equals("")){
			roomTable.setItems(roomTableData);
		}
		else{
			newValue = newValue.toUpperCase();
			for(Room aRoom : roomTable.getItems()){
				String roomNumber_STRING = Integer.toString(aRoom.getRoom_number());
				if(roomNumber_STRING.contains(newValue))				{					
					filteredList.add(aRoom);
				}
			}
			roomTable.setItems(filteredList);
		}
	}
	
	@FXML
	private void addCustomer() {
		//TODO
		System.out.println("add CUSTOMER");
	}
	
	@FXML
	private void addRoom() {
		//TODO
		System.out.println("add ROOM");
	}

}
