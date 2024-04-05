package game.engine.titans;

public class ArmoredTitan extends Titan{

	public final static int TITAN_CODE = 3;

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	

	public ArmoredTitan(TitanRegistry registry) {
		super(registry);
		
	}


	public int getTITAN_CODE() {
		return TITAN_CODE;
	}

	@Override
	public int takeDamage(int damage) {
		return super.takeDamage((int)(0.25*damage));
	}

	
}
