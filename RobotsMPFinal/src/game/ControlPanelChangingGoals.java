package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;

public class ControlPanelChangingGoals extends ControlPanel {
	
	public ControlPanelChangingGoals(int x, int y, int num_robots, Point[] obstacles, Point[] goals, String path) {
		super(x, y, num_robots, obstacles, goals, path);
	}
	
	@Override
	public void init() throws Exception {
		autorun = false;
		for (int i = 0; i < num_robots; i++) {
			robots[i] = new Point();
			robots_prev[i] = new Point();
		}
		executor = new ControllerExecutor(new BasicJitController(), path);
		for (int i = 0; i < num_robots; i++) {
			inputs.put("goalsX[" + i + "]", String.valueOf(goals[i].getX()));
			inputs.put("goalsY[" + i + "]", String.valueOf(goals[i].getY()));
		}

		executor.initState(inputs);
		
		Map<String, String> sysValues = executor.getCurrOutputs();
		
		for (int i = 0; i < num_robots; i++) {
			robots_prev[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots_prev[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
			robots[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
		}
		set_up_UI();
	}
	
	@Override
	void next() throws Exception {
		ready_for_next = false;
		advance_button.setText("...");
		for (int i = 0; i < num_robots; i++) {
			robots_prev[i].setX(robots[i].getX());
			robots_prev[i].setY(robots[i].getY());
		}
		if (all_reach_goals())
		{
			set_new_goals();
		}
		
		for (int i = 0; i < num_robots; i++) {
			inputs.put("goalsX[" + i + "]", String.valueOf(goals[i].getX()));
			inputs.put("goalsY[" + i + "]", String.valueOf(goals[i].getY()));
		}
		executor.updateState(inputs);
		
		Map<String, String> sysValues = executor.getCurrOutputs();
		
		for (int i = 0; i < num_robots; i++) {
			robots[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
		}
		board.animate();
	}
	
	private void set_new_goals()
	{
		for (int i = 0; i < goals.length; ) {
			int randx = ThreadLocalRandom.current().nextInt(0, x);
			int randy = ThreadLocalRandom.current().nextInt(0, y);
			Point goal = new Point(randx, randy);
			//check for no intersection with obstacles
			boolean valid = true;
			for (int j = 0; j < obstacles.length; j++) {
				if(goal.equals(obstacles[j])) 
					{
					valid = false;
					break;
					}
			}
			//check for no intersection with previously set goals
			if (valid)
			{
				for (int k = 0; k < i; k++)
				{
					if(goal.equals(goals[k])) 
					{
						valid = false;
						break;
					}
				}
			}
			if (valid)
			{
				goals[i] = goal;
				i++;
			}
		}
	}
	
	protected boolean all_reach_goals()
	{
		for (int i = 0; i < robots.length; i++) {
			if (!robots[i].equals(goals[i])) return false;
		}
		return true;
	}
}
