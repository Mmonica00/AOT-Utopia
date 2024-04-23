package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

public class Battle {

	private static final int[][] PHASES_APPROACHING_TITANS = { { 1, 1, 1, 2, 1, 3, 4 }, { 2, 2, 2, 1, 3, 3, 4 },
			{ 4, 4, 4, 4, 4, 4, 4 } };
	private static final int WALL_BASE_HEALTH = 10000;
	private int numberOfTurns; // RW
	private int resourcesGathered; // RW - I
	private BattlePhase battlePhase; // RW - I
	private int numberOfTitansPerTurn; // RW - I
	private int score; // RW
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives; // read from Dataloader
	private final ArrayList<Titan> approachingTitans; // will be treated as a queue (FIFO)
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException {
		super();
		this.numberOfTurns = numberOfTurns;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<>(); // used as FIFO DS
		this.lanes = new PriorityQueue<>(); // Least dangerous lanes will have the highest priority
		this.originalLanes = new ArrayList<>();
		initializeLanes(initialNumOfLanes);
	}

	private void initializeLanes(int numOfLanes) {
		for (int i = 0; i < numOfLanes; i++) {

			Wall wall = new Wall(WALL_BASE_HEALTH);
			Lane lane = new Lane(wall);

			lanes.add(lane);
			originalLanes.add(lane);
		}

	}

	// Milestone 2 is below this comment

	public void refillApproachingTitans() {
		int phaseNum;

		switch (battlePhase) {
		case EARLY: phaseNum = 0; break;
		case INTENSE: phaseNum = 1; break;
		case GRUMBLING: phaseNum = 2; break;
		default: phaseNum = 0; break;
		}

		int[] thisPhaseTitans = PHASES_APPROACHING_TITANS[phaseNum];
		for (int i = 0; i < thisPhaseTitans.length; i++) {
			Titan tempTitan = getTitan(thisPhaseTitans[i]);
			tempTitan.setDistance(titanSpawnDistance);
			approachingTitans.add(tempTitan);
		}
	}

	public Titan getTitan(int code) { // helper to return titan given it's code
		TitanRegistry tempTitan = titansArchives.get(code);
		if (code == 1)
			return new PureTitan(tempTitan);
		if (code == 2)
			return new AbnormalTitan(tempTitan);
		if (code == 3)
			return new ArmoredTitan(tempTitan);
		if (code == 4)
			return new ColossalTitan(tempTitan);
		return null;
	}

	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
		if (lane == null || lane.isLaneLost() || !lanes.contains(lane)) // lanes.contains(lane)
			throw new InvalidLaneException();
		
		FactoryResponse factoryResponse = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
		lane.addWeapon(factoryResponse.getWeapon());
		resourcesGathered = factoryResponse.getRemainingResources();
		performTurn();
	}
	
	public void passTurn() {
		performTurn();
	}

	private void addTurnTitansToLane() {
		Lane currLane = lanes.remove();
		for (int i = 0; i < numberOfTitansPerTurn; i++) {
			if (approachingTitans.isEmpty())
				refillApproachingTitans();
			if (!currLane.isLaneLost())
				currLane.addTitan(approachingTitans.remove(0));
		}
		lanes.add(currLane);
	}

	private void finalizeTurns() {
		numberOfTurns++;
		if (numberOfTurns == 15)
			battlePhase = BattlePhase.INTENSE;
		else if (numberOfTurns == 30)
			battlePhase = BattlePhase.GRUMBLING;
		
		if (numberOfTurns > 30 && numberOfTurns % 5 == 0)
			numberOfTitansPerTurn = numberOfTitansPerTurn * 2;
	}

	private void performTurn() { 
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
	}

	public boolean isGameOver() {
		return lanes.isEmpty();
	}

	private void moveTitans() {
		for(Lane currLane : lanes) {
			currLane.moveLaneTitans();
		}
	}

	private int performWeaponsAttacks() {
		int accumlatedResources = 0;
		for(Lane currLane : lanes) {
			accumlatedResources+=currLane.performLaneWeaponsAttacks();
		}
		resourcesGathered += accumlatedResources;
		score += accumlatedResources;
		return accumlatedResources;
	}

	private void updateLanesDangerLevels() {
		//DON'T change logic of Looping hena ya monica :)
		
		PriorityQueue<Lane> tempPQ = new PriorityQueue<Lane>(); 
		
		while(!lanes.isEmpty()) {
			lanes.peek().updateLaneDangerLevel();
			tempPQ.add(lanes.poll());
		}
		while(!tempPQ.isEmpty()) {
			lanes.add(tempPQ.poll());
		}
	}

	private int performTitansAttacks() {
		int sumGainedResources = 0;
		ArrayList<Lane> lostLanes = new ArrayList<>();
		
		for (Lane currentLane : lanes) {
			sumGainedResources += currentLane.performLaneTitansAttacks();
			if(currentLane.isLaneLost())
				lostLanes.add(currentLane);
		}
		
		lanes.removeAll(lostLanes);
		return sumGainedResources;
	}


	// Getters & setters

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = Math.max(numberOfTurns, 0);
	}

	public int getResourcesGathered() {
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered) {
		this.resourcesGathered = Math.max(resourcesGathered, 0);
	}

	public BattlePhase getBattlePhase() {
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn() {
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
		this.numberOfTitansPerTurn = Math.max(numberOfTitansPerTurn, 0);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = Math.max(score, 0);
	}

	public int[][] getPHASES_APPROACHING_TITANS() {
		return PHASES_APPROACHING_TITANS;
	}

	public int getWALL_BASE_HEALTH() {
		return WALL_BASE_HEALTH;
	}

	public int getTitanSpawnDistance() {
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance) {
		this.titanSpawnDistance = Math.max(titanSpawnDistance, 0);
	}

	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives() {
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans() {
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes() {
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes() {
		return originalLanes;
	}

	
}
