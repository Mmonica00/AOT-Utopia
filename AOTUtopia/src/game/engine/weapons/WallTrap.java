package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class WallTrap extends Weapon{
	
	public final static int WEAPON_CODE =4;

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) { //attacks first if reached the wall
		int totalResourcesGained=0;
		if(laneTitans.peek() != null) {
			Titan peekTitan = laneTitans.peek();
			if(peekTitan.getDistance()<=0) {
				Titan titanBeingAttacked = laneTitans.remove();
				totalResourcesGained=this.attack(titanBeingAttacked);
				if(!titanBeingAttacked.isDefeated())
					laneTitans.add(titanBeingAttacked);
			}
		}
		return totalResourcesGained;
	}

}
