package game.gui;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.lanes.Lane;
import game.engine.titans.Titan;

public class LaneController {

	Lane lane;
	ArrayList<TitanView> titansInLane = new ArrayList<TitanView>();
	//WallView 
	//WeaponView
	//Pane
	
	
	public LaneController(Lane lane) {
		
		//setup wall view
		this.lane=lane;
	}

	public void refreshLane(Lane newLane) {
		this.lane = newLane;
		
		if(lane==null || lane.isLaneLost()) {
			//ne2fel el wall khaless graphic
			
			
			
		} else {
			//update wall view 
			//update weapon view
			
			
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
