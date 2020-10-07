package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class CircleSort extends Sort {
	public CircleSort() {
		super();

		this.setLabels("Circle Sort", "Circle");
	}

	public int oneCircleSortPass(int[] array, int lo, int hi, int swapCount, double sleep) {
		if (lo == hi)
			return swapCount;

		int high = hi;
		int low = lo;
		int mid = (hi - lo) / 2;

		while (lo < hi) {
			if (reads.compare(array[lo], array[hi]) > 0) {
				this.writes.swap(array, lo, hi, sleep);
				swapCount++;
			}

			lo++;
			hi--;

			this.highlights.markArray(0, lo);
			this.highlights.markArray(1, hi);
			this.delays.sleep(sleep / 2);

		}

		if (lo == hi && this.reads.compare(array[lo], array[hi + 1]) > 0) {
			this.writes.swap(array, lo, hi + 1, sleep);
			swapCount++;

		}

		swapCount = this.oneCircleSortPass(array, low, low + mid, swapCount, sleep);
		swapCount = this.oneCircleSortPass(array, low + mid + 1, high, swapCount, sleep);

		return swapCount;
	}

	public void circleSort(int[] array, int length) {
		int numberOfSwaps = 0;
		do {
			numberOfSwaps = this.oneCircleSortPass(array, 0, length - 1, 0, 0.25);
		} while (numberOfSwaps != 0);
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
		circleSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}