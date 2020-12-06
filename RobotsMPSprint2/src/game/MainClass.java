package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainClass {
	public static void main(String args[]) throws Exception {
//		Board check = new Board();
//		JFrame frame = new JFrame();
//		frame.setTitle("ProjectName");
//		frame.setSize(check.x * check.dim + 8 + 150, check.y * check.dim + y_offset + 8);
//		check.setSize(check.x * check.dim + 8, check.y * check.dim);
		//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		check.setVisible(true);
//		frame.setLayout(null);
//		frame.add(check, BorderLayout.CENTER);
//		JButton advance_button = new JButton();
//		advance_button.addActionListener(new ActionListener() 
//				{
//					public void actionPerformed(ActionEvent arg0) {
//						try {
//							if (check.ready_for_next) check.next();
//						}
//						catch(Exception e) {}
//					}
//				});
//	    advance_button.setBounds(check.x * check.dim + 8,0,100,50);
//	    advance_button.setVisible(true);
//	    advance_button.setText("Advance");
//	    frame.add(advance_button);
//	    frame.setVisible(true);
//		check.run();
		ControlPanel cp = new ControlPanel();
		cp.init();
	}
}
