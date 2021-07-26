# RobotsMP (Workshop in Software Models)
  ## A system of robots, targets and obstacles on a board
  
  <img src="https://github.com/nirgorentau/RobotsMP/blob/d7ce817b0245997a1d2f03d19aad47fce7a9a496/Simulator_details.PNG" width="600" height="400">

### Requirements:

1. The robots should get to their targets infinitely often in every run.
2. The robots should not collide with each other or the obstacles.
3. The robots should move in steps of at most 1 in each direction, and only in one direction.

### Simulation: 

The simulation generates random goals for the robots and displays the robots movements provided by the controller.
The simulation follows the environment assumptions : 
  - The goals locations are unique and don’t collide with an obstacle 
  - The goals only change when all robots reach their destinations 

### Variants:

1. Fixed goals variant- The robots goals are pre-defined and never change.
2. Changing goals variant- Once all the robots reach their goals, the goals change.
3. Unlabeled variant- All robots should reach some target, and once they do, the goals change.
4. Limited fuel variant - each robot has limited number of steps it can make before all robots reach their pre-defined goals.

### Scenes:

4 obstacles, 2 robots , 5X5 board:

  <img src="https://github.com/nirgorentau/RobotsMP/blob/c591e5af770072d1e99b8d56a11d1e5c5f7dd206/Scene1.png" width="400" height="400">


4 obstacles, 3 robots, 4X4 board:

  <img src="https://github.com/nirgorentau/RobotsMP/blob/c591e5af770072d1e99b8d56a11d1e5c5f7dd206/Scene3.png" width="400" height="400">
  
  
“Wall” of obstacles- Unrealizable scene (Robots can't reach their targets): 
  
  <img src="https://github.com/nirgorentau/RobotsMP/blob/a5ae99d7b2e9409c0b6b60456217ca7955154286/Wall_Of_Obstacles.png" width="400" height="400">
  
  
Insufficient fuel- Unrealizable scene (Robots can't reach their targets):

  <img src="https://github.com/nirgorentau/RobotsMP/blob/35ee7151c8164e5c0a4ca6cca416ed16fdd29947/Limited_Fuel.png" width="400" height="400">


********************************************************************************************************************************************
**Comments**: 
The project includes 2 main parts:
1. Spectra specification- Spectra is a formal specification language for reactive systems which consists of logical constraints,
guarantees and assumptions, which describe temporal relations between inputs and outputs. Given a Spectra specification, construct a reactive
system implementation, if one exits, that ensures satisfaction of the specification for all computations.
2. GUI (Java Swing).

