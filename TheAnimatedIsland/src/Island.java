import java.util.ArrayList;

public class Island {
	// height and width in number of grid squares
	private int width;
	private int height;
	// length of each grid square
	private int gridSize;

	// fields for storing and animals and geographicalFeatures.
	ArrayList<Animal> animals = new ArrayList<Animal>();
	ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>();
	ArrayList<Kiwi> kiwis = new ArrayList<Kiwi>();
	ArrayList<GeographicalFeature> geographicalFeatures = new ArrayList<GeographicalFeature>();
	ArrayList<Grass> grasses = new ArrayList<Grass>();
	ArrayList<Water> waters = new ArrayList<Water>();

	// constructor taking width and height
	public Island(int width, int height, int gridSize) {
		this.width = width;
		this.height = height;
		this.gridSize = gridSize;
	}

	// check if a grass patch is the coordinate in argument and return the Grass object if so
	public Grass hasGrass(int x, int y) {

		// Grass to return
		Grass grass = null;

		// loop through all grass patches
		for (Grass g : grasses) {

			// check if grass' position matches input arguments
			if (g.getGridX() == x && g.getGridY() == y) {

				// set grass in variable and break out of loop
				grass = g;
				break;
			}
		}

		// return grass
		return grass;
	}

	// check if there is water at a certain coordinate and return a boolean
	// indicating so.
	public boolean hasWater(int x, int y) {

		// boolean to return
		boolean hasWater = false;

		// loop through all water patches
		for (Water w : waters) {

			// check if animal's position matches input arguments
			if (w.getGridX() == x && w.getGridY() == y) {

				// set boolean to true and break out of loop
				hasWater = true;
				break;
			}
		}

		// return boolean
		return hasWater;
	}

	// check if any other animal is residing in a certain position and return a
	// boolean indicating so.
	public boolean isOccupied(int x, int y) {

		// boolean to return
		boolean isOccupied = false;

		// loop through all animals
		for (Animal b : animals) {

			// check if animal's position matches input arguments
			if (b.getGridX() == x && b.getGridY() == y) {

				// set boolean to true and break out of loop
				isOccupied = true;
				break;
			}
		}

		// return boolean
		return isOccupied;
	}

	// check if any other geographical feature is in a certain position and return a
	// boolean indicating so.
	public boolean hasGeographicalFeature(int x, int y) {

		// boolean to return
		boolean hasFeature = false;

		// loop through all features
		for (GeographicalFeature g : geographicalFeatures) {

			// check if feature's position matches input arguments
			if (g.getGridX() == x && g.getGridY() == y) {

				// set boolean to true and break out of loop
				hasFeature = true;
				break;
			}
		}

		// return boolean
		return hasFeature;
	}

	// randomly generate a specified number of patches of water
	public void genWater(int numWater) {

		for (int i = 0; i < numWater; i++) {

			// randomly pick coordinates within island boundaries
			int x = (int) (Math.random() * this.width);
			int y = (int) (Math.random() * this.height);

			// check position is not already occupied and try again until one is found. or,
			// give up after 20 tries
			int count = 0;
			while ((isOccupied(x, y) || hasGeographicalFeature(x, y)) && count < 20) {
				x = (int) (Math.random() * width);
				y = (int) (Math.random() * height);
				count++;
			}
			// create water
			Water water = new Water(x, y, gridSize);

			// set island on water to this island
			water.setIsland(this);

			// add water to list of geographical features and water patches
			this.geographicalFeatures.add(water);
			this.waters.add(water);
		}
	}

	// randomly generate a specified number of patches of grass
	public void genGrass(int numGrass) {

		for (int i = 0; i < numGrass; i++) {

			// randomly pick a size between 0 and 9
			int size = (int) (Math.random() * 9);

			// randomly pick coordinates within island boundaries
			int x = (int) (Math.random() * this.width);
			int y = (int) (Math.random() * this.height);

			// check position is not already occupied and try again until one is found. or,
			// give up after 20 tries
			int count = 0;
			while ((isOccupied(x, y) || hasGeographicalFeature(x, y)) && count < 20) {
				x = (int) (Math.random() * width);
				y = (int) (Math.random() * height);
				count++;
			}
			// create grass
			Grass grass = new Grass(size, x, y, gridSize);

			// set island on grass to this island
			grass.setIsland(this);

			// add grass to list of geographical features and grass patches
			this.geographicalFeatures.add(grass);
			this.grasses.add(grass);
		}
	}

	// randomly generate a specified number of animals
	public void genAnimals(int numAnimals) {

		for (int i = 0; i < numAnimals; i++) {

			// randomly pick position. note that positions run from 0 to width - 1 and 0 to
			// height - 1
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);

			// check position is not already occupied and try again until one is found. or,
			// give up after 20 tries
			int count = 0;
			while (isOccupied(x, y) && count < 20) {
				x = (int) (Math.random() * width);
				y = (int) (Math.random() * height);
				count++;
			}

			// randomly pick energy level from 25 through 100.
			int energy = (int) (Math.random() * 25 + 75);

			// randomly choose the animal, create the animal and add to lists of animals
			double rand = Math.random();

			if (rand < 0.5) {
				Rabbit rabbit = new Rabbit(x, y, energy, gridSize);
				rabbits.add(rabbit);
				rabbit.setIsland(this);
				animals.add(rabbit);
			} else if (rand < 1) {
				Kiwi kiwi = new Kiwi(x, y, energy, gridSize);
				kiwis.add(kiwi);
				kiwi.setIsland(this);
				animals.add(kiwi);
			}
		}
	}

	// print list of animals
	public void printAnimalInfo() {
		for (Animal a : this.animals) {
			System.out.println(a.toString());
		}
	}

	// print list of geographicalFeatures
	public void printGeographicalFeatureInfo() {
		for (GeographicalFeature p : this.geographicalFeatures) {
			System.out.println(p.toString());
		}
	}

	// print out text rendering of island including all animals at their locations
	// as represented by their symbols.
	public void drawIsland() {

		// draw top border
		System.out.print('|');
		for (int i = 0; i < width; i++) {
			System.out.print('-');
		}
		System.out.println('|');

		// draw each row. using y as counter to match coordinate position
		for (int y = 0; y < height; y++) {

			// draw left border
			System.out.print('|');

			// iterate through the columns
			for (int x = 0; x < width; x++) {

				// flag whether this position is occupied
				boolean hasObject = false;

				// loop through all animals to check if position matches
				for (Animal b : this.animals) {

					// check if animal is in this position. at most one animal will be as we are
					// preventing multiple objects from occupying the same position
					if (b.getGridX() == x && b.getGridY() == y) {

						// set flag to true
						hasObject = true;

						// draw animal's symbol
						System.out.print(b.getSymbol());

						// break out of checking animals loop
						break;
					}
				}

				// if a animal is not in this position,
				if (!hasObject) {

					// loop through all geographicalFeatures to check if position matches
					for (GeographicalFeature p : this.geographicalFeatures) {

						// check if animal is in this position. at most one animal will be as we are
						// preventing multiple objects from occupying the same position
						if (p.getGridX() == x && p.getGridY() == y) {

							// set flag to true
							hasObject = true;

							// draw geographicalFeature's symbol
							System.out.print(p.getSymbol());

							// break out of checking geographical features loop
							break;
						}
					}
				}

				// if no animal or geographical feature was in this position, print an empty
				// space
				if (!hasObject) {
					System.out.print(" ");
				}

			}

			// draw right border and move onto the next line
			System.out.printf("|%n");
		}

		// draw bottom border
		System.out.print('|');
		for (int i = 0; i < width; i++) {
			System.out.print('-');
		}
		System.out.println('|');

	}

	// randomly moves (or doesn't move) all the animals in the island. if an animal
	// tries to move to a spot occupied by another animal, the move method will put
	// it back in its original position and no move will occur.
	public void updateIsland() {

		// list of animals that die this turn
		ArrayList<Animal> deadAnimals = new ArrayList<Animal>();

		// loop over all animals
		for (Animal a : this.animals) {

			// check if animal is hungry
			if (a.isHungry()) {
				
				// if animal is hungry, have them check for an immediate food source and spend this turn feeding if so. if not at a food source, the will animal seek food and move in the direction of nearby food, or move randomly if no food is detected nearby.
				a.seekFood();

			// check if animal is thirsty
			} else if (a.isThirsty()) {
				
				// if the animal is not at a water source,
				if (!a.drinkWater()) {
					
					// attempt to move toward a water source, or move randomly if no nearby water is detected
					a.seekWater();
					
				} else {
					// if the animal is by a water source, drink that water
					a.drinkWater();
				}
				
			} else {
				// have the animal attempt to move in a random direction until a movement happens (the direction to move in isn't occupied by another animal) up to 20 tries
				boolean movement = a.move(Math.random());
				int count = 0;
				while (movement == false && count < 20) {
					movement = a.move(Math.random());
					count++;
				}
				System.out.println(a.getSymbol() + " is moving randomly");
			}

			// reduce all animal's energy and hydration level the default amount
			a.decreaseEnergy();
			a.decreaseHydration();

			// if the animal's energy or hydration has reduced to 0, add to list of dead.
			if (a.getEnergy() == 0 || a.getHydration() == 0) {
				deadAnimals.add(a);
			}
		}

		// remove dead animals from island (no feeding on dead bodies here...)
		for (Animal d : deadAnimals) {
			d.setDead(true);
			this.animals.remove(d);
		}

		// list of plants to remove if they've been eaten away. just grass for now
		ArrayList<Grass> deadGrass = new ArrayList<Grass>();

		for (Grass g : grasses) {
			// check if any grass patches have had their size reduce to 0 from being eaten
			if (g.getSize() == 0) {
				deadGrass.add(g);
				g.setDead(true);
			} else {
				// otherwise, grow that grass
				g.increaseSize();
			}
		}

	}

	// run animation of island, updating and redrawing a specified number of times.
	// update island with a thread sleep in between
	public void animateIsland(int numUpdates) {
		for (int i = 0; i < numUpdates; i++) {
			this.updateIsland();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.drawIsland();
		}
	}

	// Getters and Setters. Note no setter for animals arrayList but has addAnimal and removeAnimal methods instead

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<Animal> getAnimals() {
		return animals;
	}

	public void addAnimal(Animal animal) {
		// check incoming animal's position is not already occupied before adding to island
		if (!isOccupied(animal.getGridX(), animal.getGridY())) {
			this.animals.add(animal);
		}
	}

	public void removeAnimal(Animal animal) {
		this.animals.remove(animal);
	}

	public ArrayList<Rabbit> getRabbits() {
		return rabbits;
	}

	public ArrayList<Kiwi> getKiwis() {
		return kiwis;
	}

	public ArrayList<Grass> getGrasses() {
		return grasses;
	}
	
	public ArrayList<Water> getWaters() {
		return waters;
	}

	public void reportNumAnimals() {
		// tally up the number of each surviving animal and print.
		int numRabbits = 0;
		int numKiwis = 0;
		for (Animal a : animals) {
			if (a instanceof Rabbit) {
				numRabbits++;
			} else if (a instanceof Kiwi) {
				numKiwis++;
			}
		}
		System.out.println("There are " + numRabbits + " surviving rabbits.");
		System.out.println("There are " + numKiwis + " surviving kiwis.");
	}

}
