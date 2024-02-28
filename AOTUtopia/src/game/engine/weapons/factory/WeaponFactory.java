package game.engine.weapons.factory;
import java.io.IOException;
import java.util.HashMap;
import game.engine.weapons.WeaponRegistry;
import game.engine.dataloader.DataLoader;
public class WeaponFactory {
	private final HashMap<Integer, WeaponRegistry> weaponShop; //FIXME:This attribute is initialized once the data is read and is READ ONLY 
	
	WeaponFactory() throws IOException{
		weaponShop = DataLoader.readWeaponRegistry();
	}
	
}
