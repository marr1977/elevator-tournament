package hallerdal.elevator;

public class Traveler {
	private int originFloor;
	private int destinationFloor;

	private int requestTime;
	private int disembarkTime;
	
	public Traveler(int originFloor, int destinationFloor) {
		this.originFloor = originFloor;
		this.destinationFloor = destinationFloor;
	}
	
	@Override
	public String toString() {
		return String.format("[%d -> %d, req time %d]", originFloor, destinationFloor, requestTime);
	}
	
	public int getOriginFloor() {
		return originFloor;
	}

	public int getDestinationFloor() {
		return destinationFloor;
	}

	public int getRequestTime() {
		return requestTime;
	}
	
	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}
	
	public int getDisembarkTime() {
		return disembarkTime;
	}
	
	public void setDisembarkTime(int disembarkTime) {
		this.disembarkTime = disembarkTime;
	}

	public double getTripTimePerFloorTraveled() {
		return (double) (disembarkTime - requestTime) / (double) Math.abs(destinationFloor - originFloor);
	}
	
	
	
}
