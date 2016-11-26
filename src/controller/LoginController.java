package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import beans.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.objects.UserModel;

public class LoginController implements Initializable {

	private Utility utility;
	private UserModel loginModel;
	
	@FXML
	private Label isConnected;
	
	@FXML
	private TextField txt_login;
	
	@FXML
	private TextField txt_password;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		utility = new Utility();
		loginModel = new UserModel();
		
		User currentUser = (User) Main.session.get("current_user");
		if(currentUser != null){
			txt_login.setText(currentUser.getLogin());
		}
	}	
	
	@FXML
	public void login(ActionEvent event) throws SQLException, IOException{
		if(loginModel.isLogin(txt_login.getText(), txt_password.getText())){		
			//Fermeture de la fenetre de connexion
			((Node)event.getSource()).getScene().getWindow().hide();
			utility.openView(event, "Home", "Accueil");
		} else {
			if (txt_login.getText().equals("") || txt_password.getText().equals("")) {
				isConnected.setText("Les champs Login et mot de passe sont obligatoires.");
			}else {
				isConnected.setText("La combinaison login et mot de passe est incorrecte");
			}
		}
	}

}
