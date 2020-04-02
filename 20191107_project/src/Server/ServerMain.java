package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			FXMLLoader loader 
				= new FXMLLoader(getClass().getResource("server.fxml"));
			Parent root = loader.load();
			
			ServerController controller = loader.getController();
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("Chat Server");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event->controller.stopServer());
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
