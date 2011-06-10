package org.galaxy;

public class PlanetClass {
	private int id;
	private int size;
	private float growth;
	private float initialEnergy;
	private float maxEnergy;

	public PlanetClass(int id, int size, float growth, float initialEnergy, float maxEnergy) {
		this.id = id;
		this.size = size;
		this.growth = growth;
		this.initialEnergy = initialEnergy;
		this.maxEnergy = maxEnergy;		
	}

	public int getId() {
		return id;
	}
	
	public int getSize() {
		return size;
	}


	public float getGrowth() {
		return growth;
	}


	public float getInitialEnergy() {
		return initialEnergy;
	}


	public float getMaxEnergy() {
		return maxEnergy;
	}


	public static PlanetClass[] planetClasses = new PlanetClass[] {
		new PlanetClass(1, 16, 0.5f, 5, 30),
		new PlanetClass(2, 25, 1, 10, 60),
		new PlanetClass(3, 45, 3, 30, 100),
	};
}
