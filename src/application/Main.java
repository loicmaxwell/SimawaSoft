package application;
	
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import exceptions.DatabaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.database.DBManager;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	public static final String SALT = "DTLXQfHHoVm97LPyLZEN";
	
	public static DBManager dbManager ;
	public static Map<String, Object> session = new HashMap<String, Object>();	
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws DatabaseException, SQLException {
		//test
		dbManager = new DBManager();
		Main.primaryStage = primaryStage;
		Main.primaryStage.setResizable(false);
		
		try {		
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Connexion");
			primaryStage.setResizable(false);
			primaryStage.show();			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
