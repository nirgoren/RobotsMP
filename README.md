# RobotsMP (Workshop in Software Models)
  ## A system of robots, targets and obstacles on a board
  
  ![Simulator](https://github.com/nirgorentau/RobotsMP/blob/229d808f581c2ed457897e515f2a51f5fc300db3/simulator.png){:height="100px" width="100px"}

**The project includes 2 main parts**:
1. Spectra specification- Spectra is a formal specification language for reactive systems which consists of logical constraints,
guarantees and assumptions, which describe temporal relations between inputs and outputs. Given a Spectra specification, construct a reactive
system implementation, if one exits, that ensures satisfaction of the specification for all computations.
2. GUI (Java Swing).

**Requirements**:
1. The robots should get to their targets infinitely often in every run.
2. The robots should not collide with each other or the obstacles.
3. The robots should move in steps of at most 1 in each direction, and only in one direction.

**Simulation**: 
The simulation generates random goals for the robots and displays the robots movements provided by the controller.
The simulation follows the environment assumptions : 
  - The goals locations are unique and don’t collide with an obstacle 
  - The goals only change when all robots reach their destinations 

**Modes**:
1. Automatic mode - Let the simulation advance automatically. 
2. Step-by-step mode- Advance the simulation by one step.

**Variants**:
1. Fixed goals variant- The robots goals are pre-defined and never change.
2. Changing goals variant- Once all the robots reach their goals, the goals change.
3. Unlabeled variant- All robots should reach some target, and once they do, the goals change.
4. Limited fuel variant - each robot has limited number of steps it can make before all robots reach their pre-defined goals.



