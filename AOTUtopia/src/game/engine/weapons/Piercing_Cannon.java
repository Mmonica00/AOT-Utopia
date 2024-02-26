package game.engine.weapons;

public class Piercing_Cannon extends Weapon {
	private final int  WEAPON_CODE = 1;

	public Piercing_Cannon(int baseDamage) {
		super(baseDamage);
		
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}
	
}
