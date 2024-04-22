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
		PriorityQueue<Titan> tempPQ= new PriorityQueue<Titan>();
		while(!tempPQ.isEmpty()){
			Titan titanMoving=titans.remove();
			if(!titanMoving.hasReachedTarget())
				titanMoving.move();
			tempPQ.add(titanMoving);
			
		}
		while(!tempPQ.isEmpty()) {
			titans.add(tempPQ.remove());
		}
		
	}
	
	public int performLaneTitansAttacks() {
		int numOfTitans=titans.size();
		int totalResources=0;
		for(int i=0;i<numOfTitans;i++) {
			Titan titanAttacking=titans.remove();
			if(titanAttacking!=null) {
				if(titanAttacking.hasReachedTarget()) 
					totalResources+= titanAttacking.attack(laneWall);
			}
		}
		return totalResources;
	}
	
	public int performLaneWeaponsAttacks() {
		int numOfWeapons=weapons.size();
		int totalResourcesGained=0;
		int resourcesGained=0;
		
		for(int i=0;i<numOfWeapons;i++) {
			Weapon weaponAttacking=weapons.remove(i); //logic of looping should be corrected
			
			if(weaponAttacking!=null) {
				if(weaponAttacking instanceof PiercingCannon) {
					PiercingCannon newWeapon = (PiercingCannon)weaponAttacking;
					resourcesGained=newWeapon.turnAttack(titans);
				}
				if(weaponAttacking instanceof SniperCannon) {
					SniperCannon newWeapon = (SniperCannon)weaponAttacking;
					resourcesGained=newWeapon.turnAttack(titans);
				}
				if(weaponAttacking instanceof WallTrap) {
					WallTrap newWeapon = (WallTrap)weaponAttacking;
					resourcesGained=newWeapon.turnAttack(titans);
				}
				if(weaponAttacking instanceof VolleySpreadCannon) {
					VolleySpreadCannon newWeapon = (VolleySpreadCannon)weaponAttacking;
					resourcesGained=newWeapon.turnAttack(titans);
				}
				totalResourcesGained+=resourcesGained;
			}
			weapons.add(i,weaponAttacking);

		}
		return totalResourcesGained;
	}
	
	public boolean isLaneLost() {
		return laneWall.isDefeated();
	}
	
	public void updateLaneDangerLevel() {
		int numOfTitans= titans.size();
		int sumDangerLevel=0;
		
		for(int i=0; i<numOfTitans; i++) {
			Titan currentTitan= titans.remove();
			sumDangerLevel+=currentTitan.getDangerLevel();
			titans.add(currentTitan);
		}
		this.dangerLevel=sumDangerLevel;
	}

}
