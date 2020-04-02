package project.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animation {
	
	/**
	 * @param 
	 * start 0.0 ~ width 
	 * end 0.0 ~ width
	 */
	public static void slideX(Node node , double start, double end) {
		node.setTranslateX(start);
		Timeline timeLine = new Timeline();
		KeyValue keyValue = new KeyValue(node.translateXProperty(),end);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300),keyValue); 
		timeLine.getKeyFrames().add(keyFrame);
		timeLine.play();		
	}
	
	public static void slideY(Node node , double start, double end) {
		node.setTranslateY(start);
		Timeline timeLine = new Timeline();
		KeyValue keyValue = new KeyValue(node.translateYProperty(),end);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300),keyValue); 
		timeLine.getKeyFrames().add(keyFrame);
		timeLine.play();		
	}
	
	public static void slideX(Node node , double start, double end, EventHandler<ActionEvent> onFinished) {
		node.setTranslateX(start);
		Timeline timeLine = new Timeline();
		KeyValue keyValue = new KeyValue(node.translateXProperty(),end);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300),onFinished,keyValue); 
		timeLine.getKeyFrames().add(keyFrame);
		timeLine.play();		
	}
	
	public static void slideY(Node node , double start, double end, EventHandler<ActionEvent> onFinished) {
		node.setTranslateY(start);
		Timeline timeLine = new Timeline();
		KeyValue keyValue = new KeyValue(node.translateYProperty(),end);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300),onFinished,keyValue); 
		timeLine.getKeyFrames().add(keyFrame);
		timeLine.play();		
	}
	
	/**
	 * opacity : 투명도
	 * node : 대상
	 * @param
	 * start 0.0 ~ 1.0
	 * end 0.0 ~ 1.0
	 */
	public static void fade(Node node, double start, double end) {
		node.setOpacity(start);
		Timeline timeLine = new Timeline();
		KeyValue keyValue = new KeyValue(node.opacityProperty(),end);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300),keyValue);
		timeLine.getKeyFrames().add(keyFrame);
		timeLine.play();
	}
	
	public static void fade(Node node, double start, double end, EventHandler<ActionEvent> onFinished) {
		node.setOpacity(start);
		Timeline timeLine = new Timeline();
		KeyValue keyValue = new KeyValue(node.opacityProperty(),end);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300),onFinished,keyValue);
		timeLine.getKeyFrames().add(keyFrame);
		timeLine.play();
	}
	

}
