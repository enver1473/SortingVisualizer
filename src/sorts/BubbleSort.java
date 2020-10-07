package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class BubbleSort extends Sort {
	public BubbleSort() {
		super();

		this.setLabels("Bubble Sort", "Bubble");
	}

	public void bubbleSort() {
		int[] array = this.canvas.getArray();
		int sortLength = this.canvas.getCurrentLength();

		boolean sorted = false;

		while (!sorted) {
			sorted = true;
			for (int i = 0; i < sortLength - 1; i++) {
				if (this.reads.compareIndices(i, i + 1, 0.05, true) == 1) {
					this.writes.swap(array, i, i + 1, 0.05);
					sorted = false;
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
		bubbleSort();
		this.highlights.clearAllMarks();

	}
}