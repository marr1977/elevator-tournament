package hallerdal.elevator;

public class SimulationParameters {

	private int numFloors;
	private int numElevators;
	private int travelersToDeliver;
	private int maxConcurrentTravelers;
	private int elevatorCapacity;
	private int travelTimeBetweeenFloors = 10;
	private int timePerFloorStop = 20;
	private int timePerPassengerToEnterExitElevator = 1;
	
	public SimulationParameters elevatorCapacity(int val) {
		elevatorCapacity = val;
		return this;
	}

	public SimulationParameters numFloors(int val) {
		numFloors = val;
		return this;
	}
	
	public SimulationParameters numElevators(int val) {
		numElevators = val;
		return this;
	}
	
	public SimulationParameters travelersToDeliver(int val) {
		travelersToDeliver = val;
		return this;
	}
	
	public SimulationParameters maxConcurrentTravelers(int val) {
		maxConcurrentTravelers = val;
		return this;
	}
	
	public int getTimePerFloorStop() {
		return timePerFloorStop;
	}
	
	public int getTimePerPassengerToEnterExitElevator() {
		return timePerPassengerToEnterExitElevator;
	}
	
	public int getTravelTimeBetweeenFloors() {
		return travelTimeBetweeenFloors;
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
	
	public int getElevatorCapacity() {
		return elevatorCapacity;
	}
	

	@Override
	public String toString() {
		return String.format("# floors: %d, # elevators %d, # elevator capacity %d, # travelers %d, max concurrent travelers: %d", 
			numFloors, numElevators, elevatorCapacity, travelersToDeliver, maxConcurrentTravelers);
	}
}
