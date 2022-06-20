package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;

/**
 * A completely random controller
 * 
 * @author hallerdal
 *
 */
public class RandomController extends AbstractController {

	@Override
	public Elevator assignElevator(int fromFloor, int destinationFloor) {
		return getRandomElevator();
	}

	@Override
	public Integer getNextFloorToVisit(Elevator elevator) {
		return getRandomNextFloor(elevator);
	}
}
