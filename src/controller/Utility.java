package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utility {

	public void openView(Event event, String viewName, String pageTitle) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = loader.load(getClass().getResource("/view/" + viewName + ".fxml").openStream());
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(pageTitle);
		stage.setResizable(false);
		stage.show();

		/*
		 * FXMLLoader loader = new FXMLLoader(); Pane root =
		 * loader.load(getClass().getResource("/view/" + viewName +
		 * ".fxml").openStream());
		 * 
		 * Scene scene = new Scene(root);
		 * scene.getStylesheets().add(getClass().getResource(
		 * "/application/application.css").toExternalForm());
		 * 
		 * Stage primaryStage = new Stage(); primaryStage.setScene(scene);
		 * primaryStage.show();
		 */
	}

	public void openViewAsPopUp(Event event, String viewName, String pageTitle) throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/view/" + viewName + ".fxml").openStream());
		stage.setScene(new Scene(root));
		stage.setTitle(pageTitle);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node) event.getSource()).getScene().getWindow());
		stage.showAndWait();

	}

	public void closeThisView(Event event) {
		// Fermeture de la fenetre
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	
	public String passwordHasher(String pwd) throws NoSuchAlgorithmException{
		String toHash = pwd + Main.SALT;
		byte[] hash = MessageDigest.getInstance("SHA-256").digest(toHash.getBytes());
		StringBuilder stringBuilder = new StringBuilder();
	    for (byte byt : hash) {
	        String hex = Integer.toHexString(byt);
	        if (hex.length() == 1) {
	            stringBuilder.append(0);
	            stringBuilder.append(hex.charAt(hex.length() - 1));
	        } else {
	            stringBuilder.append(hex.substring(hex.length() - 2));
	        }
	    }
	    return stringBuilder.toString();
	}
}
