package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.WeaponFactory;

public class Battle {
	
	private int[][] PHASES_APPROACHING_TITANS;
	private int WALL_BASE_HEALTH;
	private int numberOfTurns; //RW
	private int resourcesGathered; //RW - I
	private BattlePhase battlePhase; //RW - I
	private int numberOfTitansPerTurn; //RW - I
	private int score; //RW 
	private int titanSpawnDistance;
	private final  WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives; //read from Dataloader
	private final ArrayList<Titan> approachingTitans;
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;
	
	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException {
		super();
		this.PHASES_APPROACHING_TITANS= new int[10][10]; //FIXME: need to specify array size
		this.WALL_BASE_HEALTH = 10000;
		this.numberOfTurns = numberOfTurns;
		this.resourcesGathered = initialResourcesPerLane*initialNumOfLanes;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>(); //used as FIFO DS
		this.lanes = new PriorityQueue<Lane>(); //Least dangerous lanes will have the highest priority
		this.originalLanes = new ArrayList<Lane>();
	}
	
	private void initializeLanes(int numOfLanes) { //TODO: review this method
		
		for(int i=0;i<numOfLanes;i++) {
			
			Wall wall = new Wall(WALL_BASE_HEALTH);
			Lane lane = new Lane(wall);
			
			lanes.add(lane);
			originalLanes.add(lane);
		}
		
	}
	
	
	
	
}
