package project.choice;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import project.AddrVO;
import project.AppMain;

public class ChoiceController implements Initializable {
	

	@FXML private AnchorPane login;
	@FXML private BorderPane borderPane, addrPane;
	@FXML private ImageView mainImg, locateImg;
	@FXML private Button btnChatting, btnBack;
	@FXML private Label lbName, lbPrice, lbExplanation, lbCharter,
						lbWidth, lblPrice, lbFloor, lbOption1, lbOption2, lbAddr;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		btnChatting.setOnAction(event -> handleChatting(event));
		btnBack.setOnAction(event -> handleBtnBack(event));
		int indexNum = AddrVO.index.get(0);
		Image img = new Image("file:///" + AddrVO.aptImage.get(indexNum));
		mainImg = new ImageView(img);
		mainImg.setFitWidth(414);
		mainImg.setFitHeight(150);
		borderPane.setTop(mainImg);
		lbName.setText(AddrVO.aptName.get(indexNum));
		lbCharter.setText(AddrVO.aptCharter.get(indexNum));
		lbPrice.setText(AddrVO.aptMainPrice.get(indexNum));
		lbExplanation.setText(AddrVO.aptExplanation.get(indexNum));
		lbWidth.setText(AddrVO.aptWidth.get(indexNum) + "㎡");
		lblPrice.setText(AddrVO.aptMainPrice.get(indexNum));
		lbFloor.setText(AddrVO.aptFloor.get(indexNum)+"층");
		lbOption1.setText(AddrVO.aptOption1.get(indexNum));
		lbOption2.setText(AddrVO.aptOption2.get(indexNum));
		lbAddr.setText(AddrVO.aptAddr.get(indexNum)); 
		Image lImg = new Image("file:\\C:\\Users\\user\\Desktop\\포트폴리오 참고\\집사진\\APT\\map.png");
		locateImg = new ImageView(lImg);
		locateImg.setFitWidth(414);
		locateImg.setFitHeight(150);
		addrPane.setCenter(locateImg);
		
	}
	
	
	public void handleChatting(ActionEvent event) {
		
		try {
			Parent chattingscreen = FXMLLoader.load(AppMain.class.getResource("Member_Client/Member_QA.fxml"));
			
			StackPane start = (StackPane)btnChatting.getScene().getRoot();
			start.getChildren().add(chattingscreen);
			
			chattingscreen.setTranslateX(350);	
			
			Timeline timeLine = new Timeline();
			KeyValue keyValue = new KeyValue(chattingscreen.translateXProperty(),0);		
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
