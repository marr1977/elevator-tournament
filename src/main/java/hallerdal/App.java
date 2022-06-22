package hallerdal;

import hallerdal.elevator.SimulationParameters;
import hallerdal.elevator.controllers.ClosestController;
import hallerdal.elevator.controllers.ClosestControllerWithCapacityConsideration;
import hallerdal.elevator.controllers.ClosestOrFreeController;
import hallerdal.elevator.controllers.ClosestStationaryController;
import hallerdal.elevator.controllers.RandomController;
import hallerdal.elevator.controllers.ScoringController;
import hallerdal.elevator.controllers.ScoringController.Params;

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
    	controller.addContestant(new ClosestControllerWithCapacityConsideration());
    	controller.addContestant(new ClosestOrFreeController());
    	controller.addContestant(new ScoringController(new Params(20, 2, 72, 10)));
    	
    	controller.addRound(new SimulationParameters()
			.numFloors(10)
			.numElevators(1)
			.elevatorCapacity(5)
			.maxConcurrentTravelers(1)
			.travelersToDeliver(1000));
    	
    	controller.addRound(new SimulationParameters()
			.numFloors(10)
			.numElevators(1)
			.elevatorCapacity(5)
			.maxConcurrentTravelers(5)
			.travelersToDeliver(3000));
    	
    	controller.addRound(new SimulationParameters()
			.numFloors(10)
			.numElevators(4)
			.elevatorCapacity(5)
			.maxConcurrentTravelers(30)
			.travelersToDeliver(20000));
    	
    	controller.addRound(new SimulationParameters()
			.numFloors(10)
			.numElevators(4)
			.elevatorCapacity(5)
			.maxConcurrentTravelers(50)
			.travelersToDeliver(20000));
    	
    	controller.run();
    }
}
