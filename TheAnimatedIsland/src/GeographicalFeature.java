
public interface GeographicalFeature {
	public int getX();
	public int getY();
	public char getSymbol();
	public Island getIsland();
	public void setIsland(Island island);
	public String toString();
}
