package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class SelectionSort extends Sort {

	public SelectionSort() {
		this.setLabels("Selection Sort", "Selection");
	}

	public void selectionSort(int[] array, int length) {
		for (int i = 0; i < length - 1; i++) {
			int lowestindex = i;

			for (int j = i + 1; j < length; j++) {
				this.highlights.markArray(2, j);
				this.delays.sleep(0.01);

				if (this.reads.compare(array[j], array[lowestindex]) == -1) {
					lowestindex = j;
					this.highlights.markArray(1, lowestindex);
					this.delays.sleep(0.01);
				}

			}
			this.writes.swap(array, i, lowestindex, 0.02);

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
		this.selectionSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}

}
