package project.login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import project.login.Root_Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import project.AppMain;
import project.DBConnector;


public class LoginController implements Initializable {
	
	@FXML private AnchorPane login;
	@FXML private AnchorPane main;
	@FXML private TextField txtId;
	@FXML private PasswordField pfPw;
	@FXML private Button btnLook, btnLogin, btnJoin;
	Root_Controller rc=null;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	private Stage primaryStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		btnLogin.setOnAction(event->handleLoginAction(event));
		
		txtId.setOnKeyPressed(key->{
	         // 만약 엔터키를 눌렀을때 send버튼을 실행
	         if(key.getCode().equals(KeyCode.ENTER)) {
	        	 pfPw.requestFocus();
	         }
	      });
		pfPw.setOnKeyPressed(key->{
	         // 만약 엔터키를 눌렀을때 send버튼을 실행
	         if(key.getCode().equals(KeyCode.ENTER)) {
	            btnLogin.fire();
	         }
	      });
		
		btnJoin.setOnAction(event ->handleJoinAction(event));
		btnLook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent look = FXMLLoader.load(AppMain.class.getResource("look/look.fxml"));
					
					StackPane start = (StackPane)btnLook.getScene().getRoot();
					start.getChildren().add(look);
					
					look.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(look.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
							
			}

		});
	}

	

	public void handleLoginAction(ActionEvent event) {
		if(txtId.getText().equals("")) {
			System.out.println("아이디 입력해");
			txtId.requestFocus();
		}else if(pfPw.getText().equals("")){
			System.out.println("비밀번호 입력해");
			pfPw.requestFocus();
		}else {
			try {
				System.out.println("아이디 : " + txtId.getText());
				System.out.println("비밀번호 : " + pfPw.getText());
				conn = DBConnector.getConnection();
				System.out.println("Database 연결 완료 : " + conn.toString());
				String sql = "";
				sql = "select id, pw from member where id=? and pw=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, txtId.getText());
				pstmt.setString(2, pfPw.getText());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					String rt=null;
					rt=rs.getString(1);
					if (rt.equals("root")) {
						Stage addView = new Stage(StageStyle.UTILITY);
						addView.setTitle("상담사 연결");
						
						Parent root = null;
						FXMLLoader loader=null;
						
						try {
							loader = new FXMLLoader(getClass().getResource("root_QA.fxml"));
							// client.fxml에 있는 컨트롤러를 끌어쓰기위해 선언
							root = loader.load();
						} catch (IOException e1) {
							System.out.println(e1.getMessage());
						}
						rc = loader.getController();
						addView.setOnCloseRequest(event1->{
							 try {
								rc.socket.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							 Platform.exit();
						});
						
						// client.fxml에 있는 컨트롤러를 끌어쓰기위해 선언
						
						Scene scene = new Scene(root);
						addView.setScene(scene);
						addView.setResizable(false);
						addView.show();
						try {
							Parent main = FXMLLoader.load(AppMain.class.getResource("main/main.fxml"));
							
							StackPane start = (StackPane)btnLogin.getScene().getRoot();
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
						
						
					}else if(!(rt.equals("root"))){
						try {
							Parent main = FXMLLoader.load(AppMain.class.getResource("main/main.fxml"));
							
							StackPane start = (StackPane)btnLogin.getScene().getRoot();
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
				}else if(rs.next()==false){
					popup();
					System.out.println("아이디나 비밀번호가 틀립니다.");
				}
					
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}		
		}
	}
	
	public void handleJoinAction(ActionEvent event) {
		try {
			Parent join = FXMLLoader.load(AppMain.class.getResource("join/join.fxml"));
			
			StackPane start = (StackPane)btnJoin.getScene().getRoot();
			start.getChildren().add(join);
			
			join.setTranslateX(350);	
			
			Timeline timeLine = new Timeline();
			KeyValue keyValue = new KeyValue(join.translateXProperty(),0);		
			KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
			timeLine.getKeyFrames().add(keyFrame);
			timeLine.play();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void popup() {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("경고창");
		Parent root = null;
		try {
			root = FXMLLoader.load(AppMain.class.getResource("login/loginPopup.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		Label label = (Label)root.lookup("#txtMsg");
		Button close = (Button)root.lookup("#btnClose");
		
		label.setText("아이디나 비밀번호가 틀립니다.");
		
		close.setOnAction(event->{
			dialog.close();
		});
		
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
}
