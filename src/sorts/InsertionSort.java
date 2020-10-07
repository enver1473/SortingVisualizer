package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

public class InsertionSort extends Sort {

	public InsertionSort() {
		super();

		this.setLabels("Insertion Sort", "Insertion");
	}

	public void insertionSort(int[] array, int start, int end, double sleep) {
		int pos;
		int current;

		for (int i = start; i < end; i++) {
			current = array[i];
			pos = i - 1;

			while (pos >= start && this.reads.compare(array[pos], current) > 0) {
				this.writes.write(array, pos + 1, array[pos], sleep);
				pos--;

			}
			this.writes.write(array, pos + 1, current, sleep);

		}
	}

	public void customInsertSort(int[] array, int start, int end, double sleep) {
		insertionSort(array, start, end, sleep);
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
		insertionSort(this.canvas.getArray(), 0, this.canvas.count, 0.015);
		this.highlights.clearAllMarks();
		
	}

}
