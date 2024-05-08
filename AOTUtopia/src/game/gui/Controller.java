package game.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable{
	
	private int countIntroCycles=0;
	
	
	
	@FXML
	private MediaView mediaView1;


	@FXML 
	private Button startGame;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//startGame.setStyle("-fx-background-color: #C60222");
		//start.setStyle("-fx-background-color: linear-gradient(to right, #D4145A, #FBB03B);");
		
		file = new File("src/game/gui/Intro.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView1.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(new Runnable() {
	        @Override
	        public void run() {
	        	if(countIntroCycles<=2) {
	        		mediaPlayer.seek(Duration.ZERO);
	        		mediaPlayer.play();
	        	}
	        }
	    }); 
	}
	
	public void switchToScene2(ActionEvent event) throws IOException {
		  mediaPlayer.stop();
		  root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
			
	}
	


}
