package game.gui;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Controller3 implements Initializable{

	public Controller3() {
		super();
	}

	private MediaPlayer audioPlayer;
	@FXML
	private Button playButton;
	
	private String playerName;
	private int gameMode;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	public void goToGameMode(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4.fxml"));;
		try {
			if (gameMode==2) //goes to hard Game Mode
				loader = new FXMLLoader(getClass().getResource("Scene5.fxml"));
			else //goes to easy Game Mode
				loader = new FXMLLoader(getClass().getResource("Scene4.fxml"));
		}catch(Exception e) {
			System.out.println(e.getCause());
		}
		
		root=loader.load();
		playMusic();
		
		//Controller4 controller4 = new Controller4();
		
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	public void switchToScene4(ActionEvent event) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("Scene4.fxml"));
		  playMusic();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();	
	}

	public void switchToScene5(ActionEvent event) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("Scene5.fxml"));
		  playMusic();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();	
	}
	
	private void playMusic() {
		Media media = new Media(getClass().getResource("Music1.mp3").toExternalForm());
		audioPlayer = new MediaPlayer(media);
		audioPlayer.setVolume(0.30);
		audioPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		audioPlayer.play();
	}
	
	private void stopMusic() {
		if(audioPlayer!=null) {
			audioPlayer.stop();
		}
	}
	
	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
		System.out.println("Game Mode is:"+gameMode);
	}
	
	

}
