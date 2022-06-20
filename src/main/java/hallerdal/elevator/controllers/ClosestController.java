package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;
import hallerdal.elevator.Elevator.State;

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
			if (e.getState() == State.STATIONARY) {
				return true;
			}
			if (e.getState() == State.MOVING_DOWN && e.getCurrentFloor() > fromFloor) {
				return true;
			}
			if (e.getState() == State.MOVING_UP && e.getCurrentFloor() < fromFloor) {
				return true;
			}
			return false;
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
