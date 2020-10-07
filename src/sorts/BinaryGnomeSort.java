package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class BinaryGnomeSort extends Sort {
	public BinaryGnomeSort() {
		super();
		this.setLabels("Optimized Gnomesort + Binary Search", "Binary Gnome");
	}

	public void binaryGnomeSort() {
		int[] arr = this.canvas.getArray();
		int sortLength = this.canvas.getCurrentLength();

		for (int i = 1; i < sortLength; i++) {
			int num = arr[i];

			int lo = 0, hi = i;
			while (lo < hi) {
				this.highlights.clearMark(2);
				int mid = lo + ((hi - lo) / 2);

				this.highlights.markArray(0, lo);
				this.highlights.markArray(2, mid);
				this.highlights.markArray(1, hi);

				this.delays.sleep(1);

				if (this.reads.compare(num, arr[mid]) < 0) {
					hi = mid;
				} else {
					lo = mid + 1;
				}
			}

			this.highlights.clearMark(0);
			this.highlights.clearMark(1);
			this.highlights.clearMark(2);

			int j = i;
			while (j > lo) {
				this.writes.swap(arr, j, j - 1, 0.05);

				j--;
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
		binaryGnomeSort();
		this.highlights.clearAllMarks();

	}
}