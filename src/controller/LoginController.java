package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.objects.LoginModel;

public class LoginController implements Initializable {

	private Utility utility;
	private LoginModel loginModel;
	
	@FXML
	private Label isConnected;
	
	@FXML
	private TextField txt_login;
	
	@FXML
	private TextField txt_password;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		utility = new Utility();
		loginModel = new LoginModel();
	}	
	
	@FXML
	public void login(ActionEvent event) throws SQLException, IOException{
		if(loginModel.isLogin(txt_login.getText(), txt_password.getText())){		
			
			System.out.println("Connexion Réussie");

			//Fermeture de la fenetre de connexion
			((Node)event.getSource()).getScene().getWindow().hide();

			utility.openView(event, "Home", "Accueil");
			
		} else {
			System.out.println("login ou mot de passe incorrect");
			isConnected.setText("login ou mot de passe incorrect");
		}
	}
}
