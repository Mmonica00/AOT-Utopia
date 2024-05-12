package game.gui;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.lanes.Lane;
import game.engine.titans.Titan;

public class LaneController {

	ArrayList<TitanView> titansInLane = new ArrayList<TitanView>();
	//WallView 
	//WeaponView
	//Pane
	
	
	public LaneController() {
		//setup wall
	}

	public void refreshLane(Lane lane) {
		if(lane.isLaneLost()) {
			//ne2fel el wall khaless graphic
			
			
			
		} else {
			//update wall view 
			//update weapon view
			
			
			updateTitansViews(lane);		
		}
	}

	public void updateTitansViews(Lane lane) {
		titansInLane.clear();
		PriorityQueue<Titan> queue = lane.getTitans();
		for(Titan currTitan : queue) {
			titansInLane.add(new TitanView(currTitan));
		}
		for(TitanView currTitan : titansInLane) {
			currTitan.moveTitan();
		}
	}
			
}
