package sorts;

import gui.CanvasPane;
import gui.Main;
import templates.Sort;
import util.Delays;
import util.Highlights;
import util.Reads;
import util.Writes;

final public class SmartGnomeSort extends Sort {
	public SmartGnomeSort() {
		super();

		this.setLabels("Optimized Gnome Sort", "Smart Gnome");
	}

	private void swapIntoPlace(int[] array, int lowerBound, int upperBound, double sleep) {
		int pos = upperBound;

		while (pos > lowerBound && this.reads.compare(array[pos - 1], array[pos]) == 1) {
			this.writes.swap(array, pos - 1, pos, sleep);
			pos--;

		}
	}

	public void customSort(int[] array, int low, int high, double sleep) {
		for (int i = low + 1; i < high; i++) {
			swapIntoPlace(array, low, i, sleep);
		}
	}

	public void smartGnomeSort(int[] array, int length) {
		for (int i = 1; i < length; i++) {
			swapIntoPlace(array, 0, i, 0.05);
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
		smartGnomeSort(this.canvas.getArray(), this.canvas.count);
		this.highlights.clearAllMarks();

	}
}