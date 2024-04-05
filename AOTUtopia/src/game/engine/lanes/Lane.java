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
	public void addTitan(Titan titan) {
		titans.add(titan);
	}
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public void moveLaneTitans() {
		int x=titans.size();
		for(int i=0;i<x;i++) {
			Titan temp=titans.remove();

			if(temp!=null) {
			temp.move();
			titans.add(temp);}
		}
	}
	public int performLaneTitansAttacks() {
		int x=titans.size();
		int resources=0;
		for(int i=0;i<x;i++) {
			Titan temp=titans.remove();

			if(temp!=null) {
			if(temp.hasReachedTarget()) {
				temp.attack(laneWall);
				resources=resources+ temp.getResourcesValue();
			}
			}
				
		}return resources;
		
		
	}
	public int performLaneWeaponsAttacks() {
		int x=weapons.size();
		int totalResourcesGained=0;
		int resourcesGained=0;
		for(int i=0;i<x;i++) {
			Weapon tempw=weapons.remove(i);
			if(tempw!=null) {
				if(tempw instanceof PiercingCannon) {
					PiercingCannon newWeapon = (PiercingCannon)tempw;
					resourcesGained=newWeapon.turnAttack(titans);
				}
				if(tempw instanceof SniperCannon) {
					SniperCannon newWeapon = (SniperCannon)tempw;
					resourcesGained=newWeapon.turnAttack(titans);
				}if(tempw instanceof WallTrap) {
					WallTrap newWeapon = (WallTrap)tempw;
					resourcesGained=newWeapon.turnAttack(titans);
				}if(tempw instanceof VolleySpreadCannon) {
					VolleySpreadCannon newWeapon = (VolleySpreadCannon)tempw;
					resourcesGained=newWeapon.turnAttack(titans);
				}
				totalResourcesGained+=resourcesGained;
			}
			weapons.add(tempw);

		}
		return totalResourcesGained;
		
		
	}
	public boolean isLaneLost() {
		return laneWall.isDefeated();
	}
	public void updateLaneDangerLevel() {
		int x= titans.size();
		int sumDangerLevel=0;
		for(int i=0; i<x; i++) {
			Titan temp= titans.remove();

			if(temp!=null) {
				sumDangerLevel+=temp.getDangerLevel();
				titans.add(temp);
			}
		}
		dangerLevel=sumDangerLevel;
	}


}
