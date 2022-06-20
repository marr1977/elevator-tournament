package hallerdal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hallerdal.elevator.ElevatorController;
import hallerdal.elevator.Log;
import hallerdal.elevator.SimulationEngine;
import hallerdal.elevator.SimulationStatistics;

public class TournamentController {

	private List<ElevatorController> contestants = new ArrayList<>();
	private List<RoundParameters> rounds = new ArrayList<>();
	private Random random;
	
	public TournamentController() {
		this(System.currentTimeMillis());
	}
	
	public TournamentController(long seed) {
		System.out.println("Seed is " + seed);
		this.random = new Random(seed);
	}
	
	public void addContestant(ElevatorController contestant) {
		contestants.add(contestant);
	}
	
	public void addRound(RoundParameters round) {
		rounds.add(round);
	}
	
	public void run() {
		
		Log.logEnabled = false;
		
		for (RoundParameters round : rounds) {
			System.out.println("Starting round " + round);
			
			List<RoundResult> results = new ArrayList<>();
			
			for (var c : contestants) {
				SimulationEngine engine = new SimulationEngine(
					round.getNumFloors(), round.getNumElevators(), 100, round.getMaxConcurrentTravelers(), 
					round.getTravelersToDeliver(), random, c);
				
				SimulationStatistics statistics = engine.run();
				results.add(new RoundResult(c, statistics));
				
				System.out.println("  " + c.getClass().getSimpleName() + " is done");
			}
			
			Collections.sort(results);
			
			System.out.println("Result of round: " + round);
			for (int i = 0; i < results.size(); ++i) {
				System.out.println(String.format("  %d: %f - %s", i + 1, 
					results.get(i).stats.getAverageTripTimePerFloorTravelled(), results.get(i).controller.getClass().getSimpleName()));
			}
			System.out.println();
		}
	}
	
	private static class RoundResult implements Comparable<RoundResult> {
		ElevatorController controller;
		SimulationStatistics stats;
		
		public RoundResult(ElevatorController controller, SimulationStatistics stats) {
			this.controller = controller;
			this.stats = stats;
		}

		@Override
		public int compareTo(RoundResult o) {
			return Double.compare(stats.getAverageTripTimePerFloorTravelled(), o.stats.getAverageTripTimePerFloorTravelled());
		}
		
	}
}
