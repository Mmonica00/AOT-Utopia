package game.engine.interfaces;

public interface Attackee {

	int getCurrentHealth();
	void setCurrentHealth(int health);
	int getResourcesValue();

	default boolean isDefeated() {
		if(getCurrentHealth()<=0)
			return true;
		return false;
	}
	
	default int takeDamage(int damage) {
		int newHealth = getCurrentHealth()-damage;
		
		setCurrentHealth(newHealth);
		if(isDefeated())
			return getResourcesValue();
		return 0;
		
	}
}
