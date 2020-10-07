package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

/*
 * This version of Odd-Even Sort was taken from here, written by Rachit Belwariar:
 * https://www.geeksforgeeks.org/odd-even-sort-brick-sort/
 */

final public class OddEvenSort extends Sort {
	public OddEvenSort() {
		super();

		this.setLabels("Odd-Even Sort", "Odd-Even");
	}

	public void oddEvenSort(int[] array, int length) {
		boolean sorted = false;

		while (!sorted) {
			sorted = true;

			for (int i = 1; i < length - 1; i += 2) {
				if (this.reads.compare(array[i], array[i + 1]) == 1) {
					this.writes.swap(array, i, i + 1, 0.075);
					sorted = false;
				}

				this.highlights.markArray(1, i);
				this.delays.sleep(0.025);
			}

			for (int i = 0; i < length - 1; i += 2) {
				if (this.reads.compare(array[i], array[i + 1]) == 1) {
					this.writes.swap(array, i, i + 1, 0.075);
					sorted = false;
				}

				this.highlights.markArray(2, i);
				this.delays.sleep(0.025);
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
		oddEvenSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}