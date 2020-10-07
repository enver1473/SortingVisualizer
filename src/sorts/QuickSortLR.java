package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class QuickSortLR extends Sort {
	public QuickSortLR() {
		super();
		this.setLabels("Quick Sort (Left/Right Pointers)", "LR Quick");
	}

	public void quickSortLR(int[] array, int start, int end) {
		int pivot = end;

		if (end - start > 4) {
			int middleIdx = midValue(array, start, start + (end - start) / 2, end);
			if (middleIdx != end) {
				this.writes.swap(array, middleIdx, end, 2);
			}
		}

		int x = array[pivot];

		int i = start;
		int j = end;

		this.highlights.markArray(3, pivot);

		while (i <= j) {
			while (this.reads.compare(array[i], x) == -1) {
				i++;
				this.highlights.markArray(1, i);
				this.delays.sleep(0.25);

			}
			while (this.reads.compare(array[j], x) == 1) {
				j--;
				this.highlights.markArray(2, j);
				this.delays.sleep(0.25);

			}

			if (i <= j) {
				// Follow the pivot and highlight it.
				if (i == pivot) {
					this.highlights.markArray(3, j);
				}
				if (j == pivot) {
					this.highlights.markArray(3, i);
				}

				this.writes.swap(array, i, j, 1);

				i++;
				j--;
			}

		}

		if (start < j) {
			this.quickSortLR(array, start, j);
		}
		if (i < end) {
			this.quickSortLR(array, i, end);
		}
	}

	public int midValue(int[] array, int i1, int i2, int i3) {
		// i1 => index1
		// i2 => index2
		// i3 => index3
		int v1 = array[i1]; // v1 => value1
		int v2 = array[i2]; // v2 => value2
		int v3 = array[i3]; // v3 => value3

		if (v1 > v2) {
			if (v1 > v3) {
				if (v2 > v3) {
					return i2;
				} else {
					return i3;
				}
			} else {
				return i1;
			}
		} else {
			if (v2 > v3) {
				if (v1 > v3) {
					return i1;
				} else {
					return i3;
				}
			} else {
				return i2;
			}
		}
	};

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
		quickSortLR(this.canvas.getArray(), 0, this.canvas.count - 1);
		this.highlights.clearAllMarks();

	}
}