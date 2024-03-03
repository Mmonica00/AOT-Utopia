package game.engine.weapons;

public class WallTrap extends Weapon{
	public final static int WEAPON_CODE =4;

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	@Override
	public int getDamage() {
		return super.getBaseDamage();
	}
	
}
