package game.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import game.engine.base.Wall;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LaneControllerHard {

	Lane lane;
	ArrayList<TitanViewHard> titansInLane = new ArrayList<TitanViewHard>();
	WallViewHard wallView ;
	WeaponLaneViewHard weaponLane;
	AnchorPane lanePane;
	HBox fullLaneView; //ALL COMPONENTS OF THE LANE
	private ImageView lostLane;
	
	
	public LaneControllerHard(Lane lane) {
		this.lane = lane;
		this.wallView = new WallViewHard(lane); 
		this.weaponLane = new WeaponLaneViewHard(lane.getWeapons());
		
//		if(lane==null || lane.isLaneLost()) {
//			titansInLane.clear();
//			lostLane.setOpacity(0.0);
//		}
			
		this.lanePane = new AnchorPane();
		this.fullLaneView = new HBox();
		lanePane.setMaxWidth(600);
		lanePane.setMaxHeight(100);
		fullLaneView.setMaxWidth(1000);
		fullLaneView.setMaxHeight(100);
		
		
		fullLaneView.getChildren().addAll(weaponLane.getGridPane(),wallView.getWallBox(),lanePane);
		
		//lanePane.setStyle(null);
//		AnchorPane.setLeftAnchor(wallView.getWallBox(), 0.0);
//		AnchorPane.setLeftAnchor(weaponLane.getGridPane(), 100.0);
		//Image laneImg = new Image(getClass().getResourceAsStream("wall.png"));
        //lostLane = new ImageView(laneImg);
		updateTitansViews();
		
	}

	public void refreshLane(Lane newLane) {
		this.lane = newLane;
		
		if(lane==null || lane.isLaneLost()) {
			//lanePane.getChildren().add(lostLane);
			
			
			
		} else {
			WallViewHard.updateWall(lane.getLaneWall());
			weaponLane = new WeaponLaneViewHard(lane.getWeapons());
			updateTitansViews();		
		}
	}

	private void updateTitansViews() {
		
		titansInLane.clear();
		PriorityQueue<Titan> queue = lane.getTitans();
		for(Titan currTitan : queue) {
			titansInLane.add(new TitanViewHard(currTitan));
		}
		
		for(TitanViewHard currTitan : titansInLane) {
			int rndm = (int)Math.random()*70;
			currTitan.getTitanBox().setTranslateX(currTitan.getTitan().getDistance()*20+rndm);
			currTitan.getTitanBox().setTranslateY(-30);
			lanePane.getChildren().add(currTitan.getTitanBox());
			AnchorPane.setLeftAnchor(currTitan, 0.0);
		}
		
	}

	public Lane getLane() {
		return lane;
	}

	public ArrayList<TitanViewHard> getTitansInLane() {
		return titansInLane;
	}

	public AnchorPane getLanePane() {
		return lanePane;
	}

	public void setLanePane(AnchorPane lanePane) {
		this.lanePane = lanePane;
	}

	public HBox getFullLaneView() {
		return fullLaneView;
	}

	public void setFullLaneView(HBox fullLaneView) {
		this.fullLaneView = fullLaneView;
	}

	
	
}
