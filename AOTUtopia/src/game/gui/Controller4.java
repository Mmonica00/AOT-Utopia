package game.gui;

import java.awt.DisplayMode;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
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
	private HashMap<Integer, WeaponRegistry> weapons ;
	
	
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
		
		try {
			weapons = DataLoader.readWeaponRegistry();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	private void setupInitialUI() { 
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

	//*****************************************************************************************
	
	public void performBuyWeaponFromShopLane1() { //NOT FINISHED
		//called when weapon and lane are selected 
		
		int boughtWeaponCode = displayWeaponShop();
		if(boughtWeaponCode!=0)
			performPurschaeWeapon(boughtWeaponCode, battle.getOriginalLanes().get(0));
		
		endOfActionCalls();
	}
	public void performBuyWeaponFromShopLane2() { //NOT FINISHED
		//called when weapon and lane are selected 
		
		int boughtWeaponCode = getSelectedWeaponCodeFromShop();
		if(boughtWeaponCode!=0)
			performPurschaeWeapon(boughtWeaponCode, battle.getOriginalLanes().get(1));
		
		endOfActionCalls();
	}
	public void performBuyWeaponFromShopLane3() { //NOT FINISHED
		//called when weapon and lane are selected 
		
		int boughtWeaponCode = getSelectedWeaponCodeFromShop();
		if(boughtWeaponCode!=0)
			performPurschaeWeapon(boughtWeaponCode, battle.getOriginalLanes().get(2));
		
		endOfActionCalls();
	}
	
	private int getSelectedWeaponCodeFromShop() { //NOT FINISHED
		//Helper for performBuyWeaponFromShop();
		checkEndGameCondition();
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
		checkEndGameCondition();
		
		battle.passTurn();
		
		endOfActionCalls();
	}
	
	public void useAIHelper() { //NOT FINISHED
		//called when AI Help button is selected  
		
		
		//get the most Dangerous lane ID (ex. 1,2,3)
		
		//get a suitable weapon ID (ex. 1,2,3,4)
		
		//Purchase the weapon into the lane
		
		//call performPurschaseWeapon(int weaponCode, Lane lane);
		
		endOfActionCalls();
	}
	
	private void endOfActionCalls() {
		updateBattleAttributes();
		updateTurnUI();
		checkEndGameCondition();
	}
	

	//*****************************************************************************************************
	
	
	private void updateTurnUI() { //FINISHED
		//refresh the initial values of 4 labels
		this.turnNumLabel.setText("Turn: "+this.numberOfTurns+" ");
		this.scoreNumLabel.setText("Score: "+this.score+" ");
		this.resourcesNumLabel.setText("Resources: "+this.resourcesGathered+" ");
		this.phaseLabel.setText("Battle Phase: "+this.battlePhase+" ");
				
		//refresh lanes by re-creation 

		Lane lane1 = getLaneFromID(0, battle.getLanes());
		Lane lane2 = getLaneFromID(1, battle.getLanes());
		Lane lane3 = getLaneFromID(2, battle.getLanes());

		firstLaneController = new LaneController(lane1);
		secondLaneController = new LaneController(lane2);
		thirdLaneController = new LaneController(lane3);
		//thirdLaneController.getFullLaneView().setTranslateY(-20);
		
		// Create three partitions
        AnchorPane partition1 = new AnchorPane();
        AnchorPane partition2 = new AnchorPane();
        AnchorPane partition3 = new AnchorPane();

        // Set max height for each partition (adjust these values as needed)
        partition1.setMaxHeight(450); // max height of 200 pixels
        partition2.setMaxHeight(450); // max height of 150 pixels
        partition3.setMaxHeight(450); // max height of 100 pixels
        
        partition1.getChildren().add(firstLaneController.getFullLaneView());
        partition2.getChildren().add(secondLaneController.getFullLaneView());
        partition3.getChildren().add(thirdLaneController.getFullLaneView());
        
        VBox newPane = new VBox();
        newPane.setMaxHeight(450*3);
        partition2.setTranslateY(-10);
        partition3.setTranslateY(-20);
        newPane.getChildren().addAll(partition1,partition2,partition3);
        
		
		allLanesBox.getChildren().clear();
		
		allLanesBox.getChildren().addAll(newPane);
//		allLanesBox.getChildren().add(firstLaneController.getFullLaneView());
//		allLanesBox.getChildren().add(secondLaneController.getFullLaneView());
//		allLanesBox.getChildren().add(thirdLaneController.getFullLaneView());
		
	}
	
	private Lane getLaneFromID(int ID, PriorityQueue<Lane> lanes) {
		for(Lane currLane: lanes) {
			if(currLane.getLANE_ID()==ID)
				return currLane;
		}
		return null;
	}
	
	private void performPurschaeWeapon(int weaponCode, Lane lane) {//FINISHED
		checkEndGameCondition();
		try {
			battle.purchaseWeapon(weaponCode, lane);
		} catch (InsufficientResourcesException e) {
			showAlert("Insufficient Resources", InsufficientResourcesException.getMSG() + battle.getResourcesGathered());
		} catch (InvalidLaneException e) {
			showAlert("Invalid Lane Selected", InvalidLaneException.getMSG());
		}
	}
	
	private void checkEndGameCondition(){ //FINISHED
		if(battle.isGameOver())
			switchToScene6();
	}
	
	public void switchToScene6() { //FINISHED 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene6.fxml"));
		try {
			root=loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Controller6 c = loader.getController();
		c.setScore(battle.getScore());
		
		
		stage = (Stage)((Node)anchorPane).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showAlert(String header, String msg){	
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(false);
		alert.setTitle("Alert");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		
		if (alert.showAndWait().get() == ButtonType.OK){
			System.out.println("Alert is closed");
			//stage.close();
		} 
	}
	
	public int displayWeaponShop() {
		
		ToggleGroup toggleGroup = new ToggleGroup();
		GridPane gridPane = new GridPane();
		gridPane.setHgap(30); 
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);
        
		Label l1 = new Label("NAME");
		gridPane.add(l1, 0, 0);
		Label l2 = new Label("TYPE");
		gridPane.add(l2, 1, 0);
		Label l3 = new Label("PRICE");
		gridPane.add(l3, 2, 0);
		Label l4 = new Label("DAMAGE POINTS");
		gridPane.add(l4, 3, 0);
		Label l5 = new Label("ICON");
		gridPane.add(l5, 4, 0);
		Label l6 = new Label("SELECT A WEAPON:");
		gridPane.add(l6, 5, 0);
		
		RadioButton r1 = new RadioButton();
		r1.setText("Weapon 1");
		r1.setToggleGroup(toggleGroup);
		gridPane.add(r1, 5, 1);
		
		RadioButton r2 = new RadioButton();
		r2.setText("Weapon 2");
		r2.setToggleGroup(toggleGroup);
		gridPane.add(r2, 5, 2);
		
		RadioButton r3 = new RadioButton();
		r3.setText("Weapon 3");
		r3.setToggleGroup(toggleGroup);
		gridPane.add(r3, 5, 3);
		
		RadioButton r4 = new RadioButton();
		r4.setText("Weapon 4");
		r4.setToggleGroup(toggleGroup);
		gridPane.add(r4, 5, 4);
		
		Image i1 = new Image(getClass().getResourceAsStream("Weapon1.gif"));
		ImageView iv1 = new ImageView(i1);
		iv1.setFitWidth(75);
		iv1.setFitHeight(75);
		gridPane.add(iv1, 4, 1);
		
		Image i2 = new Image(getClass().getResourceAsStream("Weapon2.gif"));
		ImageView iv2 = new ImageView(i2);
		iv2.setFitWidth(75);
		iv2.setFitHeight(75);
		gridPane.add(iv2, 4, 2);
		
		Image i3 = new Image(getClass().getResourceAsStream("Weapon3.gif"));
		ImageView iv3 = new ImageView(i3);
		iv3.setFitWidth(75);
		iv3.setFitHeight(75);
		gridPane.add(iv3, 4, 3);
		
		Image i4 = new Image(getClass().getResourceAsStream("Weapon4.gif"));
		ImageView iv4 = new ImageView(i1);
		iv4.setFitWidth(75);
		iv4.setFitHeight(75);
		gridPane.add(iv4, 4, 4);
		
		
//		WeaponRegistry wr1 = weapons.get(0);
//		Label l11 = new Label(weapons.get(0).getName());
//		gridPane.add(l11, 0, 1);
		
		
		
		
		
		
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Weapon Shop");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(".");
        Button closeButton = new Button("BUY WEAPON");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(label,gridPane,closeButton);
        layout.setAlignment(Pos.TOP_CENTER);

        
        
        Scene scene = new Scene(layout,600,400);
        window.setScene(scene);
        window.showAndWait();
        
        RadioButton selectedWeapon = (RadioButton)toggleGroup.getSelectedToggle();
		
		if(selectedWeapon==null) {
			//showAlert("No weapon selected", "please select a weapon from the weaponshop and try again");
		}else {
			if(selectedWeapon.equals(r1))
				return 1;
			if(selectedWeapon.equals(r2))
				return 2;
			if(selectedWeapon.equals(r3))
				return 3;
			if(selectedWeapon.equals(r4))
				return 4;
		}
		return 0;
        
    }
	
	public static int display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return 1;
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

	public int getScore() {
		return score;
	}
	
	

}
