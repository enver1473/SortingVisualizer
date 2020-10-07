package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class HeapSort extends Sort {
	private boolean isMax;

	public HeapSort(boolean isMax) {
		this.isMax = isMax;

		if (isMax)
			this.setLabels("Max Heap Sort", "Max Heap");
		else
			this.setLabels("Min Heap Sort", "Min Heap");
	}

	private void downHeap(int[] array, int root, int dist, int start, double sleep, boolean isMax) {
		int compareVal = 0;

		if (isMax)
			compareVal = -1;
		else
			compareVal = 1;

		while (root <= dist / 2) {
			int leaf = 2 * root;
			if (leaf < dist && this.reads.compare(array[start + leaf - 1], array[start + leaf]) == compareVal) {
				leaf++;
			}
			this.highlights.markArray(1, start + root - 1);
			this.highlights.markArray(2, start + leaf - 1);
			this.delays.sleep(sleep);
			if (this.reads.compare(array[start + root - 1], array[start + leaf - 1]) == compareVal) {
				this.writes.swap(array, start + root - 1, start + leaf - 1, 0, true, false);
				root = leaf;
			} else
				break;

		}

	}

	protected void heapify(int[] arr, int low, int high, double sleep, boolean isMax) {
		int length = high - low;
		for (int i = length / 2; i >= 1; i--) {
			downHeap(arr, i, length, low, sleep, isMax);
		}
	}

	protected void heapSort(int[] arr, int start, int length, double sleep, boolean isMax) {
		heapify(arr, start, length, sleep, isMax);

		for (int i = length - start; i > 1; i--) {
			this.writes.swap(arr, start, start + i - 1, sleep);
			downHeap(arr, 1, i - 1, start, sleep, isMax);
		}

		if (!isMax) {
			this.writes.reversal(arr, start, start + length - 1, 1, true, false);
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
		this.heapSort(this.canvas.getArray(), 0, this.canvas.count, 0.33, this.isMax);
		this.highlights.clearAllMarks();

	}

}
