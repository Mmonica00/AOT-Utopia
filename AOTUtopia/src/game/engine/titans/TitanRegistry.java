package game.engine.titans;

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
	
	
}
