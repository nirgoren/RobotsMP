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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;
import game.Point;
import game.Utility;

@SuppressWarnings("serial")
public class Board extends JPanel {
	
	int animation_steps = 0;
	Timer timer;
	ControlPanel cp;
	Point[] robots_graphics;
	Point[] start_graphics;
	Point[] target_graphics;
	
	BufferedImage buffer;
	BufferedImage[] robots_images;
	BufferedImage[] goals_images;
	BufferedImage[] base_robot_images;
	BufferedImage obstacle_image;
	
	public Board(ControlPanel cp)
	{
		super();
		this.cp = cp;
		robots_graphics = new Point[cp.num_robots];
		start_graphics = new Point[cp.num_robots];
		target_graphics = new Point[cp.num_robots];
		robots_images = new BufferedImage[cp.num_robots];
		base_robot_images = new BufferedImage[cp.num_robots];
		goals_images = new BufferedImage[cp.num_robots];
	}
	

	public void init() throws Exception {
		for (int i = 0; i < cp.num_robots; i++) {
			start_graphics[i] = new Point();
			target_graphics[i] = new Point();
			robots_graphics[i] = new Point();
		}
		
		obstacle_image = ImageIO.read(new File("img/Obstacle.png"));

		for (int i = 0; i < cp.num_robots; i++) {
			base_robot_images[i] = ImageIO.read(new File("img/Robot" + String.valueOf(i) + ".png"));
			robots_images[i] = ImageIO.read(new File("img/Robot" + String.valueOf(i) + ".png"));
			goals_images[i] = ImageIO.read(new File("img/Goal" + String.valueOf(i) + ".png"));
		}
	}
	
	public void animate() throws Exception
	{
		for (int i = 0; i < cp.num_robots; i++) {
			int diff_x = cp.robots[i].getX() - cp.robots_prev[i].getX();
			int diff_y = cp.robots[i].getY() - cp.robots_prev[i].getY();
			switch(diff_x)
			{
			case -1:
				robots_images[i] = Utility.rotate(base_robot_images[i], 3*Math.PI/2);
				break;
			case 1:
				robots_images[i] = Utility.rotate(base_robot_images[i], Math.PI/2);
				break;
			}
			switch(diff_y)
			{
			case -1:
				robots_images[i] = Utility.rotate(base_robot_images[i], 0);
				break;
			case 1:
				robots_images[i] = Utility.rotate(base_robot_images[i], Math.PI);
				break;
			}
		}

		for (int i = 0; i < cp.num_robots; i++) {
			robots_graphics[i].setX(cp.robots_prev[i].getX() * cp.dim); 
			robots_graphics[i].setY(cp.robots_prev[i].getY() * cp.dim); 
			start_graphics[i].setX(cp.robots_prev[i].getX() * cp.dim); 
			start_graphics[i].setY(cp.robots_prev[i].getY() * cp.dim); 
			target_graphics[i].setX(cp.robots[i].getX() * cp.dim); 
			target_graphics[i].setY(cp.robots[i].getY() * cp.dim); 
		}

			timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int num_steps = 40;
            	if (animation_steps > num_steps)
            	{
            		timer.stop();
                    animation_steps =0;
                    cp.ready_for_next = true;
                    if (cp.autorun)
                    {
                    	try {
                            cp.next();
                        }
                        catch (Exception ex)
                        {
                        	System.out.println(ex);
                        }
                    }
                    else
                    {
                        cp.advance_button.setText("Next step");
                    }
                    return;
            	}
            	for (int i = 0; i < cp.num_robots; i++) {
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
            g2d.clearRect(0, 0, cp.x*cp.dim + 8, cp.y*cp.dim + 8);
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
            for (row = 0; row < cp.y; row++) {
    			for (col = 0; col < cp.x; col++) {
    				if ((row % 2) == (col % 2))
    					g2d.setColor(Color.WHITE);
    				else
    					g2d.setColor(Color.WHITE);

    				g2d.fillRect(col * cp.dim, row * cp.dim, cp.dim, cp.dim);
    			}
    		}

    		g2d.setColor(Color.WHITE);
    		//g2d.drawImage(robot_image, temp, dim, null);
    		for (int i = 0; i < cp.num_robots; i++) {
			g2d.drawImage(goals_images[i], cp.goals[i].getX() * cp.dim, cp.goals[i].getY() * cp.dim, null);
    		}
    		for (int i = 0; i < cp.num_robots; i++) {
    			g2d.drawImage(robots_images[i], robots_graphics[i].getX(), robots_graphics[i].getY(), null);
			}
    		for (int i = 0; i < cp.num_obstacles; i++) {
    			g2d.drawImage(obstacle_image, cp.obstacles[i].getX() * cp.dim, cp.obstacles[i].getY() * cp.dim, null);
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
	
	
}