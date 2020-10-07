package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class DoubleSelectionSort extends Sort {

	public DoubleSelectionSort() {
		this.setLabels("Double Selection Sort", "Double Selection");
	}

	public void doubleSelectionSort(int[] array, int length) {
		int left = 0;
		int right = length - 1;
		int smallest = 0;
		int biggest = 0;

		while (left <= right) {
			for (int i = left; i <= right; i++) {
				this.highlights.markArray(3, i);

				if (this.reads.compare(array[i], array[biggest]) == 1) {
					biggest = i;
					this.highlights.markArray(1, biggest);
					this.delays.sleep(0.01);
				}
				if (this.reads.compare(array[i], array[smallest]) == -1) {
					smallest = i;
					this.highlights.markArray(2, smallest);
					this.delays.sleep(0.01);
				}

				this.delays.sleep(0.01);

			}

			if (biggest == left)
				biggest = smallest;

			this.writes.swap(array, left, smallest, 0.02, true, false);
			this.writes.swap(array, right, biggest, 0.02, true, false);

			left++;
			right--;

			smallest = left;
			biggest = right;

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
		this.doubleSelectionSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}

}
