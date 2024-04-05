package game.engine.titans;

public class ColossalTitan extends Titan {

	public final static int TITAN_CODE = 4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	

	public ColossalTitan(TitanRegistry registry) {
		super(registry);
		
	}


	public int getTITAN_CODE() {
		return TITAN_CODE;
	}

	@Override
	public boolean move() {
		boolean temp = super.move();
		this.setSpeed(getSpeed()+1);
		return temp;
	}

	
}
