package game.engine.titans;

public abstract class Titan {

	// R for READ ONLY , W for WRITE ONLY , F for FINAL WHEN INITIALIZED	
	
	private int baseHealth; //original health when spawned -F
	private int currentHealth; //current health -RW
	private int baseDamage; //damage caused by titan -R
	private int heightInMeters; //titan's height 
	private int distanceFromBase; //distance of titan from wall -RW
	private int speed; //distance of titan moved each turn 
	private int resourcesValue; //amount of resources gained by defeating titan
	private int dangerLevel; //smaller the value, less dangerous titan
	
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
}
