package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainClass {
	public static void main(String args[]) throws Exception {
		//ControlPanel cp = new ControlPanel(); // Fixed goals - Compatible with Variant 1
		ControlPanel cp = new ControlPanelChangingGoals(); // Changing goals - Compatible with Variant 2
		cp.init();
	}
}
