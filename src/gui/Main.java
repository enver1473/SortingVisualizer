package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Main extends JFrame {
	public CanvasPane canvas;
	private ControlsPane controls;
	private Graphics2D mainRender;
	
	private int[] arr;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Main mainFrame = new Main();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		int w = 1080;
		int h = 600;

        mainFrame.setMinimumSize(new Dimension(w, h));
		mainFrame.setTitle("Sorting Visualizer by Enver");
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
        
        CanvasPane canvas = new CanvasPane(mainFrame);
		mainFrame.setCanvas(canvas);
		mainFrame.add(canvas, BorderLayout.CENTER);
		
		ControlsPane controls = new ControlsPane(canvas);
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		mainFrame.setControls(controls);
		mainFrame.add(controls, BorderLayout.EAST);
		
		mainFrame.pack();
	}
	
	public void setCanvas(CanvasPane canvas) {
		this.canvas = canvas;
	}
	
	public void setControls(ControlsPane controls) {
		this.controls = controls;
	}
	
	public CanvasPane getCanvas() {
		return this.canvas;
	}
	
	public ControlsPane getControls() {
		return this.controls;
	}
	
	public Graphics2D getMainRender() {
		return this.mainRender;
	}
	
	public void setMainRender(Graphics2D mainRender) {
		this.mainRender = mainRender;
	}
	
	public int getCurrentLength() {
		return this.canvas.count;
		
	}
	
	public int[] getArray() {
		return this.arr;
	}
	
	public void setArray(int[] arr) {
		this.arr = arr;
	}
}
