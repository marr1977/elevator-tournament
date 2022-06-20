package hallerdal.elevator;

public class SimulationStatistics {

	int travelersDelivered;
	double totalTripTimePerFloorTravelled;
	
	public void addDeliveredTraveler(double tripTimePerFloorTravelled) {
		travelersDelivered++;
		totalTripTimePerFloorTravelled += tripTimePerFloorTravelled;
	}
	
	@Override
	public String toString() {
		return String.format("Travelers delivered: %d%nAverage trip time per floor travelled: %f", 
			travelersDelivered, getAverageTripTimePerFloorTravelled());
	}
	
	public double getAverageTripTimePerFloorTravelled() {
		return totalTripTimePerFloorTravelled/travelersDelivered;
	}
	
	public int getTravelersDelivered() {
		return travelersDelivered;
	}
}
