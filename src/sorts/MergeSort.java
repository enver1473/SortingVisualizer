package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class MergeSort extends Sort {

	public MergeSort() {
		this.setLabels("Merge Sort", "Merge");
	}

	private void merge(int[] array, int start, int mid, int end) {
		int[] tmp = new int[end - start];

		int low = start;
		int high = mid;

		for (int nxt = 0; nxt < tmp.length; nxt++) {
			if (low >= mid && high >= end)
				break;
			this.highlights.clearAllMarks();

			this.highlights.markArray(1, low);
			this.highlights.markArray(2, high);

			if (low < mid && high >= end) {
				this.highlights.clearMark(2);
				this.writes.write(tmp, nxt, array[low++], 1, false, true);
			} else if (low >= mid && high < end) {
				this.highlights.clearMark(1);
				this.writes.write(tmp, nxt, array[high++], 1, false, true);
			} else if (this.reads.compare(array[low], array[high]) == -1) {
				this.writes.write(tmp, nxt, array[low++], 1, false, true);
			} else {
				this.writes.write(tmp, nxt, array[high++], 1, false, true);
			}

		}
		this.highlights.clearAllMarks();

		for (int i = 0; i < tmp.length; i++) {
			this.writes.write(array, start + i, tmp[i], 1, true, false);

		}
	}

	protected void mergeSort(int[] array, int start, int mid, int end) {
		if (start == mid)
			return;

		mergeSort(array, start, (mid + start) / 2, mid);
		mergeSort(array, mid, (mid + end) / 2, end);

		merge(array, start, mid, end);
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

		int start = 0;
		int end = this.canvas.count;
		int mid = start + ((end - start) / 2);

		this.mergeSort(this.canvas.getArray(), start, mid, end);
		this.highlights.clearAllMarks();

	}

}
