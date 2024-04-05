package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
	
	public final static int WEAPON_CODE =2;

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}
	
	public SniperCannon(WeaponRegistry weaponRegistry) {
		super(weaponRegistry);
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int totalResourcesGained=0;
		if(laneTitans.peek() != null) {
			Titan titanBeingAttacked = laneTitans.remove();
			totalResourcesGained=this.attack(titanBeingAttacked);
			if(!titanBeingAttacked.isDefeated())
				laneTitans.add(titanBeingAttacked);
		}
		return totalResourcesGained;
	}
	
	

}
