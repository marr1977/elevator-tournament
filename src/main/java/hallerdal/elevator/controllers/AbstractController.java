package hallerdal.elevator.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import hallerdal.elevator.Elevator;
import hallerdal.elevator.Elevator.State;
import hallerdal.elevator.ElevatorController;

public abstract class AbstractController implements ElevatorController {

	protected List<Elevator> elevators;
	protected int numFloors;

	protected Random random;

	@Override
	public void init(List<Elevator> elevators, int numFloors, Random random) {
		this.elevators = elevators;
		this.numFloors = numFloors;
		this.random = random;
		
	}
	
	protected Elevator getClosestStationaryElevator(int fromFloor) {
		return getClosestElevator(fromFloor, e -> e.getState() == State.STATIONARY);
	}
	
	protected Elevator getClosestElevator(int fromFloor, Predicate<Elevator> eligible) {
		Elevator closest = null;
		int minDiff = 0;
		
		for (Elevator e : elevators) {
			if (!eligible.test(e)) {
				continue;
			}
			int diff = Math.abs(e.getCurrentFloor() - fromFloor);
			if (closest == null || diff < minDiff) {
				closest = e;
				minDiff = diff;
			}
		}
		
		return closest;
	}

	protected Elevator getRandomElevator() {
		return elevators.get(random.nextInt(elevators.size()));
	}
	
	protected Integer getRandomNextFloor(Elevator elevator) {
		if (elevator.getFloorsToVisit().isEmpty()) {
			return null;
		}
		List<Integer> floors = new ArrayList<>(elevator.getFloorsToVisit());
		int nextFloor = elevator.getCurrentFloor();
		
		while (nextFloor == elevator.getCurrentFloor()) {
			nextFloor = floors.get(random.nextInt(floors.size()));
		}
		return nextFloor;
	}

	protected Integer getClosestFloorInCurrentDirection(Elevator elevator) {
		if (elevator.getFloorsToVisit().isEmpty()) {
			return null;
		}
		
		Integer closestInSameDir = null;
		Integer closestInSameDirDiff = null;
		Integer closestInOppositeDir = null;
		Integer closestInOppositeDirDiff = null;
		
		boolean movingUpOrStationary = 
			elevator.getState() == State.MOVING_UP || elevator.getState() == State.STATIONARY; 

		boolean movingDownOrStationary = 
			elevator.getState() == State.MOVING_DOWN || elevator.getState() == State.STATIONARY;
		
		for (int floor : elevator.getFloorsToVisit()) {
			int diff = elevator.getCurrentFloor() - floor;
			
			boolean sameDir = 
				movingUpOrStationary && diff < 0 ||
				movingDownOrStationary && diff > 0;
			
			if (sameDir && (closestInSameDirDiff == null || Math.abs(diff) < closestInSameDirDiff)) {
				closestInSameDir = floor;
			} else if (!sameDir && (closestInOppositeDirDiff == null || Math.abs(diff) < closestInOppositeDirDiff)) {
				closestInOppositeDir = floor;
			}
		}
		
		if (closestInSameDir != null) {
			return closestInSameDir;
		}
		
		if (closestInOppositeDir != null) {
			return closestInOppositeDir;
		}
		
		// Highly unexpected
		return getRandomNextFloor(elevator);
	}


}
