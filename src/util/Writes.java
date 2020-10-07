package util;

import java.text.DecimalFormat;
import java.util.ArrayList;

import gui.CanvasPane;

public class Writes {
	private CanvasPane canvas;
	private Delays delays;
	private Highlights highlights;
	private Timers timers;
	private DecimalFormat formatter;
	
	private volatile long swaps;
	private volatile long writes;
	private volatile long auxWrites;
	
	public Writes(CanvasPane canvas) {
		this.canvas = canvas;
		this.delays = canvas.getDelays();
		this.highlights = canvas.getHighlights();
		this.timers = canvas.getTimers();
		this.formatter = canvas.getFormatter();
		
		this.swaps = 0;
		this.writes = 0;
		this.auxWrites = 0;
	}
	
	public void swap(int[] array, int a, int b, double ms, boolean mark, boolean auxwrite) {
		if (mark) markSwap(a, b);

		timers.startTimer();
		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
		timers.stopTimer(System.nanoTime());
		
		updateSwaps(auxwrite);
		this.delays.sleep(ms);

        if (this.canvas.shouldVisualize()) {
            this.canvas.repaint();
        }
	}
	
	public void swap(int[] array, int a, int b, double ms) {
		this.swap(array, a, b, ms, true, false);
	}
	
	public void write(int[] array, int at, int equals, double ms, boolean mark, boolean auxwrite) {
        if (mark) this.highlights.markArray(0, at);
        
        if (!auxwrite) writes++;
        else auxWrites++;

		timers.startTimer();
        array[at] = equals;
		timers.stopTimer(System.nanoTime());
        
        this.delays.sleep(ms);

        if (this.canvas.shouldVisualize()) {
            this.canvas.repaint();
        }
	}

    public void write(int[] array, int at, int equals, double ms) {
    	this.write(array, at, equals, ms, true, false);
    }
    
    public void reversal(int[] array, int start, int length, double sleep, boolean mark, boolean auxwrite) {
        for(int i = start; i < start + ((length - start + 1) / 2); i++) {
            this.swap(array, i, start + length - i, sleep);

            if (this.canvas.shouldVisualize()) {
                this.canvas.repaint();
            }
        }
    }
    
    public void arraycopy(int[] src, int srcPos, int[] dest, int destPos, int length, double sleep, boolean mark, boolean aux) {
        for(int i = 0; i < length; i++) {
            if(mark) {
                if(aux) this.highlights.markArray(1, srcPos  + i);
                else    this.highlights.markArray(1, destPos + i);
            }
            
            this.write(dest, destPos + i, src[srcPos + i], sleep, false, aux);
        }
    }
    
    public void reversearraycopy(int[] src, int srcPos, int[] dest, int destPos, int length, double sleep, boolean mark, boolean aux) {
        for(int i = length - 1; i >= 0; i--) {
            if(mark) {
                if(aux) this.highlights.markArray(1, srcPos  + i);
                else    this.highlights.markArray(1, destPos + i);
            }
            
            this.write(dest, destPos + i, src[srcPos + i], sleep, false, aux);
        }
    }

    //Simulates a write in order to better estimate time for values being written to an ArrayList
    public void mockWrite(int length, int pos, int val, double pause) {
        int[] mockArray = new int[length];
        
        this.auxWrites++;

		timers.startTimer();
        mockArray[pos] = val;
		timers.stopTimer(System.nanoTime());
        
        this.delays.sleep(pause);

        if (this.canvas.shouldVisualize()) {
            this.canvas.repaint();
        }
    }

    public void transcribe(int[] array, ArrayList<Integer>[] registers, int start, boolean mark, boolean auxwrite) {
        int total = start;

        for(int index = 0; index < registers.length; index++) {
            for(int i = 0; i < registers[index].size(); i++) {
                this.write(array, total++, registers[index].get(i), 0, mark, auxwrite);
                if(mark) {
                	this.delays.sleep(1);

                    if (this.canvas.shouldVisualize()) {
                        this.canvas.repaint();
                    }
                }
            }
            registers[index].clear();
        }
    }

    public void transcribeMSD(int[] array, ArrayList<Integer>[] registers, int start, int min, double sleep, boolean mark, boolean auxwrite) {
        int total = start;
        int temp = 0;

        for(ArrayList<Integer> list : registers) {
            total += list.size();
        }
        
        for(int index = registers.length - 1; index >= 0; index--) {
            for(int i = registers[index].size() - 1; i >= 0; i--) {
                this.write(array, total + min - temp++ - 1, registers[index].get(i), 0, mark, auxwrite);
                if(mark) {
                	this.delays.sleep(sleep);

                    if (this.canvas.shouldVisualize()) {
                        this.canvas.repaint();
                    }
                }
            }
        }
    }

    public void fancyTranscribe(int[] array, int length, ArrayList<Integer>[] registers, double sleep) {
        int[] tempArray = new int[length];
        boolean[] tempWrite = new boolean[length];
        int radix = registers.length;

        this.transcribe(tempArray, registers, 0, false, true);
        auxWrites -= length;

        for(int i = 0; i < length; i++) {
            int register = i % radix;
            int pos = (register * (length / radix)) + (i / radix);
            
            if(!tempWrite[pos]) {
                this.write(array, pos, tempArray[pos], 0, false, false);
                tempWrite[pos] = true;
            }
            
            this.highlights.markArray(register, pos);
            if(register == 0) this.delays.sleep(sleep);

            if (this.canvas.shouldVisualize()) {
                this.canvas.repaint();
            }
        }
        
        for(int i = 0; i < length; i++) {
            if(!tempWrite[i]){
                this.write(array, i, tempArray[i], 0, false, false);
            }
        }
    
        this.highlights.clearAllMarks();
    }
	
	public void markSwap(int a, int b) {
		this.highlights.markArray(0, a);
		this.highlights.markArray(1, b);
	}
	
	public void updateSwaps(boolean auxwrite) {
		this.swaps++;
		if (!auxwrite) this.writes += 2;
		else this.auxWrites += 2;
	}
	
	public String getMainWrites() {
		return this.formatter.format(this.writes) + (this.writes == 1 ? " Write to main array" : " Writes to main array");
	}
	
	public String getAuxWrites() {
		return this.formatter.format(this.auxWrites) + (this.auxWrites == 1 ? " Write to aux array's" : " Writes to aux array's");
	}
	
	public String getSwaps() { 
		return this.formatter.format(this.swaps) + (this.swaps == 1 ? " Swap" : " Swaps");
	}
	
	public void clearStats() {
		this.swaps = 0;
		this.writes = 0;
		this.auxWrites = 0;
	}
}
