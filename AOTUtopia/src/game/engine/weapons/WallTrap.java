package game.engine.weapons;

public class WallTrap extends Weapon{
	
	public final static int WEAPON_CODE =4;

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}
	
	public WallTrap(WeaponRegistry weaponRegistry) {
		super(weaponRegistry);
		
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

}
