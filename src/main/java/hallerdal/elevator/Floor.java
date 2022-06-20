package hallerdal.elevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {

	private Map<Elevator, List<Traveler>> waitingTravelersPerElevator = new HashMap<>();
	private int floor;
	
	public Floor(int floor) {
		this.floor = floor;
	}
	
	public void addWaitingTraveler(Elevator elevator, Traveler traveler) {
		Log.info("Traveler %s now waiting for elevator %s on floor %d", traveler, elevator, floor);
		waitingTravelersPerElevator.computeIfAbsent(elevator, e -> new ArrayList<>()).add(traveler);
	}
	
	
	@Override
	public String toString() {
		return String.format("[Floor %d, # Waiting = %d, Queue = %s]", floor, getNumberOfWaitingTravelers(), waitingTravelersPerElevator.toString());
	}
	
	public int getNumberOfWaitingTravelers() {
		return waitingTravelersPerElevator.values().stream().mapToInt(l -> l.size()).sum();
	}

	public List<Traveler> removeWaiting(Elevator elevator, int maxAmount) {
		List<Traveler> removedTravelers = new ArrayList<>();
		List<Traveler> waiting = waitingTravelersPerElevator.get(elevator);
		if (waiting != null) {
			var it = waiting.iterator();
			while (it.hasNext() && removedTravelers.size() < maxAmount) {
				removedTravelers.add(it.next());
				it.remove();
			}
		}
		return removedTravelers;
	}
	
}
