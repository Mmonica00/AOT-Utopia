package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import game.engine.Battle;
import game.engine.BattlePhase;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private ArrayList<LaneController> laneViews = new ArrayList<LaneController>();
	
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
	@FXML
	private ToggleGroup weaponsToggleGroup;
	
	
	
	//Other GUI Attributes
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Creates an instance of battle (aka TheBackend)
		try {
			battle = new Battle(1, 0, 10, 3, 250);
		} catch (IOException e) {
			System.out.println(e.getCause());
		}
		
		//assign starting values of battle attributes
		setupInitialBattleAttributes();
		
		//setup the UI (WeaponShop, Attributes, Lanes)
		setupInitialUI();
		
		//call game play for actual game by buttons
		//game play will be called based on pass turn or purchase weapon being clicked
		//after any of them they will perform actions in performPassTurn();
		
	}
	
	public void setupInitialBattleAttributes() {
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
		
		//setup the lanes based on this.originalLanes
		
		
		//setup the weaponShop
		//setupWeaponShop()
		
		System.out.println("UI is set");
	
	}

	private void performBuyWeaponFromShop() { //NOT FINISHED
		
		
		//calls updateTurnUI() after completion
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}
	
	private int getSelectedWeaponCodeFromShop() { //NOT FINISHED
		//Helper for performBuyWeaponFromShop();
		int weaponCode = 0;
		//TODO: 'A' Handle Null Case
		RadioButton selectedWeapon = (RadioButton)weaponsToggleGroup.getSelectedToggle();
		System.out.println(selectedWeapon.getText());
		
		//get weaponCode based on Button clicked
		
		
		
		return weaponCode;
	}
	
	private void useAIHelper() { //NOT FINISHED
		//get the most Dangerous lane ID (ex. 1,2,3)
		
		//get a suitable weapon ID (ex. 1,2,3,4)
		
		//Purchase the weapon into the lane
		//call performBuyWeaponFromAI(int weaponCode, Lane lane);
	}
	
	private void performBuyWeaponFromAI(int weaponCode, Lane lane) { //NOT FINISHED
		// An edited version of performBuyWeaponFrom shop but is called when calling AI Button
		try {
			battle.purchaseWeapon(weaponCode, lane);
		} catch (InsufficientResourcesException e) {
			System.out.println(e.getCause());
			display("Insufficent Resources!", e.getMSG()+e.getResourcesProvided());
		} catch (InvalidLaneException e) {
			System.out.println(e.getCause());
			display("Invalid Lane!", e.getMSG());
		}
		
		//calls updateTurnUI() after completion of action
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}

	private void performPassTurn() { //NOT FINISHED
		battle.passTurn();
		//calls updateTurnUI() after completion
		updateTurnUI();
		//checks if game is over then moves to end Scene
		checkEndGameCondition();
	}
	
	private void updateTurnUI() { //NOT FINISHED
//		for(LaneController currLaneController : laneViews) {
//			currLaneController.refreshLane();
//		}
	}
	
	
	
	
	
	private void checkEndGameCondition(){ //FINISHED
		if(battle.isGameOver()) {
			switchToScene6();
		}
	}
	
	public void switchToScene6() { //FINISHED
		//int x=getSelectedWeaponCodeFromShop();
		
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
	
	public static void display(String title, String message) { //NOT FINISHED
		//Helper method to show Alerts with custom message
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(400);
        window.setHeight(200);

        Label label = new Label();
        label.setText(message);
        Font font = new Font("Arial", 20); // Font name, font size

        // Set the font of the Label
        label.setFont(font);
        Button closeButton = new Button("Close window");
        closeButton.setTranslateY(30);
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
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
