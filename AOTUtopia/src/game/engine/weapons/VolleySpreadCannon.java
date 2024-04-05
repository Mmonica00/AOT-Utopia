package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon{
	
	public final static int WEAPON_CODE =3;
	private final int minRange;
	private final int maxRange;

	public VolleySpreadCannon(int baseDamage,int minRange,int maxRange ) {
		super(baseDamage);
		this.minRange=minRange;
		this.maxRange=maxRange;
	}
	public VolleySpreadCannon(WeaponRegistry weaponRegistry) {
		super(weaponRegistry);
		this.minRange = weaponRegistry.getMinRange();
		this.maxRange = weaponRegistry.getMaxRange();
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int totalResourcesGained=0;
		if(laneTitans.peek() != null) {
			Titan peekTitan = laneTitans.peek();
			if(peekTitan.getDistance()>=getMinRange() && peekTitan.getDistance()<=getMaxRange()) {
				Titan titanBeingAttacked = laneTitans.remove();
				totalResourcesGained=this.attack(titanBeingAttacked);
				if(!titanBeingAttacked.isDefeated())
					laneTitans.add(titanBeingAttacked);
			}
		}
		return totalResourcesGained;
	}

}
