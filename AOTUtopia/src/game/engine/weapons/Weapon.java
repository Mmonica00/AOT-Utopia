package game.engine.weapons;

import game.engine.interfaces.Attacker;

public abstract class Weapon implements Attacker {

	private final int baseDamage;

	public int getBaseDamage() {
		return baseDamage;
	}

	public Weapon(int baseDamage) {
		super();
		this.baseDamage = baseDamage;
	}

}
