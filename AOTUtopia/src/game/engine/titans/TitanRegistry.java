package game.engine.titans;

import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;

public class TitanRegistry {
	final private int code;
	private int baseHealth;
	private int baseDamage;
	private int heightInMeters;
	private int speed;
	private int resourcesValue;
	private int dangerLevel;

	public TitanRegistry(int code, int baseHealth, int baseDamage, int heightInMeters, int speed, int resourcesValue,
			int dangerLevel) {
		super();
		this.code = code;
		this.baseHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

	public int getCode() {
		return code;
	}
	public int getBaseHealth() {
		return baseHealth;
	}
	public int getBaseDamage() {
		return baseDamage;
	}
	public int getHeightInMeters() {
		return heightInMeters;
	}
	public int getSpeed() {
		return speed;
	}
	public int getResourcesValue() {
		return resourcesValue;
	}
	public int getDangerLevel() {
		return dangerLevel;
	}
	public Titan spawnTitan(int distanceFromBase) {
		Titan titan = null;
		switch(this.code) {
		case 1: titan = new PureTitan(this);
		case 2: titan = new AbnormalTitan(this);
		case 3: titan = new ArmoredTitan(this);
		case 4: titan = new ColossalTitan(this);
		}
		titan.setDistance(distanceFromBase);
		
		return titan; 
	}


}
