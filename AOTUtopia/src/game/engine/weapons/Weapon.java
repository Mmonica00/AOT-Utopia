package game.engine.weapons;

public abstract class Weapon {
	private int baseDamage;

	public final int getBaseDamage() {
		return baseDamage;
	}

	public Weapon(int baseDamage) {
		super();
		this.baseDamage = baseDamage;
	}
	
}
