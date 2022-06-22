package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;

/**
 * 
 * Elevator choice:
 *  - The closest of all stationary elevators and those elevators moving towards that floor, 
 *    considering only those elevators with free capacity
 *  - Otherwise, random
 * 
 * Next floor choice:
 *  - Closest floor in the current direction, or just closest floor if stationary
 *  
 * @author hallerdal
 *
 */
public class ClosestControllerWithCapacityConsideration extends AbstractController {

	@Override
	public Elevator assignElevator(int fromFloor, int destinationFloor) {
		
		Elevator elivator = getClosestElevator(fromFloor, e -> { 
			if (!isStationaryOrGoingTowardsFloor(e, fromFloor)) {
				return false;
			}
			
			if (willHaveCapacityAtFloor(e, fromFloor)) {
				return true;
			}
			return false;
		});
		
		if (elivator != null) {
			return elivator;
		}
		
		return getRandomElevator();
	}

	private boolean willHaveCapacityAtFloor(Elevator e, int floor) {
		// This could be a much more sophisticated check. Just because
		// an elevator is full now does not mean it would be full when 
		// reaching the specified floor
		return e.getMaxCapacity() > e.getTravelers().size();
	}
	
	@Override
	public Integer getNextFloorToVisit(Elevator elevator) {
		return getClosestFloorInCurrentDirection(elevator);
	}


}
