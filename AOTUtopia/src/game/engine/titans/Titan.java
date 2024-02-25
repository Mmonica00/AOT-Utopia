package game.engine.titans;

public abstract class Titan {

	// R for READ ONLY , W for WRITE ONLY , F for FINAL WHEN INITIALIZED	
	
	private final int baseHealth; //original health when spawned -F
	private int currentHealth; //current health -RW
	private int baseDamage; //damage caused by titan -R
	private final int heightInMeters; //titan's height 
	private int distanceFromBase; //distance of titan from wall -RW
	private int speed; //distance of titan moved each turn -RW
	private final int resourcesValue; //amount of resources gained by defeating titan
	private final int dangerLevel; //smaller the value, less dangerous titan
	
	public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int
			speed, int resourcesValue, int dangerLevel) {
		this.baseHealth=baseHealth;
		this.currentHealth=baseHealth;
		this.baseDamage=baseDamage;
		this.heightInMeters=heightInMeters;
		this.distanceFromBase=distanceFromBase;
		this.speed=speed;
		this.resourcesValue=resourcesValue;
		this.dangerLevel=dangerLevel;
	}
	
	public int compareTo(Titan o) {
		int temp=this.distanceFromBase-o.distanceFromBase;
		return (temp<0)?-1:(temp>0)?1:0;
	}
	
	//Getters & Setters below 
	
	public int getBaseHealth() {
		return baseHealth;
	}

	public int getHeightInMeters() {
		return heightInMeters;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getDistanceFromBase() {
		return distanceFromBase;
	}

	public void setDistanceFromBase(int distanceFromBase) {
		this.distanceFromBase = distanceFromBase;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public int getResourcesValue() {
		return resourcesValue;
	}
	
	
}
