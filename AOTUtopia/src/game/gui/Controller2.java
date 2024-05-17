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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller2 implements Initializable {

	@FXML
	private RadioButton rButtonEasy,rButtonHard;
	
	
	private String playerName; //stores player name
	private int battleMode; //stores mode (1 for EASY && 2 for HARD)
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup group = new ToggleGroup();
		rButtonEasy.setToggleGroup(group);
		rButtonHard.setToggleGroup(group);
		
	}

	public void switchToScene3(ActionEvent event) throws IOException {
	  
		  battleMode=(rButtonHard.isSelected())?2:1; //if non chosen the default is applied which is Easy Mode
		
		  
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));	
		  root = loader.load();
			
		  Controller3 controller3 = loader.getController();
		  controller3.setGameMode(battleMode);

		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getBattleMode() {
		return battleMode;
	}

	

	
}
