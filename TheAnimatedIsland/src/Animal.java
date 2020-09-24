public interface Animal {

	// move
	public boolean move(double direction);
	
	// locate
	public int getGridX();
	public int getGridY();
	
	// getters
	public char getSymbol();
	public int getEnergy();
	public int getHydration();
	public boolean isDead();
	
	// increase and decrease energy and hydration. overloaded methods, take optional energy increase/decrase argument
	public void increaseEnergy();
	public void decreaseEnergy();
	public void increaseEnergy(int energy);
	public void decreaseEnergy(int energy);
	public void increaseHydration();
	public void decreaseHydration();
	public void increaseHydration(int hydration);
	public void decreaseHydration(int hydration);
	
	// switch animal is dead flag
	public void setDead(boolean isDead);

	public String toString();
	public void setIsland(Island island);
	
	// feeding and hunger
	public boolean isHungry();
	public boolean isThirsty();
	public boolean feedSelf();
	public boolean seekFood();
	public boolean seekWater();
	public boolean drinkWater();

}
