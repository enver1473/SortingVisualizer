package util;

import java.text.DecimalFormat;

import gui.CanvasPane;

public class Reads {
	private volatile long comparisons;
	
	private CanvasPane canvas;
	
	private int[] array;
	
	private Highlights highlights;
	private Delays delays;
	private Timers timers;
	
	private DecimalFormat formatter;
	
	public Reads(CanvasPane canvas) {
		this.canvas = canvas;
		
		this.array = canvas.getArray();
		
		this.highlights = canvas.getHighlights();
		this.delays = canvas.getDelays();
		this.timers = canvas.getTimers();
		
		this.formatter = canvas.getFormatter();
	}
	
	public int compare(int a, int b) {
		comparisons++;
		
		int value;

		timers.startTimer();
		if (a > b) 		value =  1;
		else if (a < b) value = -1;
		else 			value =  0;
		timers.stopTimer(System.nanoTime());

        if (this.canvas.shouldVisualize()) {
            this.canvas.repaint();
        }
		return value;
	}
	
	public int compareIndices(int left, int right, double ms, boolean mark) {
        if(mark) {
            this.highlights.markArray(0, left);
            this.highlights.markArray(1, right);
            this.delays.sleep(ms);
        }
        
        return this.compare(array[left], array[right]);
	}
    
    public int analyzeMaxLog(int[] array, int length, int base, double sleep, boolean mark) {
        int max = 0;
        
        for(int i = 0; i < length; i++) { 
            int log = (int) (Math.log(array[i]) / Math.log(base));
            
    		timers.startTimer();
            if(log > max) max = log;
    		timers.stopTimer(System.nanoTime());
    		
            if(mark) {
                this.highlights.markArray(0, i);
                this.delays.sleep(sleep);
            }

            if (this.canvas.shouldVisualize()) {
                this.canvas.repaint();
            }
        }

        return max;
    }
    
    public int analyzeBit(int[] array, int length) {
        // Find highest bit of highest value
        int max = 0;
        
        for(int i = 0; i < length; i++) {

    		timers.startTimer();
            max = Math.max(max, array[i]);
    		timers.stopTimer(System.nanoTime());
    		
            this.highlights.markArray(0, i);
            this.delays.sleep(0.75);
            
            if (this.canvas.shouldVisualize()) {
                this.canvas.repaint();
            }
        }
        
        int analysis;

		timers.startTimer();
        analysis = 31 - Integer.numberOfLeadingZeros(max);
		timers.stopTimer(System.nanoTime());
        
        return analysis;
    }
    
    public int getDigit(int a, int power, int radix) {
        int digit;

		timers.startTimer();
        digit = (int) (a / Math.pow(radix, power)) % radix;
		timers.stopTimer(System.nanoTime());
        
        return digit;
    }
	
	public String getComparisons() {
		return this.formatter.format(this.comparisons) + (this.comparisons == 1 ? " Comparison" : " Comparisons");
	}
	
	public void clearStats() {
		this.comparisons = 0;
	}
	
}
