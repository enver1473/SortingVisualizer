package sorts;

import java.util.ArrayList;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class RadixLSD extends Sort {
	private int bucketCount;
	public RadixLSD(int bucketCount) {
		this.setLabels("Least Significant Digit Radix Sort (Base " + bucketCount + ")", "Radix LSD");
		this.bucketCount = bucketCount;
	}
	
	private void radixLSD(int[] array, int sortLength, int bucketCount, double delay) {
        int highestpower = this.reads.analyzeMaxLog(array, sortLength, bucketCount, delay / 2, true);
        
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] registers = new ArrayList[bucketCount];
        
        for(int i = 0; i < bucketCount; i++)
            registers[i] = new ArrayList<>();
        
        for(int p = 0; p <= highestpower; p++){
            for(int i = 0; i < sortLength; i++){
                this.highlights.markArray(0, i);
                
                int digit = this.reads.getDigit(array[i], p, bucketCount);
                registers[digit].add(array[i]);
                
                this.writes.mockWrite(sortLength, digit, array[i], delay);
            }

            this.writes.fancyTranscribe(array, sortLength, registers, bucketCount * delay);
        }
	}

	@Override
	public void run(Main mainFrame, CanvasPane canvas, Reads reads, Writes writes, Delays delays,
			Highlights highlights) {
		this.mainFrame = mainFrame;
		this.canvas = canvas;
		this.reads = reads;
		this.writes = writes;
		this.delays = delays;
		this.highlights = highlights;

		this.canvas.getShuffles().shuffle();
		this.radixLSD(this.canvas.getArray(), this.canvas.count, this.bucketCount, 0.5);
		this.highlights.clearAllMarks();
	}

}
