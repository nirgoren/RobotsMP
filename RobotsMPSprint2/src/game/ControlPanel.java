package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;

public class ControlPanel {
	final int x = 5;
	final int y = 5;
	final int dim = 100;
	static final int y_offset = 30;
	final int num_robots = 2;
	final int num_obstacles = 4;
	Point[] robots = new Point[num_robots];
	Point[] obstacles = new Point[num_obstacles];
	Point[] goals = new Point[num_robots];
	
	Point[] robots_prev = new Point[num_robots];
	
	JFrame frame;
	Board board;
	JButton advance_button;
	
	boolean ready_for_next;
	ControllerExecutor executor;
	Map<String,String> inputs = new HashMap<String, String>();
	
	public void init() throws Exception {
		obstacles[0] = new Point(0,2);
		obstacles[1] = new Point(1,2);
		obstacles[2] = new Point(3,2);
		obstacles[3] = new Point(4,2);
		goals[0] = new Point(0, 4);
		goals[1] = new Point(4, 0);
		
		for (int i = 0; i < num_robots; i++) {
			robots[i] = new Point();
			robots_prev[i] = new Point();
		}
		executor = new ControllerExecutor(new BasicJitController(), "out");
		executor.initState(inputs);
		
		Map<String, String> sysValues = executor.getCurrOutputs();
		
		for (int i = 0; i < num_robots; i++) {
			robots_prev[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots_prev[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
			robots[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
		}
		advance_button = new JButton();
		frame = new JFrame();
	    frame.add(advance_button);
		board = new Board(this);
		board.init();
		frame.setTitle("ProjectName");
		frame.setSize(x * dim + 8 + 150, y * dim + y_offset + 8);
		board.setSize(x * dim + 8, y * dim);
		frame.setLayout(null);
		frame.add(board, BorderLayout.CENTER);
		advance_button.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent arg0) {
						try {
							if (ready_for_next) next();
						}
						catch(Exception e) {System.out.println(e);}
					}
				});
	    advance_button.setBounds(x * dim + 8,0,100,50);
	    advance_button.setText("Start");
	    frame.setVisible(true);
		board.setVisible(true);
	    advance_button.setVisible(true);
		ready_for_next = true;
	}
	
	void next() throws Exception {
		ready_for_next = false;
		advance_button.setText("...");
		for (int i = 0; i < num_robots; i++) {
			robots_prev[i].setX(robots[i].getX());
			robots_prev[i].setY(robots[i].getY());
		}
		executor.updateState(inputs);
		
		Map<String, String> sysValues = executor.getCurrOutputs();
		
		for (int i = 0; i < num_robots; i++) {
			robots[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
		}
		board.animate();
	}
	
	
}
