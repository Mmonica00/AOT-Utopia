package game.gui;

import game.engine.Battle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tester extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Battle battle = new Battle(1, 0, 100, 3, 250);
		battle.passTurn();
		
		//WeaponLaneView wp = new WeaponLaneView(battle.getOriginalLanes().get(0).getWeapons());
		TitanView tv = new TitanView(battle.getApproachingTitans().get(0));
		
		VBox pane = new VBox();
		pane.setMaxHeight(200);
		pane.setMaxWidth(200);
		pane.getChildren().add(tv);
		Scene scene = new Scene(pane, 200, 200);
        //scene.setFill(Color.RED);

        primaryStage.setScene(scene);
        primaryStage.setTitle("testerView");
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
