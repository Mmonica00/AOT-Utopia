package game.engine.weapons;

import game.engine.interfaces.Attacker;

public abstract class Weapon implements Attacker {

	private final int baseDamage; //damage made by the weapon on others

	public Weapon(int baseDamage) {
		super();
		this.baseDamage = baseDamage;
	}

	@Override
	public int getDamage() {
		return baseDamage;
	}
	public Weapon(WeaponRegistry weaponRegistry) {
		super();
		this.baseDamage = weaponRegistry.getDamage();
	}

}
