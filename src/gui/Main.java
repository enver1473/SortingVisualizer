package gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Main extends JFrame{

	public static void main(String[] args) {
		Main mainFrame = new Main();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		mainFrame.setSize(300, 600);
		CanvasPane canvas = new CanvasPane();
		ControlsPane controls = new ControlsPane();
		mainFrame.getContentPane().add(canvas, BorderLayout.CENTER);
		mainFrame.getContentPane().add(controls, BorderLayout.EAST);
		mainFrame.pack();
		mainFrame.setLocation(400, 100);
		mainFrame.setSize(400, 800);
		mainFrame.setVisible(true);
	}

}
