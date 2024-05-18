package game.gui;

import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.lanes.Lane;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tester extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Battle battle = new Battle(1, 0, 100, 3, 250);
		battle.purchaseWeapon(1, battle.getOriginalLanes().get(0));
		battle.purchaseWeapon(2, battle.getOriginalLanes().get(1));
		battle.purchaseWeapon(3, battle.getOriginalLanes().get(2));
//		battle.purchaseWeapon(4, battle.getOriginalLanes().get(0));
		LaneControllerEasy LC =new LaneControllerEasy(battle.getOriginalLanes().get(0));
		LaneControllerEasy LC2 =new LaneControllerEasy(battle.getOriginalLanes().get(1));
		LaneControllerEasy LC3 =new LaneControllerEasy(battle.getOriginalLanes().get(2));
		for(int i=0; i<5;i++) {
			battle.passTurn();
		}
		
		LC =new LaneControllerEasy(battle.getOriginalLanes().get(0));
		//LC2 =new LaneController(battle.getOriginalLanes().get(1));
//		LC3 =new LaneController(battle.getOriginalLanes().get(2));
//		LC.refreshLane(battle.getOriginalLanes().get(0));
		//LC2.refreshLane(battle.getOriginalLanes().get(1));
		LC3.refreshLane(battle.getOriginalLanes().get(2));
	
		
		
		Label l1 = new Label("Lane1");
		Label l2 = new Label("Lane2");
		Label l3 = new Label("Lane3");
		HBox hb1 = new HBox();
		hb1.getChildren().add(LC.getFullLaneView());

		VBox pane =new VBox();
		pane.getChildren().add(hb1);
		pane.getChildren().add(LC2.getFullLaneView());
		pane.getChildren().add(LC3.getFullLaneView());
		AnchorPane.setLeftAnchor(pane, 0.0);
		
		Scene scene = new Scene(pane, 700, 500);
        //scene.setFill(Color.RED);

        primaryStage.setScene(scene);
        primaryStage.setTitle("testerView");
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
