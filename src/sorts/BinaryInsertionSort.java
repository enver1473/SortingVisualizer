package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class BinaryInsertionSort extends Sort {

	public BinaryInsertionSort() {
		this.setLabels("Insertion Sort + Binary Search", "Binary Insertion");
	}

	protected void binaryInsertSort(int[] array, int start, int end, double compSleep, double writeSleep) {
		for (int i = start; i < end; i++) {
			int num = array[i];
			int lo = start, hi = i;

			while (lo < hi) {
				int mid = lo + ((hi - lo) / 2);
				this.highlights.markArray(1, lo);
				this.highlights.markArray(2, mid);
				this.highlights.markArray(3, hi);

				this.delays.sleep(compSleep);

				if (this.reads.compare(num, array[mid]) < 0) {
					hi = mid;
				} else {
					lo = mid + 1;
				}

			}

			this.highlights.clearMark(3);

			int j = i - 1;

			while (j >= lo) {
				this.writes.write(array, j + 1, array[j], writeSleep, true, false);
				j--;

			}

			this.writes.write(array, lo, num, writeSleep, true, false);

			this.highlights.clearAllMarks();
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
		this.binaryInsertSort(this.canvas.getArray(), 0, this.canvas.count, 1, 0.1);
		this.highlights.clearAllMarks();

	}
}
