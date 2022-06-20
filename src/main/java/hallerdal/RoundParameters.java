package hallerdal;

public class RoundParameters {

	private int numFloors;
	private int numElevators;
	private int travelersToDeliver;
	private int maxConcurrentTravelers;
	
	public RoundParameters numFloors(int val) {
		numFloors = val;
		return this;
	}
	
	public RoundParameters numElevators(int val) {
		numElevators = val;
		return this;
	}
	
	public RoundParameters travelersToDeliver(int val) {
		travelersToDeliver = val;
		return this;
	}
	
	public RoundParameters maxConcurrentTravelers(int val) {
		maxConcurrentTravelers = val;
		return this;
	}
	
	public int getNumFloors() {
		return numFloors;
	}

	public int getNumElevators() {
		return numElevators;
	}

	public int getTravelersToDeliver() {
		return travelersToDeliver;
	}

	public int getMaxConcurrentTravelers() {
		return maxConcurrentTravelers;
	}

	@Override
	public String toString() {
		return String.format("# floors: %d, # elevators %d, # travelers %d, max concurrent travelers: %d", 
			numFloors, numElevators, travelersToDeliver, maxConcurrentTravelers);
	}
}
