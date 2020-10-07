package gui;

import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import templates.Sort;
import util.Highlights;

final public class ArrayPane extends JPanel {
    final private static long serialVersionUID = 1L;

    private CanvasPane canvas;
    private Highlights highlights;

    public ArrayPane(int[] array, Main mainFrame, CanvasPane canvas) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ArrayPane.this.canvas = canvas;
        this.highlights = canvas.getHighlights();
        
        initComponents();

        setVisible(true);
    }
    
    /**
     * Initializes the components for the "Element count" slider
     * and add's a change listener to it.
     * */
    private void initComponents() {
    	JLabel mainLabel = new JLabel("Element count");
    	mainLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainLabel.setAlignmentX(CENTER_ALIGNMENT);
    	
    	JSlider slider = new JSlider(JSlider.VERTICAL, 300, 1500, 900);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        
        labels.put( 300, new JLabel("2^3"));
        labels.put( 600, new JLabel("2^6"));
        labels.put( 900, new JLabel("2^9"));
        labels.put(1200, new JLabel("2^12"));
        labels.put(1500, new JLabel("2^15"));
    	
        slider.setMajorTickSpacing(300);
        slider.setMinorTickSpacing(75);
        slider.setLabelTable(labels);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                if (!Sort.sorting) {
                	boolean updateUniqueCount = false;
                	if (canvas.getCurrentLength() <= canvas.getUniqueCount()) {
                		updateUniqueCount = true;
                	}
                	int newCount = (int) (Math.pow(2, slider.getValue() / 100d));
                    ArrayPane.this.canvas.setCurrentLength(newCount);
                    if (updateUniqueCount) canvas.setUniqueCount(newCount);
                    
                    
                    ArrayPane.this.canvas.initializeArray();
                } else {
                	slider.setValue((int)(CanvasPane.getLogBaseTwoOf(ArrayPane.this.canvas.count) * 100));
                }
                
                ArrayPane.this.highlights.clearAllMarks();
            }
        });

        this.add(mainLabel);
        this.add(slider);
    }
}