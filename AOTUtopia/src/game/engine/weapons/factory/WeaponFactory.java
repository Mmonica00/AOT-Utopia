package game.engine.weapons.factory;
import java.io.IOException;
import java.util.HashMap;
import game.engine.weapons.WeaponRegistry;
import game.engine.dataloader.DataLoader;
public class WeaponFactory {
	
	private final HashMap<Integer, WeaponRegistry> weaponShop; 
	
	public WeaponFactory() throws IOException{
		weaponShop = DataLoader.readWeaponRegistry();
	}
	
	public static void main(String[] args) throws IOException {
		WeaponFactory w = new WeaponFactory();
		System.out.println(w);
	}
}
