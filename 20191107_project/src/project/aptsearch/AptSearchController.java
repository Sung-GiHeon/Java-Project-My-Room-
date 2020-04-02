package project.aptsearch;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AptSearchController implements Initializable {
	
	@FXML private AnchorPane main;
	@FXML private StackPane aptlist;
	@FXML private Button btnBack, btnSearch;

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		btnBack.setOnAction(event-> handleBtnBack(event));
		btnSearch.setOnAction(event-> handleBtnSearch(event));
	}

	public void handleBtnSearch(ActionEvent event) {
		try {
			Parent aptlist = FXMLLoader.load(AppMain.class.getResource("aptlist/aptlist.fxml"));
			
			StackPane start = (StackPane)btnSearch.getScene().getRoot();
			start.getChildren().add(aptlist);
			
			aptlist.setTranslateX(350);	
			
			Timeline timeLine = new Timeline();
			KeyValue keyValue = new KeyValue(aptlist.translateXProperty(),0);		
			KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
			timeLine.getKeyFrames().add(keyFrame);
			timeLine.play();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleBtnBack(ActionEvent event) {
		try {
			Parent aptlist = FXMLLoader.load(AppMain.class.getResource("aptlist/aptlist.fxml"));
			
			StackPane start = (StackPane)btnBack.getScene().getRoot();
			start.getChildren().add(aptlist);
			
			aptlist.setTranslateX(350);	
			
			Timeline timeLine = new Timeline();
			KeyValue keyValue = new KeyValue(aptlist.translateXProperty(),0);		
			KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
			timeLine.getKeyFrames().add(keyFrame);
			timeLine.play();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

		
		
		