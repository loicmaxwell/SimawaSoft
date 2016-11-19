package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import beans.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class HomeController implements Initializable {
	public Utility utility;

	@FXML
	private Label userlbl;
	@FXML
	private MenuButton menuBtn_currentUser;

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
	
	public void selectCustomer(ActionEvent event) throws IOException {
		utility.openViewAsPopUp(event, "SelectCustomer", "Selectionner le Client");
	}

}
