# elevator-tournament
An elevator controller algorithm competition

Add your own controller and beat me

Example output
```
Seed is 1655749275479
Starting round # floors: 10, # elevators 1, # travelers 100, max concurrent travelers: 1
  RandomController is done
  ClosestStationaryController is done
  ClosestController is done
Result of round: # floors: 10, # elevators 1, # travelers 100, max concurrent travelers: 1
  1: 23.532615 - RandomController
  2: 24.620802 - ClosestStationaryController
  3: 24.844655 - ClosestController

Starting round # floors: 10, # elevators 1, # travelers 300, max concurrent travelers: 10
  RandomController is done
  ClosestStationaryController is done
  ClosestController is done
Result of round: # floors: 10, # elevators 1, # travelers 300, max concurrent travelers: 10
  1: 77.117177 - ClosestController
  2: 83.353347 - ClosestStationaryController
  3: 142.721423 - RandomController

Starting round # floors: 10, # elevators 4, # travelers 10000, max concurrent travelers: 50
  RandomController is done
  ClosestStationaryController is done
  ClosestController is done
Result of round: # floors: 10, # elevators 4, # travelers 10000, max concurrent travelers: 50
  1: 61.208725 - ClosestController
  2: 78.875009 - ClosestStationaryController
  3: 168.866366 - RandomController
```
