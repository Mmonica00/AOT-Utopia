package game.gui;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LaneController {

	Lane lane;
	ArrayList<TitanView> titansInLane = new ArrayList<TitanView>();
	WallView wallView  = new WallView(lane.getLaneWall()); 
	WeaponLaneView weaponLane = new WeaponLaneView(lane.getWeapons());
	AnchorPane lanePane;
	private ImageView lostLane;
	
	
	public LaneController(Lane lane) {
		
		this.lane=lane;
		lanePane.getChildren().add(wallView);
		AnchorPane.setLeftAnchor(wallView, 0.0);
		//Image laneImg = new Image(getClass().getResourceAsStream("wall.png"));
        //lostLane = new ImageView(laneImg);
	}

	public void refreshLane(Lane newLane) {
		this.lane = newLane;
		
		if(lane==null || lane.isLaneLost()) {
			//lanePane.getChildren().add(lostLane);
			
			
			
		} else {
			WallView.updateWall(lane.getLaneWall());
			weaponLane = new WeaponLaneView(lane.getWeapons());
			updateTitansViews();		
		}
	}

	public void updateTitansViews() {
		titansInLane.clear();
		PriorityQueue<Titan> queue = lane.getTitans();
		for(Titan currTitan : queue) {
			titansInLane.add(new TitanView(currTitan));
		}
		for(TitanView currTitan : titansInLane) {
			currTitan.moveTitan();
		}
	}

	public Lane getLane() {
		return lane;
	}

	public ArrayList<TitanView> getTitansInLane() {
		return titansInLane;
	}
			
	
	
}
