package game.engine.weapons;

public abstract class Weapon {
	private final int baseDamage;

	public int getBaseDamage() {
		return baseDamage;
	}

	public Weapon(int baseDamage) {
		super();
		this.baseDamage = baseDamage;
	}
	
}
