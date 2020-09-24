
public interface GeographicalFeature {
	public int getGridX();
	public int getGridY();
	public char getSymbol();
	public Island getIsland();
	public void setIsland(Island island);
	public String toString();
}
