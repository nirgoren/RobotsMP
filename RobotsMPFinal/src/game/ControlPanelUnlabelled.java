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

public class ControlPanelUnlabelled extends ControlPanelChangingGoals {
	
	public ControlPanelUnlabelled(int x, int y, int num_robots, Point[] obstacles, Point[] goals, String path) {
		super(x, y, num_robots, obstacles, goals, path);
	}
	
	@Override
	void set_up_UI() throws Exception
	{
		advance_button = new JButton();
		autorun_button = new JButton();
		frame = new JFrame();
	    frame.add(advance_button);
	    frame.add(autorun_button);
	    // Use unlabelled board
		board = new UnlabelledBoard(this);
		board.init();
		frame.setTitle("RobotsMP");
		frame.setSize(x * dim + 8 + 150, y * dim + y_offset + 8);
		board.setSize(x * dim , y * dim);
		frame.setLayout(null);
		frame.add(board, BorderLayout.CENTER);
		advance_button.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent arg0) {
						try {
							if (ready_for_next && !autorun) next();
						}
						catch(Exception e) {System.out.println(e);}
					}
				});
	    advance_button.setBounds(x * dim + 8,0,130,50);
	    advance_button.setText("Start");
	    
	    autorun_button.addActionListener(new ActionListener()
	    		{
			    	public void actionPerformed(ActionEvent arg0) {
						try {
							if(autorun)
							{
								autorun = false;
								autorun_button.setText("Auto run");
							}
							else if (ready_for_next)
							{
								autorun = true;
								autorun_button.setText("Stop Auto run");
								next();
							}
						}
						catch(Exception e) {System.out.println(e);}
					}
	    		});
	    autorun_button.setBounds(x * dim + 8,50,130,50);
	    autorun_button.setText("Auto run");
	    frame.setVisible(true);
		board.setVisible(true);
	    advance_button.setVisible(true);
	    autorun_button.setVisible(true);
		ready_for_next = true;
	}
	
	// Check if all robots have reached some destination
	@Override
	protected boolean all_reach_goals()
	{
		for (int i = 0; i < robots.length; i++) {
			boolean flag = false;
			for (int j = 0; j < robots.length; j++) {
				if (robots[i].equals(goals[j])) flag = true;
			}
			if (!flag) return false;
		}
		return true;
	}
}
