package project.start;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import project.AppMain;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class StartController implements Initializable {
	
	@FXML private Button btnStart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		btnStart.setOnAction(event ->handleBtnStart(event));
	}

	public void handleBtnStart(ActionEvent event) {
		try {
			Parent login = FXMLLoader.load(AppMain.class.getResource("login/login.fxml"));
			
			StackPane start = (StackPane)btnStart.getScene().getRoot();
			start.getChildren().add(login);
			
			login.setTranslateX(350);	
			
			Timeline timeLine = new Timeline();
			KeyValue keyValue = new KeyValue(login.translateXProperty(),0);		
			KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
			timeLine.getKeyFrames().add(keyFrame);
			timeLine.play();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
