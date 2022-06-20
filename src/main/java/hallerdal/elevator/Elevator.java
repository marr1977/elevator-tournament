package hallerdal.elevator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elevator {
	
	public enum State {
		STATIONARY,
		MOVING_UP,
		MOVING_DOWN
	}
	
	private int currentFloor;      // For moving elevators this is the last floor passed
	private int maxCapacity;

	private State state = State.STATIONARY;
	
	private String label;

	private List<Traveler> travelers = new ArrayList<>();
	private Set<Integer> floorsToVisit = new HashSet<>();
	private ElevatorController controller;
	private List<Floor> floors;
	
	private int progressBetweenFloors;
	private int ticksBetweenFloors;
	private Integer nextFloorToStopAt;
	
	public Elevator(
		int maxCapacity, 
		int ticksBetweenFloors,
		String label, 
		ElevatorController controller, 
		List<Floor> floors) {
		
		this.maxCapacity = maxCapacity;
		this.ticksBetweenFloors = ticksBetweenFloors;
		this.label = label;
		this.controller = controller;
		this.floors = floors;
	}
	
	public List<Traveler> getTravelers() {
		return travelers;
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	public State getState() {
		return state;
	}
	
	public void onNewTravelerWaiting(int floor) {
		
		if (floor != currentFloor && !floorsToVisit.add(floor)) {
			return;
		}
		
		if (state == State.STATIONARY) {
			pickUpFromCurrentFloor();
			setNextFloor();
		}
	}
	
	public List<Traveler> tick() {
		if (state == State.STATIONARY) {
			return null;
		}
		
		if (++progressBetweenFloors < ticksBetweenFloors) {
			return null;
		}
		
		// We have arrived at a new floor, check if we should stop
		currentFloor = currentFloor + (state == State.MOVING_DOWN ? -1 : 1);
		
		progressBetweenFloors = 0;
		
		if (currentFloor != nextFloorToStopAt) {
			return null;
		}
		
		floorsToVisit.remove(currentFloor);
		
		List<Traveler> disembarked = disembarkTravelers();
		
		if (disembarked.size() > 0) {
			Log.info("Elevator %s disembarked these travelers on floor %d: %s", label, currentFloor, disembarked);
		}
		
		pickUpFromCurrentFloor();
		
		setNextFloor();
		
		return disembarked;
	}

	private List<Traveler> disembarkTravelers() {
		List<Traveler> disembarked = new ArrayList<>();
		var it = travelers.iterator();
		while (it.hasNext()) {
			Traveler t = it.next();
			if (t.getDestinationFloor() == currentFloor) {
				disembarked.add(t);
				it.remove();
			}
		}
		return disembarked;
	}
	
	private void pickUpFromCurrentFloor() {
		Floor floor = floors.get(currentFloor);
		List<Traveler> travelersToPickUp = floor.removeWaiting(this, maxCapacity - travelers.size());
		
		travelersToPickUp.forEach(t -> { travelers.add(t); floorsToVisit.add(t.getDestinationFloor()); });
		
		if (travelersToPickUp.size() > 0) {
			Log.info("Elevator %s embarked these travelers on floor %d: %s", label, currentFloor, travelersToPickUp);
		}
	}

	public Set<Integer> getFloorsToVisit() {
		return floorsToVisit;
	}
	
	private void setNextFloor() {
		nextFloorToStopAt = controller.getNextFloorToVisit(this);
		if (nextFloorToStopAt == null || nextFloorToStopAt.intValue() == currentFloor) {
			state = State.STATIONARY;
			Log.info("Elevator %s is now STATIONARY on floor %d, floors to visit is %s", label, currentFloor, floorsToVisit);
		} else {
			state = nextFloorToStopAt < currentFloor ? State.MOVING_DOWN : State.MOVING_UP;
			Log.info("Elevator %s is now moving from floor %d to %d, floors to visit is %s", label, currentFloor, nextFloorToStopAt, floorsToVisit);
		}
		
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", label, state);
	}

}
