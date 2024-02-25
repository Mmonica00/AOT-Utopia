package game.engine.titans;

public class ColossalTitan extends Titan {
	
	final private int TITAN_CODE = 4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

	public int getTITAN_CODE() {
		return TITAN_CODE;
	}

}
