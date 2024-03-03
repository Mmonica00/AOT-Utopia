package game.engine.weapons.factory;
import java.io.IOException;
import java.util.HashMap;
import game.engine.weapons.WeaponRegistry;
import game.engine.dataloader.DataLoader;
public class WeaponFactory {
	
	private final HashMap<Integer, WeaponRegistry> weaponShop; 
<<<<<<< HEAD

=======
	
	public HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return weaponShop;
	}
>>>>>>> branch 'master' of https://github.com/rogereliass/AOT-Utopia.git

	public WeaponFactory() throws IOException{
		weaponShop = DataLoader.readWeaponRegistry();
	}
	
}
