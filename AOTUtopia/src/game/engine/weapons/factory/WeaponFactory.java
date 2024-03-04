package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;

import game.engine.dataloader.DataLoader;
<<<<<<< HEAD
import game.engine.weapons.WeaponRegistry;
=======

>>>>>>> branch 'master' of https://github.com/rogereliass/AOT-Utopia.git
public class WeaponFactory {

	private final HashMap<Integer, WeaponRegistry> weaponShop;

	public HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return weaponShop;
	}

	public WeaponFactory() throws IOException{
		weaponShop = DataLoader.readWeaponRegistry();
	}

}
