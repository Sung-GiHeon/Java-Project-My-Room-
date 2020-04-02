package project.join;

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
import project.AppMain;
import project.DBConnector;

public class JoinController implements Initializable {
	

	@FXML private AnchorPane login;
	@FXML private TextField idTxt, pwTxt, checkPwTxt, nameTxt, phoneTxt, addrTxt1, addrTxt2, emailTxt;
	@FXML private Button btnCheck,btnJoin, btnCancel;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	Parent root = null;
	
	private Stage primaryStage;
	
	// id중복 확인을 했는지 체크.
	int clickIdCheck = 1;
	// 중복된 아이디가 있는지 체크.
	boolean checkId = false;
	// 중복된 id가 없으면 입력한id값을 저장
	String[] textId = new String[1];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		btnCheck.setOnAction(event -> checkId());
		btnJoin.setOnAction(event -> handleJoinAction(event));
		btnCancel.setOnAction(event -> hadleCancelAction(event));
	}

	// 회원가입
	public void handleJoinAction(ActionEvent event) {
		if(nameTxt.getText().equals("")) {
			nameTxt.requestFocus();
		}else if(idTxt.getText().equals("")) {
			idTxt.requestFocus();
		}else if(pwTxt.getText().equals("")){
			pwTxt.requestFocus();
		}else if(clickIdCheck==2){
			try {		
				// 중복화인 한 후 id값 변경 체크
				if(textId[0].equals(idTxt.getText())) {	
					// 비밀번호 체크
					if(!pwTxt.getText().equals(checkPwTxt.getText())) {
						pwCheckPopup();
						pwTxt.requestFocus();
					}else if(checkId==true) {
						// 중복된 id가 없을 시 가입
						int result = 0;
						conn = DBConnector.getConnection();
						System.out.println("Insert Database 연결 완료 : " + conn.toString());
						String sql = "insert into member(id,pw,uName) values(?,?,?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, idTxt.getText());
						System.out.println(idTxt.getText());
						pstmt.setString(2, pwTxt.getText());
						System.out.println(pwTxt.getText());
						pstmt.setString(3, nameTxt.getText());
						System.out.println(nameTxt.getText());
						result = pstmt.executeUpdate();
						
						try {
							Parent login = FXMLLoader.load(AppMain.class.getResource("login/login.fxml"));
							
							StackPane start = (StackPane)btnJoin.getScene().getRoot();
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
						
						// **가입완료 경고창이 뜨고 확인 버튼 눌렀을 때 로그인 창으로 전환으로 바꾸기.**
						hadleCancelAction(event);
					}else {
						// 중복된 아이디가 있으면 id입력창에 focus
						idCheckPopup();
						idTxt.requestFocus();
					}
				}else {
					// 중복화인 한 후 id값 변경 시 다시 중복확인 하라고 경고.
					idCheckPopup();
					idTxt.requestFocus();
					clickIdCheck=1;
				}
					
			} catch (SQLException e) {
				System.out.println("INSERT 오류 : " + e.getMessage());
			} finally {
				DBConnector.close(pstmt);
			}
			// 중복확인 안했을 때
		}else if(clickIdCheck==1) {
			idCheckPopup();
		}
	
	}
	
	// 중복확인 버튼 클릭 시 중복된 아이디가 있는지 체크.
	public boolean checkId() {
		try {
			if(idTxt.getText().equals("")) {
				idTxt.requestFocus();
			}else {
				conn = DBConnector.getConnection();
				System.out.println("CHECK DB 연결 : " + conn.toString());
				String sql = "select * from member where id = ?";
				pstmt = conn.prepareStatement(sql);
				String id = idTxt.getText();
				pstmt.setString(1, id);
				System.out.println();
				rs = pstmt.executeQuery();
				if(rs.next()) {
					String chId = rs.getString(1);
					System.out.println(chId);	
					if(id.equals(chId)) {
						impossiblePopup();
						idTxt.requestFocus();
						checkId=false;
					}
				}else {
					possiblePopup();
					textId[0] = idTxt.getText();
					clickIdCheck = 2;
					checkId=true;					
				}	
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return checkId;
	}
	
	
	public void hadleCancelAction(ActionEvent event) {
		
		try {
			Parent login = FXMLLoader.load(AppMain.class.getResource("login/login.fxml"));
			
			StackPane start = (StackPane)btnCancel.getScene().getRoot();
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
	
	public void pwCheckPopup() {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("경고창");
		Parent root = null;
		try {
			root = FXMLLoader.load(AppMain.class.getResource("join/JoinPopup.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		Label label = (Label)root.lookup("#txtMsg");
		Button close = (Button)root.lookup("#btnClose");
		
		label.setText("비밀번호가 일치하지 않습니다.");
		close.setOnAction(event->{
			dialog.close();
		});
		
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();

	}
	
	
	public void impossiblePopup() {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("경고창");
		Parent root = null;
		try {
			root = FXMLLoader.load(AppMain.class.getResource("join/JoinPopup.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		Label label = (Label)root.lookup("#txtMsg");
		Button close = (Button)root.lookup("#btnClose");
		
		label.setText("사용중인 아이디입니다.");
		close.setOnAction(event->{
			dialog.close();
		});
		
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();

	}

	
	
	public void possiblePopup() {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("경고창");
		Parent root = null;
		try {
			root = FXMLLoader.load(AppMain.class.getResource("join/JoinPopup.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		Label label = (Label)root.lookup("#txtMsg");
		Button close = (Button)root.lookup("#btnClose");
		
		label.setText("사용 가능한 아이디입니다.");
		close.setOnAction(event->{
			dialog.close();
		});
		
		Scene scene = new Scene(root);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();

	}
	
	public void idCheckPopup() {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("경고창");
		Parent root = null;
		try {
			root = FXMLLoader.load(AppMain.class.getResource("join/JoinPopup.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		Label label = (Label)root.lookup("#txtMsg");
		Button close = (Button)root.lookup("#btnClose");
		
		label.setText("아이디를 확인해 주세요.");
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