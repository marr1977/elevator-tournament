package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;

/**
 * 
 * Elevator choice:
 *  - The closest of all stationary elevators and those elevators moving towards that floor
 *  - Otherwise, random
 * 
 * Next floor choice:
 *  - Closest floor in the current direction, or just closest floor if stationary
 *  
 * @author hallerdal
 *
 */
public class ClosestController extends AbstractController {

	@Override
	public Elevator assignElevator(int fromFloor, int destinationFloor) {
		
		Elevator elivator = getClosestElevator(fromFloor, e -> { 
			return isStationaryOrGoingTowardsFloor(e, fromFloor);
		});
		
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
