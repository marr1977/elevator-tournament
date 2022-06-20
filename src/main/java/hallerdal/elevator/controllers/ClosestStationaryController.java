package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;

/**
 * 
 * Elevator choice:
 *  - Closest stationary, if any
 *  - Otherwise, random
 * 
 * Next floor choice:
 *  - Closest floor in the current direction, or just closest floor if stationary
 *  
 * @author hallerdal
 *
 */
public class ClosestStationaryController extends AbstractController {

	@Override
	public Elevator assignElevator(int fromFloor, int destinationFloor) {
		
		Elevator e = getClosestStationaryElevator(fromFloor);
		
		if (e != null) {
			return e;
		}
		
		return getRandomElevator();
	}

	
	@Override
	public Integer getNextFloorToVisit(Elevator elevator) {
		return getClosestFloorInCurrentDirection(elevator);
	}


}
