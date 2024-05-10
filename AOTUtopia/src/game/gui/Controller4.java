package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller4 implements Initializable {

	private String playerName;
	
	//Battle Attributes
	private Battle battle;
	private HashMap<Integer, WeaponRegistry> weapons;
	private int WALL_BASE_HEALTH;
	private int numberOfTurns; 
	private int resourcesGathered; 
	private BattlePhase battlePhase; 
	private int numberOfTitansPerTurn; 
	private int score; 
	private int titanSpawnDistance;
	private HashMap<Integer, TitanRegistry> titansArchives; // read from Dataloader
	private ArrayList<Titan> approachingTitans; // will be treated as a queue (FIFO)
	private PriorityQueue<Lane> lanes;
	
	//SceneBuilder Attributes
	@FXML
	private GridPane gridPane;
	@FXML
	private Label turnNumLabel;
	@FXML
	private Label scoreNumLabel;
	@FXML
	private Label resourcesNumLabel;
	@FXML
	private Label phaseLabel;
	
	
	
	
	
	//Other GUI Attributes
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
//	for (int row = 0; row < gridPane.getRowCount(); row++) {
//  for (int col = 0; col < gridPane.getColumnCount(); col++) {
//      ImageView imageView = new ImageView(new Image("BG3.png")); 
//      gridPane.add(imageView, col, row);
//  }
//}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Creates an instance of battle (aka TheBackend)
		try {
			battle = new Battle(1, 0, 10, 3, 250);
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		
		//assign starting values of battle attributes
		updateBattleAttributes();
		
		//setup the UI (WeaponShop, Attributes, Lanes)
		setupInitialUI();
		
		//call gamePlay for actual game 
		//gamePlay();

		
		if(battle.isGameOver())
			switchToScene6();
	}
	
	private void setupInitialUI() { // TODO: a work here
		//setup the initial values of 4 buttons
		this.turnNumLabel.setText("Turn: "+this.numberOfTurns+"");
		this.scoreNumLabel.setText("Score: "+this.score+"");
		this.resourcesNumLabel.setText("Resources: "+this.resourcesGathered+"");
		this.phaseLabel.setText("Battle Phase: "+this.battlePhase+"");
		
		System.out.println("UI is set");
	
	}

	private void gamePlay() {
		//while game is not over do the following
		do {
		// check if buyWeapon() or passTurn() clicked 
		
		//perfom backend action
				
		// update UI (Attributes, Lanes)
				
		
		}while(!battle.isGameOver()); 
		//Checks End game condition if lost then switchToScene6 else repeat actions
	}
	

	
	public void updateBattleAttributes() {
		this.WALL_BASE_HEALTH = battle.getWALL_BASE_HEALTH();
		this.numberOfTurns = battle.getNumberOfTurns(); 
		this.resourcesGathered = battle.getResourcesGathered(); 
		this.battlePhase = battle.getBattlePhase(); 
		this.numberOfTitansPerTurn = battle.getNumberOfTitansPerTurn(); 
		this.score = battle.getScore(); 
		this.titanSpawnDistance = battle.getTitanSpawnDistance();
		this.titansArchives = battle.getTitansArchives(); // read from Dataloader
		this.approachingTitans = battle.getApproachingTitans(); // will be treated as a queue (FIFO)
		this.lanes = battle.getLanes();
		this.weapons = battle.getWeaponFactory().getWeaponShop();
	}
	
	public void switchToScene6()  {
		  try {
			root = FXMLLoader.load(getClass().getResource("Scene6.fxml"));
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		  stage = (Stage)((Node)gridPane).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	//--------------------------------------------------------------------------------
	
	private void useAIHelper() {
		//get the most Dangerous lane ID (ex. 1,2,3)
		//get a suitable weapon ID (ex. 1,2,3,4)
		//purschase the weapon into the lane
	}
	
	private void test() {
		System.out.println("Tester method Shouldn't be called");
		battle.getWeaponFactory().getWeaponShop();
		
		
	}

	//--------------------------------------------------------------------------------

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
}
