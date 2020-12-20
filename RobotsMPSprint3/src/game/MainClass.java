package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainClass {
	public static void main(String args[]) throws Exception {
		int x = 5;
		int y = 5;
		int num_robots = 2;
		Point[] obstacles = new Point[4];
		obstacles[0] = new Point(0,2);
		obstacles[1] = new Point(1,2);
		obstacles[2] = new Point(3,2);
		obstacles[3] = new Point(4,2);
		Point[] goals = new Point[num_robots];
		goals[0] = new Point(0, 4);
		goals[1] = new Point(4, 0);
		ControlPanel cp;
		if(args.length > 0)
		{
			switch(args[0]) {
			case "fixed":
				cp = new ControlPanel(x, y, num_robots, obstacles, goals); // Fixed goals - Compatible with RobotsMPFixedGoals
				break;
			case "changing":
				cp = new ControlPanelChangingGoals(x, y, num_robots, obstacles, goals); // Changing goals - Compatible with RobotsMPChangingGoals
				break;
			case "unlabelled":
				cp = new ControlPanelUnlabelled(x, y, num_robots, obstacles, goals); // Unlabelled robots and goals - Compatible with RobotsMPUnlabelled
				break;
			default:
			    cp = new ControlPanel(x, y, num_robots, obstacles, goals); // Fixed goals - Compatible with RobotsMPFixedGoals
			}
		}
		else {
		    cp = new ControlPanel(x, y, num_robots, obstacles, goals); // Fixed goals - Compatible with RobotsMPFixedGoals
		}
		cp.init();
		
	}
}
