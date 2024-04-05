package game.engine.weapons;

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

}
