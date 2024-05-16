package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller4 implements Initializable {

	private String playerName;
	
	//Battle Attributes
	private Battle battle;
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
	private ArrayList<Lane> originalLanes;
	private HashMap<Integer, WeaponRegistry> weapons;
	
	
	//SceneBuilder Attributes
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Button passTurnButton;
	@FXML
	private Button AIButton;
	@FXML
	private Label turnNumLabel;
	@FXML
	private Label scoreNumLabel;
	@FXML
	private Label resourcesNumLabel;
	@FXML
	private Label phaseLabel;
	@FXML
	private ToggleGroup weaponsToggleGroup;
	@FXML
	private VBox allLanesBox;
	@FXML
	private Button buyLane1;
	@FXML
	private RadioButton weapon1;
	@FXML
	private RadioButton weapon2;
	@FXML
	private RadioButton weapon3;
	@FXML
	private RadioButton weapon4;
	
	
	
	//Other GUI Attributes
	private LaneController firstLaneController;
	private LaneController secondLaneController;
	private LaneController thirdLaneController;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Creates an instance of battle (aka TheBackend)
		try {
			battle = new Battle(1, 0, 40, 3, 250);
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		
//		battle.passTurn();
//		Wall wall = battle.getOriginalLanes().get(0).getLaneWall();
//		WallView wv = new WallView(wall);
//		AnchorPane.setTopAnchor(wv, 10.0);
//		anchorPane.getChildren().add(wv);
		
		//assign starting values of battle attributes
		updateBattleAttributes();
		
		//setup the UI (WeaponShop, Attributes, Lanes)
		setupInitialUI();
		
		//call game play for actual game by buttons
		//game play will be called based on pass turn or purchase weapon being clicked
		//after any of them they will perform actions in performPassTurn();
		
	}
	
	private void updateBattleAttributes() {
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
		this.originalLanes = battle.getOriginalLanes();
		this.weapons = battle.getWeaponFactory().getWeaponShop();
	}
	
	private void setupInitialUI() { // TODO: a work here
		//setup the initial values of 4 labels
		this.turnNumLabel.setText("Turn: "+this.numberOfTurns+" ");
		this.scoreNumLabel.setText("Score: "+this.score+" ");
		this.resourcesNumLabel.setText("Resources: "+this.resourcesGathered+" ");
		this.phaseLabel.setText("Battle Phase: "+this.battlePhase+" ");
		
		//setup the lane Controllers based on this.originalLanes
		firstLaneController = new LaneController(battle.getOriginalLanes().get(0));
		secondLaneController = new LaneController(battle.getOriginalLanes().get(1));
		thirdLaneController = new LaneController(battle.getOriginalLanes().get(2));
		
		allLanesBox.getChildren().add(firstLaneController.getFullLaneView());
		allLanesBox.getChildren().add(secondLaneController.getFullLaneView());
		allLanesBox.getChildren().add(thirdLaneController.getFullLaneView());
		//setup the weaponShop
		//setupWeaponShop();
		
		System.out.println("UI is set");
	
	}

	public void performBuyWeaponFromShopLane1() { //NOT FINISHED
		//called when weapon and lane are selected 
		int boughtWeaponCode = getSelectedWeaponCodeFromShop();
		try {
			battle.purchaseWeapon(boughtWeaponCode, battle.getOriginalLanes().get(0));
		} catch (InsufficientResourcesException e) {
			showAlert("Insufficient Resources", InsufficientResourcesException.getMSG() + battle.getResourcesGathered());
		} catch (InvalidLaneException e) {
			showAlert("Invalid Lane Selected", InvalidLaneException.getMSG());
		}
		
		//update the battle attributes
		updateBattleAttributes();
		//calls updateTurnUI() after completion
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}
	public void performBuyWeaponFromShopLane2() { //NOT FINISHED
		//called when weapon and lane are selected 
		int boughtWeaponCode = getSelectedWeaponCodeFromShop();
		try {
			battle.purchaseWeapon(boughtWeaponCode, battle.getOriginalLanes().get(1));
		} catch (InsufficientResourcesException e) {
			showAlert("Insufficient Resources", InsufficientResourcesException.getMSG() + battle.getResourcesGathered());
		} catch (InvalidLaneException e) {
			showAlert("Invalid Lane Selected", InvalidLaneException.getMSG());
		}
		
		//update the battle attributes
		updateBattleAttributes();
		//calls updateTurnUI() after completion
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}
	public void performBuyWeaponFromShopLane3() { //NOT FINISHED
		//called when weapon and lane are selected 
		int boughtWeaponCode = getSelectedWeaponCodeFromShop();
		try {
			battle.purchaseWeapon(boughtWeaponCode, battle.getOriginalLanes().get(2));
		} catch (InsufficientResourcesException e) {
			showAlert("Insufficient Resources", InsufficientResourcesException.getMSG() + battle.getResourcesGathered());
		} catch (InvalidLaneException e) {
			showAlert("Invalid Lane Selected", InvalidLaneException.getMSG());
		}
		
		//update the battle attributes
		updateBattleAttributes();
		//calls updateTurnUI() after completion
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}
	
	private int getSelectedWeaponCodeFromShop() { //NOT FINISHED
		//Helper for performBuyWeaponFromShop();
		
		int weaponCode = 0;
		RadioButton selectedWeapon = (RadioButton)weaponsToggleGroup.getSelectedToggle();
		//System.out.println(selectedWeapon.getText());
		if(selectedWeapon==null) {
			showAlert("No weapon selected", "please select a weapon from the weaponshop and try again");
		}else {
			if(selectedWeapon.equals(weapon1))
				return 1;
			if(selectedWeapon.equals(weapon2))
				return 2;
			if(selectedWeapon.equals(weapon3))
				return 3;
			if(selectedWeapon.equals(weapon4))
				return 4;
		}
		return weaponCode;
	}
	
	public void performPassTurn() { //NOT FINISHED
		//called when pass turn button is clicked
		
		battle.passTurn();
		//update the battle attributes
		updateBattleAttributes();
		//calls updateTurnUI() after completion
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}
	
	public void useAIHelper() { //NOT FINISHED
		//called when AI Help button is selected  
		
		
		//get the most Dangerous lane ID (ex. 1,2,3)
		
		//get a suitable weapon ID (ex. 1,2,3,4)
		
		//Purchase the weapon into the lane
		//call performBuyWeaponFromAI(int weaponCode, Lane lane);
	}
	
	public void performBuyWeaponFromAI(int weaponCode, Lane lane) { //NOT FINISHED
		// An edited version of performBuyWeaponFrom shop but is called when calling AI Button
		try {
			battle.purchaseWeapon(weaponCode, lane);
		} catch (InsufficientResourcesException e) {
			showAlert("Insufficient Resources", InsufficientResourcesException.getMSG() + battle.getResourcesGathered());
		} catch (InvalidLaneException e) {
			showAlert("Invalid Lane Selected", InvalidLaneException.getMSG());
		}
		
		//update the battle attributes
		updateBattleAttributes();
		//calls updateTurnUI() after completion of action
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}

	
	
	private void updateTurnUI() { //FINISHED
		//refresh the initial values of 4 labels
		this.turnNumLabel.setText("Turn: "+this.numberOfTurns+" ");
		this.scoreNumLabel.setText("Score: "+this.score+" ");
		this.resourcesNumLabel.setText("Resources: "+this.resourcesGathered+" ");
		this.phaseLabel.setText("Battle Phase: "+this.battlePhase+" ");
				
		//calls refresh lane for every lane
//		Lane newFirstLane = battle.getOriginalLanes().get(0);
//		firstLaneController.refreshLane(newFirstLane);
//		
//		Lane newSecondLane = battle.getOriginalLanes().get(1);
//		firstLaneController.refreshLane(newSecondLane);
//		
//		Lane newThirdLane = battle.getOriginalLanes().get(2);
//		firstLaneController.refreshLane(newThirdLane);

		firstLaneController = new LaneController(battle.getOriginalLanes().get(0));
		secondLaneController = new LaneController(battle.getOriginalLanes().get(1));
		thirdLaneController = new LaneController(battle.getOriginalLanes().get(2));
		
		allLanesBox.getChildren().clear();
		
		allLanesBox.getChildren().add(firstLaneController.getFullLaneView());
		allLanesBox.getChildren().add(secondLaneController.getFullLaneView());
		allLanesBox.getChildren().add(thirdLaneController.getFullLaneView());
		
	}
	
	private Lane findCorrespondingLane(Lane lane,PriorityQueue<Lane> lanes) { //STATUS: FINISHED
		//helper to link original lane to newLane in PQ
		
		for(Lane currLane:lanes) {
			if(lane.getLANE_ID()==currLane.getLANE_ID())
				return currLane;
		}
		
		//if lane is lost it's removed from PQ so method returns null
		return null; 
	}

	
	private void checkEndGameCondition(){ //FINISHED
		if(battle.isGameOver())
			switchToScene6();
	}
	
	public void switchToScene6() { //FINISHED 
		try {
			root = FXMLLoader.load(getClass().getResource("Scene6.fxml"));
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		
		stage = (Stage)((Node)anchorPane).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showAlert(String header, String msg){	
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setResizable(false);
		alert.setTitle("Alert");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		
		if (alert.showAndWait().get() == ButtonType.OK){
			System.out.println("Alert is closed");
			//stage.close();
		} 
	}

	//--------------------------------------------------------------------------------
	// Supplementary methods
	
	
	private void test() {
		System.out.println("Tester method Shouldn't be called");
		weapons.get(2).buildWeapon();
		
		
	}

	//--------------------------------------------------------------------------------
	//Getters and Setters (Just in case)
	
	public Battle getBattle() {
		return battle;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
