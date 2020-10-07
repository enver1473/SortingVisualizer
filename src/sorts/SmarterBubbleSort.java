package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;

final public class SmarterBubbleSort extends Sort {
    public SmarterBubbleSort() {
        super();
        
        this.setLabels("More Optimized Bubble Sort", "Smarter Bubble");
    }
    
    public void smarterBubbleSort(int[] array, int length) {
    	int consecSorted;
        for(int i = length - 1; i > 0; i -= consecSorted) {
            consecSorted = 1;
            for(int j = 0; j < i; j++) {
                if (this.reads.compare(array[j], array[j + 1]) == 1){
                    this.writes.swap(array, j, j + 1, 0.075, true, false);
                    consecSorted = 1;
                } else consecSorted++;
                
                this.highlights.markArray(1, j);
                this.highlights.markArray(2, j + 1);
                this.delays.sleep(0.025);
            }
        }
    }

	@Override
	public void run(Main mainFrame, CanvasPane canvas, util.Reads reads, util.Writes writes, util.Delays delays,
			util.Highlights highlights) {
		this.mainFrame = mainFrame;
		this.canvas = canvas;
		this.reads = reads;
		this.writes = writes;
		this.delays = delays;
		this.highlights = highlights;

		this.canvas.getShuffles().shuffle();
		smarterBubbleSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}