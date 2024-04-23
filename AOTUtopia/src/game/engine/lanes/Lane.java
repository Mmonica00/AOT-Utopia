package game.engine.lanes;

import java.util.ArrayList;
import game.engine.titans.Titan;
import java.util.PriorityQueue;
import game.engine.base.Wall;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.VolleySpreadCannon;

import game.engine.weapons.Weapon;
import game.engine.interfaces.Attackee;

public class Lane implements Comparable<Lane> {
	final private Wall laneWall; // A wall object found in the lane.
	private int dangerLevel = 0; // danger level of a lane based on the number and danger level of the titans on
									// it.
	private final PriorityQueue<Titan> titans; // A queue that stores all the titans in a given lane
	private final ArrayList<Weapon> weapons; // A queue that stores all the weapons in a given lane

	public Lane(Wall laneWall) {
		super();
		this.laneWall = laneWall;
		this.titans = new PriorityQueue<Titan>();
		this.weapons = new ArrayList<Weapon>();
	}
	
	public void moveLaneTitans() {
		PriorityQueue<Titan> tempPQ = new PriorityQueue<Titan>();
		while (!titans.isEmpty()) {
			Titan titanMoving = titans.remove();
			if (!titanMoving.hasReachedTarget())
				titanMoving.move();
			tempPQ.add(titanMoving);

		}
		while (!tempPQ.isEmpty()) {
			titans.add(tempPQ.remove());
		}

	}

	public int performLaneTitansAttacks() {
		int numOfTitans = titans.size();
		int totalResources = 0;
		for (Titan t : titans) {
			if(t.hasReachedTarget())
				totalResources += t.attack(laneWall);
			
		}
		return totalResources;
	}

	public int performLaneWeaponsAttacks() {
		int totalResourcesGained = 0;
		for (Weapon currentWeapon : weapons) {
			if (currentWeapon != null) 
				totalResourcesGained += currentWeapon.turnAttack(titans);
		}
		return totalResourcesGained;
	}

	public boolean isLaneLost() {
		return laneWall.isDefeated();
	}

	public void updateLaneDangerLevel() {
		int sumDangerLevel = 0;
		for(Titan currentTitan : titans) {
			sumDangerLevel+= currentTitan.getDangerLevel();
		}
		this.dangerLevel = sumDangerLevel;
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
		return this.dangerLevel - o.dangerLevel;

	}

	public void addTitan(Titan titan) {
		titans.add(titan);
	}

	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	

}
