package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;
import game.Point;


@SuppressWarnings("serial")
public class Board extends JPanel {
	final int x = 5;
	final int y = 5;
	final int dim = 100;
	static final int y_offset = 30;
	final int num_robots = 2;
	Point[] robots = new Point[num_robots];
	Point[] obstacles = new Point[4];
	Point[] goals = new Point[num_robots];
	
	Point[] robots_prev = new Point[num_robots];
	Point[] robots_graphics = new Point[num_robots];
	Point[] start_graphics = new Point[num_robots];
	Point[] target_graphics = new Point[num_robots];
	
	
	
	int animation_steps = 0;
	Timer timer;
	
	BufferedImage buffer;
	BufferedImage[] robots_images = new BufferedImage[num_robots];
	BufferedImage[] goals_images = new BufferedImage[num_robots];
	BufferedImage base_robot_image;
	BufferedImage base_goal_image;
	BufferedImage obstacle_image;
	int temp = 0;

	ControllerExecutor executor;
	Map<String,String> inputs = new HashMap<String, String>();
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    BufferedImage result = deepCopy(image, false);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}

	public static BufferedImage deepCopy(BufferedImage bi, boolean copyPixels) {
	    ColorModel cm = bi.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bi.getRaster().createCompatibleWritableRaster();
	    if (copyPixels) {
	        bi.copyData(raster);
	    }
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}    

	public void run() throws Exception {
		obstacles[0] = new Point(0,2);
		obstacles[1] = new Point(1,2);
		obstacles[2] = new Point(3,2);
		obstacles[3] = new Point(4,2);
		goals[0] = new Point(0, 4);
		goals[1] = new Point(4, 0);
		
		for (int i = 0; i < num_robots; i++) {
			robots[i] = new Point();
			robots_prev[i] = new Point();
			start_graphics[i] = new Point();
			target_graphics[i] = new Point();
			robots_graphics[i] = new Point();
		}
		
		base_robot_image = ImageIO.read(new File("img/pacman.jpg"));
		obstacle_image = ImageIO.read(new File("img/ghost.jpg"));
		base_goal_image = ImageIO.read(new File("img/coin.jpg"));

		executor = new ControllerExecutor(new BasicJitController(), "out");
		for (int i = 0; i < num_robots; i++) {
			robots_images[i] = ImageIO.read(new File("img/pacman.jpg"));
			goals_images[i] = ImageIO.read(new File("img/coin.jpg"));
		}

//		Random rand = new Random();
//		banana[0] = rand.nextInt(8);
//		banana[1] = rand.nextInt(8);
		
//		inputs.put("banana[0]", Integer.toString(banana[0]));
//		inputs.put("banana[1]", Integer.toString(banana[1]));
		executor.initState(inputs);
		
		Map<String, String> sysValues = executor.getCurrOutputs();
		
		for (int i = 0; i < num_robots; i++) {
			robots_prev[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots_prev[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
			robots[i].setX(Integer.parseInt(sysValues.get("robotsX[" + i + "]")));
			robots[i].setY(Integer.parseInt(sysValues.get("robotsY[" + i + "]")));
		}
		
		next();
	}
	
	void next() throws Exception {
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
		animate();
	}
	
	public void animate() throws Exception
	{
		for (int i = 0; i < num_robots; i++) {
			int diff_x = robots[i].getX() - robots_prev[i].getX();
			int diff_y = robots[i].getY() - robots_prev[i].getY();
			switch(diff_x)
			{
			case 1:
				robots_images[i] = base_robot_image;
				break;
			case -1:
				robots_images[i] = rotate(base_robot_image, Math.PI);
				break;
			}
			switch(diff_y)
			{
			case 1:
				robots_images[i] = rotate(base_robot_image, Math.PI/2);
				break;
			case -1:
				robots_images[i] = rotate(base_robot_image, 3*Math.PI/2);
				break;
			}
		}
		for (int i = 0; i < num_robots; i++) {
			robots_graphics[i].setX(robots_prev[i].getX() * dim); 
			robots_graphics[i].setY(robots_prev[i].getY() * dim); 
			start_graphics[i].setX(robots_prev[i].getX() * dim); 
			start_graphics[i].setY(robots_prev[i].getY() * dim); 
			target_graphics[i].setX(robots[i].getX() * dim); 
			target_graphics[i].setY(robots[i].getY() * dim); 
		}
		
			timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int num_steps = 40;
            	if (animation_steps > num_steps)
            	{
            		timer.stop();
                    animation_steps =0;
            		try {
						next();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    return;
            	}
            	for (int i = 0; i < num_robots; i++) {
					robots_graphics[i].setX((int)(start_graphics[i].getX() * (1 - (double)(animation_steps)/num_steps)
							+ target_graphics[i].getX() * ((double)(animation_steps)/num_steps)));
					robots_graphics[i].setY((int)(start_graphics[i].getY() * (1 - (double)(animation_steps)/num_steps)
							+ target_graphics[i].getY() * ((double)(animation_steps)/num_steps)));
				}
            	animation_steps++;
            	updateBuffer();
            	repaint();
            }
        });
        timer.start();
	}
	
    @Override
    public void invalidate() {
        buffer = null;
        //updateBuffer();
        super.invalidate();
    }
	
	protected void updateBuffer() {

        if (getWidth() > 0 && getHeight() > 0) {

            if (buffer == null) {

                buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

            }

            Graphics2D g2d = buffer.createGraphics();
            g2d.clearRect(0, 0, x*dim + 8, y*dim + y_offset + 8);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            
            int row;
    		int col;
    		//todo: toggle grid with user input
//            for (row = 0; row < y; row++) {
//    			for (col = 0; col < x; col++) {
//    				if ((row % 2) == (col % 2))
//    					g2d.setColor(Color.BLACK);
//    				else
//    					g2d.setColor(Color.DARK_GRAY);
//
//    				g2d.fillRect(col * dim, row * dim, dim, dim);
//    			}
//    		}

    		g2d.setColor(Color.WHITE);
    		//g2d.drawImage(robot_image, temp, dim, null);
    		for (int i = 0; i < num_robots; i++) {
			g2d.drawImage(goals_images[i], goals[i].getX() * dim, goals[i].getY() * dim, null);
    		}
    		for (int i = 0; i < num_robots; i++) {
    			g2d.drawImage(robots_images[i], robots_graphics[i].getX(), robots_graphics[i].getY(), null);
			}
    		for (int i = 0; i < obstacles.length; i++) {
    			g2d.drawImage(obstacle_image, obstacles[i].getX() * dim, obstacles[i].getY() * dim, null);
			}
        }

    }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (buffer != null) {
            g2d.drawImage(buffer, 0, 0, this);
        }
	}
	
	public static void main(String args[]) throws Exception {
		Board check = new Board();
		JFrame frame = new JFrame();
		frame.setTitle("ProjectName");
		frame.setSize(check.x * check.dim + 8, check.y * check.dim + y_offset + 8);
		check.setSize(check.x * check.dim + 8, check.y * check.dim);
		//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		check.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.add(check, BorderLayout.CENTER);
		check.run();
	}
}