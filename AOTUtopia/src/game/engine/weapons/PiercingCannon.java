package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {
	
	public final static int  WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage) {
		super(baseDamage);

	}
	

	public PiercingCannon(WeaponRegistry weaponRegistry) {
		super(weaponRegistry);
	}


	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) { //attacks first 5 titans
		int totalResourcesGained=0;
		for(int i=0;i<=4;i++) {
			if(laneTitans.peek() != null) {
				Titan titanBeingAttacked = laneTitans.remove();
				totalResourcesGained+=this.attack(titanBeingAttacked);
				if(!titanBeingAttacked.isDefeated())
					laneTitans.add(titanBeingAttacked);
			}
		}
		return totalResourcesGained;
	}

}
