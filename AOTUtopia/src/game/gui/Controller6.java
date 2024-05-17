package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller6 implements Initializable{
	
    @FXML
    private Button logout;
    @FXML
    private AnchorPane anchorPane;
    @FXML 
    private Label scoreLabel;
    
    private int finalScore;
    
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.scoreLabel.setText("Your Final Score is: "+finalScore);
		
	}
	
	public void switchToScene2(ActionEvent event) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void logout(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to close the game? ");
		alert.setResizable(false);
		
		if(alert.showAndWait().get() == ButtonType.OK){
			stage = (Stage) anchorPane.getScene().getWindow();
			System.out.println("User logged out");
			stage.close();
		}
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	

	
}
