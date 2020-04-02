package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppMain extends Application {
	
	public static StackPane stackPane;

	@Override
	public void start(Stage primaryStage) throws Exception{
		
		stackPane 
			= FXMLLoader.load(AppMain.class.getResource("start/start.fxml"));
		Scene scene = new Scene(AppMain.stackPane);
		primaryStage.setScene(scene);
		primaryStage.setWidth(414);
		primaryStage.setHeight(736);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	
}

