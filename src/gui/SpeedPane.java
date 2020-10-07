package gui;

import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Delays;

final public class SpeedPane extends JPanel {
    final private static long serialVersionUID = 1L;
    
    private CanvasPane canvas;
    private JSlider slider;
    
    public SpeedPane(int[] array, Main mainFrame, CanvasPane canvas) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        
        setVisible(true);
        this.canvas = canvas;
    }

    private void initComponents() {
    	JLabel mainLabel = new JLabel("Speed");
    	mainLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainLabel.setAlignmentX(CENTER_ALIGNMENT);
    	
    	this.slider = new JSlider(JSlider.VERTICAL, 0, 40, 20);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("1%"));
        labels.put(10, new JLabel("10%"));
        labels.put(20, new JLabel("100%"));
        labels.put(30, new JLabel("1k%"));
        labels.put(40, new JLabel("10k%"));
    	
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(2);
        slider.setLabelTable(labels);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
            	double denominator = Math.pow(10, (slider.getValue() / 10.0)) / 100d;
            	
    			SpeedPane.this.canvas.setVisualizationSpeed(denominator);
    			
                Delays.userDelayFactor = 1 / denominator;
            }
        });

        this.add(mainLabel);
        this.add(slider);
    }
    
    public void resetSpeedFactor() {
    	this.slider.setValue(20);
    }
}