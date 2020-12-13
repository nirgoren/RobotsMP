package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainClass {
	public static void main(String args[]) throws Exception {
		//ControlPanel cp = new ControlPanel();
		ControlPanel cp = new ControlPanelChangingGoals();
		cp.init();
	}
}
