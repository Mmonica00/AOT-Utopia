package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;


public class WeaponFactory {

	private final HashMap<Integer, WeaponRegistry> weaponShop;

	public HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return weaponShop;
	}

	public WeaponFactory() throws IOException{
		weaponShop = DataLoader.readWeaponRegistry();
	}
	
	public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException{
		WeaponRegistry weaponRegistry = weaponShop.get(weaponCode);
		
		if (resources>weaponRegistry.getPrice()) {
			return new FactoryResponse(weaponRegistry.buildWeapon(),resources-weaponRegistry.getPrice());
		}
		else {
			throw new InsufficientResourcesException(resources);
		}
			
	}
	
	public void addWeaponToShop(int code, int price) {
		WeaponRegistry weaponRegistry = new WeaponRegistry(code,price);
		weaponShop.put(code, weaponRegistry);
	}
	public void addWeaponToShop(int code, int price, int damage, String name) {
		WeaponRegistry weaponRegistry = new WeaponRegistry(code,price,damage,name);
		weaponShop.put(code, weaponRegistry);
	}
	public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange) {
		WeaponRegistry weaponRegistry = new WeaponRegistry(code,price,damage,name,minRange,maxRange);
		weaponShop.put(code, weaponRegistry);
	}
}
