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
		PriorityQueue<Titan> tempPQ = new PriorityQueue<Titan>();
		while(!laneTitans.isEmpty()) {
			Titan peekTitan = laneTitans.remove();
			if(peekTitan.getDistance()>=getMinRange() && peekTitan.getDistance()<=getMaxRange()) 
				totalResourcesGained+=this.attack(peekTitan);
			if(!peekTitan.isDefeated())
				tempPQ.add(peekTitan);
			
		}
		while(!tempPQ.isEmpty())
			laneTitans.add(tempPQ.remove());
		return totalResourcesGained;
	}

}
