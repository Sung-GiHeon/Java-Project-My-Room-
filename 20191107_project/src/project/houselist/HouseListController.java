package project.houselist;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class HouseListController implements Initializable{
    
	@FXML private AnchorPane login;
	@FXML private AnchorPane main;
	@FXML private Button btnBack, btnNext, btnSearch, btnHome;
	@FXML private StackPane listStackPane; 
	
	private int currentPage = 1;
	private int totalPage = 1;
	private int count = 5;
	
	ListView<BorderPane> listView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
//		btnBack.setOnAction(event -> handleBtnBack(event));
//		btnNext.setOnAction(event -> handleBtnNext(event));
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					Parent ofcsearch = FXMLLoader.load(AppMain.class.getResource("ofcsearch/ofcsearch.fxml"));
					
					StackPane start = (StackPane)btnSearch.getScene().getRoot();
					start.getChildren().add(ofcsearch);
					
					ofcsearch.setTranslateX(350);	
					
					Timeline timeLine = new Timeline();
					KeyValue keyValue = new KeyValue(ofcsearch.translateXProperty(),0);		
					KeyFrame keyFrame = new KeyFrame(Duration.millis(100),keyValue);	
					timeLine.getKeyFrames().add(keyFrame);
					timeLine.play();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
							
			}
			
		});
		btnHome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
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
		listView = getList(1);
		listStackPane.getChildren().add(listView);
	}
	
	public ListView<BorderPane> getList(int page) {
		ListView<BorderPane> listView = new ListView<>();
		
		for(int j=1; j<=5; j++) {
			String number = j+"";
			if(j == 5) {
				currentPage++;
				totalPage++;
			}
			System.out.println(j);
			try {
				BorderPane borderPane = new BorderPane();
				Image image = new Image(getClass().getResource("../imagesHouse/House/"+number+".jpg").toString());
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(150);
				imageView.setFitHeight(123);
				borderPane.setLeft(imageView);
				
				AnchorPane anchorPane = new AnchorPane();
				
				Label Lapt = new Label("주택 이름");
				Lapt.setFont(new Font(12));
				Lapt.setLayoutX(10);
				Lapt.setLayoutY(10);
				anchorPane.getChildren().add(Lapt);
				
				Label Lcharter = new Label("전세");
				Lcharter.setStyle("-fx-text-fill: red;");
				Lcharter.setFont(new Font(15));
				Lcharter.setLayoutX(10);
				Lcharter.setLayoutY(31);
				anchorPane.getChildren().add(Lcharter);
				
				Label Lprice = new Label("1억 1000");
				Lprice.setStyle("-fx-text-fill: red;");
				Lprice.setFont(new Font(15));
				Lprice.setLayoutX(45);
				Lprice.setLayoutY(31);
				anchorPane.getChildren().add(Lprice);
				
				Label Lsection = new Label("주택" + " | ");
				Lsection.setFont(new Font(12));
				Lsection.setLayoutX(10);
				Lsection.setLayoutY(58);
				anchorPane.getChildren().add(Lsection);
				
				Label Lfloor = new Label("중층" + " | ");
				Lfloor.setFont(new Font(12));
				Lfloor.setLayoutX(58);
				Lfloor.setLayoutY(58);
				anchorPane.getChildren().add(Lfloor);
				
				Label Lwidth = new Label("36.64m");
				Lwidth.setFont(new Font(12));
				Lwidth.setLayoutX(95);
				Lwidth.setLayoutY(58);
				anchorPane.getChildren().add(Lwidth);
				
				Label Lexplanation = new Label("추가설명...");
				Lexplanation.setFont(new Font(12));
				Lexplanation.setLayoutX(10);
				Lexplanation.setLayoutY(79);
				anchorPane.getChildren().add(Lexplanation);
				
				Label Loption1 = new Label("주차");
				Loption1.setStyle("-fx-background-color: lightgray;");
				Loption1.setFont(new Font(12));
				Loption1.setLayoutX(10);
				Loption1.setLayoutY(101);
				anchorPane.getChildren().add(Loption1);
				
				Label Loption2 = new Label("반려동물");
				Loption2.setStyle("-fx-background-color: lightgray;");
				Loption2.setFont(new Font(12));
				Loption2.setLayoutX(38);
				Loption2.setLayoutY(101);
				anchorPane.getChildren().add(Loption2);
				
				borderPane.setCenter(anchorPane);
				
				listView.getItems().add(borderPane);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return listView;
	}
	

	public void handleBtnNext(ActionEvent event) {
		if(currentPage < totalPage) {
			currentPage++;
			ListView<BorderPane> listView = getList(currentPage);
			listStackPane.getChildren().add(0,listView);
	
		}
	}

	public void handleBtnBack(ActionEvent event) {
		if(currentPage > 1) {
			currentPage--;
			ListView<BorderPane> listView = getList(currentPage);
			listStackPane.getChildren().add(0,listView);
			
		}

	}
	
	private void showButton() {
		if(currentPage == 1) {
			btnBack.setVisible(false);
		}else {
			btnBack.setVisible(true);
		}
		
		if(currentPage == totalPage) {
			btnNext.setVisible(false);
		}else {
			btnNext.setVisible(true);
		}
	}
	
}
