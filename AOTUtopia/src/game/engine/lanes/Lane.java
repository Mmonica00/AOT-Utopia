package game.engine.lanes;
import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane {
	private Wall laneWall;
	private int dangerLevel;
	private PriorityQueue<Titan> titans;
	private ArrayList<Weapon> weapons;
	
	public Lane(Wall laneWall) {
		super();
		this.laneWall = laneWall;
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
	
	public int compareTo(Lane o) {
		return (this.dangerLevel>o.dangerLevel)?1:-1;
	}
	
	
	
}
