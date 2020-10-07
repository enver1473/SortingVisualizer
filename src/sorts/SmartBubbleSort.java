package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class SmartBubbleSort extends Sort {

	public SmartBubbleSort() {
		super();
		setLabels("Optimized Bubble Sort", "Smart Bubble");
	}

	public void smartBubbleSort() {
		int[] arr = this.canvas.getArray();
		for (int i = 0; i < this.canvas.count; i++) {
			for (int j = 0; j < this.canvas.count - i - 1; j++) {
				if (this.reads.compareIndices(j, j + 1, 0.05, true) == 1) {
					this.writes.swap(arr, j, j + 1, 0.05);
				}

			}
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
		smartBubbleSort();
		this.highlights.clearAllMarks();

	}

}
