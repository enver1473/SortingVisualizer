package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class StoogeSort extends Sort {
	public StoogeSort() {
		super();

		this.setLabels("Stooge Sort", "Stooge");
	}

	private void stoogeSort(int[] A, int i, int j) {
		if (this.reads.compare(A[i], A[j]) == 1) {
			this.writes.swap(A, i, j, 0.005);
		}

		this.delays.sleep(0.0025);

		this.highlights.markArray(1, i);
		this.highlights.markArray(2, j);

		if (j - i + 1 >= 3) {
			int t = (j - i + 1) / 3;

			this.highlights.markArray(3, j - t);
			this.highlights.markArray(4, i + t);

			this.stoogeSort(A, i, j - t);
			this.stoogeSort(A, i + t, j);
			this.stoogeSort(A, i, j - t);
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
		stoogeSort(this.canvas.getArray(), 0, this.canvas.count - 1);
		this.highlights.clearAllMarks();

	}
}