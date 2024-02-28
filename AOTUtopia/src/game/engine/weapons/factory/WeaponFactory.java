package game.engine.weapons.factory;
import java.io.IOException;
import java.util.HashMap;
import game.engine.weapons.WeaponRegistry;

public class WeaponFactory {
	private HashMap<Integer, WeaponRegistry> weaponShop; //FIXME:This attribute is initialized once the data is read and is READ ONLY 
	
	WeaponFactory() throws IOException{
		
	}
}
