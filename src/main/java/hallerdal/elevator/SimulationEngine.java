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
	private Random random;
	private ElevatorController controller;
	private SimulationParameters params;
	
	public SimulationEngine(
		SimulationParameters params,
		Random random,
		ElevatorController controller) {
		
		this.params = params;
		this.controller = controller;
		this.random = new Random();
		
		for (int i = 0; i < params.getNumFloors(); ++i) {
			floors.add(new Floor(i));
		}

		for (int i = 0; i < params.getNumElevators(); ++i) {
			String label = Character.valueOf((char)('A' + i)).toString();
			elevators.add(new Elevator(
				params, label, controller, floors));
		}
		
		controller.init(elevators, params.getNumFloors(), random);
	}
	
	public SimulationStatistics run() {
		
		SimulationStatistics statistics = new SimulationStatistics();
		
		int currentTime = 0;
		
		while (statistics.getTravelersDelivered() < params.getTravelersToDeliver()) {
			if (getPeopleInSystem() < params.getMaxConcurrentTravelers()) {
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
		int originFloor = random.nextInt(params.getNumFloors());
		int destinationFloor = originFloor;
		while (destinationFloor == originFloor) {
			destinationFloor = random.nextInt(params.getNumFloors());
		}
		return new Traveler(originFloor, destinationFloor);
	}


	private int getPeopleInSystem() {
		return 
			elevators.stream().mapToInt(e -> e.getTravelers().size()).sum() + 
			floors.stream().mapToInt(f -> f.getNumberOfWaitingTravelers()).sum();
	}
	
}
