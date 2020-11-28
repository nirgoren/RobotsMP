package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;

@SuppressWarnings("serial")
public class Board extends JFrame {
	final int x = 8;
	final int y = 8;
	final int dim = 100;
	int[] robot0 = new int[2];
	int[] robot1 = new int[2];
	int[] obstacle0 = new int[] {0,4};
	int[] goal0 = new int[] {0,7};
	int[] goal1 = new int[] {7,0};
	BufferedImage m;

	ControllerExecutor executor;
	Map<String,String> inputs = new HashMap<String, String>();

	public void run() throws Exception {

		executor = new ControllerExecutor(new BasicJitController(), "out");
		m = ImageIO.read(new File("img/monkey.jpg"));

//		Random rand = new Random();
//		banana[0] = rand.nextInt(8);
//		banana[1] = rand.nextInt(8);
		
//		inputs.put("banana[0]", Integer.toString(banana[0]));
//		inputs.put("banana[1]", Integer.toString(banana[1]));
		executor.initState(inputs);
		
		Map<String, String> sysValues = executor.getCurrOutputs();
		
		robot0[0] = Integer.parseInt(sysValues.get("robotsX[0]"));
		robot0[1] = Integer.parseInt(sysValues.get("robotsY[0]"));
		robot1[0] = Integer.parseInt(sysValues.get("robotsX[1]"));
		robot1[1] = Integer.parseInt(sysValues.get("robotsY[1]"));
		
		paint(this.getGraphics());
		Thread.sleep(1000);
		
		while (true) {
			
//			if (monkey[0] == banana[0] & monkey[1] == banana[1]) {
//				banana[0] = rand.nextInt(8);
//				banana[1] = rand.nextInt(8);
//			}
			
//			inputs.put("banana[0]", Integer.toString(banana[0]));
//			inputs.put("banana[1]", Integer.toString(banana[1]));
			
			executor.updateState(inputs);
			
			sysValues = executor.getCurrOutputs();
			
			robot0[0] = Integer.parseInt(sysValues.get("robotsX[0]"));
			robot0[1] = Integer.parseInt(sysValues.get("robotsY[0]"));
			robot1[0] = Integer.parseInt(sysValues.get("robotsX[1]"));
			robot1[1] = Integer.parseInt(sysValues.get("robotsY[1]"));
			
			paint(this.getGraphics());
			Thread.sleep(1000);
		}
	}

	@Override
	public void paint(Graphics g) {
		int row;
		int col;

		for (row = 0; row < y; row++) {
			for (col = 0; col < x; col++) {
				if ((row % 2) == (col % 2))
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.LIGHT_GRAY);

				g.fillRect(col * dim, row * dim, dim, dim);
			}
		}

		g.setColor(Color.BLACK);
		g.drawString("GOAL0", goal0[0] * dim + 40, goal0[1] * dim + 50);
		g.drawString("GOAL1", goal1[0] * dim + 40, goal1[1] * dim + 50);
		g.drawString("OBSTACLE0", obstacle0[0] * dim + 20, obstacle0[1] * dim + 50);
		g.drawString("ROBOT0", robot0[0] * dim + 40, robot0[1] * dim + 50);
		g.drawString("ROBOT1", robot1[0] * dim + 40, robot1[1] * dim + 50);
		//System.out.println(robot1[0] + " " + robot1[1]);

//		if (banana[0] != -1) {
//			g.setColor(Color.YELLOW);
//			g.fillRect(banana[0] * dim, banana[1] * dim, dim, dim);
//			g.setColor(Color.BLACK);
//			g.drawString("BANANA", banana[0] * dim + 30, banana[1] * dim + 50);
//		}
//		if (m != null) {
//			g.drawImage(m, monkey[0] * dim, monkey[1] * dim, null);
//		} else {
//			g.setColor(Color.BLUE);
//			g.fillRect(monkey[0] * dim, monkey[1] * dim, dim, dim);
//			g.setColor(Color.WHITE);
//			g.drawString("MONKEY", monkey[0] * dim + 20, monkey[1] * dim + 50);
//		}
	}

	public static void main(String args[]) throws Exception {
		Board check = new Board();
		check.setTitle("ProjectName");
		check.setSize(check.x * check.dim, check.y * check.dim);
		check.setDefaultCloseOperation(EXIT_ON_CLOSE);
		check.setVisible(true);
		check.run();
	}
}