package project.sell;


import java.io.File;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.AppMain;
import project.DBConnector;

public class SellController implements Initializable {
	
	@FXML private AnchorPane login;
	@FXML private AnchorPane main;
	@FXML private Button btnBefore, btnEnroll, btnAdd;
	@FXML private ChoiceBox<String> boxAptKind, boxKind1, boxKind2;
	@FXML private TextField txtName, txtPrice, txtAddr1, txtAddr2, txtWidth, 
							txtFloor, txtOption1, txtOption2, txtExplanation,
							txtRoom, txtBath, txtFile;
	@FXML private HBox paneImg1, paneImg2;
	Stage primaryStage;
	private int count=0;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		btnBefore.setOnAction(event->handleBtnBack(event));
		boxAptKind.getItems().add("아파트");
		boxAptKind.getItems().add("원룸");
		boxAptKind.getItems().add("오피스텔");
		boxKind1.getItems().add("매매");
		boxKind1.getItems().add("전세");
		boxKind1.getItems().add("월세");
		boxKind2.getItems().add("복도식");
		boxKind2.getItems().add("계단식");
		btnEnroll.setOnAction(event->handleBtnEnroll(event));
		btnAdd.setOnAction(event->handleBtnAdd(event));
	}

	public void handleBtnBack(ActionEvent event) {
		try {
			Parent main = FXMLLoader.load(AppMain.class.getResource("main/main.fxml"));
			
			
			StackPane start = (StackPane)btnBefore.getScene().getRoot();
			start.getChildren().add(main);
			
			main.setTranslateX(350);	
			
			Timeline timeLine = new Timeline();
			KeyValue keyValue = new KeyValue(main.translateXProperty(),0);		
			KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
			timeLine.getKeyFrames().add(keyFrame);
			timeLine.play();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleBtnEnroll(ActionEvent event) {
		
		try {
			conn = DBConnector.getConnection();
			String sql = "";
			int result = 0;
			sql = "INSERT INTO apt_info (apt_kind1, apt_kind2, apt_name, apt_addr, apt_charter, apt_price, apt_main_price, apt_room, apt_bath, apt_total_floor, apt_floor, apt_width, apt_explanation, apt_option1, apt_option2, apt_image_path)"
				 +"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boxAptKind.getValue());
			pstmt.setString(2, boxKind2.getValue());
			pstmt.setString(3, txtName.getText());
			pstmt.setString(4, txtAddr1.getText());
			pstmt.setString(5, boxKind1.getValue());
			pstmt.setString(6, "");
			pstmt.setString(7, txtPrice.getText());
			pstmt.setString(8, txtRoom.getText());
			pstmt.setString(9, txtBath.getText());
			pstmt.setString(10, "");
			pstmt.setString(11, txtFloor.getText());
			pstmt.setString(12, txtWidth.getText());
			pstmt.setString(13, txtExplanation.getText());
			pstmt.setString(14, txtOption1.getText());
			pstmt.setString(15, txtOption2.getText());
			pstmt.setString(16, txtFile.getText());
			
			result = pstmt.executeUpdate();
			if(result == 0) {
				System.out.println("오류오류");
			}else {
				try {
					Parent main = FXMLLoader.load(AppMain.class.getResource("main/main.fxml"));
					
					StackPane start = (StackPane)btnEnroll.getScene().getRoot();
					start.getChildren().add(main);
					
					main.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(main.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void handleBtnAdd(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters().addAll(
				
				new ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg","*.gif")
			);
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		
		
		if(selectedFile != null) {
			
			System.out.println(selectedFile.getPath());
			Image image = new Image("file:///" + selectedFile.getPath());
			ImageView imageView= new ImageView(image);
			imageView.setFitWidth(100);
			imageView.setFitHeight(100);
			count++;
			System.out.println(count);
			if(count <= 3) {	
				paneImg1.getChildren().add(imageView);
				txtFile.setText(selectedFile.getPath());
			}else if(count <= 6 ) {
				paneImg2.getChildren().add(imageView);
				txtFile.setText(selectedFile.getPath());
			}else if(count == 7 ) {
				btnAdd.setDisable(true);
				System.out.println("더 이상 넣을 수 없습니다.");
			}
			
		}
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}