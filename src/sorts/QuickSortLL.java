package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class QuickSortLL extends Sort {

	public QuickSortLL() {
		super();
		setLabels("Quick Sort (LL Pointers)", "LL Quick");
	}

	public int partition(int[] array, int lo, int hi) {
		int pivot = array[hi];
		int i = lo;

		for (int j = lo; j < hi; j++) {
			if (this.reads.compare(array[j], pivot) == -1) {
				this.writes.swap(array, i, j, 2);
				i++;
			} else {
				this.highlights.markArray(0, i == 0 ? 0 : i - 1);
				this.highlights.markArray(1, j);
			}

		}
		this.writes.swap(array, i, hi, 2);

		this.highlights.clearMark(0);
		this.highlights.clearMark(1);
		return i;
	}

	public void quickSortLL(int[] array, int lo, int hi) {
		if (lo < hi) {
			int p = this.partition(array, lo, hi);
			quickSortLL(array, lo, p - 1);
			quickSortLL(array, p + 1, hi);
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
		quickSortLL(this.canvas.getArray(), 0, this.canvas.count - 1);
		this.highlights.clearAllMarks();

	}

}
