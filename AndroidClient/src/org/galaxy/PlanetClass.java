package org.galaxy;

public class PlanetClass {
	private int size;
	private float growth;
	private float initialEnergy;
	private float maxEnergy;

	public PlanetClass(int size, float growth, float initialEnergy, float maxEnergy) {
		this.size = size;
		this.growth = growth;
		this.initialEnergy = initialEnergy;
		this.maxEnergy = maxEnergy;		
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
		new PlanetClass(16, 0.5f, 5, 30),
		new PlanetClass(25, 1, 10, 60),
		new PlanetClass(45, 3, 30, 100),
	};
}
