package project.main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import project.AddrVO;
import project.AppMain;
import project.DBConnector;

public class MainController implements Initializable {
	
	@FXML private AnchorPane login;
	@FXML private AnchorPane main;
	@FXML private TextField txtSearch;
	@FXML private Button btnApt, btnVilla,  btnHouse, btnOffice, btnSell ;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		btnApt.setOnAction(event -> handleBtnAPT(event));
		
		btnOffice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent ofclist = FXMLLoader.load(AppMain.class.getResource("ofclist/ofclist.fxml"));
					
					StackPane start = (StackPane)btnOffice.getScene().getRoot();
					start.getChildren().add(ofclist);
					
					ofclist.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(ofclist.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}			
		
			}
			
		});
		
		btnVilla.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent villalist = FXMLLoader.load(AppMain.class.getResource("villalist/villalist.fxml"));
					
					StackPane start = (StackPane)btnVilla.getScene().getRoot();
					start.getChildren().add(villalist);
					
					villalist.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(villalist.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}			
				
			}
			
		});
		
		btnHouse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent Houselist = FXMLLoader.load(AppMain.class.getResource("houselist/houselist.fxml"));
					
					StackPane start = (StackPane)btnHouse.getScene().getRoot();
					start.getChildren().add(Houselist);
					
					Houselist.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(Houselist.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}			
		
			}
			
		});
		
		
		btnSell.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent sell = FXMLLoader.load(AppMain.class.getResource("sell/sell.fxml"));
					
					StackPane start = (StackPane)btnSell.getScene().getRoot();
					start.getChildren().add(sell);
					
					sell.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(sell.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
							
			}
			
		});
		
	}

	public void openPage() {
		try {
			Parent aptlist = FXMLLoader.load(AppMain.class.getResource("aptlist/aptlist.fxml"));
			
			StackPane start = (StackPane)btnApt.getScene().getRoot();
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
	
	public void handleBtnAPT(ActionEvent event) {
		try {
			
			conn = DBConnector.getConnection();
			String sql = "";
			if(txtSearch.getText().equals("")) {
				sql = "SELECT * FROM APT_info WHERE apt_kind1=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, btnApt.getText());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					while(true) {
						String apt_kind1 = rs.getString(2);
						String apt_kind2 = rs.getString(3);
						String apt_name = rs.getString(4);
						String apt_addr = rs.getString(5);
						String apt_charter = rs.getString(6);
						String apt_price = rs.getString(7);
						String apt_main_price = rs.getString(8);
						String apt_room = rs.getString(9);
						String apt_bath = rs.getString(10);
						String apt_total_floor = rs.getString(11);
						String apt_floor = rs.getString(12);
						String apt_width = rs.getString(13);
						String apt_explanation = rs.getString(14);
						String apt_option1 = rs.getString(15);
						String apt_option2 = rs.getString(16);
						String apt_image_path = rs.getString(17);
						
						AddrVO.aptKind1.add(apt_kind1);
						AddrVO.aptKind2.add(apt_kind2);
						AddrVO.aptName.add(apt_name);
						AddrVO.aptAddr.add(apt_addr);
						AddrVO.aptCharter.add(apt_charter);
						AddrVO.aptPrice.add(apt_price);
						AddrVO.aptMainPrice.add(apt_main_price);
						AddrVO.room.add(apt_room);
						AddrVO.bath.add(apt_bath);
						AddrVO.aptTotalFloor.add(apt_total_floor);
						AddrVO.aptFloor.add(apt_floor);
						AddrVO.aptWidth.add(apt_width);
						AddrVO.aptExplanation.add(apt_explanation);
						AddrVO.aptOption1.add(apt_option1);
						AddrVO.aptOption2.add(apt_option2);
						AddrVO.aptImage.add(apt_image_path);
						if(!rs.next())break;
					}
					openPage();
				}else if(!rs.next()){
						System.out.println("결과값 없음");
				}
				
			}else if(!txtSearch.getText().equals("")){
				String address = txtSearch.getText();
				System.out.println("address : " + address);
				sql = "SELECT * FROM APT_info WHERE apt_kind1=? and apt_addr LIKE ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, btnApt.getText());
				pstmt.setString(2, "%" + txtSearch.getText() + "%");
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					while(true) {
						String apt_kind1 = rs.getString(2);
						String apt_kind2 = rs.getString(3);
						String apt_name = rs.getString(4);
						String apt_addr = rs.getString(5);
						String apt_charter = rs.getString(6);
						String apt_price = rs.getString(7);
						String apt_main_price = rs.getString(8);
						String apt_room = rs.getString(9);
						String apt_bath = rs.getString(10);
						String apt_total_floor = rs.getString(11);
						String apt_floor = rs.getString(12);
						String apt_width = rs.getString(13);
						String apt_explanation = rs.getString(14);
						String apt_option1 = rs.getString(15);
						String apt_option2 = rs.getString(16);
						String apt_image_path = rs.getString(17);
						
						AddrVO.aptKind1.add(apt_kind1);
						AddrVO.aptKind2.add(apt_kind2);
						AddrVO.aptName.add(apt_name);
						AddrVO.aptAddr.add(apt_addr);
						AddrVO.aptCharter.add(apt_charter);
						AddrVO.aptPrice.add(apt_price);
						AddrVO.aptMainPrice.add(apt_main_price);
						AddrVO.room.add(apt_room);
						AddrVO.bath.add(apt_bath);
						AddrVO.aptTotalFloor.add(apt_total_floor);
						AddrVO.aptFloor.add(apt_floor);
						AddrVO.aptWidth.add(apt_width);
						AddrVO.aptExplanation.add(apt_explanation);
						AddrVO.aptOption1.add(apt_option1);
						AddrVO.aptOption2.add(apt_option2);
						AddrVO.aptImage.add(apt_image_path);
						if(!rs.next())break;
					}
					openPage();
				}else if(!rs.next()){
					System.out.println("결과값 없음");
				}
		
			}
		} catch (SQLException e) {
			System.out.println("SELECT 오류 " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	

}
