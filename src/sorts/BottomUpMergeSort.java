package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class BottomUpMergeSort extends Sort {
	private int[] scratchArray;
	private int copyLength;

	public BottomUpMergeSort() {
		this.setLabels("Bottom-up Merge Sort", "Bottom-up Merge");
	}

	private void merge(int[] array, int currentLength, int index, int mergeSize, double ms) {
		int left = index;
		int mid = left + (mergeSize / 2);
		int right = mid;
		int end = Math.min(currentLength, index + mergeSize);

		int scratchIndex = left;

		this.highlights.clearAllMarks();

		if (right < end) {
			while (left < mid && right < end) {
				this.highlights.markArray(1, left);
				this.highlights.markArray(2, right);
				this.delays.sleep(ms);

				if (this.reads.compare(array[left], array[right]) <= 0) {
					this.writes.write(this.scratchArray, scratchIndex++, array[left++], 0, false, true);
				} else {
					this.writes.write(this.scratchArray, scratchIndex++, array[right++], 0, false, true);
				}

			}
			if (left < mid) {
				while (left < mid) {
					this.highlights.markArray(1, left);
					this.delays.sleep(ms);

					this.writes.write(this.scratchArray, scratchIndex++, array[left++], 0, false, true);
				}
			}
			if (right < end) {
				while (right < end) {
					this.highlights.markArray(2, right);
					this.delays.sleep(ms);

					this.writes.write(this.scratchArray, scratchIndex++, array[right++], 0, false, true);
				}
			}
		} else {
			this.copyLength = left;
		}

		this.highlights.clearAllMarks();

	}

	public void bottomUpMergeSort(int[] array, int currentLength, double ms) {
		this.scratchArray = new int[currentLength];
		int mergeSize = 2;

		while (mergeSize <= currentLength) {
			this.copyLength = currentLength;

			for (int i = 0; i < currentLength; i += mergeSize) {
				this.merge(array, currentLength, i, mergeSize, ms);
			}

			this.highlights.clearMark(2);

			for (int i = 0; i < this.copyLength; i++) {
				this.writes.write(array, i, this.scratchArray[i], ms, true, false);

			}

			mergeSize *= 2;

		}
		if ((mergeSize / 2) != currentLength) {
			this.merge(array, currentLength, 0, mergeSize, ms);

			this.highlights.clearMark(2);

			for (int i = 0; i < currentLength; i++) {
				this.writes.write(array, i, this.scratchArray[i], ms, true, false);

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
		this.bottomUpMergeSort(this.canvas.getArray(), this.canvas.count, 0.25);
		this.highlights.clearAllMarks();

	}

}
