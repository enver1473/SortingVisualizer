package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlsPane extends JPanel {
	JButton button;
	public ControlsPane() {
		super();
		button = new JButton("Klikni!");
		button.setSize(30, 30);
		this.add(button);
	}
}
