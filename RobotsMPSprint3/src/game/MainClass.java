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
			case "1":
				System.out.println("Running with scene 1");
			case "2":
				System.out.println("Running with scene 2");
				x = 5;
				y = 6;
				obstacles = new Point[6];
				obstacles[0] = new Point(0,2);
				obstacles[1] = new Point(1,2);
				obstacles[2] = new Point(3,3);
				obstacles[3] = new Point(4,2);
				obstacles[4] = new Point(3,5);
				obstacles[5] = new Point(0,1);
			}
		}
		
		if(args.length > 1)
		{
			switch(args[1]) {
			case "fixed":
				System.out.println("Running in fixed goals mode");
				cp = new ControlPanel(x, y, num_robots, obstacles, goals); // Fixed goals - Compatible with RobotsMPFixedGoals
				break;
			case "changing":
				System.out.println("Running in changing goals mode");
				cp = new ControlPanelChangingGoals(x, y, num_robots, obstacles, goals); // Changing goals - Compatible with RobotsMPChangingGoals
				break;
			case "unlabelled":
				System.out.println("Running in unlabelled mode");
				cp = new ControlPanelUnlabelled(x, y, num_robots, obstacles, goals); // Unlabelled robots and goals - Compatible with RobotsMPUnlabelled
				break;
			default:
				System.out.println("Running in fixed goals mode");
			    cp = new ControlPanel(x, y, num_robots, obstacles, goals); // Fixed goals - Compatible with RobotsMPFixedGoals
			}
		}
		else {
		    cp = new ControlPanel(x, y, num_robots, obstacles, goals); // Fixed goals - Compatible with RobotsMPFixedGoals
		}
		cp.init();
		
	}
}
