package game.engine.weapons;

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

}
