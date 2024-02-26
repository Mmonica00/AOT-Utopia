package game.engine.weapons;

public class Wall_Trap extends Weapon{
	private final int WEAPON_CODE =4;

	public Wall_Trap(int baseDamage) {
		super(baseDamage);
	}

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}
	
}
