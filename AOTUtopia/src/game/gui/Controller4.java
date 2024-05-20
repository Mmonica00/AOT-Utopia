package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller4 implements Initializable {

	public static boolean lane1Lost=false;
	public static boolean lane2Lost=false;
	public static boolean lane3Lost=false;
	
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
	private Lane chosenLaneFromAI; 
	
	
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
	
	
	
	
	
	//Other GUI Attributes
	private LaneControllerEasy firstLaneController;
	private LaneControllerEasy secondLaneController;
	private LaneControllerEasy thirdLaneController;
	
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
			System.out.println("BATTLE INITIALIZATION ERROR");
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
		this.phaseLabel.setText("Phase: "+this.battlePhase+" ");
		
		//setup the lane Controllers based on this.originalLanes
		firstLaneController = new LaneControllerEasy(battle.getOriginalLanes().get(0));
		secondLaneController = new LaneControllerEasy(battle.getOriginalLanes().get(1));
		thirdLaneController = new LaneControllerEasy(battle.getOriginalLanes().get(2));
		
		allLanesBox.getChildren().add(firstLaneController.getFullLaneView());
		allLanesBox.getChildren().add(secondLaneController.getFullLaneView());
		allLanesBox.getChildren().add(thirdLaneController.getFullLaneView());

		
		System.out.println("UI is set");
	
	}

	//*****************************************************************************************
	
	public void performBuyWeaponFromShopLane1() { //FINISHED
		//called when weapon and lane are selected 
		
		int boughtWeaponCode = displayWeaponShop();
		if(boughtWeaponCode!=0)
			performPurschaeWeapon(boughtWeaponCode, battle.getOriginalLanes().get(0));
		
		endOfActionCalls();
	}
	public void performBuyWeaponFromShopLane2() { //FINISHED
		//called when weapon and lane are selected 
		
		int boughtWeaponCode = displayWeaponShop();
		if(boughtWeaponCode!=0)
			performPurschaeWeapon(boughtWeaponCode, battle.getOriginalLanes().get(1));
		
		endOfActionCalls();
	}
	public void performBuyWeaponFromShopLane3() { //FINISHED
		//called when weapon and lane are selected 
		
		int boughtWeaponCode = displayWeaponShop();
		if(boughtWeaponCode!=0)
			performPurschaeWeapon(boughtWeaponCode, battle.getOriginalLanes().get(2));
		
		endOfActionCalls();
	}
	
	public void performPassTurn() { //FINISHED
		//called when pass turn button is clicked
		checkEndGameCondition();
		if(!battle.isGameOver())
			battle.passTurn();
		
		endOfActionCalls();
	}
	
	public void useAIHelper() { //FINISHED
		//called when AI Help button is selected  
		//ALL Helper methods can be find at the end of the class
		//gets the most Dangerous lane ID (ex. 1,2,3)
		//gets a suitable weapon ID (ex. 1,2,3,4)
		
		if(!battle.isGameOver()) {
			int chosenWeaponCode = findBestWeaponCode(battle.getLanes(), battle.getResourcesGathered(), battle.getWeaponFactory().getWeaponShop());
			
			if(chosenWeaponCode==-1) {
				showAlert("AI Message", "AI Helper cannot suggest a good Lane so it passed the turn");
				battle.passTurn();
				updateTurnUI();
			}else {
				//Purchase the weapon into the lane
				
				try {
					battle.purchaseWeapon(chosenWeaponCode, chosenLaneFromAI);
				} catch (InsufficientResourcesException e) {
					showAlert("AI Message", "Resources are Low!! AI can't help so it passed the turn");
					battle.passTurn();
					updateTurnUI();
					System.out.println("AI RESOURCES"); 
				} catch (InvalidLaneException e) {
					e.printStackTrace();
					System.out.println("AI LANES"); //shouldn't happen ever as it gets lanes from PQ
				} catch (Error e) {
					e.printStackTrace();
					System.out.println("AI Error");
				}
				
				endOfActionCalls();
			}
		}
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

		if(!firstLaneController.lane.isLaneLost()) {
			firstLaneController = new LaneControllerEasy(lane1);
		} else {
//			lane1Lost = true;
		}
		if(!secondLaneController.lane.isLaneLost()) {
			secondLaneController = new LaneControllerEasy(lane2);
		}else {
//			lane2Lost = true;
		}
		if(!thirdLaneController.lane.isLaneLost()) {
			thirdLaneController = new LaneControllerEasy(lane3);
		}else {
//			lane3Lost = true;
		}
		
		Rectangle rect = new Rectangle();
		rect.setWidth(1000);
		rect.setHeight(150);
		rect.setTranslateX(100);
		//rect.setTranslateY(-30);
		rect.setOpacity(0.65);
		
		
		// Create three partitions
        AnchorPane partition1 = new AnchorPane();
        AnchorPane partition2 = new AnchorPane();
        AnchorPane partition3 = new AnchorPane();

        partition1.setMaxHeight(450); 
        //partition1.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        partition2.setMaxHeight(450); 
        //partition2.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        partition3.setMaxHeight(450); 
        //partition3.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        
        if(firstLaneController.lane.isLaneLost())
        	partition1.getChildren().add(rect);
        else
        	partition1.getChildren().add(firstLaneController.getFullLaneView());
        
        if(secondLaneController.lane.isLaneLost())
        	partition2.getChildren().add(rect);
        else
        	partition2.getChildren().add(secondLaneController.getFullLaneView());
        
        if(thirdLaneController.lane.isLaneLost())
        	partition3.getChildren().add(rect);
        else
        	partition3.getChildren().add(thirdLaneController.getFullLaneView());
        
//        partition1.getChildren().add(firstLaneController.getFullLaneView());
//        partition2.getChildren().add(secondLaneController.getFullLaneView());
//        partition3.getChildren().add(thirdLaneController.getFullLaneView());
        
        VBox newPane = new VBox();
        newPane.setMaxHeight(450*3);
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
		
		
		stage = (Stage)((Node)passTurnButton).getScene().getWindow();
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
		
		if (alert.showAndWait().get() == ButtonType.OK)
			System.out.println("Alert is closed");
			
	}
	
	public int displayWeaponShop() {
		
		ToggleGroup toggleGroup = new ToggleGroup();
		GridPane gridPane = new GridPane();
		gridPane.setHgap(30); 
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);
        
		Label l1 = new Label("NAME");
		l1.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 20pt;");
		gridPane.add(l1, 0, 0);
		Label l2 = new Label("TYPE");
		l2.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 20pt;");
		gridPane.add(l2, 1, 0);
		Label l3 = new Label("PRICE");
		l3.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 20pt;");
		gridPane.add(l3, 2, 0);
		Label l4 = new Label("DAMAGE");
		l4.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 20pt;");
		gridPane.add(l4, 3, 0);
		Label l5 = new Label("ICON");
		l5.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 20pt;");
		gridPane.add(l5, 4, 0);
		Label l6 = new Label("SELECTION:");
		l6.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 20pt;");
		gridPane.add(l6, 5, 0);
		
		RadioButton r1 = new RadioButton();
		r1.setText("Weapon 1");
		r1.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		r1.setToggleGroup(toggleGroup);
		gridPane.add(r1, 5, 1);
		
		RadioButton r2 = new RadioButton();
		r2.setText("Weapon 2");
		r2.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		r2.setToggleGroup(toggleGroup);
		gridPane.add(r2, 5, 2);
		
		RadioButton r3 = new RadioButton();
		r3.setText("Weapon 3");
		r3.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		r3.setToggleGroup(toggleGroup);
		gridPane.add(r3, 5, 3);
		
		RadioButton r4 = new RadioButton();
		r4.setText("Weapon 4");
		r4.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		r4.setToggleGroup(toggleGroup);
		gridPane.add(r4, 5, 4);
		
		Image i1 = new Image(getClass().getResourceAsStream("W1.png"));
		ImageView iv1 = new ImageView(i1);
		iv1.setFitWidth(75);
		iv1.setFitHeight(75);
		gridPane.add(iv1, 4, 1);
		
		Image i2 = new Image(getClass().getResourceAsStream("W2.png"));
		ImageView iv2 = new ImageView(i2);
		iv2.setFitWidth(75);
		iv2.setFitHeight(75);
		gridPane.add(iv2, 4, 2);
		
		Image i3 = new Image(getClass().getResourceAsStream("W3.png"));
		ImageView iv3 = new ImageView(i3);
		iv3.setFitWidth(75);
		iv3.setFitHeight(75);
		gridPane.add(iv3, 4, 3);
		
		Image i4 = new Image(getClass().getResourceAsStream("W4.png"));
		ImageView iv4 = new ImageView(i4);
		iv4.setFitWidth(75);
		iv4.setFitHeight(75);
		gridPane.add(iv4, 4, 4);
		
		
		WeaponRegistry wr1 = weapons.get(1);
		Label l11 = new Label(wr1.getName());
		l11.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l11, 0, 1);
		Label l12 = new Label("Piercing Cannon");
		l12.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l12, 1, 1);
		Label l13 = new Label(wr1.getPrice()+"");
		l13.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l13, 2, 1);
		Label l14 = new Label(wr1.getDamage()+"");
		l14.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l14, 3, 1);
		
		
		WeaponRegistry wr2 = weapons.get(2);
		Label l21 = new Label(wr2.getName());
		l21.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l21, 0, 2);
		Label l22 = new Label("Sniper Cannon");
		l22.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l22, 1, 2);
		Label l23 = new Label(wr2.getPrice()+"");
		l23.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l23, 2, 2);
		Label l24 = new Label(wr2.getDamage()+"");
		l24.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l24, 3, 2);
		
		
		WeaponRegistry wr3 = weapons.get(3);
		Label l31 = new Label(wr3.getName());
		l31.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l31, 0, 3);
		Label l32 = new Label("VolleySpread Cannon");
		l32.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 8pt;");
		gridPane.add(l32, 1, 3);
		Label l33 = new Label(wr3.getPrice()+"");
		l33.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l33, 2, 3);
		Label l34 = new Label(wr3.getDamage()+"");
		l34.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l34, 3, 3);
		
		WeaponRegistry wr4 = weapons.get(4);
		Label l41 = new Label(wr4.getName());
		l41.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l41, 0, 4);
		Label l42 = new Label("Wall Trap");
		l42.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l42, 1, 4);
		Label l43 = new Label(wr4.getPrice()+"");
		l43.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l43, 2, 4);
		Label l44 = new Label(wr4.getDamage()+"");
		l44.setStyle("-fx-font-family: 'Georgia'; -fx-font-weight: bold; -fx-font-size: 11pt;");
		gridPane.add(l44, 3, 4);
		
		
		
		
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("WEAPON SHOP");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(".");
        Button closeButton = new Button("BUY WEAPON");
        closeButton.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11pt;"+"-fx-background-color: #74bad4;"+ "-fx-border-color: black; -fx-border-width: 2px;");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(70);
        closeButton.setTranslateY(-10);
        closeButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(toggleGroup.getSelectedToggle() == null)
					showAlert("No weapon Selected", "Please select a weapon from the shop");
				else
					window.close();
			}
        	
        });

        
        VBox layout = new VBox();
        
        layout.getChildren().addAll(label,gridPane,closeButton);
        layout.setAlignment(Pos.TOP_CENTER);

        Image bgImg = new Image(getClass().getResourceAsStream("weaponShop1.jpg"));
		BackgroundImage backgroundImage = new BackgroundImage(bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		layout.setBackground(new Background(backgroundImage));
		
        Scene scene = new Scene(layout,950,450);
        window.setScene(scene);
        window.setResizable(false);
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
	
	public static void display(String title, String message) {
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
        
    }

	//--------------------------------------------------------------------------------
	// AI Helper Methods
	


    public int findBestWeaponCode(PriorityQueue<Lane> lanes, int resourcesGathered, HashMap<Integer, WeaponRegistry> weapons) {
        int bestWeaponCode = -1;
        double maxUtility = Double.NEGATIVE_INFINITY;

        for (Lane lane : lanes) {
            double laneUtility = calculateLaneUtility(lane);

            for (Map.Entry<Integer, WeaponRegistry> entry : weapons.entrySet()) {
                WeaponRegistry weapon = entry.getValue();
                if (weapon.getPrice() <= resourcesGathered) {
                    double weaponUtility = calculateWeaponUtility(weapon, lane); 
                    double totalUtility = laneUtility * weaponUtility;

                    if (totalUtility > maxUtility) {
                        maxUtility = totalUtility;
                        bestWeaponCode = weapon.getCode();
                        chosenLaneFromAI = lane;
                    }
                }
            }
        }

        return bestWeaponCode;
    }

    private double calculateLaneUtility(Lane lane) {
        // Calculate the utility of the lane based on danger level, wall health
        double laneUtility = 0;

        laneUtility -= lane.getDangerLevel();
        laneUtility -= (100 - lane.getLaneWall().getCurrentHealth()) * 0.1;

        return laneUtility;
    }

    private double calculateWeaponUtility(WeaponRegistry weapon, Lane lane) {
        // Calculate the utility of the weapon based on factors like weapon type, titan characteristics, etc.
        double weaponUtility = 0;
        
        weaponUtility += weapon.getDamage() * 0.1;
        
        switch (weapon.getCode()) {
            case 1:
                if (lane.getTitans().size() >= 5) {
                    weaponUtility += 1;
                }
                break;
            case 2:
                if (!lane.getTitans().isEmpty()) {
                    weaponUtility += 1;
                }
                break;
            case 3:
                weaponUtility += calculateRangeUtility(weapon, lane);
                break;
            case 4:
            	if (lane.getTitans().peek()!=null) {
            		if(lane.getTitans().peek().getDistance()==0)
            			weaponUtility += 0.5;
                }
                break;

        }

        return weaponUtility;
    }

    private double calculateRangeUtility(WeaponRegistry weapon, Lane lane) {
        // Calculate the utility based on the weapon's range and the position of titans in the lane
        double rangeUtility = 0;
        int titansInRange = 0;
        for (Titan titan : lane.getTitans()) {
            if (titan.getDistance() >= weapon.getMinRange() && titan.getDistance() < weapon.getMaxRange()) {
                titansInRange++;
            }
        }
        
        rangeUtility += titansInRange * 0.5;

        return rangeUtility;
    }

	

	//--------------------------------------------------------------------------------
	//Getters and Setters (Just in case)
	
	public Battle getBattle() {
		return battle;
	}

	public int getScore() {
		return score;
	}
	
	

}
