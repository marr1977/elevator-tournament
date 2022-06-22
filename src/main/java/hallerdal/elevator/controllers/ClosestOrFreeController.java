package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;

/**
 * 
 * Elevator choice:
 *  - The closest of all stationary elevators and those elevators moving towards that floor
 *  - Otherwise, an elevator with some capacity
 *  - Otherwise, random
 * 
 * Next floor choice:
 *  - Closest floor in the current direction, or just closest floor if stationary
 *  
 * @author hallerdal
 *
 */
public class ClosestOrFreeController extends AbstractController {

	@Override
	public Elevator assignElevator(int fromFloor, int destinationFloor) {
		
		Elevator elivator = getClosestElevator(fromFloor, e -> { 
			return isStationaryOrGoingTowardsFloor(e, fromFloor);
		});
		
		if (elivator != null) {
			return elivator;
		}
		
		elivator = elevators.stream().filter(e -> e.getMaxCapacity() > e.getTravelers().size()).findAny().orElse(null);
		
		if (elivator != null) {
			return elivator;
		}
		
		return getRandomElevator();
	}

	
	@Override
	public Integer getNextFloorToVisit(Elevator elevator) {
		return getClosestFloorInCurrentDirection(elevator);
	}


}
