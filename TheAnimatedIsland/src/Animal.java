public interface Animal {

	// move
	public void move(double direction);
	
	// locate
	public int getX();
	public int getY();
	
	// getters
	public char getSymbol();
	public int getEnergy();
	public int getHydration();
	
	// increase and decrease energy and hydration. overloaded methods, take optional energy increase/decrase argument
	public void increaseEnergy();
	public void decreaseEnergy();
	public void increaseEnergy(int energy);
	public void decreaseEnergy(int energy);
	public void increaseHydration();
	public void decreaseHydration();
	public void increaseHydration(int hydration);
	public void decreaseHydration(int hydration);

	public String toString();
	public void setIsland(Island island);
	
	// feeding and hunger
	public boolean isHungry();
	public boolean isThirsty();
	public boolean feedSelf();
	public void seekFood();
	public boolean drinkWater();

}
