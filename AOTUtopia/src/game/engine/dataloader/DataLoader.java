package game.engine.dataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

public class DataLoader {

	private static final String TITANS_FILE_NAME = "titans.csv";
	private static final String WEAPONS_FILE_NAME = "weapons.csv";
	
	public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException{
		HashMap<Integer, TitanRegistry> titanRegistryMap = new HashMap<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(TITANS_FILE_NAME))){
			String line;
			while((line = reader.readLine()) != null) {
				String[] data = line.split(",");
                int code = Integer.parseInt(data[0]);
                int baseHealth = Integer.parseInt(data[1]);
                int baseDamage = Integer.parseInt(data[2]);
                int heightInMeters = Integer.parseInt(data[3]);
                int speed = Integer.parseInt(data[4]);
                int resourcesValue = Integer.parseInt(data[5]);
                int dangerLevel = Integer.parseInt(data[6]);
                
                TitanRegistry titanregistry = new TitanRegistry(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel);
                titanRegistryMap.put(code, titanregistry);
			}
		} catch(IOException e) {
			System.out.println("Invalid Titan Input");
		}
		
		return titanRegistryMap;
	}
	
	public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException {
	    HashMap<Integer, WeaponRegistry> weaponRegistryMap = new HashMap<>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(WEAPONS_FILE_NAME))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            int code = Integer.parseInt(data[0]);
	            int price = Integer.parseInt(data[1]);
	            int damage = Integer.parseInt(data[2]);
	            String name = data[3];
	            WeaponRegistry weaponRegistry;

	            if (data.length == 6) {
	                int minRange = Integer.parseInt(data[4]);
	                int maxRange = Integer.parseInt(data[5]);
	                weaponRegistry = new WeaponRegistry(code, price, damage, name, minRange, maxRange);
	            } else {
	                weaponRegistry = new WeaponRegistry(code, price, damage, name);
	            }
	            
	            weaponRegistryMap.put(code, weaponRegistry);
	        }
	    }catch(IOException e) {
			System.out.println("Invalid Weapon Input");
		}
	    
	    return weaponRegistryMap;
	}
	
	
	
}