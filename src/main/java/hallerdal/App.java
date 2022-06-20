package hallerdal;

import hallerdal.elevator.controllers.ClosestController;
import hallerdal.elevator.controllers.ClosestStationaryController;
import hallerdal.elevator.controllers.RandomController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	TournamentController controller = new TournamentController();
    	controller.addContestant(new RandomController());
    	controller.addContestant(new ClosestStationaryController());
    	controller.addContestant(new ClosestController());
    	
    	controller.addRound(new RoundParameters()
			.numFloors(10)
			.numElevators(1)
			.maxConcurrentTravelers(1)
			.travelersToDeliver(100));
    	
    	controller.addRound(new RoundParameters()
			.numFloors(10)
			.numElevators(1)
			.maxConcurrentTravelers(10)
			.travelersToDeliver(300));
    	
    	controller.addRound(new RoundParameters()
			.numFloors(10)
			.numElevators(4)
			.maxConcurrentTravelers(50)
			.travelersToDeliver(10000));
    	
    	controller.run();
    }
}
