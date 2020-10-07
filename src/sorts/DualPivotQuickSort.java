package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class DualPivotQuickSort extends Sort {
	public DualPivotQuickSort() {
		super();

		this.setLabels("Dual-Pivot Quick Sort", "Dual-Pivot Quick");
	}

	private void dualPivot(int[] array, int left, int right, int divisor) {
		int length = right - left;

		if (length < 4) {
			this.highlights.clearMark(1);
			int pos;
			int current;
			int start = left;
			int end = right + 1;
			double sleep = 1;

			for (int i = start; i < end; i++) {
				current = array[i];
				pos = i - 1;

				while (pos >= start && this.reads.compare(array[pos], current) > 0) {
					this.writes.write(array, pos + 1, array[pos], sleep);
					pos--;

				}
				this.writes.write(array, pos + 1, current, sleep);

			}

			this.highlights.clearAllMarks();
			return;
		}

		int third = length / divisor;

		// "medians"
		int med1 = left + third;
		int med2 = right - third;

		if (med1 <= left) {
			med1 = left + 1;
		}
		if (med2 >= right) {
			med2 = right - 1;
		}
		if (this.reads.compare(array[med1], array[med2]) == -1) {
			this.writes.swap(array, med1, left, 1);
			this.writes.swap(array, med2, right, 1);
		} else {
			this.writes.swap(array, med1, right, 1);
			this.writes.swap(array, med2, left, 1);
		}

		// pivots
		int pivot1 = array[left];
		int pivot2 = array[right];

		// pointers
		int less = left + 1;
		int great = right - 1;

		// sorting
		for (int k = less; k <= great; k++) {
			if (this.reads.compare(array[k], pivot1) == -1) {
				this.writes.swap(array, k, less++, 1);
			} else if (this.reads.compare(array[k], pivot2) == 1) {
				while (k < great && this.reads.compare(array[great], pivot2) == 1) {
					great--;
					this.highlights.markArray(2, great);
					this.delays.sleep(1);

				}
				this.writes.swap(array, k, great--, 1);
				this.highlights.clearMark(2);

				if (this.reads.compare(array[k], pivot1) == -1) {
					this.writes.swap(array, k, less++, 1);
				}

			}

		}

		// swaps
		int dist = great - less;

		if (dist < 13) {
			divisor++;
		}

		this.writes.swap(array, less - 1, left, 1);
		this.writes.swap(array, great + 1, right, 1);

		// subarrays
		this.dualPivot(array, left, less - 2, divisor);
		if (pivot1 < pivot2) {
			this.dualPivot(array, less, great, divisor);
		}
		this.dualPivot(array, great + 2, right, divisor);
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
		this.dualPivot(this.canvas.getArray(), 0, this.canvas.count - 1, 3);
		this.highlights.clearAllMarks();

	}
}