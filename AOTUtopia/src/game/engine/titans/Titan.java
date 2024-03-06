package game.engine.titans;

import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;

public abstract class Titan implements Attacker,Attackee,Mobil{

	// R for READ ONLY , W for WRITE ONLY , F for FINAL WHEN INITIALIZED

	private final int baseHealth; //original health when spawned -F
	private int currentHealth; //current health -RW
	private final int baseDamage; //damage caused by titan -R
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

	@Override
	public int getCurrentHealth() {
		return currentHealth;
	}

	@Override
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = Math.max(0, currentHealth);
	}

	public int getDistanceFromBase() {
		return distanceFromBase;
	}

	@Override
	public int getDistance() {
		return distanceFromBase;
	}
	public void setDistanceFromBase(int distanceFromBase) {
		this.distanceFromBase = distanceFromBase;
	}

	@Override
	public void setDistance(int distance) {
		this.distanceFromBase=distance;
	}
	
	@Override
	public int getResourcesValue() {
		return resourcesValue;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public int getDamage() {
		return baseDamage;
	}

	


}
