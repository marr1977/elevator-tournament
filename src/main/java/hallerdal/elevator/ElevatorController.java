package hallerdal.elevator;

import java.util.List;
import java.util.Random;

public interface ElevatorController {
	void init(List<Elevator> elevators, int numFloors, Random random);
    Elevator assignElevator(int fromFloor, int destinationFloor);
    Integer getNextFloorToVisit(Elevator elevator);
}
