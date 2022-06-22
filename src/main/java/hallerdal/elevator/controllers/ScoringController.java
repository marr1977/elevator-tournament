package hallerdal.elevator.controllers;

import hallerdal.elevator.Elevator;

/**
 * 
 * Elevator choice:
 *  - Elevators are scored by
 *    - Direction towards floor
 * 
 * Next floor choice:
 *  - Closest floor in the current direction, or just closest floor if stationary
 *  
 * @author hallerdal
 *
 */
public class ScoringController extends AbstractController {

	
	public static class Params {
		private final int directionWeight;
		private final int proximityWeight;
		private final int alreadyGoingThereWeight;
		private final int freeCapacityWeight;
		
		public Params(
			int directionWeight, 
			int proximityWeight, 
			int alreadyGoingThereWeight, 
			int freeCapacityWeight) {
			
			this.directionWeight = directionWeight;
			this.proximityWeight = proximityWeight;
			this.alreadyGoingThereWeight = alreadyGoingThereWeight;
			this.freeCapacityWeight = freeCapacityWeight;
		}

		@Override
		public String toString() {
			return "ScoringController.Params [directionWeight=" + directionWeight + ", proximityWeight=" + proximityWeight
					+ ", alreadyGoingThereWeight=" + alreadyGoingThereWeight + ", freeCapacityWeight="
					+ freeCapacityWeight + "]";
		}
	}
	
	private Params params;
	
	public ScoringController(Params params) {
		this.params = params;
	}
	
	@Override
	public Elevator assignElevator(int fromFloor, int destinationFloor) {
		
		Elevator bestElevator = null;
		int bestScore = 0;
		
		for (Elevator elevator : elevators) {
			int directionScore = isStationaryOrGoingTowardsFloor(elevator, fromFloor) ? params.directionWeight : 0; 

			int proximityScore = params.proximityWeight * (numFloors - Math.abs(elevator.getCurrentFloor() - fromFloor));
			
			int alreadyGoingThereScore = elevator.getFloorsToVisit().contains(fromFloor) ? params.alreadyGoingThereWeight : 0;
			
			int freeCapacityScore = params.freeCapacityWeight * (elevator.getMaxCapacity() - elevator.getTravelers().size());
			
			int totalScore = directionScore + proximityScore + freeCapacityScore + alreadyGoingThereScore;
			
			if (bestElevator == null || totalScore > bestScore) {
				bestElevator = elevator;
				bestScore = totalScore;
			}
		}
		
		return bestElevator;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public Integer getNextFloorToVisit(Elevator elevator) {
		return getClosestFloorInCurrentDirection(elevator);
	}


}
