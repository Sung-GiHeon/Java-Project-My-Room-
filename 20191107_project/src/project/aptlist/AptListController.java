package project.aptlist;

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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import project.AddrVO;
import project.AppMain;
import project.DBConnector;

public class AptListController implements Initializable{
    
	@FXML private AnchorPane login;
	@FXML private AnchorPane main;
	@FXML private Button btnSearch, btnHome;
	@FXML private StackPane listStackPane; 
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
//	private Stage primaryStage;
//	private int currentPage = 0;
//	private int totalPage = AddrVO.aptName.size();
	
	String number;
	String[] aptKind1 = new String[AddrVO.aptName.size()];
	String[] aptKind2 = new String[AddrVO.aptName.size()];
	String[] aptName = new String[AddrVO.aptName.size()];
	String[] aptCharter = new String[AddrVO.aptName.size()];
	String[] aptPrice = new String[AddrVO.aptName.size()];
	String[] aptFloor = new String[AddrVO.aptName.size()];
	String[] aptWidth = new String[AddrVO.aptName.size()];
	String[] aptExplanation = new String[AddrVO.aptName.size()];
	String[] aptOption1 = new String[AddrVO.aptName.size()];
	String[] images = new String[AddrVO.aptName.size()];
	
	ListView<BorderPane> listView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		btnHome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					initList();
					Parent main = FXMLLoader.load(AppMain.class.getResource("main/main.fxml"));
					
					StackPane start = (StackPane)btnHome.getScene().getRoot();
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
			
		});
		
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent aptsearch = FXMLLoader.load(AppMain.class.getResource("aptsearch/aptsearch.fxml"));
					
					StackPane start = (StackPane)btnSearch.getScene().getRoot();
					start.getChildren().add(aptsearch);
					
					aptsearch.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(aptsearch.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
							
			}
			
		});		
		
//		if(listView.getItems().isEmpty()==false) {
//			listView.getItems().clear();
//			System.out.println("init : " + listView.getItems().size());
//		}else if(listView.getItems().isEmpty()==true){
//			
//		}
		listView = getList(1);
		listStackPane.getChildren().add(listView);
	}
	
	public ListView<BorderPane> getList(int page) {
		ListView<BorderPane> listView = new ListView<>();
		for(int i = 0 ; i<AddrVO.aptName.size(); i++) {
			String kind1 = AddrVO.aptKind1.get(i);
			String kind2 = AddrVO.aptKind2.get(i);
			String name = AddrVO.aptName.get(i);
			String charter = AddrVO.aptCharter.get(i);
			String price = AddrVO.aptMainPrice.get(i);
			String floor = AddrVO.aptFloor.get(i);
			String width = AddrVO.aptWidth.get(i);
			String explanation = AddrVO.aptExplanation.get(i);
			String option1 = AddrVO.aptOption1.get(i);
			String image = AddrVO.aptImage.get(i);
			
			aptKind1[i] = kind1;
			aptKind2[i] = kind2;
			aptName[i] = name;
			aptCharter[i] = charter; 
			aptPrice[i] = price;
			aptFloor[i] = floor;
			aptWidth[i] = width;
			aptExplanation[i] = explanation;
			aptOption1[i] = option1;
			images[i] = image;
			
		}
		
		for(int i=0; i<AddrVO.aptName.size(); i++) {				
			System.out.println("i"+i);
			try {
				BorderPane borderPane = new BorderPane();
				System.out.println(images[i]);
				Image image = new Image("file:///" + images[i]);
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(150);
				imageView.setFitHeight(123);
				borderPane.setLeft(imageView);
				
				AnchorPane anchorPane = new AnchorPane();
				
				Label Lapt = new Label(aptName[i]);
				Lapt.setFont(new Font(12));
				Lapt.setLayoutX(10);
				Lapt.setLayoutY(10);
				anchorPane.getChildren().add(Lapt);
				System.out.println("Lapt : " + Lapt.getText());
				Label Lcharter = new Label(aptCharter[i]);
				Lcharter.setStyle("-fx-text-fill: red;");
				Lcharter.setFont(new Font(15));
				Lcharter.setLayoutX(10);
				Lcharter.setLayoutY(31);
				anchorPane.getChildren().add(Lcharter);
				
				Label Lprice = new Label(aptPrice[i]);
				Lprice.setStyle("-fx-text-fill: red;");
				Lprice.setFont(new Font(15));
				Lprice.setLayoutX(45);
				Lprice.setLayoutY(31);
				anchorPane.getChildren().add(Lprice);
				
				Label Lsection = new Label(aptKind2[i] + " | ");
				Lsection.setFont(new Font(12));
				Lsection.setLayoutX(10);
				Lsection.setLayoutY(58);
				anchorPane.getChildren().add(Lsection);
				
				Label Lfloor = new Label(aptFloor[i] + "층 | ");
				Lfloor.setFont(new Font(12));
				Lfloor.setLayoutX(58);
				Lfloor.setLayoutY(58);
				anchorPane.getChildren().add(Lfloor);
				
				Label Lwidth = new Label(aptWidth[i]+ "㎡");
				
				Lwidth.setFont(new Font(12));
				Lwidth.setLayoutX(95);
				Lwidth.setLayoutY(58);
				anchorPane.getChildren().add(Lwidth);
				
				Label Lexplanation = new Label(aptExplanation[i]);
				Lexplanation.setFont(new Font(12));
				Lexplanation.setLayoutX(10);
				Lexplanation.setLayoutY(79);
				anchorPane.getChildren().add(Lexplanation);
				
				Label Loption1 = new Label(aptOption1[i]);
				Loption1.setStyle("-fx-background-color: lightgray;");
				Loption1.setFont(new Font(12));
				Loption1.setLayoutX(10);
				Loption1.setLayoutY(101);
				anchorPane.getChildren().add(Loption1);
				
				Label Loption2 = new Label("세탁기");
				Loption2.setStyle("-fx-background-color: lightgray;");
				Loption2.setFont(new Font(12));
				Loption2.setLayoutX(50);
				Loption2.setLayoutY(101);
				anchorPane.getChildren().add(Loption2);
				
				borderPane.setCenter(anchorPane);
				
				listView.getItems().add(borderPane);
				
				listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						handleDetail();
						try {
							Stage stage = new Stage();
							Parent choice = FXMLLoader.load(AppMain.class.getResource("choice/choice.fxml")); 
							Scene scene = new Scene(choice);
							stage.setScene(scene);
							stage.setWidth(414);
							stage.setHeight(736);
							stage.setResizable(false);
							stage.show();
							Stage main = (Stage) listView.getScene().getWindow();
					        main.close();
						} catch (IOException e) {
							System.out.println("오류 : " + e.getMessage());
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return listView;
	}
	
	public void handleDetail() {
		try {
			int index = listView.getSelectionModel().getSelectedIndex();
			System.out.println(aptName[index]);	
			conn = DBConnector.getConnection();
			String sql = "";
			
			sql = "SELECT * FROM APT_info WHERE apt_name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aptName[index]);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				AddrVO.index.put(0, index);
			}
		} catch (SQLException e) {
			System.out.println("Detail Select 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void initList() {
		AddrVO.index.clear();
		AddrVO.aptKind1.clear();
		AddrVO.aptKind2.clear();
		AddrVO.aptKind3.clear();
		AddrVO.aptName.clear();
		AddrVO.aptAddr.clear();
		AddrVO.aptCharter.clear();
		AddrVO.aptPrice.clear();
		AddrVO.aptMainPrice.clear();
		AddrVO.room.clear();
		AddrVO.bath.clear();
		AddrVO.aptTotalFloor.clear();
		AddrVO.aptFloor.clear();
		AddrVO.aptWidth.clear();
		AddrVO.aptExplanation.clear();
		AddrVO.aptOption1.clear();
		AddrVO.aptOption2.clear();
		AddrVO.aptImage.clear();
	}
	
	
}
