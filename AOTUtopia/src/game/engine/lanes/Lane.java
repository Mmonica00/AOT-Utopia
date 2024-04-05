package game.engine.lanes;
import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane>{
	final private Wall laneWall; //A wall object found in the lane.
	private int dangerLevel; //danger level of a lane based on the number and danger level of the titans on it.
	private final PriorityQueue<Titan> titans; //A queue that stores all the titans in a given lane
	private final ArrayList<Weapon> weapons; //A queue that stores all the weapons in a given lane

	public Lane(Wall laneWall) {
		super();
		this.laneWall = laneWall;
		this.titans= new PriorityQueue<Titan>();
		this.weapons= new ArrayList<Weapon>();
	}

	public PriorityQueue<Titan> getTitans() {
		return titans;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setDangerLevel(int dangerLevel) {
		this.dangerLevel = dangerLevel;
	}


	public Wall getLaneWall() {
		return laneWall;
	}
	@Override
	public int compareTo(Lane o) {
		int temp= this.dangerLevel - o.dangerLevel;
		return (temp<0)?-1:(temp>0)?1:0;
	}
	
	
	


}
