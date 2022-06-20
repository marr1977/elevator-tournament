package hallerdal.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Assumptions:
 * 
 *  - Passengers leaving and getting on the elevator takes 0 time
 * 
 * @author hallerdal
 *
 */
public class SimulationEngine {

	private List<Elevator> elevators = new ArrayList<>();
	private List<Floor> floors = new ArrayList<>();
	private int peopleToDeliver;
	private int maxConcurrentTravelers;
	private int numFloors;
	private Random random;
	private ElevatorController controller;
	
	/**
	 * Number of ticks it takes to travel between floors
	 */
	private static final int FLOOR_TRAVEL_TIME = 10;
	
	public SimulationEngine(int numFloors, int numElevators, int elevatorCapacity,
		int maxConcurrentTravelers, int peopleToDeliver, Random random,
		ElevatorController controller) {
		
		this.numFloors = numFloors;
		this.maxConcurrentTravelers = maxConcurrentTravelers;
		this.peopleToDeliver = peopleToDeliver;
		this.controller = controller;
		this.random = new Random();
		
		for (int i = 0; i < numFloors; ++i) {
			floors.add(new Floor(i));
		}

		for (int i = 0; i < numElevators; ++i) {
			String label = Character.valueOf((char)('A' + i)).toString();
			elevators.add(new Elevator(
				elevatorCapacity, FLOOR_TRAVEL_TIME, label, controller, floors));
		}
		
		controller.init(elevators, numFloors, random);
	}
	
	public SimulationStatistics run() {
		
		SimulationStatistics statistics = new SimulationStatistics();
		
		int currentTime = 0;
		
		while (statistics.getTravelersDelivered() < peopleToDeliver) {
			if (getPeopleInSystem() < maxConcurrentTravelers) {
				Traveler traveler = getTraveler();
				traveler.setRequestTime(currentTime);

				Elevator elevator = controller.assignElevator(traveler.getOriginFloor(), traveler.getDestinationFloor());
				
				Log.info("Created new traveler %s and assigned to elevator %s", traveler, elevator);
				
				floors.get(traveler.getOriginFloor()).addWaitingTraveler(elevator, traveler);
				
				elevator.onNewTravelerWaiting(traveler.getOriginFloor());
			}
			
			for (Elevator elevator : elevators) {

	            List<Traveler> disembarked = elevator.tick();

	            if (disembarked != null) {
	            	for (Traveler t : disembarked) {
	            		t.setDisembarkTime(currentTime);
	            		Log.info("Traveler disembarked after having travelled %d floors, total trip time was %d",
            				Math.abs(t.getDestinationFloor() - t.getOriginFloor()), t.getDisembarkTime() - t.getRequestTime());
	            		statistics.addDeliveredTraveler(t.getTripTimePerFloorTraveled());
	            	}
	            }
	        }
			
			++currentTime;
		}
		
		return statistics;
	}
	
	/**
	 * Gets a traveler from random origin floor to random destination floor 
	 */
	private Traveler getTraveler() {
		int originFloor = random.nextInt(numFloors);
		int destinationFloor = random.nextInt(numFloors);
		while (destinationFloor == originFloor) {
			destinationFloor = random.nextInt(numFloors);
		}
		return new Traveler(originFloor, destinationFloor);
	}


	private int getPeopleInSystem() {
		return 
			elevators.stream().mapToInt(e -> e.getTravelers().size()).sum() + 
			floors.stream().mapToInt(f -> f.getNumberOfWaitingTravelers()).sum();
	}
	
}
