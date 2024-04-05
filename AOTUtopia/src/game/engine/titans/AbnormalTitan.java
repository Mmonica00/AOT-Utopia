package game.engine.titans;

import game.engine.interfaces.Attackee;

public class AbnormalTitan extends Titan {

	public final static int TITAN_CODE = 2;

	public AbnormalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	

	public AbnormalTitan(TitanRegistry registry) {
		super(registry);
		
	}


	public int getTITAN_CODE() {
		return TITAN_CODE;
	}

	@Override
	public int attack(Attackee target) {
		int resourcesGained = super.attack(target);
		if(!target.isDefeated())
			resourcesGained+=super.attack(target);
		return resourcesGained;
	}

	
}
